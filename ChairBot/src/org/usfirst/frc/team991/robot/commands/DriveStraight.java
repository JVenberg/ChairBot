package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraight extends Command {
	private double speed, distance;
	
    public DriveStraight(double speed, double distance, double timeout) {
        requires(Robot.drivetrain);
        setTimeout(timeout);
        this.speed = speed;
        this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGyroAngle();
    	Robot.drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drivetrain.driveStraight(speed, distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut() || Robot.drivetrain.getEncoderDistance() >= distance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
