package frc.robot.commands;

import frc.robot.subsystems.Drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DefaultDrive extends CommandBase {
    private final Drivetrain m_Drivetrain;
    private final DoubleSupplier m_Magnitude;
    private final DoubleSupplier m_Rotation;
    private final BooleanSupplier m_MaxSpeed;
    private final BooleanSupplier m_ClimbMode;

    public DefaultDrive(Drivetrain drive, DoubleSupplier magnitude, DoubleSupplier rotation, BooleanSupplier maxSpeed, BooleanSupplier climbMode){
        m_Drivetrain = drive;
        m_Magnitude = magnitude;
        m_Rotation = rotation;
        m_MaxSpeed = maxSpeed;
        m_ClimbMode = climbMode;
        addRequirements(m_Drivetrain);
    }

    @Override 
    public void execute(){
        m_Drivetrain.ArcadeOpenLoop(m_Magnitude.getAsDouble(), m_Rotation.getAsDouble(), m_MaxSpeed.getAsBoolean(), m_ClimbMode.getAsBoolean());
    }

}