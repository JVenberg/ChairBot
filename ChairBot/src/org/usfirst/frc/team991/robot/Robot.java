
package org.usfirst.frc.team991.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team991.robot.commands.Autonomous;
import org.usfirst.frc.team991.robot.commands.DriveAuto;
import org.usfirst.frc.team991.robot.subsystems.DriveTrain;
import org.usfirst.frc.team991.robot.subsystems.Pneumatics;
import org.usfirst.frc.team991.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static OI oi;

	public static DriveTrain drivetrain;
	public static Pneumatics pneumatics;
	public static Shooter shooter;
	

	Command autoCommand;
	public SendableChooser autoChooser;
	public SendableChooser autonomousDirectionChooser;
	
    public void robotInit() {
		drivetrain = new DriveTrain();
		pneumatics = new Pneumatics();
		shooter = new Shooter();

        SmartDashboard.putData(drivetrain);
        SmartDashboard.putData(pneumatics);
        SmartDashboard.putData(shooter);
		
		oi = new OI();

		autoChooser = new SendableChooser();
		autoChooser.addDefault("Autonomous", new Autonomous());
		autoChooser.addObject("Autonomous Drive", new DriveAuto(0,0,0));
		SmartDashboard.putData("Auto Mode", autoChooser);
		
		pneumatics.start();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	autoCommand = (Command) autoChooser.getSelected();
    	autoCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autoCommand != null) autoCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
