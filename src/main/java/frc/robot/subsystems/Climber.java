// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class Climber extends SubsystemBase {
 
    SparkMax leadMotor;
    SparkMax followMotor;
    SparkClosedLoopController m_leadMotor;
    SparkClosedLoopController m_followMotor;

  public Climber() {
    leadMotor = new SparkMax(Constants.ClimbMotorIDs.leadMotor, MotorType.kBrushless);
    followMotor = new SparkMax(Constants.ClimbMotorIDs.followMotor, MotorType.kBrushless);
    m_leadMotor = leadMotor.getClosedLoopController();
    m_followMotor = followMotor.getClosedLoopController();

    SparkMaxConfig config = new SparkMaxConfig();
    SparkMaxConfig followerConfig = new SparkMaxConfig();

    config.closedLoop
      .p(1)
      .i(0)
      .d(0)
      .outputRange(1, -1);
    followerConfig.apply(config).follow(Constants.ClimbMotorIDs.leadMotor, true);

    leadMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    followMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  public void climb(double speed) {
    m_leadMotor.setSetpoint(speed, ControlType.kPosition);
  }

  public void manualControl(double speed){
    leadMotor.set(speed);
  }

  public void Stop (double speed){
    leadMotor.set(0);
  }

  public double getPosition(){
    return leadMotor.getEncoder().getPosition();
  }


    @Override
  public void periodic() {
    SmartDashboard.putNumber("Climb Position: ", this.getPosition());
  }
}
