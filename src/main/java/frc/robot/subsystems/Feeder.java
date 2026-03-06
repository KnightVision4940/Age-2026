// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Feeder extends SubsystemBase {
  public SparkMax m_shootfeed;
  /** Creates a new Feeder. */
  public Feeder() {
    m_shootfeed = new SparkMax(Constants.Motors.Intake.kShootFeed, MotorType.kBrushless);
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .smartCurrentLimit(Constants.Motors.Intake.kCurrentLimit)
        .idleMode(IdleMode.kCoast);
    

    // Persist parameters to retain configuration in the event of a power cycle
    m_shootfeed.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  
  public void spin(double speed){
    m_shootfeed.set(-speed);
  }

  public void stop(){
    m_shootfeed.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
