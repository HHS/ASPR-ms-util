package gov.hhs.aspr.ms.util.measures;

import org.apache.commons.math3.util.FastMath;

/**
 * A collection of standard measures, base units, composed units and constants
 */
public final class StandardMeasures {

	public static Measure LENGTH = new Measure("length");
	public static Measure MASS = new Measure("mass");
	public static Measure TIME = new Measure("time");
	public static Measure CURRENT = new Measure("current");
	public static Measure TEMPERATURE = new Measure("temperature");
	public static Measure LUMINOSITY = new Measure("luminosity");
	public static Measure ANGLE = new Measure("angle");
	public static Measure SOLID_ANGLE = new Measure("solid_angle");
	

	// Length
	public static BaseUnit METER = new BaseUnit(LENGTH, "meter", "m");
	public static BaseUnit CM = new BaseUnit(METER, 0.01, "centimeter", "cm");
	public static BaseUnit DM = new BaseUnit(CM, 10, "decimeter", "dm");
	public static BaseUnit INCH = new BaseUnit(CM, 2.54, "inch", "in");
	public static BaseUnit FOOT = new BaseUnit(INCH, 12, "foot", "ft");
	public static BaseUnit MILE = new BaseUnit(FOOT, 5280, "mile", "mi");

	// Time
	public static BaseUnit SECOND = new BaseUnit(TIME, "second", "s");
	public static BaseUnit MINUTE = new BaseUnit(SECOND, 60, "minute", "min");
	public static BaseUnit HOUR = new BaseUnit(MINUTE, 60, "hour", "h");
	public static BaseUnit DAY = new BaseUnit(HOUR, 24, "day", "d");

	// Mass
	public static BaseUnit KILOGRAM = new BaseUnit(MASS, "kilogram", "kg");
	public static BaseUnit GRAM = new BaseUnit(KILOGRAM, 0.001, "gram", "g");
	public static BaseUnit MILLIGRAM = new BaseUnit(GRAM, 0.001, "milligram", "mg");
	public static BaseUnit MICROGRAM = new BaseUnit(MILLIGRAM, 0.001, "microgram", "mcg");
	
	//Current
	public static BaseUnit AMPERE = new BaseUnit(CURRENT, "ampere", "A");
	
	//TemperatureScale
	public static BaseUnit KELVIN = new BaseUnit(TEMPERATURE, "kelvin", "K");
	
	//Luminosity
	public static BaseUnit CANDELA = new BaseUnit(LUMINOSITY, "candela", "cd");
	
	//Angle	
	public static BaseUnit RADIAN = new BaseUnit(ANGLE, "raidan", "rad");
	public static BaseUnit DEGREE = new BaseUnit(RADIAN, FastMath.PI/180,"degree", "deg");
	
	//Solid Angle
	public static BaseUnit STERADIAN = new BaseUnit(SOLID_ANGLE, "steradian", "st");
	
	//speed
	public static ComposedUnit MPH = ComposedUnit.builder().setBaseUnit(MILE, 1).setBaseUnit(HOUR, -1).build();
	public static ComposedUnit MPS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -1).build();
	
	//acceleration
	public static ComposedUnit ACCELERATION_MPSS = ComposedUnit.builder().setBaseUnit(METER, 1).setBaseUnit(SECOND, -2).build();
	public static Constant EARTH_GRAVITY = new Constant(new Quantity(ACCELERATION_MPSS, 9.80665), "earth_gravity", "eg");

	//Liquid volume
	public static ComposedUnit ML = ComposedUnit.builder().setBaseUnit(CM, 3).setShortName("ml").setLongName("milliliter")
			.build();
	
	public static ComposedUnit LITER = ComposedUnit.builder().setBaseUnit(DM, 3).setShortName("L").setLongName("liter")
			.build();
	
	//common scalar values
	
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

	private StandardMeasures() {}
	
	

}
