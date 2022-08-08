package com.winter.domain.bike.wheel;

import com.winter.annotation.Component;

@Component
public class Wheel290 implements Wheel {
    @Override
    public void oneCycle() {
        System.out.println("Мой диаметр 29 дюймов, и за один оборот я проехало 2,298 метра");
    }
}
