package org.usfirst.frc.team991.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

/** Extends Trigger to add multi-button support by overwriting get() method.
 * Triggers when both buttons are pressed.
 * @author Jack Venberg
 */
public class DoubleButton extends Trigger {
	private Joystick joy;
	private int button1, button2;
	
	/** Constructs double button.
	 * @param joy		Source joystick
	 * @param button1	First button
	 * @param button2	Second button
	 */
	public DoubleButton(Joystick joy, int button1, int button2) {
		this.joy = joy;
		this.button1 = button1;
		this.button2 = button2;
	}	
	
	/** Overrides get() to allow for two buttons 
	 * @see edu.wpi.first.wpilibj.buttons.Trigger#get()
	 */
	public boolean get() {
		return joy.getRawButton(button1) && joy.getRawButton(button2);
	}
}