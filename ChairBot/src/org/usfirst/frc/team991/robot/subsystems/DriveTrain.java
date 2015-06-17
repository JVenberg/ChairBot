package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.TankDriveJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class DriveTrain extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private SpeedController front_right_motor, back_right_motor, front_left_motor, back_left_motor;
	private RobotDrive drive;
	
	public DriveTrain() {
		super();
		front_left_motor = new Talon(RobotMap.frontleftMotor);
		back_left_motor = new Talon(RobotMap.backleftMotor);
		front_right_motor = new Talon(RobotMap.frontrightMotor);
		back_right_motor = new Talon(RobotMap.backrightMotor);
		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
		
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) front_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) front_right_motor);
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) back_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) back_right_motor);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TankDriveJoystick());
    }
    
    public void tankDrive(double y, double rot) {
    	if(y <= 0) {
        	y = y * .45;
    	} else {
    		y = y * .75;
    	}
    	rot = rot * .6;
		drive.arcadeDrive(y, rot, true);
	}


//	public void tankDrive(Joystick leftJoy, Joystick rightJoy, boolean squared) {
//		drive.tankDrive(leftJoy, rightJoy, squared);
//	}
	public void stop() {
		drive.tankDrive(0, 0);
	}
}

