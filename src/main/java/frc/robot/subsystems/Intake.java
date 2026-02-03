// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
  public SparkMax m_intake; 
  
  /** Creates a new Intake. */
  public Intake() {
    m_intake = new SparkMax(2, MotorType.kBrushless);

    SparkMaxConfig config = new SparkMaxConfig();
    config
        .smartCurrentLimit(40)
        .idleMode(IdleMode.kBrake);

    // Persist parameters to retain configuration in the event of a power cycle
    m_intake.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}

