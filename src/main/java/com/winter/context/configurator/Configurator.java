package com.winter.context.configurator;

import com.winter.context.Context;

public interface Configurator {

    <T> void configure(T obj, Context context);
}
