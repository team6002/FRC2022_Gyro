// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class SUB_GearShift extends SubsystemBase{
    private final Solenoid m_Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 3);

    public SUB_GearShift(){
        
    }

    public void setHighGear(){
        m_Solenoid.set(true);
    }

    public void setLowGear(){
        m_Solenoid.set(false);
    }
}
