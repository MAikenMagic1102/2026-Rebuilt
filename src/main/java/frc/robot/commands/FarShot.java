package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;
import frc.robot.subsystems.Hood.Hood;

public class FarShot extends SequentialCommandGroup{

    public FarShot(Drumm drumm, Hood hood, Feeder feeder, Floor floor) {
        addCommands(
            hood.HOODFARPOS().alongWith(drumm.DRUMMFar()),
            new WaitCommand(2),
            floor.FloorOn().alongWith(feeder.FeederFeed()),
            new WaitCommand(4),
            drumm.DRUMMStop(),
            feeder.FeederStop(),
            floor.FloorStop(),
            hood.HOODHome()
        
        );
    }

}
