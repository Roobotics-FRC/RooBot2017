package org.usfirst.frc.team4373.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.GearReleaseCommand;

/**
 * A programmatic representation of physical gear release components.
 *
 * @author aaplmath
 * @author Henry Pitcairn
 */
public class GearRelease extends Subsystem {

    private static GearRelease gearRelease = null;
    private PneumaticsControl solenoids;
    private Compressor compressor;

    private GearRelease() {
        super("GearRelease");
        this.solenoids = PneumaticsControl.getPneumaticsControl();
        this.compressor = new Compressor(0);
    }

    public static GearRelease getGearRelease() {
        gearRelease = gearRelease == null ? new GearRelease() : gearRelease;
        return gearRelease;
    }

    public PneumaticsControl getSolenoids() {
        return this.solenoids;
    }

    public void startCompressor() {
        this.compressor.start();
    }

    public void stopCompressor() {
        this.compressor.stop();
    }


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new GearReleaseCommand());
    }
}
