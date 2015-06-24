package org.usfirst.frc.team991.robot;

import org.usfirst.frc.team991.robot.commands.FireShooter;
import org.usfirst.frc.team991.robot.commands.LoadAndFire;
import org.usfirst.frc.team991.robot.commands.LoadShooter;
import org.usfirst.frc.team991.robot.triggers.DoubleButton;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick joystick_0 = new Joystick(0);
	private Joystick joystick_1 = new Joystick(1);
	
	public OI() {
		new DoubleButton(joystick_0, 1, 2).whenActive(new LoadAndFire());

		// SmartDashboard Buttons
		SmartDashboard.putData("Load And Fire", new LoadAndFire());
		SmartDashboard.putData("Load", new LoadShooter());
		SmartDashboard.putData("Fire", new FireShooter());
	}
	
	public Joystick getLeftJoy() {
		return joystick_0;
	}
	
	public Joystick getRightJoy() {
		return joystick_1;
	}
}

