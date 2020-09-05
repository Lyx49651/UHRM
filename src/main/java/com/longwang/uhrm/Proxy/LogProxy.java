package com.longwang.uhrm.Proxy;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LogProxy implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation mi) throws Throwable{
        pre_message();
        Object obj = mi.proceed();
        aft_message();
        return obj;
    }
    private void pre_message(){
//        System.out.println("---------------前置分割线--------------------");
    }
    private void aft_message(){
//        System.out.println("---------------后置分割线--------------------");
    }
}
