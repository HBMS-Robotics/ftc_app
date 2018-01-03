package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorREVColorDistance;

/**
 * Created by hbms on 10/17/17.
 */

public class JewelTailHardware {

    public Servo    servo_tilt = null;
    public ColorSensor color_sensor = null;
    public DistanceSensor distance_sensor = null;
    private DcMotor front_right = null;
    private DcMotor front_left = null;
    private DcMotor back_right = null;
    private DcMotor back_left = null;

    public void init(HardwareMap hwmap) {

        // Define and initialize ALL installed servos
        servo_tilt = hwmap.get(Servo.class, "servo_tilt");
        front_right = hwmap.get(DcMotor.class, "front_right");
        front_left = hwmap.get(DcMotor.class, "front_left");
        back_left = hwmap.get(DcMotor.class, "back_left");
        back_right = hwmap.get(DcMotor.class, "back_right");
        color_sensor = hwmap.get(ColorSensor.class, "color_sensor");
        distance_sensor = hwmap.get(DistanceSensor.class, "color_sensor");

        //servo_pan.setPosition(0.0);
      //  servo_tilt.setPosition(0.0);

    }
}