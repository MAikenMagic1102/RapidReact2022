package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDH extends SubsystemBase{
    PowerDistribution REV_PDH;

    public PDH(){
        REV_PDH = new PowerDistribution(1, ModuleType.kRev);

        REV_PDH.clearStickyFaults();
    }
}
