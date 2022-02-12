package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Turret;

public class CMD_HuntRight extends CommandBase{
    SUB_Turret m_Turret;

    public CMD_HuntRight(SUB_Turret p_Turret)
    {
        m_Turret = p_Turret;
    }

    @Override
    public void initialize() {
        m_Turret.huntRight();
        m_Turret.hunt();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
    
}