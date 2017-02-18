package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Created by jrr6 on 2/18/17.
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
        SmartDashboard.putNumber("Encoder Position", driveTrain.getTestEncoder());
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
