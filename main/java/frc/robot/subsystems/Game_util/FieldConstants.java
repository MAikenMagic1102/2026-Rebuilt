// Copyright (c) 2025 FRC 6328
// http://github.com/Mechanical-Advantage
//
// Use of this source code is governed by an MIT-style
// license that can be found in the LICENSE file at
// the root directory of this project.

package frc.robot.game_util;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

/**
 * Contains various field dimensions and useful reference points. All units are in meters and poses
 * have a blue alliance origin.
 * All measurements from https://firstfrc.blob.core.windows.net/frc2026/FieldAssets/2026-field-dimension-dwgs.pdf unless otherwise noted.
 * All left/right perspectives except for the tower are from the alliance's driver station. The tower is from the field center looking toward the alliance wall.
 */
public class FieldConstants {
    public static final AprilTagFieldLayout field = AprilTagFieldLayout.loadField(AprilTagFields.k2026RebuiltWelded);
    public static final double fieldLengthMeters = field.getFieldLength();
    public static final double fieldWidthMeters = field.getFieldWidth();
    public static final Translation2d fieldCenter = new Translation2d(fieldLengthMeters / 2, fieldWidthMeters / 2);
    public static final double startingLineX =
            Units.inchesToMeters(156.61 + 2); // Measured from the inside of starting line

    // Hub dimensions and locations
    public static final double hubCenterToAllianceWall = Units.inchesToMeters(182.11);
    public static final double hubWidth = Units.inchesToMeters(47.0);
    public static final double hubHeight = Units.inchesToMeters(72.0);
    public static final double hubHexagonWidth = Units.inchesToMeters(41.73);

    public static class Hub {
        public static final Translation3d blueHubCenter =
                new Translation3d(hubCenterToAllianceWall, fieldWidthMeters / 2, hubHeight);
        public static final Translation3d redHubCenter = rotateAroundCenter(blueHubCenter);
        public static final Translation2d blueHubCenter2d = blueHubCenter.toTranslation2d();
        public static final Translation2d redHubCenter2d = redHubCenter.toTranslation2d();

        public static final Area blueHubBase = new Area(new Rectangle2D.Double(
                blueHubCenter2d.getX() - hubWidth / 2, blueHubCenter2d.getY() - hubWidth / 2, hubWidth, hubWidth));
        public static final Area redHubBase = rotateAroundCenter(blueHubBase);
    }

    // Bump dimensions and locations
    public static final double bumpToAllianceWall = Units.inchesToMeters(158.06);
    public static final double bumpLength = Units.inchesToMeters(47.0);
    public static final double bumpWidth = Units.inchesToMeters(73.0);

    public static class Bumps {
        public static final Area bumpBlueLeft =
                new Area(new Rectangle2D.Double( // uses the bottom left corner as the starting point
                        bumpToAllianceWall, fieldWidthMeters / 2 + hubWidth / 2, bumpLength, bumpWidth));
        public static final Area bumpBlueRight = mirrorAcrossFieldWidth(bumpBlueLeft);
        public static final Area bumpRedLeft = rotateAroundCenter(bumpBlueLeft);
        public static final Area bumpRedRight = rotateAroundCenter(bumpBlueRight);
    }

    // Extended bump for drive assist check
    public static final double extension = Units.inchesToMeters(24); // Arbitrary value, maybe change later

    public static class ExtendedBumps {
        public static final Area extendedBumpBlueLeft =
                new Area(new Rectangle2D.Double( // uses the bottom left corner as the starting point
                        bumpToAllianceWall - extension,
                        fieldWidthMeters / 2 + hubWidth / 2,
                        bumpLength + (2 * extension),
                        bumpWidth));
        public static final Area extendedBumpBlueRight = mirrorAcrossFieldWidth(extendedBumpBlueLeft);
        public static final Area extendedBumpRedLeft = rotateAroundCenter(extendedBumpBlueLeft);
        public static final Area extendedBumpRedRight = rotateAroundCenter(extendedBumpBlueRight);
    }

    // Outpost dimensions and locations
    public static final double outpostToFieldWall = Units.inchesToMeters(26.22);
    public static final double outpostWidth = Units.inchesToMeters(32.0);

