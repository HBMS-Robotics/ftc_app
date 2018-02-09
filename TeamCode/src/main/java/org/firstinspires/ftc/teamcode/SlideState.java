package org.firstinspires.ftc.teamcode;


/**
 * Created by todd on 11/16/17.
 */

public class SlideState extends StateMachine.State {



    public SlideState(String name, MecanumBotHardware robot_, int distance_, String next_)
    {
        super(name);
        next = next_;
        robot = robot_;
        distance = distance_;
        start_position = 0;
        target_position = 0;
        speed = 0.1f;
    }

    @Override
    public void enter() {
        // Does nothing.
        //assume using encoder
        //set start for encoder (record position and remember)
        start_position = robot.front_right.getCurrentPosition();
        //set target position
        target_position = start_position + distance;
            //stop and next state
    }

    @Override
    public void exit()
    {
        //stop
          robot.drive(0.0, 0.0, 0.0, 0.0f);

    }
        // Does nothing.


    @Override
    public String update(double secs) {
        int currentPosition = robot.front_right.getCurrentPosition();
        opmode.telemetry.addData("position", currentPosition);
        opmode.telemetry.addData("target", target_position);
        if (Math.abs(target_position - currentPosition) > 30)
        {
            //keep driving
             if(distance < 0)
             {
                 robot.drive(0.0, -0.5, 0.0, speed);
             }
             else
             {
                 robot.drive(0.0, 0.5, 0.0, speed);
             }
            return "";
        }
        else
        {
           return next;
        }

    }

    private String next;
    private MecanumBotHardware robot;
    private int start_position;
    private int distance;
    private int target_position;
    public float speed;

}
