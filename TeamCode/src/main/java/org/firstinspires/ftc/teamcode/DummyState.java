package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

/*
    This class is a dummy state that prints messages and moves on.
 */
public class DummyState extends StateMachine.State {
    public DummyState(String name, String next_) {
        super(name);
        next = next_;
    }

    @Override
    public void enter() {
        if (opmode != null)
            opmode.telemetry.addData("StateMachine:", "Dummy %s Enter", name());
    }

    @Override
    public void exit() {
        if (opmode != null)
            opmode.telemetry.addData("StateMachine:", "Dummy %s Exit", name());
    }

    @Override
    public String update(double secs) {
        return next; // Returns empty string indicating no state change.
    }

    private String next;
}
