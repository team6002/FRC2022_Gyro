package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Shooter;

public class CMD_ShooterReady extends CommandBase{
    SUB_Shooter m_Shooter;
    private double setpoint = 2000;

    public CMD_ShooterReady(SUB_Shooter p_Shooter)
    {
        m_Shooter = p_Shooter;
    }

    @Override
    public void initialize() {
        m_Shooter.shooterOn();
    }

    @Override
    public boolean isFinished() {
        return m_Shooter.getVelocity()/setpoint >= .95;
    }
}
