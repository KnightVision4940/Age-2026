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
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
  public SparkMax m_roller;
  public SparkMax m_pivot; 
  public SparkClosedLoopController m_pivotController;
  
  /** Creates a new Intake. */
  public Intake() {
    m_roller = new SparkMax(Constants.Motors.Intake.kRoller, MotorType.kBrushless);
    m_pivot = new SparkMax(Constants.Motors.Intake.kPivot, MotorType.kBrushless);
    SparkMaxConfig config = new SparkMaxConfig();
    config
        .smartCurrentLimit(Constants.Motors.Intake.kCurrentLimit)
        .idleMode(IdleMode.kBrake);
    
    
    m_pivotController = m_pivot.getClosedLoopController();
    config.closedLoop
      .p(0.0685)
      .i(0)
      .d(0)
      .outputRange(-0.5, 0.5);

    // Persist parameters to retain configuration in the event of a power cycle
    m_roller.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_pivot.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    m_pivot.getEncoder().setPosition(0);

  }

  public Command intakeCommand(double speed) {
    return this.runEnd(() ->
    {
      spin(speed);
    }, () -> {
      rollerStop();
    });
  }

  public void spin(double speed) {
    m_roller.set(speed);
  }

  public void movePivot(double speed) {
    m_pivot.set(speed);
  }

  public double getPivotPosition(){
    return m_pivot.getEncoder().getPosition();
  }

  public void pivotIntake(double position){
    m_pivotController.setSetpoint(position, ControlType.kPosition);
    
  }

  public void rollerStop() {
    m_roller.set(0);
  }
  public void pivotStop() {
    m_pivot.set(0);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Pivot Position", getPivotPosition());
  }

  
}

