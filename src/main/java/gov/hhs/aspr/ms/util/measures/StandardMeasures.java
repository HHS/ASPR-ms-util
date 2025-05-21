package gov.hhs.aspr.ms.util.measures;

import org.apache.commons.math3.util.FastMath;

/**
 * A collection of standard UnitTypes, Units, ComposedUnits and Constants
 */
public final class StandardMeasures {

	public static UnitType LENGTH = new UnitType("length");
	public static UnitType MASS = new UnitType("mass");
	public static UnitType TIME = new UnitType("time");
	public static UnitType CURRENT = new UnitType("current");
	public static UnitType TEMPERATURE = new UnitType("temperature");
	public static UnitType LUMINOSITY = new UnitType("luminosity");
	public static UnitType ANGLE = new UnitType("angle");
	public static UnitType SOLID_ANGLE = new UnitType("solid_angle");

	// Length
	public static Unit METER = new Unit(LENGTH, "meter", "m");
	public static Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
	public static Unit DM = new Unit(CM, 10, "decimeter", "dm");
	public static Unit INCH = new Unit(CM, 2.54, "inch", "in");
	public static Unit FOOT = new Unit(INCH, 12, "foot", "ft");
	public static Unit MILE = new Unit(FOOT, 5280, "mile", "mi");

	// Time
	public static Unit SECOND = new Unit(TIME, "second", "s");
	public static Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
	public static Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
	public static Unit DAY = new Unit(HOUR, 24, "day", "d");

	// Mass
	public static Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
	public static Unit GRAM = new Unit(KILOGRAM, 0.001, "gram", "g");
	public static Unit MILLIGRAM = new Unit(GRAM, 0.001, "milligram", "mg");
	public static Unit MICROGRAM = new Unit(MILLIGRAM, 0.001, "microgram", "mcg");

	// Current
	public static Unit AMPERE = new Unit(CURRENT, "ampere", "A");

	// TemperatureScale
	public static Unit KELVIN = new Unit(TEMPERATURE, "kelvin", "K");

	// Luminosity
	public static Unit CANDELA = new Unit(LUMINOSITY, "candela", "cd");

	// Angle
	public static Unit RADIAN = new Unit(ANGLE, "raidan", "rad");
	public static Unit DEGREE = new Unit(RADIAN, FastMath.PI / 180, "degree", "deg");

	// Solid Angle
	public static Unit STERADIAN = new Unit(SOLID_ANGLE, "steradian", "st");

	// speed
	public static ComposedUnit MPH = ComposedUnit.builder().setUnit(MILE, 1).setUnit(HOUR, -1).build();
	public static ComposedUnit MPS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -1).build();

	// acceleration
	public static ComposedUnit ACCELERATION_MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
	public static Constant EARTH_GRAVITY = new Constant(new Quantity(ACCELERATION_MPSS, 9.80665), "earth_gravity",
			"eg");

	// Liquid volume
	public static ComposedUnit ML = ComposedUnit.builder().setUnit(CM, 3).setShortName("ml").setLongName("milliliter")
			.build();

	public static ComposedUnit LITER = ComposedUnit.builder().setUnit(DM, 3).setShortName("L").setLongName("liter")
			.build();

	// common scalar values

	public final static double QUECTO = 1E-30;
	public final static double RONTO = 1E-27;
	public final static double YOCTO = 1E-24;
	public final static double ZEPTO = 1E-21;
	public final static double ATTO = 1E-18;
	public final static double FEMTO = 1E-15;
	public final static double PICO = 1E-12;
	public final static double NANO = 1E-9;
	public final static double MICRO = 1E-6;
	public final static double MILLI = 1E-3;
	public final static double CENTI = 1E-2;
	public final static double DECI = 1E-1;
	public final static double DECA = 1E1;
	public final static double HECTO = 1E2;
	public final static double KILO = 1E3;
	public final static double MEGA = 1E6;
	public final static double GIGA = 1E9;
	public final static double TERA = 1E12;
	public final static double PETA = 1E15;
	public final static double EXA = 1E18;
	public final static double ZETTA = 1E21;
	public final static double YOTTA = 1E24;
	public final static double RONNA = 1E27;
	public final static double QUETTA = 1E30;

	private StandardMeasures() {
	}
}
