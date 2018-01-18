package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by hbms on 10/17/17.
 */
@Autonomous(name="JewelTailSMTest", group="Autonomous")
@Disabled
public class RedJewel extends OpMode{

    /* Declare OpMode members. */
    JewelTailHardware jt = null;
    MecanumBotHardware mhw = null;
    StateMachine sm = null;

    @Override
    public void init() {


        // TODO: we must realize that there is no sweep now, there is only drive to knock off jewel
        // TODO: and then go forward or drive forward and knock off jewel while doing so.
        // TODO: Checkpoint Edit.


        // Create the hardware instance and initialize it.
        jt = new JewelTailHardware();
        jt.init(hardwareMap);
        mhw.init(hardwareMap);

        // Create the state machine and configure states.
        sm = new StateMachine(this, 16);
        sm.addStartState(new WaitState("BriefPause", 1.0, "tilt"));
        sm.addState(new JewelTailPosition("tilt", jt, "sense", -0.8));
        sm.addState(new ColorSensorState("sense", jt, "next_b", "next_r", "next_else"));
        sm.addState(new DriveState("next_b", mhw, 3575, "jewel_up_1"));
        sm.addState(new JewelTailPosition("jewel_up_1", jt, "strafe_1", 0.8));
        sm.addState(new SlideState("strafe_1", mhw, 2000, "drive_1"));
        sm.addState(new DriveState("drive_1", mhw, 3500, "driveBack1"));
        sm.addState(new DriveState("driveBack1", mhw, -500, "terminal"));
        sm.addState(new DriveState("next_r", mhw, -500, "jewel_up_2"));
        sm.addState(new JewelTailPosition("jewel_up_2", jt, "drive_2", 0.8));
        sm.addState(new DriveState("drive_2", mhw, 4075, "strafe_2"));
        sm.addState(new SlideState("strafe_2", mhw, 2000, "drive_3"));
        sm.addState(new DriveState("drive_3", mhw, 3500, "driveBack2"));
        sm.addState(new DriveState("driveBack2", mhw, -500, "terminal"));
        sm.addState(new JewelTailPosition("next_else", jt, "drive_4", 0.8));
        sm.addState(new DriveState("drive_4", mhw, 3575, "strafe_3"));
        sm.addState(new SlideState("strafe_3", mhw, 2000, "drive_5"));
        sm.addState(new DriveState("drive_5", mhw, 3500, "driveBack"));
        sm.addState(new DriveState("driveBack", mhw, -500, "terminal"));
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
