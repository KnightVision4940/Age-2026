// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Feeder;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Shooter;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class ShootFuelAuto extends Command {
  
  double speed;
  Shooter shooter;
  double startTime;
  Feeder feeder;

  public ShootFuelAuto(Feeder feeder, Shooter shooter, double speed, double startTime) {
    this.speed = speed;
    this.shooter = shooter;
    this.feeder = feeder;
    addRequirements(shooter, feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp();
    this.shooter.shooterShoot(speed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    this.feeder.spin(0.3);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.shooter.Stop();
    this.feeder.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    double currentTime = Timer.getFPGATimestamp();
    if ((currentTime - startTime) >= Constants.AutoConstants.AutoFuelIntakeTime)
    {
        return true;
    }
    return false;
  }
} 