    public static class Outpost {
        public static final Translation2d outpostCenterBlue = new Translation2d(0, outpostToFieldWall);
        public static final Translation2d outpostCenterRed = rotateAroundCenter(outpostCenterBlue);
    }

    // Tower dimensions and locations
    public static final double towerToFieldCenter = Units.inchesToMeters(-11.38);
    public static final double towerWidth = Units.inchesToMeters(47);
    public static final double towerToAllianceWall =
            Units.inchesToMeters(40 + 3.51); // 3.51 is the thickness of the tower uprights
    public static final double towerUprightCenterToCenter =
            Units.inchesToMeters(32.25 + 1.5); // 1.5 is the thickness of the tower uprights

    public static class Tower {
        public static final Translation2d towerBlue =
                new Translation2d(towerToAllianceWall, fieldWidthMeters / 2 + towerToFieldCenter);
        public static final Translation2d towerRed = rotateAroundCenter(towerBlue);

        public static final Translation2d
                towerBarBlueLeft = // left when facing the outpost from the center of the field
                towerBlue.minus(new Translation2d(0, towerUprightCenterToCenter / 2));
        public static final Translation2d
                towerBarBlueRight = // right when facing the outpost from the center of the field
                towerBlue.plus(new Translation2d(0, towerUprightCenterToCenter / 2));
        public static final Translation2d towerBarRedLeft = rotateAroundCenter(towerBarBlueLeft);
        public static final Translation2d towerBarRedRight = rotateAroundCenter(towerBarBlueRight);
    }

    // Depot dimensions and locations
    public static final double depotToFieldCenter = Units.inchesToMeters(75.93);
    public static final double depotWidth = Units.inchesToMeters(42);
    public static final double depotLength = Units.inchesToMeters(27);

    public static class Depot {
        public static final Area depotBlue = new Area(new Rectangle2D.Double(
                0, fieldWidthMeters / 2 + depotToFieldCenter - depotWidth / 2, depotLength, depotWidth));
        public static final Area depotRed = rotateAroundCenter(depotBlue);
    }

    // Scoring zone dimensions and locations
    public static final double scoringZoneLength = startingLineX;

    public static class ScoringZones {
        public static final Area scoringZoneBlue =
                new Area(new Rectangle2D.Double(0, 0, scoringZoneLength, fieldWidthMeters));
        public static final Area scoringZoneRed = rotateAroundCenter(scoringZoneBlue);
    }

    // Trench dimensions and locations
    public static final double trenchToAllianceWall = hubCenterToAllianceWall;
    public static final double trenchArmLength = Units.inchesToMeters(3.5); // inferred for now
    public static final double trenchArmWidth = Units.inchesToMeters(50.34); // from game manual 5.6
    public static final double trenchBaseLength = Units.inchesToMeters(47); // from game manual 5.6
    public static final double trenchBaseWidth = Units.inchesToMeters(65.65 - 50.34); // from game manual 5.6

    public static class Trench {
        public static final Area trenchArmRedRight = new Area(
                new Rectangle2D.Double(trenchToAllianceWall - trenchArmLength / 2, 0, trenchArmLength, trenchArmWidth));
        public static final Area trenchArmRedLeft = mirrorAcrossFieldWidth(trenchArmRedRight);
        public static final Area trenchArmBlueRight = rotateAroundCenter(trenchArmRedRight);
        public static final Area trenchArmBlueLeft = rotateAroundCenter(trenchArmRedLeft);
        public static final Area trenchBaseRedRight = new Area(new Rectangle2D.Double(
                trenchToAllianceWall - trenchBaseLength / 2, trenchArmWidth, trenchBaseLength, trenchBaseWidth));
        public static final Area trenchBaseRedLeft = mirrorAcrossFieldWidth(trenchBaseRedRight);
        public static final Area trenchBaseBlueRight = rotateAroundCenter(trenchBaseRedRight);
        public static final Area trenchBaseBlueLeft = rotateAroundCenter(trenchBaseRedLeft);
    }

