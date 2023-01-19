//This TeleOp is used to make sure the intake is working
package org.firstinspires.ftc.teamcode.teleops.testTeleops;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeTestTeleop extends OpMode{
    Servo intake = null;

    @Override
    public void init() {
        intake = hardwareMap.get(Servo.class, "intake");
        intake.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void start() {
        intake.setPosition(0.5);
    }

    @Override
    public void loop() {
        if (gamepad2.left_bumper) {
            intake.setPosition(1);
        }
        if (gamepad2.right_bumper) {
            intake.setPosition(0);
        }
    }
}
