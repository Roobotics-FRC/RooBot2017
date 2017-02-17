package org.usfirst.frc.team4373.robot.commands.auton;

import com.ctre.CANTalon;
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
        driveTrain.switchControlMode(CANTalon.TalonControlMode.Position);
    }

    @Override
    protected void execute() {
        driveTrain.setBoth(5);
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
