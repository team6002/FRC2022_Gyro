package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.DriveConstants;

public class SUB_Indexer {
    private CANSparkMax m_IndexerTop = new CANSparkMax(DriveConstants.kIndexerTop, MotorType.kBrushless);
    private CANSparkMax m_IndexerBottom = new CANSparkMax(DriveConstants.kIndexerBottom, MotorType.kBrushless);
    private CANSparkMax m_IndexerBack = new CANSparkMax(DriveConstants.kIndexerBack, MotorType.kBrushless);

    public SUB_Indexer(){
        m_IndexerBottom.setInverted(true);
        m_IndexerTop.setInverted(true);
        m_IndexerBack.setInverted(true);
    }

    public void setIndexerForward()
    {
        m_IndexerTop.set(DriveConstants.kIndexerFSpeed);
        m_IndexerBottom.set(DriveConstants.kIndexerFSpeed);
        m_IndexerBack.set(DriveConstants.kIndexerFSpeed);
    }

    public void setIndexerReverse()
    {
        m_IndexerTop.set(DriveConstants.kIndexerRSpeed);
        m_IndexerBottom.set(DriveConstants.kIndexerRSpeed);
        m_IndexerBack.set(DriveConstants.kIndexerRSpeed);
    }

    public void setFeedShooter()
    {
        m_IndexerTop.set(DriveConstants.kIndexerFSpeed);
        m_IndexerBottom.set(DriveConstants.kIndexerRSpeed);
        m_IndexerBack.set(DriveConstants.kIndexerRSpeed);
    }

    public void setIndexerOff()
    {
        m_IndexerTop.set(0);
        m_IndexerBottom.set(0);
        m_IndexerBack.set(0);
    }
}
