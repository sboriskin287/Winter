package com.winter.domain.bike.seat;

import com.winter.annotation.Component;

@Component
public class SportSeat implements Seat {
    @Override
    public void onHumanSeats() {
        System.out.println("Я спортивное седло, сча помчим");
    }
}
