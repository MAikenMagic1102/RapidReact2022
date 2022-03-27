package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;
import frc.robot.Constants;

public class ClimberCommands {

    private Climber m_climb;
    private ClimberArm m_arm;

    public ClimberCommands(Climber climber, ClimberArm arm){
        m_climb = climber;
        m_arm = arm;
    }

    public Command pullUpFromGround(){
        return retractPoles().alongWith(new WaitCommand(5).andThen(armOnBar()));
    }

    public Command releasePolesfromBar(){
        return extendPolesPartial();
    }

    public Command nextBar(){
        return extendPolesPartial().alongWith(new WaitCommand(0.5).andThen(armToStandby()));
    }
    
    public Command extendPoles(){
        return new InstantCommand(() -> m_climb.ClimberPolesToSetpoint(Constants.ClimberExtendedFull), m_climb);
    }

    public Command extendPolesPartial(){
        return new InstantCommand(() -> m_climb.ClimberPolesToSetpoint(Constants.ClimberExtendedShort), m_climb);
    }

    public Command retractPoles(){
        return new InstantCommand(() -> m_climb.ClimberPolesToSetpoint(Constants.ClimberRetractedFull), m_climb);
    }

    public Command armToStandby(){
        return new InstantCommand(() -> m_arm.ClimberArmToSetpoint(Constants.ArmStandby), m_arm);
    }

    public Command armOnBar(){
        return new InstantCommand(() -> m_arm.ClimberArmToSetpoint(Constants.ArmOnBar), m_arm);
    }



}
