package org.usfirst.frc.team4373.robot.commands.auton;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Pure position based auton
 * @author Henry Pitcairn
 * @author Alex Fang
 */
public class PositionBasedAuton extends Command {
    private double rotations;
    private DriveTrain driveTrain;
    private boolean ended;

    public PositionBasedAuton(double rotations) {
        this.rotations = rotations;
        this.driveTrain = DriveTrain.getDriveTrain();
    }

    public double getRotations() {
        return rotations;
    }

    public void setRotations(double rotations) {
        this.rotations = rotations;
    }

    @Override
    protected void initialize() {
        driveTrain.getLeft1().setEncPosition(-(int)(rotations * driveTrain.getLeftEncoderCPR()));
        driveTrain.getRight1().setEncPosition(-(int)(rotations * driveTrain.getRightEncoderCPR()));
        driveTrain.getMiddle1().setEncPosition(0);
        driveTrain.changeControlMode(CANTalon.TalonControlMode.Position);
        driveTrain.setBoth(0);
    }

    @Override
    protected void execute() {
        if (driveTrain.getLeftEncoderTurns() == 0) {
            ended = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return ended;
    }

    @Override
    protected void end() {
        driveTrain.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }

    @Override
    protected void interrupted() {
        driveTrain.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }
}
