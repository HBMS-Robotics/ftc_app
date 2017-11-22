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
    GigabyteHardware robot;
    double          clawOffset  = 0.0 ;                  // Servo mid position
    final double    CLAW_SPEED  = 0.02 ;                 // sets rate to move servo

    public GigabyteTeleopState(String name, GigabyteHardware hw) {
        super(name);
        robot = hw;
    }

    @Override
    public void enter() {
        // Does nothing.
    }

    @Override
    public void exit() {
        // Does nothing.
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public String update(double secs) {
        float gpad_x=0;
        float gpad_y=0;
        float gpad_x2=0;
        float f_right=0;
        float f_left=0;
        float b_right=0;
        float b_left=0;    // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        gpad_x = -opmode.gamepad1.left_stick_x;
        gpad_y = -opmode.gamepad1.left_stick_y;
        gpad_x2 = -opmode.gamepad1.right_stick_x;
        f_left = (gpad_y - gpad_x);
        f_right =(gpad_y + gpad_x);
        b_left = (gpad_y - gpad_x);
        b_right = (gpad_y + gpad_x);
        float logBase=(float)Math.E;
        b_left= (float) ((float)(Math.signum(b_left))*Math.log((logBase-1)*Math.abs(b_left)+1)/Math.log(logBase));
        b_right= (float) ((float)(Math.signum(b_right))*Math.log((logBase-1)*Math.abs(b_right)+1)/Math.log(logBase));
        if(robot.IS_USING_FOUR_MOTORS){
            f_left-=gpad_x2;
            f_right+=gpad_x2;
            b_left+=gpad_x2;
            b_right-=gpad_x2;
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

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
        robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

        // Use gamepad buttons to move the arm up (Y) and down (A)
        if (opmode.gamepad2.y){
            robot.leftArm.setPower(robot.ARM_UP_POWER);
        }
        else if (opmode.gamepad2.a) {
            robot.leftArm.setPower(robot.ARM_DOWN_POWER);
        }
        else {
            robot.leftArm.setPower(0.0);
        }
        if(robot.limitSwitch.getState()&&opmode.gamepad2.a){
            robot.leftArm.setPower(0);
        }
        // Send telemetry message to signify robot running;
        opmode.telemetry.addData("Claw",  "Offset = %.2f", clawOffset);
        opmode.telemetry.addData("Arm Speed",robot.leftArm.getPower());
        opmode.telemetry.addData("Switch",robot.limitSwitch.getState());
        opmode.telemetry.addData("Left", "%.2f",b_left);
        opmode.telemetry.addData("Right", "%.2f", b_right);
        opmode.telemetry.addData("Runtime","%f", secs);

        return "";
    }


}