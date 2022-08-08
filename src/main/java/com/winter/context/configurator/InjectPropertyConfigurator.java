package com.winter.context.configurator;

import com.winter.annotation.Component;
import com.winter.annotation.Property;
import com.winter.context.Context;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class InjectPropertyConfigurator implements Configurator {
    Map<String, String> propMap;

    public InjectPropertyConfigurator() {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        this.getClass().getClassLoader().getResourceAsStream("application.properties"), StandardCharsets.UTF_8));
        propMap = br
                .lines()
                .map(l -> l.split("="))
                .collect(toMap(a -> a[0], a -> a[1]));
    }

    @Override
    public <T> void configure(T obj, Context context) {
        Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Property.class))
                .peek(f -> f.setAccessible(true))
                .forEach(f -> setField(f, obj));
    }

    private String getPropVal(Field f) {
        Property annotation = f.getAnnotation(Property.class);
        String key = annotation.key();
        return key.isEmpty() ? propMap.get(f.getName()) : propMap.get(key);
    }

    @SneakyThrows
    private void setField(Field f, Object obj) {
        f.set(obj, getPropVal(f));
    }
}
