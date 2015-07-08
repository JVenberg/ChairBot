package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Command to fire the shooter.
 */
public class FireShooter extends Command {

    public FireShooter() {
        requires(Robot.shooter);
        setTimeout(1);
        setInterruptible(false);
    }

    protected void initialize() {
    	Robot.shooter.triggerOn();
    }

    protected void execute() {}

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Robot.shooter.triggerOff();
    }
    
    protected void interrupted() {
    	end();
    }
}
