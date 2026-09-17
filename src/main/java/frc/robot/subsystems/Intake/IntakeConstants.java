package frc.robot.subsystems.Intake;

import com.ctre.phoenix6.configs.CurrentLimitsConfigs;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.InvertedValue;
// import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.counter.UpDownCounter;

public class IntakeConstants {

     public static String bus = "rio";
    public static int IntakeMotorFX = 54;

    public static double intakegearRatio = 12.5;

     public static TalonFXConfiguration config = new TalonFXConfiguration()
        .withCurrentLimits(
            new CurrentLimitsConfigs()
            .withSupplyCurrentLimit(70)
        )
        .withMotorOutput(
            new MotorOutputConfigs()
            .withNeutralMode(NeutralModeValue.Brake)
            .withInverted(InvertedValue.Clockwise_Positive)
        )
        .withFeedback(
            new FeedbackConfigs()
            .withSensorToMechanismRatio(intakegearRatio)
        )
        
        .withSlot0(
            new Slot0Configs()
            .withKG(.82)
            .withKV(0.0)
            .withKA(0.0)
            .withKP(40.0)
            .withKI(0.0)
            .withKD(4.0)
            .withGravityType(GravityTypeValue.Arm_Cosine)
        );
   
}
