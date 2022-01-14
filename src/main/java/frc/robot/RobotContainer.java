// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.wpilibj.XboxController.Button;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem m_robotDrive = new DriveSubsystem();
  private final SUB_GearShift m_GearShift = new SUB_GearShift();
  private final SUB_Climb m_Climb = new SUB_Climb();
  private final SUB_Intake m_Intake = new SUB_Intake();
  private final SUB_Indexer m_Indexer = new SUB_Indexer();
  private final SUB_Shooter m_Shooter = new SUB_Shooter();

  // The driver's controller
  XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    // Set the default drive command to split-stick arcade drive
    m_robotDrive.setDefaultCommand(
        // A split-stick arcade command, with forward/backward controlled by the left
        // hand, and turning controlled by the right.
        new RunCommand(
            () ->
                m_robotDrive.arcadeDrive(
                    m_driverController.getLeftY(),
                    -m_driverController.getRightX()),
            m_robotDrive));
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Drive at half speed when the right bumper is held
    new JoystickButton(m_driverController, Button.kRightBumper.value)
        .whenPressed(() -> m_robotDrive.setMaxOutput(0.5))
        .whenReleased(() -> m_robotDrive.setMaxOutput(1));

    // Stabilize robot to drive straight with gyro when left bumper is held
    new JoystickButton(m_driverController, Button.kLeftBumper.value)
        .whenHeld(
            new PIDCommand(
                new PIDController(
                    DriveConstants.kStabilizationP,
                    DriveConstants.kStabilizationI,
                    DriveConstants.kStabilizationD),
                // Close the loop on the turn rate
                m_robotDrive::getTurnRate,
                // Setpoint is 0
                0,
                // Pipe the output to the turning controls
                output ->
                    m_robotDrive.arcadeDrive(
                        m_driverController.getLeftY(), output),
                // Require the robot drive
                m_robotDrive));

    // Turn to 90 degrees when the 'X' button is pressed, with a 5 second timeout
    // new JoystickButton(m_driverController, Button.kX.value)
    //     .whenPressed(new TurnToAngle(90, m_robotDrive).withTimeout(5));

    // Turn to -90 degrees with a profile when the 'A' button is pressed, with a 5 second timeout
    // new JoystickButton(m_driverController, Button.kA.value)
    //     .whenPressed(new TurnToAngleProfiled(-90, m_robotDrive).withTimeout(5));
    
    new JoystickButton(m_driverController, Button.kLeftBumper.value)
        .whenPressed(new CMD_DrivetrainSetHighGear(m_GearShift))
        .whenReleased(new CMD_DrivetrainSetLowGear(m_GearShift));
        
    // //testing climb
    // new JoystickButton(m_driverController, Button.kB.value)
    //     .whenPressed(new SequentialCommandGroup(new CMD_ReleaseWinchRatchet(m_Climb), new CMD_ClimbUp(m_Climb)))
    //     .whenReleased(new CMD_ClimbDown(m_Climb));

    // //testing intake
    // new JoystickButton(m_driverController, Button.kRightBumper.value)
    //     .whenPressed(new CMD_DeployIntake(m_Intake))
    //     .whenReleased(new CMD_RetractIntake(m_Intake));
    // new JoystickButton(m_driverController, Button.kY.value) 
    //     .whenPressed(new CMD_IntakeForward(m_Intake));
    // new JoystickButton(m_driverController, Button.kA.value)
    //     .whenPressed(new CMD_IntakeReverse(m_Intake));
    // new JoystickButton(m_driverController, Button.kX.value)
    //     .whenPressed(new CMD_IntakeOff(m_Intake));

    //testing indexer
    // new JoystickButton(m_driverController, Button.kA.value)
    //     .whenPressed(new CMD_IndexerForward(m_Indexer))
    //     .whenReleased(new CMD_IndexerOff(m_Indexer));
    // new JoystickButton(m_driverController, Button.kB.value)
    //     .whenPressed(new CMD_IndexerReverse(m_Indexer))
    //     .whenReleased(new CMD_IndexerOff(m_Indexer));
    // new JoystickButton(m_driverController, Button.kY.value)
    //     .whenPressed(new CMD_IndexerOff(m_Indexer));

    //testing intake mode
    new JoystickButton(m_driverController, Button.kX.value)
        .whenPressed(new SequentialCommandGroup(new CMD_ShooterReady(m_Shooter)
            , new CMD_FeedShooter(m_Indexer)));
        // .whenPressed(new CMD_IntakeMode(m_Intake, m_Indexer))
        // .whenReleased(new CMD_IntakeModeOff(m_Intake, m_Indexer));
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // no auto
    return new InstantCommand();
  }
}
