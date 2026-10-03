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
    
  // Talon motor drumm right
    public static TalonFX DrummLB = new TalonFX(24, "rio"); //Left Bottom
    public static TalonFX DrummLT = new TalonFX(25, "rio"); //Left Top
    public static TalonFX DrummRT = new TalonFX(26, "rio"); //Right Top
    public static TalonFX DrummRB = new TalonFX(27, "rio"); //Right Bottom

    // private final Follower m_followerLT = new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Aligned);
    // private final Follower m_followerRT = new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed);
    // private final Follower m_followerRB = new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed);
    public double VoltageClosedLoopRampPeriod = 1;
    

    public Drumm(){

        // var Slot0Configs = new Slot0Configs();

        // Slot0Configs.kP = 5;
        // Slot0Configs.kI = 0;
        // Slot0Configs.kD = 0;

        TalonFXConfiguration drumConfig = new TalonFXConfiguration();
        // drumConfig.Slot0.kS = 0.1;
        // drumConfig.Slot0.kV = 0.12; // * 2,3,4,5,6,7,8,9;
        // drumConfig.Slot0.kP = 0.11;
        // drumConfig.Slot0.kI = 0;
        // drumConfig.Slot0.kD = 0;
        drumConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        drumConfig.CurrentLimits.SupplyCurrentLimit = 80;
        

        DrummLB.getConfigurator().apply(drumConfig);
        DrummLT.getConfigurator().apply(drumConfig);
        DrummRT.getConfigurator().apply(drumConfig);
        DrummRB.getConfigurator().apply(drumConfig);

        DrummLT.setControl(new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Aligned)); // Aligned
        DrummRT.setControl(new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed));  // Opposed
        DrummRB.setControl(new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed));  // Opposed

        
        
        // DrummR.setControl(new Follower(1, MotorAlignmentValue.Opposed));
    }

    @Override
    public void periodic() {
      // DrummLT.setControl(m_followerLT);
      // DrummRT.setControl(m_followerRT);
      // DrummRB.setControl(m_followerRB);

      SmartDashboard.putNumber("Drumm LB Speed RPM", DrummLB.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Drumm LB Voltage", DrummLB.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Drumm LB Current (A)", DrummLB.getStatorCurrent().getValueAsDouble());

      SmartDashboard.putNumber("Drumm LT Speed RPM", DrummLT.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Drumm LT Voltage", DrummLT.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Drumm LT Current (A)", DrummLT.getStatorCurrent().getValueAsDouble());

      SmartDashboard.putNumber("Drumm RB Speed RPM", DrummRB.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Drumm RB Voltage", DrummRB.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Drumm RB Current (A)", DrummRB.getStatorCurrent().getValueAsDouble());

      SmartDashboard.putNumber("Drumm RT Speed RPM", DrummRT.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("Drumm RT Voltage", DrummRT.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("Drumm RT Current (A)", DrummRT.getStatorCurrent().getValueAsDouble());
    }
    
    public void DrummStop(){
      DrummLB.setVoltage(0);
    }

    public void DrummClean(){
      DrummLB.setVoltage(6);
    }

    public void DrummNear(){
      DrummLB.setVoltage(-4.0);
    }

      public void DrummFar(){
      DrummLB.setVoltage(-5.0);
    }
    

      public void DrummShuttle(){
      DrummLB.setVoltage(-9.0);
    }
    
    // public void DrummAutoRange() {
    // double voltage = DrummConstants.kVoltageMap.get(BobotState.getDrummDistance());
    // SmartDashboard.putNumber("Drumm Voltage", voltage);
    // DrummL.setVoltage(voltage);  // positive because we used negative values in points
    // }
    
    public Command DRUMMNear(){
      return runOnce(
        () -> {
            DrummNear();
        }
      );
    }

        public Command DRUMMFar(){
      return runOnce(
        () -> {
            DrummFar();
        }
      );
    }
        public Command DRUMMShuttle(){
      return runOnce(
        () -> {
            DrummShuttle();
        }
      );
    }

     public Command DRUMMStop(){
     return runOnce(
        () -> {
            DrummStop();
        }
      );
    }

     public Command DRUMMClean(){
        return runOnce(
        () -> {
            DrummClean();
        }
      );
    }

}
