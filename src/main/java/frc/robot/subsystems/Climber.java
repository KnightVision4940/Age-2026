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
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class Climber extends SubsystemBase {

  SparkMax leadMotor;
  SparkMax followMotor;
  SparkClosedLoopController m_leadMotor;
  SparkClosedLoopController m_followMotor;
  Servo climbServo;
  boolean locked;
  SparkMaxConfig config;
  SparkClosedLoopController climbController;

  public Climber() {
    locked = false;
    climbServo = new Servo(Constants.ClimbServoConstants.climbServo);
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

  public void goToPosition(double position) {
    m_leadMotor.setSetpoint(position, ControlType.kPosition);
  }

  // TODO: Make sure a lock check
  public void manualControl(double speed) {
    leadMotor.set(speed);
  }

  public void stop() {
    leadMotor.set(0);
  }


  public double getPosition() {
    return leadMotor.getEncoder().getPosition();
  }

  // Climb lock
  public void lock() {
    climbServo.set(1.0);
    locked = true;
  }
  // Climb unlock
  public void unlock() {
    climbServo.set(0.0);
    locked = false;
  }

  public boolean isLocked() {
    return locked;
  }  


  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Locked", this.isLocked());
    SmartDashboard.putNumber("Climb Position: ", this.getPosition());
    // This method will be called once per scheduler run
  }  
}
