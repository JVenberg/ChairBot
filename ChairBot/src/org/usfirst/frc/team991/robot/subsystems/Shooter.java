package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/** Shooter Subsystem.
 * Defines the capabilities of T-shirt cannon shooter.
 * @author Jack Venberg
 */
public class Shooter extends Subsystem {
	Solenoid loader;
	Solenoid trigger;
	
	/** Constructs shooter subsystem.
	 * Initializes loader and trigger solenoids
	 * and adds them to LiveWindow.
	 */
	public Shooter() {
		loader = new Solenoid(RobotMap.loadersolenoid);
		trigger = new Solenoid(RobotMap.triggersolenoid);
		
		LiveWindow.addActuator("Shooter", "Loader", loader);
		LiveWindow.addActuator("Shooter", "Trigger", trigger);
	}
	
	/** Opens up loader solenoid to start
	 * filling chamber with air.
	 */
	public void loadShooterOn() {
		loader.set(true);
	}
	
	/** Closes loader solenoid to stop
	 * filling chamber with air.
	 */
	public void loadShooterOff() {
		loader.set(false);
	}
	
	/** Opens up trigger solenoid to
	 * fire T-shirt cannon.
	 */
	public void triggerOn() {
		trigger.set(true);
	}
	
	/** Closes trigger solenoid to
	 * stop firing T-shirt cannon.
	 */
	public void triggerOff() {
		trigger.set(false);
	}
	
	/** Initialize the default command for a subsystem
	 * By default subsystems have no default command, but if they do,
	 * the default command is set with this method.
	 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
	 */
	public void initDefaultCommand() {}
}

