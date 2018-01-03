package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

public class JewelTailPosition extends StateMachine.State {
    public JewelTailPosition(String name, JewelTailHardware  jewel_hw_, String next_, double servo_tilt_) {
        super(name);
        next = next_;
        jewel_hw = jewel_hw_;
        servo_tilt = servo_tilt_;
    }

    @Override
    public void enter() {
        // Sets servo positions
        jewel_hw.servo_tilt.setPosition(servo_tilt);
    }

    @Override
    public void exit() {
        // Does nothing.
    }

    @Override
    public String update(double secs) {
        return next;
    }

    private String next = "";
    private double servo_tilt = 0.0;
    private JewelTailHardware jewel_hw = null;
}
