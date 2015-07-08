package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *	Drive Train Subsystem.
 */
public class DriveTrain extends Subsystem {
	
	private SpeedController front_right_motor, back_right_motor, front_left_motor, back_left_motor;
	private RobotDrive drive;
	private Encoder left_encoder, right_encoder;
	private final double DEADZONE, MAX_SPEED, FORWARD, MAXPERIOD, MINRATE, ROTSCALER, HARDBRAKE;
	private double current_speed, scaler, left_rate, right_rate;
	
	public DriveTrain() {
		//Constants
		DEADZONE = Robot.pref.getDouble("Deadzone", 0.1);
		MAX_SPEED = Robot.pref.getDouble("Max Speed", 2700); //Human = 2700; Cannon = 3100;
		FORWARD = Robot.pref.getDouble("Forward", 0.1);
		MAXPERIOD = Robot.pref.getDouble("Max Period", 0.1);
		MINRATE = Robot.pref.getDouble("Min Rate", 100);
		ROTSCALER = Robot.pref.getDouble("Rotation Scaler", 0.4);
		HARDBRAKE = Robot.pref.getDouble("Hand Brake", 0.2);
		
		//Initialize motor controllers
		front_left_motor = new Talon(RobotMap.frontleftMotor);
		back_left_motor = new Talon(RobotMap.backleftMotor);
		front_right_motor = new Talon(RobotMap.frontrightMotor);
		back_right_motor = new Talon(RobotMap.backrightMotor);
		
		//Initialize encoders
		left_encoder = new Encoder(RobotMap.leftencoderAChannel, RobotMap.leftencoderBChannel, true, Encoder.EncodingType.k2X);
		right_encoder = new Encoder(RobotMap.rightencoderAChannel, RobotMap.rightencoderBChannel, false, Encoder.EncodingType.k2X);
		
		left_encoder.setMaxPeriod(MAXPERIOD);
		left_encoder.setMinRate(MINRATE);
		right_encoder.setMaxPeriod(MAXPERIOD);
		right_encoder.setMinRate(MINRATE);
		
		//Initialize robot drive
		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
		
		//Invert motors
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		//LiveWindow
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) front_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) front_right_motor);
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) back_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) back_right_motor);
		
		LiveWindow.addSensor("Drive Train", "Left Encoder", left_encoder);
		LiveWindow.addSensor("Drive Train", "Right Encoder", right_encoder);
	}
	

    public void initDefaultCommand() {
    	setDefaultCommand(new ArcadeDriveJoystick());
    }
    
    public void arcadeDrive(double y, double rot) {
    	
    	left_rate = left_encoder.getRate();
    	right_rate = right_encoder.getRate();
    	
    	if(Math.abs(y) >= DEADZONE) {
    		//Calculate y-scaler
    		current_speed = (Math.abs(left_rate) + Math.abs(right_rate))/2;
    		scaler = (current_speed/MAX_SPEED) + FORWARD;
    		y *= Math.min(scaler, 1);
    		
    		//SmartDashboard update
            SmartDashboard.putNumber("Current Speed", current_speed);
            SmartDashboard.putNumber("Scaler", scaler);
            
            /*	Prevents hard brake
             *	Checks if moving and set to 0 if y is in opposite direction */
        	if(!right_encoder.getStopped() && !left_encoder.getStopped()) {
        		if(right_rate < 0 && left_rate < 0) {
        			if(y < 0){
        				y = 0;
        			}
        		} else {
        			if(y > 0) {
        				y = 0;
        			}
        		}
        	}
    	}
    	//Scale rotation
    	rot *= ROTSCALER;
    	
    	//Update drive values
		drive.arcadeDrive(y, rot, false);
		
		//SmartDashboard update
        SmartDashboard.putNumber("Right Encoder Speed", right_rate);
        SmartDashboard.putNumber("Left Encoder Speed", left_rate);
	}
    
    public void hardBrake() {
    	
    	//Get speed of encoders
    	left_rate = left_encoder.getRate();
    	right_rate = right_encoder.getRate();
    	
    	//Feeds opposite value to brake
    	if(!left_encoder.getStopped() && !right_encoder.getStopped()) {
    		if (left_rate < 0 && right_rate < 0) {
    			drive.tankDrive(-HARDBRAKE, -HARDBRAKE);
    		} else {
    			drive.tankDrive(HARDBRAKE, HARDBRAKE);
    		}
    	}
    }
    
	public void stop() {
		drive.tankDrive(0, 0);
	}
}

