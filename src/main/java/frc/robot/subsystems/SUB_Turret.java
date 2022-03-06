package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
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

    private double center = 80; //center of the camera (160x120)
    private boolean onTarget = false;
    public int huntDirection = 1;

    //Network Table
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Turret");

    //alliance color picker variable
    String RED = "RED";
    String BLUE = "BLUE";
    String bColor = "YOSHI";
    SendableChooser<String> m_color = new SendableChooser<>();

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

        //testing encoders (figure out converstion factor on real robo)
        // m_Encoder.setPositionConversionFactor((2.0 * Math.PI * 0.0381)/6.0);

        //add options to sendable chooser
        m_color.addOption("RED", RED);
        m_color.addOption("BLUE", BLUE);
        m_color.setDefaultOption("RED", RED);
        SmartDashboard.putData("chooser", m_color);
    }

    public void turretReset() {
        if(m_ReverseLimitSwitch.isPressed() == true) {
            m_Encoder.setPosition(0);
        }
    }

    //2020 robot positions
    private double targetPosition = 0;
    private int turretMode = 0;

    public void setFrontPosition() {
        targetPosition = -11.857;
    }

    public void setBackPosition() {
        targetPosition = -45.285;
    }

    public void setSidePosition() {
        targetPosition = -28.309;
    }

    public void increasePosition() {
        targetPosition++;
    }

    public void setTurretMode(int wantedMode) {
        if(wantedMode == 1) {
            turretMode = wantedMode;
        } else {
            turretMode = 0;
        }
    }

    public int getTurretMode() {
        return turretMode;
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
        return readcX() - (center + OFFSET);
    }

    //shooter ball color alliance thangy

    private boolean redBall = true;
    public void setBallColor(boolean col){
        redBall = col; 
    }

    //checks if the alliance color is red or blue
    public boolean checkChooser(){
        if (bColor == "RED"){
            return true;
        } else return false;
    }

    //check ball color **DO NOT USE COLOR SENSOR**
    // public boolean correctBall() {
    //     if(m_colorSensor.getColor() == Color && m_colorSensor.getColor() != "unknown")
    //     {
    //         return true;
    //     } else return false;
    // }

    //set offset
    private int OFFSET = 0;
    public void setOffset(){
        OFFSET = 40;
    }

    @Override
    public void periodic() {
        turretReset();
        double targetX = readcX();
        double sentOutput = 0;
        double diffFromCenter = 0;

        if(redBall != checkChooser()) {
            setOffset();
        } else {
            OFFSET = 0;
        }

        SmartDashboard.putBoolean("checkChooser", checkChooser());
        SmartDashboard.putString("ballcolor", bColor);
        bColor = m_color.getSelected();

        if(turretMode == 0) {
            targetPosition = 0;
            if(targetX == -1) {
                //no target found
                //move turret towards hunt direction, hunt direction -1 = counterclockwise +1 = clockwise
                if(m_ForwardLimitSwitch.isPressed() == true) {
                    setHuntDirection(1);
                }
                else if(m_ReverseLimitSwitch.isPressed() == true) {
                    setHuntDirection(-1);
                    turretReset();
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
                    turretReset();
                }
            }
        }
        else {
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
                turretReset();
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
        SmartDashboard.putBoolean("Ball color???", redBall);
        SmartDashboard.putNumber("Turret Encoder", m_Encoder.getPosition());
        SmartDashboard.putNumber("Target Encoder", targetPosition);
        SmartDashboard.putNumber("Turret Mode", turretMode);
    }

    //soft limit forward = 43
    //soft limit reverse = -16
}