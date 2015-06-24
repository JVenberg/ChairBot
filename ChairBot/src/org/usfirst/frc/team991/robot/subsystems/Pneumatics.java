package org.usfirst.frc.team991.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Pneumatics extends Subsystem {
	Compressor compressor;

    public void initDefaultCommand() {}

	public Pneumatics() {
		compressor = new Compressor();
	}

	/**
	 * Start the compressor going. The compressor automatically starts and stops as it goes above and below maximum pressure.
	 */
	public void start() {
		compressor.start();
	}
}

