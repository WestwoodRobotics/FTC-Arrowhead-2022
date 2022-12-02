//This TeleOp is used to make sure the slide code is working
package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@TeleOp
public class ElevatorTestTeleop extends OpMode{
    DcMotorEx elevator = null;
    double input = 0;
    final int SMALL_POLE_POS = 7572;
    final int MED_POLE_POS = 11546;
    final int LONG_POLE_POS = 14313;
    int targetPosition = 0;
    double elevatorPower = 0;
    int currentPosition = 0;



    @Override
    public void init() {
        elevator = hardwareMap.get(DcMotorEx.class, "elevator");
        elevator.setDirection(DcMotor.Direction.REVERSE);
        elevator.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        elevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        elevator.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        currentPosition = elevator.getCurrentPosition();
        if (gamepad2.left_trigger > 0 || gamepad2.right_trigger > 0) {
            //elevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            if (gamepad2.right_trigger > 0) {
                //elevator.setPower(.8);
                targetPosition -= 20;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            } else if (gamepad2.left_trigger > 0) {
//                elevator.setPower(-.8);
                targetPosition = 20;
                elevator.setTargetPosition(targetPosition);
                elevator.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
        } else {
            if (gamepad2.a) {
                targetPosition = 617;
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
            elevator.setPower(1);
        }
        else if (currentPosition > targetPosition) {
            elevator.setPower(1);
        }
        else if (currentPosition == targetPosition) {
            elevator.setPower(0);
        }

        //limits
        if (targetPosition > 10000) {
            targetPosition = 10000;
        } else if (targetPosition < 0) {
            targetPosition = 0;
        }

        telemetry.addData("elevatorPos", elevator.getCurrentPosition());
    }
}
