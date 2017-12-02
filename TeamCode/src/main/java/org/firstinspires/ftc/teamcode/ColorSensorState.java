package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;

/**
 * Created by todd on 11/16/17.
 */

public class ColorSensorState extends StateMachine.State {

    public ColorSensorState(String name, JewelTailHardware jewel_hw, String next_b_, String next_r_) {
        super(name);
        jt_hw = jewel_hw;
        next_b = next_b_;
        next_r = next_r_;
    }

    @Override
    public void enter() {
        // Does nothing.


    }

    @Override
    public void exit() {
        // Does nothing.
    }

    @Override
    public String update(double secs) {
        return "";
    }

    private String next_b;
    private String next_r;
    private JewelTailHardware jt_hw;
}
