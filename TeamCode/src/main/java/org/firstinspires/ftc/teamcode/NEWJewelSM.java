package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="NEWJewelSM", group="Autonomous")
@Disabled
public class NEWJewelSM extends OpMode{

    /* Declare OpMode members. */
    JewelTailHardware robot = null;
    StateMachine sm = null;

    @Override
    public void init() {


        // TODO: we must realize that there is no sweep now, there is only drive to knock off jewel
        // TODO: and then go forward or drive forward and knock off jewel while doing so.



        // Create the hardware instance and initialize it.
        robot = new JewelTailHardware();
        robot.init(hardwareMap);

        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "init_pose"));
        sm.addState(new JewelTailPosition("init_pose", robot, "wait_1", 0.0));
        sm.addState(new WaitState("wait_1", 1.0, "move_to_sense"));
        sm.addState(new JewelTailPosition("move_to_sense", robot, "wait_2", 0.0));
        sm.addState(new WaitState("wait_2", 2.0, "sense_jewel"));
        sm.addState(new ColorSensorState("sense_jewel", robot, "tilt_blue", "tilt_red", "do_neither"));
        sm.addState(new JewelTailPosition("tilt_blue", robot, "drive_back", 0.0));
        sm.addState(new JewelTailPosition("tilt_red", robot, "drive_forward", 0.0));
        sm.addState(new JewelTailPosition("do_neither", robot, "go_back", 0.0));
        sm.addState(new JewelTailPosition("go_back", robot, "drive_forward", 0.0));
        sm.addState(new WaitState("wait_3", 2.0, "endPose"));
        sm.addState(new JewelTailPosition("endPose", robot, "terminal", 0.0));
        sm.addState(new TerminalState("terminal"));
        // Init the state machine
        sm.init();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        sm.init_loop();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        sm.start();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        sm.loop();
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        sm.stop();
    }

}
