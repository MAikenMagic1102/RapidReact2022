package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Shooter;

public class AutoShoot extends CommandBase {

    private final Shooter m_shooter;
    private final Feeder m_Feeder;
    private final Turret m_Turret;
    private final Intake m_intake;

    
    private Timer timer = new Timer();
    private boolean finished = false;

    public AutoShoot(Shooter shoot, Feeder feed, Turret turret, Intake intake){
        m_shooter = shoot;
        m_Feeder = feed;
        m_Turret = turret;
        m_intake = intake;

        timer.reset();
        addRequirements(m_shooter, m_Feeder, m_Turret, m_intake);
    }

    
    @Override 
    public void execute(){
        if(timer.get() == 0){
            timer.start();
            m_shooter.ShooterHood_toPosition(35);
            m_shooter.ShooterTarget(3750);
        }

        if(!timer.hasElapsed(1)){
            m_Turret.NearLimelightControl(0);
            m_Feeder.Feed(m_shooter.ShooterReady());
            m_intake.RearTunnelIn();
        }

        if(timer.hasElapsed(6)){
            timer.stop();
            finished = true;
            m_Feeder.StopFeed();
            m_shooter.ShooterTarget(0);
            m_Turret.turret_Stop();
            m_intake.IntakeTunnelStop();
        }

    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return finished;
  }

}