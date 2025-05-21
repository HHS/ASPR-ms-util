package gov.hhs.aspr.ms.util.measures;

import org.apache.commons.math3.util.FastMath;

public final class StandardUnits {
    // Length
    public final static Unit METER = new Unit(StandardUnitTypes.LENGTH, "meter", "m");
    public final static Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
    public final static Unit DM = new Unit(CM, 10, "decimeter", "dm");
    public final static Unit INCH = new Unit(CM, 2.54, "inch", "in");
    public final static Unit FOOT = new Unit(INCH, 12, "foot", "ft");
    public final static Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

    // Time
    public final static Unit SECOND = new Unit(StandardUnitTypes.TIME, "second", "s");
    public final static Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
    public final static Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
    public final static Unit DAY = new Unit(HOUR, 24, "day", "d");

    // Mass
    public final static Unit KILOGRAM = new Unit(StandardUnitTypes.MASS, "kilogram", "kg");
    public final static Unit GRAM = new Unit(KILOGRAM, 0.001, "gram", "g");
    public final static Unit MILLIGRAM = new Unit(GRAM, 0.001, "milligram", "mg");
    public final static Unit MICROGRAM = new Unit(MILLIGRAM, 0.001, "microgram", "mcg");

    // Current
    public final static Unit AMPERE = new Unit(StandardUnitTypes.CURRENT, "ampere", "A");

    // TemperatureScale
    public final static Unit KELVIN = new Unit(StandardUnitTypes.TEMPERATURE, "kelvin", "K");

    // Luminosity
    public final static Unit CANDELA = new Unit(StandardUnitTypes.LUMINOSITY, "candela", "cd");

    // Angle
    public final static Unit RADIAN = new Unit(StandardUnitTypes.ANGLE, "raidan", "rad");
    public final static Unit DEGREE = new Unit(RADIAN, FastMath.PI / 180, "degree", "deg");

    // Solid Angle
    public final static Unit STERADIAN = new Unit(StandardUnitTypes.SOLID_ANGLE, "steradian", "st");

    private StandardUnits() {
    }
}
