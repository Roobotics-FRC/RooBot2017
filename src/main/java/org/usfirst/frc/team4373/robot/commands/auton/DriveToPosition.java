package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Created by jrr6 on 2/17/17.
 */
public class DriveToPosition extends Command {
    private DriveTrain driveTrain;

    public DriveToPosition() {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {

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
