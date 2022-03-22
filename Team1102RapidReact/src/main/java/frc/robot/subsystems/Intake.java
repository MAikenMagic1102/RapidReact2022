package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    //LazyTalonFX frontIntakeMotor;
    WPI_TalonFX frontTunnelMotor;
    LazyTalonFX rearTunnelMotor;
    WPI_TalonFX rearIntakeMotor;

    private boolean firstrun = false;
    private Timer m_ShootTimer = new Timer();

    public Intake(){
        //frontIntakeMotor = new LazyTalonFX(Constants.intakeFront);
        frontTunnelMotor = new WPI_TalonFX(Constants.frontTunnel, "can2");
        rearTunnelMotor = new LazyTalonFX(Constants.rearTunnel);
        rearIntakeMotor = new WPI_TalonFX(Constants.intakeRear, "can2");

        //frontIntakeMotor.configFactoryDefault();
        frontTunnelMotor.configFactoryDefault();
        rearTunnelMotor.configFactoryDefault();
        rearIntakeMotor.configFactoryDefault();

        //frontIntakeMotor.setInverted(true);
        frontTunnelMotor.setInverted(true);
        rearTunnelMotor.setInverted(false);
        rearIntakeMotor.setInverted(true);
    }

    public void FrontTunnelIn(){
        frontTunnelMotor.set(ControlMode.PercentOutput, 0.70);    
    }

    public void RearTunnelIn(){
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.70);
    }
    
    public void IntakeIn(){
        //frontIntakeMotor.set(ControlMode.PercentOutput, 0.70);
        rearIntakeMotor.set(ControlMode.PercentOutput, 0.90);
    }

    public void IntakeOut(){
        //frontIntakeMotor.set(ControlMode.PercentOutput, -0.70);
        rearIntakeMotor.set(ControlMode.PercentOutput, -0.70);
    }

    public void IntakeStop(){
        //frontIntakeMotor.set(ControlMode.PercentOutput, -0.0);
        rearIntakeMotor.set(ControlMode.PercentOutput, -0.0);
    }

    public void IntakeTunnelIn(){
        frontTunnelMotor.set(ControlMode.PercentOutput, 0.60);
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.60);
    }

    public void IntakeTunnelOut(){
        frontTunnelMotor.set(ControlMode.PercentOutput, -0.70);
        rearTunnelMotor.set(ControlMode.PercentOutput, -0.70);
    }

    public void IntakeTunnelStop(){
        frontTunnelMotor.set(ControlMode.PercentOutput, 0.0);
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void Shoot(boolean shoot){
        if(shoot){
            if(!firstrun){
                m_ShootTimer.start();
                firstrun = true;
            }
            rearTunnelMotor.set(ControlMode.PercentOutput, 0.70);
            if(m_ShootTimer.hasElapsed(0.3)){
                frontTunnelMotor.set(ControlMode.PercentOutput, 0.70);
            }
        }
    }

    public void ShootFinished(){
        frontTunnelMotor.set(ControlMode.PercentOutput, 0.0);
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.0);
        m_ShootTimer.stop();
        m_ShootTimer.reset();
        firstrun = false;
    }

    @Override
    public void periodic() {

    }
}
