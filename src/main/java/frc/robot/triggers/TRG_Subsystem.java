package frc.robot.triggers;

import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.subsystems.FSM_Robot;

public class TRG_Subsystem extends Button{
    private FSM_Robot m_FSM_Robot;
    private String m_wantedState;

    public TRG_Subsystem(FSM_Robot p_FSM_Robot, String p_wantedState)
    {
        m_FSM_Robot = p_FSM_Robot;
        m_wantedState = p_wantedState;
    }

    @Override
    public boolean get()
    {
        return m_FSM_Robot.getState(m_wantedState);
    }
}
