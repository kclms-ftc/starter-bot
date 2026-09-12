package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Controls the roller intake mechanism for collecting pollen.
 * Intake mechanism consists of one motor driving an intake roller, and two servos which pull elements out of corners.
 * This class manages motor and  servo velocity and direction.
 */
public class Intake {
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;
    private DcMotor intakeMotor;

    /**
     * Initializes the intake specific hardware: intake servos and motors.
     * Sets base power and direction.
     *
     * @param hwMap the hardware map allowing code to communicate with the control hub.
     */
    public void init(HardwareMap hwMap) {

        //Initializes each servo and the motor.
        leftIntakeServo = hwMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hwMap.get(CRServo.class, "right_intake_servo");
        intakeMotor = hwMap.get(DcMotor.class, "intake_motor");


        //Ensures each motor and servo is motionless until we intentionally send a command.
        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);
        intakeMotor.setPower(0);

        //Ensures both the left and the right servos act in the same direction.
        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);

        //Causes the motor to slow down faster, creating a more controllable intake.
        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    /**
     * Allows the user to set the speed of intake servos and motors simultaneously.
     *
     * @param power Must be a double between -1 and 1.
     */
    public void setPower(double power) {
        leftIntakeServo.setPower(power);
        rightIntakeServo.setPower(power);
        intakeMotor.setPower(power);
    }


}
