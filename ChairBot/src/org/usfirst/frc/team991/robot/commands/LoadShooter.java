package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Command to load the shooter.
 */
public class LoadShooter extends Command {

    public LoadShooter() {
    	requires(Robot.pneumatics);
    	setTimeout(8);
    	setInterruptible(false);
    }

    protected void initialize() {
    	Robot.shooter.loadShooterOn();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Robot.shooter.loadShooterOff();
    }

    protected void interrupted() {
    	end();
    }
}
