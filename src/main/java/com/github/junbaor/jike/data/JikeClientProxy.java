package com.github.junbaor.jike.data;

import com.github.junbaor.jike.exceptions.UnauthorizedException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class JikeClientProxy implements MethodInterceptor {

    private final JikeClientImpl target;

    public JikeClientProxy(JikeClientImpl target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object result = null;
        try {
            result = method.invoke(target, objects);
        } catch (UnauthorizedException e) {
            target.refreshToken();
            result = method.invoke(target, objects);
        }
        return result;
    }

    public static JikeClientImpl getProxy(JikeClientImpl target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new JikeClientProxy(target));
        return (JikeClientImpl) enhancer.create();
    }

}
