package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Turret;

/**
 * A complex auto command that drives forward, releases a hatch, and then drives backward.
 */
public class Autonomous extends SequentialCommandGroup {

    public Autonomous(Drivetrain drive, Intake intake, Feeder feed, Shooter shooter, Turret turret){
        addCommands(

            new InstantCommand(intake::IntakeIn),
            new InstantCommand(intake::RearTunnelIn),
            new DriveStraight(drive),

            new AutoShoot(shooter, feed, turret, intake),

            new InstantCommand(intake::IntakeStop, feed)

        );
    }

//   public Autonomous(DriveSubsystem drive, HatchSubsystem hatch) {
//     addCommands(
//         // Drive forward the specified distance
//         new DriveDistance(AutoConstants.kAutoDriveDistanceInches, AutoConstants.kAutoDriveSpeed,
//                           drive),

//         // Release the hatch
//         new ReleaseHatch(hatch),

//         // Drive backward the specified distance
//         new DriveDistance(AutoConstants.kAutoBackupDistanceInches, -AutoConstants.kAutoDriveSpeed,
//                           drive));
//     }

}