# Java 8 Lambda 表达式示例
自从我听说Java8将要支持Lambda表达式（或称闭包），我便开始狂热的想要将这些体面的简洁的功能元素应用到我的代码中来。大多开发者普遍的使用匿名内部类来开发事件处理器，比较器，thread/runnable实现等等，一些没有必要的辅助代码将逻辑复杂化，即便一些非常简单的代码也变的复杂不堪。Java8现在加入了Lambda表达式作为语法的一部分将会极大地解决这一类似问题。
它使得开发者可以封装一个单独的行为单元并且传递给其他代码。他更像是一个匿名类（带有一个方法的可推断类型）的语法糖和一个更少对象的方法。我希望这篇文章不会扯过多广泛的理论材料，但是在理解Lambda的语法，结构和实例前，这里还是有一个非常重要的概念需要注意。

## 函数式接口
一个函数式接口 (又或者称单抽象方法类型或者SMA)是任何包含为一个抽象方法的接口. 但它可能包含一些static或者/和default 方法.java.lang.Runnableis是一个函数式接口的例子, 因为他只有一个run()方法, 并且是抽象的. 类似的 ActionListener也是一个函数式接口. 下面是一个用户自定义函数式接口的例子。

```
interface Worker() {  
    boolean doWork();  
};  
```  

喊一下另一个典型的函数式接口的例子:

	interface Operator {
    	TYPE operate(TYPE operand1, TYPE operand2);
	}
	
就是这样, 因为他就是一个含有一个抽象方法的普通接口. 尽管对于函数式接口还有很多需要讨论尤其是java8提供的 java.util.function包和@FunctionalInterface注解，但现在仅仅关注lambdas。我会在另一篇单独的博客中讨论那些话题。

## Lambda 表达式

> Lambda 表达式，也被称为闭包，是为开发者提供用简单和紧凑的方式表示数据的匿名函数。
							*Brian Goetz, Specification Lead for JSR 335*  

为了更容易的了解lambda表达式的语法，我们先来看一下常规的匿名内部类。

```
new Runnable() {
   	public void run() {
        performWork();
    }
};
```	

Lambda 表达式为匿名内部类提供了一个更简洁的实现方法，上面的5行代码可以被转换成下面这一行代码：  
```() -> performWork();```

## 语法和结构

所以，标准的lambda表达式的语法像下面这样：

```() -> some expression```
或者

```(arguments) -> { body just like function }```  

一个 lambda 表达式有以下三部分组成：  

1. 用括号包裹，并以逗号分割的参数列表

	```
	// 接受连个整数，并返回它们的和
	(int x, int y) -> x + y
 
	// 用一个整数作为参数的lambda表达式，并且返回该整数的下一个整数
	(int x) -> { return x + 1; }
	```

	在lambda表达式中，你可以省略参数的数据类型。

	```
	// 同样的lambda表达式，但是没有了数据类型
	(x, y) -> x + y
 
	(x) -> { return x + 1; }
	```
	此外，如果只有一个参数的话，你甚至可以将括号省略掉
	
	```
	// 只有一个参数的lambda表达式，并且省略掉了括号
	x -> { return x + 1; }
	```

2. 箭头符号，->  

	```
	//没有参数，并且返回一个常数92的lambda表达式
	() -> 92
 
	// 接受一个字符串作为参数，并且将其输出在控制台中
	(String s) -> { System.out.println(s); }
	```
3. 函数体(body)——包括至少一句表达式，或这一个表达式块。在表达式中，主体部分被执行，并且返回。

	```
	// 一条语句的body，没有必要使用花括号包裹或返回语句。
	x -> x + 1
 
	// 简单的lambda表达式，返回值为空
	() -> System.out.println(“Hello World!”)
	```
	在代码块的形式中，body就像一个方法的body那样被执行，并且返回语句将流程返回到匿名方法的调用处。
好了，我们已经花了些时间来了解lambda表达式的语法，接下来，让我们来看一些实际的例子吧。

## Lambda 表达式的例子

为了更容易的了解lambda表达式，让我们先来看几个那lambda表达式和匿名内部类作比较的例子。在第一个例子中，我们会看到使用lambda表达式来实现一个 Comparator 接口。假设我们有一个Person 类，该类有一个name属性，并且，我们创建了一个Person类型的数组，名为persons。

```
Arrays.sort(persons, new Comparator() {
 
    @Override
    public int compare(Person first, Person second) {
 
        return first.getName().compareTo(second.getName());
    }
});

// 这也是一个标准的排序，但是有趣的是，它传入的是一个lambda表达式，而不是一个Comparator类型的对象
Arrays.sort(persons,(first, second) -> first.getName().compareTo(second.getName()));
```
注意到5行的代码被精简到了1行，这就是lambda表达式比匿名内部类漂亮的地方。接下来，让我们看一个用lambda表达式来实现 Runable 接口的例子。结果也是一样。

```
Runnable printer = new Runnable() {
 
    @Override
    public void run() {
 
        System.out.println("Hello, I’m inside runnable class...");
    }
};
 
printer.run();
 
printer = () -> System.out.println("Hello, I’m inside runnable lambda...");
 
printer.run();
```

为用户自定义的接口写lambda表达式也同样是间非常简单容易的事。下面的例子使用 Operator自定义接口，并且将lambda表达式的引用保存在了变量中，以便重用。

```
Operator addition = (op1, op2) -> op1 + op2;
 
System.out.println("Addition result: " + addition.operate(2, 3));
```

为了更深入的理解，这里有一个使用了代码块的lambda表达式的例子。

```
interface GenericOperator {
 
    TYPE operate(TYPE ... operands);
}
 
//使用了代码块的lambda表达式
GenericOperator multiply = numbers -> {
 
    int result = 0;
 
    for(int num : numbers)
        result *= num;
 
    return result;
};
 
System.out.println("Multiplication result: " + multiply.operate(2,3,4));
```

如上所示，代码块就如同一个普通方法的代码块，lambda表达式就如同一个方法，但是它更有意义。

## 什么时候使用Lambda表达式

Lambda表达式并不是匿名内部类的替代者，而是一个用来实现单一抽象方法的更好的方式。两者都有自己的意义，并在各自特定的情况下使用。  

* Lambda 表达式用来实现单一的行为，并且该行为要被传递个其他的代码。
* 当只需要一个功能接口的简单实例，而不需要类型，构造方法和一些相关的东西时，Lambda表达式比较适用。
* 另一方面，只在需要新的字段和功能时使用匿名内部类。

## Lambda表达式和匿名内部类

* 匿名内部类会引入下一级作用域，而lambda表达式不会。在匿名内部类中可以出现和父类中相同名字的变量，但这在lambda表达式中会报错，并且不允许这样做。因为后者不会引入下一级作用域，并且父级作用域中的局部变量和方法可以被直接访问。
* 匿名内部类把 this 关键字作为它自身的对象，而lambda表达式把它当成是该表达式所在类的对象。在表达式所在的类中，你可以通过使用 this 来访问lambda表达式中的变量。
* 但是，像匿名内部类那样，lambda表达式只能访问所在类中用final修饰的变量。同样的，如果要访问非final修饰的变量则会报错。
* 只有当lambda表达式的参数和返回值都是Serializable类型时才允许将其序列化。就像不推荐序列化匿名内部类一样，我们也不推荐序列化lambda表达式。
* Lambda表达式被编译成其所在类的私有方法。java 7中引入的invokedynamic被用来动态的绑定方法。


