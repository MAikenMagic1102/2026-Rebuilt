package frc.robot.subsystems.Hood;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Hood extends SubsystemBase{

public static TalonFX hoodMotorFx = new TalonFX(20, "rio");
final PositionVoltage m_hood = new PositionVoltage(0).withSlot(0);
 public Hood(){

    var Slot0Configs = new Slot0Configs();

    Slot0Configs.kP = 6;
    Slot0Configs.kI = 3;
    Slot0Configs.kD = 0;

    hoodMotorFx.getConfigurator().apply(Slot0Configs);
    
    }

    public void HoodUP(){
        hoodMotorFx.setControl(m_hood.withPosition(-1.3));
    }

    public void HoodLOW(){
        hoodMotorFx.setControl(m_hood.withPosition(0.175));
    }

        public void HoodMID(){
        hoodMotorFx.setControl(m_hood.withPosition(-0.5625));
    }

    public Command HoodGoUP(){

        return run(
            () -> {
                HoodUP();
            }
        );

    }

        public Command HoodGoLOW(){

        return run(
            () -> {
                HoodLOW();
            }
        );

    }

        public Command HoodGoMID(){

        return run(
            () -> {
                HoodUP();
            }
        );

    }

}
