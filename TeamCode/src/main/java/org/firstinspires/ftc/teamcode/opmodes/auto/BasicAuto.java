package org.firstinspires.ftc.teamcode.opmodes.auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.mechanisms.Drivetrain;

/**
 * A very simple autonomous routine. Purely for scoring leave and park points.
 */
@Autonomous
public class BasicAuto extends LinearOpMode {
    private final Drivetrain drivetrain = new Drivetrain();

    @Override
    public void runOpMode() {
        // --- Initializes the drivetrain

        drivetrain.init(hardwareMap);
        telemetry.addLine("Auto Ready");
        telemetry.update();

        //Keeps the robot paused until we are ready.
        waitForStart();

        // --- Moving the Robot Forward ---

        //Moves the robot forward for 1 second
        drivetrain.robotOrientedDrive(0.5,0,0);
        sleep(1000);

        drivetrain.robotOrientedDrive(0,0,0);
        telemetry.addLine("Robot has moved forward");
        telemetry.update();
        sleep(750); //Small time delay between next command

        // --- Moving the Robot Sideways ---

        //Moves the robot left for 1 second
        drivetrain.robotOrientedDrive(0,-0.5,0);
        sleep(1000);

        drivetrain.robotOrientedDrive(0,0,0);
        telemetry.addLine("Robot has moved sideways");
        telemetry.update();
        sleep(750); //Small time delay between next command

        // --- Moving the Robot Backwards ---

        //Moves the robot backwards for 1 second
        drivetrain.robotOrientedDrive(-0.5,0,0);
        sleep(1000);


        drivetrain.robotOrientedDrive(0,0,0);
        telemetry.addLine("Robot has moved backwards");
        telemetry.update();
    }

}
