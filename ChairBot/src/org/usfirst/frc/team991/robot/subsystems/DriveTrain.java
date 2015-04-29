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
	
	private SpeedController right_motor, left_motor;
	private RobotDrive drive;
	
	public DriveTrain() {
		super();
		left_motor = new Talon(RobotMap.leftMotor);
		right_motor = new Talon(RobotMap.rightMotor);
		drive = new RobotDrive(left_motor, right_motor);
		
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) right_motor);
	}
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new TankDriveJoystick());
    }
    
    public void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}


	public void tankDrive(Joystick leftJoy, Joystick rightJoy, boolean squared) {
		drive.tankDrive(leftJoy, rightJoy, squared);
	}
	public void stop() {
		drive.tankDrive(0, 0);
	}
}

