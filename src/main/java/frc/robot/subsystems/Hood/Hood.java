package frc.robot.subsystems.Hood;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;

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

    public void HoodClimber(){
        hoodMotorFx.setControl(m_hood.withPosition(-0.12));
    }

    public void HoodTrench(){
        hoodMotorFx.setControl(m_hood.withPosition(-0.12));
    }

        public void HoodHP(){
        hoodMotorFx.setControl(m_hood.withPosition(-0.2675));
    }

            public void HoodVIS(){
        double hoodRaw = 0.175 - (1.475 * (BobotState.getHoodAngle() / 100));
        hoodMotorFx.setControl(m_hood.withPosition(hoodRaw));
        SmartDashboard.putNumber("Hood Raw", hoodRaw);

    }

        public void HoodStop(){
        hoodMotorFx.setControl(m_hood.withPosition(0));
    }


    public Command HoodGoClimber(){

        return run(
            () -> {
                HoodClimber();
            }
        );

    }

        public Command HoodGoTrench(){

        return run(
            () -> {
                HoodTrench();
            }
        );

    }

        public Command HoodGoHP(){

        return run(
            () -> {
                HoodHP();
            }
        );

    }

            public Command HoodVision(){

        return run(
            () -> {
                HoodVIS();
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
