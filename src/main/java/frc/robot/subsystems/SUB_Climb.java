// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

/** Add your docs here. */
public class SUB_Climb {
    private Solenoid m_Solenoid = new Solenoid(PneumaticsModuleType.CTREPCM,2);
    private Solenoid m_WinchRatchet = new Solenoid(PneumaticsModuleType.CTREPCM, 0);

    public SUB_Climb(){

    }

    public void setClimberUp(){
        m_Solenoid.set(true);
    }

    public void setClimberDown(){
        m_Solenoid.set(false);
    }

    public void setReleaseWinchRatchet(){
        m_WinchRatchet.set(true);
    }

    public void setEngageWinchRatchet(){
        m_WinchRatchet.set(false);
    }
}
