// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Drumm.Drumm;
import frc.robot.subsystems.Feeder.Feeder;

import java.time.Instant;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Shoot10sec extends SequentialCommandGroup {
  /** Creates a new Shoot10sec. */
  public Shoot10sec(Drumm drum, Feeder feed) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
      
    // drum.DRUMM4()

    // new WaitCommand(5).andThen(feed.FeederOut()),

    // new WaitCommand(10),

    // drum.DRUMMNO().alongWith(feed.FeederStop())



      // new WaitCommand(10).alongWith(drum.DRUMM4()),
      // feed.FeederOut().alongWith(drum.DRUMM4()),
      // new WaitCommand(10),
      // drum.DRUMMNO(),
      // feed.FeederStop()
    );
  }
}
