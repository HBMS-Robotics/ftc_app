package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by hbms on 12/15/17.
 */

public class ShoulderPose extends StateMachine.State {
    int poseTicks;
    MecanumBotHardware robot;
    public ShoulderPose(String name, MecanumBotHardware hw,int ticks) {
        super(name);
        robot = hw; // Save the reference to the hardware robot.
        poseTicks=ticks;
    }
    @Override
    public void enter() {
        robot.shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.shoulder.setTargetPosition(poseTicks);
        opmode.telemetry.addData("STATE","POSE RUNNING INTO ENTER");

    }
    @Override
    public void exit() {
        robot.shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public String update(double secs){
        if((!robot.shoulder.isBusy())||(secs>=2000)){
            return "MainTeleop";
        }
        else {return "";}
    }

}
