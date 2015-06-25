package org.usfirst.frc.team991.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	Command group to load and fire the shooter.
 */
public class LoadAndFire extends CommandGroup {
    
    public  LoadAndFire() {
    	addSequential(new LoadShooter());
    	Timer.delay(0.5);
    	addSequential(new FireShooter());
    }
}
