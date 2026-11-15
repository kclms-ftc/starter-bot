package org.firstinspires.ftc.teamcode.mechanisms;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/**
 * Controls our hardware for ejecting pollen.
 * Consists of one main launcher motor and one windmill servo for bringing pollen in.
 */
@SuppressWarnings("FieldCanBeLocal") //Makes my code slightly more clean
public class Shooter {
    private CRServo windmillServo;
    private DcMotorEx launcherMotor;
    private final int LAUNCHER_TARGET_VELOCITY = 1250;
    private final int LAUNCHER_MIN_VELOCITY = 1200;

    /**
     * Initializes the shooter specific hardware: the main intake motor and the windmill servo.
     * Sets control coefficients for the motor.
     * @param hwMap the hardware map allowing code to communicate with the control hub.
     */
    public void init(HardwareMap hwMap) {
        //Initializes each motor and our imu for field centric driving.
        launcherMotor = hwMap.get(DcMotorEx.class, "launcher");
        windmillServo = hwMap.get(CRServo.class, "windmillServo");

        //Changes some settings on the intake motor to increase accuracy
        launcherMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        launcherMotor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        //Reverses the windmill servo so it takes pollen inwards.
        windmillServo.setPower(0);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    /**
     * Ramps up the shooter motor and begins to fire pollen.
     * @param launchButton the button we will use to indicate that we want to launch.
     */
    public void shooterSequence(boolean launchButton) {

        //Increases the motor's velocity to prepare for fire.
        if (launchButton) {
            launcherMotor.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else {
            launcherMotor.setVelocity(0);
        }

        //Only begins to shoot if the motor is at the correct velocity.
        if (this.isReadyToLaunch(launchButton)) {
            windmillServo.setPower(1);
        } else {
            windmillServo.setPower(0);
        }
    }

    /**
     * Checks if we are ready to begin shooting.
     * @param launchButton the button we will use to indicate that we want to launch.
     */
    public boolean isReadyToLaunch(boolean launchButton) {
        return (launchButton && launcherMotor.getVelocity() > LAUNCHER_MIN_VELOCITY);
    }

}
