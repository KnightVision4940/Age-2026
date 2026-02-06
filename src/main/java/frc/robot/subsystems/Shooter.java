// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {

  SparkMax shooterMotor;
  SparkClosedLoopController m_Controller;

  public void Shooter(){
    shooterMotor = new SparkMax(Constants.MotorIDs.shooterMotor, MotorType.kBrushless);
    m_Controller = shooterMotor.getClosedLoopController();
    SparkMaxConfig config = new SparkMaxConfig();
    config.closedLoop
      .p(1)
      .i(0)
      .d(0)
      .outputRange(1, -1);
  }

  public void shooterShoot(double speed){
    m_Controller.setSetpoint(speed, ControlType.kVelocity);
  }

  public void Stop(){
    shooterMotor.set(0);
  }
}
