package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;

public class DriveForward extends CommandBase {
    private final Drivetrain m_Drivetrain;

    private Timer timer = new Timer();
    private boolean finished = false;

    public DriveForward(Drivetrain drive){

        m_Drivetrain = drive;

        timer.reset();
        addRequirements(m_Drivetrain);
    }

    @Override 
    public void execute(){
        if(timer.get() == 0){
            timer.start();
        }

        if(!timer.hasElapsed(3.5)){
            m_Drivetrain.ArcadeOpenLoop(0.6, 0.0, true, false);
        }

        if(timer.hasElapsed(1.5)){
            m_Drivetrain.ArcadeOpenLoop(0.0, 0.0, true, false);
            finished = true;
        }

    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return finished;
  }

}