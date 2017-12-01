package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;

/**
 * Created by hbms on 10/17/17.
 */

public class JewelTailHardware {

    public Servo    servo_tilt = null;
    public Servo    servo_pan   = null;
    public SensorREVColorDistance color_sensor = null;

    public void init(HardwareMap hwmap) {

        // Define and initialize ALL installed servos.
        servo_pan  = hwmap.get(Servo.class, "servo_pan");
        servo_tilt = hwmap.get(Servo.class, "servo_tilt");
       // color_sensor = hwmap.get(SensorREVColorDistance.class, "color_sensor");
        servo_pan.setPosition(0.0);
        servo_tilt.setPosition(0.0);

    }
}