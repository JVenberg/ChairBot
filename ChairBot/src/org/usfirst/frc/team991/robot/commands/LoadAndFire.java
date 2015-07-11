package org.usfirst.frc.team991.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *	Command group to load and fire the shooter.
 */
public class LoadAndFire extends CommandGroup {
	
	public  LoadAndFire() {
		addSequential(new LoadShooter());
		addSequential(new WaitCommand(2));
		addSequential(new FireShooter());
	}
}
