package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="JewelTailSMTest", group="Autonomous")
public class JewelTailSMTest extends OpMode{

    /* Declare OpMode members. */
    JewelTailHardware robot = null;
    StateMachine sm = null;

    @Override
    public void init() {

        // Create the hardware instance and initialize it.
        robot = new JewelTailHardware();
        robot.init(hardwareMap);

        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "init_pose"));
        sm.addState(new JewelTailPosition("init_pose", robot, "wait_1", 0.6, 0.2));
        sm.addState(new WaitState("wait_1", 1.0, "move_to_sense"));
        sm.addState(new JewelTailPosition("move_to_sense", robot, "wait_2", 0.0, 0.5));
        sm.addState(new WaitState("wait_2", 2.0, "sense_jewel"));
        sm.addState(new ColorSensorState("sense_jewel", robot, "sweep_back", "sweep_forward", "go_back"));
        sm.addState(new JewelTailPosition("sweep_back", robot, "wait_3", 0.4, 0.5));
        sm.addState(new JewelTailPosition("sweep_forward", robot, "wait_3", 0.2, 0.8));
        sm.addState(new JewelTailPosition("go_back", robot, "wait_3", 0.6, 0.2));
        sm.addState(new WaitState("wait_3", 2.0, "endPose"));
        sm.addState(new JewelTailPosition("endPose", robot, "terminal", 0.0, 0.0));
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
