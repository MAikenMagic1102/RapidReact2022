package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.filter.SlewRateLimiter;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.lib.geometry.Twist2d;
import frc.robot.lib.util.DriveSignal;
import frc.robot.lib.util.Kinematics;
import frc.robot.lib.LazyTalonFX;
import frc.robot.lib.Util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class Drivetrain extends SubsystemBase{

    WPI_TalonFX leftMotor1;
    WPI_TalonFX leftMotor2;
    //WPI_TalonFX leftMotor3;

    WPI_TalonFX rightMotor1;
    WPI_TalonFX rightMotor2;
    //LazyTalonFX rightMotor3;

    DifferentialDrive drive;

    private SlewRateLimiter speedLimiter = new SlewRateLimiter(2.7);
    private SlewRateLimiter rotLimiter = new SlewRateLimiter(2.7);

    SupplyCurrentLimitConfiguration currentLmt = new SupplyCurrentLimitConfiguration(true, 30, 60, .2);

    public Drivetrain(){
        
        leftMotor1 = new WPI_TalonFX(Constants.leftDrive1, "can2");
        leftMotor2 = new WPI_TalonFX(Constants.leftDrive2, "can2");
        //leftMotor3 = new WPI_TalonFX(Constants.leftDrive3, "can2");

        rightMotor1 = new WPI_TalonFX(Constants.rightDrive1);
        rightMotor2 = new WPI_TalonFX(Constants.rightDrive2);
        //rightMotor3 = new LazyTalonFX(Constants.rightDrive3);

        leftMotor1.configFactoryDefault();
        leftMotor2.configFactoryDefault();
        //leftMotor3.configFactoryDefault();
        rightMotor1.configFactoryDefault();
        rightMotor2.configFactoryDefault();
        //rightMotor3.configFactoryDefault();

        leftMotor1.configSupplyCurrentLimit(currentLmt);
        leftMotor2.configSupplyCurrentLimit(currentLmt);
        rightMotor1.configSupplyCurrentLimit(currentLmt);
        rightMotor2.configSupplyCurrentLimit(currentLmt);


        leftMotor2.follow(leftMotor1);
        //leftMotor3.follow(leftMotor1);

        rightMotor2.follow(rightMotor1);
        //rightMotor3.follow(rightMotor1);

        Util.SetCANStatusFrames(leftMotor2);
        //Util.SetCANStatusFrames(leftMotor3);
        Util.SetCANStatusFrames(rightMotor2);
        //Util.SetCANStatusFrames(rightMotor3);

        leftMotor1.setInverted(false);
        rightMotor1.setInverted(true);

        leftMotor2.setInverted(InvertType.FollowMaster);
        rightMotor2.setInverted(InvertType.FollowMaster);

        //leftMotor3.setInverted(InvertType.FollowMaster);
        //rightMotor3.setInverted(InvertType.FollowMaster);

        drive = new DifferentialDrive(leftMotor1, rightMotor1);
    }


    public void ArcadeOpenLoop(double magnitude, double rotation, boolean maxSpeed, boolean climbMode){
        magnitude = magnitude * -1;
        if(!climbMode){
            drive.arcadeDrive(speedLimiter.calculate(magnitude), rotLimiter.calculate(rotation));
        }else{
            drive.arcadeDrive(magnitude * 0.4, rotation * 0.4);
        }
        
    }

    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    //   SmartDashboard.putNumber("LeftDrive", leftMotor1.getSelectedSensorVelocity());
    //   SmartDashboard.putNumber("RightDrive", rightMotor1.getSelectedSensorVelocity());
    }
    
}
