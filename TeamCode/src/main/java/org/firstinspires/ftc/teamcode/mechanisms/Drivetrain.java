package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Holds the main logic for driving the robot.
 * Drivetrain mechanism consists of 4 Mecanum wheels.
 */
public class Drivetrain {
    private DcMotor leftFrontDrive;
    private DcMotor leftBackDrive;
    private DcMotor rightFrontDrive;
    private DcMotor rightBackDrive;
    private IMU imu;

    /**
     * Initializes the drivetrain motors.
     * Sets each motor to operate in the correct direction
     * @param hwMap the hardware map allowing code to communicate with the control hub.
     */
    public void init(HardwareMap hwMap) {
        //Initializes each motor and our imu for field centric driving.
        leftFrontDrive= hwMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hwMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hwMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hwMap.get(DcMotor.class, "rightBackDrive");
        imu = hwMap.get(IMU.class, "imu");

        //Reverses the direction of opposite-sided motors such that it drives correctly.
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //Allows the motors to run slightly more consistently.
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //Causes each motor to slow down faster. Increases responsiveness.
        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Sets the orientation of the IMU.
        RevHubOrientationOnRobot RevOrientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP);
        imu.initialize(new IMU.Parameters(RevOrientation));

    }

    /**
     * Code for driving our robot. Drives relative to the robot's position.
     * @param forward how quickly our robot moves forward. Takes the value of the left stick's y component.
     * @param strafe our robot's side-to-side movement. Takes the value of the left stick's x component.
     * @param rotate our robot's rotation. Takes the value of the right stick's x component.
     */
    public void robotOrientedDrive(double forward, double strafe, double rotate) {
        //Calculates the power sent to each motor (established algorithm)
        double leftFrontPower = forward + strafe + rotate;
        double rightFrontPower = forward - strafe - rotate;
        double leftBackPower = forward - strafe + rotate;
        double rightBackPower = forward + strafe - rotate;

        //Finds the maximum power between all 4 powers
        double maxPower = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        maxPower = Math.max(maxPower, Math.abs(leftBackPower));
        maxPower = Math.max(maxPower, Math.abs(rightBackPower));

        //If maximum power is above 1, divides all powers by the maximum (to maintain the direction of the velocity vector)
        //Set power only takes arguments between -1 and 1.
        if (maxPower > 1.0) {
            leftFrontPower /= maxPower;
            rightFrontPower /= maxPower;
            leftBackPower /= maxPower;
            rightBackPower /= maxPower;
        }

        //Updates each motor's speed.
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);
    }

    /**
     * Code for driving our robot. Drives relative to the robot's position.
     * @param forward how quickly our robot moves forward. Takes the value of the left stick's y component.
     * @param strafe our robot's side-to-side movement. Takes the value of the left stick's x component.
     * @param rotate our robot's rotation. Takes the value of the right stick's x component.
     */
    public void fieldOrientedDrive(double forward, double strafe, double rotate) {

        //Finds our velocity vector in polar coordinates (as rotation is easier)
        double theta = Math.atan2(forward, strafe);
        double r = Math.hypot(forward, strafe);

        //Rotates our vector by our IMU offset
        theta = AngleUnit.normalizeRadians(theta -
                imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS));

        //Converts vector back to Cartesian coordinates
        double newForward = r * Math.sin(theta);
        double newStrafe = r * Math.cos(theta);

        //Calls our original drive with fixed coordinates.
        this.robotOrientedDrive(newForward, newStrafe, rotate);
    }
}
