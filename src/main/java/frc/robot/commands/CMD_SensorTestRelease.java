package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.triggers.DigitalSensor;

public class CMD_SensorTestRelease extends CommandBase{
    DigitalSensor m_sensor;

    public CMD_SensorTestRelease(DigitalSensor p_sensor)
    {
        m_sensor = p_sensor;
    }

    @Override
    public void initialize()
    {
        System.out.println("IS RELEASED");
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
