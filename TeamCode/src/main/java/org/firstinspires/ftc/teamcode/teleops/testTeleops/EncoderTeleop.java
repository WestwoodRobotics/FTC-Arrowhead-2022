//This TeleOp is used to find the pole heights for encoder
package org.firstinspires.ftc.teamcode.teleops.testTeleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class EncoderTeleop extends OpMode {
    DcMotorEx elevator = null;
    double input = 0;

    @Override
    public void init() {
        elevator = hardwareMap.get(DcMotorEx.class, "elevator");
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        input = gamepad1.left_stick_y;
        elevator.setPower(input * .6);
        if (input == 0) {
            elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        telemetry.addData("elevator position", elevator.getCurrentPosition());
    }
}
