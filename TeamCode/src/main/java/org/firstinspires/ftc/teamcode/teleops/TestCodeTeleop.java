// This TeleOp is our main TeleOp!!

package org.firstinspires.ftc.teamcode.teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 */

@TeleOp()
public class TestCodeTeleop extends OpMode{
    private ElapsedTime runtime = new ElapsedTime();

    //objects
    DcMotorEx frontRight = null;
    DcMotorEx frontLeft = null;
    DcMotorEx backRight = null;
    DcMotorEx backLeft = null;
    DcMotorEx elevator = null;
    Servo intake = null;

    //elevator variables
    int currentPosition = 0;
    int targetPosition = 0;
    final int SMALL_POLE_POS = 1292;
    final int MED_POLE_POS = 2239;
    final int LONG_POLE_POS = 3083;

    //motor power and servo positions
    double frontRightPower = 0; //motor domain [-1,1]
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;

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
        intake = hardwareMap.get(Servo.class, "intake");

        //motor and servo directions
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        elevator.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(Servo.Direction.FORWARD);

        //elevator encoder for set positions
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Zero Power Behavior: full break when motor input = 0;
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //setting PID coefficients
        frontRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
        frontLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);
        backRight.setVelocityPIDFCoefficients(15, 0, 0, 0);
        backLeft.setVelocityPIDFCoefficients(15, 0, 0, 0);

        //text on driver hub to let us know robot has been initialized
        telemetry.addData("Status", "Initialized");
        telemetry.update();
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
//------------------------------------------- WHEELS -------------------------------------
        double drive = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        frontRightPower = drive - strafe - turn;
        frontLeftPower = drive + strafe + turn;
        backRightPower = drive + strafe - turn;
        backLeftPower = drive - strafe + turn;

        frontRight.setVelocity(frontRightPower*1500);
        frontLeft.setVelocity(frontLeftPower*1500);
        backRight.setVelocity(backRightPower*1500);
        backLeft.setVelocity(backLeftPower*1500);

        //intake
        //in
        if (gamepad2.left_bumper) {
            targetPosition -=200;
            elevator.setTargetPosition(targetPosition);
            elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            intake.setPosition(0.4);
        }
        //out
        if (gamepad2.right_bumper) {
            intake.setPosition(0.0);
        }

//----------------------------------------ELEVATOR----------------------------------------------
        currentPosition = elevator.getCurrentPosition();

        //manual controls using trigger
        if (gamepad2.left_trigger > 0 || gamepad2.right_trigger > 0) {
            if (gamepad2.right_trigger > 0) {
                targetPosition -= 20;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.left_trigger > 0) {
                targetPosition += 20;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        } else { //pre-defined pole heights using abxy buttons
            if (gamepad2.a) {
                targetPosition = 350;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.b) {
                targetPosition = SMALL_POLE_POS;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.x) {
                targetPosition = MED_POLE_POS;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.y) {
                targetPosition = LONG_POLE_POS;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        }

        if (targetPosition > currentPosition) {
            elevator.setPower(0.8);
        }
        else if (currentPosition > targetPosition) {
            elevator.setPower(-0.8);
        }
        else if (currentPosition == targetPosition) {
            elevator.setPower(0);
        }

        //limits
        if (targetPosition > 4000) {
            targetPosition = 4000;
        } else if (targetPosition < 0) {
            targetPosition = 0;
        }

        telemetry.addData("elevator pos: ", elevator.getCurrentPosition());
        telemetry.addData("frontRight pos: ", frontRight.getCurrentPosition());
        telemetry.addData("frontLeft pos: ", frontLeft.getCurrentPosition());
        telemetry.addData("backRight pos: ", backRight.getCurrentPosition());
        telemetry.addData("backLeft pos: ", backLeft.getCurrentPosition());
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }

}
