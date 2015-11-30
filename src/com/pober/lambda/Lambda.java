package com.pober.lambda;

/**
 * Created by Bob
 * on 15/11/29.
 */
interface IFather{
    void onResponse();
    void test
}

public class Lambda {
    static IFather father= () -> System.out.print("onResponse is invoked");
    public static void main(String[] args){
        father.onResponse();
    }
}


/**
 * 语法和结构

 所以，标准的lambda表达式的语法像下面这样：
 () -> some expression
 或者 (arguments) -> { body just like function }
 一个 lambda 表达式有以下三部分组成：
 用括号包裹，并以逗号分割的参数列表

 // 接受连个整数，并返回它们的和
 (int x, int y) -> x + y

 // 用一个整数作为参数的lambda表达式，并且返回该整数的下一个整数
 (int x) -> { return x + 1; }
 在lambda表达式中，你可以省略参数的数据类型。

 // 同样的lambda表达式，但是没有了数据类型
 (x, y) -> x + y

 (x) -> { return x + 1; }
 此外，如果只有一个参数的话，你甚至可以将括号省略掉

 // 只有一个参数的lambda表达式，并且省略掉了括号
 x -> { return x + 1; }
 箭头符号，->

 //没有参数，并且返回一个常数92的lambda表达式
 () -> 92

 // 接受一个字符串作为参数，并且将其输出在控制台中
 (String s) -> { System.out.println(s); }
 函数体(body)——包括至少一句表达式，或这一个表达式块。在表达式中，主体部分被执行，并且返回。

 // 一条语句的body，没有必要使用花括号包裹或返回语句。
 x -> x + 1

 // 简单的lambda表达式，返回值为空
 () -> System.out.println(“Hello World!”)
 在代码块的形式中，body就像一个方法的body那样被执行，并且返回语句将流程返回到匿名方法的调用处。
 好了，我们已经花了些时间来了解lambda表达式的语法，接下来，让我们来看一些实际的例子吧。
 */