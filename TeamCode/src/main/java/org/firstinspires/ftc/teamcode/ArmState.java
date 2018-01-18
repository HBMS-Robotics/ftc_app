package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

public class ArmState extends StateMachine.State {
    public ArmState(String name, String next_) {
        super(name);
        next = next_;
    //    robot = robot_;
    }

    @Override
    public void enter() {
        // Does nothing.
    }

    @Override
    public void exit() {
        // Does nothing.
    }

    @Override
    public String update(double secs) {

            return ""; // Returns empty string indicating no state change.
    }


    private String next;
    private MecanumBotHardware robot_;
}
