package org.usfirst.frc.team4373.robot.input.did;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.USBCamera;
import org.usfirst.frc.team4373.robot.RobotMap;

/**
 * Created by derros on 3/4/17.
 * @author RJ Alexander Fang
 */
public class USBCameraStream implements Runnable {

    private String name;
    private CameraServer server;
    private static USBCameraStream stream;

    // default: cam0
    private USBCameraStream(String name) {
        this.name = name;
        server = CameraServer.getInstance();
    }

    public static USBCameraStream getUSBCameraStream() {
        if (stream == null) {
            stream = new USBCameraStream(RobotMap.CAMERA_NAME);
        }

        return stream;
    }

    public void run() {
        server.setQuality(100);
        server.startAutomaticCapture();
    }

    public void finalize() {
        server = null;
        name = null;
        System.gc();
    }
}
