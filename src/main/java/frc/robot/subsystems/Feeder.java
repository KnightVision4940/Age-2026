// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkBase.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Feeder extends Command {
  public SparkMax m_shootfeed;
  public SparkMax m_hopfeed;
  
  /** Creates a new Feeder. */
  public Feeder() {
    m_shootfeed = new SparkMax(Constants.Motors.Intake.kShootFeed, MotorType.kBrushless);
    m_hopfeed = new SparkMax(Constants.Motors.Intake.kHopFeed, MotorType.kBrushless);
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .smartCurrentLimit(Constants.Motors.Intake.kCurrentLimit)
        .idleMode(IdleMode.kBrake);
    

    // Persist parameters to retain configuration in the event of a power cycle
    m_shootfeed.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_hopfeed.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

  }
    
    // Use addRequirements() here to declare subsystem dependencies.

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
