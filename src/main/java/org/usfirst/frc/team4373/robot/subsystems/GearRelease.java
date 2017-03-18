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
    private DoubleSolenoid solenoid1;
    private DoubleSolenoid solenoid2;
    private Compressor compressor;

    private GearRelease() {
        super("GearRelease");
        this.solenoid1 = new DoubleSolenoid(
                RobotMap.PCM_PORT,
                RobotMap.BACKWARD_SOLENOID_PORT,
                RobotMap.FORWARD_SOLENOID_PORT
        );
        this.solenoid2 = new DoubleSolenoid(
                RobotMap.PCM_PORT,
                RobotMap.PUSHER_SOLENOID_PORT
        );
        this.compressor = new Compressor(0);
    }

    public static GearRelease getGearRelease() {
        gearRelease = gearRelease == null ? new GearRelease() : gearRelease;
        return gearRelease;
    }

    private void setBoth(DoubleSolenoid.Value value) {
        this.solenoid1.set(value);
    }

    public void activate() {
        setBoth(DoubleSolenoid.Value.kForward);
    }

    public void deactivate() {
        setBoth(DoubleSolenoid.Value.kReverse);
    }

    public void setNeutral() {
        setBoth(DoubleSolenoid.Value.kOff);
    }

    public void startCompressor() {
        this.compressor.start();
    }

    public void stopCompressor() {
        this.compressor.stop();
    }

    public void setPusherSolenoid(DoubleSolenoid.Value value) {
        this.solenoid2.set(value);
    }

    public void activatePusherSolenoid() {
        this.setPusherSolenoid(DoubleSolenoid.Value.kForward);
    }

    public void deactivatePusherSolenoid() {
        this.setPusherSolenoid(DoubleSolenoid.Value.kReverse);
    }

    public void setPusherSolenoidNeutral() {
        this.setPusherSolenoid(DoubleSolenoid.Value.kOff);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new GearReleaseCommand());
    }
}
