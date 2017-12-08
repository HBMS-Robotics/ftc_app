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
    public boolean HAS_CLAWS;
    public boolean IS_USING_FOUR_MOTORS;
    public boolean HAS_WRIST;
    public boolean HAS_SHOULDER;
    public int shoulderSpeed = 100;
    public DcMotor front_right = null;
    public DcMotor front_left = null;
    public DcMotor back_right = null;
    public DcMotor back_left = null;
    public Servo   leftClaw = null;
    public Servo   rightClaw = null;
    public Servo   wrist = null;
    public Servo   wrist2 = null;
    public DcMotor  shoulder = null;
    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.55 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    public MecanumBotHardware(boolean _IS_USING_FOUR_MOTORS,boolean _HAS_SHOULDER, boolean _HAS_WRIST, boolean _HAS_CLAWS){
        IS_USING_FOUR_MOTORS=_IS_USING_FOUR_MOTORS;
        HAS_SHOULDER=_HAS_SHOULDER;
        HAS_WRIST=_HAS_WRIST;
        HAS_CLAWS=_HAS_CLAWS;
    }

    public void init(HardwareMap hwmap) {
        if(IS_USING_FOUR_MOTORS){
            front_right = hwmap.get(DcMotor.class, "front_right");
           front_left = hwmap.get(DcMotor.class, "front_left");
        }
        back_right=hwmap.get(DcMotor.class,"back_right");
        back_left=hwmap.get(DcMotor.class,"back_left");
        if(HAS_SHOULDER) {
            shoulder = hwmap.get(DcMotor.class, "shoulder");
            shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            shoulder.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
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
        if(HAS_CLAWS) {
            leftClaw = hwmap.get(Servo.class, "left_hand");
            rightClaw = hwmap.get(Servo.class, "right_hand");
        }
        if(HAS_WRIST) {
            wrist = hwmap.get(Servo.class, "wrist");
            wrist.setPosition(MID_SERVO);
            wrist2 = hwmap.get(Servo.class, "wrist2");
            wrist2.setPosition(MID_SERVO);
        }
        if(HAS_CLAWS) {
            leftClaw.setPosition(MID_SERVO);
            rightClaw.setPosition(MID_SERVO);
        }
    }
}
