package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.TankDriveJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private SpeedController front_right_motor, back_right_motor, front_left_motor, back_left_motor;
	private RobotDrive drive;
	private Encoder left_encoder, right_encoder;
	private final double DEADZONE, MAX_SPEED, FORWARD;
	
	public DriveTrain() {
		super();
		DEADZONE = 0.1;
		MAX_SPEED = 5;
		FORWARD = .1;
		
		front_left_motor = new Talon(RobotMap.frontleftMotor);
		back_left_motor = new Talon(RobotMap.backleftMotor);
		front_right_motor = new Talon(RobotMap.frontrightMotor);
		back_right_motor = new Talon(RobotMap.backrightMotor);
		
		left_encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
		right_encoder = new Encoder(2, 4, false, Encoder.EncodingType.k4X);
		
		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
		
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) front_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) front_right_motor);
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) back_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) back_right_motor);
		
		LiveWindow.addSensor("Drive Train", "Left Encoder", left_encoder);
		LiveWindow.addSensor("Drive Train", "Right Encoder", right_encoder);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TankDriveJoystick());
    }
    
    public void arcadeDrive(double y, double rot) {
    	if(Math.abs(y) >= DEADZONE) {
    		double current_speed = (Math.abs(left_encoder.getRate()) + Math.abs(right_encoder.getRate()))/2;
    		double scaler = (current_speed/MAX_SPEED) + FORWARD;
    		y *= Math.min(scaler, 1);
            SmartDashboard.putNumber("Current Speed", current_speed);
            SmartDashboard.putNumber("Scaler", scaler);
    	}
    	rot *= .6;
		drive.arcadeDrive(y, rot, true);
	}

//	public void tankDrive(Joystick leftJoy, Joystick rightJoy, boolean squared) {
//		drive.tankDrive(leftJoy, rightJoy, squared);
//	}
    
	public void stop() {
		drive.tankDrive(0, 0);
	}
}

