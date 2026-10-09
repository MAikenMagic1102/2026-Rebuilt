package frc.robot.subsystems.Pivot;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {

    public static TalonFX Pivot = new TalonFX(1, "can2");
    // public static TalonFX PivotR = new TalonFX(44, "rio");

    private final MotionMagicVoltage m_request = new MotionMagicVoltage(0).withSlot(0);

    public Pivot() {
        var cfg = new TalonFXConfiguration();

        // PID gains
        cfg.Slot0.kP = 10;
        cfg.Slot0.kI = 0;
        cfg.Slot0.kD = 0.2;
        // Feedforward — estimated for a light 10 lb intake pivot
        cfg.Slot0.kS = 0.18;  // volts to overcome static friction
        cfg.Slot0.kV = 0.11;  // volts per rot/s
        cfg.Slot0.kA = 0.005; // volts per rot/s² (light load, low inertia)

        // Motion Magic profile — conservative for a 10 lb intake
        cfg.MotionMagic.MotionMagicCruiseVelocity = 40;   // rot/s
        cfg.MotionMagic.MotionMagicAcceleration   = 80;   // rot/s²
        cfg.MotionMagic.MotionMagicJerk            = 800;  // rot/s³

        Pivot.getConfigurator().apply(cfg);
        // PivotR.getConfigurator().apply(cfg);
        Pivot.setPosition(0);
    }

    @Override
    public void periodic() {
        // PivotR.setControl(m_follower);
        SmartDashboard.putNumber("Pivot Pos",  Pivot.getPosition().getValueAsDouble());
        SmartDashboard.putNumber("Pivot Volt", Pivot.getMotorVoltage().getValueAsDouble());
        SmartDashboard.putNumber("Pivot Amp",  Pivot.getStatorCurrent().getValueAsDouble());

        // SmartDashboard.putNumber("Pivot R Pos",  PivotR.getPosition().getValueAsDouble());
        // SmartDashboard.putNumber("Pivot R Volt", PivotR.getMotorVoltage().getValueAsDouble());
        // SmartDashboard.putNumber("Pivot R Amp",  PivotR.getStatorCurrent().getValueAsDouble());
    }

    public void PIVOTDown() {
        Pivot.setControl(m_request.withPosition(9.4));
        // PivotR.set(0.25);
    }

    public void PIVOTUp() {
        Pivot.setControl(m_request.withPosition(0.0));
        // PivotR.set(-0.3);
    }

    public void PIVOTStop() {
        Pivot.stopMotor();
        // PivotR.stopMotor();
    }

    public Command PivotDown() { return runOnce(this::PIVOTDown); }
    public Command PivotUp()   { return runOnce(this::PIVOTUp); }
    public Command PivotStop() { return runOnce(this::PIVOTStop); }

}