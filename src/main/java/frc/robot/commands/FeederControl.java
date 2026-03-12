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
  Shooter shooter;
  boolean readyToShoot = false;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public FeederControl(Feeder feeder, double speed, Shooter shooter) {
    this.feeder = feeder;
    this.shooter = shooter;
    this.speed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(feeder, shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    this.shooter.shooterShoot(speed);
    this.readyToShoot = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    if(this.shooter.getCurrentVelocity() >= this.speed) {
      readyToShoot = true;
    }
    
    if(readyToShoot)
      this.feeder.spin(0.3);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    this.feeder.stop();
    this.shooter.Stop();;

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
