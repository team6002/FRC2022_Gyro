// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveSubsystem extends SubsystemBase {
  // The motors on the left side of the drive.

  private CANSparkMax m_motor_L1 = new CANSparkMax(DriveConstants.kLeftMotor1Port, MotorType.kBrushless);
  private CANSparkMax m_motor_L2 = new CANSparkMax(DriveConstants.kLeftMotor2Port, MotorType.kBrushless);
  private CANSparkMax m_motor_R1 = new CANSparkMax(DriveConstants.kRightMotor1Port, MotorType.kBrushless);
  private CANSparkMax m_motor_R2 = new CANSparkMax(DriveConstants.kRightMotor2Port, MotorType.kBrushless);

  

  private final MotorControllerGroup m_leftMotors =
      new MotorControllerGroup(
          m_motor_L1,
          m_motor_L2);

  // The motors on the right side of the drive.
  private final MotorControllerGroup m_rightMotors =
      new MotorControllerGroup(
        m_motor_R1,
        m_motor_R2);

  // The robot's drive
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

  // The left-side drive encoder
  private final RelativeEncoder m_leftEncoder = m_motor_L1.getEncoder();

  // The right-side drive encoder
  private final RelativeEncoder m_rightEncoder = m_motor_R1.getEncoder();

  // The gyro sensor
  private final Gyro m_gyro = new ADXRS450_Gyro();

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {
    // Sets the distance per pulse for the encoders
    m_leftEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);
    m_rightEncoder.setPositionConversionFactor(DriveConstants.kEncoderDistancePerPulse);

    m_motor_L1.setInverted(true);
    m_motor_L2.setInverted(true);
    m_motor_R1.setInverted(true);
    m_motor_R2.setInverted(true);
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param fwd the commanded forward movement
   * @param rot the commanded rotation
   */
  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  /** Resets the drive encoders to currently read a position of 0. */
  // public void resetEncoders() {
  //   m_leftEncoder.reset();
  //   m_rightEncoder.reset();
  // }

  /**
   * Gets the average distance of the two encoders.
   *
   * @return the average of the two encoder readings
   */
  // public double getAverageEncoderDistance() {
  //   return (m_leftEncoder.getDistance() + m_rightEncoder.getDistance()) / 2.0;
  // }

  /**
   * Gets the left drive encoder.
   *
   * @return the left drive encoder
   */
  public RelativeEncoder getLeftEncoder() {
    return m_leftEncoder;
  }

  /**
   * Gets the right drive encoder.
   *
   * @return the right drive encoder
   */
  public RelativeEncoder getRightEncoder() {
    return m_rightEncoder;
  }

  /**
   * Sets the max output of the drive. Useful for scaling the drive to drive more slowly.
   *
   * @param maxOutput the maximum output to which the drive will be constrained
   */
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    m_gyro.reset();
  }

  /**
   * Returns the heading of the robot.
   *
   * @return the robot's heading in degrees, from 180 to 180
   */
  public double getHeading() {
    return Math.IEEEremainder(m_gyro.getAngle(), 360) * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }

  /**
   * Returns the turn rate of the robot.
   *
   * @return The turn rate of the robot, in degrees per second
   */
  public double getTurnRate() {
    return m_gyro.getRate() * (DriveConstants.kGyroReversed ? -1.0 : 1.0);
  }
}
