package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Shooter extends Subsystem {
    
	Solenoid loader;
	Solenoid trigger;
	
	public Shooter() {
		loader = new Solenoid(RobotMap.loadersolenoid);
		trigger = new Solenoid(RobotMap.triggersolenoid);
		
		LiveWindow.addActuator("Shooter", "Loader", loader);
		LiveWindow.addActuator("Shooter", "Trigger", trigger);
	}
	
	public void loadShooterOn() {
		loader.set(true);
	}
	
	public void loadShooterOff() {
		loader.set(false);
	}
	
	public void triggerOn() {
		trigger.set(true);
	}
	
	public void triggerOff() {
		trigger.set(false);
	}

    public void initDefaultCommand() {}
}

