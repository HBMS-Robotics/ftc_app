package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by hbms on 12/15/17.
 */

public class ShoulderPose extends StateMachine.State {
    int poseTicks;
    double wristMove;
    MecanumBotHardware robot;
    public ShoulderPose(String name, MecanumBotHardware hw, int ticks,  String next_) {
        super(name);
        robot = hw; // Save the reference to the hardware robot.
        poseTicks=ticks;
        next = next_;
    }
    @Override
    public void enter() {
        robot.shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.shoulder.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.shoulder.setPower(0.6);
        robot.shoulder.setTargetPosition(poseTicks);
//        robot.wrist.setPosition(wristMove+0.5);
//        robot.wrist2.setPosition(0.5-wristMove);
    }
    @Override
    public void exit() {

        robot.shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public String update(double secs){
//        opmode.telemetry.addData("Test","Working");
//        opmode.telemetry.addData("Encoder",robot.shoulder.getCurrentPosition());
//        opmode.telemetry.addData("Target",robot.shoulder.getTargetPosition());
//        opmode.telemetry.addData("Time",secs);
//        opmode.telemetry.addData("Busy",robot.shoulder.isBusy());
        if((!robot.shoulder.isBusy())||((secs>=10.0)&&!robot.touch.getState())){
            robot.shoulder.setPower(0);
            return next;
        }
        else {return "";}

    }

    private String next;
    private int ticks_;
    private double wrist_;

}
