package frc.robot.subsystems.Hood;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

public class Hood extends SubsystemBase{
    public static TalonFX hoodMotorFx = new TalonFX(28 ,"rio");
    public static CANcoder hoodCANcoder = new CANcoder(22,"rio");
    
    
  //   private DutyCycleOut dutyOut = new DutyCycleOut(0);
  //   private PositionVoltage posVoltage = new PositionVoltage(0);
  //   private MotionMagicVoltage mmVoltage = new MotionMagicVoltage(0);


  //   private enum scoreTarget {
  //      Home,
  //      Pos1,
  //      Pos2,
  //      Pos3,
  //      Pos4,

  //   };

  //   private scoreTarget currentAngleTarget = scoreTarget.Home;

  //   private double HoodTargetAngle = HoodConstants.HoodHome;

  //   boolean closedLoop = false;

  //               public void HoodAngle(){
  //       SmartDashboard.putNumber("Hood Raw", getAngleDegrees());

  //   }

 
  //   }
  //   public boolean HoodAtScoring(){
  //       return getAngleDegrees() < 15;
  //   }

  // public boolean HoodAtHome(){
  //   return getAngleDegrees() > 10 && getAngleDegrees() < 12;
  // }
  
  // public boolean atGoal(){
  //   return Math.abs(HoodTargetAngle - getAngleDegrees()) < HoodConstants.positionTolerence;
  // }

  // public void setOpenLoop(double demand){
  //   dutyOut.withOutput(demand);
  //   hood_motor.setControl(dutyOut);
  //   closedLoop = false;
  // }

  // public void setAnglePosition(double angle){
  //   HoodTargetAngle = angle;
  //   posVoltage.withPosition(Units.degreesToRotations(angle));
  //   hood_motor.setControl(posVoltage);
  //   closedLoop = true;
  // }

  // public Command setAngle(double angle){
  //   return runOnce(() -> setAnglePosition(angle));
  // }
  final PositionVoltage m_hood = new PositionVoltage(0).withSlot(0);
 public Hood(){
    double hoodRaw = 0.175 - (1.475 * (BobotState.getHoodAngle() / 100));
    SmartDashboard.putNumber("Hood Raw", hoodRaw);


    var Slot0Configs = new Slot0Configs();

    Slot0Configs.kS = .15;
    Slot0Configs.kV = 0.5;
    Slot0Configs.kP = 16;
    Slot0Configs.kI = 0.2;
    Slot0Configs.kD = 0;

    hoodMotorFx.getConfigurator().apply(Slot0Configs);

    
    
}

// 32/19 motor
//10/25 hood positioning

 @Override
    public void periodic() {
            double HOODPOS = (hoodCANcoder.getPosition().getValueAsDouble()*HoodConstants.hoodCANcoderGearing+(32/19));

            

            SmartDashboard.putNumber("Hood angle", HOODPOS);


    }

    public void HoodHomePos(){
        hoodMotorFx.setControl(m_hood.withPosition(-0.20));
    }

    public void HoodPos1(){
        hoodMotorFx.setControl(m_hood.withPosition(-0.12));
    }

    public void HoodPos2(){
        hoodMotorFx.setControl(m_hood.withPosition(-2));
    }
    public void HoodPos3(){
        hoodMotorFx.setControl(m_hood.withPosition(-4.5));
    }

   
  
        public void HoodStop(){
        hoodMotorFx.setControl(m_hood.withPosition(0));
    }


    public Command HOMEPOS(){

        return runOnce(
            () -> {
                HoodHomePos();
            }
        );

    }

        public Command POS1(){

        return run(
            () -> {
                HoodPos1();
            }
        );

    }

        public Command Pos2(){

        return run(
            () -> {
                HoodPos2();
            }
        );

    }
      public Command Pos3(){

        return run(
            () -> {
                HoodPos3();
            }
        );

    }
    

    public Command HoodNO(){

        return run(
            () -> {
                HoodStop();
            }
        );

    }

}