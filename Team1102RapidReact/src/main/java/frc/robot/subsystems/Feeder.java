package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {
    LazyTalonFX centerFeederMotor;
    WPI_TalonFX omniFeederLMotor;
    //LazyTalonFX omniFeederRMotor;

    public Feeder(){
        centerFeederMotor = new LazyTalonFX(Constants.shooterCenterFeed);
        omniFeederLMotor = new WPI_TalonFX(Constants.infeedOmniL, "can2");
        //omniFeederRMotor = new LazyTalonFX(Constants.infeedOmniR);

        centerFeederMotor.configFactoryDefault();
        omniFeederLMotor.configFactoryDefault();
        //omniFeederRMotor.configFactoryDefault();

        omniFeederLMotor.setInverted(true);
    }

    public void Feed(boolean shooterReady){
        if(shooterReady){
            centerFeederMotor.set(ControlMode.PercentOutput, 0.7);
            //omniFeederLMotor.set(ControlMode.PercentOutput, 0.4);
            //omniFeederRMotor.set(ControlMode.PercentOutput, 0.4);
        }else{
            centerFeederMotor.set(ControlMode.PercentOutput, 0);
            //omniFeederLMotor.set(ControlMode.PercentOutput, 0);
            //omniFeederRMotor.set(ControlMode.PercentOutput, 0);
        }
    }

    public void StopFeed(){
        centerFeederMotor.set(ControlMode.PercentOutput, 0);
        //omniFeederLMotor.set(ControlMode.PercentOutput, 0);
        //omniFeederRMotor.set(ControlMode.PercentOutput, 0);
    }

    public void CenterFeederReverse(){
        centerFeederMotor.set(ControlMode.PercentOutput, -0.70);
    }
    public void CenterFeederStop(){
        centerFeederMotor.set(ControlMode.PercentOutput, 0.0);
    }
}
