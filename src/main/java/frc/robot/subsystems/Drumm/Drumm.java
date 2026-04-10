package frc.robot.subsystems.Drumm;


import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.VelocityVoltage;
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

        drumConfig.Slot0.kP = 0.1;   // Proportional: reacts to error
        drumConfig.Slot0.kI = 0.0;   // Integral: usually leave at 0 for flywheels
        drumConfig.Slot0.kD = 0.0;   // Derivative: usually leave at 0 for flywheels
        drumConfig.Slot0.kV = 0.12;  // Velocity feedforward: the main term for flywheels
                                    // Approximate: 12V / (max RPS of Falcon) ≈ 12/100 = 0.12
        drumConfig.Slot0.kS = 0.1;   // Static friction feedforward
        
        drumConfig.ClosedLoopRamps.VoltageClosedLoopRampPeriod = 0.5;

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

    private final VelocityVoltage m_velocity = new VelocityVoltage(0).withSlot(0);
private double targetRPS = 0;

public void DrummSetVelocity(double rps) {
    targetRPS = rps;
    DrummL.setControl(m_velocity.withVelocity(rps));
    // Note: DrummR follows DrummL via Follower, no need to set it separately
}

public void DrummAutoRange(double distanceMeters) {
    double rps = DrummConstants.kRPSMap.get(distanceMeters);
    DrummSetVelocity(-rps);  // negative for your shooting direction
}

public boolean isAtSpeed() {
    if (targetRPS == 0) return false;
    double currentRPS = DrummL.getVelocity().getValueAsDouble();
    return Math.abs(Math.abs(targetRPS) - Math.abs(currentRPS)) < 2.0;  // ±2 RPS tolerance
}

    public void DrummOut(){
      DrummL.setVoltage(-6.0);
      // DrummR.setVoltage(12);

    }
    

    public void DrummStop(){
      DrummL.setVoltage(0);
      // DrummR.setVoltage(0);

    }

    public void DrummClean(){
      DrummL.setVoltage(6);
      // DrummR.setVoltage(-12);

    }

    public Command DRUMMGO(){
      return runOnce(
        () -> {
            DrummOut();
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
