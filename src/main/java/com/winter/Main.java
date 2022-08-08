package com.winter;

import com.winter.context.Application;
import com.winter.context.Context;
import com.winter.domain.bike.Bike;

public class Main {
    public static void main(String[] args) {
        Context ctx = Application.run(Main.class);
        var bike = ctx.getObject(Bike.class);
        bike.manSeat();
        bike.go();
    }
}
