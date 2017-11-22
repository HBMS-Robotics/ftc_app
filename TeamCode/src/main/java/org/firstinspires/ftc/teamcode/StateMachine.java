package org.firstinspires.ftc.teamcode;

/**
 * Created by todd on 11/16/17.
 */
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
This defines the state machine class, which allows you to create and add a collection of states
and manage the transitions between them.

This class is designed to be used with an Iterative OpMode and provide a way to arbitrarily
extend the states in the "main" loop while maintaining the key states of init, start and
stop that are part of the Iterative OpMode design.

To use this class you create an instance of StateMachine in your OpMode and then
create as many different StateMachine.State derived classes as you need. Then
create instances of your states and add them to the machine in the init method
of your OpMode.

Your OpMode must then call the corresponding StateMachine methods (init, init_loop, start,
loop and stop) in its OpMode methods in order for the StateMachine to do its job.

*/
public class StateMachine {

    /*
    The state defines what the state machined does in a single state.
    You create your own state objects for each control state that extend this class
    in order for them to plug into the state machine.
    */
    static public abstract class State {
        public State(String name_) {
            name = name_;
        }

        // Returns the name of your state. All States have a string name.
        public String name() {
            return name;
        }

        /*
        Helper method so that the state machine can pass the opmode object
        into your states.
        */
        public void setOpMode(OpMode opmode_) { opmode = opmode_; }

        // Called when your state is entered.
        public abstract void enter();
        // Called when your state is exited.
        public abstract void exit();

        /*
        Called each time your state is the current state during loop();
        The elapsed time in seconds in the current state is passed in to
        simplify the use of timing in your states.
        */
        public abstract String update(double secs);

        // opmode reference so that derived classes can send telemetry easily.
        protected OpMode opmode;
        // Every state has a name.
        private String name;
    }

    /*
    Constructs a state machine that can handle up to numStates total states.
    It takes a reference to your opMode and uses that to automatically
    provide telemetry for your state transitions. The opMode is passed
    on to states so that they can easily provide their own additional telemetry
    as needed.
    */
    public StateMachine(OpMode opmode_, int numStates) {
        opmode = opmode_;
        states = new State[numStates]; // Creates space for # of states.
        initState = null;
        stopState = null;
        startState = null;

        currentState = null;
        stateTimer = new ElapsedTime(); // Keeps track of elapsed time in each state.
    }

    // State machine init function to be called from op_mode init
    public void init() {
        if (initState != null) {
            currentState = initState;
            logStateEntry(currentState);
            stateTimer.reset();
            currentState.enter();
        } else {
            if (opmode != null)
                opmode.telemetry.addData("WARNING: StateMachine:", "No init state.");
        }
    }

    // State machine init-loop function to be called from op_mode init_loop
    public void init_loop() {
        if (currentState != null)
            currentState.update(stateTimer.seconds());

        if (opmode != null)
            opmode.telemetry.update();
    }

    // State machine start function to be called from op_mode start()
    public void start() {
        if (currentState != null) {
            logStateExit(currentState);
            currentState.exit();
        }
        if (startState != null) {
            currentState = startState;
            logStateEntry(currentState);
            currentState.enter();
        } else {
            if (opmode != null)
                opmode.telemetry.addData("WARNING: StateMachine:", "No start state.");
        }
        if (opmode != null)
            opmode.telemetry.update();
    }

    // State machine loop function to be called from op_mode loop()
    public void loop() {
        if (currentState !=null) {
            // Run the update of the current state with the current elapsed time.
            String next = currentState.update(stateTimer.seconds());

            // If it returns something other than empty, then try the transition to
            // that state.
            if (!next.isEmpty()) {
                if (transitionTo(next) == false) {
                    if (opmode != null)
                        opmode.telemetry.addData("WARNING: State Machine:",
                                                    "Invalid next state %s", next);
                }
            }
            // Otherwise we use the default and stay in this state.
        } else {
            if (opmode != null)
                opmode.telemetry.addData("WARNING: State Machine:", "No current state.");
        }

        if (opmode != null)
            opmode.telemetry.update();
    }

    // State machine stop function to be called from op_mode stop()
    public void stop() {
        if (currentState != null)
         currentState.exit();
        
        if (stopState != null) {
            logStateExit(currentState);
            currentState = stopState;
            logStateExit(currentState);
            stateTimer.reset();
            currentState.enter();
            // Note: You never exit the stop state.
        } else {
            if (opmode != null)
                opmode.telemetry.addData("WARNING: State Machine:", "No stop state.");
                opmode.telemetry.update();
        }
    }

    // Add a state to the state machine
    public boolean addState(State s) {
        State found = findState(s.name());
        if (found != null)
            return false;
        else {
            // Otherwise, find an empty spot to put our state.
            int idx_e = 0;
            while (states[idx_e] != null && idx_e < states.length) {
                idx_e++;
            }
            if (states[idx_e] == null) {
                states[idx_e] = s;  // Save our state.
                s.setOpMode(opmode);
                return true;
            } else
                return false; // Returns error.
        }
    }

    // Adds the init state used by init in your state machine.
    public void addInitState(State s) {
        initState = s;
        s.setOpMode(opmode);
    }

    /*
    Adds the start state used by start in your state machine.
    If you don't set a start state that provides some transitions, your state machine
    won't do anything.
    */
    public void addStartState(State s) {
      startState = s;
      s.setOpMode(opmode);
    }

    // Adds the stop state used by start in your state machine.
    public void addStopState(State s) {
        stopState = s;
        s.setOpMode(opmode);
    }

    // Helper method used by the state machine to locate a state and transition to it.
    private boolean transitionTo(String name) {
        State next = findState(name);
        if (next == null || currentState == null)
            return false;
        else {
            logStateExit(currentState);

            currentState.exit();
            stateTimer.reset();
            currentState = next;

           logStateEntry(currentState);

            currentState.enter();
            return true;
        }
    }

    // Helper method used by the state machine to find a state by name
    // Returns null if the state is not found.
    private State findState(String name) {
        int idx = 0;
        while (states[idx] != null) {
            if (states[idx].name().equals(name))
                return states[idx];
            idx++;
        }
        return null; // Did not find the state.
    }

    // Helper method for logging state entry and exit to telemetry.
    void logStateEntry(State s) {
        if (opmode != null)
            opmode.telemetry.addData("StateMachine: Entering"," %s", currentState.name());

    }
    void logStateExit(State s) {
        if (opmode != null)
            opmode.telemetry.addData("StateMachine: Exiting"," %s", currentState.name());
    }

    private ElapsedTime stateTimer;

    // Special states for op_mode init, start and stop:
    private State initState;
    private State startState;
    private State stopState;

    // Keeps track of which state is current.
    private State currentState;

    // Array of states that were added to the state machine for the main loop.
    private State[] states;

    // Opmode so we can call opmode functions to provide automatic telemetry.
    private OpMode opmode;
}
