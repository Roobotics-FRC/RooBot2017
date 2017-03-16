package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * @author (tesla)
 * Created on 3/16/17
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
        driveTrain.setBoth(3);
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
