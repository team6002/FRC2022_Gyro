package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.IndexerConstants;

public class SUB_Indexer {
    private CANSparkMax m_IndexerTop = new CANSparkMax(IndexerConstants.kIndexerTop, MotorType.kBrushless);
    private CANSparkMax m_IndexerBottom = new CANSparkMax(IndexerConstants.kIndexerBottom, MotorType.kBrushless);
    private CANSparkMax m_IndexerBack = new CANSparkMax(IndexerConstants.kIndexerBack, MotorType.kBrushless);

    public SUB_Indexer(){
        System.out.println("invert indexer");
        m_IndexerBottom.setInverted(true);
        m_IndexerTop.setInverted(true);
        m_IndexerBack.setInverted(true);
    }

    public boolean getInverted()
    {
        return m_IndexerBack.getInverted();
    }

    public void setIndexerForward()
    {
        m_IndexerTop.set(IndexerConstants.kIndexerFSpeed);
        m_IndexerBottom.set(IndexerConstants.kIndexerFSpeed);
    }

    public void setIndexerReverse()
    {
        m_IndexerTop.set(IndexerConstants.kIndexerRSpeed);
        m_IndexerBottom.set(IndexerConstants.kIndexerRSpeed);
        m_IndexerBack.set(IndexerConstants.kIndexerRSpeed);
    }

    public void setFeedShooter()
    {
        m_IndexerTop.set(IndexerConstants.kIndexerFSpeed);
        m_IndexerBottom.set(IndexerConstants.kIndexerRSpeed);
        m_IndexerBack.set(IndexerConstants.kIndexerRSpeed);
    }

    public void setBackOn()
    {
        m_IndexerBack.set(IndexerConstants.kIndexerFSpeed);
    }

    public void setBackOff()
    {
        m_IndexerBack.set(0);
    }

    public void setIndexerOff()
    {
        m_IndexerTop.set(0);
        m_IndexerBottom.set(0);
        m_IndexerBack.set(0);
    }
}
