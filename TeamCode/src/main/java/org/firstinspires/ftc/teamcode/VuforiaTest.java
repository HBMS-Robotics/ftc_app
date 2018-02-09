package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="VuforiaTest", group="Autonomous")
//@Disabled
public class VuforiaTest extends OpMode{

    /* Declare OpMode members. */

    StateMachine sm = null;

    @Override
    public void init() {

        // Create the hardware instance and initialize it.


        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "sense"));
        sm.addState(new VuforiaSense("sense", "L", "M", "R"));
        sm.addState(new WaitState("L", 5.0, ""));
        sm.addState(new WaitState("M", 5.0, ""));
        sm.addState(new WaitState("R", 5.0, ""));
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
