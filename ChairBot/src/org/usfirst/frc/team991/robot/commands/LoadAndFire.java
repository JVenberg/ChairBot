package org.usfirst.frc.team991.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class LoadAndFire extends CommandGroup {
    
    public  LoadAndFire() {
    	addSequential(new LoadShooter());
    	Timer.delay(0.5);
    	addSequential(new FireShooter());
    	
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
