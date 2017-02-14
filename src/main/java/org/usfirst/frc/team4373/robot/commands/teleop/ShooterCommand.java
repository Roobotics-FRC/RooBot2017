package org.usfirst.frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.OI;
import org.usfirst.frc.team4373.robot.subsystems.Shooter;

/**
 * Created by jrr6 on 2/13/17.
 */
public class ShooterCommand extends Command {

    private Shooter shooter;

    public ShooterCommand() {
        super("ShooterCommand");
        requires(shooter = Shooter.getShooter());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        double rawAxis = OI.getOI().getDriveJoystick().getRawAxis(3);
        double power = -rawAxis / 2 + 0.5;
        shooter.setPower(power);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        shooter.stop();
    }

    @Override
    protected void interrupted() {
        shooter.stop();
    }
}
