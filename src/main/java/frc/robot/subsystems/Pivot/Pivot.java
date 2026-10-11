package frc.robot.subsystems.Pivot;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pivot extends SubsystemBase {

    public static TalonFX Pivot = new TalonFX(1, "can2");
    // public static TalonFX PivotR = new TalonFX(44, "rio");

    public Pivot() {
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
        Pivot.set(0.25);
        // PivotR.set(0.25);
    }

    public void PIVOTUp() {
        Pivot.set(-0.3);
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
