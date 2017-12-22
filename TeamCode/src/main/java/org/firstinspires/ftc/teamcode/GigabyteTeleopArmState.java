package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by hbms on 10/17/17.
 */
public class GigabyteTeleopArmState extends  StateMachine.State {

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    MecanumBotHardware robot;
    double          clawOffset  = 0.0 ;                  // Servo mid position
    double          wristOffset  = 0.0 ;                  // Servo mid position
    final double    CLAW_SPEED  = 0.02 ;                 // sets rate to move servo
    double arm_move =0;
    float drive_speed=1;
    int             shoulderOffset = 0;
    public GigabyteTeleopArmState(String name, MecanumBotHardware hw) {
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
            robot.shoulder.setPower(0);
        }
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public String update(double secs) {
        if (opmode.gamepad2.right_bumper)
            clawOffset += CLAW_SPEED;
        else if (opmode.gamepad2.left_bumper)
            clawOffset -= CLAW_SPEED;
        if (opmode.gamepad1.right_bumper)
            drive_speed=Range.clip(drive_speed+0.01f,0,1);
        else if (opmode.gamepad1.left_bumper)
            drive_speed=Range.clip(drive_speed-0.01f,0,1);
        if(opmode.gamepad2.x){
            return "Pose1";
        }
        if(robot.HAS_WRIST) {
            wristOffset+=-0.1*opmode.gamepad2.right_stick_y;
            wristOffset=Range.clip(wristOffset,-0.5,0.5);
        }
        if(robot.HAS_SHOULDER) {
            arm_move = -0.4*opmode.gamepad2.left_stick_y;
            arm_move=robot.logCurve((float)arm_move);
            if(!robot.touch.getState()&&arm_move<0) {
                arm_move = 0;
                robot.shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            else {
                robot.shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.shoulder.setPower(arm_move);
        }
        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        if(robot.HAS_CLAWS) {
            robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
            robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);
        }
        if(robot.HAS_WRIST) {
            robot.wrist.setPosition(robot.MID_SERVO + wristOffset);
            robot.wrist2.setPosition(1-(robot.MID_SERVO + wristOffset));
        }
        // Use gamepad buttons to move the arm up (Y) and down (A)
        // Send telemetry message to signify robot running;
        if(robot.HAS_CLAWS) {
            opmode.telemetry.addData("Claw", "Offset = %.2f", clawOffset);
        }
        if(robot.HAS_WRIST){
            opmode.telemetry.addData("Wrist", "Offset = %.2f", wristOffset);
        }
        if(robot.HAS_CLAWS) {
            opmode.telemetry.addData("ClawActual", "Offset = %.2f", robot.leftClaw.getPosition() - 0.5);
        }
        if(robot.HAS_WRIST) {
            opmode.telemetry.addData("WristActual", "Offset = %.2f", robot.wrist.getPosition() - 0.5);
        }
        if(robot.HAS_SHOULDER) {
            opmode.telemetry.addData("Arm Position", robot.shoulder.getCurrentPosition());
        }
        opmode.telemetry.addData("Runtime","%f", secs);
        return ""; // Don't change state
    }

}
