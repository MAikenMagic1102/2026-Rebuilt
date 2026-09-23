package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drum.Drum;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;
import frc.robot.subsystems.Hood.Hood;

public class CloseShot extends SequentialCommandGroup {

    public CloseShot(Drum drumm, Hood hood, Feeder feeder, Floor floor) {
        addCommands(
                hood.HOODNear().alongWith(drumm.DRUMNear()),
                new WaitCommand(1.5),
                feeder.FeederFeed().alongWith(floor.FloorOn()));
    }

}
