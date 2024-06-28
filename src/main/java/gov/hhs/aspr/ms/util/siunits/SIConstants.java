package gov.hhs.aspr.ms.util.siunits;

public class SIConstants {

	// base
	public static SIConstant METER = new SIConstant("meter", "m",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.LENGTH, 1).build());//
	
	public static SIConstant KILOGRAM = new SIConstant("kilogram", "kg",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.MASS, 1).build());//

	public static SIConstant SECOND = new SIConstant("second", "s",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.TIME, 1).build());//

	public static SIConstant AMPERE = new SIConstant("ampere", "A",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.ELECTRIC_CURRENT, 1).build());//

	public static SIConstant KELVIN = new SIConstant("kelvin", "K",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.TEMPERATURE, 1).build());//

	public static SIConstant MOLE = new SIConstant("mole", "mol",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.SUBSTANCE_AMOUNT, 1).build());//

	public static SIConstant CANDELA = new SIConstant("candela", "cd",
			SIQuantity.builder().setScalar(1).setDimension(SIBaseUnit.LUMINOUS_INTENSITY, 1).build());//


//	// derived units from 8th edition of the SI Brochure
//	HERTZ(1, "hertz", "Hz", 0, 0, -1, 0, 0, 0, 0), //
//	NEWTON(1, "newton", "N", 1, 1, -2, 0, 0, 0, 0), //
//	PASCAL(1, "pascal", "Pa", -1, 1, -2, 0, 0, 0, 0), //
//	JOULE(1, "joule", "J", 2, 1, -2, 0, 0, 0, 0), //
//	WATT(1, "watt", "W", 2, 1, -3, 0, 0, 0, 0), //
//	COULOMB(1, "coulomb", "C", 0, 0, 1, 1, 0, 0, 0), //
//	VOLT(1, "volt", "V", 2, 1, -3, -1, 0, 0, 0), //
//	FARAD(1, "farad", "F", -2, -1, 4, 2, 0, 0, 0), //
//	OHM(1, "ohm", "\u2126", 2, 1, -3, -2, 0, 0, 0), //
//	SIEMENS(1, "siemens", "S", -2, -1, 3, 2, 0, 0, 0), //
//	WEBER(1, "weber", "Wb", 2, 1, -2, -1, 0, 0, 0), //
//	TESLA(1, "tesla", "T", 0, 1, -2, -1, 0, 0, 0), //
//	HENRY(1, "henry", "H", 2, 1, -2, -2, 0, 0, 0), //
//	LUMEN(1, "lumen", "lm", 0, 0, 0, 0, 0, 0, 1), //
//	LUX(1, "lux", "lx", -2, 0, 0, 0, 0, 0, 1), //
//	BECQUEREL(1, "becquerel", "Bq", 0, 0, -1, 0, 0, 0, 0), //
//	GRAY(1, "gray", "Gy", 2, 0, -2, 0, 0, 0, 0), //
//	SIEVERT(1, "sievert", "Sv", 2, 0, -2, 0, 0, 0, 0), //
//	KATAL(1, "katal", "kat", 0, 0, -1, 0, 0, 1, 0), //
//
//	ANGSTROM(1e-10, "angstrom", "\u212B", 1, 0, 0, 0, 0, 0, 0), //
//	ASTRONOMICAL_UNIT(149597870700.0, "astronomical unit", "AU", 1, 0, 0, 0, 0, 0, 0), //
//	BOHR(5.2917720859E-11, "bohr", "a0", 1, 0, 0, 0, 0, 0, 0), //
//	FERMI(1E-15, "fermi", "fm", 1, 0, 0, 0, 0, 0, 0), //
//
//	YOCTO(1E-24, "yocto", "yocto-", 0, 0, 0, 0, 0, 0, 0), //
//	ZEPTO(1E-21, "zepto", "zepto-", 0, 0, 0, 0, 0, 0, 0), //
//	ATTO(1E-18, "atto", "atto-", 0, 0, 0, 0, 0, 0, 0), //
//	FEMTO(1E-15, "femto", "femto-", 0, 0, 0, 0, 0, 0, 0), //
//	PICO(1E-12, "pico", "pico-", 0, 0, 0, 0, 0, 0, 0), //
//	NANO(1E-9, "nano", "nano-", 0, 0, 0, 0, 0, 0, 0), //
//	MICRO(1E-6, "micro", "micro-", 0, 0, 0, 0, 0, 0, 0), //
//	MILLI(1E-3, "milli", "milli-", 0, 0, 0, 0, 0, 0, 0), //
//	CENTI(1E-2, "centi", "centi-", 0, 0, 0, 0, 0, 0, 0), //
//	DECI(1E-1, "deci", "deci-", 0, 0, 0, 0, 0, 0, 0), //
//	DECA(1E+1, "deca", "deca-", 0, 0, 0, 0, 0, 0, 0), //
//	HECTO(1E+2, "", "hecto-", 0, 0, 0, 0, 0, 0, 0), //
//	KILO(1E+3, "hecto", "kilo-", 0, 0, 0, 0, 0, 0, 0), //
//	MEGA(1E+6, "mega", "mega-", 0, 0, 0, 0, 0, 0, 0), //
//	GIGA(1E+9, "giga", "giga-", 0, 0, 0, 0, 0, 0, 0), //
//	TERA(1E+12, "tera", "tera-", 0, 0, 0, 0, 0, 0, 0), //
//	PETA(1E+15, "peta", "peta-", 0, 0, 0, 0, 0, 0, 0), //
//	EXA(1E+18, "exa", "exa-", 0, 0, 0, 0, 0, 0, 0), //
//	ZETTA(1E+21, "zetta", "zetta-", 0, 0, 0, 0, 0, 0, 0), //
//	YOTTA(1E+24, "yotta", "yotta-", 0, 0, 0, 0, 0, 0, 0),//

//	private SIQuantity siQuantity;
//
//	private String name;
//
//	public String getName() {
//		return name;
//	}
//
//	private String symbol;
//
//	public String getSymbol() {
//		return symbol;
//	}

//	private SIConstants(double scalar, String name, String symbol, int metreDim, int kilogramDim, int secondDim,
//			int ampereDim, int kelvinDim, int moleDim, int candelaDim) {
//		siQuantity = SIQuantity.builder()
//		.setScalar(scalar)
//		.setDimension(SIBaseUnit.METRE, metreDim)
//		.setDimension(SIBaseUnit.KILOGRAM, kilogramDim)
//		.setDimension(SIBaseUnit.SECOND, secondDim)
//		.setDimension(SIBaseUnit.AMPERE, ampereDim)
//		.setDimension(SIBaseUnit.KELVIN, kelvinDim)
//		.setDimension(SIBaseUnit.MOLE, moleDim)
//		.setDimension(SIBaseUnit.CANDELA, candelaDim)
//		.build();
//		this.name = name;
//		this.symbol = symbol;
//	}

//	public SIQuantity getSIQuantity(double scalar) {
//		return siQuantity.scalarMultiply(scalar);
//	}

	public static SIConstant fromSIBaseUnit(SIBaseUnit siBaseUnit) {
		switch (siBaseUnit) {
		case ELECTRIC_CURRENT:
			return AMPERE;
		case LUMINOUS_INTENSITY:
			return CANDELA;
		case TEMPERATURE:
			return KELVIN;
		case MASS:
			return KILOGRAM;
		case LENGTH:
			return METER;
		case SUBSTANCE_AMOUNT:
			return MOLE;
		case TIME:
			return SECOND;
		default:
			throw new RuntimeException("unknown SI Base Unit");
		}
	}

}
