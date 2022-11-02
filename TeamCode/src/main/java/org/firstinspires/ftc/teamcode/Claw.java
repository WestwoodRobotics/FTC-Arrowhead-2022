package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Claw")
public class Claw extends OpMode {

    public Servo leftServo;
    public Servo rightServo;
    public double leftServoPower = 0;


    @Override
    public void init() {
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");
    }

    @Override
    public void loop() {
        leftServoPower = (gamepad1.left_stick_x + 1)/2;
        leftServo.setPosition(leftServoPower);
    }
}
