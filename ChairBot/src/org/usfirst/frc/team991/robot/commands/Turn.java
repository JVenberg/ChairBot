package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn extends Command {
	private double angleOfTurn;

	public Turn(double angleOfTurn, double timeout) {
		requires(Robot.drivetrain);
		setTimeout(timeout);
		this.angleOfTurn = angleOfTurn;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetGyroAngle();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drivetrain.turn(angleOfTurn);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return isTimedOut();
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
