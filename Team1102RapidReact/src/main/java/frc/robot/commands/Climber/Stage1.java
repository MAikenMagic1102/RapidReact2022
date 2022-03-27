package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;

public class Stage1 extends SequentialCommandGroup {
    Climber climberPoles;
    ClimberArm climberArm;

    public Stage1(Climber Poles, ClimberArm Arm){

        climberArm = Arm;
        climberPoles = Poles;

        addCommands(
            new RetractPolesFull(climberPoles),
            new WaitCommand(1.5),
            new ArmHooksToBar(climberArm),
            new WaitCommand(1),
            new ExtendPolesShortS1(climberPoles),
            new WaitCommand(1),
            new ExtendPolesShort(climberPoles)

        );
    }
    
}
