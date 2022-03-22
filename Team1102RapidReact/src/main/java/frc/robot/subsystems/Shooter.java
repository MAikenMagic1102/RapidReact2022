package frc.robot.subsystems;

import java.util.ResourceBundle.Control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.InterpolatingDouble;
import frc.robot.lib.Util;

public class Shooter extends SubsystemBase {

    WPI_TalonFX shooterMotorL;
    WPI_TalonFX shooterMotorR;

    TalonSRX hoodMotor;
    
    double targetRPM, targetS;
    double shooterDistance = 0.0;

    public Shooter(){

        shooterMotorL = new WPI_TalonFX(Constants.shooterL);
        shooterMotorR = new WPI_TalonFX(Constants.shooterR);

        hoodMotor = new WPI_TalonSRX(Constants.hood);
        hoodMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, Constants.CTRE_TimeoutMS);
        
        hoodMotor.setSensorPhase(true);

        shooterMotorL.configFactoryDefault();
        shooterMotorR.configFactoryDefault();
        hoodMotor.configFactoryDefault();

        shooterMotorR.follow(shooterMotorL);

        Util.SetCANStatusFrames(shooterMotorR);

        shooterMotorL.configNominalOutputForward(0, Constants.CTRE_TimeoutMS);
        shooterMotorL.configPeakOutputForward(1, Constants.CTRE_TimeoutMS);
        shooterMotorL.configPeakOutputReverse(0, Constants.CTRE_TimeoutMS);
        shooterMotorL.configVoltageCompSaturation(11);

        shooterMotorR.configNominalOutputForward(0, Constants.CTRE_TimeoutMS);
        shooterMotorR.configPeakOutputForward(1, Constants.CTRE_TimeoutMS);
        shooterMotorR.configPeakOutputReverse(0, Constants.CTRE_TimeoutMS);
        shooterMotorR.setInverted(true);

        shooterMotorL.configClosedloopRamp(0.7);

        shooterMotorL.config_kP(0, Constants.Shooter_kF);
        shooterMotorL.config_kF(0, Constants.Shooter_kP);

        hoodMotor.config_kP(0, Constants.Hood_kP, Constants.CTRE_TimeoutMS);

        SmartDashboard.putNumber("ShooterTunableSpeed", 3000);
        SmartDashboard.putNumber("HoodTuneable", 20);
    }

    public double ShooterHood_GetPositionDegrees(){
        return (hoodMotor.getSelectedSensorPosition() / (4096 / 360)) / 5.25;
    }

    public double ShooterHood_DegreestoSensorUnits(double target){
        return (((target) * 4096) / 360) * 5.25;
    }

    public void ShooterClose(){

    }

    public void ShooterOpenLoop(double duty){
        shooterMotorR.set(ControlMode.PercentOutput, duty);
    }

    public boolean ShooterReady(){
        if(targetRPM != 0){
            return Math.abs(targetRPM - Shooter_GetRPM()) < 50;
        }else{
            return false;
        }
    }

    public double Shooter_GetRPM(){
        //600 = 100ms in on 1minute
        //2048 = pulses of encoder in 1 revolution of motor shaft        
        return shooterMotorL.getSelectedSensorVelocity() * 600 / 2048;
    }

    public double speedConvert(double speed){
        targetRPM = speed;
        targetS = (speed * (2048 / 600)); 
        return targetS;
    }

    public void ShooterTarget(double targetRPM){
        shooterMotorL.set(ControlMode.Velocity, speedConvert(targetRPM));
    }

    public void ShooterTunable(){
        double shooterRPM = SmartDashboard.getNumber("ShooterTunableSpeed", 3000);
        double hoodAngle = SmartDashboard.getNumber("HoodTuneable", 20);
        ShooterTarget(shooterRPM);
        ShooterHood_toPosition(hoodAngle);
    }

    public void ShooterAutoRange(double distance){
        shooterDistance = distance;
        ShooterTarget(Constants.kRPMMap.getInterpolated(new InterpolatingDouble(distance)).value);
        ShooterHood_toPosition(Constants.kHoodMap.getInterpolated(new InterpolatingDouble(distance)).value);
    }

    public void ShooterHood_toPosition(double targetdeg){
        hoodMotor.set(ControlMode.Position, ShooterHood_DegreestoSensorUnits(targetdeg));
    }

        
    @Override
    public void periodic() {
        SmartDashboard.putNumber("Hood Angle", ShooterHood_GetPositionDegrees());
        SmartDashboard.putNumber("Shooter RPM", Shooter_GetRPM());
        SmartDashboard.putNumber("TargetRPM", targetRPM);
        SmartDashboard.putNumber("Error Flywheel", Math.abs(shooterMotorL.getClosedLoopError() * 60 / 2048));
        SmartDashboard.putBoolean("Shooter Ready", ShooterReady());
        SmartDashboard.putNumber("Shooter Dist", shooterDistance);
    }
    
}
