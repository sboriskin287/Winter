package com.winter.domain.bike.wheel;

import com.winter.annotation.Component;
import com.winter.annotation.Measure;
import com.winter.annotation.Primary;

@Component
@Primary
public class Wheel275 implements Wheel {

    @Override
    @Measure
    public void oneCycle() {
        System.out.println("Мой диаметр 27.5 дюймов, и за один оборот я проехало 2,148 метра");
    }
}
