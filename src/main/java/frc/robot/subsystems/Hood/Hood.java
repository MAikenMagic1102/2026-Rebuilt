package frc.robot.subsystems.Hood;
import edu.wpi.first.math.util.Units;

import com.ctre.phoenix6.hardware.TalonFX;

public class Hood {
    public static TalonFX hood_motor = new TalonFX(0 ,"rio");



    private enum scoreTarget {
       Home,
       Pos1,
       Pos2,
       Pos3,

    };

    private scoreTarget currenTarget = scoreTarget.Home;

    private double HoodTargetAngle = 0.0;
     public double getAngleDegrees(){
    return Units.rotationsToDegrees(armCaNcoder.getPosition().getValueAsDouble() / ArmConstants.armGearingCANcoder);
  }



}
