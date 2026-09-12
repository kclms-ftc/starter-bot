package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Intake;

@TeleOp
public class MainTeleOp extends OpMode {
    private final Intake intake = new Intake();

    @Override
    public void init() {
        intake.init(hardwareMap);
    }

    @Override
    public void loop() {
        // --- Mapping inputs to variables --
        double intakePower = gamepad1.right_trigger - gamepad1.left_trigger;

        // --- Intake Code ---
        intake.setPower(intakePower);

        // --- Telemetry ---
        telemetry.addData("Triggers", "left (%.2f), right (%.2f)",gamepad1.left_trigger, gamepad1.right_trigger);
        telemetry.addData("Intake Power", "%.2f", intakePower);
    }
}

