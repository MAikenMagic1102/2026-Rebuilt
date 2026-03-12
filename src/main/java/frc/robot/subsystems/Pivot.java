package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.MotorAlignmentValue;

import edu.wpi.first.epilogue.Logged;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

@Logged
public class Pivot extends SubsystemBase {

    private TalonFX pivotLeftMotorFx;
    private TalonFX pivotRightMotorFx;

    private PositionVoltage positonReq = new PositionVoltage(0).withSlot(0);
    private DutyCycleOut dutyCycleOutput = new DutyCycleOut(0);

    public Pivot() {
        pivotLeftMotorFx = new TalonFX(Constants.pivotLeftID, Constants.busname);
        pivotRightMotorFx = new TalonFX(Constants.pivotRightID, Constants.busname);

        // Apply config to the leader (right) motor
        StatusCode status = StatusCode.StatusCodeNotInitialized;
        for (int i = 0; i < 5; ++i) {
            status = pivotRightMotorFx.getConfigurator().apply(Constants.pivotConfig);
            if (status.isOK()) break;
        }
        if (!status.isOK()) {
            System.out.println("Could not apply configs, error code: " + status.toString());
        }

        // Left motor follows right motor in opposed direction
        pivotLeftMotorFx.setControl(new Follower(pivotRightMotorFx.getDeviceID(), MotorAlignmentValue.Opposed));
    }

    public void pivotUp() {
        pivotRightMotorFx.setControl(positonReq.withPosition(Constants.pivotUp));
    }

    public void pivotDown() {
        pivotRightMotorFx.setControl(positonReq.withPosition(Constants.pivotDown));
    }

    public Command upPositionCommand() {
        return runOnce(() -> pivotUp());
    }

    public Command downPositionCommand() {
        return runOnce(() -> pivotDown());
    }

    public Command downDutyCycle(){
      return runOnce(() -> setDutyCycle(-0.4));
    }

    public Command upDutyCycle(){
      return runOnce(() -> setDutyCycle(0.4));
    }

    public Command stopPivot(){
      return runOnce(() -> setDutyCycle(0));
    }

    @Logged(name = "AngleDeg")
    public double getPivotAngle() {
        return Units.rotationsToDegrees(pivotRightMotorFx.getPosition().getValueAsDouble());
    }

    //maybe we use this for the commands? Depends on if the angle is being read correctly.
    public void setAngle(double angle) {
        pivotRightMotorFx.setControl(positonReq.withPosition(Units.degreesToRotations(angle)));
    }

    public Command setAngleCommand(double angle) {
        return runOnce(() -> setAngle(angle));
    }

    public void setDutyCycle(double output) {
        pivotRightMotorFx.setControl(dutyCycleOutput.withOutput(output));
    }
}
