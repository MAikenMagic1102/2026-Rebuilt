package frc.robot.subsystems;

import com.ctre.phoenix6.StatusCode;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.MotorAlignmentValue;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.filter.Debouncer;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.units.PerUnit;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.OperatorConstants;

import com.ctre.phoenix6.configs.Slot1Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicVoltage;


import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Second;



import edu.wpi.first.units.DistanceUnit;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.units.measure.LinearVelocity;


import edu.wpi.first.wpilibj.DutyCycle;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.motorcontrol.PWMMotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Pivot extends SubsystemBase{

private TalonFX pivotLeftMotorFx;
private TalonFX pivotRightMotorFx;

private TalonFXSimState rollerSimState;
private TalonFXSimState pivotSimState;

private DCMotor pivotGearbox = DCMotor.getKrakenX60Foc(1);

 private DutyCycleOut dutyCycleOutput = new DutyCycleOut(0);

private SingleJointedArmSim PivotIntakeSim = 
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

    pivotLeftMotorFx.setControl(new Follower(pivotRightMotorFx.getDeviceID(), MotorAlignmentValue.Opposed));


StatusCode status = StatusCode.StatusCodeNotInitialized;

for (int i = 0; i < 5; ++i ){
  status = pivotLeftMotorFx.getConfigurator().apply(Constants.pivotConfig);
      if (status.isOK()) break;
    }
    if (!status.isOK()) {
      System.out.println("Could not apply configs, error code: " + status.toString());
    }

//     StatusCode status2 = StatusCode.StatusCodeNotInitialized;

// for (int i = 0; i < 5; ++i ){
//   status2 = pivotRightMotorFx.getConfigurator().apply(Constants.pivotConfig);
//       if (status2.isOK()) break;
//     }
//     if (!status2.isOK()) {
//       System.out.println("Could not apply configs, error code: " + status2.toString());
//     }
}

public void startPosition () {
  pivotLeftMotorFx.setControl(posVoltageUp);
  // pivotRightMotorFx.setControl(posVoltageUp);
}
public void downPosition () {
    pivotLeftMotorFx.setControl(posVoltageDown);
    // pivotRightMotorFx.setControl(posVoltageDown);
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

  public static Distance rotationsToMeters(Angle rotations) {
    /* Apply gear ratio to input rotations */
    var gearedRadians = rotations.in(Radians) / Constants.pivotGearRatio;
    /* Then multiply the wheel radius by radians of rotation to get distance */
    return Constants.pivotTotalDistance.times(gearedRadians);
  }

  public double getPositionMeters() {
    return rotationsToMeters(pivotLeftMotorFx.getRotorPosition().getValue()).in(Meters);
  }


  public double getPivotAngle(){
    return Units.rotationsToDegrees(pivotLeftMotorFx.getPosition().getValueAsDouble());
  }

  public void setAngle(double angle){
    targetPosition = angle;
    posVoltage.withPosition(Units.degreesToRotations(angle)).withEnableFOC(true);
    pivotLeftMotorFx.setControl(posVoltage);
    // pivotRightMotorFx.setControl(posVoltage);
  }

  public Command  setAngleCommand(double angle){
    return runOnce(() -> setAngle(angle));
  }

   public double upGoalPos(){
    return 4.95;
  }
  
   public double downGoalPos(){
    return 0.45;
  }

  public void setOpenLoop(double input){
    dutyCycleOutput.withOutput(input);

    pivotLeftMotorFx.setControl(dutyCycleOutput);
  }


}
//intakepivot 4.95, 0.45
//intake pivot 2 -0.45. -4.95
//hood 0.145, -1.305
