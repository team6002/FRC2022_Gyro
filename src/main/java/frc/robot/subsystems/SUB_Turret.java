package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;

public class SUB_Turret {
    private CANSparkMax m_Turret = new CANSparkMax(DriveConstants.kTurretMotor, MotorType.kBrushless);
    
    public SUB_Turret()
    {
        m_Turret.restoreFactoryDefaults();
        m_Turret.setIdleMode(IdleMode.kBrake);
    }

    

}
