# FTC Mecanum Starter Bot

Robot code for a mecanum-drive starter bot, with goBILDA Pinpoint odometry.

## Structure

```
Opmodes/
    TeleOp.java
    Auto.java

Subsystems/
    Drivetrain.java
    Intake.java

Helpers/
    PinpointLocalizer.java
```

### Opmodes/
Entry points registered with the Driver Station. These only handle gamepad input and sequencing; all hardware logic lives in the subsystems.

- **TeleOp.java**: driver-controlled period. Reads the gamepads and passes commands to `Drivetrain` and `Intake`.
- **Auto.java** : 30-second autonomous routine. Uses `PinpointLocalizer` for pose feedback.

### Subsystems/
Hardware wrappers. Each class owns its motors/servos and exposes a small API, so opmodes never touch `hardwareMap` directly.

- **Drivetrain.java**: mecanum kinematics. Converts a robot-relative or field-relative (x, y, rotation) command into the four wheel powers, and handles motor directions and zero-power behaviour.
- **Intake.java**: intake motor/servo control (in, out, stop).

### Helpers/
Shared utilities that are not physical subsystems.

- **PinpointLocalizer.java**: wraps the goBILDA Pinpoint Odometry Computer. Handles pod offsets, encoder resolution, IMU recalibration, and returns the robot pose (x, y, heading).

## Setup

1. Clone into a standard `FtcRobotController` project and place the packages under `TeamCode/src/main/java/org/firstinspires/ftc/teamcode/`.
2. Match the hardware configuration names in the Driver Station to the strings used in each subsystem's `init`.
3. Set the Pinpoint pod offsets and encoder direction in `PinpointLocalizer` before running autonomous.

## Conventions

- Opmodes contain no hardware initialisation.
- Motor directions and constants are defined once, at the top of each subsystem.
- Field-relative driving is toggled in `TeleOp`, not baked into `Drivetrain`.