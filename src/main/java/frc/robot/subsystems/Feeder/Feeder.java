package frc.robot.subsystems.Feeder;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Feeder extends SubsystemBase {
    public static TalonFX feederMotor = new TalonFX(23, "rio");

    //TODO: Move voltage control values, configurations, and CAN ids to a FeederConstants.Java

    public Feeder(){
        TalonFXConfiguration feederConfig = new TalonFXConfiguration();
        feederConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
        feederConfig.CurrentLimits.SupplyCurrentLimit = 80;
        feederConfig.CurrentLimits.StatorCurrentLimit = 100;

        feederMotor.getConfigurator().apply(feederConfig);
    }

    @Override
    public void periodic() {
      SmartDashboard.putNumber("feeder Speed RPM", feederMotor.getVelocity().getValueAsDouble() * 60);
      SmartDashboard.putNumber("feeder Voltage", feederMotor.getMotorVoltage().getValueAsDouble());
      SmartDashboard.putNumber("feeder Current (A)", feederMotor.getStatorCurrent().getValueAsDouble());

      
    }
    

    public void FeederON(){
        feederMotor.setVoltage(-11);
    }

    public void FeederCLEAR(){
        feederMotor.setVoltage(-12);
    }

    public void FeederSTOP(){
        feederMotor.setVoltage(0);
    }

    public Command FeederFeed(){
       return Commands.runOnce(() -> FeederON());
    }
     public Command FeederClean(){
       return Commands.runOnce(() -> FeederCLEAR());
    }
     public Command FeederStop(){
       return Commands.runOnce(() -> FeederSTOP());
    }
}
