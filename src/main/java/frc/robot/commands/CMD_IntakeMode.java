package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SUB_Indexer;
import frc.robot.subsystems.SUB_Intake;

public class CMD_IntakeMode extends CommandBase{
    private SUB_Intake m_Intake;
    private SUB_Indexer m_Indexer;

    public CMD_IntakeMode(SUB_Intake p_Intake, SUB_Indexer p_Indexer)
    {
        m_Intake = p_Intake;
        m_Indexer = p_Indexer;
    }

    @Override
    public void initialize() {
        m_Intake.setIntakeMode();
        m_Indexer.setIndexerForward();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
