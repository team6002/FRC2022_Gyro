package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.TurretConstants;

public class SUB_Turret extends SubsystemBase{
    private CANSparkMax m_Turret = new CANSparkMax(TurretConstants.kTurretMotor, MotorType.kBrushless);
    private final RelativeEncoder m_Encoder = m_Turret.getEncoder();
    private SparkMaxPIDController m_Controller = m_Turret.getPIDController();

    private double center = 80; //center of the camera (160x120)
    private boolean onTarget = false;
    public int huntDirection = 1;

    //ratio difference/center * the max voltage output = how much voltage to send the turret
    public double sentOutput = diffFromCenter() / center * TurretConstants.kTurretVoltage;

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
    }

    //Reads from the network table
    // double x = table.getEntry("cX").getDouble(-1);

    public double readcX() { //doesn't work
        double x = -1;
        try {
            x = table.getEntry("cX").getDouble(-1);
        }
        catch(Exception e) {
            // code
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

    public boolean isCentered() {
        if(Math.abs(diffFromCenter()) < 2) {
            onTarget = true;
        }
        else {
            onTarget = false;
        }

        return onTarget;
    }

    public void setHuntDirection(int dir) {
        if(dir == 1) {
            huntDirection = 1;
        }
        else {
            huntDirection = -1;
        }
    }

    //Check to see if you can grab data from rasppi (E: yup)
    @Override
    public void periodic() {
        double targetX = readcX();
        double diffFromCenter = 0;
        if(targetX == -1) {
            //no target found
            //move turret towards hunt direction, hunt direction -1 = counterclockwise +1 = clockwise
            diffFromCenter = -999;
            sentOutput = huntDirection * TurretConstants.kTurretHuntVoltage;
        }
        else {
            diffFromCenter = diffFromCenter();
            sentOutput = diffFromCenter / center * TurretConstants.kTurretVoltage;
        }

        m_Turret.setVoltage(sentOutput);

        SmartDashboard.putNumber("X", readcX());
        SmartDashboard.putNumber("Y", readcY());
        SmartDashboard.putNumber("Voltage", sentOutput);
        SmartDashboard.putNumber("Difference", diffFromCenter);
        SmartDashboard.putBoolean("Target?", onTarget);
        SmartDashboard.putNumber("Hunting Direction", huntDirection);
    }

    //soft limit forward = 51
    //soft limit reverse = -7
}
