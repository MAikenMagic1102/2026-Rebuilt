package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.Distance;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import lombok.Builder;

/** Contains basic functions that are used often. */
public class Util {
    public static final double kEpsilon = 1e-12;

    /** Prevent this class from being instantiated. */
    private Util() {}

    public static boolean isRedSide() {
        var alliance = edu.wpi.first.wpilibj.DriverStation.getAlliance();
        if (alliance.isPresent()) {
            return alliance.get() == edu.wpi.first.wpilibj.DriverStation.Alliance.Red;
        }
        return false;
    }

    /** Clamps the given input to the given magnitude. */
    public static double clamp(double v, double maxMagnitude) {
        return clamp(v, -maxMagnitude, maxMagnitude);
    }

    /** Clamps the given input to the given range. */
    public static double clamp(double v, double min, double max) {
        return Math.min(max, Math.max(min, v));
    }

    /** Clamps the given input to the given range. */
    public static int clamp(int v, int min, int max) {
        return Math.min(max, Math.max(min, v));
    }

    public static boolean inRange(double v, double maxMagnitude) {
        return inRange(v, -maxMagnitude, maxMagnitude);
    }

    /** Checks if the given input is within the range (min, max), both exclusive. */
    public static boolean inRange(double v, double min, double max) {
        return v > min && v < max;
    }

    public static double interpolate(double a, double b, double x) {
        x = clamp(x, 0.0, 1.0);
        return a + (b - a) * x;
    }

    public static String joinStrings(final String delim, final List<?> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); ++i) {
            sb.append(strings.get(i).toString());
            if (i < strings.size() - 1) {
                sb.append(delim);
            }
        }
        return sb.toString();
    }

    public static boolean epsilonEquals(double a, double b, double epsilon) {
        return (a - epsilon <= b) && (a + epsilon >= b);
    }

    public static boolean epsilonEquals(double a, double b) {
        return epsilonEquals(a, b, kEpsilon);
    }

    public static boolean epsilonEquals(int a, int b, int epsilon) {
        return (a - epsilon <= b) && (a + epsilon >= b);
    }

    public static boolean allCloseTo(final List<Double> list, double value, double epsilon) {
        for (Double value_in : list) {
            if (!epsilonEquals(value_in, value, epsilon)) {
                return false;
            }
        }
        return true;
    }

    public static double handleDeadband(double value, double deadband) {
        deadband = Math.abs(deadband);
        if (deadband == 1) {
            return 0;
        }
        double scaledValue = (value + (value < 0 ? deadband : -deadband)) / (1 - deadband);
        return (Math.abs(value) > Math.abs(deadband)) ? scaledValue : 0;
    }

    public static <T> Supplier<T> memoizeByIteration(IntSupplier iteration, Supplier<T> delegate) {
        AtomicReference<T> value = new AtomicReference<>();
        AtomicInteger last_iteration = new AtomicInteger(-1);
        return () -> {
            int last = last_iteration.get();
            int now = iteration.getAsInt();
            if (last != now) {
                value.updateAndGet((cur) -> null);
            }
            T val = value.get();
            if (val == null) {
                val = value.updateAndGet(cur -> cur == null ? delegate.get() : cur);
                last_iteration.set(now);
            }
            return val;
        };
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static double rangeModulo(double input, double high, double low) {
        double range = high - low;
        if (input < low) {
            return input + (Math.ceil((low - input) / range)) * range;
        } else if (input > high) {
            return input - (Math.ceil((input - high) / range)) * range;
        }
        return input;
    }

    public static <T> Set<T> mergeSets(@SuppressWarnings("unchecked") Set<T>... sets) {
        Set<T> set = new HashSet<>();
        for (Set<T> s : sets) {
            set.addAll(s);
        }
        return set;
    }

    public static <T> Set<T> removeSets(Set<T> set, @SuppressWarnings("unchecked") Set<T>... sets) {
        for (Set<T> s : sets) {
            set.removeAll(s);
        }
        return set;
    }

    /**
     * Returns the middle of two rotations, but chooses the middle that is on the same side as the given side rotation.
     * @param a
     * @param b
     * @param side
     * @return
     */
    public static Rotation2d getMiddleRotationChoosingSide(Rotation2d a, Rotation2d b, Rotation2d side) {
        var middle = a.plus(b).times(0.5);
        if (middle.minus(side).getCos() > 0) {
            // middle and side are pointing in approximately the same direction, so we can return middle as is.
            return middle;
        } else {
            // middle and side are pointing in approximately opposite directions, so we need to flip middle around to
            // return the middle of a and b closer to side.
            return middle.plus(new Rotation2d(Math.PI));
        }
    }

    @Builder
    public record RobotRelativeTransform(
            Distance forwardPositive,
            Distance leftPositive,
            Distance upPositive,
            Angle rollPositive,
            Angle pitchPositive,
            Angle yawPositive) {
        public Transform3d getTransform() {
            return new Transform3d(
                    forwardPositive,
                    leftPositive,
                    upPositive,
                    new Rotation3d(rollPositive, pitchPositive, yawPositive));
        }
    }
}
