package com.winter.context;

import com.winter.annotation.Component;
import com.winter.context.configurator.Configurator;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ObjectFactory {

    @Getter
    private final Reflections finder;
    @Setter
    private Context context;
    private final List<Configurator> configurators;
    private final List<ProxyConfigurator> proxyConfigurators;

    public ObjectFactory(String basePackage) {
        this.finder = new Reflections(basePackage);
        configurators = finder.getSubTypesOf(Configurator.class)
                .stream()
                .filter(c -> !c.isInterface())
                .map(this::instanceFromDefaultConstructor)
                .collect(toList());
        proxyConfigurators = finder.getSubTypesOf(ProxyConfigurator.class)
                .stream()
                .filter(c -> !c.isInterface())
                .map(this::instanceFromDefaultConstructor)
                .collect(toList());
    }

    @SneakyThrows
    public <T> T createObject(Class<T> aClass) {
        T t = create(aClass);
        configure(t);
        t = proxyConfigure(t);
        return t;
    }

    private <T> T proxyConfigure(T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators) {
            t = proxyConfigurator.configure(t, context);
        }
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(c -> c.configure(t, context));
    }

    private <T> T create(Class<T> aClass) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (!aClass.isAnnotationPresent(Component.class)) {
            throw new IllegalArgumentException("Class " + aClass + " is not component");
        }
        return aClass.getConstructor().newInstance();
    }

    @SneakyThrows
    private <T> T instanceFromDefaultConstructor(Class<T> aClass) {
        return aClass.getConstructor().newInstance();
    }
}
