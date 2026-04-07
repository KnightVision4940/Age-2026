// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.Operators;
import frc.robot.commands.AutoIntake;
import frc.robot.commands.Autos;
import frc.robot.commands.ShootFuelAuto;
import frc.robot.commands.ShooterVariableSpeed;
import frc.robot.commands.ClimbToBottom;
import frc.robot.commands.ClimbToTop;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeGrabPosition;
import frc.robot.commands.IntakeStowPosition;
import frc.robot.commands.ManualIntake;
import frc.robot.commands.OuttakeFeeder;
import frc.robot.commands.ReverseFeeder;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.commands.ManualClimbControl;
import frc.robot.commands.ManualClimbControlTest;
import frc.robot.subsystems.Climber;
import frc.robot.commands.FeedShooter;
import frc.robot.commands.FeederControl;
import frc.robot.commands.ShootFuel;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.SwerveSubsystem;
import swervelib.SwerveInputStream;

import java.io.File;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.subsystems.Shooter;
import frc.robot.commands.ShooterVariableSpeed;;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Intake intake = new Intake();
  private final Climber m_Climber = new Climber();
  private final Feeder m_Feeder = new Feeder();

  private final SwerveSubsystem drivebase = new SwerveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"));
  public final static Shooter m_Shooter = new Shooter();
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(Operators.XboxController.kPort);
  SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    m_autoChooser.setDefaultOption("Do Nothing", new InstantCommand());
    m_autoChooser.addOption("Just Shoot", new ShootFuelAuto(m_Feeder, m_Shooter, 3000));
    m_autoChooser.addOption("Test", new PathPlannerAuto("Test Auto"));
    m_autoChooser.addOption("Start Right", new PathPlannerAuto("Start Right"));
    SmartDashboard.putData("Auto Options", m_autoChooser);
    // drivebase.centerModulesCommand();
    drivebase.setDefaultCommand(driveFieldOrientedAngularVelocity); 
  }

  SwerveInputStream driveAngularVelocity = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> m_driverController.getLeftY() *-1.0,
                                                                () -> m_driverController.getLeftX() *-1.0)
                                                            .withControllerRotationAxis(()->m_driverController.getRightX()*-1)
                                                            .deadband(Constants.OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);
    SwerveInputStream driveDirectAngle = driveAngularVelocity.copy().withControllerHeadingAxis( () -> m_driverController.getRightX()*1,
                                                            () -> m_driverController.getRightY()*-1)
                                          .headingWhile(true);

  SwerveInputStream driveAngularVelocitySlow = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                                () -> m_driverController.getLeftY() *-0.4,
                                                                () -> m_driverController.getLeftX() *-0.4)
                                                            .withControllerRotationAxis(()->m_driverController.getRightX()*-1)
                                                            .deadband(Constants.OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .allianceRelativeControl(true);
                                                            SwerveInputStream driveDirectAngleSlow = driveAngularVelocity.copy().withControllerHeadingAxis( () -> m_driverController.getRightX()*1,
                                                            () -> m_driverController.getRightY()*-1)
                                                           .headingWhile(true);                                                         

  SwerveInputStream driveRobotOriented = SwerveInputStream.of(drivebase.getSwerveDrive(),
                                                              () -> m_driverController.getLeftY()*0.5,
                                                              () -> m_driverController.getLeftX()*0.5)
                                                            .withControllerRotationAxis(()-> m_driverController.getRightX()*-1)
                                                            .deadband(Constants.OperatorConstants.DEADBAND)
                                                            .scaleTranslation(0.8)
                                                            .robotRelative(true)
                                                            .allianceRelativeControl(false);                                                         
                                                            
                                                            Command driveFieldOrientedDirectAngle = drivebase.driveFieldOriented(driveDirectAngle);
                                                            Command driveRobotOrientedAngularVelocity  = drivebase.driveFieldOriented(driveRobotOriented);
                                                            Command driveFieldOrientedAngularVelocity = drivebase.driveFieldOriented(driveAngularVelocity);

                                                            Command driveFieldOrientedDirectAngleSlow = drivebase.driveFieldOriented(driveDirectAngleSlow);
                                                            Command driveFieldOrientedAngularVelocitySlow = drivebase.driveFieldOriented(driveAngularVelocitySlow);

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true'

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    NamedCommands.registerCommand("ShootFuelAuto", new ShootFuelAuto(m_Feeder, m_Shooter, 3000));
    NamedCommands.registerCommand("IntakeGrabPosition", new IntakeGrabPosition(intake)); 
    m_driverController.x().onTrue(new InstantCommand(() -> {
      m_Climber.lock();
    }, m_Climber));

    m_driverController.y().onTrue(new InstantCommand(() -> {
      m_Climber.unlock();
    }, m_Climber));

    m_driverController.povUp().whileTrue(new ManualClimbControl(m_Climber, 0.1));

    m_driverController.povDown().whileTrue(new ManualClimbControl(m_Climber, -0.1));

    m_driverController.povRight().whileTrue(new ClimbToTop(m_Climber));

    m_driverController.povLeft().whileTrue(new ClimbToBottom(m_Climber));

    m_driverController.rightBumper().whileTrue(new FeederControl(m_Feeder, 3000, m_Shooter));
    m_driverController.rightTrigger().whileTrue(new FeederControl(m_Feeder, 5000, m_Shooter));
    m_driverController.leftTrigger().whileTrue(new OuttakeFeeder(m_Feeder));
    m_driverController.leftBumper().whileTrue(new AutoIntake(intake));
    m_driverController.start().onTrue(new IntakeGrabPosition(intake));
    m_driverController.back().onTrue(new IntakeStowPosition(intake));

  }

  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_autoChooser.getSelected();
  }

  
}
  

