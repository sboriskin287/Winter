package com.winter.context.configurator;

import com.winter.annotation.Component;
import com.winter.annotation.Inject;
import com.winter.context.Context;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Arrays;

@Component
public class InjectFieldConfigurator implements Configurator {

    @Override
    public <T> void configure(T obj, Context context) {
        Arrays
                .stream(obj.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Inject.class))
                .peek(f -> f.setAccessible(true))
                .forEach(f -> setField(f, obj, context.getObject(f.getType())));
    }

    @SneakyThrows
    private void setField(Field f, Object obj, Object val) {
        f.set(obj, val);
    }
}
