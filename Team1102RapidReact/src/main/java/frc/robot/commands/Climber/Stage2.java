package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;

public class Stage2 extends SequentialCommandGroup {
    Climber climberPoles;
    ClimberArm climberArm;

    public Stage2(Climber Poles, ClimberArm Arm){

        climberArm = Arm;
        climberPoles = Poles;

        addCommands(
            new ExtendPolesShort(climberPoles),
            new WaitCommand(1),
            new ArmHooksToStandBy(climberArm),
            new WaitCommand(0.5),
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
