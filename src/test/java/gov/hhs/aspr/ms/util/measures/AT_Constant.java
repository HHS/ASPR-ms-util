package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_Constant {

	@Test
	@UnitTestConstructor(target = Constant.class, args = { Quantity.class, String.class, String.class })
	public void testConstant() {
		// postcondition tests covered by the remaining tests

		UnitType LENGTH = new UnitType("length");

		Unit METER = new Unit(LENGTH, "meter", "m");

		Quantity quantity = new Quantity(METER, 103.34778);
		// precondition test: if the long name is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> new Constant(quantity, null, "short_name"));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the long name is empty or contains only white space
		// characters
		contractException = assertThrows(ContractException.class, () -> new Constant(quantity, "", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Constant(quantity, " ", "short_name"));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is null
		contractException = assertThrows(ContractException.class, () -> new Constant(quantity, "long_name", null));
		assertEquals(MeasuresError.NULL_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the short name is empty or contains only white space
		// characters
		contractException = assertThrows(ContractException.class, () -> new Constant(quantity, "long_name", ""));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Constant(quantity, "long_name", " "));
		assertEquals(MeasuresError.BLANK_UNIT_NAME, contractException.getErrorType());

		// precondition test: if the quantity is null
		contractException = assertThrows(ContractException.class, () -> new Constant(null, "long_name", "short_name"));
		assertEquals(MeasuresError.NULL_QUANTITY, contractException.getErrorType());
	}

	/*
	 * Creates a randomized Constant using several time, length and mass units to
	 * powers in [-5,-1]U[1,5], a random value and randomized long and short names.
	 */
	private Constant getRandomConstant(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
		List<Unit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");
		List<Unit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");
		List<Unit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		Unit timeUnit = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int timePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int distancePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int massPower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);

		ComposedUnit composedUnit = ComposedUnit.builder()//
				.setUnit(timeUnit, timePower)//
				.setUnit(distanceUnit, distancePower)//
				.setUnit(massUnit, massPower)//
				.build();

		double value = randomGenerator.nextDouble();
		Quantity quantity = new Quantity(composedUnit, value);
		String longName = "name_" + randomGenerator.nextInt(10000);
		String shortName = "n_" + randomGenerator.nextInt(10000);
		return new Constant(quantity, longName, shortName);
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "equals", args = { Object.class })
	public void testEquals() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(8800448024744882881L);
		// Two constants are equal if and only if their quantities and names are equal

		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			Constant constant1 = getRandomConstant(seed);
			Constant constant2 = getRandomConstant(seed);
			assertEquals(constant1, constant2);
		}

		// not equal to null
		for (int i = 0; i < 30; i++) {
			Constant constant = getRandomConstant(randomGenerator.nextLong());
			assertFalse(constant.equals(null));
		}

		// not equal to another type
		for (int i = 0; i < 30; i++) {
			Constant constant = getRandomConstant(randomGenerator.nextLong());
			assertFalse(constant.equals(new Object()));
		}

		// reflexive
		for (int i = 0; i < 30; i++) {
			Constant constant = getRandomConstant(randomGenerator.nextLong());
			assertTrue(constant.equals(constant));
		}

		// symmetric, transitive, stable
		for (int i = 0; i < 30; i++) {
			Constant constant = getRandomConstant(randomGenerator.nextLong());
			assertTrue(constant.equals(constant));
		}

	}

	/*
	 * Creates two randomized quantities using several time, length and mass units
	 * to powers in [-5,-1]U[1,5]. They will have random values, but will agree on
	 * unitTypes and powers.
	 */
	private Pair<Quantity, Quantity> getRandomCompatibleQuanties(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		UnitType MASS = new UnitType("mass");

		Unit SECOND = new Unit(TIME, "second", "s");
		Unit MINUTE = new Unit(SECOND, 60, "minute", "min");
		Unit HOUR = new Unit(MINUTE, 60, "hour", "h");
		List<Unit> timeUnits = new ArrayList<>();
		timeUnits.add(SECOND);
		timeUnits.add(MINUTE);
		timeUnits.add(HOUR);

		Unit METER = new Unit(LENGTH, "meter", "m");
		Unit CM = new Unit(METER, 0.01, "centimeter", "cm");
		Unit INCH = new Unit(CM, 2.54, "inch", "in");
		Unit FOOT = new Unit(INCH, 12, "foot", "ft");
		Unit MILE = new Unit(FOOT, 5280, "mile", "mi");
		List<Unit> distanceUnits = new ArrayList<>();
		distanceUnits.add(METER);
		distanceUnits.add(CM);
		distanceUnits.add(INCH);
		distanceUnits.add(FOOT);
		distanceUnits.add(MILE);

		Unit KILOGRAM = new Unit(MASS, "kilogram", "kg");
		Unit POUND = new Unit(KILOGRAM, 0.45359237, "pound", "lb");
		Unit OUNCE = new Unit(POUND, 1.0 / 16, "ounce", "oz");
		List<Unit> massUnits = new ArrayList<>();
		massUnits.add(KILOGRAM);
		massUnits.add(POUND);
		massUnits.add(OUNCE);

		Unit timeUnit1 = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit1 = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit1 = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		Unit timeUnit2 = timeUnits.get(randomGenerator.nextInt(timeUnits.size()));
		Unit distanceUnit2 = distanceUnits.get(randomGenerator.nextInt(distanceUnits.size()));
		Unit massUnit2 = massUnits.get(randomGenerator.nextInt(massUnits.size()));

		int timePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int distancePower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);
		int massPower = randomGenerator.nextInt(5) + 1 * (randomGenerator.nextInt(2) * 2 - 1);

		ComposedUnit composedUnit1 = ComposedUnit.builder()//
				.setUnit(timeUnit1, timePower)//
				.setUnit(distanceUnit1, distancePower)//
				.setUnit(massUnit1, massPower)//
				.build();

		ComposedUnit composedUnit2 = ComposedUnit.builder()//
				.setUnit(timeUnit2, timePower)//
				.setUnit(distanceUnit2, distancePower)//
				.setUnit(massUnit2, massPower)//
				.build();

		double value1 = randomGenerator.nextDouble();
		double value2 = randomGenerator.nextDouble();

		Quantity quantity1 = new Quantity(composedUnit1, value1);
		Quantity quantity2 = new Quantity(composedUnit2, value2);

		return new Pair<>(quantity1, quantity2);
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getConvertedValue", args = { Quantity.class })
	public void testGetConvertedValue() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7009329198205894838L);
		// Two constants are equal if and only if their quantities and names are equal

		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			Pair<Quantity, Quantity> pair = getRandomCompatibleQuanties(seed);
			Quantity quantity1 = pair.getFirst();
			Quantity quantity2 = pair.getSecond();
			Constant constant = new Constant(quantity1, "long_name", "short_name");
			double actualValue = constant.getConvertedValue(quantity2);
			double expectedValue = quantity2.rebase(quantity1.getComposedUnit()).getValue() / quantity1.getValue();
			/*
			 * Slight differences in how the expected and actual values are calculated
			 * prevent us from having an exact match
			 */
			assertTrue(1 - (actualValue / expectedValue) < 1e-12);
		}

	}

	/*
	 * Returns a randomized time quantity.
	 */
	private Quantity getRandomizedTimeQuanity(long seed) {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(seed);
		UnitType unitType = new UnitType("TIME");
		
		Unit SECOND = new Unit(unitType, "second", "sec");
		Unit MINUTE = new Unit(SECOND,60,"minute", "min");
		Unit HOUR = new Unit(MINUTE,60,"hour", "hr");
		Unit DAY = new Unit(HOUR,24,"day", "dy");
		
		List<Unit> units = new ArrayList<>();
		units.add(SECOND);
		units.add(MINUTE);
		units.add(HOUR);
		units.add(DAY);
		
		Unit unit = units.get(randomGenerator.nextInt(units.size()));
		return new Quantity(unit, randomGenerator.nextDouble()/10+0.9);
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getLongString", args = { Quantity.class })
	public void testGetLongString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(778126436041924435L);

		for (int i = 0; i < 30; i++) {
			Quantity constantQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			String longName = "long_name_"+randomGenerator.nextInt(100000);
			Constant constant = new Constant(constantQuantity, longName, "short_name");
			Quantity conversionQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			
			double normal1 = constantQuantity.getComposedUnit().getValue()*constantQuantity.getValue();
			double normal2 = conversionQuantity.getComposedUnit().getValue()*conversionQuantity.getValue();
			
			String expectedValue = normal2/normal1+" "+longName;
			
			String actualValue = constant.getLongString(conversionQuantity);
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getShortString", args = { Quantity.class })
	public void testGetShortString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(2863726012144072861L);

		for (int i = 0; i < 30; i++) {
			Quantity constantQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			String shortName = "short_name_"+randomGenerator.nextInt(100000);
			Constant constant = new Constant(constantQuantity, "long_name", shortName);
			Quantity conversionQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			
			double normal1 = constantQuantity.getComposedUnit().getValue()*constantQuantity.getValue();
			double normal2 = conversionQuantity.getComposedUnit().getValue()*conversionQuantity.getValue();
			
			String expectedValue = normal2/normal1+" "+shortName;
			
			String actualValue = constant.getShortString(conversionQuantity);
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getLongName", args = {})
	public void testGetLongName() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(598688435087897951L);

		for (int i = 0; i < 30; i++) {
			Quantity constantQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			String expectedValue = "long_name_"+randomGenerator.nextInt(100000);
			Constant constant = new Constant(constantQuantity, expectedValue, "short_name");
			String actualValue = constant.getLongName();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getQuantity", args = {})
	public void testGetQuantity() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(5230784933008333474L);
		for (int i = 0; i < 30; i++) {
			Quantity expectedValue = getRandomizedTimeQuanity(randomGenerator.nextLong());
			Constant constant = new Constant(expectedValue, "long_name", "short_name");
			Quantity actualValue = constant.getQuantity();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getShortName", args = {})
	public void testGetShortName() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3900313195340096046L);

		for (int i = 0; i < 30; i++) {
			Quantity constantQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			String expectedValue = "short_name_"+randomGenerator.nextInt(100000);
			Constant constant = new Constant(constantQuantity,"long_name", expectedValue);
			String actualValue = constant.getShortName();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "getShortString", args = { Quantity.class })
	public void getShortString() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(598688435087897951L);

		for (int i = 0; i < 30; i++) {
			Quantity constantQuantity = getRandomizedTimeQuanity(randomGenerator.nextLong());
			String expectedValue = "short_name_"+randomGenerator.nextInt(100000);
			Constant constant = new Constant(constantQuantity, "long_name",expectedValue);
			String actualValue = constant.getShortName();
			assertEquals(expectedValue, actualValue);
		}
	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "hashCode", args = {})
	public void testHashCode() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(3747610952977439670L);
		// equal objects have equal hash codes
		for (int i = 0; i < 30; i++) {
			long seed = randomGenerator.nextLong();
			Constant constant1 = getRandomConstant(seed);
			Constant constant2 = getRandomConstant(seed);
			assertEquals(constant1, constant2);
			assertEquals(constant1.hashCode(), constant2.hashCode());
		}

		// hash codes are reasonably distributed
		Set<Integer> hashCodes = new LinkedHashSet<>();
		for (int i = 0; i < 100; i++) {
			Constant constant = getRandomConstant(randomGenerator.nextLong());
			hashCodes.add(constant.hashCode());
		}
		assertEquals(100, hashCodes.size());

	}

	@Test
	@UnitTestMethod(target = Constant.class, name = "toString", args = {})
	public void testToString() {

		UnitType TIME = new UnitType("time");
		UnitType LENGTH = new UnitType("length");
		Unit SECOND = new Unit(TIME, "second", "s");
		Unit METER = new Unit(LENGTH, "meter", "m");
		ComposedUnit MPSS = ComposedUnit.builder().setUnit(METER, 1).setUnit(SECOND, -2).build();
		Quantity quantity = new Quantity(MPSS, 9.80665);

		Constant EARTH_GRAVITY = new Constant(quantity, "earth_gravity", "g");

		String expectedValue = "Constant [longName=earth_gravity, shortName=g, quantity=Quantity [composedUnit=ComposedUnit [value=1.0, longName=null, shortName=null, unitTypes={Measure [name=length]=UnitPower [unit=Unit [unitType=Measure [name=length], value=1.0, name=meter, shortName=m], power=1], Measure [name=time]=UnitPower [unit=Unit [unitType=Measure [name=time], value=1.0, name=second, shortName=s], power=-2]}], value=9.80665]]";
		String actualValue = EARTH_GRAVITY.toString();
		assertEquals(expectedValue, actualValue);
	}

}
