package com.pober.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Bob
 * on 15/11/29.
 */
interface IFather{
    int onResponse(int x );
    //void test(String sta);Lambda仅仅适用于父类仅有一个方法的情况
}

/**
 * 在Android studio中,默认是不支持Lambda的,即使你使用的是jdk1.8
 * 因为它要兼容1.8及一下的java版本,而Lambda是不兼容的.
 * If necessary,需要使用
 compileOptions {
 sourceCompatibility JavaVersion.VERSION_1_8;
 targetCompatibility JavaVersion.VERSION_1_8;
 }
 在gradle中显示声明编译所使用的java版本
 */
public class Lambda {

    public static void main(String[] args){
        IFather father= x -> 0;
    }
}
