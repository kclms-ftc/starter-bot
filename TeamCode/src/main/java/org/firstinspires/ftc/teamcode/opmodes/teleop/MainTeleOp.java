package org.firstinspires.ftc.teamcode.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.Shooter;

/**
 * The main TeleOp which brings our subsystems together.
 * Our controls are as such:
 *  Velocity - Left stick x and y
 *  Rotation - Right stick x
 *  Shooting - Right bumper
 *  Increase intake - Right Trigger
 *  Decrease intake - Left Trigger
 */
@TeleOp
public class MainTeleOp extends OpMode {
    private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();
    private final Drivetrain drivetrain = new Drivetrain();

    @Override
    public void init() {
        //Initializes all subsystems
        intake.init(hardwareMap);
        drivetrain.init(hardwareMap);
        shooter.init(hardwareMap);
        telemetry.addLine("Hardware initialised");
    }

    @Override
    public void loop() {
        // --- Mapping our inputs to variablezini ---
        double intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
        boolean shootButton = gamepad1.right_bumper;

        // --- Drivetrain Code ---
        drivetrain.fieldOrientedDrive(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x);
        //drivetrain.robotOrientedDrive(-gamepad1.left_stick_y,gamepad1.left_stick_x,gamepad1.right_stick_x); for testing + as contingency

        // --- Shooter Code ---
        shooter.shooterSequence(shootButton);
        boolean readyToShoot = shooter.isReadyToLaunch(shootButton);

        // --- Intake Code ---
        if (readyToShoot) {
            intakePower += 0.5;
        }
        intake.setPower(intakePower);


        // --- Telemetry ---
        telemetry.addData("Triggers", "left (%.2f), right (%.2f)",gamepad1.left_trigger, gamepad1.right_trigger);
        telemetry.addData("Intake Power", "%.2f", intakePower);
        telemetry.addData("Currently shooting", readyToShoot);
    }
}

