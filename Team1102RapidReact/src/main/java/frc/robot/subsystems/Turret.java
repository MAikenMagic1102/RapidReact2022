package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;


import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.AS5600Encoder;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Limelight;
import frc.robot.lib.Util;
import frc.robot.lib.Limelight.LightMode;

public class Turret extends SubsystemBase {
    
    WPI_TalonSRX turretMotor;
    Limelight limelight;

    double distance = 0;

    public Turret(){
        turretMotor = new WPI_TalonSRX(Constants.turret);
        turretMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Constants.CTRE_TimeoutMS);
        
        turretMotor.setInverted(true);
        turretMotor.setSensorPhase(true);
        turretMotor.config_kP(0, Constants.Turret_SRXkP);

        limelight = new Limelight();
        limelight.setLedMode(LightMode.eOff);

    }

    public void NearLimelightControl(double input){
        limelight.setPipeline(0);
        limelight.setLedMode(LightMode.eOn);
        if(limelight.isTarget()){
            double turretOutput = limelight.getTx() * Constants.Turret_kP;
            turretMotor.set(ControlMode.PercentOutput, turretOutput);
        }else{
            turretMotor.set(ControlMode.PercentOutput, input);
        }
    }

    public void MidLimelightControl(double input){
        limelight.setPipeline(1);
        limelight.setLedMode(LightMode.eOn);
        if(limelight.isTarget()){
            double turretOutput = limelight.getTx() * Constants.Turret_kP;
            turretMotor.set(ControlMode.PercentOutput, turretOutput);
        }else{
            turretMotor.set(ControlMode.PercentOutput, input);
        }
    }

    public void FarLimelightControl(double input){
        limelight.setPipeline(2);
        limelight.setLedMode(LightMode.eOn);
        if(limelight.isTarget()){
            double turretOutput = limelight.getTx() * Constants.Turret_kP;
            turretMotor.set(ControlMode.PercentOutput, turretOutput);
        }else{
            turretMotor.set(ControlMode.PercentOutput, input);
        }
    }
    

    public void turret_Stop(){
        turretMotor.set(ControlMode.PercentOutput, 0.0);
        limelight.setLedMode(LightMode.eOff);
    }

    public void ClimbPosition(){
        turretMotor.set(ControlMode.Position, 2953);
    }

    public void Rotate_OpenLoop(double rotation, boolean ClimberMode){
        if(ClimberMode){
            ClimbPosition();
        }else{
        
        if((rotation > 0 && (turretMotor.getSelectedSensorPosition() > Constants.TurretRightLimit))){
            turretMotor.set(ControlMode.PercentOutput, 0.0);
        } else{
            if((rotation < 0 && (turretMotor.getSelectedSensorPosition() < Constants.TurretLeftLimit))){
                turretMotor.set(ControlMode.PercentOutput, 0.0);
            }else{
                turretMotor.set(ControlMode.PercentOutput, Math.abs(rotation) > 0.1 ? rotation * 0.5 : 0 );
            }
        }
    }
    }

    public boolean robot_InRange(){
        double distance = limelight.getDistance();
        return distance > 80 && distance < 120;
    }

    public double limelight_range(){
        return limelight.getDistance();
    }
    
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Turret Position", turretMotor.getSelectedSensorPosition());
        SmartDashboard.putNumber("Distance", limelight.getDistance());
        SmartDashboard.putBoolean("Target", limelight.isTarget());
        SmartDashboard.putBoolean("Robot In Range?", robot_InRange());
    }
}
