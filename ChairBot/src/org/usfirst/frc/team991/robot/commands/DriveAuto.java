package org.usfirst.frc.team991.robot.commands;

import org.usfirst.frc.team991.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *	Command to drive robot during autonomous for a certain period of time.
 */
public class DriveAuto extends Command {
	
	private double leftDrive, rightDrive, time;
	private Timer timer;

    public DriveAuto(double leftDrive, double rightDrive, double time) {
    	timer = new Timer();
    	this.leftDrive = leftDrive;
    	this.rightDrive = rightDrive;
    	this.time = time;
    	requires(Robot.drivetrain);
    }

    protected void initialize() {
    	timer.start();
    }

    protected void execute() {
    	Robot.drivetrain.arcadeDrive(leftDrive, rightDrive);
    }

    protected boolean isFinished() {
        if (timer.get() >= time)
        	return true;
        return false;
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    	timer.stop();
    }

    protected void interrupted() {}
}
