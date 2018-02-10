package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="RedVuforia", group="Autonomous")
public class RedVufoia extends OpMode{

    /* Declare OpMode members. */
    MecanumBotHardware mh = null;
    StateMachine sm = null;

    @Override
    public void init() {

        // Create the hardware instance and initialize it.
        mh = new MecanumBotHardware(true, true, true, true);
        mh.init(hardwareMap);

        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "sense"));
        sm.addState(new VuforiaSense("sense", "left", "middle", "right"));
        sm.addState(new WaitState("left", 5.0, "leftdrive"));
        sm.addState(new DriveState("leftdrive", mh, 1000, "Lstrafe"));
        sm.addState(new SlideState("Lstrafe", mh, -1300, "drive"));
        sm.addState(new WaitState("right", 5.0, "rightdrive"));
        sm.addState(new DriveState("rightdrive", mh, 1000, "Rstrafe"));
        sm.addState(new SlideState("Rstrafe", mh, -500, "drive"));
        sm.addState(new WaitState("middle", 5.0, "middledrive"));
        sm.addState(new DriveState("middledrive", mh, 1000, "Mstrafe"));
        sm.addState(new SlideState("Mstrafe", mh, -900, "drive"));
        sm.addState(new DriveState("drive", mh, 750, "driveBack"));
        sm.addState(new DriveState("driveBack", mh, -125, "terminal"));
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
        mh.shoulder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
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
