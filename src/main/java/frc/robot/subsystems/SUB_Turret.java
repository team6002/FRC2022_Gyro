package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
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

    //ratio difference/center * the max voltage output = how much voltage to send the turret
    public double sentOutput = diffFromCenter() / center * TurretConstants.kTurretVoltage;

    //Network Table
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Shooter");
    NetworkTableEntry cXEntry, cYEntry;

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
    public double readcX(){
        return table.getEntry("cX").getDouble(-1);
    }

    public double readcY(){
        return table.getEntry("cY").getDouble(-1);
    }

    //calculate how far the target is from center
    //right is negative, left is positive
    public double diffFromCenter() {
        return readcX() - center;
    }

    public boolean isCentered() {
        if(diffFromCenter() == center) {
            onTarget = true;
            m_Turret.setVoltage(0);
        }
        else {
            onTarget = false;
            m_Turret.setVoltage(diffFromCenter() / center * TurretConstants.kTurretVoltage);
        }
        return onTarget;
    }


    //hint left and right functions
    //huntDirection(-1, 1)
    //if -1: counter clock, 1: clock
    //if hits limit switch, *-1

    public int huntDirection = 1;
    public void huntLeft() {
        huntDirection = -1;
    }

    public void huntRight() {
        huntDirection = 1;
    }

    public void hunt() {
        m_Turret.setVoltage(TurretConstants.kTurretHuntVoltage * huntDirection);
    }


    //Check to see if you can grab data from rasppi (E: yup)
    @Override
    public void periodic() {
        sentOutput = diffFromCenter() / center * TurretConstants.kTurretVoltage;
        diffFromCenter();
        isCentered();

        SmartDashboard.putNumber("cX", readcX());
        SmartDashboard.putNumber("cY", readcY());
        SmartDashboard.putNumber("Voltage", sentOutput);
        SmartDashboard.putNumber("Difference", diffFromCenter());

        //soft limit forward = 51
        //soft limit reverse = -7
    }
}
