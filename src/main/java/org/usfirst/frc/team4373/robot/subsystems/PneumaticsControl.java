package org.usfirst.frc.team4373.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import org.usfirst.frc.team4373.robot.RobotMap;

import java.util.HashMap;

/**
 * Created by derros on 3/18/17.
 */
public class PneumaticsControl {

    private static PneumaticsControl controller = null;

    private HashMap<Integer, DoubleSolenoid> solenoidControl;

    private PneumaticsControl(int pcmPort, int[] solenoidPorts) {
        solenoidControl = new HashMap<Integer, DoubleSolenoid>(5);
        for(int port : solenoidPorts) {
            solenoidControl.put(port, (new DoubleSolenoid(pcmPort, port)));
        }
    }

    public static PneumaticsControl getPneumaticsControl() {
        if(controller == null) {
            controller = new PneumaticsControl(RobotMap.PCM_PORT, RobotMap.SOLENOID_PORTS);
        }
        return controller;
    }

    public void setAll(DoubleSolenoid.Value value) {
        this.solenoidControl.forEach((k, v) -> {v.set(value);});
    }

    public void setSpecific(int solenoidPort, DoubleSolenoid.Value value) {
        try {
            solenoidControl.get(solenoidPort).set(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activate() {
        this.setAll(DoubleSolenoid.Value.kForward);
    }

    public void activate(int solenoidPort) {
        this.setSpecific(solenoidPort, DoubleSolenoid.Value.kForward);
    }

    public void deactivate() {
        this.setAll(DoubleSolenoid.Value.kReverse);
    }

    public void deactivate(int solenoidPort) {
        this.setSpecific(solenoidPort, DoubleSolenoid.Value.kReverse);
    }

    public void setNeutral() {
        this.setAll(DoubleSolenoid.Value.kOff);
    }

    public void setNeutral(int solenoidPort) {
        this.setSpecific(solenoidPort, DoubleSolenoid.Value.kOff);
    }


}
