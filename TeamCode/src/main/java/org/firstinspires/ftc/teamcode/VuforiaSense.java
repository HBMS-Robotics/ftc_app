package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.external.samples.ConceptVuforiaNavigation;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by todd on 11/16/17.
 */

public class VuforiaSense extends StateMachine.State {
    public VuforiaSense(String name, String next_L_, String next_M_, String next_R_) {
        super(name);
        next_L = next_L_;
        next_R = next_R_;
        next_M = next_M_;
    }

    @Override
    public void enter() {

        int cameraID = opmode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opmode.hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraID);

        parameters.vuforiaLicenseKey = "ATKNdyn/////AAAAmYQhJpUAuEl+gLr8eW1fdqZiov/1+Ph3SRLpLy8hTtmGYB3hW7EgXM0DDKfQC3WIKU42zSSx2eqQKHbIPs5lM2f92UZiqOG2WDP0nR7lqfsE9MSI3r2SEI71dSmOPn6HmQkG/eAOhZfH5EaidI978+xppwmYPusNQybcHnRJUEos8G5HRU1rjP5sY2n6PDLXQltvI0V/TF3DW3fdzzybtcmnXGtmMsYR1qmPUS85wZh0zMixnbXohqIIZ+ctJFqT8Zb+lsDjchj+Ceh8EHxRgF201aM5wKVlf/iuxepPH7BKQ+iQFvWW0SRWRyAei2Lih4OcN1La/9gz/o2SltdOvAoh5mPa00x/MaFZdjQMvITQ";;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        relicTrackables = vuforia.loadTrackablesFromAsset("RelicVuMark");

        relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        relicTrackables.activate();

        counter = 0;
    }

    @Override
    public void exit() {

        relicTrackables.deactivate();
    }

    @Override
    public String update(double secs) {


        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        opmode.telemetry.addData("VuMark", "Try %d, %s visible", counter, vuMark);


        if (vuMark == RelicRecoveryVuMark.LEFT) {

            return next_L;

        }
        if (vuMark == RelicRecoveryVuMark.RIGHT) {

            return next_R;

        }
        if (vuMark == RelicRecoveryVuMark.CENTER) {

            return next_M;

        }
        else {

            return next_L;
        }

    }

    private String next_R;
    private String next_L;
    private String next_M;
    // private VuforiaLocalizer.Parameters parameters;
    private VuforiaLocalizer vuforia;
    private VuforiaTrackables relicTrackables;
    private VuforiaTrackable relicTemplate;
    private int counter;
}
