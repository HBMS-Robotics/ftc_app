package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;

/**
 * Created by Claudia on 11/16/17.
 */

public class ColorSensorState extends StateMachine.State {

    public ColorSensorState(String name, JewelTailHardware jewel_hw, String next_b_, String next_r_, String next_what_) {
        super(name);
        jt_hw = jewel_hw;
        next_b = next_b_;
        next_r = next_r_;
        next_what = next_what_;
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
        boolean Red = seeRed();
        boolean Blue = seeBlue();
        if (secs < 2.0)
        {
            return "";
        }
        if (Red)
        {
            return next_r;
        }
        if (Blue)
        {
            return next_b;
        }
        else
        {
            return next_what;
        }

    }


    protected boolean seeRed()
    {
        opmode.telemetry.addData("Clear", jt_hw.color_sensor.alpha());
        opmode.telemetry.addData("Red  ", jt_hw.color_sensor.red());
        opmode.telemetry.addData("Green", jt_hw.color_sensor.green());
        opmode.telemetry.addData("Blue ", jt_hw.color_sensor.blue());
        opmode.telemetry.update();
        if  (jt_hw.color_sensor.red() > 75 && jt_hw.color_sensor.green() < 70 && jt_hw.color_sensor.blue() < 85 && jt_hw.color_sensor.alpha() > 200);
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    protected boolean seeBlue()
    {
        opmode.telemetry.addData("Alpha", jt_hw.color_sensor.alpha());
        opmode.telemetry.addData("Red  ", jt_hw.color_sensor.red());
        opmode.telemetry.addData("Green", jt_hw.color_sensor.green());
        opmode.telemetry.addData("Blue ", jt_hw.color_sensor.blue());
        opmode.telemetry.update();
        if  (jt_hw.color_sensor.red() < 50 && jt_hw.color_sensor.green() < 175 && jt_hw.color_sensor.blue() > 175 && jt_hw.color_sensor.alpha() > 200);
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
    private String next_what;
    private JewelTailHardware jt_hw;
}
