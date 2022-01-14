package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Indexer;

public class CMD_IndexerForward extends CommandBase{
    private SUB_Indexer m_Indexer;

    public CMD_IndexerForward(SUB_Indexer p_Indexer)
    {
        m_Indexer = p_Indexer;
    }
    
    @Override
    public void initialize() {
        m_Indexer.setIndexerForward();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
}
