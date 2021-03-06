package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HardBrake extends Command {

	public HardBrake() {
		requires(Robot.drivetrain);
		setTimeout(1);
		setInterruptible(false);
	}

	protected void initialize() {
		Robot.drivetrain.hardBrake();
	}

	protected void execute() {}

	protected boolean isFinished() {
		return (isTimedOut() || Robot.drivetrain.isStopped());
	}

	protected void end() {
		Robot.drivetrain.stop();
	}

	protected void interrupted() {}
}
