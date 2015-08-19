package org.usfirst.frc.team991.robot.subsystems;

import org.usfirst.frc.team991.robot.Robot;
import org.usfirst.frc.team991.robot.RobotMap;
import org.usfirst.frc.team991.robot.commands.ArcadeDriveJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Drive Train Subsystem.
 * Defines the capabilities of robot drive train.
 * @author JackV
 */
public class DriveTrain extends Subsystem {
	private SpeedController front_right_motor, back_right_motor, front_left_motor, back_left_motor;
	private RobotDrive drive;
	private Encoder left_encoder, right_encoder;
	private final double DEADZONE, MAX_SPEED, FORWARD, MAXPERIOD, MINRATE, ROTSCALER, HARDBRAKE,
							KP_KEEP_STRAIGHT, KP_TURN, STOPPING_DISTANCE, ENCODER_SCALER;
	private double current_speed, scaler, left_rate, right_rate;
	private int robotDirection;
	private Gyro gyro;
	
	/** Constructs DriveTrain Subsystem.
	 * Initializes preferences, motors, encoders, gyro, drive system.
	 * Inverts motors.
	 * Writes motors and sensors to LiveWindow.
	 */
	public DriveTrain() {
		/* ----------------------------------------------------------------
		 * Preferences
		 * ---------------------------------------------------------------- */
		//Encoders
		MAXPERIOD = Robot.pref.getDouble("Max Period", 0.1);
		MINRATE = Robot.pref.getDouble("Min Rate", 100);
		ENCODER_SCALER = Robot.pref.getDouble("Encoder Scaler", 0.008727);
		
		//ArcadeDrive
		DEADZONE = Robot.pref.getDouble("Deadzone", 0.1);
		MAX_SPEED = Robot.pref.getDouble("Max Speed", 2700 * ENCODER_SCALER); //Human = 2700; Cannon = 2700;
		FORWARD = Robot.pref.getDouble("Forward", 0.1);
		ROTSCALER = Robot.pref.getDouble("Rotation Scaler", 0.4);
		
		//Braking
		HARDBRAKE = Robot.pref.getDouble("Hand Brake", 0.3);
		
		//Autonomous
		KP_KEEP_STRAIGHT = Robot.pref.getDouble("kP for DriveStraight", 0.03);
		KP_TURN = Robot.pref.getDouble("kP for Turn", 0.03);
		STOPPING_DISTANCE = Robot.pref.getDouble("Stop Distance for DriveStraight", 24);
		
		/* ----------------------------------------------------------------
		 * INITIALIZATION
		 * ---------------------------------------------------------------- */
		
		//Initialize motor controllers
		front_left_motor = new Talon(RobotMap.frontleftMotor);
		back_left_motor = new Talon(RobotMap.backleftMotor);
		front_right_motor = new Talon(RobotMap.frontrightMotor);
		back_right_motor = new Talon(RobotMap.backrightMotor);
		
		//Initialize encoders and gyro
		left_encoder = new Encoder(RobotMap.leftencoderAChannel, RobotMap.leftencoderBChannel, true, Encoder.EncodingType.k2X);
		right_encoder = new Encoder(RobotMap.rightencoderAChannel, RobotMap.rightencoderBChannel, false, Encoder.EncodingType.k2X);
		
		left_encoder.setMaxPeriod(MAXPERIOD);
		left_encoder.setMinRate(MINRATE);
		left_encoder.setDistancePerPulse(ENCODER_SCALER);
		
		right_encoder.setMaxPeriod(MAXPERIOD);
		right_encoder.setMinRate(MINRATE);
		right_encoder.setDistancePerPulse(ENCODER_SCALER);
		
		gyro = new Gyro(RobotMap.gyro);
		gyro.initGyro();
		
		//Initialize robot drive
		drive = new RobotDrive(front_left_motor, back_left_motor, front_right_motor, back_right_motor);
		
		//Invert motors
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearLeft, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
		drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
		
		//LiveWindow
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) front_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) front_right_motor);
		LiveWindow.addActuator("Drive Train", "Left Motor", (Talon) back_left_motor);
		LiveWindow.addActuator("Drive Train", "Right Motor", (Talon) back_right_motor);
		
		LiveWindow.addSensor("Drive Train", "Left Encoder", left_encoder);
		LiveWindow.addSensor("Drive Train", "Right Encoder", right_encoder);
		LiveWindow.addSensor("Drive Train", "Gyroscope", gyro);
	}
	

	/** Initialize the default command for a subsystem
	 * By default subsystems have no default command, but if they do,
	 * the default command is set with this method.
	 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
	 */
	public void initDefaultCommand() {
		setDefaultCommand(new ArcadeDriveJoystick());
	}
	
	/* ----------------------------------------------------------------
	 * ROBOT MOVEMENT CONTROLS
	 * ---------------------------------------------------------------- */
	
	/** Arcade Drive.
	 * Called continuously.
	 * Gets y-axis and rotation from joystick
	 * and robot velocity and direction from encoders.
	 * If y-axis is greater than DEADZONE, it scales the
	 * y-axis by current_speed/MAX_SPEED, adds FORWARD, and limits
	 * it to a max/min of 1.
	 * Also prevents y-axis values opposite direction of motion.
	 * Writes velocity and scaler to SmartDashboard.
	 * @param y 	Y-axis value from joystick
	 * @param rot	Rotational value from joystick
	 */
	public void arcadeDrive(double y, double rot) {
		left_rate = left_encoder.getRate();
		right_rate = right_encoder.getRate();

		robotDirection = getRobotDirection();
		
		if(Math.abs(y) >= DEADZONE) {
			//Calculate y-scaler
			current_speed = (Math.abs(left_rate) + Math.abs(right_rate))/2;
			scaler = (current_speed/MAX_SPEED) + FORWARD;
			y *= Math.min(scaler, 1);
			
			/*	Prevents hard brake
			 *	Checks if moving and set to 0 if y is in opposite direction */
//			if(robotDirection == 1) {
//				if(y > 0){
//					y = 0;
//				}
//			} else if (robotDirection == -1){
//				if(y < 0) {
//					y = 0;
//				}
//			}
			
			//SmartDashboard update
			SmartDashboard.putNumber("Current Speed", current_speed);
			SmartDashboard.putNumber("Scaler", scaler);
		}
		
		//Scale rotation
		rot *= ROTSCALER;
		
		//Update drive values
		drive.arcadeDrive(y, rot, false);
		
		//SmartDashboard update
		SmartDashboard.putNumber("Right Encoder Speed", right_rate);
		SmartDashboard.putNumber("Left Encoder Speed", left_rate);
	}
	
	/** Drives Straight.
	 * Called continuously.
	 * Slows down linearly from STOPPING_DISTANCE to passed in distance
	 * by scaling speed by distanceAway/STOPPING_DISTANCE.
	 * Gets angle offset from previously zeroed gyroscope, negates it,
	 * and multiplies it by KP_KEEP_STRAIGHT to convert angle values into
	 * rotational speed values.
	 * Uses scaled speed and rotational values and passes them
	 * into ArcadeDrive.
	 * @param speed		Desired speed of robot.
	 * @param distance	Desired distance in inches to travel.
	 */
	public void driveStraight(double speed, double distance) {
		double distanceAway = distance - getEncoderDistance();
		
		//Scales speed after coming within certain stopping distance
		if (distanceAway < STOPPING_DISTANCE) {
			speed = distanceAway/STOPPING_DISTANCE * speed;
		}
		
		drive.arcadeDrive(-speed, -gyro.getAngle() * KP_KEEP_STRAIGHT);
	}
	
	/** Turns robot
	 * Continuously called.
	 * Takes desired angle of turn and subtracts angle already turned from
	 * gyroscope, multiplies by KP_TURN to convert to angular speed, then
	 * calls ArcadeDrive.
	 * @param angleOfTurn	Desired angle of turn.
	 */
	public void turn(double angleOfTurn) {
		drive.arcadeDrive(0, (angleOfTurn - gyro.getAngle()) * KP_TURN);
	}
	
	/** Performs a hard brake
	 * Gets robot direction from encoders, then calls TankDrive
	 * with HARDBRAKE in the opposite direction of motion.
	 */
	public void hardBrake() {
		robotDirection = getRobotDirection();
		
		//Feeds opposite value to brake
		if (robotDirection == 1) {
			drive.tankDrive(HARDBRAKE, HARDBRAKE);
		} else if (robotDirection == -1) {
			drive.tankDrive(-HARDBRAKE, -HARDBRAKE);
		}
	}
	
	/** Stops drive train
	 * Sets TankDrive to (0,0).
	 */
	public void stop() {
		drive.tankDrive(0, 0);
	}
	
	/* ----------------------------------------------------------------
	 * ROBOT MOVEMENT INFORMATION
	 * ---------------------------------------------------------------- */
	
	/** Checks if robot is stopped
	 * @return True if robot is stopped.
	 */
	public boolean isStopped() {
		if (left_encoder.getStopped() && right_encoder.getStopped())
			return true;
		return false;
	}
	
	/** Returns robot direction
	 * @return Forward = 1; Backwards = -1; Stopped = 0
	 */
	public int getRobotDirection() {
		if (!isStopped()) {
			if (left_encoder.getRate() > 0 && right_encoder.getRate() > 0) {
				return 1;
			} else {
				return -1;
			}
		} else {
			return 0;
		}
	}
	
	/** Gets gyroscope angle
	 * @return Angle from gyroscope
	 */
	public double getGyroAngle() {
		return gyro.getAngle();
	}
	
	/** Resets gyroscope to 0
	 */
	public void resetGyroAngle() {
		gyro.reset();
	}
	
	/** Gets encoder distance through averaging left and right.
	 * @return Average of left and right encoder distances.
	 */
	public double getEncoderDistance() {
		return (Math.abs(left_encoder.getDistance()) + Math.abs(right_encoder.getDistance())) / 2;
	}
	
	/** Resets encoder distance to 0
	 */
	public void resetEncoders() {
		left_encoder.reset();
		right_encoder.reset();
	}
}

