package org.usfirst.frc.team4373.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.commands.auton.PositionBasedGearAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TestAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedAuton;
import org.usfirst.frc.team4373.robot.commands.auton.TimeBasedGearAuton;
//import org.usfirst.frc.team4373.robot.commands.teleop.DriveWithJoystick;
import org.usfirst.frc.team4373.robot.subsystems.Climber;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * This is the main robot class.
 */
public class Robot extends IterativeRobot {
    private Command autonCommand = null;
    private SendableChooser autonChooser = null;

    @Override
    public void robotInit() {
        SmartDashboard.putNumber("Auton Time:", 4);
        SmartDashboard.putNumber("Auton Revolutions:", 10);
        SmartDashboard.putNumber("Auton Speed:", 0.5);

        autonChooser = new SendableChooser();
        autonChooser.addObject("Disabled", "disabled");
        autonChooser.addDefault("DriveStraight", "driveStraight");
        autonChooser.addObject("DriveRevolutions", "driveRevolutions");
        autonChooser.addObject("RudimentaryGear", "rudimentaryGear");
        autonChooser.addObject("Test", "test");
        SmartDashboard.putData("Auton Mode Selector", autonChooser);

        // OI.getOI().getGyro().calibrate();
        DriveTrain.getDriveTrain();
        Climber.getClimber();
        GearRelease.getGearRelease();
    }

    @Override
    public void teleopInit() {
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        OI.getOI().getGyro().reset();
        super.teleopInit();
    }

    @Override
    public void autonomousInit() {
        //DriveWithJoystick.getDriveWithJoystick().cancel();
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        String command = (String) autonChooser.getSelected();
        int autonValueKey = (int) SmartDashboard.getNumber("Auton Time:",
                RobotMap.TIME_BASED_AUTON_DEFAULT_SECONDS);
        int autonRevsKey = (int) SmartDashboard.getNumber("Auton Revolutions:",
                RobotMap.POSITION_BASED_AUTON_DEFAULT_REVOLUTIONS);
        double motorValue = SmartDashboard.getNumber("Auton Speed:",
                RobotMap.TIME_BASED_AUTON_MOTOR_VALUE);
        switch (command) {
            case "driveStraight":
                autonCommand = TimeBasedAuton.getTimeBasedAuton(autonValueKey, motorValue);
                break;
            case "rudimentaryGear":
                autonCommand = TimeBasedGearAuton.getTimeBasedGearAuton(autonValueKey, motorValue);
                break;
            case "driveRevolutions":
                autonCommand = PositionBasedGearAuton.getPositionBasedGearAUton(
                        autonRevsKey, motorValue);
                break;
            case "test":
                autonCommand = TestAuton.getTestAuton();
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
        //DriveWithJoystick.getDriveWithJoystick().cancel();
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic() {
        if (autonCommand != null) {
            autonCommand.cancel();
        }
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Gyro", OI.getOI().getGyro().getAngle());
    }

    public String toString() {
        return "Main robot class";
    }
}
// "A radioactive cat has eighteen half-lives."
// A friend is a present you give yourself.
		-- Robert Louis Stevenson
// Good day to avoid cops.  Crawl to work.
// Is not marriage an open question, when it is alleged, from the
beginning of the world, that such as are in the institution wish to get
out, and such as are out wish to get in?
		-- Ralph Emerson
// I only know what I read in the papers.
		-- Will Rogers
// All we know is the phenomenon: we spend our time sending messages to each
other, talking and trying to listen at the same time, exchanging information.
This seems to be our most urgent biological function; it is what we do with
our lives."
		-- Lewis Thomas, "The Lives of a Cell"
// Two peanuts were walking through the New York.  One was assaulted.
// The true way goes over a rope which is not stretched at any great height
but just above the ground.  It seems more designed to make people stumble
than to be walked upon.
		-- Franz Kafka
// People who are funny and smart and return phone calls get much better
press than people who are just funny and smart.
		-- Howard Simons, "The Washington Post"
// No problem is insoluble in all conceivable circumstances.
// I think my career is ruined!
// Q:	Why did the astrophysicist order three hamburgers?
A:	Because he was hungry.
// If you have a procedure with 10 parameters, you probably missed some.
// People are beginning to notice you.  Try dressing before you leave the house.
// Counting in octal is just like counting in decimal--if you don't use your thumbs.
		-- Tom Lehrer
// All that glitters has a high refractive index.
// QOTD:
	"I used to go to UCLA, but then my Dad got a job."
// A neighbor came to Nasrudin, asking to borrow his donkey.  "It is out on
loan," the teacher replied.  At that moment, the donkey brayed loudly inside
the stable.  "But I can hear it bray, over there."  "Whom do you believe,"
asked Nasrudin, "me or a donkey?"
// You may call me by my name, Wirth, or by my value, Worth.
- Nicklaus Wirth
// I know you believe you understand what you think this fortune says, but
I'm not sure you realize that what you are reading is not what it means.
