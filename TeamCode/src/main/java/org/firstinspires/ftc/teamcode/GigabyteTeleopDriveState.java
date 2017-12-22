package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by hbms on 10/17/17.
 */
public class GigabyteTeleopDriveState extends  StateMachine.State {

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    MecanumBotHardware robot;
    double          clawOffset  = 0.0 ;                  // Servo mid position
    double          wristOffset  = 0.0 ;                  // Servo mid position
    final double    CLAW_SPEED  = 0.02 ;                 // sets rate to move servo
    double arm_move =0;
    float drive_speed=1;
    int             shoulderOffset = 0;
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
        robot.back_left.setPower(0.0);
        robot.back_right.setPower(0.0);

        if (robot.IS_USING_FOUR_MOTORS) {
            robot.front_left.setPower(0.0);
            robot.front_right.setPower(0.0);
        }
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    float logBase=(float)Math.E;
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
        robot.drive(gpad_y,gpad_x,gpad_x2,drive_speed);
//        b_left = (gpad_y - gpad_x2);
//        b_right = (gpad_y + gpad_x2);
//
//        if (robot.IS_USING_FOUR_MOTORS){
//            f_left = (gpad_y - gpad_x2)-gpad_x;
//            f_right =(gpad_y + gpad_x2)+gpad_x;
//            b_left = (gpad_y - gpad_x2)+gpad_x;
//            b_right = (gpad_y + gpad_x2)-gpad_x;
//        }
        opmode.telemetry.addData("X", "%f", gpad_x);
        opmode.telemetry.addData("Y", "%f", gpad_y);
//        robot.back_left.setPower(Range.clip(b_left,-1,1));
//        robot.back_right.setPower(Range.clip(b_right,-1,1));
//
//        if (robot.IS_USING_FOUR_MOTORS) {
//            robot.front_left.setPower(Range.clip(f_left,-1,1));
//            robot.front_right.setPower(Range.clip(f_right,-1,1));
//        }
        // Use gamepad left & right Bumpers to open and close the claw
        // Move both servos to new position.  Assume servos are mirror image of each other.
        opmode.telemetry.addData("Back left", "%.2f", (float)robot.back_left.getCurrentPosition());
        opmode.telemetry.addData("Back right", "%.2f", (float)robot.back_right.getCurrentPosition());
        opmode.telemetry.addData("Front left", "%.2f", (float)robot.front_left.getCurrentPosition());
        opmode.telemetry.addData("Front right", "%.2f", (float)robot.front_right.getCurrentPosition());
        opmode.telemetry.addData("Runtime","%f", secs);
        opmode.telemetry.addData("Drive Scale",drive_speed);
        return ""; // Don't change state
    }

}
