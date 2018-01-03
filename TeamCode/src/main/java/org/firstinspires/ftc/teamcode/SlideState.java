package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

public class SlideState extends StateMachine.State {



    public SlideState(String name, MecanumBotHardware m_hw_, int distance_, String next_)
    {
        super(name);
        next = next_;
        m_hw = m_hw_;
        distance = distance_;
        start_position = 0;
        target_position = 0;
    }

    @Override
    public void enter() {
        // Does nothing.
        //assume using encoder
        //set start for encoder (record position and remember)
        start_position = m_hw.front_right.getCurrentPosition();
        //set target position
        target_position = start_position + distance;
            //stop and next state
    }

    @Override
    public void exit()
    {
        //stop
          m_hw.drive(0.0, 0.0, 0.0);

    }
        // Does nothing.


    @Override
    public String update(double secs) {
        int currentPosition = m_hw.front_right.getCurrentPosition();
        opmode.telemetry.addData("position", currentPosition);
        opmode.telemetry.addData("target", target_position);
        if (Math.abs(target_position - currentPosition) > 30)
        {
            //keep driving
             if(distance < 0)
             {
                 m_hw.drive(0.0, -0.5, 0.0);
             }
             else
             {
                 m_hw.drive(0.0, 0.5, 0.0);
             }
            return "";
        }
        else
        {
           return next;
        }

    }

    private String next;
    private MecanumBotHardware m_hw;
    private int start_position;
    private int distance;
    private int target_position;

}
