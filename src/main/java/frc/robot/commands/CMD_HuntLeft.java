package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Turret;

public class CMD_HuntLeft extends CommandBase{
    SUB_Turret m_Turret;

    public CMD_HuntLeft(SUB_Turret p_Turret)
    {
        m_Turret = p_Turret;
    }

    @Override
    public void initialize() {
        m_Turret.huntLeft();
        m_Turret.hunt();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
    
}