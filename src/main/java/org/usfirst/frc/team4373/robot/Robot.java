package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.AutonDriveToPosition;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {

    @Override
    public void robotInit() {
        SmartDashboard.putNumber("kP", 0.0d);
        SmartDashboard.putNumber("kI", 0.0d);
        SmartDashboard.putNumber("kD", 0.0d);

        OI.getOI().getGyro().calibrate();
        DriveTrain.getDriveTrain();
    }

    @Override
    public void teleopInit() {
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    private Command autonCommand = null;
    private SendableChooser autonChooser;

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        OI.getOI().getGyro().reset();
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        String command = (String) autonChooser.getSelected();
        switch (command) {
            case "driveToPosition":
                autonCommand = new AutonDriveToPosition();
                break;
            default:
                autonCommand = null;
        }
        if (autonCommand != null) {
            autonCommand.start();
        }
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public  void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro", OI.getOI().getGyro().getAngle());
    }
    
    public String toString() {
        return "Main robot class";
    }
}
