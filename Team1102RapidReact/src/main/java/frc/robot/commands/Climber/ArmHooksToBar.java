package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;

public class ArmHooksToBar extends CommandBase {
    private final ClimberArm arm;

    public ArmHooksToBar(ClimberArm m_arm){
        arm = m_arm;
        addRequirements(arm);
    }

    boolean isFinished = false;

    @Override 
    public void execute(){
        arm.ClimberArmToSetpoint(Constants.ArmOnBar);
        isFinished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isFinished;
    }
    
}
