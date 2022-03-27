package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Infastructure extends SubsystemBase{
    private PowerDistribution REV_PDH;
    private PneumaticsControlModule PCM;

    public Infastructure(){
        REV_PDH = new PowerDistribution(1, ModuleType.kRev);
        PCM = new PneumaticsControlModule();

        REV_PDH.clearStickyFaults();
    }

    public void Enabled(){
        PCM.enableCompressorDigital();
    }

    public void Disable(){
        PCM.disableCompressor();
    }
}
