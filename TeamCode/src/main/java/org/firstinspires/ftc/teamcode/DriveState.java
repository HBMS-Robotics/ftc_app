package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by todd on 11/16/17.
 */

public class DriveState extends StateMachine.State {



    public DriveState(String name, MecanumBotHardware m_hw_, int distance_, String next_)
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
//        m_hw.front_right.setPower(0);
//        m_hw.front_left.setPower(0);
//        m_hw.back_left.setPower(0);
//        m_hw.back_right.setPower(0);
          m_hw.drive(0.0, 0.0, 0.0,1.0f);

    }
        // Does nothing.


    @Override
    public String update(double secs) {
        int currentPosition = m_hw.front_right.getCurrentPosition();
        opmode.telemetry.addData("position", currentPosition);
        opmode.telemetry.addData("target", target_position);
        if (Math.abs(target_position - currentPosition) > 50)
        {
            if(distance < 0)
            {
                m_hw.drive(-0.5, 0.0, 0.0, 1.0f);
            }
            else
            {
                m_hw.drive(0.5, 0.0, 0.0, 1.0f);
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
