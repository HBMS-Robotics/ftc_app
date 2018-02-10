package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by hbms on 10/17/17.
 */
public class GigabyteTeleopDriveState extends  StateMachine.State {

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    MecanumBotHardware robot;
    float scale=0.25f;
    float drive_speed=scale*0.5f;
    float clip=0.5f;
    public GigabyteTeleopDriveState(String name, MecanumBotHardware hw) {
        super(name);
        robot = hw; // Save the reference to the hardware robot.
    }

    @Override
    public void enter() {
        // Does nothing.
    }

    @Override
    public void exit() {
        // Stops commands when you leave the mode:
//        robot.back_left.setPower(0.0);
//        robot.back_right.setPower(0.0);

//        if (robot.IS_USING_FOUR_MOTORS) {
//            robot.front_left.setPower(0.0);
//            robot.front_right.setPower(0.0);
//        }
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    float gpad_x = 0;
    float gpad_y = 0;
    float gpad_x2 = 0;
    float f_right = 0;
    float f_left = 0;
    float b_right = 0;
    float b_left = 0;
    @Override
    public String update(double secs) {
        gpad_x = 0;
        gpad_y = 0;
        gpad_x2 = 0;
        f_right = 0;
        f_left = 0;
        b_right = 0;
        b_left = 0;
        gpad_x = -opmode.gamepad1.left_stick_x;
        gpad_y = -opmode.gamepad1.left_stick_y;
        gpad_x2 = -opmode.gamepad1.right_stick_x;
        gpad_x= robot.logCurve(gpad_x);
        gpad_y= robot.logCurve(gpad_y);
        gpad_x2= robot.logCurve(gpad_x2);
        gpad_x*=-1;
        robot.drive(gpad_y,gpad_x,gpad_x2,drive_speed,clip);
//        b_left = (gpad_y - gpad_x2);
//        b_right = (gpad_y + gpad_x2);
//
//        if (robot.IS_USING_FOUR_MOTORS){
//            f_left = (gpad_y - gpad_x2)-gpad_x;
//            f_right =(gpad_y + gpad_x2)+gpad_x;
//            b_left = (gpad_y - gpad_x2)+gpad_x;
//            b_right = (gpad_y + gpad_x2)-gpad_x;
//        }
//        if(!left_press_previous&&left_press_current)
//            drive_speed=Range.clip(drive_speed+0.125f,0,0.5f);
//        if(!right_press_previous&&right_press_current)
//            drive_speed=Range.clip(drive_speed-0.125f,0,0.5f);
        if(opmode.gamepad1.a){
            drive_speed=0.25f*scale;
        }
        else if(opmode.gamepad1.x){
            drive_speed=0.5f*scale;
        }
        else if(opmode.gamepad1.b){
            drive_speed=0.75f*scale;
        }
        else if(opmode.gamepad1.y){
            drive_speed=scale;
        }
        drive_speed=Range.clip(drive_speed,0,1);
        if(opmode.gamepad1.dpad_down){
            return "180DegSpin";
        }
        if(opmode.gamepad1.dpad_right){
            return "270DegSpin";
        }
        if(opmode.gamepad1.dpad_left){
            return "90DegSpin";
        }
        opmode.telemetry.addData("X", "%f", gpad_x);
        opmode.telemetry.addData("Y", "%f", gpad_y);
        if(opmode.gamepad1.left_trigger>=0.8){
            robot.front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        else {
            if (robot.front_left.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE ||
                    robot.front_right.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE ||
                    robot.back_left.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE ||
                    robot.back_right.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE) {
                robot.front_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.front_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.back_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                robot.back_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            }
        }
//        robot.back_left.setPower(Range.clip(b_left,-1,1));
//        robot.back_right.setPower(Range.clip(b_right,-1,1));
//
//        if (robot.IS_USING_FOUR_MOTORS) {
//            robot.front_left.setPower(Range.clip(f_left,-1,1));
//            robot.front_right.setPower(Range.clip(f_right,-1,1));
//        }
        // Use gamepad left & right Bumpers to open and close the claw
        opmode.telemetry.addData("Back left", "%.2f", (float)robot.back_left.getCurrentPosition());
        opmode.telemetry.addData("Back right", "%.2f", (float)robot.back_right.getCurrentPosition());
        opmode.telemetry.addData("Front left", "%.2f", (float)robot.front_left.getCurrentPosition());
        opmode.telemetry.addData("Front right", "%.2f", (float)robot.front_right.getCurrentPosition());
        opmode.telemetry.addData("Runtime","%f", secs);
        opmode.telemetry.addData("Drive Scale",drive_speed);
        return ""; // Don't change state
    }

}
