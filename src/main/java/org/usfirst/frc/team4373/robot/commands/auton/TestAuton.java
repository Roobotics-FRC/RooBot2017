package org.usfirst.frc.team4373.robot.commands.auton;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * Test mode.
 * @author Henry Pitcairn
 */
public class TestAuton extends Command {
    private static TestAuton testAuton = null;

    public static TestAuton getTestAuton() {
        if (testAuton == null) testAuton = new TestAuton();
        return testAuton;
    }

    private DriveTrain driveTrain;

    private TestAuton() {
        super();
    }

    @Override
    protected void initialize() {
        driveTrain = DriveTrain.getDriveTrain();
    }

    @Override
    protected void execute() {
        driveTrain.changeControlMode(CANTalon.TalonControlMode.Position);
        driveTrain.setLeft(13 * 4096);
    }

    @Override
    protected boolean isFinished() {
        return false;
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
