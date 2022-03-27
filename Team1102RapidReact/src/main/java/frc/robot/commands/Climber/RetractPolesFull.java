package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class RetractPolesFull extends CommandBase {

    private final Climber m_climb;

    public RetractPolesFull(Climber climb){
        m_climb = climb;
        addRequirements(climb);
    }

    boolean isFinished = false;
    Timer time = new Timer();

    @Override 
    public void execute(){
        if(time.get() == 0){
            time.start();
        }

        m_climb.ClimberPolesToSetpoint(Constants.ClimberRetractedFull);

        if(m_climb.ClimberPoleLeftError() < 3000 && m_climb.ClimberPoleRightError() < 3000)
            isFinished = true;

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isFinished;
    }
    
}
