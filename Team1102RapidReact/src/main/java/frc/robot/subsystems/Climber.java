package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Util;

public class Climber extends SubsystemBase {
    WPI_TalonFX climberArmMotor;
    WPI_TalonFX climberPoleLeft;
    WPI_TalonFX climberPoleRight;

    CANCoder encoderPoleL;
    CANCoder encoderPoleR;
    CANCoder encoderArm;

    boolean climbermode = false;

    boolean hold = false;
    boolean ahold = false;

    boolean leftPoleZeroed = false;
    boolean rightPoleZeroed = false;

    double holdsetpointL = 0;
    double holdsetpointR = 0;
    double holdsetpointA = 0;

    

    public Climber(){
        climberArmMotor = new WPI_TalonFX(Constants.climberArm, "can2");
        climberPoleLeft = new WPI_TalonFX(Constants.climberPoleL, "can2");
        climberPoleRight = new WPI_TalonFX(Constants.climberPoleR, "can2");

        encoderPoleL = new CANCoder(Constants.canCoderPoleL, "can2");
        encoderPoleR = new CANCoder(Constants.canCoderPoleR, "can2");
        encoderArm = new CANCoder(Constants.canCoderArm, "can2");

        climberArmMotor.configRemoteFeedbackFilter(encoderArm, 0, Constants.CTRE_TimeoutMS);

        climberArmMotor.configFactoryDefault();
        climberPoleLeft.configFactoryDefault();
        climberPoleRight.configFactoryDefault();

        encoderPoleL.configFactoryDefault();
        encoderPoleR.configFactoryDefault();
        encoderArm.configFactoryDefault();

        climberPoleLeft.setNeutralMode(NeutralMode.Brake);
        climberPoleRight.setNeutralMode(NeutralMode.Brake);
        
        climberPoleRight.setInverted(true);
        encoderPoleR.configSensorDirection(true);

        climberPoleLeft.config_kP(0, 0.01);
        climberPoleRight.config_kP(0, 0.01);
        climberArmMotor.config_kP(0, 1.5);

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
        holdsetpointL = 159600;
        holdsetpointR = 159600;
    }

    public void ClimberRetract(){
        holdsetpointL = 50;
        holdsetpointR = 50;
    }

    public void ClimberOpenLoop(double demandArm, double demandPoles){
        if(Math.abs(demandPoles) > 0.1){
            hold = false;
            if(climbermode){
                climberArmMotor.set(ControlMode.PercentOutput, demandArm * 0.6);
                climberPoleLeft.set(ControlMode.PercentOutput, demandPoles * 0.7);
                climberPoleRight.set(ControlMode.PercentOutput, demandPoles * 0.7);
            }
        }else{
            ClimberPoleStop();
        }
    }

    public void ClimberArmStop(){
        if(!ahold){
            holdsetpointA = encoderArm.getAbsolutePosition();
            ahold = true;
        }
        if(ahold){
            climberArmMotor.set(ControlMode.Position, holdsetpointA);
        }
    }

    public void ClimberPoleStop(){
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

    public void ClimberPolesToSetpoint(double setpoint){
        hold = false;
        climberPoleLeft.set(ControlMode.Position, setpoint);
        climberPoleRight.set(ControlMode.Position, setpoint);
    }

    public void ClimberArmToSetpoint(double setpoint){
        holdsetpointA = setpoint;
        ahold = false;
        climberArmMotor.set(ControlMode.Position, setpoint);
    }

    public double ClimberPoleLeftError(){
        return climberPoleLeft.getClosedLoopError();
    }

    public double ClimberPoleRightError(){
        return climberPoleRight.getClosedLoopError();
    }

    public double ClimberArmError(){
        return climberArmMotor.getClosedLoopError();
    }

    // public void ClimbonBar1(){
    //     hold = false;
    //     if(!climbStage1){
    //         ClimberPolesFullRetract();
    //         //Arm to standby position

    //         if(climberPoleLeft.getClosedLoopError() < 100 && climberPoleRight.getClosedLoopError() < 100)
    //             climbStage1 = true;
    //     }

    //     if(climbStage1 && !climbStage2){
    //         //Arm to pole latch position
    //         if(climberArmMotor.getClosedLoopError() < 3)
    //             climbStage2 = true;
    //     }

    //     if(climbStage1 && climbStage2 && !climbStage3){
    //         //ClimberPoles slight extend ~6 inches
    //     }

    //     if(climbStage1 && climbStage2 && climbStage3 && !climbStage4){
    //         //Tilt Arm to known good position for poles
    //     }
    // }

    public void ClimberEnable(){
        climbermode = true;
    }

    public boolean ClimberEnabled(){
        return climbermode;
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Climber Mode", ClimberEnabled());
        SmartDashboard.putNumber("Arm Position", encoderArm.getAbsolutePosition());
        SmartDashboard.putNumber("Left Pole Position", climberPoleLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("Right Pole Position", climberPoleRight.getSelectedSensorPosition());
        SmartDashboard.putNumber("ClimberPoleLeft Current", climberPoleLeft.getStatorCurrent());
        SmartDashboard.putNumber("ClimberPoleRight Current", climberPoleRight.getStatorCurrent());
        if(hold){
            SmartDashboard.putNumber("RightPole Setpoint", climberPoleRight.getClosedLoopTarget());
            SmartDashboard.putNumber("LeftPole Setpoint", climberPoleLeft.getClosedLoopTarget());
        }
        // SmartDashboard.putNumber("Arm FalconSetPt", climberArmMotor.getClosedLoopTarget());
        // SmartDashboard.putNumber("ArmSetpoint", holdsetpointA);
    }
}
