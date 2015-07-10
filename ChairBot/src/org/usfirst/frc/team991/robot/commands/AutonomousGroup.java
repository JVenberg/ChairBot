package org.usfirst.frc.team991.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *	Drive forward for a certain amount of time.
 */
public class AutonomousGroup extends CommandGroup {
    
    public  AutonomousGroup() {
    	addSequential(new DriveStraight(.5, 24, 2));
    }
}
