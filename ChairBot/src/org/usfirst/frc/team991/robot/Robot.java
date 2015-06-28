
package org.usfirst.frc.team991.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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
	Command autonomousCommand;
	public static OI oi;

	//Preferences
	public static Preferences pref;
	
	//Subsystems
	public static DriveTrain drivetrain;
	public static Pneumatics pneumatics;
	public static Shooter shooter;
	
	//Auto Chooser
	public SendableChooser autoChooser;
	public SendableChooser autonomousDirectionChooser;
	
    public void robotInit() {
    	//Gets Preferences
    	pref = Preferences.getInstance();
    	
    	//Subsystems
		drivetrain = new DriveTrain();
		pneumatics = new Pneumatics();
		shooter = new Shooter();
		
		oi = new OI();
		
		//Auto Chooser
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Autonomous", new Autonomous());
		autoChooser.addObject("Autonomous Drive", new DriveAuto(0,0,0));
		SmartDashboard.putData("Auto Mode", autoChooser);
		
		//Pneumatics
		pneumatics.start();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

    public void autonomousInit() {
    	autonomousCommand = (Command) autoChooser.getSelected();
    	autonomousCommand.start();
    }

    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
    	//Stops autonomous
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit(){}

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
