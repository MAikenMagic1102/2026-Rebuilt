package frc.robot.subsystems;


import java.util.function.Supplier;

import com.ctre.phoenix6.swerve.SwerveRequest;

import choreo.auto.AutoFactory;
import choreo.auto.AutoRoutine;
import choreo.auto.AutoTrajectory;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
// import frc.robot.commands.AutoRunToCoral;
// import frc.robot.commands.DriveToPoseCommand;
// import frc.robot.commands.IntakeDeploy;
// import frc.robot.commands.IntakeRetract;
// import frc.robot.commands.IntakeRollersOn;
// import frc.robot.commands.PrepScore;
// import frc.robot.commands.ReturnToHome;
// import frc.robot.commands.ScoreCoral;
// import frc.robot.game_util.FieldUtils;
// import frc.robot.field.ReefPole;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.BobotState;
// import frc.robot.subsystems.Superstructure;
import frc.robot.game_util.FieldConstants;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.Hood.Hood;
import frc.robot.subsystems.Intake.Intake;
import frc.robot.subsystems.Pivot.Pivot;
import frc.robot.subsystems.Shooter.Shooter;
import frc.robot.subsystems.Tower.Tower;
import frc.robot.subsystems.vision.Vision;

public class AutoRoutines extends SubsystemBase{

       private final AutoFactory m_factory;
    private final CommandSwerveDrivetrain drive;
    private final Hood m_hood;
    private final Intake m_intake;
    private final Pivot m_pivot;
    private final Shooter m_shooter;
    private final Tower m_tower;
    private final Vision m_vision;
    private final AutoAlignComand m_autoAlignComand;

