package org.usfirst.frc.team4373.robot.commands.auton;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Autonomously drives the robot to a position using position closed loop.
 * @author aaplmath
 */
public class AutonDriveForward extends Command {
    private DriveTrain driveTrain;

    public AutonDriveForward() {
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
