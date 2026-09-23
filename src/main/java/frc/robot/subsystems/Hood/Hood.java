package frc.robot.subsystems.Hood;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BobotState;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

public class Hood extends SubsystemBase {

    // TODO: Move configurations to HoodConstants.java

    public static TalonFX hoodMotorFx = new TalonFX(28, "rio");
    public static CANcoder hoodCANcoder = new CANcoder(22, "rio");

    final PositionVoltage m_hood = new PositionVoltage(0).withSlot(0);

    public Hood() {
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

    @Override

    public void periodic() {
        double HOODPOS = (hoodCANcoder.getPosition().getValueAsDouble() * HoodConstants.hoodCANcoderGearing
                + (32.0 / 19.0));
        SmartDashboard.putNumber("Hood angle", HOODPOS);
    }

    public void HoodHomePos() {
        hoodMotorFx.setControl(m_hood.withPosition(-0.30));
    }

    public void MiddleHoodPos() {
        hoodMotorFx.setControl(m_hood.withPosition(-1.5));
    }

    public void MaxHoodPos() {
        hoodMotorFx.setControl(m_hood.withPosition(-4));
    }

    public Command HOODNear() {
        return runOnce(
                () -> {
                    HoodHomePos();
                });
    }

    public Command MIDDLEPOS() {
        return runOnce(
                () -> {
                    MiddleHoodPos();
                });
    }

    public Command MaxHOODPOS() {
        return runOnce(
                () -> {
                    MaxHoodPos();
                });
    }
}