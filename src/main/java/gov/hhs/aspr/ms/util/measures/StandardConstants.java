package gov.hhs.aspr.ms.util.measures;

public final class StandardConstants {
    // acceleration
    public static Constant EARTH_GRAVITY = new Constant(new Quantity(StandardComposites.ACCELERATION_MPSS, 9.80665),
            "earth_gravity", "eg");

    private StandardConstants() {
    }
}
