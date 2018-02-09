package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by hbms on 10/17/17.
 */
public class GigabyteTeleopArmState extends  StateMachine.State {

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    MecanumBotHardware robot;
    double          clawOffset  = 0.0 ;                  // Servo mid position
//    double          wristOffset  = 0.0 ;                  // Servo mid position
    final double    CLAW_SPEED  = 0.04 ;                 // sets rate to move servo
    double arm_move =0;
    double armspeed=1;
    double armscale=0.5;
    int             shoulderOffset = 0;
    public GigabyteTeleopArmState(String name, MecanumBotHardware hw) {
        super(name);
        robot = hw; // Save the reference to the hardware robot.
    }

    @Override
    public void enter() {


    }

    @Override
    public void exit() {
    }


    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public String update(double secs) {
        clawOffset=robot.leftClaw.getPosition()-0.5;
        if (opmode.gamepad2.right_bumper)
            clawOffset += CLAW_SPEED;
        else if (opmode.gamepad2.left_bumper)
            clawOffset -= CLAW_SPEED;
        if(opmode.gamepad2.dpad_down){
//            wristOffset=0.12;
            return "Pose1";
        }
        if(opmode.gamepad2.dpad_left){
//            wristOffset=0.05;
            return "Pose2";
        }
        if(opmode.gamepad2.dpad_up){
//            wristOffset=-0.17;
            return "Pose3";
        }
        if(opmode.gamepad2.dpad_right){
//            wristOffset=-0.35;
            return "Pose4";
        }
        if(opmode.gamepad2.a){
            armspeed=0.25*armscale;
        }
        if(opmode.gamepad2.x){
            armspeed=0.5*armscale;
        }
        if(opmode.gamepad2.b){
            armspeed=0.75*armscale;
        }
        if(opmode.gamepad2.y){
            armspeed=armscale;
        }


        if(robot.HAS_SHOULDER) {
            arm_move = -opmode.gamepad2.left_stick_y;
            arm_move=robot.logCurve((float)arm_move);
            if(!robot.touch.getState()&&arm_move<0) {
                arm_move = 0;
                robot.shoulder.setPower(0);
                robot.shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            else {
                robot.shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if(!robot.touch2.getState()&&arm_move>0) {
                arm_move = 0;
                robot.shoulder.setPower(0);
            }
            else {
                robot.shoulder.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.shoulder.setPower(armspeed*arm_move);

        }
        // Move both servos to new position.  Assume servos are mirror image of each other.
        clawOffset = Range.clip(clawOffset, -0.5, 0.5);
        if(robot.HAS_CLAWS) {
            robot.leftClaw.setPosition(robot.MID_SERVO + clawOffset);
            robot.rightClaw.setPosition(robot.MID_SERVO + clawOffset);
            robot.leftClawLower.setPosition(robot.MID_SERVO + clawOffset);
            robot.rightClawLower.setPosition(robot.MID_SERVO + clawOffset);
        }
//        if(robot.HAS_WRIST) {
//            if(opmode.gamepad2.a) {
//                int maxTicks=5763;
//                wristOffset=0.25-0.8*robot.shoulder.getCurrentPosition()/maxTicks;
//            }
//            robot.wrist.setPosition(robot.MID_SERVO + wristOffset);
//            robot.wrist2.setPosition(1 - (robot.MID_SERVO + wristOffset));
//        }
        // Use gamepad buttons to move the arm up (Y) and down (A)
        // Send telemetry message to signify robot running;
        if(robot.HAS_CLAWS) {
            opmode.telemetry.addData("Claw", "Offset = %.2f", clawOffset);
        }
//        if(robot.HAS_WRIST){
//            opmode.telemetry.addData("Wrist", "Offset = %.2f", wristOffset);
//        }
        if(robot.HAS_SHOULDER) {
            opmode.telemetry.addData("Arm Position", robot.shoulder.getCurrentPosition());
        }
        opmode.telemetry.addData("Limit Switch",!robot.touch.getState());
        return ""; // Don't change state
    }

}
