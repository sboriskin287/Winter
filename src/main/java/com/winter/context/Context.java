package com.winter.context;

import com.winter.annotation.Component;
import com.winter.annotation.Primary;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

public class Context {
    @Setter
    private ObjectFactory objectFactory;
    private final Map<Class<?>, Object> cache = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> tClass) {
        if (cache.containsKey(tClass)) {
            return (T) cache.get(tClass);
        }
        Class<? extends T> impClass = tClass;
        if (!tClass.isAnnotationPresent(Component.class)) {
            var candidates = objectFactory.getFinder().getSubTypesOf(tClass)
                    .stream()
                    .filter(c -> c.isAnnotationPresent(Component.class))
                    .collect(toList());
            if (candidates.size() > 1) {
                candidates.removeIf(c -> !c.isAnnotationPresent(Primary.class));
            }
            if (candidates.size() != 1) {
                throw new IllegalStateException("Number of components " + tClass + " is not 1");
            }
            impClass = candidates.iterator().next();
        }
        T t = objectFactory.createObject(impClass);
        cache.put(tClass, t);
        return t;
    }
}
