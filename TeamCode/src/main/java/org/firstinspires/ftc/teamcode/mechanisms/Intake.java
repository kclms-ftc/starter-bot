package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Controls the roller intake mechanism for collecting pollen.
 * Manages servo velocity and direction.
 */
public class Intake {
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;

    /**
     * Initializes specifically intake servos.
     * Sets base power and direction.
     * @param hwMap the hardware map allowing code to communicate with the control hub.
     */
    public void init(HardwareMap hwMap) {

        //Initializes each servo.
        leftIntakeServo = hwMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hwMap.get(CRServo.class, "right_intake_servo");

        //Ensures each servo is motionless until we intentionally send a command.
        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);

        //Ensures both the left and the right servos act in the same direction.
        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    /**
     * Allows the user to set the speed of intake servos simultaneously.
     * @param power Must be a double between -1 and 1.
     */
    public void setPower(double power) {
        leftIntakeServo.setPower(power);
        rightIntakeServo.setPower(power);
    }



}
