package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;

/**
 * Created by Claudia on 11/16/17.
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
        // TODO:
        // Look at sensor, decide to return next blue or next red state.
        return "";
    }


    protected boolean seeRed()
    {
        opmode.telemetry.addData("Clear", jt_hw.color_sensor.alpha());
        opmode.telemetry.addData("Red  ", jt_hw.color_sensor.red());
        opmode.telemetry.addData("Green", jt_hw.color_sensor.green());
        opmode.telemetry.addData("Blue ", jt_hw.color_sensor.blue());
        opmode.telemetry.update();
        if  (jt_hw.color_sensor.red() > jt_hw.color_sensor.blue())
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    private String next_b;
    private String next_r;
    private JewelTailHardware jt_hw;
}
