package org.usfirst.frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team4373.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4373.robot.subsystems.GearRelease;

/**
 * Autonomously deposits a gear using hard-coded time values.
 * @author Henry Pitcairn
 * @author aaplmath
 */
public class PositionBasedGearAuton extends Command {
    private static final int TO_MILLISECONDS = 1000;
    private DriveTrain driveTrain;
    private GearRelease gearRelease;
    private double moveForwardTurns;
    private double moveBackwardTurns;
    private boolean isFinished = false;
    private double motorValue;
    private double positionStart;
    private State state;

    enum State {
        WAITING,
        MOVING_TOWARD_PEG,
        MOVING_AWAY_FROM_PEG
    }

    private static PositionBasedGearAuton positionBasedGearAuton = null;

    /**
     * Gets the current TimeBasedGearAuton instance with the specified parameters.
     * @param time The amount of time the motor should run.
     * @param motorValue The speed (0-1) at which the motor should run.
     */
    public static PositionBasedGearAuton getPositionBasedGearAUton(int time, double motorValue) {
        if (positionBasedGearAuton == null) {
            positionBasedGearAuton = new PositionBasedGearAuton(time, motorValue);
        } else {
            positionBasedGearAuton.setMotorValue(motorValue);
        }
        return positionBasedGearAuton;
    }

    private PositionBasedGearAuton(int time, double motorValue) {
        super();
        requires(driveTrain = DriveTrain.getDriveTrain());
        requires(gearRelease = GearRelease.getGearRelease());
        this.moveForwardTurns = SmartDashboard.getNumber("Move forward turns", 4);
        this.moveBackwardTurns = 4;
        this.motorValue = motorValue;
        this.state = State.WAITING;
    }

    public void setMotorValue(double motorValue) {
        this.motorValue = motorValue;
    }

    @Override
    protected void initialize() {
        positionStart = 0;
        state = State.WAITING;
    }

    @Override
    protected void execute() {
        double turns = Math.abs(driveTrain.getLeftEncoderTurns());
        SmartDashboard.putNumber("Turns",
                turns - positionStart);
        switch (state) {
            case WAITING:
                positionStart = driveTrain.getLeftEncoderTurns();
                state = State.MOVING_TOWARD_PEG;
                break;
            case MOVING_TOWARD_PEG:
                if (positionStart == 0) positionStart = turns;
                if (turns - positionStart <= moveForwardTurns) {
                    driveTrain.setBoth(-this.motorValue);
                } else {
                    driveTrain.setBoth(0.0d);
                    positionStart = turns;
                    state = State.MOVING_AWAY_FROM_PEG;
                }
                break;
            case MOVING_AWAY_FROM_PEG:
                if (turns - positionStart <= moveBackwardTurns) {
                    driveTrain.setBoth(0.25);
                } else {
                    driveTrain.setBoth(0.0d);
                    gearRelease.deactivate();
                    positionStart = 0;
                    state = State.WAITING;
                    isFinished = true;  
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.isFinished;
    }

    @Override
    protected void end() {
        positionStart = 0;
        driveTrain.setBoth(0.0d);
        state = State.WAITING;
    }

    @Override
    protected void interrupted() {
        // shouldn't be
        positionStart = 0;
        driveTrain.setBoth(0d);
        state = State.WAITING;
    }
}
