package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by hbms on 10/17/17.
 */
@TeleOp(name="Gigabyte Teleop 2.0 (Groundnet)", group="Teleop")
public class GigabyteSMTeleop extends OpMode{

    /* Declare OpMode members. */
    // HardwareMap hardwareMap;
    MecanumBotHardware robot = null;
    StateMachine smDrive = null;
    StateMachine smArm = null;
    @Override
    public void init() {

        // Create the hardware instance and initialize it.
        robot = new MecanumBotHardware(true,true,true,true);
        robot.init(hardwareMap);

        // Create the state machine and configure states.
        smDrive = new StateMachine(this, 16);
        smDrive.addStartState(new WaitState("wait",0.1,"MainDriveTeleop"));
        smDrive.addState(new GigabyteTeleopDriveState("MainDriveTeleop", robot));
        smDrive.addState(new SpinPose("180DegSpin",6417,-6382,7407,-7421,robot));

        smArm = new StateMachine(this, 16);
        smArm.addStartState(new WaitState("wait",0.1,"MainArmTeleop"));
        smArm.addState(new GigabyteTeleopArmState("MainArmTeleop", robot));
        smArm.addState(new ShoulderPose("Pose1", robot,0,0.12, "MainArmTeleop"));
        smArm.addState(new ShoulderPose("Pose2", robot,1079,0.05, "MainArmTeleop"));
        smArm.addState(new ShoulderPose("Pose3", robot,2602,-0.17, "MainArmTeleop"));
        smArm.addState(new ShoulderPose("Pose4", robot,3698,-0.35, "MainArmTeleop" ));
        // Init the state machine
        smDrive.init();
        smArm.init();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        smDrive.init_loop();
        smArm.init_loop();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        smDrive.start();
        smArm.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        smDrive.loop();
        smArm.loop();
        telemetry.update();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        smDrive.stop();
        smArm.stop();
    }

}
