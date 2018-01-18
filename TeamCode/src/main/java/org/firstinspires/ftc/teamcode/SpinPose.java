package org.firstinspires.ftc.teamcode;

/**
 * Created by hbms on 1/9/18.
 */

public class SpinPose extends StateMachine.State {
    int bl,br,fl,fr;
    MecanumBotHardware robot;
    public SpinPose(String name,int bl_,int br_,int fl_,int fr_,MecanumBotHardware robot_){
        super(name);
        bl=bl_;
        br=br_;
        fl=fl_;
        fr=fr_;
        robot=robot_;
    }
    int start,end;
    @Override
    public void enter(){
        start=robot.back_left.getCurrentPosition();
        end=start+bl;
        robot.back_left.setPower(Math.signum(bl));
        robot.back_right.setPower(-Math.signum(bl));
        robot.front_left.setPower(Math.signum(bl));
        robot.front_right.setPower(-Math.signum(bl));
    }
    @Override
    public void exit(){
    }
    @Override
    public String update(double time){
        opmode.telemetry.addData("The Stuffery","Not done yet maybe.");
        opmode.telemetry.addData("BL",bl);
        opmode.telemetry.addData("BL",start);
        opmode.telemetry.addData("BLA",robot.back_left.getCurrentPosition());
        if(Math.abs(robot.back_left.getCurrentPosition()-end)<144){
            return "MainDriveTeleop";
        }
        else{
            return "";
        }
    }
}