    // from game manual 6.3.4.1
    private static final double stagedFuelLength = Units.inchesToMeters(72);
    private static final double stagedFuelWidth = Units.inchesToMeters(206);
    public static final Area stagedFuelArea = new Area(new Rectangle2D.Double(
            fieldCenter.getX() - stagedFuelLength / 2,
            fieldCenter.getY() - stagedFuelWidth / 2,
            stagedFuelLength,
            stagedFuelWidth));

    /** AdvantageKit-safe loggable version of `AprilTag` that contains data we want without lookups */
    public static record AprilTagStruct(int fiducialId, Pose3d pose) {}

    // All left/right are from the perspective of that alliance's driver station
    // Inner tags face the center of the field, outer face alliance walls
    // e.g., redTrenchRightInner is the tag on the trench, red alliance side
    // right side from red's perspective, facing the center of the field

    // Trench AprilTags
    public static final AprilTagStruct tagRedTrenchRightInner =
            new AprilTagStruct(1, field.getTagPose(1).get());
    public static final AprilTagStruct tagRedTrenchRightOuter =
            new AprilTagStruct(12, field.getTagPose(12).get());
    public static final AprilTagStruct tagRedTrenchLeftInner =
            new AprilTagStruct(6, field.getTagPose(6).get());
    public static final AprilTagStruct tagRedTrenchLeftOuter =
            new AprilTagStruct(7, field.getTagPose(7).get());
    public static final AprilTagStruct tagBlueTrenchRightInner =
            new AprilTagStruct(17, field.getTagPose(17).get());
    public static final AprilTagStruct tagBlueTrenchRightOuter =
            new AprilTagStruct(28, field.getTagPose(28).get());
    public static final AprilTagStruct tagBlueTrenchLeftInner =
            new AprilTagStruct(22, field.getTagPose(22).get());
    public static final AprilTagStruct tagBlueTrenchLeftOuter =
            new AprilTagStruct(23, field.getTagPose(23).get());

    // Hub AprilTags
    // The first part of the name is the hub face the tag is on,
    // the second is its position on the hub face
    // e.g., redHubInnerLeft is on the red hub, and is
    // the left tag on the side of the hub facing the center of the field
    public static final AprilTagStruct tagRedHubInnerLeft =
            new AprilTagStruct(4, field.getTagPose(4).get());
    public static final AprilTagStruct tagRedHubInnerRight =
            new AprilTagStruct(3, field.getTagPose(3).get());
    public static final AprilTagStruct tagRedHubRightInner =
            new AprilTagStruct(2, field.getTagPose(2).get());
    public static final AprilTagStruct tagRedHubRightOuter =
            new AprilTagStruct(11, field.getTagPose(11).get());
    public static final AprilTagStruct tagRedHubOuterRight =
            new AprilTagStruct(10, field.getTagPose(10).get());
    public static final AprilTagStruct tagRedHubOuterLeft =
            new AprilTagStruct(9, field.getTagPose(9).get());
    public static final AprilTagStruct tagRedHubLeftInner =
            new AprilTagStruct(5, field.getTagPose(5).get());
    public static final AprilTagStruct tagRedHubLeftOuter =
            new AprilTagStruct(8, field.getTagPose(8).get());
    public static final AprilTagStruct tagBlueHubInnerLeft =
            new AprilTagStruct(20, field.getTagPose(20).get());
    public static final AprilTagStruct tagBlueHubInnerRight =
            new AprilTagStruct(19, field.getTagPose(19).get());
    public static final AprilTagStruct tagBlueHubRightInner =
            new AprilTagStruct(18, field.getTagPose(18).get());
    public static final AprilTagStruct tagBlueHubRightOuter =
            new AprilTagStruct(27, field.getTagPose(27).get());
    public static final AprilTagStruct tagBlueHubOuterRight =
            new AprilTagStruct(26, field.getTagPose(26).get());
    public static final AprilTagStruct tagBlueHubOuterLeft =
            new AprilTagStruct(25, field.getTagPose(25).get());
    public static final AprilTagStruct tagBlueHubLeftInner =
            new AprilTagStruct(21, field.getTagPose(21).get());
    public static final AprilTagStruct tagBlueHubLeftOuter =
            new AprilTagStruct(24, field.getTagPose(24).get());

