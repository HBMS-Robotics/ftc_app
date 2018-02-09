package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

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
    public Servo   leftClawLower = null;
    public Servo   rightClawLower = null;
    public DcMotor  shoulder = null;
    public DigitalChannel touch;
    public DigitalChannel touch2;
    public static final double MID_SERVO       =  0.5 ;
    public MecanumBotHardware(boolean _IS_USING_FOUR_MOTORS,boolean _HAS_SHOULDER, boolean _HAS_WRIST, boolean _HAS_CLAWS){
        IS_USING_FOUR_MOTORS=_IS_USING_FOUR_MOTORS;
        HAS_SHOULDER=_HAS_SHOULDER;
        HAS_WRIST=false;
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
        back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if(IS_USING_FOUR_MOTORS){
            front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }


        if(HAS_CLAWS) {
            leftClaw = hwmap.get(Servo.class, "left_hand");
            rightClaw = hwmap.get(Servo.class, "right_hand");

          // Don't move servos at start.
          //  leftClaw.setPosition(MID_SERVO);
          //  rightClaw.setPosition(MID_SERVO);

            leftClaw.setDirection(Servo.Direction.FORWARD);
            rightClaw.setDirection(Servo.Direction.REVERSE);

            leftClawLower = hwmap.get(Servo.class, "left_hand_lower");
            rightClawLower = hwmap.get(Servo.class, "right_hand_lower");

          // Don't move servos at start.
          //  leftClawLower.setPosition(MID_SERVO);
          //  rightClawLower.setPosition(MID_SERVO);

            leftClawLower.setDirection(Servo.Direction.FORWARD);
            rightClawLower.setDirection(Servo.Direction.REVERSE);
        }
        touch = hwmap.get(DigitalChannel.class,"touch");
        touch2 = hwmap.get(DigitalChannel.class,"touch2");
    }

    public void drive(double tf, double tl, double r,float speed,float clip){
        //Drives the robot forward by tf, side by tl, and rotate by r.
        //tf= tl= r=
        double fl=(tf-r)-tl;
        double fr=(tf+r)+tl;
        double bl=(tf-r)+tl;
        double br=(tf+r)-tl;
        fl*=speed;
        fr*=speed;
        bl*=speed;
        br*=speed;
        fl= Range.clip(fl,-clip,clip);
        fr= Range.clip(fr,-clip,clip);
        bl= Range.clip(bl,-clip,clip);
        br= Range.clip(br,-clip,clip);
        front_left.setPower(fl);
        front_right.setPower(fr);
        back_left.setPower(bl);
        back_right.setPower(br);
    }
    public void set(double arm_move){
        shoulder.setPower(-0.6);
        while(true) {
            if (HAS_SHOULDER) {
                if (!touch.getState()) {
                    arm_move = 0;
                    shoulder.setPower(0);
                    shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                    shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                }
            }
        }
    }
    public void drive(double tf, double tl, double r,float speed){
        //Drives the robot forward by tf, side by tl, and rotate by r.
        //tf= tl= r=
        double fl=(tf-r)-tl;
        double fr=(tf+r)+tl;
        double bl=(tf-r)+tl;
        double br=(tf+r)-tl;
        fl*=speed;
        fr*=speed;
        bl*=speed;
        br*=speed;
        front_left.setPower(fl);
        front_right.setPower(fr);
        back_left.setPower(bl);
        back_right.setPower(br);
    }
    public static float logCurve(float num){
        float sqBase=2;
        if(num==0)
            return 0;
        else
            return (float) (Math.signum(num) * Math.pow(Math.abs(num),sqBase));

    }
//    public void driveToPosition(float tf,float tl, float r){
//        //Resets encoders.
//        front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        //Sets encoders to run to position.
//        front_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        front_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        back_left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        back_right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        float fl=(tf-r)-tl;
//        float fr=(tf+r)+tl;
//        float bl=(tf-r)+tl;
//        float br=(tf+r)-tl;
//        int encoderTicks=1120;
//        fl*=encoderTicks;
//        fr*=encoderTicks;
//        bl*=encoderTicks;
//        br*=encoderTicks;
//        front_left.setTargetPosition(fl);
//        front_right.setTargetPosition(fr);
//        back_left.setTargetPosition(bl);
//        back_right.setTargetPosition(br);
//    }
}
