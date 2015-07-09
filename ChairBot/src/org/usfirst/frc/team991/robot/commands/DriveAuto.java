package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Command to drive robot during autonomous for a certain period of time.
 */
public class DriveAuto extends Command {
	
	private double y, rot;

    public DriveAuto(double y, double rot, double time) {
    	this.y = y;
    	this.rot = rot;
    	
    	requires(Robot.drivetrain);
    	setTimeout(time);
    }

    protected void initialize() {}

    protected void execute() {
    	Robot.drivetrain.arcadeDrive(y, rot);
    }

    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {
    	Robot.drivetrain.stop();
    }

    protected void interrupted() {}
}
