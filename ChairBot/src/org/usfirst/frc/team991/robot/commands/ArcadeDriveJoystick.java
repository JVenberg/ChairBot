package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 *	Command to control the Drive Train with Joystick through tank drive.
 */
public class ArcadeDriveJoystick extends Command {

	public ArcadeDriveJoystick() {
		requires(Robot.drivetrain);
	}

	protected void initialize() {}

	protected void execute() {
		Robot.drivetrain.arcadeDrive(Robot.oi.getLeftJoy().getY(), Robot.oi.getLeftJoy().getTwist());
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		Robot.drivetrain.stop();
	}

	protected void interrupted() {
		end();
	}
}