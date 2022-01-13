// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_GearShift;

/** Add your docs here. */
public class CMD_DrivetrainSetHighGear extends CommandBase {
    private SUB_GearShift m_GearShift;

    public CMD_DrivetrainSetHighGear(SUB_GearShift p_GearShift){
        m_GearShift = p_GearShift;
    }

    @Override
    public void initialize() {
        m_GearShift.setHighGear();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
