package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Feeder.Feeder;
import frc.robot.subsystems.Floor.Floor;

public class SystemOff extends ParallelCommandGroup{
   
  public SystemOff(Drumm drumm, Feeder feeder, Floor floor) {
    addCommands(
      drumm.DRUMMStop(),
      feeder.FeederStop(),
       floor.FloorStop()
    );
  }




}
