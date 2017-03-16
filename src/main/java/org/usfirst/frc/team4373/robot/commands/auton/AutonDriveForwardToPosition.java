package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Drives forward autonomously at a specified speed for a specified number of motor rotations.
 * @author aaplmath
 */
public class AutonDriveForwardToPosition extends Command {
    private DriveTrain driveTrain;
    private int targetRevolutions;
    private boolean isFinished = false;
    private double motorValue;
    private double revsStart;

    private static AutonDriveForwardToPosition autonDriveForwardToPosition = null;

    /**
     * Get instance of AutonDriveForwardToPosition.
     * @param revolutions The number of revolutions to turn.
     * @param motorValue The voltage to supply to the motors.
     * @return The singleton instance of AutonDriveForwardToPosition.
     */
    public static AutonDriveForwardToPosition getAutonDriveForwardToPosition(int revolutions,
                                                                             double motorValue) {
        if (autonDriveForwardToPosition == null) {
            autonDriveForwardToPosition = new AutonDriveForwardToPosition(revolutions, motorValue);
        } else {
            autonDriveForwardToPosition.setTargetRevolutions(revolutions);
            autonDriveForwardToPosition.setMotorValue(motorValue);
            autonDriveForwardToPosition.revsStart =
                    DriveTrain.getDriveTrain().getLeftEncoderPosition();
        }
        return autonDriveForwardToPosition;
    }

    private AutonDriveForwardToPosition(int revolutions, double motorValue) {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
        this.targetRevolutions = revolutions;
        this.motorValue = motorValue;
    }

    public void setTargetRevolutions(int revolutions) {
        this.targetRevolutions = revolutions;
    }

    public void setMotorValue(double motorValue) {
        this.motorValue = motorValue;
    }

    @Override
    protected void initialize() {
        revsStart = driveTrain.getLeftEncoderPosition();
    }

    @Override
    protected void execute() {
        if (this.revsStart == -1) {
            revsStart = driveTrain.getLeftEncoderPosition();
        }
        int leftTurns = driveTrain.getLeftEncoderPosition();
        SmartDashboard.putNumber("Revolutions remaining",
                targetRevolutions - leftTurns);
        if (leftTurns <= targetRevolutions) {
            driveTrain.setBoth(this.motorValue);
        } else {
            driveTrain.setBoth(0.0d);
            isFinished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.isFinished;
    }

    @Override
    protected void end() {
        revsStart = -1;
        driveTrain.setBoth(0.0d);
    }

    @Override
    protected void interrupted() {
        revsStart = -1;
        driveTrain.setBoth(0d);
    }
}
