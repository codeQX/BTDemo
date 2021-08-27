package com.lepu.bt.demo.pattern

import com.lepu.bt.demo.pattern.builder.ConcreteBuilder
import com.lepu.bt.demo.pattern.builder.Director

/**
 * =================================================================================================
 * 默认描述
 *
 * Author: qixin
 * Date: 2021/7/22 上午 10:34
 * =================================================================================================
 */
fun main() {
    val director = Director()
    val create = director.create(ConcreteBuilder())
    println(create)
}