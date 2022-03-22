package frc.robot.lib;

import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Util {

    public static void SetCANStatusFrames(WPI_TalonFX motor){
        motor.setStatusFramePeriod(1, 1000);
        motor.setStatusFramePeriod(2, 1000);
        motor.setStatusFramePeriod(3, 1000);
        motor.setStatusFramePeriod(4, 1000);
        motor.setStatusFramePeriod(8, 1000);
        motor.setStatusFramePeriod(10, 1000);
        motor.setStatusFramePeriod(12, 1000);
        motor.setStatusFramePeriod(13, 1000);
        motor.setStatusFramePeriod(14, 1000);
        motor.setStatusFramePeriod(21, 1000);
    }

    public static void SetCANStatusFramesExcept1(WPI_TalonFX motor){
        motor.setStatusFramePeriod(2, 1000);
        motor.setStatusFramePeriod(3, 1000);
        motor.setStatusFramePeriod(4, 1000);
        motor.setStatusFramePeriod(8, 1000);
        motor.setStatusFramePeriod(10, 1000);
        motor.setStatusFramePeriod(12, 1000);
        motor.setStatusFramePeriod(13, 1000);
        motor.setStatusFramePeriod(14, 1000);
        motor.setStatusFramePeriod(21, 1000);
    }

    public static void SetUnusedCANStatusFramestoMAX(WPI_TalonFX motor){
        motor.setStatusFramePeriod(2, 1000);
        motor.setStatusFramePeriod(3, 1000);
        motor.setStatusFramePeriod(4, 1000);
        motor.setStatusFramePeriod(8, 1000);
        motor.setStatusFramePeriod(10, 1000);
        motor.setStatusFramePeriod(12, 1000);
        motor.setStatusFramePeriod(13, 1000);
        motor.setStatusFramePeriod(14, 1000);
    }
    
    public static void SetUnusedCANStatusFramestoMAX(WPI_TalonSRX motor){
        motor.setStatusFramePeriod(3, 1000);
        motor.setStatusFramePeriod(4, 1000);
        motor.setStatusFramePeriod(8, 1000);
        motor.setStatusFramePeriod(10, 1000);
        motor.setStatusFramePeriod(12, 1000);
        motor.setStatusFramePeriod(13, 1000);
        motor.setStatusFramePeriod(14, 1000);
        motor.setStatusFramePeriod(21, 1000);
    }
}
