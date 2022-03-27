// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.lib.InterpolatingDouble;
import frc.robot.lib.InterpolatingTreeMap;
import frc.robot.lib.LinearInterpolator;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int REV_PDH = 1;

    public static final int leftDrive1 = 2;

    public static final int leftDrive2 = 3;

    public static final int leftDrive3 = 4;
    
    public static final int rightDrive1 = 5;

    public static final int rightDrive2 = 6;

    public static final int rightDrive3 = 7;

    public static final int infeedOmniL = 8;

    public static final int infeedOmniR = 9;

    public static final int climberPoleL = 10;

    public static final int climberPoleR = 11;

    public static final int climberArm = 12;

    public static final int canCoderPoleL = 13;
    
    public static final int canCoderPoleR = 14;

    public static final int canCoderArm = 15;

    public static final int intakeFront = 16;

    public static final int intakeRear = 17;

    public static final int frontTunnel = 18;

    public static final int shooterCenterFeed = 19;

    public static final int rearTunnel = 20;

    public static final int shooterL = 21;

    public static final int shooterR = 22;

    public static final int hood = 23; //Robin Hood

    public static final int turret = 24;

    public static final double kDriveWheelTrackWidthInches = 25.5;
    public static final double kDriveWheelDiameterInches = 4.0;
    public static final double kDriveWheelRadiusInches = kDriveWheelDiameterInches / 2.0;
    public static final double kDriveWheelTrackRadiusWidthMeters = kDriveWheelTrackWidthInches / 2.0 * 0.02712;
    public static final double kTrackScrubFactor = 1.0469745223;

    public static final int CTRE_TimeoutMS = 10;

    public static final double Shooter_kP = 0.0685;
    public static final double Shooter_kF = 0.1;

    public static final double Hood_kP = 3.2;

    public static final double Turret_kP = 0.075;
    public static final double Turret_SRXkP = 1.6;

    public static final double Climber_kP = 0.1;

    public static final int TurretLeftLimit = 3300;
    public static final int TurretRightLimit  = 5260;

    //Low Goal Fender Shot Constants
    public static final double ShooterHoodClose = 32;
    public static final double ShooterSpeedClose = 1800;

    //this is the 254 method for interpolated maps
    //Left term is distance? and Right term is Hood angle or Shooter Speed this method does not use a loop

    public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kHoodMap = new InterpolatingTreeMap<>();
    static {
        kHoodMap.put(new InterpolatingDouble(61.00), new InterpolatingDouble(31.00));
        kHoodMap.put(new InterpolatingDouble(71.00), new InterpolatingDouble(33.00));
        kHoodMap.put(new InterpolatingDouble(81.00), new InterpolatingDouble(35.00));
        kHoodMap.put(new InterpolatingDouble(92.0), new InterpolatingDouble(36.00));
        kHoodMap.put(new InterpolatingDouble(104.0), new InterpolatingDouble(37.00));
        kHoodMap.put(new InterpolatingDouble(110.0), new InterpolatingDouble(37.00));
        kHoodMap.put(new InterpolatingDouble(120.0), new InterpolatingDouble(37.50));
    }

    public static InterpolatingTreeMap<InterpolatingDouble, InterpolatingDouble> kRPMMap = new InterpolatingTreeMap<>();
    static {
        kRPMMap.put(new InterpolatingDouble(61.00), new InterpolatingDouble(3250.00));
        kRPMMap.put(new InterpolatingDouble(71.00), new InterpolatingDouble(3400.00));
        kRPMMap.put(new InterpolatingDouble(81.0), new InterpolatingDouble(3470.00));
        kRPMMap.put(new InterpolatingDouble(92.0), new InterpolatingDouble(3500.00));
        kRPMMap.put(new InterpolatingDouble(104.0), new InterpolatingDouble(3800.00));
        kRPMMap.put(new InterpolatingDouble(110.0), new InterpolatingDouble(3800.00));
        kRPMMap.put(new InterpolatingDouble(120.0), new InterpolatingDouble(3870.00));
    }

    //To get value do the following
    //kRPMMap.getInterpolated(new InterpolatingDouble(distance).value);

    public static final double ClimberExtendedFull = 159400.00;
    public static final double ClimberExtendedShort = 80000.0;
    public static final double ClimberRetractedFull = 50.0;

    public static final double ArmStandby = 26000.0;
    public static final double ArmOnBar = 61000.0;
    public static final double ArmRobotTiltedDown = 173.0;
    public static final double ArmPolesTiltedtoBar = 0.0;
}
