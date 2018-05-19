package com.centit.demo.kotlin

//Array<String>
fun main(vararg args: String) {
    var a = 1
// 模板中的简单名称：
    val s1 = "a is $a"
    println(s1)
    a = 2
// 模板中的任意表达式：
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)

    val sumLambda: (Int, Int) -> Int = {x,y ->  x + y }
    println(sumLambda(1,2))  // 输出 3

    val testLombok = TestLombok()
    testLombok.numa = 12
    testLombok.numb = 15
    println(testLombok.sum())
}