package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by hbms on 10/17/17.
 */

public class MecanumBotHardware {
    public final boolean IS_USING_FOUR_MOTORS=true;
    public DcMotor front_right = null;
    public DcMotor front_left = null;
    public DcMotor back_right = null;
    public DcMotor back_left = null;
    public Servo   leftClaw    = null;
    public Servo   rightClaw   = null;
    public Servo   wrist = null;
    public DcMotor  shoulder = null;
    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    public MecanumBotHardware(){
    }

    public void init(HardwareMap hwmap) {
        if(IS_USING_FOUR_MOTORS){
            front_right = hwmap.get(DcMotor.class, "front_right");
           front_left = hwmap.get(DcMotor.class, "front_left");
        }
        back_right=hwmap.get(DcMotor.class,"back_right");
        back_left=hwmap.get(DcMotor.class,"back_left");
        shoulder=hwmap.get(DcMotor.class,"shoulder");
        shoulder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_left.setDirection(DcMotor.Direction.FORWARD);
        back_right.setDirection(DcMotor.Direction.REVERSE);
        if(IS_USING_FOUR_MOTORS){
            front_left.setDirection(DcMotor.Direction.FORWARD);
            front_right.setDirection(DcMotor.Direction.REVERSE);
        }
        back_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        if(IS_USING_FOUR_MOTORS){
            front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
//        dim = hwmap.get(DeviceInterfaceModule.class, "dim");
//        limitSwitch = hwmap.get(DigitalChannel.class, "switch");
//        color_sensor = hwmap.get(ColorSensor.class, "color_sensor");

        leftClaw  = hwmap.get(Servo.class, "left_hand");
        rightClaw = hwmap.get(Servo.class, "right_hand");
        wrist = hwmap.get(Servo.class, "wrist");
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);
        wrist.setPosition(MID_SERVO);
    }
}
