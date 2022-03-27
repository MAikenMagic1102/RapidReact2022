package frc.robot.commands.Climber;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;

public class Stage1 extends CommandBase {

    private Timer timer = new Timer();
    private boolean finished = false;
    private Climber climber;

    public Stage1(Climber climb){

        climber = climb;

        timer.reset();
        addRequirements(climber);
    }

    @Override 
    public void execute(){
        // if(timer.get() == 0){
        //     timer.start();
        //     climber.ClimberArmToSetpoint(Constants.ArmStandby);
        // }

        // if(!timer.hasElapsed(0.5)){
        //     climber.ClimberPolesToSetpoint(Constants.ClimberRetractedFull);
        // }

        // if(timer.hasElapsed(3)){
        //     climber.ClimberArmToSetpoint(Constants.ArmOnBar);
        // }

        // if(timer.hasElapsed(7)){
        //     climber.ClimberPolesToSetpoint(Constants.ClimberExtendedShort);
        //     finished = true;
        //     timer.stop();
        // }

    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return finished;
  }
    
}
