package frc.robot.subsystems.Hood;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.controls.StaticBrake;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;

public class Hood extends SubsystemBase{

    public static TalonFX hoodMotorFx = new TalonFX(20, "rio");
    final PositionVoltage m_hood = new PositionVoltage(0).withSlot(0);

    public Hood(){
        
        var Slot0Configs = new Slot0Configs();

        // PID values
        Slot0Configs.kP = 6;
        Slot0Configs.kI = 3;
        Slot0Configs.kD = 0;

        hoodMotorFx.getConfigurator().apply(Slot0Configs);
    }

    public void hoodToAngle(){

        // HoodPCT is the hood angle expressed as a percent of the maximum angle.
        // This is the primary method of hood control
        double hoodPCT = BobotState.getHoodAngle();

        // HoodRaw is the raw output sent to the motor. Expressed as rotations
        double hoodRaw = 0.175 - (1.475 * (hoodPCT / 100));
        SmartDashboard.putNumber("Hood Raw", hoodRaw);

        hoodMotorFx.setControl(m_hood.withPosition(hoodRaw));
    }

    public void hoodStop(){
        // This stops the hood at its current angle
        hoodMotorFx.setControl(new StaticBrake());
    }


    public Command runHood(){

        return run(
            () -> {
                hoodToAngle();
            }
        );
    }

    public Command hoodBrake(){

        return run(
            () -> {
                hoodStop();
            }
        );
    }
}