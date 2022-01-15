package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Indexer;

public class CMD_IndexerBackOn extends CommandBase{
    SUB_Indexer m_Indexer;

    public CMD_IndexerBackOn(SUB_Indexer p_Indexer)
    {
        m_Indexer = p_Indexer;
    }

    @Override
    public void initialize() {
        m_Indexer.setBackOn();
    }


    @Override
    public boolean isFinished() {
        return true;
    }
    
}
