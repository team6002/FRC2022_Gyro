// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


/** Add your docs here. */
public class SUB_Intake extends SubsystemBase {
    private final Solenoid m_IntakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 1);
    private TalonSRX m_IntakeMotor = new TalonSRX(13);
    
    public SUB_Intake() { 
        
    }
    
    public void setDeployIntake(){
        m_IntakeSolenoid.set(true);
    }
    
    public void setRetractIntake(){
        m_IntakeSolenoid.set(false);
    }

    public void setIntakeForward(){
        m_IntakeMotor.set(ControlMode.PercentOutput,0.3);
    }

    public void setIntakeReverse(){
        m_IntakeMotor.set(ControlMode.PercentOutput,-0.3);
    }
    public void setIntakeOff(){
        m_IntakeMotor.set(ControlMode.PercentOutput, 0);
    }
}
        