    // Tower AprilTags
    public static final AprilTagStruct tagRedTowerCentered =
            new AprilTagStruct(15, field.getTagPose(15).get());
    public static final AprilTagStruct tagRedTowerOffset =
            new AprilTagStruct(16, field.getTagPose(16).get());
    public static final AprilTagStruct tagBlueTowerCentered =
            new AprilTagStruct(31, field.getTagPose(31).get());
    public static final AprilTagStruct tagBlueTowerOffset =
            new AprilTagStruct(32, field.getTagPose(32).get());

    // Outpost AprilTags
    public static final AprilTagStruct tagRedOutpostCentered =
            new AprilTagStruct(13, field.getTagPose(13).get());
    public static final AprilTagStruct tagRedOutpostOffset =
            new AprilTagStruct(14, field.getTagPose(14).get());
    public static final AprilTagStruct tagBlueOutpostCentered =
            new AprilTagStruct(29, field.getTagPose(29).get());
    public static final AprilTagStruct tagBlueOutpostOffset =
            new AprilTagStruct(30, field.getTagPose(30).get());

    public static final double fuelDiameterMeters = 0.15;

    // Geometry helper functions
    public static Translation2d rotateAroundCenter(Translation2d point) {
        return point.rotateAround(fieldCenter, Rotation2d.k180deg);
    }

    public static Translation3d rotateAroundCenter(Translation3d point) {
        Translation2d rotated2d = rotateAroundCenter(new Translation2d(point.getX(), point.getY()));
        return new Translation3d(rotated2d.getX(), rotated2d.getY(), point.getZ());
    }

    public static Translation2d mirrorAcrossFieldWidth(Translation2d point) {
        return new Translation2d(point.getX(), fieldWidthMeters - point.getY());
    }

    public static Translation2d mirrorAcrossFieldLength(Translation2d point) {
        return new Translation2d(fieldLengthMeters - point.getX(), point.getY());
    }

    public static Area rotateAroundCenter(Area area) {
        // Approximate by rotating bounding box
        Rectangle2D bounds = area.getBounds2D();
        Translation2d bottomLeft = new Translation2d(bounds.getX(), bounds.getY());
        Translation2d topRight =
                new Translation2d(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
        Translation2d newBottomLeft = rotateAroundCenter(topRight);
        Translation2d newTopRight = rotateAroundCenter(bottomLeft);
        return new Area(new Rectangle2D.Double(
                newBottomLeft.getX(),
                newBottomLeft.getY(),
                newTopRight.getX() - newBottomLeft.getX(),
                newTopRight.getY() - newBottomLeft.getY()));
    }

    public static Area mirrorAcrossFieldWidth(Area area) {
        // Approximate by mirroring bounding box
        Rectangle2D bounds = area.getBounds2D();
        Translation2d topLeft = new Translation2d(bounds.getX(), bounds.getY() + bounds.getHeight());
        Translation2d bottomRight = new Translation2d(bounds.getX() + bounds.getWidth(), bounds.getY());
        Translation2d newBottomLeft = mirrorAcrossFieldWidth(topLeft);
        Translation2d newTopRight = mirrorAcrossFieldWidth(bottomRight);
        return new Area(new Rectangle2D.Double(
                newBottomLeft.getX(),
                newBottomLeft.getY(),
                newTopRight.getX() - newBottomLeft.getX(),
                newTopRight.getY() - newBottomLeft.getY()));
    }

    public static Area mirrorAcrossFieldLength(Area area) {
        // Approximate by mirroring bounding box
        Rectangle2D bounds = area.getBounds2D();
        Translation2d bottomRight = new Translation2d(bounds.getX() + bounds.getWidth(), bounds.getY());
        Translation2d topLeft = new Translation2d(bounds.getX(), bounds.getY() + bounds.getHeight());
        Translation2d newBottomLeft = mirrorAcrossFieldLength(bottomRight);
        Translation2d newTopRight = mirrorAcrossFieldLength(topLeft);
        return new Area(new Rectangle2D.Double(
                newBottomLeft.getX(),
                newBottomLeft.getY(),
                newTopRight.getX() - newBottomLeft.getX(),
                newTopRight.getY() - newBottomLeft.getY()));
    }
}