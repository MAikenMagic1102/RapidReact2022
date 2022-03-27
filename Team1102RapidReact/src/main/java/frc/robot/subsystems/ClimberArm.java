package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

public class ClimberArm extends SubsystemBase {
    TalonFX climberArmMotor;

    double holdsetpointA = 0;

    CANCoder encoderArm;
    boolean ahold = false;

    boolean climbermode = false;

    public ClimberArm(){
        climberArmMotor = new TalonFX(Constants.climberArm, "can2");
        encoderArm = new CANCoder(Constants.canCoderArm, "can2");
        climberArmMotor.configFactoryDefault();
        encoderArm.configFactoryDefault();


        climberArmMotor.setInverted(true);
        climberArmMotor.setSelectedSensorPosition(0);
        climberArmMotor.config_kP(0, 0.04);
    }

    public void ClimberOpenLoop(double demandArm){
        if(Math.abs(demandArm) > 0.1){
            ahold = false;
            if(climbermode){
                climberArmMotor.set(ControlMode.PercentOutput, demandArm * 0.6);
            }
        }else{
            ClimberArmStop();
        }
    }

    public void ClimberArmStop(){
        if(!ahold){
            holdsetpointA = climberArmMotor.getSelectedSensorPosition();
            ahold = true;
        }
        if(ahold){
            climberArmMotor.set(ControlMode.Position, holdsetpointA);
        }
    }

    public void ClimberArmToSetpoint(double setpoint){
        holdsetpointA = setpoint;
    }

    public double ClimberArmError(){
        return Math.abs(climberArmMotor.getClosedLoopError());
    }

    public void ClimberArmEnable(){
        climbermode = true;
        holdsetpointA = Constants.ArmStandby;
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm Position", climberArmMotor.getSelectedSensorPosition());
        if(ahold){
            SmartDashboard.putNumber("Arm Setpoint Var", holdsetpointA);
            SmartDashboard.putNumber("Arm Setpoint", climberArmMotor.getClosedLoopTarget());
            SmartDashboard.putNumber("Arm Error", climberArmMotor.getClosedLoopError());
        }
        SmartDashboard.putNumber("Arm Demand", climberArmMotor.getMotorOutputPercent());
        SmartDashboard.putNumber("Arm CurrentDraw", climberArmMotor.getStatorCurrent());
        // SmartDashboard.putNumber("Arm FalconSetPt", climberArmMotor.getClosedLoopTarget());
        // SmartDashboard.putNumber("ArmSetpoint", holdsetpointA);
    }

}
