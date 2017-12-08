package org.firstinspires.ftc.teamcode;


import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;

/**
 * Created by Claudia on 11/16/17.
 */

public class ColorSensorState extends StateMachine.State {

    public ColorSensorState(String name, JewelTailHardware jewel_hw, String next_b_,
                            String next_r_, String next_what_) {
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
        boolean What = seeWhat();
        if (secs < 4) {
            return "";
        }


        if (Red) {
            return next_r;
        }


        if (Blue) {
            return next_b;
        }
        if (What)
        {
            return next_what;
        }

        return "";
    }



    protected boolean seeRed()
    {
        opmode.telemetry.addData("Clear", jt_hw.color_sensor.alpha());
        opmode.telemetry.addData("Red  ", jt_hw.color_sensor.red());
        opmode.telemetry.addData("Green", jt_hw.color_sensor.green());
        opmode.telemetry.addData("Blue ", jt_hw.color_sensor.blue());
        opmode.telemetry.update();
        if  (jt_hw.color_sensor.red() > 90 && jt_hw.color_sensor.green() < 45 &&
                jt_hw.color_sensor.blue() < 75 && jt_hw.color_sensor.alpha() > 100)
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
        if  (jt_hw.color_sensor.red() < 60 && jt_hw.color_sensor.green() < 250 &&
                jt_hw.color_sensor.blue() > 150 && jt_hw.color_sensor.alpha() > 150)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    protected boolean seeWhat() {
        opmode.telemetry.addData("Alpha", jt_hw.color_sensor.alpha());
        opmode.telemetry.addData("Red  ", jt_hw.color_sensor.red());
        opmode.telemetry.addData("Green", jt_hw.color_sensor.green());
        opmode.telemetry.addData("Blue ", jt_hw.color_sensor.blue());
        opmode.telemetry.update();
        if (jt_hw.color_sensor.red() < 20 && jt_hw.color_sensor.green() < 20 &&
                jt_hw.color_sensor.blue() < 15 && jt_hw.color_sensor.alpha() < 45)
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
