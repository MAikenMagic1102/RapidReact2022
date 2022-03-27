package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXFeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.SensorInitializationStrategy;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Util;

public class Climber extends SubsystemBase {
    WPI_TalonFX climberPoleLeft;
    WPI_TalonFX climberPoleRight;

    CANCoder encoderPoleL;
    CANCoder encoderPoleR;

    boolean climbermode = false;

    boolean hold = false;

    boolean leftPoleZeroed = false;
    boolean rightPoleZeroed = false;

    double holdsetpointL = 0;
    double holdsetpointR = 0;
    

    public Climber(){
        climberPoleLeft = new WPI_TalonFX(Constants.climberPoleL, "can2");
        climberPoleRight = new WPI_TalonFX(Constants.climberPoleR, "can2");

        encoderPoleL = new CANCoder(Constants.canCoderPoleL, "can2");
        encoderPoleR = new CANCoder(Constants.canCoderPoleR, "can2");
        
        climberPoleLeft.configFactoryDefault();
        climberPoleRight.configFactoryDefault();

        encoderPoleL.configFactoryDefault();
        encoderPoleR.configFactoryDefault();

        climberPoleLeft.setNeutralMode(NeutralMode.Brake);
        climberPoleRight.setNeutralMode(NeutralMode.Brake);
        
        climberPoleRight.setInverted(true);
        encoderPoleR.configSensorDirection(true);

        climberPoleLeft.config_kP(0, 0.02);
        climberPoleRight.config_kP(0, 0.02);
    }

    public void ClimberInit(){
        hold = false;
        if(!leftPoleZeroed && climberPoleLeft.getStatorCurrent() < 20){
            climberPoleLeft.set(ControlMode.PercentOutput, -0.2);
        }else{
            climberPoleLeft.setSelectedSensorPosition(0);
            leftPoleZeroed = true;
            climberPoleLeft.set(ControlMode.PercentOutput, 0);
        }

        if(!rightPoleZeroed && climberPoleRight.getStatorCurrent() < 20){
            climberPoleRight.set(ControlMode.PercentOutput, -0.2);
        }else{
            climberPoleRight.setSelectedSensorPosition(0);
            rightPoleZeroed = true;
            climberPoleRight.set(ControlMode.PercentOutput, 0);
        }
    }

    public void ClimberExtend(){
        holdsetpointL = 159400;
        holdsetpointR = 159400;
    }

    public void ClimberRetract(){
        holdsetpointL = 50;
        holdsetpointR = 50;
    }

    public void ClimberOpenLoop(double demandPoles){
        if(Math.abs(demandPoles) > 0.1){
            hold = false;
            if(climbermode){
                climberPoleLeft.set(ControlMode.PercentOutput, demandPoles * 0.7);
                climberPoleRight.set(ControlMode.PercentOutput, demandPoles * 0.7);
            }
        }else{
            ClimberPoleStop();
        }
    }


    public void ClimberPoleStop(){
        if(!leftPoleZeroed && !rightPoleZeroed){
            ClimberInit();
        }else{
            if(!hold){
                holdsetpointL = climberPoleLeft.getSelectedSensorPosition();
                holdsetpointR = climberPoleRight.getSelectedSensorPosition();
                hold = true;
            }
            if(hold){
                climberPoleLeft.set(ControlMode.Position, holdsetpointL);
                climberPoleRight.set(ControlMode.Position, holdsetpointR);
            }
        }
    }

    public void ClimberPolesToSetpoint(double setpoint){
        holdsetpointL = setpoint;
        holdsetpointR = setpoint;
    }

    public double ClimberPoleLeftError(){
        return Math.abs(climberPoleLeft.getClosedLoopError());
    }

    public double ClimberPoleRightError(){
        return Math.abs(climberPoleRight.getClosedLoopError());
    }

    public void ClimberEnable(){
        climbermode = true;
        holdsetpointL = Constants.ClimberExtendedFull;
        holdsetpointR = Constants.ClimberExtendedFull;
    }

    public boolean ClimberEnabled(){
        return climbermode;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Climber Mode", ClimberEnabled());
        SmartDashboard.putNumber("Left Pole Position", climberPoleLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Pole Position", climberPoleRight.getSelectedSensorPosition());
        SmartDashboard.putNumber("ClimberPoleLeft Error", climberPoleLeft.getClosedLoopError());
        SmartDashboard.putNumber("ClimberPoleRight Error", climberPoleRight.getClosedLoopError());
        if(hold){
            SmartDashboard.putNumber("RightPole Setpoint", climberPoleRight.getClosedLoopTarget());
            SmartDashboard.putNumber("LeftPole Setpoint", climberPoleLeft.getClosedLoopTarget());
            SmartDashboard.putNumber("ClimberPoleLeft Error", climberPoleLeft.getClosedLoopError());
            SmartDashboard.putNumber("ClimberPoleRight Error", climberPoleRight.getClosedLoopError());
        }
    }
}
