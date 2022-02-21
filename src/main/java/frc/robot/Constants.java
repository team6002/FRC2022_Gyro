// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    //drive motors
    public static final int kLeftMotor1Port = 15;
    public static final int kLeftMotor2Port = 14;
    public static final int kRightMotor1Port = 16;
    public static final int kRightMotor2Port = 1;

    //drive encoders
    public static final int[] kLeftEncoderPorts = new int[] {0, 1};
    public static final int[] kRightEncoderPorts = new int[] {2, 3};
    public static final boolean kLeftEncoderReversed = false;
    public static final boolean kRightEncoderReversed = true;

    public static final int kEncoderCPR = 1024;
    public static final double kWheelDiameterInches = 6;
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
    
    //gyro
    public static final boolean kGyroReversed = false;

    public static final double kStabilizationP = 1;
    public static final double kStabilizationI = 0.5;
    public static final double kStabilizationD = 0;

    public static final double kTurnP = 1;
    public static final double kTurnI = 0;
    public static final double kTurnD = 0;

    public static final double kMaxTurnRateDegPerS = 100;
    public static final double kMaxTurnAccelerationDegPerSSquared = 300;

    public static final double kTurnToleranceDeg = 5;
    public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final class IndexerConstants {
    //indexer motors
    public static final int kIndexerTop = 10;
    public static final int kIndexerBottom = 4;
    public static final int kIndexerBack = 18;

    //indexer speeds
    public static final double kIndexerFSpeed = 0.5;
    public static final double kIndexerRSpeed = -0.3;
  }

  public static final class ShooterConstants {
    //shooter
    public static final int kShooterMaster = 2;
    public static final int kShooterSlave = 3;

    //shooter PID
    public static final double kShooterFF = 0.000180; //Danny's numbers
    public static final double kShooterP = 0.00068;
    public static final double kShooterI = 0; 
    public static final double kShooterD = 0.00000750;

    //shooter speeds
    public static final double kShootingVelocity = 2200;
    public static final double kShootingAccel = 1000;

    public static final double kMinOutput = -1;
    public static final double kMaxOutput = 1;

    public static final double kShooterSpeed = 0.3;
  }

  public static final class TurretConstants {
    //turret
    public static final int kTurretMotor = 5;

    //turret PID
    public static final double kTurretFF = 0;
    public static final double kTurretP = 0.0005;
    public static final double kTurretI = 0.00000;
    public static final double kTurretD = 0.00000;

    //turret speeds
    public static final double kMinTurretOutput = -1;
    public static final double kMaxTurretOutput = 1;

    public static final double kTurretVoltage = 5;
    public static final double kTurretHuntVoltage = 3;
  }
}
