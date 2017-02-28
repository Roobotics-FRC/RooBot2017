package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Tests encoders.
 * @author aaplmath
 */
public class TestEncoderCommand extends Command {
    private DriveTrain driveTrain;

    public TestEncoderCommand() {
        requires(this.driveTrain = DriveTrain.getDriveTrain());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        int[] encVelocities = this.driveTrain.getTestEncoders();
        for (int i = 0; i < encVelocities.length; i++) {
            SmartDashboard.putNumber("Encoder Velocity " + i, encVelocities[i]);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}
