package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase{
    //LazyTalonFX frontIntakeMotor;
    WPI_TalonFX frontTunnelMotor;
    LazyTalonFX rearTunnelMotor;
    WPI_TalonFX rearIntakeMotor;

    Solenoid intakeSolenoid;

    private boolean firstrun = false;
    private Timer m_ShootTimer = new Timer();

    public Intake(){
        //frontIntakeMotor = new LazyTalonFX(Constants.intakeFront);
        rearTunnelMotor = new LazyTalonFX(Constants.rearTunnel);
        rearIntakeMotor = new WPI_TalonFX(Constants.intakeRear, "can2");

        intakeSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, 0);

        //frontIntakeMotor.configFactoryDefault();
        rearTunnelMotor.configFactoryDefault();
        rearIntakeMotor.configFactoryDefault();

        rearIntakeMotor.setNeutralMode(NeutralMode.Brake);
        rearTunnelMotor.setNeutralMode(NeutralMode.Brake);

        rearTunnelMotor.setInverted(false);
        rearIntakeMotor.setInverted(true);
    }

    public void RearTunnelIn(){
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.70);
    }
    
    public void IntakeIn(){
        //frontIntakeMotor.set(ControlMode.PercentOutput, 0.70);
        rearIntakeMotor.set(ControlMode.PercentOutput, 0.90);
        intakeSolenoid.set(true);
    }

    public void IntakeOut(){
        //frontIntakeMotor.set(ControlMode.PercentOutput, -0.70);
        rearIntakeMotor.set(ControlMode.PercentOutput, -0.70);
        intakeSolenoid.set(true);
    }

    public void IntakeStop(){
        //frontIntakeMotor.set(ControlMode.PercentOutput, -0.0);
        rearIntakeMotor.set(ControlMode.PercentOutput, -0.0);
        intakeSolenoid.set(false);
    }

    public void IntakeTunnelIn(){
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.60);
    }

    public void IntakeTunnelOut(){
        rearTunnelMotor.set(ControlMode.PercentOutput, -0.70);
    }

    public void IntakeTunnelStop(){
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.0);
    }

    public void Shoot(boolean shoot){
        if(shoot){
            intakeSolenoid.set(true);
            rearTunnelMotor.set(ControlMode.PercentOutput, 0.70);
        }
    }

    public void ShootFinished(){
        rearTunnelMotor.set(ControlMode.PercentOutput, 0.0);
        intakeSolenoid.set(false);
        m_ShootTimer.stop();
        m_ShootTimer.reset();
        firstrun = false;
    }

    @Override
    public void periodic() {

    }
}
