package org.usfirst.frc.team991.robot;
/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Drive Train
    public static int backleftMotor = 0;
    public static int frontleftMotor = 1;
    public static int backrightMotor = 2;
    public static int frontrightMotor = 3;
    
    public static int leftencoderAChannel = 1;
    public static int leftencoderBChannel = 0;
    public static int rightencoderAChannel = 3;
    public static int rightencoderBChannel = 2;
    
    // Shooter
    public static int loadersolenoid = 0;
    public static int triggersolenoid = 1;
}
