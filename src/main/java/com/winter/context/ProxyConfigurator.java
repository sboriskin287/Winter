package com.winter.context;

public interface ProxyConfigurator {

    <T> T configure(T t, Context context);
}
