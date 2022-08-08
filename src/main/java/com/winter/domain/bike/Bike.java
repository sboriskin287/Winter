package com.winter.domain.bike;

import com.winter.annotation.Component;
import com.winter.annotation.Inject;
import com.winter.domain.bike.seat.Seat;
import com.winter.domain.bike.wheel.Wheel;
import lombok.SneakyThrows;

import static java.lang.Thread.sleep;

@Component
public class Bike {

    @Inject
    private Wheel firstWheel;
    @Inject
    private Wheel secondWheel;
    @Inject
    private Seat seat;

    public void manSeat() {
        seat.onHumanSeats();
    }

    @SneakyThrows
    public void go() {
        for (var i = 0; i < 3; i += 1) {
            System.out.println("Первое колесо: ");
            firstWheel.oneCycle();
            System.out.println("Второе колесо: ");
            secondWheel.oneCycle();
            sleep(1000);
        }
    }
}
