package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import java.lang.Math.*;
/**
 * Created by hbms on 10/17/17.
 */
@TeleOp(name="Gigabyte Shmeleop V1.7", group="Teleop")
public class GigabyteTeleop extends OpMode{

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    GigabyteHardware robot=new GigabyteHardware();
    ElapsedTime elapsed=new ElapsedTime();
    double          clawOffset  = 0.0 ;                  // Servo mid position
    final double    CLAW_SPEED  = 0.02 ;                 // sets rate to move servo
    /*
     * Code to run ONCE when the driver hits INIT
     */
    RobotState state=RobotState.DRIVE;
    double armMovementEstimate=0;
    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("stuffs","thingy-fying");
        telemetry.addLine("Robot is initializing...");
        elapsed.reset();
        elapsed.startTime();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    float gpad_x=0;
    float gpad_y=0;
    float gpad_x2=0;
    float f_right=0;
    float f_left=0;
    float b_right=0;
    float b_left=0;
    @Override
    public void loop() {
        gpad_x=0;
        gpad_y=0;
        gpad_x2=0;
        f_right=0;
        f_left=0;
        b_right=0;
        b_left=0;    // Run wheels in tank mode (note: The joystick goes negative when pushed forwards, so negate it)
        gpad_x = -gamepad1.left_stick_x;
        gpad_y = -gamepad1.left_stick_y;
        gpad_x2 = -gamepad1.right_stick_x;
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
        telemetry.addData("X", "%f", gpad_x);
        telemetry.addData("Y", "%f", gpad_y);
        if (state == RobotState.DRIVE && gamepad1.left_trigger >= 0.9) {
            state = RobotState.ENDGAME;
            telemetry.addLine("Endgame mode enabled");
        }
        if (state == RobotState.ENDGAME && gamepad1.right_trigger >= 0.9) {
            state = RobotState.DRIVE;
            telemetry.addLine("Drive mode enabled");

        }
        if (state == RobotState.DRIVE) {
            robot.back_left.setPower(Range.clip(b_left,-1,1));
            robot.back_right.setPower(Range.clip(b_right,-1,1));
        } else if (state == RobotState.ENDGAME) {
            robot.back_left.setPower(Range.clip(0.75 * b_left,-1,1));
            robot.back_right.setPower(Range.clip(0.75 * b_right,-1,1));
        }
        if (robot.IS_USING_FOUR_MOTORS) {
            if (state == RobotState.DRIVE) {
                robot.front_left.setPower(Range.clip(f_left,-1,1));
                robot.front_right.setPower(Range.clip(f_right,-1,1));
            } else if (state == RobotState.ENDGAME) {
                robot.front_left.setPower(Range.clip(0.75 * f_left,-1,1));
                robot.front_right.setPower(Range.clip(0.75 * f_right,-1,1));
            }
        }
        // Use gamepad left & right Bumpers to open and close the claw
        if (gamepad2.right_bumper)
            clawOffset += CLAW_SPEED;
        else if (gamepad2.left_bumper)
            clawOffset -= CLAW_SPEED;

        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
        robot.rightClaw.setPosition(robot.MID_SERVO - clawOffset);

        // Use gamepad buttons to move the arm up (Y) and down (A)
        if (gamepad2.y){
            robot.leftArm.setPower(robot.ARM_UP_POWER);
        }
        else if (gamepad2.a) {
            robot.leftArm.setPower(robot.ARM_DOWN_POWER);
        }
        else {
            robot.leftArm.setPower(0.0);
        }
        if(robot.limitSwitch.getState()&&gamepad2.a){
            robot.leftArm.setPower(0);
        }
        // Send telemetry message to signify robot running;
        telemetry.addData("Claw",  "Offset = %.2f", clawOffset);
        telemetry.addData("Arm Speed",robot.leftArm.getPower());
        telemetry.addData("Arm Estimate",armMovementEstimate);
        telemetry.addData("Switch",robot.limitSwitch.getState());
        telemetry.addData("Left", "%.2f",b_left);
        telemetry.addData("Right", "%.2f", b_right);
        telemetry.addData("Runtime","%f",elapsed.seconds());
        telemetry.addData("State",state);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        telemetry.clear();
        telemetry.addLine("Turning off...");
    }
}
