package com.winter.domain.bike.seat;

import com.winter.annotation.Component;
import com.winter.annotation.Primary;
import com.winter.annotation.Property;

@Component
@Primary
public class PleasureSeat implements Seat {
    @Property
    private String message;

    @Override
    public void onHumanSeats() {
        System.out.println(message);
    }
}
