package frc.robot;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;


public class Robot extends TimedRobot {
// The encoders for the drive
Encoder leftEncoder = new Encoder(1,2);
Encoder rightEncoder = new Encoder(5,6);

// The gain for a simple P loop
double kP = 1;

// Initialize motor controllers and drive
Spark left1 = new Spark(2);
Spark left2 = new Spark(3);

Spark right1 = new Spark(0);
Spark right2 = new Spark(1);
SpeedControllerGroup leftMotors = new SpeedControllerGroup(left1, left2);
SpeedControllerGroup rightMotors = new SpeedControllerGroup(right1, right2);

DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

@Override
public void autonomousInit() {
    //leftEncoder.reset();
    //rightEncoder.reset();
    // Configures the encoders' distance-per-pulse
    leftEncoder.setDistancePerPulse(1./50.);
    rightEncoder.setDistancePerPulse(1./50.);
}

@Override
public void autonomousPeriodic() {
    // Assuming no wheel slip, the difference in encoder distances is proportional to the heading error
   // double error = leftEncoder.getDistance() - rightEncoder.getDistance();

    // Drives forward continuously at half speed, using the encoders to stabilize the heading
    drive.tankDrive(.5,.5);

    //System.out.println(Timer.getFPGATimestamp());
    System.out.print("Left: \t");
    System.out.println(leftEncoder.getDistance());
    System.out.print("Right: \t");
    System.out.println(rightEncoder.getDistance());
}
}
