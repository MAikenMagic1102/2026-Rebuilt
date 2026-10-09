package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Drumm.Drumm;
import lombok.Getter;
import lombok.Setter;
import frc.robot.subsystems.Hood.Hood;
import frc.robot.subsystems.util.ShotTable;

public class BobotState {
    @Getter @Setter
    private static Pose2d globalPose = new Pose2d();
    @Getter @Setter
    private static Translation2d distanceToHub = new Translation2d();
    @Getter @Setter
    private static double shooterSpeed = 0.0;
    @Getter @Setter 
    private static double hoodAngle = 0.0;
    @Getter @Setter
    private static CommandSwerveDrivetrain m_Drivetrain = TunerConstants.createDrivetrain();
    @Getter @Setter
    private static double drummDistance = 0.0;
    @Getter @Setter
    private static Hood hood = new Hood();
    @Getter @Setter
    private static Drumm drumm = new Drumm();
    @Getter @Setter
    private static ShotTable shotTable = ShotTable.maikenMagicTable();
    @Getter @Setter
    private static double distanceToHubActual;
}
