# FTC Mecanum Starter Bot

Mecanum drive code with goBILDA Pinpoint odometry.

```
Opmodes/
    TeleOp.java     driver control
    Auto.java       30s autonomous

Subsystems/
    Drivetrain.java mecanum kinematics, wheel powers
    Intake.java     intake motor/servo

Helpers/
    PinpointLocalizer.java   pose (x, y, heading) from Pinpoint
```

Opmodes handle input and sequencing only. All hardware init and constants live in the subsystems.

## Setup
1. Drop the packages into `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`.
2. Match Driver Station config names to the strings in each subsystem.
3. Set pod offsets and encoder direction in `PinpointLocalizer` before running Auto.