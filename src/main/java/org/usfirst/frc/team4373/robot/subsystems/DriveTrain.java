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

        this.controlMode = CANTalon.TalonControlMode.Speed;
        this.left1.changeControlMode(controlMode);
        this.right1.changeControlMode(controlMode);
        this.middle1.changeControlMode(controlMode);

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
        talon.setF(0.0);
        talon.setP(0.1);
        talon.setI(0.0);
        talon.setD(0.0);
    }

    /**
     * Sets power to the motors.
     * @param forward The forward power (-1=backward to 1=forward).
     * @param right The left/right power (-1=left to 1=right).
     */
    public void move(double forward, double right) {
        // TODO: Determine appropriate amount of power to deliver to right/left motors
        // TODO: Figure out if we even need this method
        this.middle1.set(right);
    }

    // In "set" methods, we multiply by 500 to convert from voltage to RPM
    // (5400 max rpm / 10:1 gear ratio)

    /**
     * Sets power to the left motors.
     * @param power The power to allocate to the left motors from -1 to 1.
     */
    public void setLeft(double power) {
        if (controlMode == CANTalon.TalonControlMode.Speed) {
            this.left1.set(power * 500);
        } else {
            this.left1.set(power);
        }
    }

    /**
     * Sets power to the right motors.
     * Note that the right motors are facing backwards, so power is negated.
     * @param power The power to allocate to the right motors from -1 to 1.
     */
    public void setRight(double power) {
        if (controlMode == CANTalon.TalonControlMode.Speed) {
            this.right1.set(power * 500);
        } else {
            this.right1.set(-power);
        }
    }

    /**
     * Sets power to the middle motors.
     * @param power The power to allocate to the middle motor from -1 (left) to 1 (right).
     */
    public void setMiddle(double power) {
        if (controlMode == CANTalon.TalonControlMode.Speed) {
            this.middle1.set(power * 500);
        } else {
            this.middle1.set(power);
        }
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
     * Sets power to both motors simultaneously.
     * @param power The power to allocate to both motors from -1 to 1.
     */
    public void setBoth(double power) {
        setLeft(power);
        setRight(power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveWithJoystick());
    }

}
