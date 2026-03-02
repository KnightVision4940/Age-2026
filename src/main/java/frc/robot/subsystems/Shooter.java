// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  SparkMax leadMotor;
  SparkMax followMotor;
  SparkClosedLoopController m_Controller;

  public Shooter(){
    leadMotor = new SparkMax(Constants.MotorIDs.leadMotor, MotorType.kBrushless);
    followMotor = new SparkMax(Constants.MotorIDs.followMotor, MotorType.kBrushless);
    m_Controller = leadMotor.getClosedLoopController();
    SparkMaxConfig baseConfig = new SparkMaxConfig();
    SparkMaxConfig followConfig = new SparkMaxConfig();
    baseConfig.closedLoop
      .p(1)
      .i(0)
      .d(0)
      .outputRange(1, -1);

      followConfig.apply(baseConfig).follow(Constants.MotorIDs.leadMotor, true);

      leadMotor.configure(baseConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
      followMotor.configure(followConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void shooterShoot(double speed){
    m_Controller.setSetpoint(speed, ControlType.kVelocity);
  }

  public void shooterSetPower(double speed){
    leadMotor.set(speed);
  }

  public void Stop(){
    leadMotor.set(0);
  }

  public double getCurrentVelocity(){
    return leadMotor.getEncoder().getVelocity();
  }

  public void periodic(){
    SmartDashboard.putNumber("Shooter Velocity: ", this.getCurrentVelocity());
  }
}
