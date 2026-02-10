// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import static edu.wpi.first.units.Units.Meter;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import java.io.File;
import java.util.Arrays;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;
import swervelib.SwerveDrive;
import swervelib.SwerveInputStream;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;

public class SwerveSubsystem extends SubsystemBase {
  /** Creates a new SwerveModule. */

  double maximumSpeed;
  File directory;
  SwerveDrive  swerveDrive;
  SparkMax driveMotor;

  private boolean robotRelative = false;

  public SwerveSubsystem(File directory) {
    maximumSpeed = Units.feetToMeters(4.5);
    SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
    directory = new File(Filesystem.getDeployDirectory(),"swerve");
    try {
      swerveDrive = new SwerveParser(directory).createSwerveDrive(
        maximumSpeed,
        new Pose2d(
          new Translation2d(Meter.of(1),
          Meter.of(4)
          ),
      Rotation2d.fromDegrees(0)));
      // Alternative method if you don't want to supply the conversion factor via JSON files.
      // swerveDrive = new SwerveParser(directory).createSwerveDrive(maximumSpeed, angleConversionFactor, driveConversionFactor);
    } catch(Exception error){
      System.err.println(error);
    }
  
 
  setupPathPlanner(); 
  
  //swerveDrive.setCosineCompensator(!SwerveDriveTelemetry.isSimulation);
  swerveDrive.setAutoCenteringModules(false);
  }

public frc.robot.subsystems.PathPlannerAuto getAutonomousCommand(String pathName)
{
  return new PathPlannerAuto();
}

public void driveFieldOriented(ChassisSpeeds velocity)
{
  swerveDrive.driveFieldOriented(velocity);
}

public Command driveFieldOriented(Supplier<ChassisSpeeds> velocity)
{
  return run(()->{
    swerveDrive.driveFieldOriented(velocity.get());

  });
}

  public Command centerModulesCommand()
  {
    return run(() -> Arrays.asList(swerveDrive.getModules())
    .forEach(it -> it.setAngle(0.0)));
  }

   public void resetOdometry(Pose2d initialHolonomicPose)
  {
    swerveDrive.resetOdometry(initialHolonomicPose);
  }

  /**
     * Gets the current pose (position and rotation) of the robot, as reported by odometry.
     *
     * @return The robot's pose
     */
  public Pose2d getPose()
  {
    return swerveDrive.getPose();
  }

  /**
   * Set chassis speeds with closed-loop velocity control.
   *
   * @param chassisSpeeds Chassis Speeds to set.
   */
  public void setChassisSpeeds(ChassisSpeeds chassisSpeeds)
  {
    swerveDrive.setChassisSpeeds(chassisSpeeds);
  }

  /**
   * Post the trajectory to the field.
   *
   * @param trajectory The trajectory to post.
   */
  public void postTrajectory(Trajectory trajectory)
  {
    swerveDrive.postTrajectory(trajectory);
  }

  /**
   * Resets the gyro angle to zero and resets odometry to the same position, but facing toward 0.
   */
  public void zeroGyro()
  {
    swerveDrive.zeroGyro();
  }

  public ChassisSpeeds getRobotVelocity()
  {
    return swerveDrive.getRobotVelocity();
  }


  public void setupPathPlanner()
  {
    // Load the RobotConfig from the GUI settings. You should probably
    // store this in your Constants file
    RobotConfig config;
    try
    {
      config = RobotConfig.fromGUISettings();

      final boolean enableFeedforward = true;
      // Configure AutoBuilder last
      AutoBuilder.configure(
          this::getPose,
          // Robot pose supplier
          this::resetOdometry,
          // Method to reset odometry (will be called if your auto has a starting pose)
          this::getRobotVelocity,
          // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
          (speedsRobotRelative, moduleFeedForwards) -> {
            if (enableFeedforward)
            {
              swerveDrive.drive(
                  speedsRobotRelative,
                  swerveDrive.kinematics.toSwerveModuleStates(speedsRobotRelative),
                  moduleFeedForwards.linearForces()
                               );
            } else
            {
              swerveDrive.setChassisSpeeds(speedsRobotRelative);
            }
          },
          // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
          new PPHolonomicDriveController(
          ),
          config,
          // The robot configuration
          () -> {
            // Boolean supplier that controls when the path will be mirrored for the red alliance
            // This will flip the path being followed to the red side of the field.
            // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

            var alliance = DriverStation.getAlliance();
            if (alliance.isPresent())
            {
              return alliance.get() == DriverStation.Alliance.Red;
            }
            return false;
          },
          this
          // Reference to this subsystem to set requirements
                           );

    } catch (Exception e)
    {
      // Handle exception as needed
      e.printStackTrace();
    }

    //Preload PathPlanner Path finding
    // IF USING CUSTOM PATHFINDER ADD BEFORE THIS LINE
    PathfindingCommand.warmupCommand().schedule();
  }

public SwerveDrive getSwerveDrive()
{
  return swerveDrive;
}

public boolean getRobotRelative(){
  return this.robotRelative;
}



  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }


public SwerveDrive getSwerveDrive() {
    return swerveDrive;
}
}
