package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Turn extends Command {
	private double angleOfTurn;

	/**
	 * @param angleOfTurn
	 * @param timeout
	 */
	public Turn(double angleOfTurn, double timeout) {
		requires(Robot.drivetrain);
		setTimeout(timeout);
		this.angleOfTurn = angleOfTurn;
	}
	
	/** Called just before this Command runs the first time.
	 * @see edu.wpi.first.wpilibj.command.Command#initialize()
	 */
	protected void initialize() {
		Robot.drivetrain.resetGyroAngle();
	}

	/** Called repeatedly when this Command is scheduled to run.
	 * @see edu.wpi.first.wpilibj.command.Command#execute()
	 */
	protected void execute() {
		Robot.drivetrain.turn(angleOfTurn);
	}

	/** Make this return true when this Command no longer needs to run execute()
	 * @see edu.wpi.first.wpilibj.command.Command#isFinished()
	 */
	protected boolean isFinished() {
		return isTimedOut();
	}

	/** Called once after isFinished returns trues
	 * @see edu.wpi.first.wpilibj.command.Command#end()
	 */
	protected void end() {
		Robot.drivetrain.stop();
	}

	/** Called when another command which requires one or more of the same
	 * subsystems is scheduled to run.
	 * @see edu.wpi.first.wpilibj.command.Command#interrupted()
	 */
	protected void interrupted() {
		end();
	}
}
