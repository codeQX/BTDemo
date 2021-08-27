package com.lepu.bt.demo.pattern.builder;

/**
 * =================================================================================================
 * 具体建造者(工人)
 * <p>
 * Author: qixin
 * Date: 2021/7/22 上午 10:32
 * =================================================================================================
 */
public class ConcreteBuilder extends Builder{
    private Product product;
    public ConcreteBuilder() {
        product = new Product();
    }
    @Override
    void bulidA() {
        product.setBuildA("地基");
    }
    @Override
    void bulidB() {
        product.setBuildB("钢筋工程");
    }
    @Override
    void bulidC() {
        product.setBuildC("铺电线");
    }
    @Override
    void bulidD() {
        product.setBuildD("粉刷");
    }
    @Override
    Product getProduct() {
        return product;
    }
}
