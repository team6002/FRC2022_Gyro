// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Turret;

public class CMD_MannualTurretMode extends CommandBase {
  /** Turns the Turret Mode to mannual mode */
  SUB_Turret m_Turret;

  public CMD_MannualTurretMode(SUB_Turret p_Turret) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_Turret = p_Turret;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Turret.setTurretMode(2);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}