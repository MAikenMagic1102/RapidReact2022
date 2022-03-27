package frc.robot.commands.Climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ClimberArm;

public class Stage3 extends SequentialCommandGroup {
    Climber climberPoles;
    ClimberArm climberArm;

    public Stage3(Climber Poles, ClimberArm Arm){

        climberArm = Arm;
        climberPoles = Poles;

        addCommands(
            new ExtendPolesShort(climberPoles),
            new WaitCommand(1),
            new ArmHooksToStandBy(climberArm)
        );
    }
}
