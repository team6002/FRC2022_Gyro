package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.DriveConstants;

public class SUB_Shooter {
    private CANSparkMax m_ShooterMaster = new CANSparkMax(DriveConstants.kShooterMaster, MotorType.kBrushless);
    private CANSparkMax m_ShooterSlave = new CANSparkMax(DriveConstants.kShooterSlave, MotorType.kBrushless);

    private RelativeEncoder m_ShooterMasterEncoder = m_ShooterMaster.getEncoder();
    private RelativeEncoder m_ShooterSlaveEncoder = m_ShooterSlave.getEncoder();

    private SparkMaxPIDController m_Controller = m_ShooterMaster.getPIDController();

    public SUB_Shooter()
    {
        m_ShooterMaster.restoreFactoryDefaults();
        m_ShooterSlave.restoreFactoryDefaults();

        m_ShooterSlave.follow(m_ShooterMaster, true);

        m_Controller.setP(DriveConstants.kShooterP);
        m_Controller.setI(DriveConstants.kShooterI);
        m_Controller.setD(DriveConstants.kShooterD);
    }

    public void shooterOn()
    {
        m_ShooterMaster.set(DriveConstants.kShooterSpeed);
    }

    public double getVelocity()
    {
        return m_ShooterMasterEncoder.getVelocity() * -1;
    }

    public void shooterOff()
    {
        m_ShooterMaster.set(0);
    }
}
