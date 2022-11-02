package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorEx;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 */

@TeleOp(name="Moving Motor", group="Iterative Opmode")

public class TestCodeTeleop extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();
    DcMotorEx frontRight = null;
    DcMotorEx frontLeft = null;
    DcMotorEx backRight = null;
    DcMotorEx backLeft = null;
    DcMotorEx elevator = null;

    double frontRightPower = 0;
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;
    double elevatorPower = 0;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {

        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        elevator = hardwareMap.get(DcMotorEx.class, "elevator");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        backRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
        backLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
//        elevatorDrive1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//        elevatorDrive2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //setting PID coefficients
//        frontRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
//        frontLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);
//        backRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
//        backLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);
//        intakeDrive.setVelocityPIDFCoefficients(15, 0, 0, 0);
//        outtakeServo.setPosition(0);
    }
    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        double drive = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        frontRightPower = drive - strafe - turn;
        frontLeftPower = drive + strafe + turn;
        backRightPower = drive + strafe - turn;
        backLeftPower = drive - strafe + turn;

//          frontRightPower = drive + turn;
//          frontLeftPower = drive - turn;
//          backRightPower = drive + turn;
//          backLeftPower = drive - turn;

        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);

        frontRight.setVelocity(frontRightPower*3000);
        frontLeft.setVelocity(frontLeftPower*3000);
        backRight.setVelocity(backRightPower*3000);
        backLeft.setVelocity(backLeftPower*3000);

        //encoder stuff
//        frontRight.setTargetPosition(448); //gear ratio (16:1 for bot) * 28 ticks per revolution
//        frontLeft.setTargetPosition(448);
//        backRight.setTargetPosition(448);
//        backLeft.setTargetPosition(448);
////        elevator.setTargetPosition(437);
//
//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontRight.setPower(.5);
        frontLeft.setPower(.5);
        backRight.setPower(.5);
        backLeft.setPower(.5);

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        telemetry.addData("Motors", "left front (%.2f)", frontLeft.getVelocity());
        telemetry.addData("Motors", "right front (%.2f)", frontRight.getVelocity());
        telemetry.addData("Motors", "left back (%.2f)", backLeft.getVelocity());
        telemetry.addData("Motors", "right back (%.2f)", backRight.getVelocity());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
