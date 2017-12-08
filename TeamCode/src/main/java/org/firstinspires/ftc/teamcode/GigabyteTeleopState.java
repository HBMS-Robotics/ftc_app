package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by hbms on 10/17/17.
 */
public class GigabyteTeleopState extends  StateMachine.State {

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    MecanumBotHardware robot;
    double          clawOffset  = 0.0 ;                  // Servo mid position
    double          wristOffset  = 0.0 ;                  // Servo mid position
    final double    CLAW_SPEED  = 0.02 ;                 // sets rate to move servo
    double arm_move =0;
    int             shoulderOffset = 0;
    public GigabyteTeleopState(String name, MecanumBotHardware hw) {
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
    @Override
    public String update(double secs) {
        float gpad_x = 0;
        float gpad_y = 0;
        float gpad_x2 = 0;
        float f_right = 0;
        float f_left = 0;
        float b_right = 0;
        float b_left = 0;
        gpad_x = -opmode.gamepad1.left_stick_x;
        gpad_y = -opmode.gamepad1.left_stick_y;
        gpad_x2 = -opmode.gamepad1.right_stick_x;
        gpad_x= (float) ((float)(Math.signum(gpad_x))*Math.log((logBase-1)*Math.abs(gpad_x)+1)/Math.log(logBase));
        gpad_y= (float) ((float)(Math.signum(gpad_y))*Math.log((logBase-1)*Math.abs(gpad_y)+1)/Math.log(logBase));
        gpad_x2= (float) ((float)(Math.signum(gpad_x2))*Math.log((logBase-1)*Math.abs(gpad_x2)+1)/Math.log(logBase));
        f_right =(gpad_y + gpad_x2);
        b_left = (gpad_y - gpad_x2);
        b_right = (gpad_y + gpad_x2);

        if (robot.IS_USING_FOUR_MOTORS){
            f_left = (gpad_y - gpad_x2)-gpad_x;
            f_right =(gpad_y + gpad_x2)+gpad_x;
            b_left = (gpad_y - gpad_x2)+gpad_x;
            b_right = (gpad_y + gpad_x2)-gpad_x;
        }
        opmode.telemetry.addData("X", "%f", gpad_x);
        opmode.telemetry.addData("Y", "%f", gpad_y);
        robot.back_left.setPower(Range.clip(b_left,-1,1));
        robot.back_right.setPower(Range.clip(b_right,-1,1));

        if (robot.IS_USING_FOUR_MOTORS) {
            robot.front_left.setPower(Range.clip(f_left,-1,1));
            robot.front_right.setPower(Range.clip(f_right,-1,1));
        }
        // Use gamepad left & right Bumpers to open and close the claw
        if (opmode.gamepad2.right_bumper)
            clawOffset += CLAW_SPEED;
        else if (opmode.gamepad2.left_bumper)
            clawOffset -= CLAW_SPEED;
        if(robot.HAS_WRIST) {
            wristOffset=-0.5*opmode.gamepad2.right_stick_y;
        }
        if(robot.HAS_SHOULDER) {
            arm_move = -0.4*opmode.gamepad2.left_stick_y;
        }
        if(robot.HAS_SHOULDER){
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
        opmode.telemetry.addData("Back left", "%.2f", b_left);
        opmode.telemetry.addData("Back right", "%.2f", b_right);
        opmode.telemetry.addData("Front left", "%.2f", b_left);
        opmode.telemetry.addData("Front right", "%.2f", b_right);
        opmode.telemetry.addData("Runtime","%f", secs);
        return ""; // Don't change state
    }


}
