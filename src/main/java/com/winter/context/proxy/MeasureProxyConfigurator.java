package com.winter.context.proxy;

import com.winter.annotation.Component;
import com.winter.annotation.Measure;
import com.winter.context.Context;
import com.winter.context.ProxyConfigurator;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.time.LocalDateTime;
import java.util.Arrays;

import static java.util.stream.Collectors.toList;

@Component
public class MeasureProxyConfigurator implements ProxyConfigurator {
    @Override
    public Object configure(Object t, Context context) {
        Class<?> aClass = t.getClass();
        if (Arrays
                .stream(aClass.getDeclaredMethods())
                .noneMatch(m -> m.isAnnotationPresent(Measure.class))) {
            return t;
        }
        var methods = Arrays
                .stream(aClass.getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Measure.class))
                .map(Method::getName)
                .collect(toList());
        return Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), (proxy, method, args) -> {
            if (!methods.contains(method.getName())) {
                return method.invoke(t, args);
            }
            System.out.println(LocalDateTime.now());
            var ret = method.invoke(t, args);
            System.out.println(LocalDateTime.now());
            return ret;
        });
    }
}
