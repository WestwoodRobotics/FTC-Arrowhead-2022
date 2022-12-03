//This TeleOp is used to make sure our wheel motor directions are correct
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class fixMecanum extends OpMode{
    DcMotorEx frontRight = null;
    DcMotorEx frontLeft = null;
    DcMotorEx backRight = null;
    DcMotorEx backLeft = null;

    double frontRightPower = 0;
    double frontLeftPower = 0;
    double backRightPower = 0;
    double backLeftPower = 0;

    @Override
    public void init() {
        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
    }

    @Override
    public void loop() {
//        frontRight.setPower(0.1);
//        frontLeft.setPower(0.1);
//        backRight.setPower(0.1);
//        backLeft.setPower(0.1);

        double drive = gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double turn = gamepad1.right_stick_x;

        frontRightPower = drive - strafe - turn;
        frontLeftPower = drive + strafe + turn;
        backRightPower = drive + strafe - turn;
        backLeftPower = drive - strafe + turn;

    }

}
