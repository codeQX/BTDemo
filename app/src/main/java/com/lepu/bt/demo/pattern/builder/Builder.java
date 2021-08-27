package com.lepu.bt.demo.pattern.builder;

/**
 * =================================================================================================
 * 建造者
 * <p>
 * Author: qixin
 * Date: 2021/7/22 上午 10:31
 * =================================================================================================
 */
abstract class Builder {
    //地基
    abstract void bulidA();
    //钢筋工程
    abstract void bulidB();
    //铺电线
    abstract void bulidC();
    //粉刷
    abstract void bulidD();
    //完工-获取产品
    abstract Product getProduct();
}
