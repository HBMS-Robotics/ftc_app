package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

public class WaitState extends StateMachine.State {
    public WaitState(String name, double delay_, String next_) {
        super(name);
        delay = delay_;
        next = next_;
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
        // Returns next state if we've been here longer than the delay.
        if (secs >= delay) {
           return next;
        }
        else
            return ""; // Returns empty string indicating no state change.
    }

    private double delay;
    private String next;
}
