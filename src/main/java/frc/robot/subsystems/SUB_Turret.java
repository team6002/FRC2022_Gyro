package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
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

    private double center = 27
    ; //limelight res 320x240
    public int huntDirection = 1;

    //Network Table
    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

    //joystick
    XboxController joystick;

    public SUB_Turret(XboxController p_joystick){
        joystick = p_joystick;

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

    //Reads x and y from the network table
    public double readtX() { 
        double x = 0.0;
        try {
            x = table.getEntry("tx").getDouble(0.0);
        }
        catch(Exception e) {
            
        }
        
        return x;
    }

    public double readtY() {
        double y = 0.0;
        try {
            y = table.getEntry("ty").getDouble(0.0);
        }
        catch(Exception e) {
            
        }

        return y;
    }

    //reads if limelight sees any targets
    public double readtV(){
        double v = 0.0;
        try {
            v = table.getEntry("tv").getDouble(0.0);
        }
        catch(Exception e) {
            
        }

        return v;
    }

    //calculate how far the target is from center
    //right is negative, left is positive
    public double diffFromCenter() {
        return readtX() - (center + OFFSET);
    }

    public void setTurretMode(int wantedMode) {
        if(wantedMode == 1) {
            turretMode = wantedMode;
        } else if (wantedMode == 0) {
            turretMode = 0;
        } else if (wantedMode == 2) {
            turretMode = 2;
        }
    }

    public int getTurretMode() {
        return turretMode;
    }

    //2020 robot positions
    private double targetPosition = 0;
    private int turretMode = 2;

    public void setFrontPosition() {
        targetPosition = -11.857;
    }

    public void setBackPosition() {
        targetPosition = -45.285;
    }

    public void setSidePosition() {
        targetPosition = -28.309;
    }

    public void turretReset() {
        if(m_ReverseLimitSwitch.isPressed() == true) {
            m_Encoder.setPosition(0);
        }
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

    //set offset
    private int OFFSET = 0;
    public void setOffset(){
        OFFSET = 40;
    }

    @Override
    public void periodic() {
        turretReset();
        double targetX = readtX();
        double sentOutput = 0;
        double diffFromCenter = 0;

        if(turretMode == 0) {
            targetPosition = 0;
            if(readtV() == 0) { //no target found
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
                sentOutput = readtX() / center * TurretConstants.kTurretVoltage;
                if(m_ForwardLimitSwitch.isPressed() == true && sentOutput < 0)
                {
                    sentOutput = 0;
                }
                else if(m_ReverseLimitSwitch.isPressed() == true && sentOutput > 0)
                {
                    sentOutput = 0;
                }
            }
        }
        else if (turretMode == 1){ //manual position
            sentOutput = (m_Encoder.getPosition() - targetPosition) / -30* TurretConstants.kTurretMannualVoltage;

            if(sentOutput > 1) {
                sentOutput = TurretConstants.kTurretHuntVoltage;
            }

            if(m_ForwardLimitSwitch.isPressed() == true && sentOutput < 0)
            {
                sentOutput = 0;
            }
            else if(m_ReverseLimitSwitch.isPressed() == true && sentOutput > 0)
            {
                sentOutput = 0;
            }
        }
        else if(turretMode == 2) { //manual move
            double xVal = joystick.getLeftX();
            sentOutput = xVal * TurretConstants.kTurretJoystickVoltage;
        }

        
        m_Turret.setVoltage(sentOutput);

        //Shuffleboard Output
        SmartDashboard.putNumber("X", readtX());
        SmartDashboard.putNumber("Y", readtY());
        SmartDashboard.putNumber("Targets?", readtV());
        SmartDashboard.putNumber("Voltage", sentOutput);
        SmartDashboard.putNumber("Difference", diffFromCenter);
        // SmartDashboard.putNumber("Hunting Direction", huntDirection);
        SmartDashboard.putBoolean("Forward Limit Switch", m_ForwardLimitSwitch.isPressed());
        SmartDashboard.putBoolean("Reverse Limit Switch", m_ReverseLimitSwitch.isPressed());
        // SmartDashboard.putNumber("Turret Encoder", m_Encoder.getPosition());
        // SmartDashboard.putNumber("Target Encoder", targetPosition);
        SmartDashboard.putNumber("Turret Mode", turretMode);
    }

    //soft limit forward = 43
    //soft limit reverse = -16
}