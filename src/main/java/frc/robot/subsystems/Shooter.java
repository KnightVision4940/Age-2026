// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;


public class Shooter extends SubsystemBase {

  SparkMax shooterMotor;

  public void Shooter(){
    shooterMotor = new SparkMax(Constants.MotorIDs.shooterMotor, MotorType.kBrushless);
  }

  public void shooterShoot(double speed){
    shooterMotor.set(speed);
  }

  public void Stop(){
    shooterMotor.set(0);
  }
}
