package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.SUB_Shooter;

public class CMD_ShooterReady extends CommandBase{
    SUB_Shooter m_Shooter;

    public CMD_ShooterReady(SUB_Shooter p_Shooter)
    {
        m_Shooter = p_Shooter;
    }

    @Override
    public void initialize() {
        m_Shooter.readyShooter();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("Shooter Velocity", m_Shooter.getVelocity());
    }

    @Override
    public boolean isFinished() {
        return m_Shooter.isReady(DriveConstants.kShootingVelocity, 150);
    }
}
