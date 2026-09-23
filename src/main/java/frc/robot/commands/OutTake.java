package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Floor.Floor;
import frc.robot.subsystems.Intake.Intake;


public class OutTake extends ParallelCommandGroup {

    public OutTake(Floor floor, Intake intake) {
        addCommands(
            floor.FloorOut(),
            intake.OUT()
        );
    }

}
