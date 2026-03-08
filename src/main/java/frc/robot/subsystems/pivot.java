package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.PerUnit;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Pivot extends SubsystemBase{

private TalonFX pivotLeftMotorFx;
private TalonFX pivotRightMotorFx;

private TalonFXSimState rollerSimState;
private TalonFXSimState pivotSimState;

private DCMotor pivotGearbox = DCMotor.getKrakenX60Foc(1);

private SingleJointedArmSim CoralIntakeSim = 
  new SingleJointedArmSim(
        pivotGearbox,
        Constants.pivotGearRatio,
        SingleJointedArmSim.estimateMOI(Constants.pivotLength, Constants.pivotMass),
        Constants.pivotLength,
        Constants.pivotMinAngle,
        Constants.pivotMaxAngle,
        false,
        Constants.pivotStartingAngle);

  private DutyCycleOut pivotOut = new DutyCycleOut(0);
  private PositionVoltage posVoltage = new PositionVoltage(0).withSlot(0);
  private boolean isClosedLoop = false;

  private boolean L1Mode = false;

  private double targetPosition = 0;

  private double pivotSpeed = 0;


  // AdvantageScope log paths
  private final String loggerPath = "Subsystems/Pivot";
  private final String motorLoggerPath = loggerPath + "/Motors";
  private final String pivotMotorLoggerPath = motorLoggerPath + "/Pivot";
  private Debouncer pivotDebouncer;


private PositionVoltage posVoltageDown = new PositionVoltage(Constants.pivotDown);
private PositionVoltage posVoltageUp = new PositionVoltage(Constants.pivotUp);

public Pivot () {
    pivotLeftMotorFx = new TalonFX(Constants.pivotLeftID, Constants.busname);
    pivotRightMotorFx = new TalonFX(Constants.pivotRightID, Constants.busname);
    pivotDebouncer = new Debouncer(0.1);

    //pivot roller

StatusCode status = StatusCode.StatusCodeNotInitialized;

for (int i = 0; i < 5; ++i ){
  status = pivotLeftMotorFx.getConfigurator().apply(Constants.pivotConfig);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }

    StatusCode status2 = StatusCode.StatusCodeNotInitialized;

for (int i = 0; i < 5; ++i ){
  status2 = pivotRightMotorFx.getConfigurator().apply(Constants.pivotConfig);
      if (status2.isOK()) break;
    }
    if (!status2.isOK()) {
      System.out.println("Could not apply configs, error code: " + status2.toString());
    }
}



public void startPosition () {
  pivotLeftMotorFx.setControl(posVoltageUp);
  pivotRightMotorFx.setControl(posVoltageUp);
}
public void downPosition () {
    pivotLeftMotorFx.setControl(posVoltageDown);
    pivotRightMotorFx.setControl(posVoltageDown);
}
public Command upPositionCommand () {
    return runOnce(
        () -> {
            startPosition();
        }
    );
    
}
public Command downPositionCommand () {
    return runOnce(
        () -> {
            downPosition();
        }
    );
}



}
//intakepivot 4.95, 0.45
//intake pivot 2 -0.45. -4.95
//hood 0.145, -1.305