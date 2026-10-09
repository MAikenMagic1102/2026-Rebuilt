package frc.robot.subsystems.Drumm;


import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.measure.MutMomentOfInertia;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class Drumm extends SubsystemBase {
    
  // Talon motor drumm right
    public static TalonFX DrummLB = new TalonFX(24, "rio"); //Left Bottom
    public static TalonFX DrummLT = new TalonFX(25, "rio"); //Left Top
    public static TalonFX DrummRT = new TalonFX(26, "rio"); //Right Top
    public static TalonFX DrummRB = new TalonFX(27, "rio"); //Right Bottom

    // private final Follower m_followerLT = new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Aligned);
    // private final Follower m_followerRT = new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed);
    // private final Follower m_followerRB = new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed);
    private final VelocityVoltage m_velocity = new VelocityVoltage(0);

    private PrintWriter csvWriter;
    private int logCounter = 0;

    private double last_kP = 0.8, last_kI = 0.0, last_kD = 0.0, last_kV = 0.103, last_kS = 0.1;

    public Drumm(){
        try {
            String filename = "/home/lvuser/drumm_log_" + (long) Timer.getFPGATimestamp() + ".csv";
            csvWriter = new PrintWriter(new FileWriter(filename, true));
            csvWriter.println("Time(s),LB RPM,LT RPM,RB RPM,RT RPM");
            csvWriter.flush();
        } catch (IOException e) {
            System.err.println("Failed to create drum CSV log: " + e.getMessage());
            csvWriter = null;
        }

        // var Slot0Configs = new Slot0Configs();

        // Slot0Configs.kP = 5;
        // Slot0Configs.kI = 0;
        // Slot0Configs.kD = 0;

        TalonFXConfiguration drumConfig = new TalonFXConfiguration();
        drumConfig.Slot0.kS = 0.1;
        drumConfig.Slot0.kV = 0.103;
        drumConfig.Slot0.kP = 0.8;
        drumConfig.Slot0.kI = 0.0;
        drumConfig.Slot0.kD = 0.001;
        drumConfig.MotionMagic.MotionMagicAcceleration = 800; // RPS/s — tune this
        drumConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        drumConfig.CurrentLimits.SupplyCurrentLimit = 120;
        

        DrummLB.getConfigurator().apply(drumConfig);
        DrummLT.getConfigurator().apply(drumConfig);
        DrummRT.getConfigurator().apply(drumConfig);
        DrummRB.getConfigurator().apply(drumConfig);

        DrummLT.setControl(new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Aligned)); // Aligned
        DrummRT.setControl(new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed));  // Opposed
        DrummRB.setControl(new Follower(DrummLB.getDeviceID(), MotorAlignmentValue.Opposed));  // Opposed

        SmartDashboard.putNumber("Drumm kP", drumConfig.Slot0.kP);
        SmartDashboard.putNumber("Drumm kI", drumConfig.Slot0.kI);
        SmartDashboard.putNumber("Drumm kD", drumConfig.Slot0.kD);
        SmartDashboard.putNumber("Drumm kV", drumConfig.Slot0.kV);
        SmartDashboard.putNumber("Drumm kS", drumConfig.Slot0.kS);

        
        
        // DrummR.setControl(new Follower(1, MotorAlignmentValue.Opposed));
    }

    @Override
    public void periodic() {
      // DrummLT.setControl(m_followerLT);
      // DrummRT.setControl(m_followerRT);
      // DrummRB.setControl(m_followerRB);

      double kP = SmartDashboard.getNumber("Drumm kP", last_kP);
      double kI = SmartDashboard.getNumber("Drumm kI", last_kI);
      double kD = SmartDashboard.getNumber("Drumm kD", last_kD);
      double kV = SmartDashboard.getNumber("Drumm kV", last_kV);
      double kS = SmartDashboard.getNumber("Drumm kS", last_kS);

      if (kP != last_kP || kI != last_kI || kD != last_kD || kV != last_kV || kS != last_kS) {
          Slot0Configs slot0 = new Slot0Configs();
          slot0.kP = kP; slot0.kI = kI; slot0.kD = kD; slot0.kV = kV; slot0.kS = kS;
          DrummLB.getConfigurator().apply(slot0);
          DrummLT.getConfigurator().apply(slot0);
          DrummRT.getConfigurator().apply(slot0);
          DrummRB.getConfigurator().apply(slot0);
          last_kP = kP; last_kI = kI; last_kD = kD; last_kV = kV; last_kS = kS;
      }

      if (++logCounter >= 1) {
        logCounter = 0;
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

      if (DriverStation.isEnabled() && csvWriter != null) {
        csvWriter.printf("%.3f,%.2f,%.2f,%.2f,%.2f%n",
          Timer.getFPGATimestamp(),
          DrummLB.getVelocity().getValueAsDouble() * 60,
          DrummLT.getVelocity().getValueAsDouble() * 60,
          DrummRB.getVelocity().getValueAsDouble() * 60,
          DrummRT.getVelocity().getValueAsDouble() * 60);
        csvWriter.flush();
      }
    }
    
    public void DrummStop(){
      DrummLB.stopMotor();
    }

    public void DrummClean(){
      DrummLB.setControl(m_velocity.withVelocity(3000.0 / 60.0)); // 3000 RPM
    }

    public void DrummClose() {
      DrummLB.setControl(m_velocity.withVelocity(-1750.0/60.0)); // -2000 RPM
    }

    public void DrummMid() {
      DrummLB.setControl(m_velocity.withVelocity(-2000.0/60.0)); // -2000 RPM
    }

    public void DrummTower(){
      DrummLB.setControl(m_velocity.withVelocity(-2000.0 / 60.0)); // -2000 RPM
    }

    public void DrummFar(){
      DrummLB.setControl(m_velocity.withVelocity(-2000.0 / 60.0)); // -2000 RPM
    }

    public void MaxShot(){
      DrummLB.setControl(m_velocity.withVelocity(0));
    }

    public void DrummShuttle(){
      DrummLB.setControl(m_velocity.withVelocity(-4500.0 / 60.0)); // -4500 RPM
    }
    
    public void DrummVariable(double rpm){
      DrummLB.setControl(m_velocity.withVelocity(rpm / 60.0));
    }


    // public void DrummAutoRange() {
    // double voltage = DrummConstants.kVoltageMap.get(BobotState.getDrummDistance());
    // SmartDashboard.putNumber("Drumm Voltage", voltage);
    // DrummL.setVoltage(voltage);  // positive because we used negative values in points
    // }
    



    public Command DRUMMCLOSE() {
      return runOnce(
        () -> {
          DrummClose();
        }
      );
    }

    public Command DRUMMMIDSHOT() {
      return runOnce(
        () -> {
          DrummMid();
        }
      );
    }

    public Command DRUMMTOWER(){
      return runOnce(
        () -> {
            DrummTower();
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

        public Command MAXSHOT () {
          return runOnce(
            () -> {
              MaxShot();
            }
          );
        }

        public Command DRUMMNear(){
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

    public Command DRUMMVariable(double rpm){
      return runOnce(
        () -> {
          DrummVariable(rpm);
        }
      );
    }

}
