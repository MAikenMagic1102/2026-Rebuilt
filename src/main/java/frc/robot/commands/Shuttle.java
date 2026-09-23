package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drum.Drum;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;
import frc.robot.subsystems.Hood.Hood;

public class Shuttle extends SequentialCommandGroup{

    public Shuttle(Drum drumm, Hood hood, Feeder feeder, Floor floor) {
        addCommands(
            hood.MaxHOODPOS().alongWith(drumm.DRUMShuttle()),
            new WaitCommand(1.5),
            floor.FloorOn().alongWith(feeder.FeederFeed())
        );
    }

}
