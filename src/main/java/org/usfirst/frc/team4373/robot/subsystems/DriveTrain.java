package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick;
import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick.Direction;

/**
 * Programmatic representation of physical drive train components.
 * @author aaplmath
 * @author Henry Pitcairn
 */
public class DriveTrain extends Subsystem {
    private CANTalon left1;
    private CANTalon left2;
    private CANTalon right1;
    private CANTalon right2;
    private CANTalon middle1;
    private CANTalon middle2;

    private CANTalon.TalonControlMode controlMode;

    private static DriveTrain driveTrain = null;

    public static DriveTrain getDriveTrain() {
        driveTrain = driveTrain == null ? new DriveTrain() : driveTrain;
        return driveTrain;
    }

    // Whenever we're configuring motors, we will always use the following order:
    // left (1, 2), right (1, 2), middle (1, 2)

    /**
     * Initializes motors on respective ports, sets break and reverse modes, and sets followers.
    */
    private DriveTrain() {
        super("DriveTrain");
        this.left1 = new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.left2 = new CANTalon(RobotMap.LEFT_DRIVE_MOTOR_2);
        this.right1 = new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.right2 = new CANTalon(RobotMap.RIGHT_DRIVE_MOTOR_2);
        this.middle1 = new CANTalon(RobotMap.MIDDLE_DRIVE_MOTOR_1);
        this.middle2 = new CANTalon(RobotMap.MIDDLE_DRIVE_MOTOR_2);

        configureTalon(this.left1);
        configureTalon(this.left2);
        configureTalon(this.right1);
        configureTalon(this.right2);
        configureTalon(this.middle1);
        configureTalon(this.middle2);

        switchControlMode(CANTalon.TalonControlMode.Speed);

        this.left2.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.left2.set(RobotMap.LEFT_DRIVE_MOTOR_1);
        this.right2.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.right2.set(RobotMap.RIGHT_DRIVE_MOTOR_1);
        this.middle2.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.middle2.set(RobotMap.MIDDLE_DRIVE_MOTOR_1);
    }

    /**
     * Configures a CANTalon to work with velocity closed loop.
     * @param talon The CANTalon to configure.
     */
    private void configureTalon(CANTalon talon) {
        talon.enableBrakeMode(true);

        int absolutePosition = talon.getPulseWidthPosition() & 0xFFF; /* mask out the bottom12 bits, we don't care about the wrap arounds */
        // Use the low level API to set the quad encoder signal
        talon.setEncPosition(absolutePosition);
        // Set sensor and sensor direction
        talon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        talon.reverseSensor(false);
        // Set peak and nominal outputs—12V means full
        talon.configNominalOutputVoltage(+0d, -0d);
        talon.configPeakOutputVoltage(+12d, -12d);
        // Set the allowable closed-loop error—Closed-Loop output will be neutral in this range
        talon.setAllowableClosedLoopErr(0); // Always servo
        // Set closed loop gains in slot 0
        talon.setProfile(0);
        // PID config
        talon.setF(0.0d);
        talon.setP(0.1d);
        talon.setI(0.0d);
        talon.setD(0.0d);
    }

    /**
     * Sets the control mode of the motors.
     * @param mode The control mode to which to switch.
     */
    public void switchControlMode(CANTalon.TalonControlMode mode) {
        this.controlMode = mode;
        this.left1.changeControlMode(mode);
        this.right1.changeControlMode(mode);

        // This code has been modified because the middle wheel has no encoder
        this.middle1.changeControlMode(CANTalon.TalonControlMode.Voltage);
        //     this.middle1.changeControlMode(mode);
    }

    // In voltage/speed "set" methods, we multiply by 500 to convert from voltage to RPM
    // (5400 max rpm / 10:1 gear ratio) ≈ 500

    /**
     * Sets power to the left motors.
     * @param value In voltage or speed mode,
     *              the power to allocate to the left motors from -1 to 1.
     *              In position mode, the number of rotations to turn the left motors.
     */
    public void setLeft(double value) {
        if (controlMode == CANTalon.TalonControlMode.Speed) {
            this.left1.set(value * 500);
        } else {
            this.left1.set(value);
        }
    }

    /**
     * Sets power to the right motors.
     * @param value In voltage or speed mode,
     *              the power to allocate to the right motors from -1 to 1.
     *              In position mode, the number of rotations to turn the right motors.
     */
    public void setRight(double value) {
        if (controlMode == CANTalon.TalonControlMode.Speed) {
            this.right1.set(value * 500);
        } else {
            this.right1.set(-value);
        }
    }

    /**
     * Sets power to the middle motors.
     * @param value In voltage or speed mode,
     *              the power to allocate to the middle motors from -1 to 1.
     *              In position mode, the number of rotations to turn the middle motors.
     */
    public void setMiddle(double value) {
        // This code is commented because we have no encoder on the talon
        //    if (controlMode == CANTalon.TalonControlMode.Speed) {
        //        this.middle1.set(value * 500);
        //    } else {
        //        this.middle1.set(value);
        //    }
        this.middle1.set(value);
    }

    /**
     * "Bumps" the robot in the target direction.
     * @param direction The direction in which to bump the robot.
     */
    public void bumpToDirection(Direction direction) {
        CANTalon.TalonControlMode origControlMode = this.right1.getControlMode();
        this.right1.changeControlMode(CANTalon.TalonControlMode.Position);
        this.left1.changeControlMode(CANTalon.TalonControlMode.Position);
        this.middle1.changeControlMode(CANTalon.TalonControlMode.Position);
        switch (direction) {
            case FORWARD:
                this.right1.set(0.5d);
                this.left1.set(0.5d);
                break;
            case BACKWARD:
                this.right1.set(-0.5d);
                this.left1.set(-0.5d);
                break;
            case RIGHT:
                this.middle1.set(0.5d);
                break;
            case LEFT:
                this.middle1.set(-0.5d);
                break;
            default:
                break;
        }
        this.right1.changeControlMode(origControlMode);
        this.left1.changeControlMode(origControlMode);
        this.middle1.changeControlMode(origControlMode);
    }

    /**
     * Sets power to both left and right motors.
     * @param value In voltage or speed mode,
     *              the power to allocate to the motors from -1 to 1.
     *              In position mode, the number of rotations to turn the motors.
     */
    public void setBoth(double value) {
        setLeft(value);
        setRight(value);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

}
