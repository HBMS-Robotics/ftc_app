/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.IntegratingGyroscope;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AngularVelocity;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;


//This is an example LinearOpMode that shows how to use the Modern Robotics Gyro.

@TeleOp(name = "Sensor: MR Gyro", group = "Sensor")
@Disabled
public class Gyro_Test extends LinearOpMode 
{

 //{@link IntegratingGyroscope} (and it's base interface,
 //{@link Gyroscope}) {@link MRgyro}
  IntegratingGyroscope gyro;
  ModernRoboticsI2cGyro MRgyro;
  ServoController servoBOX;

  // A timer helps provide feedback while calibration is taking place
  ElapsedTime timer = new ElapsedTime();

 // @Override
  public void runOpMode() 
  {

    boolean lastResetState = false;
    boolean curResetState  = false;
    int head = 0;
    // A timer helps provide feedback while calibration is taking place
    ElapsedTime timer = new ElapsedTime();
    
    // Get a reference to a Modern Robotics gyro object.
    MRgyro = hardwareMap.get(ModernRoboticsI2cGyro.class, "gyro");
    servoBOX = hardwareMap.get(ServoController.class, "servo");
    gyro = (IntegratingGyroscope)MRgyro;

    // Start calibrating the gyro. DO at Initialization 
    telemetry.log().add("Gyro Calibrating. Do Not Move!");
    MRgyro.calibrate();
    timer.reset();
    while (!isStopRequested() && MRgyro.isCalibrating())  {
      telemetry.addData("calibrating", "%s", Math.round(timer.seconds())%2==0 ? "|.." : "..|");
      telemetry.update();
      sleep(50);
    }
    
    telemetry.log().clear(); telemetry.log().add("Gyro Calibrated. Press Start.");
    telemetry.clear(); telemetry.update();

    // Wait for the start button
    waitForStart();
    telemetry.log().clear();
    telemetry.log().add("Press A & B to reset telemetry");
    
    
    while (opModeIsActive())  
    {
//this is THE LOOP
      // If the A and B buttons are pressed just now, reset Z heading.
      curResetState = (gamepad1.a && gamepad1.b);
      if (curResetState && !lastResetState) {
        MRgyro.resetZAxisIntegrator();
      }
      lastResetState = curResetState;

      // The raw() methods report the angular rate of change about each of the
      // three axes directly as reported by the underlying sensor IC.
      int rawX = MRgyro.rawX();
      int rawY = MRgyro.rawY();
      int rawZ = MRgyro.rawZ();
      int heading = MRgyro.getHeading();
      int integratedZ = MRgyro.getIntegratedZValue();


      // Read dimensionalized data from the gyro. 
      AngularVelocity rates = gyro.getAngularVelocity(AngleUnit.DEGREES);
      float zAngle = gyro.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;

      // Read administrative information from the gyro
      int zAxisOffset = MRgyro.getZAxisOffset();
      int zAxisScalingCoefficient = MRgyro.getZAxisScalingCoefficient();


      double servo_ang = 0.5 + (2.0 * (double)heading) / 360.0;
      servoBOX.setServoPosition(1, servo_ang);

      telemetry.addLine()
        .addData("dx", formatRate(rates.xRotationRate))
        .addData("dy", formatRate(rates.yRotationRate))
        .addData("dz", "%s deg/s", formatRate(rates.zRotationRate));
      telemetry.addData("angle", "%s deg", formatFloat(zAngle));
      telemetry.addData("heading", "%3d deg", heading);
      telemetry.addData("integrated Z", "%3d", integratedZ);
      telemetry.addLine().addData("z offset", zAxisOffset).addData("z coeff", zAxisScalingCoefficient);
      telemetry.addData("Servo", servo_ang);
      telemetry.update();

    }
    

    
  }

  String formatRaw(int rawValue) {
    return String.format("%d", rawValue);
  }

  String formatRate(float rate) {
    return String.format("%.3f", rate);
  }

  String formatFloat(float rate) {
    return String.format("%.3f", rate);
  }

}
