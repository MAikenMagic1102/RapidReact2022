package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class ExtendPolesShortS1 extends CommandBase{
        
    private final Climber m_climb;

    public ExtendPolesShortS1(Climber climb){
        m_climb = climb;
        addRequirements(climb);
    }

    boolean isFinished = false;

    @Override 
    public void execute(){
        m_climb.ClimberPolesToSetpoint(Constants.ClimberExtendedShortS1);
        isFinished = true;
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isFinished;
    }
    
    
}
