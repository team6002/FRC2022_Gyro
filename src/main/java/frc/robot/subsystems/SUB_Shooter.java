package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.ShooterConstants;

public class SUB_Shooter {
    private CANSparkMax m_ShooterMaster = new CANSparkMax(ShooterConstants.kShooterMaster, MotorType.kBrushless);
    private CANSparkMax m_ShooterSlave = new CANSparkMax(ShooterConstants.kShooterSlave, MotorType.kBrushless);

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

        m_Controller.setFF(ShooterConstants.kShooterFF);
        m_Controller.setP(ShooterConstants.kShooterP);
        m_Controller.setI(ShooterConstants.kShooterI);
        m_Controller.setD(ShooterConstants.kShooterD);

        m_Controller.setOutputRange(ShooterConstants.kMinOutput, ShooterConstants.kMaxOutput);
        m_Controller.setSmartMotionMaxVelocity(ShooterConstants.kShootingVelocity, 0);
        m_Controller.setSmartMotionMaxAccel(ShooterConstants.kShootingAccel, 0);
    }

    public void shooterOn()
    {
        m_ShooterMaster.set(ShooterConstants.kShooterSpeed);
    }

    public void shooterOff()
    {
        m_ShooterMaster.set(0);
    }

    public void readyShooter()
    {
        m_Controller.setReference(ShooterConstants.kShootingVelocity, CANSparkMax.ControlType.kVelocity);
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
