//This TeleOp is used to make sure the claw is working
package org.firstinspires.ftc.teamcode.Teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class clawTestTeleop extends OpMode{
    Servo claw = null;

    @Override
    public void init() {
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setDirection(Servo.Direction.FORWARD);
    }

    @Override
    public void start() {
        claw.setPosition(0.5);
    }

    @Override
    public void loop() {
        if (gamepad2.left_bumper) {
            claw.setPosition(1);
        }
        if (gamepad2.right_bumper) {
            claw.setPosition(0);
        }
    }
}
