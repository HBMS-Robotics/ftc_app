package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="BlueGlyph", group="Autonomous")
public class BlueGlyph extends OpMode{

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
        sm.addStartState(new WaitState("BriefPause", 1.0, "arm"));
        sm.addState(new ShoulderPose("arm", mh,4650,0.4, "drive"));
        sm.addState(new DriveState("drive", mh, 3575, "strafe"));
        sm.addState(new SlideState("strafe", mh, -2000, "drive_2"));
        sm.addState(new DriveState("drive_2", mh, 3500, "driveBack"));
        sm.addState(new DriveState("driveBack", mh, -500, "terminal"));
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
