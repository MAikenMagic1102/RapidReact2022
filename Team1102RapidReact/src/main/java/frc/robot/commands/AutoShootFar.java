package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Shooter;

public class AutoShootFar extends CommandBase {

    private final Shooter m_shooter;
    private final Feeder m_Feeder;
    private final Turret m_Turret;

    
    private Timer timer = new Timer();
    private boolean finished = false;

    public AutoShootFar(Shooter shoot, Feeder feed, Turret turret){
        m_shooter = shoot;
        m_Feeder = feed;
        m_Turret = turret;

        timer.reset();
        addRequirements(m_shooter, m_Feeder, m_Turret);
    }

    
    @Override 
    public void execute(){
        if(timer.get() == 0){
            m_Turret.NearLimelightControl(0);
            m_shooter.ShooterAutoRange(m_Turret.limelight_range());
        }

        if(!timer.hasElapsed(1)){
            m_Feeder.Feed(m_shooter.ShooterReady());
        }

        if(timer.hasElapsed(3)){
            timer.stop();
            finished = true;
            m_Feeder.StopFeed();
            m_shooter.ShooterTarget(0);
            m_Turret.turret_Stop();
        }

    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return finished;
  }

}