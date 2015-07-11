package org.usfirst.frc.team991.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/** Pneumatic Subsystem.
 * Defines the capabilities of pneumatics on robot.
 * @author Jack Venberg
 */
public class Pneumatics extends Subsystem {
	Compressor compressor;

	/** Constructs pneumatic subsystem and
	 * initializes compressor.
	 */
	public Pneumatics() {
		compressor = new Compressor();
	}

	/** Starts the compressor going.
	 * The compressor automatically starts and stops
	 * as it goes above and below maximum pressure.
	 */
	public void start() {
		compressor.start();
	}
	
	/** Initialize the default command for a subsystem
	 * By default subsystems have no default command, but if they do,
	 * the default command is set with this method.
	 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
	 */
	public void initDefaultCommand() {}
}

