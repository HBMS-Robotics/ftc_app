package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="JewelTailSMTest", group="Autonomous")
@Disabled
public class ColorSensorTestSM extends OpMode{

    /* Declare OpMode members. */
//    JewelTailHardware jt = null;
    StateMachine sm = null;

    @Override
    public void init() {


        // TODO: we must realize that there is no sweep now, there is only drive to knock off jewel
        // TODO: and then go forward or drive forward and knock off jewel while doing so.



        // Create the hardware instance and initialize it.
       // jt = new JewelTailHardware();
       // jt.init(hardwareMap);

        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "sense"));

       // sm.addState(new ColorSensorState("sense", jt, "next_b", "next_r", "nest_else"));
       // sm.addState(new JewelTailPosition("next_b", jt, "terminal", 0.8));
       // sm.addState(new JewelTailPosition("next_r", jt, "terminal", -0.8));
       // sm.addState(new JewelTailPosition("next_else", jt, "terminal", 0.0));
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
