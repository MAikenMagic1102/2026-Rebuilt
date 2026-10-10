package frc.robot.subsystems.Hood;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
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
  final MotionMagicVoltage m_hood = new MotionMagicVoltage(0).withSlot(0);
  // Converts absolute physical hood angle (degrees) to motor rotations
  // Negative motor = more hood angle, so we subtract the home offset and negate
  private double hoodDegreesToMotor(double physicalDegrees) {
      return -(physicalDegrees - HoodConstants.HoodStartingAngle) * HoodConstants.hoodGearing / 360.0;
  }

  // Returns current absolute physical hood angle in degrees (11 = home, 55 = max)
  public double getHoodAngleDegrees() {
      double relativeAngle = hoodCANcoder.getPosition().getValueAsDouble() * 360.0 / (182.0 / 10.0);
      return HoodConstants.HoodStartingAngle + relativeAngle;
  }
 public Hood(){


    var Slot0Configs = new Slot0Configs();

    Slot0Configs.kS = .15;
    Slot0Configs.kV = 0.5;
    Slot0Configs.kP = 12;
    Slot0Configs.kI = 0.2;
    Slot0Configs.kD = 0.005;

    hoodMotorFx.getConfigurator().apply(Slot0Configs);

    var mmConfigs = new MotionMagicConfigs();
    mmConfigs.MotionMagicCruiseVelocity = 10; // motor rotations/sec — tune me
    mmConfigs.MotionMagicAcceleration = 20;   // motor rotations/sec² — tune me
    hoodMotorFx.getConfigurator().apply(mmConfigs);

    SmartDashboard.putNumber("Hood Target Angle", HoodConstants.HoodStartingAngle);
    setDefaultCommand(run(() -> {
        double target = SmartDashboard.getNumber("Hood Target Angle", HoodConstants.HoodStartingAngle);
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(target)));
    }));

    // Seed TalonFX encoder from CANcoder absolute position so motor position 0 = home (11°)
    // regardless of where the TalonFX magnetic encoder happens to boot.
    var absSignal = hoodCANcoder.getAbsolutePosition();
    BaseStatusSignal.waitForAll(0.1, absSignal);
    double absCANcoder = absSignal.getValueAsDouble();
    hoodMotorFx.setPosition(-absCANcoder * HoodConstants.hoodGearing / (182.0 / 10.0));

}

 @Override
    public void periodic() {
            SmartDashboard.putNumber("Hood Angle (deg)", getHoodAngleDegrees());
            SmartDashboard.putNumber("Hood Raw", hoodCANcoder.getPosition().getValueAsDouble());
            SmartDashboard.putNumber("Hood Absolute", hoodCANcoder.getAbsolutePosition().getValueAsDouble());
            SmartDashboard.putNumber("Hood Motor Position", hoodMotorFx.getPosition().getValueAsDouble());
    }
    //Home is 11 degrees and max is 55 using an angle finder

    public void HoodHomePos(){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(11.0)));  // physical minimum
    }

    public void HoodClosePos(){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(20.0)));  // tune me
    }

    public void MiddleHoodPos(){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(33.0)));  // tune me
    }

    public void HoodTowerPos(){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(25.0)));  // tune me
    }

    public void HoodFarPos(){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(45.0)));  // tune me
    }

    public void MaxHoodPos(){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(55.0)));  // physical maximum
    }

    public void hoodVariable(double position){
        hoodMotorFx.setControl(m_hood.withPosition(hoodDegreesToMotor(position)));
    }

    public Command HOODHome(){

        return runOnce(
            () -> {
                HoodHomePos();
            }
        );
    }

    public Command CLOSEPOSE(){
        return runOnce(
            () -> {
                HoodClosePos();
            }
        );
    }

    public Command MIDDLEPOS(){

        return runOnce(
            () -> {
                MiddleHoodPos();
            }
        );
    }
    
    public Command HOODTOWER(){

        return runOnce(
            () -> {
                HoodTowerPos();
            }
        );
    }

    public Command HOODFARPOS() {
        return runOnce(
            () -> {
                HoodFarPos();
            }
        );
    }

    public Command MaxHOODPOS(){

        return runOnce(
            () -> {
                MaxHoodPos();
            }
        );
    }

    public Command HOODVariable(double angle){
        
        return runOnce(
            () -> {
                hoodVariable(angle);
            }
        );
    }

}