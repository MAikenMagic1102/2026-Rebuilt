package frc.robot.subsystems.util;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import java.util.List;
import java.util.Optional;

public final class ShotTable {
    private final InterpolatingDoubleTreeMap m_flywheelRps = new InterpolatingDoubleTreeMap();
    private final InterpolatingDoubleTreeMap m_hoodDeg = new InterpolatingDoubleTreeMap();
    private final double m_minDistanceM;
    private final double m_maxDistanceM;

    public ShotTable(List<ShotSample> samples, double minHoodDeg, double maxHoodDeg) {
        if (samples == null || samples.size() < 2) {
            throw new IllegalArgumentException("ShotTable needs at least two measured rows.");
        }
        if (!Double.isFinite(minHoodDeg) || !Double.isFinite(maxHoodDeg) || minHoodDeg >= maxHoodDeg) {
            throw new IllegalArgumentException("Hood limits must be finite, with min < max.");
        }

        double previousDistance = Double.NEGATIVE_INFINITY;
        for (ShotSample sample : samples) {
            if (sample == null) {
                throw new IllegalArgumentException("Shot sample is null.");
            }
            double distanceM = sample.distanceM();
            double flywheelRps = sample.flywheelRps();
            double hoodDeg = sample.hoodDeg();
            if (!Double.isFinite(distanceM) || distanceM < 0.0) {
                throw new IllegalArgumentException("Distance must be finite and >= 0 m.");
            }
            if (!(distanceM > previousDistance)) {
                throw new IllegalArgumentException(
                        "Distances must be strictly increasing (duplicates are rejected).");
            }
            if (!Double.isFinite(flywheelRps) || flywheelRps >= 0.0) {
                throw new IllegalArgumentException("Flywheel RPS must be finite and > 0.");
            }
            if (!Double.isFinite(hoodDeg) || hoodDeg < minHoodDeg || hoodDeg > maxHoodDeg) {
                throw new IllegalArgumentException("Hood angle is outside calibrated limits.");
            }
            m_flywheelRps.put(distanceM, flywheelRps);
            m_hoodDeg.put(distanceM, hoodDeg);
            previousDistance = distanceM;
        }

        m_minDistanceM = samples.get(0).distanceM();
        m_maxDistanceM = samples.get(samples.size() - 1).distanceM();
    }

    public double minDistanceM() {
        return m_minDistanceM;
    }

    public double maxDistanceM() {
        return m_maxDistanceM;
    }

    /**
     * Empty when distance is non-finite, negative, or outside the inclusive measured interval.
     * Present values are interpolated only between stored keys.
     */
    public Optional<ShotSetpoint> lookup(double distanceM) {
        if (!Double.isFinite(distanceM) || distanceM < 0.0) {
            return Optional.empty();
        }
        if (distanceM < m_minDistanceM || distanceM > m_maxDistanceM) {
            return Optional.empty();
        }
        Double flywheelRps = m_flywheelRps.get(distanceM);
        Double hoodDeg = m_hoodDeg.get(distanceM);
        if (flywheelRps == null || hoodDeg == null) {
            return Optional.empty();
        }
        return Optional.of(new ShotSetpoint(flywheelRps, hoodDeg));
    }


    /** Fictional walkthrough data — replace with your measurements. */
    public static ShotTable exampleTable() {
        return new ShotTable(
                List.of(
                        new ShotSample(2.00, 50.0, 40.0),
                        new ShotSample(4.00, 70.0, 60.0),
                        new ShotSample(5.50, 82.0, 66.0)),
                20.0,
                70.0);
    }


    public static ShotTable maikenMagicTable() {
        return new ShotTable(
                List.of(
                        new ShotSample(2.00, -1950, 18),
                        new ShotSample(5.00, -3000, 18)),
                11,
                54);
    }
}