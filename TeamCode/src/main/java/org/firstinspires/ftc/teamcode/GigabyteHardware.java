package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.hardware.configuration.MotorConfiguration;
import com.qualcomm.robotcore.hardware.configuration.MotorControllerConfiguration;

/**
 * Created by hbms on 10/17/17.
 */

public class GigabyteHardware {
    public final boolean IS_USING_FOUR_MOTORS=false;
    public DcMotor front_right=null;
    public DcMotor front_left=null;
    public DcMotor back_right=null;
    public DcMotor back_left=null;
    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;
    public DcMotor  leftArm     = null;
    public Servo    leftClaw    = null;
    public Servo    rightClaw   = null;
    DigitalChannel limitSwitch;
    DeviceInterfaceModule dim;
    public GigabyteHardware(){
    }

    public void init(HardwareMap hwmap) {
        if(IS_USING_FOUR_MOTORS){
            front_right = hwmap.get(DcMotor.class, "front_right");
           front_left = hwmap.get(DcMotor.class, "front_left");
        }
        back_right=hwmap.get(DcMotor.class,"back_right");
        back_left=hwmap.get(DcMotor.class,"back_left");
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
        dim = hwmap.get(DeviceInterfaceModule.class, "dim");
        limitSwitch = hwmap.get(DigitalChannel.class, "switch");
        leftArm=hwmap.get(DcMotor.class,"left_arm");
        leftArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Define and initialize ALL installed servos.
        leftClaw  = hwmap.get(Servo.class, "left_hand");
        rightClaw = hwmap.get(Servo.class, "right_hand");
        leftClaw.setPosition(MID_SERVO);
        rightClaw.setPosition(MID_SERVO);

    }
}
