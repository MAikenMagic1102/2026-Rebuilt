package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;
import frc.robot.subsystems.Hood.Hood;

public class MidRangeShot extends SequentialCommandGroup{


    public MidRangeShot(Drumm drumm, Hood hood, Feeder feeder, Floor floor){
        addCommands(
            hood.MIDDLEPOS().alongWith(drumm.DRUMMFar()),
            new WaitCommand(1.5),
            feeder.FeederFeed().alongWith(floor.FloorOn())
        );
    }


}
