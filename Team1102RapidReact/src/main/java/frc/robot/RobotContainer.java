// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.Climber.ClimberCommands;
import frc.robot.commands.Climber.Stage1;
import frc.robot.commands.Autonomous;
import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Infastructure;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final Drivetrain driveTrain = new Drivetrain();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Turret turret = new Turret();
  private final Shooter shooter = new Shooter();
  private final Climber climber = new Climber();
  private final ClimberArm arm = new ClimberArm();
  private final Infastructure base = new Infastructure();
  
  private final Autonomous m_autoCommand = new Autonomous(driveTrain, intake, feeder, shooter, turret);
  private final ClimberCommands climb = new ClimberCommands(climber, arm);

  private final Joystick driver1 = new Joystick(0);

  private final XboxController operator = new XboxController(1);


  
  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    base.setDefaultCommand(new RunCommand(base::Enabled, base));

    driveTrain.setDefaultCommand(new DefaultDrive(
      driveTrain,
      () -> driver1.getRawAxis(1) * -1, 
      () -> driver1.getRawAxis(2),
      () -> driver1.getRawButton(7),
      () -> climber.ClimberEnabled()));

    turret.setDefaultCommand(new RunCommand(
      () -> turret.Rotate_OpenLoop(operator.getRawAxis(4), climber.ClimberEnabled()), turret)); 

    climber.setDefaultCommand(new RunCommand(
      () -> climber.ClimberOpenLoop(operator.getRawAxis(1) * -1), climber));

    arm.setDefaultCommand(new RunCommand(
      () -> arm.ClimberOpenLoop(driver1.getRawAxis(5)), arm));

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    new JoystickButton(driver1, 5)
    .whenPressed(new InstantCommand(intake::IntakeIn, intake))
    .whenPressed(new InstantCommand(intake::RearTunnelIn, intake))
    .whenReleased(new InstantCommand(intake::IntakeStop, intake))
    .whenReleased(new InstantCommand(intake::IntakeTunnelStop, intake));

    new JoystickButton(driver1, 6)
    .whenPressed(new InstantCommand(intake::IntakeOut, intake))
    .whenPressed(new InstantCommand(intake::IntakeTunnelOut, intake))
    .whenPressed(new InstantCommand(feeder::CenterFeederReverse, feeder))
    .whenReleased(new InstantCommand(intake::IntakeStop, intake))
    .whenReleased(new InstantCommand(intake::IntakeTunnelStop, intake))
    .whenReleased(new InstantCommand(feeder::CenterFeederStop, feeder));

    new JoystickButton(operator, 5)
    .whileHeld(new InstantCommand(() -> shooter.ShooterAutoRange(turret.limelight_range()), shooter))
    .whileHeld(new InstantCommand(() -> feeder.Feed(shooter.ShooterReady()), feeder))
    .whileHeld(new InstantCommand(() -> intake.Shoot(shooter.ShooterReady()), intake))
    .whileHeld(new InstantCommand(base::Disable, base))
    .whileHeld(new RunCommand(() -> turret.NearLimelightControl(operator.getRawAxis(0))))
    .whenReleased(new InstantCommand(turret::turret_Stop, turret)) 
    .whenReleased(new InstantCommand(intake::ShootFinished, intake))
    .whenReleased(new InstantCommand(feeder::StopFeed, feeder))
    .whenReleased(new InstantCommand(base::Enabled, base))
    .whenReleased(new InstantCommand(() -> shooter.ShooterTarget(0), shooter));


    new JoystickButton(operator, 6)
    .whenPressed(new InstantCommand(() -> shooter.ShooterTarget(Constants.ShooterSpeedClose), shooter))
    .whenPressed(new InstantCommand(() -> shooter.ShooterHood_toPosition(Constants.ShooterHoodClose), shooter))
    .whileHeld(new InstantCommand(() -> feeder.Feed(shooter.ShooterReady()), feeder))
    .whileHeld(new InstantCommand(() -> intake.Shoot(shooter.ShooterReady()), intake))
    .whenReleased(new InstantCommand(intake::ShootFinished, intake))
    .whenReleased(new InstantCommand(feeder::StopFeed, feeder))
    .whenReleased(new InstantCommand(() -> shooter.ShooterTarget(0), shooter));

    new POVButton(operator, 0)
    .whenPressed(climb.pullUpFromGround());

    new JoystickButton(operator, 1)
    .whileHeld(new RunCommand(() -> turret.NearLimelightControl(operator.getRawAxis(0))))
    .whenReleased(new InstantCommand(turret::turret_Stop, turret));

    new JoystickButton(operator, 7)
    .whileHeld(new InstantCommand(climber::ClimberInit, climber))
    .whenReleased(new InstantCommand(climber::ClimberPoleStop, climber));

    new JoystickButton(operator, 8)
    .whenPressed(new InstantCommand(climber::ClimberEnable, climber))
    .whenPressed(new InstantCommand(arm::ClimberArmEnable, arm));

    new JoystickButton(operator, 4)
    .whileActiveOnce(new InstantCommand(climber::ClimberExtend, climber));
    // .whenPressed(new RunCommand(() -> climber.ClimberArmToSetpoint(Constants.ArmStandby), climber))
    // .whenReleased(new InstantCommand(climber::ClimberArmStop, climber));

    new JoystickButton(operator, 3)
    .whileActiveOnce(new InstantCommand(climber::ClimberRetract, climber));
    // .whenPressed(new RunCommand(() -> climber.ClimberArmToSetpoint(Constants.ArmOnBar), climber))
    // .whenReleased(new InstantCommand(climber::ClimberArmStop, climber));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
