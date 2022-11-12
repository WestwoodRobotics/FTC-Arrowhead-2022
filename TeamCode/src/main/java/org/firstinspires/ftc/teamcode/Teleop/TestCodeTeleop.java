package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 */

@TeleOp()
public class TestCodeTeleop extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();

    //declare wheels, servo, and elevator
    DcMotorEx frontRight = null;
    DcMotorEx frontLeft = null;
    DcMotorEx backRight = null;
    DcMotorEx backLeft = null;
    DcMotorEx elevator = null;
    CRServo leftServo;
    CRServo rightServo;
    //DcMotor elevator = null;

    //elevator variables
    int currentPosition = 0;
    int targetPosition = 0;
    final int SMALL_POLE_POS = -1445;
    final int MED_POLE_POS = -2489;
    final int LONG_POLE_POS = -3327;

    //motor power and servo positions
    double frontRightPower = 0; //motor domain [-1,1]
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;
    double elevatorPower = 0;
    public double leftServoPosition = 0.5; //servos domain: [0,1]
    public double rightServoPosition = 0.5;


    /*
     * Code to run ONCE when the driver hits INIT (on the driver hub)
     */
    @Override
    public void init() {
        //sync to driver hub and robot
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        elevator = hardwareMap.get(DcMotorEx.class, "elevator");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        rightServo = hardwareMap.get(CRServo.class, "rightServo");

        //motor and servo directions
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        elevator.setDirection(DcMotor.Direction.FORWARD);
        leftServo.setDirection(CRServo.Direction.FORWARD);
        rightServo.setDirection(CRServo.Direction.REVERSE);

//        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

//        frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
//        frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
//        backRight.setMode(DcMotor.RunMode.RESET_ENCODERS);
//        backLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);

        //elevator encoder
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        elevator.setMode(DcMotor.RunMode.RESET_ENCODERS);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Zero Power Behavior: full break when motor input = 0;
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        //setting PID coefficients
        frontRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
        frontLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);
        backRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
        backLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);
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
        //game pad controls
        double drive = gamepad1.left_stick_y;
        double strafe = -gamepad1.left_stick_x;
        double turn = -gamepad1.right_stick_x;
        elevatorPower = gamepad2.left_stick_y;
        leftServoPosition = gamepad2.left_stick_x;
        rightServoPosition = -gamepad2.right_stick_x;
        telemetry.addData("left servo pos", leftServo.getPower());
        telemetry.addData("right servo pos", rightServo.getPower());

        //wheel equations
        frontRightPower = drive - strafe - turn;
        frontLeftPower = drive + strafe + turn;
        backRightPower = drive + strafe - turn;
        backLeftPower = drive - strafe + turn;

        //motor power and servo position
        frontRight.setPower(frontRightPower);
        frontLeft.setPower(frontLeftPower);
        backRight.setPower(backRightPower);
        backLeft.setPower(backLeftPower);
        leftServo.setPower(leftServoPosition);
        rightServo.setPower(rightServoPosition);

        frontRight.setVelocity(frontRightPower*3000);
        frontLeft.setVelocity(frontLeftPower*3000);
        backRight.setVelocity(backRightPower*3000);
        backLeft.setVelocity(backLeftPower*3000);

//        frontRight.setTargetPosition(448); //gear ratio (16:1 for bot) * 28 ticks per revolution
//        frontLeft.setTargetPosition(448);
//        backRight.setTargetPosition(448);
//        backLeft.setTargetPosition(448);
//        elevator.setTargetPosition(448);

//        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Elevator Code
        currentPosition = elevator.getCurrentPosition();

        if (gamepad1.a) {
            elevator.setTargetPosition(-180);
            elevator.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            targetPosition = -183;
        } else if (gamepad1.b) {
            elevator.setTargetPosition(SMALL_POLE_POS);
            elevator.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            targetPosition = SMALL_POLE_POS;
        } else if (gamepad1.x) {
            elevator.setTargetPosition(MED_POLE_POS);
            elevator.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            targetPosition = MED_POLE_POS;
        } else if (gamepad1.y) {
            elevator.setTargetPosition(LONG_POLE_POS);
            elevator.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            targetPosition = LONG_POLE_POS;
        }


        if (targetPosition > currentPosition) {
            elevator.setPower(1);
        }
        else if (currentPosition > targetPosition) {
            elevator.setPower(1);
        }
        else if (currentPosition == targetPosition) {
            elevator.setPower(0);
        }

        if (gamepad1.dpad_left) {
            elevator.setPower(0);
        }
        if (gamepad1.dpad_up) {
            elevator.setPower(.5);
        }
        if (gamepad1.dpad_down) {
            elevator.setPower(.5);
        }

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
