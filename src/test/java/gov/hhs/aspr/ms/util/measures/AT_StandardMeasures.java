package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardMeasures {
	@Test
	@UnitTestField(target = StandardMeasures.class, name = "LENGTH")
	public void test_LENGTH() {
		assertInstanceOf(Measure.class, StandardMeasures.LENGTH);
		assertEquals("length", StandardMeasures.LENGTH.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MASS")
	public void test_MASS() {
		assertInstanceOf(Measure.class, StandardMeasures.MASS);
		assertEquals("mass", StandardMeasures.MASS.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "TIME")
	public void test_TIME() {
		assertInstanceOf(Measure.class, StandardMeasures.TIME);
		assertEquals("time", StandardMeasures.TIME.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "CURRENT")
	public void test_CURRENT() {
		assertInstanceOf(Measure.class, StandardMeasures.CURRENT);
		assertEquals("current", StandardMeasures.CURRENT.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "TEMPERATURE")
	public void test_TEMPERATURE() {
		assertInstanceOf(Measure.class, StandardMeasures.TEMPERATURE);
		assertEquals("temperature", StandardMeasures.TEMPERATURE.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "LUMINOSITY")
	public void test_LUMINOSITY() {
		assertInstanceOf(Measure.class, StandardMeasures.LUMINOSITY);
		assertEquals("luminosity", StandardMeasures.LUMINOSITY.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "ANGLE")
	public void test_ANGLE() {
		assertInstanceOf(Measure.class, StandardMeasures.ANGLE);
		assertEquals("angle", StandardMeasures.ANGLE.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "SOLID_ANGLE")
	public void test_SOLID_ANGLE() {
		assertInstanceOf(Measure.class, StandardMeasures.SOLID_ANGLE);
		assertEquals("solid_angle", StandardMeasures.SOLID_ANGLE.getName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "METER")
	public void test_METER() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.METER);
		assertEquals("meter", StandardMeasures.METER.getLongName());
		assertEquals("m", StandardMeasures.METER.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "CM")
	public void test_CM() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.CM);
		assertEquals("centimeter", StandardMeasures.CM.getLongName());
		assertEquals("cm", StandardMeasures.CM.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "DM")
	public void test_DM() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.DM);
		assertEquals("decimeter", StandardMeasures.DM.getLongName());
		assertEquals("dm", StandardMeasures.DM.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "INCH")
	public void test_INCH() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.INCH);
		assertEquals("inch", StandardMeasures.INCH.getLongName());
		assertEquals("in", StandardMeasures.INCH.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "FOOT")
	public void test_FOOT() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.FOOT);
		assertEquals("foot", StandardMeasures.FOOT.getLongName());
		assertEquals("ft", StandardMeasures.FOOT.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MILE")
	public void test_MILE() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.MILE);
		assertEquals("mile", StandardMeasures.MILE.getLongName());
		assertEquals("mi", StandardMeasures.MILE.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "SECOND")
	public void test_SECOND() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.SECOND);
		assertEquals("second", StandardMeasures.SECOND.getLongName());
		assertEquals("s", StandardMeasures.SECOND.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MINUTE")
	public void test_MINUTE() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.MINUTE);
		assertEquals("minute", StandardMeasures.MINUTE.getLongName());
		assertEquals("min", StandardMeasures.MINUTE.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "HOUR")
	public void test_HOUR() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.HOUR);
		assertEquals("hour", StandardMeasures.HOUR.getLongName());
		assertEquals("h", StandardMeasures.HOUR.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "DAY")
	public void test_DAY() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.DAY);
		assertEquals("day", StandardMeasures.DAY.getLongName());
		assertEquals("d", StandardMeasures.DAY.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "KILOGRAM")
	public void test_KILOGRAM() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.KILOGRAM);
		assertEquals("kilogram", StandardMeasures.KILOGRAM.getLongName());
		assertEquals("kg", StandardMeasures.KILOGRAM.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "GRAM")
	public void test_GRAM() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.GRAM);
		assertEquals("gram", StandardMeasures.GRAM.getLongName());
		assertEquals("g", StandardMeasures.GRAM.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MILLIGRAM")
	public void test_MILLIGRAM() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.MILLIGRAM);
		assertEquals("milligram", StandardMeasures.MILLIGRAM.getLongName());
		assertEquals("mg", StandardMeasures.MILLIGRAM.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MICROGRAM")
	public void test_MICROGRAM() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.MICROGRAM);
		assertEquals("microgram", StandardMeasures.MICROGRAM.getLongName());
		assertEquals("mcg", StandardMeasures.MICROGRAM.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "AMPERE")
	public void test_AMPERE() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.AMPERE);
		assertEquals("ampere", StandardMeasures.AMPERE.getLongName());
		assertEquals("A", StandardMeasures.AMPERE.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "KELVIN")
	public void test_KELVIN() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.KELVIN);
		assertEquals("kelvin", StandardMeasures.KELVIN.getLongName());
		assertEquals("K", StandardMeasures.KELVIN.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "CANDELA")
	public void test_CANDELA() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.CANDELA);
		assertEquals("candela", StandardMeasures.CANDELA.getLongName());
		assertEquals("cd", StandardMeasures.CANDELA.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "RADIAN")
	public void test_RADIAN() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.RADIAN);
		assertEquals("raidan", StandardMeasures.RADIAN.getLongName());
		assertEquals("rad", StandardMeasures.RADIAN.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "DEGREE")
	public void test_DEGREE() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.DEGREE);
		assertEquals("degree", StandardMeasures.DEGREE.getLongName());
		assertEquals("deg", StandardMeasures.DEGREE.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "STERADIAN")
	public void test_STERADIAN() {
		assertInstanceOf(BaseUnit.class, StandardMeasures.STERADIAN);
		assertEquals("steradian", StandardMeasures.STERADIAN.getLongName());
		assertEquals("st", StandardMeasures.STERADIAN.getShortName());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MPH")
	public void test_MPH() {
		assertInstanceOf(ComposedUnit.class, StandardMeasures.MPH);
		assertEquals(2, StandardMeasures.MPH.getBaseUnits().size());
		// mile
		Optional<BaseUnit> optionalUnit = StandardMeasures.MPH.getBaseUnit(StandardMeasures.MILE.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.MILE, optionalUnit.get());
		Optional<Integer> optionalPower = StandardMeasures.MPH.getPower(StandardMeasures.MILE.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(1, optionalPower.get());

		// hour
		optionalUnit = StandardMeasures.MPH.getBaseUnit(StandardMeasures.HOUR.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.HOUR, optionalUnit.get());
		optionalPower = StandardMeasures.MPH.getPower(StandardMeasures.HOUR.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(-1, optionalPower.get());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MPS")
	public void test_MPS() {
		assertInstanceOf(ComposedUnit.class, StandardMeasures.MPS);
		assertEquals(2, StandardMeasures.MPS.getBaseUnits().size());
		// meter
		Optional<BaseUnit> optionalUnit = StandardMeasures.MPS.getBaseUnit(StandardMeasures.METER.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.METER, optionalUnit.get());
		Optional<Integer> optionalPower = StandardMeasures.MPS.getPower(StandardMeasures.METER.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(1, optionalPower.get());

		// second
		optionalUnit = StandardMeasures.MPS.getBaseUnit(StandardMeasures.SECOND.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.SECOND, optionalUnit.get());
		optionalPower = StandardMeasures.MPS.getPower(StandardMeasures.SECOND.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(-1, optionalPower.get());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "ACCELERATION_MPSS")
	public void test_ACCELERATION_MPSS() {
		assertInstanceOf(ComposedUnit.class, StandardMeasures.ACCELERATION_MPSS);
		assertEquals(2, StandardMeasures.ACCELERATION_MPSS.getBaseUnits().size());
		// meter
		Optional<BaseUnit> optionalUnit = StandardMeasures.ACCELERATION_MPSS
				.getBaseUnit(StandardMeasures.METER.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.METER, optionalUnit.get());
		Optional<Integer> optionalPower = StandardMeasures.ACCELERATION_MPSS
				.getPower(StandardMeasures.METER.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(1, optionalPower.get());

		// second
		optionalUnit = StandardMeasures.ACCELERATION_MPSS.getBaseUnit(StandardMeasures.SECOND.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.SECOND, optionalUnit.get());
		optionalPower = StandardMeasures.ACCELERATION_MPSS.getPower(StandardMeasures.SECOND.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(-2, optionalPower.get());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "EARTH_GRAVITY")
	public void test_EARTH_GRAVITY() {
		assertInstanceOf(Constant.class, StandardMeasures.EARTH_GRAVITY);
		Quantity quantity = StandardMeasures.EARTH_GRAVITY.getQuantity();
		ComposedUnit composedUnit = quantity.getComposedUnit();

		assertEquals(9.80665, quantity.getValue());

		assertEquals(2, composedUnit.getBaseUnits().size());

		// meter
		Optional<BaseUnit> optionalUnit = composedUnit.getBaseUnit(StandardMeasures.METER.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.METER, optionalUnit.get());
		Optional<Integer> optionalPower = StandardMeasures.ACCELERATION_MPSS
				.getPower(StandardMeasures.METER.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(1, optionalPower.get());

		// second
		optionalUnit = composedUnit.getBaseUnit(StandardMeasures.SECOND.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.SECOND, optionalUnit.get());
		optionalPower = StandardMeasures.ACCELERATION_MPSS.getPower(StandardMeasures.SECOND.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(-2, optionalPower.get());
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "ML")
	public void test_ML() {
		assertInstanceOf(ComposedUnit.class, StandardMeasures.ML);
		assertEquals(1, StandardMeasures.ML.getBaseUnits().size());
		// centimeter
		Optional<BaseUnit> optionalUnit = StandardMeasures.ML.getBaseUnit(StandardMeasures.CM.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.CM, optionalUnit.get());
		Optional<Integer> optionalPower = StandardMeasures.ML.getPower(StandardMeasures.CM.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(3, optionalPower.get());

	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "LITER")
	public void test_LITER() {
		assertInstanceOf(ComposedUnit.class, StandardMeasures.LITER);
		assertEquals(1, StandardMeasures.LITER.getBaseUnits().size());
		// decimeter
		Optional<BaseUnit> optionalUnit = StandardMeasures.LITER.getBaseUnit(StandardMeasures.DM.getMeasure());
		assertTrue(optionalUnit.isPresent());
		assertEquals(StandardMeasures.DM, optionalUnit.get());
		Optional<Integer> optionalPower = StandardMeasures.LITER.getPower(StandardMeasures.DM.getMeasure());
		assertTrue(optionalPower.isPresent());
		assertEquals(3, optionalPower.get());

	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "QUECTO")
	public void test_QUECTO() {
		assertEquals(1E-30, StandardMeasures.QUECTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "RONTO")
	public void test_RONTO() {
		assertEquals(1E-27, StandardMeasures.RONTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "YOCTO")
	public void test_YOCTO() {
		assertEquals(1E-24, StandardMeasures.YOCTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "ZEPTO")
	public void test_ZEPTO() {
		assertEquals(1E-21, StandardMeasures.ZEPTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "ATTO")
	public void test_ATTO() {
		assertEquals(1E-18, StandardMeasures.ATTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "FEMTO")
	public void test_FEMTO() {
		assertEquals(1E-15, StandardMeasures.FEMTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "PICO")
	public void test_PICO() {
		assertEquals(1E-12, StandardMeasures.PICO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "NANO")
	public void test_NANO() {
		assertEquals(1E-9, StandardMeasures.NANO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MICRO")
	public void test_MICRO() {
		assertEquals(1E-6, StandardMeasures.MICRO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MILLI")
	public void test_MILLI() {
		assertEquals(1E-3, StandardMeasures.MILLI);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "CENTI")
	public void test_CENTI() {
		assertEquals(1E-2, StandardMeasures.CENTI);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "DECI")
	public void test_DECI() {
		assertEquals(1E-1, StandardMeasures.DECI);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "DECA")
	public void test_DECA() {
		assertEquals(1E1, StandardMeasures.DECA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "HECTO")
	public void test_HECTO() {
		assertEquals(1E2, StandardMeasures.HECTO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "KILO")
	public void test_KILO() {
		assertEquals(1E3, StandardMeasures.KILO);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "MEGA")
	public void test_MEGA() {
		assertEquals(1E6, StandardMeasures.MEGA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "GIGA")
	public void test_GIGA() {
		assertEquals(1E9, StandardMeasures.GIGA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "TERA")
	public void test_TERA() {
		assertEquals(1E12, StandardMeasures.TERA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "PETA")
	public void test_PETA() {
		assertEquals(1E15, StandardMeasures.PETA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "EXA")
	public void test_EXA() {
		assertEquals(1E18, StandardMeasures.EXA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "ZETTA")
	public void test_ZETTA() {
		assertEquals(1E21, StandardMeasures.ZETTA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "YOTTA")
	public void test_YOTTA() {
		assertEquals(1E24, StandardMeasures.YOTTA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "RONNA")
	public void test_RONNA() {
		assertEquals(1E27, StandardMeasures.RONNA);
	}

	@Test
	@UnitTestField(target = StandardMeasures.class, name = "QUETTA")
	public void test_QUETTA() {
		assertEquals(1E30, StandardMeasures.QUETTA);
	}

}
