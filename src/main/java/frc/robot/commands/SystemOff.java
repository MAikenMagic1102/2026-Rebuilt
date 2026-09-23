package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Drum.Drum;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;

public class SystemOff extends ParallelCommandGroup{
   
  public SystemOff(Drum drumm, Feeder feeder, Floor floor) {
    addCommands(
      drumm.DRUMStop(),
      feeder.FeederStop(),
       floor.FloorStop()
    );
  }




}
