package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *	Shooter Subsystem.
 */
public class Shooter extends Subsystem {
    
	//Initializing solenoids
	Solenoid loader;
	Solenoid trigger;
	
	public Shooter() {
		loader = new Solenoid(RobotMap.loadersolenoid);
		trigger = new Solenoid(RobotMap.triggersolenoid);
		
		LiveWindow.addActuator("Shooter", "Loader", loader);
		LiveWindow.addActuator("Shooter", "Trigger", trigger);
	}
	
	//Loader controls
	public void loadShooterOn() {
		loader.set(true);
	}
	
	public void loadShooterOff() {
		loader.set(false);
	}
	
	//Trigger controls
	public void triggerOn() {
		trigger.set(true);
	}
	
	public void triggerOff() {
		trigger.set(false);
	}

    public void initDefaultCommand() {}
}

