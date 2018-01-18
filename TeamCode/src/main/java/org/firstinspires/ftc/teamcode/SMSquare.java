package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="SMSquare", group="Autonomous")
@Disabled
public class SMSquare extends OpMode{

    /* Declare OpMode members. */
    JewelTailHardware jt = null;
    MecanumBotHardware mh = null;
    StateMachine sm = null;

    @Override
    public void init() {

        // Create the hardware instance and initialize it.
        jt = new JewelTailHardware();
        mh = new MecanumBotHardware(true, true, true, true);
        jt.init(hardwareMap);
        mh.init(hardwareMap);

        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "drive"));
        sm.addState(new ForwardState("drive", mh, 4000, "rotate"));
        sm.addState(new Rotate_State("rotate", mh, 3250, "drive_2"));
        sm.addState(new ForwardState("drive_2", mh, 4000, "rotate_2"));
        sm.addState(new Rotate_State("rotate_2", mh, 3250, "drive_3"));
        sm.addState(new ForwardState("drive_3", mh, 4000, "slide"));
        sm.addState(new SlideState("slide", mh, 4000, "terminal"));
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
