package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FSM_Robot;

public class CMD_SetRobotState extends CommandBase{
    private final FSM_Robot m_FSM_Robot;
    private final FSM_Robot.State m_DesireState;

    public CMD_SetRobotState(FSM_Robot p_FSM_Robot, FSM_Robot.State desireState)
    {
        m_FSM_Robot = p_FSM_Robot;
        m_DesireState = desireState;
    }

    @Override
    public void initialize() {
        m_FSM_Robot.setState(m_DesireState);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
