# Lambda for Java 8
## 概述

### 目的
本文主要是来介绍Java SE 8里的Lambda表达式。
### 阅读耗时
大约1小时
### 介绍：
Lambda 表达式是Java SE8中的新特性。它提供了一种清晰而简洁的方式来表示接口方法。Lambda表达式也提高了集合库的一些循环，过滤以及从集合中取数据的能力。另外，也提高了多核芯环境下程序的并发能力。
这些Oracle 例子(OBE)提供了关于Java SE 8的Lambda表达式的介绍。提供了对匿名内部函数的介绍，并且已经被函数式接口和新的Lambda句法所遵从。那么在Lambda之前以及之后的一种通用的使用模式就这样出现了。
下一个模块评估了一个常用的搜索用例和如何通过Lambda表达式来提升Java代码。除此之外，一些常用的函数式接口如：Predicate 和 Function，已经在 java.util.function 被包含了进来。
OBE完成了使用Lambda表达式如何更新Java 集合的评估。
这里还为大家提供了所有例子的源码。

### 硬件和软件要求
以下是硬件和软件的要求列表：
* JDK8及以上
* NetBeans 7.4

### 先决条件
想要运行本文中的例子，你的JDK必须要是JDK8并且NetBeans必须要是7.4版本及以上。对应的连接可以在[the main Lambda site](http://openjdk.java.net/projects/lambda/)里找到。为了方便起见，这里直接给出这两个链接
* [Java Development Kit 8 (JDK8) Early Access](https://jdk8.java.net/download.html)
	* 建议：在同一页也下载JDK 8的API文档
* [NetBeans 7.4 or later](http://netbeans.org/)，7.4版本的NetBeans支持JDK 8

**注意**：所有版本的主流操作系统都有对应的编译工具。这些OBE是使用Linux 13（Ubuntu／Debian）来写的。
安装JDK8和NetBeans等工具都有提供下载。然后需要为Java和NetBeans添加bin目录到你电脑的环境路径PATH中。

## 背景

###匿名内部类

在Java中，匿名内部类提供了一种只在程序中出现一次的方式来实现一个类（接口）。例如，在标准的Swing或者JavaFX 应用中，一些事件处理器要求键盘或鼠标事件，你可以像这样写：

```
JButton testButton = new JButton("Test Button");
     testButton.addActionListener(new ActionListener(){
     @Override
     public void actionPerformed(ActionEvent ae){
         System.out.println("Click Detected by Anon Class");
       }
     });
```
否则，你就需要为每一个事件写一个类来实现ActionListener。在这种地方创建类，所有创建该类的地方的代码都会变得不易读。大量的代码被书写仅仅是为了定义一个方法就导致了代码缺乏优雅性。

### 函数式接口

定义ActionListener接口的代码如下：

```
	package java.awt.event;
   import java.util.EventListener;
    
   public interface ActionListener extends EventListener {
        
   public void actionPerformed(ActionEvent e);
    
   }
```
这个ActionListener案例死一个仅包含一个方法的接口。在Java SE8中，像这样的代码形式被称为“函数式接口”
	**注意:**接口类型之前被称作单抽象方法类型（SAM）

在匿名内部类上使用函数式接口是在Java中是一种比较普遍使用的模式。除EventListener类以外，还有Runnable和Comparator等像这样被使用。所以，使用Lambda表达式对函数式接口的使用意义非凡。

### Lambda表达式句法：
Lambda表达式可以将繁杂的匿名内部类代码从5行转换为1行。这种简单水平答案解决了内部类带来的“纵向问题”。

一个Lambda表达式由以下部分组成：  

	参数列表		     	  |  箭头  |  函数体  
	----------------------|-------|--------  
	(int x, int y)        |  ->   |	x + y  
函数体可以是单行的表达式，也可以声明一个语句块。  

* 表达式：表达式会被执行然后返回执行结果
* 语句块：语句块中的语句会被依次执行，就像方法中的语句一样
	* `return`语句会把控制权交给匿名方法的调用者
	* `break`和`continue`只能在循环中使用
	* 如果函数有返回值，那么函数体内部的每一条路径都必须有返回值或者抛出异常

再看看以下例子：

```
(int x, int y) -> x + y

() -> 42

(String s) -> { System.out.println(s); }
```		
 第一个Lambda表达式有两个整型参数`x`和`y`,并且返回两个数的和。第二个表达式没有参数，返回了一个整型数42.第三个表达式有一个字符串参数，并且将它打印到控制台，没有返回值。
 
看了一些基本的句法，我们再来看看一些例子。

## Lambda例子

### Runnable Lambda
使用Lambda来写Runnable

```
public class RunnableTest {
   public static void main(String[] args) {
     
     System.out.println("=== RunnableTest ===");
     
     // Anonymous Runnable
     Runnable r1 = new Runnable(){
       
       @Override
       public void run(){
         System.out.println("Hello world one!");
       }
     };
     
     // Lambda Runnable
     Runnable r2 = () -> System.out.println("Hello world two!");
     
     // Run em!
     r1.run();
     r2.run();
     
   }
 }
```
在两个例子中，注意这里没有参数传递和返回。Lambda表达式把5行代码转换为了一行。

Comparator Lambda
在java中，Comparator类是用来对Collections集合进行排序的。在以下例子中，一个ArrayList包涵了一组使用surName属性来排序的Person对象集合，以下是Person类里的字段。

```
public classPerson{	private String givenName;	private String surName;	private int age;	private Gender gender;	private String eMail;	private String phone;	private String address;
```
以下代码使用了内部类的方式以及一些Lambda表达式来应用Comparator类

```
public class ComparatorTest{	public static void main(String[] args) { 	List<Person> personList = Person.createShortList();  	// Sort with Inner Class 	Collections.sort(personList, new Comparator<Person>(){ 		public int compare(Person p1, Person p2){ 			return p1.getSurName().compareTo(p2.getSurName());   		}    });	System.out.println("=== Sorted Asc SurName ===");	for(Person p:personList){  		p.printName();    }	// Use Lambda instead
		// Print Asc	System.out.println("=== Sorted Asc SurName ===");	Collections.sort(personList, (Person p1, Person p2) ->	p1.getSurName().compareTo(p2.getSurName())); 	
	for(Person p:personList){  		p.printName();    }

	// Print Desc 	System.out.println("=== Sorted Desc SurName ==="); 	Collections.sort(personList, (p1, p2) ->	p2.getSurName().compareTo(p1.getSurName())); 	
	for(Person p:personList){
		p.printName();	
	}  }}
```
17-21行的代码可以很容易就被32行的代码所代替。请注意，第一个Lambda表达式声明了参数类型。然而，在第二个表达式中你可以看到，参数类型是可选的。Lambda支持根据上下文推理出所需要的“目标类型”。因为我们为Comparator分配范型，因此编译器能够推理出两个参数的类型是Person。

### Listener Lambda
最后我们重新看一下ActionListener这个例子。

```
public class ListenerTest {
   public static void main(String[] args) {
         
     JButton testButton = new JButton("Test Button");
     testButton.addActionListener(new ActionListener(){
     @Override public void actionPerformed(ActionEvent ae){
         System.out.println("Click Detected by Anon Class");
       }
     });
     
     testButton.addActionListener(e -> System.out.println("Click Detected by Lambda Listner"));
     
    // Swing stuff
    JFrame frame = new JFrame("Listener Test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(testButton, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);
    
  }
}
```
请注意，Lambda表达式给出了一个参数，目标类型会在一些上下文中被使用过，如：

  * 各种声明中
  * 任务委派
  * 返回声明
  * 数组初始化
  * 方法或构造器参数中
  * Lambda函数体中
  * 条件表达式中 如 ？：
  * 表达式转换

### 资源
包含有这部分源码的NetBeans工程包可以到以下链接下载  
[LambdaExamples01.zip](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/examples/LambdaExamples01.zip)


## 使用Lambda表达式优化代码
这部分依赖于之前的例子来向你展示Lambda表达式如何优化你的代码。Lambda提供了一种更好的解决重复使用规则并且使你的代码更加简洁易读的一种方式。

### 常用的查询用例
程序中常用的查询用例指在数据集合中查找符合具体某一个标准的数据。在JavaOne 2012

*In the excellent "Jump-Starting Lambda" presentation at JavaOne 2012, Stuart Marks and Mike Duigou walk though just such a use case. Given a list of people, various criteria are used to make robo calls (automated phone calls) to matching persons. This tutorial follows that basic premise with slight variations.*

在这个例子中，我们是要针对美国的以下三种人：

* 司机：年龄大于16岁的人
* 入伍士兵：年龄在18－25岁之间的男性
* 领航员（商业类领航员）：年龄在23－65岁之间的人

真正能这样做的机器人还未步入我们的商业圈。对于打电话，取而代之的可以是邮递或者邮件，一条打印到工作台的信息。信息包涵一个人的姓名，年龄以及具体信息的联系方案（例如，什么时候发邮件，打电话的时间）。

**Person 类**
每一个在测试群体中的人用包含有如下字段的Person类来定义：

```
public class Person {
  	private String givenName;
   	private String surName;
   	private int age;
   	private Gender gender;
   	private String eMail;
   	private String phone;
   	private String address;
```

这个Person类使用Builder来创建一个实体。一个人群样本使用`createShortList`方法来创建。以下是这个方法的部分代码片段。 **注意：**关于本教程的所有源码都在本模块末尾的NetBeans项目链接里。

```
public static List<Person> createShortList(){
    List<Person> people = new ArrayList<>();     
     people.add(
       new Person.Builder()
             .givenName("Bob")
             .surName("Baker")
             .age(21)
            .gender(Gender.MALE)
            .email("bob.baker@example.com")
            .phoneNumber("201-121-4678")
            .address("44 4th St, Smallville, KS 12333")
            .build() 
      );
    
    people.add(
      new Person.Builder()
            .givenName("Jane")
            .surName("Doe")
            .age(25)
             .gender(Gender.FEMALE)
            .email("jane.doe@example.com")
            .phoneNumber("202-123-4678")
            .address("33 3rd St, Smallville, KS 12333")
            .build() 
      );
    
    people.add(
      new Person.Builder()
            .givenName("John")
            .surName("Doe")
            .age(25)
            .gender(Gender.MALE)
            .email("john.doe@example.com")
            .phoneNumber("202-123-4678")
            .address("33 3rd St, Smallville, KS 12333")
            .build()
    ); 
```

### 第一次尝试
对于这个Person类和搜索功能的定义，你可以定义一个`RoboContact`类。一种可行的方案是为每一个用例定义这样一个方法：

**RoboContactsMethods.java**

```
	package com.example.lambda;
  
    import java.util.List;
    
    /**
     *
     * @author MikeW
     */
    public class RoboContactMethods {
     
     public void callDrivers(List<Person> pl){
       for(Person p:pl){
         if (p.getAge() >= 16){
           roboCall(p);
         }
       }
     }
     
     public void emailDraftees(List<Person> pl){
       for(Person p:pl){
         if (p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE){
           roboEmail(p);
         }
       }
     }
     
     public void mailPilots(List<Person> pl){
       for(Person p:pl){
         if (p.getAge() >= 23 && p.getAge() <= 65){
           roboMail(p);
         }
       }
     }
    
  		public void roboCall(Person p){
       System.out.println("Calling " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getPhone());
     }
     
     public void roboEmail(Person p){
       System.out.println("EMailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getEmail());
     }
     
     public void roboMail(Person p){
       System.out.println("Mailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getAddress());
     }
   
   }
```
正如你看到描述几种行为发生的方法名（`callDrivers`,`emailDraftees`和`mailPilots`）一样。搜索要求被清晰地表示并且每一个机器动作都发出了合适的请求。然而，这个设计仍有一些做的不好的部分：

* 没有遵循DRY原则
  * 在每一个方法里都定义了同一个循环体
  * 筛选条件在每一个方法里必须要被重写
* 很多方法被要求实现每一个用例
* 代码可变动性差，如果一个搜索条件改变了，此时就会要求大量的代码被修改。这样，代码就变得难以维护。

### 方法的重构
怎么修改以上这个类呢？搜索条件是一个不错的入口。如果把测试条件分离出一个单独的方法，那会是一个不错的提升。
**RoboContactMethods2.java**

```
package com.example.lambda;
    
    import java.util.List;
    
    /**
     *
     * @author MikeW
     */
    public class RoboContactMethods2 {
     
     public void callDrivers(List<Person> pl){
       for(Person p:pl){
         if (isDriver(p)){
           roboCall(p);
         }
       }
     }
     
     public void emailDraftees(List<Person> pl){
       for(Person p:pl){
         if (isDraftee(p)){
           roboEmail(p);
         }
       }
     }
     
     public void mailPilots(List<Person> pl){
       for(Person p:pl){
         if (isPilot(p)){
           roboMail(p);
         }
       }
     }
  
  	  public boolean isDraftee(Person p){
      return p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
     }
     
     public boolean isPilot(Person p){
       return p.getAge() >= 23 && p.getAge() <= 65;
     }
     
     public void roboCall(Person p){
       System.out.println("Calling " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getPhone());
     }
     
     public void roboEmail(Person p){
       System.out.println("EMailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getEmail());
     }
     
     public void roboMail(Person p){
       System.out.println("Mailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getAddress());
     }
   }
```
搜索判定条件被写在了一个单独的方法里，相比上一个例子是一个提升。测试条件可以在整个类中被重用、修改。然而在这个类中依旧存在着大量重复的代码，每一个用例依旧要求一个单独的方法。那么对于这种情况，还有更好的方式来优化搜索条件吗？


### 内部类

在Lambda表达式之前，匿名内部类也是一种可选的方案。比如，一个只包含一个测试方法并且返回一个boolean类型值的接口(**MyTest.java｜函数式接口**)或许是一个不错的方案。当方法被调用时测试标准应该被执行。接口如下：

```
public interface MyTest<T> {
	public boolean teset(T t);
}
```

重写机器人类如下：
**RoboContactAnon.java**

```
 public class RoboContactAnon {

  public void phoneContacts(List<Person> pl, MyTest<Person> aTest){
    for(Person p:pl){
      if (aTest.test(p)){
        roboCall(p);
      }
    }
 }

  public void emailContacts(List<Person> pl, MyTest<Person> aTest){
    for(Person p:pl){
      if (aTest.test(p)){
        roboEmail(p);
      }
    }
  }

  public void mailContacts(List<Person> pl, MyTest<Person> aTest){
    for(Person p:pl){
      if (aTest.test(p)){
        roboMail(p);
      }
    }
  }  
  
  public void roboCall(Person p){
    System.out.println("Calling " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getPhone());
  }
   
   public void roboEmail(Person p){
     System.out.println("EMailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getEmail());
   }

		 public void roboMail(Person p){
     System.out.println("Mailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getAddress());
   }
   
 }
```
这又是另外一个提升，因为机器人只需要运行三个方法。然而，当方法被调用的时候仍然会有一点丑陋的问题。为这个类再写一个测试类：

**RoboCallTest03.java**

```
package com.example.lambda;
   
   import java.util.List;
   
   /**
     * @author MikeW
    */
    public class RoboCallTest03 {
    
     public static void main(String[] args) {
      
       List<Person> pl = Person.createShortList();
      RoboContactAnon robo = new RoboContactAnon();
      
      System.out.println("\n==== Test 03 ====");
      System.out.println("\n=== Calling all Drivers ===");
      robo.phoneContacts(pl, 
          new MyTest<Person>(){
            @Override
            public boolean test(Person p){
              return p.getAge() >=16;
             }
          }
      );
      
      System.out.println("\n=== Emailing all Draftees ===");
      robo.emailContacts(pl, 
          new MyTest<Person>(){
            @Override
            public boolean test(Person p){
              return p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
            }
          }
      );
      
      System.out.println("\n=== Mail all Pilots ===");
      robo.mailContacts(pl, 
          new MyTest<Person>(){
            @Override
            public boolean test(Person p){
              return p.getAge() >= 23 && p.getAge() <= 65;
            }
          }
      );
      
      
    }
  }
```
这是一个很不错的关于“纵向”问题的案例，这段代码读起来可能稍微有点困难。另外，我们还必须为每一个用例自定义一个搜索标准。

### Lambda表达式让世界更美好
Lambda可以解决迄今为止遇到的所有问题。但是第一次会有些整理工作

**java.util.function**  
在前边的几个例子中，`MyTest`这个函数式接口使用匿名内部类来实现。然而，那样实现接口没必要。Java SE8 提供了的java.util.function包里有一些标准的函数式接口。既然这样，以下Predicate接口也符合我们的需求。

```
public interface Predicate<T> {
	public boolean test(T t);
}
```
`test`方法在一个范型类中并且返回了一个boolean值的结果。这个方法也是筛选判断需要写的部分。以下是最终版本的`robot`类。

**RoboContactsLambda.java**

```
package com.example.lambda;
    
    import java.util.List;
    import java.util.function.Predicate;
    
    /**
     *
     * @author MikeW
     */
   public class RoboContactLambda {
     public void phoneContacts(List<Person> pl, Predicate<Person> pred){
       for(Person p:pl){
         if (pred.test(p)){
           roboCall(p);
         }
       }
     }
   
     public void emailContacts(List<Person> pl, Predicate<Person> pred){
      for(Person p:pl){
         if (pred.test(p)){
           roboEmail(p);
         }
       }
     }
   
     public void mailContacts(List<Person> pl, Predicate<Person> pred){
       for(Person p:pl){
        if (pred.test(p)){
          roboMail(p);
        }
      }
    }  
    
  		public void roboCall(Person p){
      System.out.println("Calling " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getPhone());
    }
    
    public void roboEmail(Person p){
      System.out.println("EMailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getEmail());
    }
    
     public void roboMail(Person p){
      System.out.println("Mailing " + p.getGivenName() + " " + p.getSurName() + " age " + p.getAge() + " at " + p.getAddress());
    }
  
  }
```
使用这种方式，只需要三个方法就够了，每一种联系方式。Lambda表达式只需要筛选Persons实例就可以达到测试条件。

### 纵向问题的解决
Lambda表达式解决了纵向问题以及很容易就可以复用一些表达式。再看看一些实用Lambda表达式写的测试类。

**RoboCallTest04.java**

```
package com.example.lambda;
    
    import java.util.List;
    import java.util.function.Predicate;
    
    /**
     *
     * @author MikeW
     */
   public class RoboCallTest04 {
     
     public static void main(String[] args){ 
   
       List<Person> pl = Person.createShortList();
       RoboContactLambda robo = new RoboContactLambda();
       
       // Predicates
       Predicate<Person> allDrivers = p -> p.getAge() >= 16;
       Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
       Predicate<Person> allPilots = p -> p.getAge() >= 23 && p.getAge() <= 65;
       
       System.out.println("\n==== Test 04 ====");
       System.out.println("\n=== Calling all Drivers ===");
       robo.phoneContacts(pl, allDrivers);
       
       System.out.println("\n=== Emailing all Draftees ===");
       robo.emailContacts(pl, allDraftees);
       
       System.out.println("\n=== Mail all Pilots ===");
       robo.mailContacts(pl, allPilots);
       
       // Mix and match becomes easy
       System.out.println("\n=== Mail all Draftees ===");
       robo.mailContacts(pl, allDraftees);  
       
       System.out.println("\n=== Call all Pilots ===");
       robo.phoneContacts(pl, allPilots);    
       
     }
   }
```
注意Predicate设置了allDrivers，allDrafees，allPilots三组对象。你可以通过这三组任意一个来过滤并调用对应的方法。代码变的非常简洁易读，而且重复性低了。

### 资源
包含有这部分源码的NetBeans工程包可以到以下链接下载  
[RoboCallExample.zip](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/examples/RoboCallExample.zip)

## java.util.function 包
### 介绍 java.util.function 包
 当然，Java SE 8不仅仅提供了Predicate一个函数式接口。为开发者还提供了很多的标准接口作为引导。
 
 * Predicate: 带有参数的特点
 * Consumer: 带有参数并且表示一种行为
 * Function: 把一个T变成U
 * Supplier: 提供一个T的实例
 * UnaryOperator: 一个一元操作 T->T
 * BinaryOperator: 一个二元操作 (T, T)->T
除此之外，这些接口里多数都有一些早期的版本。这些都会给你的Lambda表达式体统一个不错的起点。

### 东方风格的名称以及方法引用
在写以上代码过程中，我发现对于`Person`类来讲，这是一个可变性不错的打印系统。现在有一个特别的需求是将姓名以西方以及东方两种风格的形式展现出来。在西方，名字会写在前边，姓会写在后边。在多数的东方文化里，却是姓在前，名字在后。

**一个老版本的例子**
以下是不使用Lambda表达式实现Person打印类的例子。

*Person.java*

```
public void printWesternName(){
   
     System.out.println("\nName: " + this.getGivenName() + " " + this.getSurName() + "\n" +
              "Age: " + this.getAge() + "  " + "Gender: " + this.getGender() + "\n" +
              "EMail: " + this.getEmail() + "\n" + 
              "Phone: " + this.getPhone() + "\n" +
              "Address: " + this.getAddress());
   }
   
   public void printEasternName(){
       
     System.out.println("\nName: " + this.getSurName() + " " + this.getGivenName() + "\n" +
              "Age: " + this.getAge() + "  " + "Gender: " + this.getGender() + "\n" +
              "EMail: " + this.getEmail() + "\n" + 
              "Phone: " + this.getPhone() + "\n" +
              "Address: " + this.getAddress());
   }
```
这里为每一种打印名字的风格都写了一个方法。

**函数式接口**

函数式接口对这个问题很有用。以以下方式只需要写一个方法就够了
`public R apply(T t){ }`
它使用了一个范型T和返回范型值R。在这个例子中，是Person类和返回一个字符串值。一个可修改的打印信息的方法可以这么写：

*Person.java*

```
public String printCustom(Function <Person, String> f){
       return f.apply(this);
 }
```
变得更加简单了。一个函数传递给了这个方法并且返回了一个字符串。`apply`方法可以通过Lambda表达式来决定什么样的Person信息会被返回。

那么具体函数怎么定义呢？以下是一段调用之前方法的测试代码。

**NameTestNew.java**

```
public class NameTestNew {

   public static void main(String[] args) {
     
     System.out.println("\n==== NameTestNew02 ===");
     
     List<Person> list1 = Person.createShortList();
     
     // Print Custom First Name and e-mail
     System.out.println("===Custom List===");
     for (Person person:list1){
         System.out.println(
             person.printCustom(p -> "Name: " + p.getGivenName() + " EMail: " + p.getEmail())
         );
     }
 
     
     // Define Western and Eastern Lambdas
     
     Function<Person, String> westernStyle = p -> {
       return "\nName: " + p.getGivenName() + " " + p.getSurName() + "\n" +
              "Age: " + p.getAge() + "  " + "Gender: " + p.getGender() + "\n" +
              "EMail: " + p.getEmail() + "\n" + 
              "Phone: " + p.getPhone() + "\n" +
              "Address: " + p.getAddress();
     };
     
     Function<Person, String> easternStyle =  p -> "\nName: " + p.getSurName() + " " 
             + p.getGivenName() + "\n" + "Age: " + p.getAge() + "  " + 
             "Gender: " + p.getGender() + "\n" +
             "EMail: " + p.getEmail() + "\n" + 
             "Phone: " + p.getPhone() + "\n" +
             "Address: " + p.getAddress();   
     
     // Print Western List
     System.out.println("\n===Western List===");
     for (Person person:list1){
         System.out.println(
             person.printCustom(westernStyle)
         );
     }
     // Print Eastern List
     System.out.println("\n===Eastern List===");
     for (Person person:list1){
         System.out.println(
             person.printCustom(easternStyle)
         );
     }
   }
 }
```
第一个循环打印了名字以及邮件地址。但是一些有效信息本应该被传入printCustom方法的。东方以及西方风格的打印方式使用Lambda表达式定义并且使用变量引用之。这两个变量被用在了两个循环中。Lambda表达式很容易就被合并到Map中而且复用也更加容易。Lambda表达式为代码的可修整性提供了一个不错的解决方案。

**示例输出**  
下边是程序的一些输出范例。

```
==== NameTestNew02 ===
===Custom List===
Name: Bob EMail: bob.baker@example.com
Name: Jane EMail: jane.doe@example.com
Name: John EMail: john.doe@example.com
Name: James EMail: james.johnson@example.com
Name: Joe EMail: joebob.bailey@example.com
Name: Phil EMail: phil.smith@examp;e.com
Name: Betty EMail: betty.jones@example.com

===Western List===

Name: Bob Baker
Age: 21  Gender: MALE
EMail: bob.baker@example.com
Phone: 201-121-4678
Address: 44 4th St, Smallville, KS 12333

Name: Jane Doe
Age: 25  Gender: FEMALE
EMail: jane.doe@example.com
Phone: 202-123-4678
Address: 33 3rd St, Smallville, KS 12333

Name: John Doe
Age: 25  Gender: MALE
EMail: john.doe@example.com
Phone: 202-123-4678
Address: 33 3rd St, Smallville, KS 12333

Name: James Johnson
Age: 45  Gender: MALE
EMail: james.johnson@example.com
Phone: 333-456-1233
Address: 201 2nd St, New York, NY 12111

Name: Joe Bailey
Age: 67  Gender: MALE
EMail: joebob.bailey@example.com
Phone: 112-111-1111
Address: 111 1st St, Town, CA 11111

Name: Phil Smith
Age: 55  Gender: MALE
EMail: phil.smith@examp;e.com
Phone: 222-33-1234
Address: 22 2nd St, New Park, CO 222333

Name: Betty Jones
Age: 85  Gender: FEMALE
EMail: betty.jones@example.com
Phone: 211-33-1234
Address: 22 4th St, New Park, CO 222333

===Eastern List===

Name: Baker Bob
Age: 21  Gender: MALE
EMail: bob.baker@example.com
Phone: 201-121-4678
Address: 44 4th St, Smallville, KS 12333

Name: Doe Jane
Age: 25  Gender: FEMALE
EMail: jane.doe@example.com
Phone: 202-123-4678
Address: 33 3rd St, Smallville, KS 12333

Name: Doe John
Age: 25  Gender: MALE
EMail: john.doe@example.com
Phone: 202-123-4678
Address: 33 3rd St, Smallville, KS 12333

Name: Johnson James
Age: 45  Gender: MALE
EMail: james.johnson@example.com
Phone: 333-456-1233
Address: 201 2nd St, New York, NY 12111

Name: Bailey Joe
Age: 67  Gender: MALE
EMail: joebob.bailey@example.com
Phone: 112-111-1111
Address: 111 1st St, Town, CA 11111

Name: Smith Phil
Age: 55  Gender: MALE
EMail: phil.smith@examp;e.com
Phone: 222-33-1234
Address: 22 2nd St, New Park, CO 222333

Name: Jones Betty
Age: 85  Gender: FEMALE
EMail: betty.jones@example.com
Phone: 211-33-1234
Address: 22 4th St, New Park, CO 222333
```

### 资源
包含有这部分源码的NetBeans工程包可以到以下链接下载  
[LambdaFunctionExamples.zip](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/examples/LambdaFunctionExamples.zip)

## Lambda表达式以及集合
前边的几个模块介绍了函数式接口以及使用Lambda表达式写了一些例子。这个模块将介绍Lambda表达式对Collection集合的优化。

### Lambda表达式以及集合
到目前为止讲到的例子中，集合类就是用了一点点。然而，一些Lambda表达式特性可以影响一些使用集合的方式。以下模块介绍了一少部分的新特性。

**其他类**  
司机，领航员，以及入伍士兵的搜索条件已经被涵盖在了`SearchCriteria`类中

*SearchCriteria.java*

```
package com.example.lambda;
  import java.util.HashMap;
  import java.util.Map;
  import java.util.function.Predicate;
  
  /**
   *
   * @author MikeW
  */
 public class SearchCriteria {
 
   private final Map<String, Predicate<Person>> searchMap = new HashMap<>();
 
  private SearchCriteria() {
     super();
    initSearchMap();
  }

  private void initSearchMap() {
    Predicate<Person> allDrivers = p -> p.getAge() >= 16;
    Predicate<Person> allDraftees = p -> p.getAge() >= 18 && p.getAge() <= 25 && p.getGender() == Gender.MALE;
    Predicate<Person> allPilots = p -> p.getAge() >= 23 && p.getAge() <= 65;

    searchMap.put("allDrivers", allDrivers);
    searchMap.put("allDraftees", allDraftees);
    searchMap.put("allPilots", allPilots);

  }

  public Predicate<Person> getCriteria(String PredicateName) {
    Predicate<Person> target;

    target = searchMap.get(PredicateName);

    if (target == null) {

      System.out.println("Search Criteria not found... ");
      System.exit(1);
    
    }
      
    return target;
		 }

  public static SearchCriteria getInstance() {
    return new SearchCriteria();
  }
}
```
基于搜索条件的Predicate使用在了这类里并且对我们的测试方法也是可用的。

**循环**  
第一个特性我们来着眼于一些可用forEach方法的集合类。以下是两个打印Person列表的例子  
*Test01ForEach.java*

```
public class Test01ForEach {
  
  public static void main(String[] args) {
    
    List<Person> pl = Person.createShortList();
    
    System.out.println("\n=== Western Phone List ===");
    pl.forEach( p -> p.printWesternName() );
    
    System.out.println("\n=== Eastern Phone List ===");
    pl.forEach(Person::printEasternName);
    
    System.out.println("\n=== Custom Phone List ===");
    pl.forEach(p -> { System.out.println(p.printCustom(r -> "Name: " + r.getGivenName() + " EMail: " + r.getEmail())); });
    
  }

}
```
第一个例子使用了标准的Lambda表达式来打印每一个人的西方文化的姓名。第二个例子使用了一个方法映射。在这种一个类里已经存在一个方法去执行某个操作的时候，这种句式就可以使用基本的Lambda表达式来做到。最后一个例子在这种情况下如何使用printCustom来实现同样的功能。请注意当一个Lambda表达式在不同的地方被引入的时候，会因为名称的不同而产生细微的差异。
你可以对其他集合得出类似的结论。对循环的优化来讲基本的结构都是类似的。然而，对于拥有迭代器的类来讲好处更多。

**链表、过滤器**

除了循环容器的内容以外，you can chain method together.第一个方法是把Predicate接口作为参数的过滤器。

*Test02Filter.java*

```
public class Test02Filter {
  
   public static void main(String[] args) {
 
     List<Person> pl = Person.createShortList();
     
     SearchCriteria search = SearchCriteria.getInstance();
     
     System.out.println("\n=== Western Pilot Phone List ===");
 
     pl.stream().filter(search.getCriteria("allPilots"))
       .forEach(Person::printWesternName);
     
    
     System.out.println("\n=== Eastern Draftee Phone List ===");
 
     pl.stream().filter(search.getCriteria("allDraftees"))
       .forEach(Person::printEasternName);
     
   }
}
```
第一个和最后一个循环展示了基于搜索标准的List过滤器。输出如下：

```
=== Eastern Draftee Phone List ===

Name: Baker Bob
Age: 21  Gender: MALE
EMail: bob.baker@example.com
Phone: 201-121-4678
Address: 44 4th St, Smallville, KS 12333

Name: Doe John
Age: 25  Gender: MALE
EMail: john.doe@example.com
Phone: 202-123-4678
Address: 33 3rd St, Smallville, KS 12333
```
*还可以更懒*  
这些特性都很有用，但目前集合的循环策略已经足够完美了啊？把这些特性加到库里，Java开发者将能够对代码做更多的优化。为了更好的解释，以下定义了几个术语：

* **懒惰:** 
在程序中，懒指的是只在你需要执行代码块的时候程序才会去执行。在前边的几个例子中，最后一个循环之所以被称为“懒”是因为它只需要在过滤后剩下的两个对象中进行循环。由于最终的操作都是在选定的对象上执行的，因此代码效率更高。
* **饥饿:**
在列表中每一个对象上的操作可以看作“渴望”。例如，通过将一整个List集合的范围转换为两个对象的循环优化就可以看作更加“急切渴望”的做法。

通过两个集合库上做循环，当合适的情况下代码可以通过“懒惰”的方式来得到优化。当一个更迫切的操作需要响应（如求和求平均值），饥饿法就可以被使用。这种方案要比只用饥饿法的效率和可修改性要高很多。

*stream方法*   
在前边的例子中，请注意stream方法在过滤和循环开始之前被调用。这个方法将Collection作为输入，返回一个java.util.stream.Stream接口对象作为输出。一个流表示一系列将多种方法连接到一起的元素。默认的，一旦元素被用尽，流里的这些元素将不再可用。因此，一串的操作在一个特定的流上只能够执行一次。除此之外，依赖于方法被调用，一个流也可以被序列化（默认）或平行化。一个平行化流的例子会在本模块的末尾进行介绍。

**转变和结果**  
正如之前提到的一样，一个流会在使用后被销毁掉。因此，一个集合里的元素是不能够通过流来修改或者转型。那你到底想要在你一系列的操作完成之后返回什么样的元素呢？你可以把它们存到一个新的集合中。以下代码会告诉你怎么做：  

**Test03toList.java**  

```
public class Test03toList {
   
   public static void main(String[] args) {
     
     List<Person> pl = Person.createShortList();
     
     SearchCriteria search = SearchCriteria.getInstance();
     
     // Make a new list after filtering.
     List<Person> pilotList = pl
             .stream()
             .filter(search.getCriteria("allPilots"))
             .collect(Collectors.toList());
     
     System.out.println("\n=== Western Pilot Phone List ===");
     pilotList.forEach(Person::printWesternName);
 
   }
 
 }
```
`collect`方法只有一个参数，Collectors类。Collectors类可以基于结果流返回一个List或者Set对象。这个例子展示了结果流如何被指派到一个新的List集合。

**map的计算**  
map方法经常和过滤器配合使用。这个方法从一个类里获取特性并为之服务。以下的例子基于计算年龄来说明这个特性。

*Test04Map.java*

```
public class Test04Map {

   public static void main(String[] args) {
     List<Person> pl = Person.createShortList();
     
     SearchCriteria search = SearchCriteria.getInstance();
     
     // Calc average age of pilots old style
     System.out.println("== Calc Old Style ==");
     int sum = 0;
     int count = 0;
     
     for (Person p:pl){
       if (p.getAge() >= 23 && p.getAge() <= 65 ){
         sum = sum + p.getAge();
         count++;
       }
     }
     
     long average = sum / count;
     System.out.println("Total Ages: " + sum);
     System.out.println("Average Age: " + average);
     
     
     // Get sum of ages
     System.out.println("\n== Calc New Style ==");
     long totalAge = pl
             .stream()
             .filter(search.getCriteria("allPilots"))
             .mapToInt(p -> p.getAge())
             .sum();
 
     // Get average of ages
     OptionalDouble averageAge = pl
             .parallelStream()
             .filter(search.getCriteria("allPilots"))
             .mapToDouble(p -> p.getAge())
             .average();
 
     System.out.println("Total Ages: " + totalAge);
     System.out.println("Average Age: " + averageAge.getAsDouble());    
     
   }
   
 }
```
输出如下：

```
== Calc Old Style ==
Total Ages: 150
Average Age: 37

== Calc New Style ==
Total Ages: 150
Average Age: 37.5
```
这段代码计算了我们列表中领航员的平均年龄。第一个循环展示了使用老风格的for循环计算数字。第二个循环通过一个序列化流使用了map方法获取每一个人的年龄。注意总年龄是一个long型数。map方法返回一个含有返回值为long型数据的sum方法的IntSteam对象。

**注意:**
第二次计算平均值时，计算总年龄是没必要的。接下来便是sum方法的例子。

最后一个循环从流中计算了平均年龄。注意parallelStream方法是用来获取平行流里的数据并加以运算。返回值在这里也略有不同。

### 资源
包含有这部分源码的NetBeans工程包可以到以下链接下载  
[LambdaCollectionExample.zip](http://www.oracle.com/webfolder/technetwork/tutorials/obe/java/Lambda-QuickStart/examples/LambdaCollectionExamples.zip)

##总结
在本次的教程中，你可以学到：

* Java中的匿名内部类
* 在Java SE 8中使用Lambda表达式替换匿名内部类
* Lambda表达式的正确句法
* Perdicate接口在List列表搜索上的使用
* 使用函数式接口将一种数据转型
* 在Java SE 8中添加到Collections中一些支持Lambda表达式的新特性

### 资源
更多关于Java SE 8的Lambda表达式的信息，可以看看以下链接：

* [Java 8](http://openjdk.java.net/projects/jdk8/)
* [Lambda项目](http://openjdk.java.net/projects/lambda/)
* [Lambda说明](http://cr.openjdk.java.net/~briangoetz/lambda/lambda-state-4.html)
* [Lambda集合的说明](http://cr.openjdk.java.net/~briangoetz/lambda/sotc3.html)
* [Jump-Starting Lambda JavaOne 2012(You Tube)](http://www.youtube.com/watch?v=bzO5GSujdqI)
* 学习更多关于Java以及了解相关话题，请点击 [Oracle Learning Library](http://www.oracle.com/goto/oll)
