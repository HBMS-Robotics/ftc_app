package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;
import java.lang.Math.*;

@TeleOp(name="Gigabyte Shmeleop V2.1", group="Teleop")
public class GigabyteTeleop extends OpMode{

    MecanumBotHardware robot=new MecanumBotHardware();
    ElapsedTime elapsed=new ElapsedTime();
    double          clawOffset  = 0.0 ;
    final double    CLAW_SPEED  = 0.02 ;
    RobotState state=RobotState.DRIVE;
    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("stuffs","thingy-fying");
        telemetry.addLine("Robot is initializing...");
        telemetry.addData("Important Message", "!EL MORSA SER MUERTAS MUCHOSESi");
        elapsed.reset();
        elapsed.startTime();
    }

    float gpad_x=0;
    float gpad_y=0;
    float gpad_x2=0;
    float f_right=0;
    float f_left=0;
    float b_right=0;
    float b_left=0;
    @Override
    public void loop() {
//        telemetry.addData("Red",robot.color_sensor.red());
//        telemetry.addData("Blue",robot.color_sensor.blue());
        gpad_x=0;
        gpad_y=0;
        gpad_x2=0;
        f_right=0;
        f_left=0;
        b_right=0;
        b_left=0;
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
        f_left= (float) ((float)(Math.signum(f_left))*Math.log((logBase-1)*Math.abs(f_left)+1)/Math.log(logBase));
        f_right= (float) ((float)(Math.signum(f_right))*Math.log((logBase-1)*Math.abs(f_right)+1)/Math.log(logBase));
        if(robot.IS_USING_FOUR_MOTORS){
            f_left-=gpad_x2;
            f_right+=gpad_x2;
            b_left+=gpad_x2;
            b_right-=gpad_x2;
        }
        telemetry.addData("X", "%f", gpad_x);
        telemetry.addData("Y", "%f", gpad_y);

        if (state == RobotState.DRIVE) {
            robot.back_left.setPower(b_left);
            robot.back_right.setPower(b_right);
        } else if (state == RobotState.ENDGAME) {
            robot.back_left.setPower(b_left*0.75);
            robot.back_right.setPower(b_right*0.75);
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
        // Use gamepad buttons to move the arm up (Y) and down (A)
        if (gamepad2.y){
        }
//        else if (gamepad2.a&&!robot.limitSwitch.getState()) {
//        }
        else {
        }
        // Send telemetry message to signify robot running;
  //      telemetry.addData("Switch",robot.limitSwitch.getState());
  //      telemetry.addData("Switch Need",robot.limitSwitch.getState()==true&&gamepad2.a);
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