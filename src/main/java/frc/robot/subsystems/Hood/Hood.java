package frc.robot.subsystems.Hood;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

public class Hood extends SubsystemBase{
    public static TalonFX hood_motor = new TalonFX(0 ,"rio");
    public static CANcoder hoodCANcoder = new CANcoder(0,"rio");
    
    
    private DutyCycleOut dutyOut = new DutyCycleOut(0);
    private PositionVoltage posVoltage = new PositionVoltage(0);
    private MotionMagicVoltage mmVoltage = new MotionMagicVoltage(0);


    private enum scoreTarget {
       Home,
       Pos1,
       Pos2,
       Pos3,

    };

    private scoreTarget currentAngleTarget = scoreTarget.Home;

    private double HoodTargetAngle = 0.0;

    boolean closedLoop = false;


    public double getAngleDegrees(){
        return Units.rotationsToDegrees(hoodCANcoder.getPosition().getValueAsDouble() / HoodConstants.hoodCANcoderGearing);
    }
    public boolean HoodAtScoring(){
        return getAngleDegrees() < 15;
    }

  public boolean HoodAtHome(){
    return getAngleDegrees() > 10 && getAngleDegrees() < 12;
  }
  
  public boolean atGoal(){
    return Math.abs(HoodTargetAngle - getAngleDegrees()) < HoodConstants.positionTolerence;
  }

  public void setOpenLoop(double demand){
    dutyOut.withOutput(demand);
    hood_motor.setControl(dutyOut);
    closedLoop = false;
  }

  public void setAnglePosition(double angle){
    HoodTargetAngle = angle;
    posVoltage.withPosition(Units.degreesToRotations(angle));
    hood_motor.setControl(posVoltage);
    closedLoop = true;
  }

  public Command setAngle(double angle){
    return runOnce(() -> setAnglePosition(angle));
  }
  

}
