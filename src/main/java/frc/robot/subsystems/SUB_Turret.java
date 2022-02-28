package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurretConstants;

public class SUB_Turret extends SubsystemBase{
    //motors, encoders, PID controller, limit switches
    private CANSparkMax m_Turret = new CANSparkMax(TurretConstants.kTurretMotor, MotorType.kBrushless);
    private final RelativeEncoder m_Encoder = m_Turret.getEncoder();
    private SparkMaxPIDController m_Controller = m_Turret.getPIDController();
    private SparkMaxLimitSwitch m_ForwardLimitSwitch = m_Turret.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private SparkMaxLimitSwitch m_ReverseLimitSwitch = m_Turret.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);

    private double center = 80; //center of the camera (160x120)
    private boolean onTarget = false;
    public int huntDirection = 1;

    //ratio difference/center * the max voltage output = how much voltage to send the turret
    //public double sentOutput = diffFromCenter() / center * TurretConstants.kTurretVoltage;

    //Network Table
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Turret");

    public SUB_Turret(){
        m_Turret.setIdleMode(IdleMode.kBrake);
        m_Turret.setInverted(true);

        m_Controller.setFF(TurretConstants.kTurretFF);
        m_Controller.setP(TurretConstants.kTurretP);
        m_Controller.setI(TurretConstants.kTurretI);
        m_Controller.setD(TurretConstants.kTurretD);

        m_Controller.setOutputRange(TurretConstants.kMinTurretOutput, TurretConstants.kMaxTurretOutput);

        m_ForwardLimitSwitch.enableLimitSwitch(false);
        m_ReverseLimitSwitch.enableLimitSwitch(false);
    }

    //sets the which way the turret should turn to find a target
    public void setHuntDirection(int dir) {
        if(dir == 1) {
            huntDirection = 1;
        }
        else {
            huntDirection = -1;
        }
    }

    //Reads from the network table
    public double readcX() { 
        double x = -1;
        try {
            x = table.getEntry("cX").getDouble(-1);
        }
        catch(Exception e) {
            
        }
        
        return x;
    }

    public double readcY() {
        return table.getEntry("cY").getDouble(-1);
    }

    //calculate how far the target is from center
    //right is negative, left is positive
    public double diffFromCenter() {
        return readcX() - center;
    }

    @Override
    public void periodic() {
        double targetX = readcX();
        double diffFromCenter = 0;
        double sentOutput = 0;
        if(targetX == -1) {
            //no target found
            //move turret towards hunt direction, hunt direction -1 = counterclockwise +1 = clockwise
            if(m_ForwardLimitSwitch.isPressed() == true) {
                setHuntDirection(1);
            }
            else if(m_ReverseLimitSwitch.isPressed() == true) {
                setHuntDirection(-1);
            }

            diffFromCenter = -999;
            sentOutput = huntDirection * TurretConstants.kTurretHuntVoltage;
        }
        else {
            diffFromCenter = diffFromCenter();
            sentOutput = diffFromCenter / center * TurretConstants.kTurretVoltage;
            if(m_ForwardLimitSwitch.isPressed() == true && sentOutput < 0)
            {
                sentOutput = 0;
            }
            else if(m_ReverseLimitSwitch.isPressed() == true && sentOutput > 0)
            {
                sentOutput = 0;
            }
        }

        m_Turret.setVoltage(sentOutput);

        //Shuffleboard Output
        SmartDashboard.putNumber("X", readcX());
        SmartDashboard.putNumber("Y", readcY());
        SmartDashboard.putNumber("Voltage", sentOutput);
        SmartDashboard.putNumber("Difference", diffFromCenter);
        SmartDashboard.putBoolean("Target?", onTarget);
        SmartDashboard.putNumber("Hunting Direction", huntDirection);
        SmartDashboard.putBoolean("Forward Limit Switch", m_ForwardLimitSwitch.isPressed());
        SmartDashboard.putBoolean("Reverse Limit Switch", m_ReverseLimitSwitch.isPressed());
    }

    //soft limit forward = 43
    //soft limit reverse = -16
}