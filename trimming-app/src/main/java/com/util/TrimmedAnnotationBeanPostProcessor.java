package com.util;

import com.bobocode.annotation.Trimmed;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Trimmed.class)) {
            return createTrimmingProxy(beanClass);
        }
        return bean;
    }

    private Object createTrimmingProxy(Class<?> beanClass) {
        var enhancer = new Enhancer();
        enhancer.setSuperclass(beanClass);
        enhancer.setInterfaces(beanClass.getInterfaces());
        MethodInterceptor interceptor = (obj, method, args, proxy) -> {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof String) {
                    args[i] = ((String) args[i]).trim();
                }
            }
            Class<?> returnType = method.getReturnType();
            if (returnType.equals(String.class)) {
                return proxy.invokeSuper(obj, args).toString().trim();
            }
            return proxy.invokeSuper(obj, args);
        };
        enhancer.setCallback(interceptor);
        return beanClass.cast(enhancer.create());
    }

}
