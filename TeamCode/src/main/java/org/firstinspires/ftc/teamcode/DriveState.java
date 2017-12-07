package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

public class DriveState extends StateMachine.State {
    public DriveState(String name, GigabyteHardware g_hw_, int distance_,String next_) {
        super(name);
        next = next_;
        g_hw = g_hw_;
        distance = distance_;
        start_position = 0;
    }

    @Override
    public void enter() {
        // Does nothing.
        //assume using encoder
        //set start for encoder (record position and remember)
        start_position = g_hw.front_right.getCurrentPosition();
        //travel until encoder ticks = 3120

        //stop and next state
    }

    @Override
    public void exit() {
        // Does nothing.
    }

    @Override
    public String update(double secs) {

        return "";

    }

    private String next;
    private GigabyteHardware g_hw;
    private int start_position;
    private int distance;
}
