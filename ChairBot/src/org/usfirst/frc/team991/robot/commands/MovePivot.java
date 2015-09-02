package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MovePivot extends Command {
	double pivotSpeed;
	int direction;
	
    public MovePivot(int direction) {
        requires(Robot.shooter);
        pivotSpeed = .5;
        this.direction = direction;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (direction == 1) {
        	Robot.shooter.pivotUp(pivotSpeed);
    	} else if (direction == -1) {
    		Robot.shooter.pivotDown(pivotSpeed);
    	} else {
    		Robot.shooter.pivotStop();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.pivotStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		Robot.shooter.pivotStop();
    }
}
