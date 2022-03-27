package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;

public class ArmHooksToStandBy extends CommandBase{

    private final ClimberArm arm;

    public ArmHooksToStandBy(ClimberArm m_arm){
        arm = m_arm;
        addRequirements(arm);
    }

    boolean isFinished = false;

    @Override 
    public void execute(){
        arm.ClimberArmToSetpoint(Constants.ArmStandby);
        isFinished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
