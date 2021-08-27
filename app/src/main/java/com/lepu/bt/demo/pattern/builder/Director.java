package com.lepu.bt.demo.pattern.builder;

/**
 * =================================================================================================
 * 指挥者
 * <p>
 * Author: qixin
 * Date: 2021/7/22 上午 10:33
 * =================================================================================================
 */
public class Director {
    //指挥工人按顺序造房
    public Product create(Builder builder) {
        builder.bulidA();
        builder.bulidB();
        builder.bulidC();
        builder.bulidD();
        return builder.getProduct();
    }
}
