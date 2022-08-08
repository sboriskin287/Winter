package com.winter.context;

import com.winter.annotation.Component;
import com.winter.annotation.Package;

import static java.util.Objects.nonNull;

public class Application {

    public static Context run(Class<?> aClass) {
        Package pack = aClass.getAnnotation(Package.class);
        String basePackage = nonNull(pack) ? pack.value() : aClass.getPackageName();
        ObjectFactory objectFactory = new ObjectFactory(basePackage);
        Context context = new Context();
        context.setObjectFactory(objectFactory);
        objectFactory.setContext(context);
        objectFactory
                .getFinder()
                .getTypesAnnotatedWith(Component.class)
                .forEach(context::getObject);
        return context;
    }
}