     public AutoRoutines(CommandSwerveDrivetrain drive, Hood hood, Intake intake, Pivot pivot, Shooter shooter, Tower tower, Vision vision, AutoAlignComand autoAlignComand) {

        this.drive = drive;
        m_factory = drive.createAutoFactory();
        m_hood = hood;
        m_intake = intake;
        m_pivot = pivot;
        m_shooter = shooter;
        m_tower = tower;
        m_vision = vision;
        m_autoAlignComand = autoAlignComand;
  

        //  final SwerveRequest.FieldCentricFacingAngle driveAtAngle =
        //     new SwerveRequest.FieldCentricFacingAngle()
        //         .withHeadingPID(5, 0, 0); // tune kP


     }
    public AutoRoutine BlueMiddle(){
        AutoRoutine routine = m_factory.newRoutine("Blue");
        final AutoTrajectory blueM = routine.trajectory("BlueMiddle");

            routine.active().onTrue(
            Commands.sequence(   

                blueM.resetOdometry(),
                blueM.cmd(),

                 new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision()
                
                ).alongWith(m_tower.UP()),

                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )

                

            ));

             return routine;

    }

     public AutoRoutine RedMiddle(){
        AutoRoutine routine = m_factory.newRoutine("Red");
        final AutoTrajectory RedM = routine.trajectory("RedMiddle");

            routine.active().onTrue(
            Commands.sequence(   

                RedM.resetOdometry(),
                RedM.cmd(),

                     new ParallelCommandGroup(
                m_autoAlignComand.AutoAlignCommand(),
                new WaitCommand(1)
                 ),

                 new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision()
                
                ).alongWith(m_tower.UP()),

                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )

            ));

             return routine;
    }

    
    
     public AutoRoutine BlueLeft(){
        AutoRoutine routine = m_factory.newRoutine("BlueLeft");
        final AutoTrajectory blueL1 = routine.trajectory("BlueL1");
          final AutoTrajectory blueL2 = routine.trajectory("BlueL2");
            final AutoTrajectory blueL3 = routine.trajectory("BlueL3");

            routine.active().onTrue(
            Commands.sequence(
        //      

                blueL1.resetOdometry(),
                blueL1.cmd(),
                m_pivot.PUP(),
                new WaitCommand(1),
                m_pivot.PSTOP(),

                new ParallelCommandGroup(
                blueL2.cmd(),
                m_intake.IN()
                ),

                new WaitCommand(4),

                m_intake.STOP(),

                blueL3.cmd(),

                new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision(),
                m_tower.UP()

                ),

                new ParallelCommandGroup(
                m_autoAlignComand.AutoAlignCommand(),
                new WaitCommand(1)
                 ),

                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )
            )
        );

             return routine;
    }

     public AutoRoutine BlueRight(){
        AutoRoutine routine = m_factory.newRoutine("BlueRight");
        final AutoTrajectory blueR1 = routine.trajectory("BlueR1");
          final AutoTrajectory blueR2 = routine.trajectory("BlueR2");
            final AutoTrajectory blueR3 = routine.trajectory("BlueR3");

            routine.active().onTrue(
            Commands.sequence(
        //      

                blueR1.resetOdometry(),
                blueR1.cmd(),
                m_pivot.PUP(),
                new WaitCommand(1),
                m_pivot.PSTOP(),

                new ParallelCommandGroup(
                blueR2.cmd(),
                m_intake.IN()
                ),

                new WaitCommand(4),

                m_intake.STOP(),

                blueR3.cmd(),

                new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision(),
                m_tower.UP()
                ),

                     new ParallelCommandGroup(
                m_autoAlignComand.AutoAlignCommand(),
                new WaitCommand(1)
                 ),
                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )
            )
        );

             return routine;
    }


     public AutoRoutine RedLeft(){
        AutoRoutine routine = m_factory.newRoutine("RedLeft");
        final AutoTrajectory redL1 = routine.trajectory("RedL1");
          final AutoTrajectory redL2 = routine.trajectory("RedL2");
            final AutoTrajectory redL3 = routine.trajectory("RedL3");

            routine.active().onTrue(
            Commands.sequence(
        //      

                redL1.resetOdometry(),
                redL1.cmd(),
                m_pivot.PUP(),
                new WaitCommand(1),
                m_pivot.PSTOP(),

                new ParallelCommandGroup(
                redL2.cmd(),
                m_intake.IN()
                ),

                new WaitCommand(4),

                m_intake.STOP(),

                redL3.cmd(),

                new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision(),
                m_tower.UP()
                ),

                     new ParallelCommandGroup(
                m_autoAlignComand.AutoAlignCommand(),
                new WaitCommand(1)
                 ),

                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )
            )
        );

             return routine;
    }

     public AutoRoutine RedRight(){
        AutoRoutine routine = m_factory.newRoutine("RedRight");
        final AutoTrajectory redR1 = routine.trajectory("RedR1");
          final AutoTrajectory redR2 = routine.trajectory("RedR2");
            final AutoTrajectory redR3 = routine.trajectory("RedR3");

            routine.active().onTrue(
            Commands.sequence(
        //      

                redR1.resetOdometry(),
                redR1.cmd(),
                m_pivot.PUP(),
                new WaitCommand(1),
                m_pivot.PSTOP(),

                new ParallelCommandGroup(
                redR2.cmd(),
                m_intake.IN()
                ),

                new WaitCommand(4),

                m_intake.STOP(),

                redR3.cmd(),

                new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision(),
                m_tower.UP()
                ),

                new ParallelCommandGroup(
                m_autoAlignComand.AutoAlignCommand(),
                new WaitCommand(1)
                 ),
                new WaitCommand(1),

                
                new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision(),
                m_tower.UP()
                ),

                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )
            )
        );

             return routine;
    }




     public AutoRoutine Simp(){
        AutoRoutine routine = m_factory.newRoutine("Simple");
        final AutoTrajectory simple = routine.trajectory("Simple");

        routine.active().onTrue(

        Commands.sequence(
              simple.resetOdometry(),
                   simple.cmd(),
   
                new ParallelCommandGroup(

                m_shooter.ShooterSEE(),
                m_hood.HoodVision(),
                m_tower.UP()
                ),

                new ParallelCommandGroup(
                m_autoAlignComand.AutoAlignCommand(),
                new WaitCommand(1)
                 ),

                new WaitCommand(6),
               new ParallelCommandGroup(

                m_shooter.ShooterStop(),
                m_hood.HoodNO(),
                m_tower.TOWERSTOP()
                )
        )
              
        );

        return routine;
}
}