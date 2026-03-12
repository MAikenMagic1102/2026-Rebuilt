package frc.robot.logging;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.epilogue.CustomLoggerFor;
import edu.wpi.first.epilogue.logging.ClassSpecificLogger;
import edu.wpi.first.epilogue.logging.EpilogueBackend;

@CustomLoggerFor(TalonFX.class)
public class TalonFXLogger extends ClassSpecificLogger<TalonFX> {
    public TalonFXLogger() {
        super(TalonFX.class);
    }

    @Override
    public void update(EpilogueBackend backend, TalonFX motor) {
        backend.log("Voltage", motor.getMotorVoltage().getValueAsDouble());
        backend.log("StatorCurrent", motor.getStatorCurrent().getValueAsDouble());
        backend.log("Velocity", motor.getVelocity().getValueAsDouble());
        backend.log("Position", motor.getPosition().getValueAsDouble());
    }
}
