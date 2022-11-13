//For use when we are on the blue alliance and on the right side of the field.
//Blue terminal is to our right
//Formerly RunRightAuton
package org.firstinspires.ftc.teamcode.Autons;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@Autonomous()
public class TeamBluePosRight extends LinearOpMode {
    DcMotorEx frontRight = null;
    DcMotorEx frontLeft = null;
    DcMotorEx backRight = null;
    DcMotorEx backLeft = null;
    DcMotor elevator = null;
    //CRServo leftServo;
    //CRServo rightServo;

    final double frontRightPower = 0.5;
    final double frontLeftPower = 0.5;
    final double backRightPower = 0.5;
    final double backLeftPower = 0.5;
    final int SMALL_POLE_POS = -1545;

    @Override

    public void runOpMode() {
        frontRight  = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight  = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        //elevator = hardwareMap.get(DcMotorEx.class, "arm");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        //elevator.setDirection(DcMotor.Direction.REVERSE);

        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        //elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        elevator.setMode(DcMotor.RunMode.RESET_ENCODERS);

        waitForStart();


        frontRight.setPower(-frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(-backLeftPower);
        sleep(1000);

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backRight.setPower(0);
        backLeft.setPower(0);
        sleep(1000);

    }
}
