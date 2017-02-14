package org.usfirst.frc.team4373.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team4373.robot.RobotMap;
import org.usfirst.frc.team4373.robot.commands.teleop.ShooterCommand;

/**
 * Programmatic representation of physical shooter components.
 * @author aaplmath
 */
public class Shooter extends Subsystem {
    private CANTalon shooterTalon;

    private static Shooter shooter = null;

    public static Shooter getShooter() {
        shooter = shooter == null ? new Shooter() : shooter;
        return shooter;
    }

    private Shooter() {
        this.shooterTalon = new CANTalon(RobotMap.SHOOTER_MOTOR);
    }

    /**
     * Set power to the shooting motor
     * @param power The power, between 0 and 1, to supply to the motor.
     */
    public void setPower(double power) {
        this.shooterTalon.set(power);
    }

    /**
     * Stop the shooting motor.
     */
    public void stop() {
        this.shooterTalon.set(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ShooterCommand());
    }
}
