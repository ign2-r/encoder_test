
package frc.robot;
import java.lang.*; 
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

public class Robot extends TimedRobot {
  // Setting up all the PWM Channels
  private static final int kRearRightChannel = 0;
  private static final int kFrontRightChannel = 1;
  private static final int kRearLeftChannel = 2;
  private static final int kFrontLeftChannel = 3;
  private static final int kWinch = 4;
  private static final int kArm = 5;
  private static final int kIntake = 6;
  private static final int kLauncher = 7;
  private static final int kIndexer = 8;
  private static final int kColorwheel = 9;

  private static final int kJoystickChannel = 0;
  private XboxController m_xbox;

  // Setting up subsystems
  Spark indexer = new Spark(kIndexer);
  Spark winch = new Spark(kWinch);
  Spark arm = new Spark(kArm);
  Spark intake = new Spark(kIntake);
  Spark launcher = new Spark(kLauncher);
  Spark colorwheel = new Spark(kColorwheel);

  // Setting up driving motor contorllers
  Spark frontLeft = new Spark(kFrontLeftChannel);
  Spark rearLeft = new Spark(kRearLeftChannel);
  Spark frontRight = new Spark(kFrontRightChannel);
  Spark rearRight = new Spark(kRearRightChannel);
  SpeedControllerGroup m_left = new SpeedControllerGroup(frontLeft, rearLeft);
  SpeedControllerGroup m_right = new SpeedControllerGroup(frontRight, rearRight);
  private DifferentialDrive m_base = new DifferentialDrive(m_left, m_right);


  //DutyCycleEncoder encoder_left;
  //DutyCycleEncoder encoder_right;
  //Encoder encoder_left = new Encoder(1,2);
  //Encoder encoder_right = new Encoder(5,6);

  DutyCycleEncoder encoder_left = new DutyCycleEncoder(0);
  DutyCycleEncoder encoder_right = new DutyCycleEncoder(4);
  

  


  // Auton
  private double startTime;

  @Override
  public void robotInit() {
    //encoder_left = new DutyCycleEncoder(0);
    //encoder_right = new DutyCycleEncoder(4);

    encoder_right.setDistancePerRotation(-0.5*Math.PI);
    encoder_left.setDistancePerRotation(0.5*Math.PI); 

    // Vision setup
    CameraServer.getInstance().startAutomaticCapture(0);
    CameraServer.getInstance().startAutomaticCapture(1);

    // If the motors are inverted. DO NOT mess with it until it has been tested.
    m_left.setInverted(true);
    m_right.setInverted(true);

    // Setting up the drive train and Xbox controller
    m_xbox = new XboxController(kJoystickChannel);

  }

  @Override
  public void autonomousInit() {
    //encoder_right.reset();
    //encoder_left.reset();
    //encoder_right.setDistancePerRotation(-0.5*Math.PI); // feet per rotation
    //encoder_left.setDistancePerRotation(0.5*Math.PI); // one of these is negative because its installed flipped 180 degrees
    startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();
    double runTime = (time - startTime);
    //System.out.println(runTime);
    //System.out.println("RIGHT ENC: " + Double.toString(encoder_right.get()) + "\nLEFT ENC: " + Double.toString(encoder_left.get()));
    
    System.out.println("RUNS");
    System.out.println(encoder_right.getDistance());
    System.out.println(encoder_left.getDistance());


    double indexerPower = 0.0;
    double launcherPower = 0.0;
    double leftPower = 0;
    double rightPower = 0;

  if (runTime > 10) {
      indexerPower = 0.0;
       launcherPower = 0.0;
       leftPower = 0.0;
       rightPower = 0.0;
       winch.stopMotor();
     } 
     else if (runTime > 6) {
       indexerPower = 0.0;
       launcherPower = 0.0;
       leftPower = 0.5;
       rightPower = 0.5;
       winch.stopMotor();
     } 
     else {
       indexerPower = -1.0;
       launcherPower = 0.8;
       leftPower = 0.0;
       rightPower = 0.0;
       winch.stopMotor();
     }


indexer.set(indexerPower);
launcher.set(launcherPower);
m_base.tankDrive(leftPower, rightPower);
}

  // Remote Controls
  @Override
  public void teleopPeriodic() {
 

    // Left joystick is for the left side
    // Right joystick for the right side

    m_base.tankDrive(m_xbox.getY(Hand.kLeft) * 3 / 4, m_xbox.getY(Hand.kRight) * 3 / 4);

    // DPAD TEST
    if (m_xbox.getPOV() == 90) {
      colorwheel.set(-1);
    }
    if (m_xbox.getPOV() == 180) {
      colorwheel.set(1);
    }
    if (!(m_xbox.getPOV() == 90) || (m_xbox.getPOV() == 180)) {
      colorwheel.set(0.0);
    }
    // Left Stick
    // Right Stick
    if(m_xbox.getAButtonPressed()) {
      launcher.set(1); 
      winch.stopMotor();
      intake.stopMotor();
    }
    if(m_xbox.getAButtonReleased()) {
      launcher.set(0.0);
      winch.stopMotor();
      intake.stopMotor();
    }
   // if(m_xbox.getAButtonPressed()) {
   //   indexer.set(-1);
   // }


    // Left bumper
    // Left trigger
    if (m_xbox.getBumper(Hand.kLeft)) {
      indexer.set(-1);
    }
    if (m_xbox.getTriggerAxis(Hand.kLeft) != 0) {
      indexer.set(0.0);
    }
    if (!(m_xbox.getBumper(Hand.kLeft) || m_xbox.getTriggerAxis(Hand.kLeft) != 0)) {
      indexer.set(0.0);
    }

    // Right bumper
    // Right trigger
    if (m_xbox.getBumper(Hand.kRight)) {
      intake.set(-0.7);
      winch.stopMotor();
    }
    if (m_xbox.getTriggerAxis(Hand.kRight) != 0) {
      intake.set(0.7);
      winch.stopMotor();
    }
    if (!(m_xbox.getBumper(Hand.kRight) || m_xbox.getTriggerAxis(Hand.kRight) != 0)) {
      intake.set(0.0);
      winch.stopMotor();
    }

    // A button
    // B button
    if (m_xbox.getBButtonPressed()) {
      winch.set(1);
    }
    if (m_xbox.getBButtonReleased()) {
      winch.set(0.0);
    }

    // X button
    // Y button
    if (m_xbox.getXButton()) {
      arm.set(-.45);
    }
    if (m_xbox.getYButton()) {
      arm.set(.45);
    }
    if (!(m_xbox.getXButton() || m_xbox.getYButton())) {
      arm.set(0.0);
    }
  }
} 
