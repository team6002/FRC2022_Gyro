// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Climb;

/** Add your docs here. */
public class CMD_ClimbDown extends CommandBase{
    private SUB_Climb m_Climb;

    public CMD_ClimbDown(SUB_Climb p_Climb){
        m_Climb = p_Climb;
    }

    @Override
    public void initialize() {
        m_Climb.setClimberDown();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
