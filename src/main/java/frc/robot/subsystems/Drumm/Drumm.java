package frc.robot.subsystems.Drumm;


import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;



public class Drumm extends SubsystemBase {
    
    public static TalonFX DrummR = new TalonFX(47, "rio");
    public static TalonFX DrummR2 = new TalonFX(59, "rio");
    public static TalonFX DrummL = new TalonFX(50, "rio");
    public static TalonFX DrummL2 = new TalonFX(32, "rio");
    private final Follower m_follower = new Follower(DrummL.getDeviceID(), MotorAlignmentValue.Opposed);
    private final Follower m_followerL = new Follower(DrummL.getDeviceID(), MotorAlignmentValue.Aligned);
    private final Follower m_followerR = new Follower(DrummR.getDeviceID(), MotorAlignmentValue.Aligned);
    public double VoltageClosedLoopRampPeriod = 1;
    

    public Drumm(){

        // var Slot0Configs = new Slot0Configs();

        // Slot0Configs.kP = 5;
        // Slot0Configs.kI = 0;
        // Slot0Configs.kD = 0;

        TalonFXConfiguration drumConfig = new TalonFXConfiguration();
        drumConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        drumConfig.CurrentLimits.SupplyCurrentLimit = 80;
        

        DrummL.getConfigurator().apply(drumConfig);
        DrummR.getConfigurator().apply(drumConfig);
        DrummR2.getConfigurator().apply(drumConfig);
        DrummL2.getConfigurator().apply(drumConfig);


        
        
        // DrummR.setControl(new Follower(1, MotorAlignmentValue.Opposed));
    }

    @Override
    public void periodic() {
      DrummR.setControl(m_follower);
      DrummR2.setControl(m_followerR);
      DrummL2.setControl(m_followerL);
      SmartDashboard.putNumber("Shooter L Speed RPM", DrummL.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Shooter L Voltage", DrummL.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Shooter L Current (A)", DrummL.getStatorCurrent().getValueAsDouble());

      SmartDashboard.putNumber("Shooter L2 Speed RPM", DrummL2.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Shooter L2 Voltage", DrummL2.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Shooter L2 Current (A)", DrummL2.getStatorCurrent().getValueAsDouble());

      SmartDashboard.putNumber("Shooter R Speed RPM", DrummR.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Shooter R Voltage", DrummR.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Shooter R Current (A)", DrummR.getStatorCurrent().getValueAsDouble());

      SmartDashboard.putNumber("Shooter R2 Speed RPM", DrummR2.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Shooter R2 Voltage", DrummR2.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Shooter R2 Current (A)", DrummR2.getStatorCurrent().getValueAsDouble());
    }

    public void Drumm4(){
      DrummL.setVoltage(-6.0);
      // DrummR.setVoltage(12);

    }

      public void Drumm7(){
      DrummL.setVoltage(-6.35);
      // DrummR.setVoltage(12);

    }
    

      public void Drumm9(){
      DrummL.setVoltage(-7.75);
      // DrummR.setVoltage(12);

    }
    
    
    public void DrummAutoRange() {
    double voltage = DrummConstants.kVoltageMap.get(BobotState.getDrummDistance());
    SmartDashboard.putNumber("Drumm Voltage", voltage);
    DrummL.setVoltage(voltage);  // positive because we used negative values in points
    }


    public void DrummStop(){
      DrummL.setVoltage(0);
      // DrummR.setVoltage(0);

    }

    public void DrummClean(){
      DrummL.setVoltage(6);
      // DrummR.setVoltage(-12);

    }

    public Command DRUMM4(){
      return runOnce(
        () -> {
            Drumm4();
        }

      );
    }

        public Command DRUMM7(){
      return runOnce(
        () -> {
            Drumm7();
        }

      );
    }
        public Command DRUMM9(){
      return runOnce(
        () -> {
            Drumm9();
        }

      );
    }

     public Command DRUMMNO(){
     return runOnce(
        () -> {
            DrummStop();
        }
      );
    }

     public Command DRUMMCLEAN(){
        return runOnce(
        () -> {
            DrummClean();
        }
      );
    }

}
