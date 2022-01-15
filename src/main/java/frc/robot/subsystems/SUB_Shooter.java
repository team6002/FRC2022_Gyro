package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
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

        m_ShooterMaster.setIdleMode(IdleMode.kCoast);
        m_ShooterSlave.setIdleMode(IdleMode.kCoast);

        m_ShooterSlave.follow(m_ShooterMaster, true);

        m_Controller.setFF(DriveConstants.kShooterFF);
        m_Controller.setP(DriveConstants.kShooterP);
        m_Controller.setI(DriveConstants.kShooterI);
        m_Controller.setD(DriveConstants.kShooterD);

        m_Controller.setOutputRange(DriveConstants.kMinOutput, DriveConstants.kMaxOutput);
        m_Controller.setSmartMotionMaxVelocity(DriveConstants.kShootingVelocity, 0);
        m_Controller.setSmartMotionMaxAccel(DriveConstants.kShootingAccel, 0);
    }

    public void shooterOn()
    {
        m_ShooterMaster.set(DriveConstants.kShooterSpeed);
    }

    public void shooterOff()
    {
        m_ShooterMaster.set(0);
    }

    public void readyShooter()
    {
        m_Controller.setReference(DriveConstants.kShootingVelocity, CANSparkMax.ControlType.kVelocity);
    }

    public boolean isReady(double setpoint, double epsilon)
    {
        return (getVelocity() - epsilon <= setpoint) && (getVelocity() + epsilon >= setpoint);
    }

    public double getVelocity()
    {
        return m_ShooterMasterEncoder.getVelocity();
    }
}
