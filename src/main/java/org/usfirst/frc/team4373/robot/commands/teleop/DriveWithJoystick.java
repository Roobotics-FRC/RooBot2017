package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.input.hid.RooJoystick;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

import java.util.concurrent.atomic.AtomicBoolean;

enum Direction {
    FORWARD, BACKWARD, RIGHT, LEFT
}

/**
 * This command handles operator control of the drive train subsystem.
 * It sets outputs based on joystick axes.
 * @author Henry Pitcairn
 * @author aaplmath
 */
public class DriveWithJoystick extends PIDCommand {
    private static double kP = 0.1000d;
    private static double kI = 0.0001d;
    private static double kD = 0.0000d;

    private Direction forwardDirection;

    private DriveTrain driveTrain;
    private RooJoystick joystick;
    private double pidOutput;
    // Whether robot is cooling down from a turn and should not immediately readjust
    private final AtomicBoolean cooldown;
    private long cooldownEndTime;

    /**
     * Constructor for DriveWithJoystick.
     */
    public DriveWithJoystick() {
        super("DriveWithJoystick", kP, kI, kD);
        requires(DriveTrain.getDriveTrain());
        driveTrain = DriveTrain.getDriveTrain();
        joystick = OI.getOI().getDriveJoystick();
        cooldown = new AtomicBoolean(false);
    }

    @Override
    protected void execute() {
        if (System.currentTimeMillis() >= cooldownEndTime) {
            cooldown.set(false);
        } else {
            OI.getOI().getGyro().reset();
        }
        if (OI.getOI().getDriveJoystick().getRawButton(1)) {
            switch (OI.getOI().getDriveJoystick().getPOV()) {
                case 0:
                    forwardDirection = Direction.FORWARD;
                    break;
                case 90:
                    forwardDirection = Direction.RIGHT;
                    break;
                case 180:
                    forwardDirection = Direction.BACKWARD;
                    break;
                case 270:
                    forwardDirection = Direction.LEFT;
                    break;
            }
        }

        // Turn more slowly
        double twistAxis = this.joystick.getAxis(RobotMap.JOYSTICK_TWIST_AXIS) / 2;
        double horizontalAxis = this.joystick.getAxis(RobotMap.JOYSTICK_HORIZONTAL_AXIS);
        double forwardAxis = -this.joystick.getAxis(RobotMap.JOYSTICK_FORWARD_AXIS);

        switch (forwardDirection) {
            case BACKWARD:
                forwardAxis = -forwardAxis;
                horizontalAxis = -horizontalAxis;
                twistAxis = -twistAxis;
                break;
            case LEFT:
                twistAxis = -twistAxis;
                // fall through because left and right need to switch horizontal and vertical
            case RIGHT:
                double temp = forwardAxis;
                forwardAxis = horizontalAxis;
                horizontalAxis = temp;
                break;
        }

        if (twistAxis == 0 && forwardAxis != 0) { // Just forward
            double right = forwardAxis;
            double left = forwardAxis;
            if (pidOutput > 0) {
                right -= pidOutput;
            } else {
                left -= Math.abs(pidOutput);
            }
            driveTrain.setLeft(left);
            driveTrain.setRight(right);
        } else if (twistAxis != 0 && forwardAxis != 0) { // Twist and forward
            OI.getOI().getGyro().reset();
            double right = forwardAxis;
            double left = forwardAxis;
            if (twistAxis > 0) {
                right -= twistAxis;
            } else if (twistAxis < 0) {
                left -= Math.abs(twistAxis);
            }
            driveTrain.setRight(right);
            driveTrain.setLeft(left);
        } else if (twistAxis != 0 && forwardAxis == 0) { // Just twist
            OI.getOI().getGyro().reset();
            driveTrain.setRight(-twistAxis);
            driveTrain.setLeft(twistAxis);
            cooldown.set(true);
            cooldownEndTime = System.currentTimeMillis() + 500;

        } else { // Hold straight
            driveTrain.setRight(-pidOutput);
            driveTrain.setLeft(pidOutput);
        }
        driveTrain.setMiddle(horizontalAxis);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(0);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-1, 1);

        this.getPIDController().setPID(kP, kI, kD);

        cooldown.set(false);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
        cooldown.set(false);
    }

    @Override
    protected void interrupted() {
        this.getPIDController().reset();
        this.driveTrain.setBoth(0);
        cooldown.set(false);
    }

    @Override
    protected double returnPIDInput() {
        return OI.getOI().getAngleRelative();
    }

    @Override
    protected void usePIDOutput(double pidOutput) {
        if (cooldown.get()) {
            this.pidOutput = 0;
        } else {
            this.pidOutput = pidOutput;
            SmartDashboard.putNumber("PID Output", this.pidOutput);
        }
    }
}
