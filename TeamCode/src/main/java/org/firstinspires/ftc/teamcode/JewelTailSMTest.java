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
        sm.addStartState(new WaitState("BriefPause", 1.0, "position_1"));
        sm.addState(new JewelTailPosition("position_1", robot, "wait_1", 0.0, 0.0));
        sm.addState(new WaitState("wait_1", 1.0, "position_2"));
        sm.addState(new JewelTailPosition("position_2", robot, "wait_2", 0.2, 0.5));
        sm.addState(new WaitState("wait_2", 2.0, "terminal"));
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
