package frc.robot.subsystems;

public class FSM_Robot {

    public enum State 
    {
        HOME
        , INTAKE 
        , SHOOT 
        , TRANSITIONING
    }

    private State m_currentState = State.HOME;

    public void setState(State p_State) {
        m_currentState = p_State;
    }
    
    public State getState() {
        return m_currentState;
    }
    
    public boolean getState(State p_State) {
        return (m_currentState == p_State);
    }

    public boolean getState(String p_State) {
        return (m_currentState.toString().equals(p_State));
    }

}
