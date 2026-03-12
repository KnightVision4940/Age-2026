// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.RobotContainer;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class FeederControl extends Command {
  double speed;
  Feeder feeder;
  Shooter m_Shooter;
  boolean readyToShoot = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FeederControl(Feeder feeder, double speed, Shooter m_Shooter) {
    this.feeder = feeder;
    this.m_Shooter = m_Shooter;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(feeder, m_Shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.m_Shooter.shooterShoot(speed);}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    if(this.m_Shooter.getCurrentVelocity() >= this.speed) {
      readyToShoot = true;
    }
    
    if(readyToShoot)
      this.feeder.spin(0.3);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
