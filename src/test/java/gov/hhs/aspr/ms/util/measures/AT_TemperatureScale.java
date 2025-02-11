package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;

public class AT_TemperatureScale {

	@Test
	@UnitTestMethod(target = TemperatureScale.class, name = "fromAbsolute", args = { double.class,
			TemperatureScale.class })
	public void testFromAbsolute() {
		Map<TemperatureScale, Double> relativeKelvinMap = new LinkedHashMap<>();
		relativeKelvinMap.put(TemperatureScale.CELSIUS, 1.0);
		relativeKelvinMap.put(TemperatureScale.DELISLE, -2.0 / 3);
		relativeKelvinMap.put(TemperatureScale.FAHRENHEIT, 5.0 / 9);
		relativeKelvinMap.put(TemperatureScale.KELVIN, 1.0);
		relativeKelvinMap.put(TemperatureScale.NEWTON, 1 / 0.33);
		relativeKelvinMap.put(TemperatureScale.RANKINE, 5.0 / 9);
		relativeKelvinMap.put(TemperatureScale.REAUMUR, 5.0 / 4);
		relativeKelvinMap.put(TemperatureScale.ROMER, 40.0 / 21);

		assertEquals(relativeKelvinMap.size(), TemperatureScale.values().length);

		Map<TemperatureScale, Double> offsetKelvinMap = new LinkedHashMap<>();
		offsetKelvinMap.put(TemperatureScale.CELSIUS, 273.15);
		offsetKelvinMap.put(TemperatureScale.DELISLE, 373.15);
		offsetKelvinMap.put(TemperatureScale.FAHRENHEIT, -32.0 / 9 * 5 + 273.15);
		offsetKelvinMap.put(TemperatureScale.KELVIN, 0.0);
		offsetKelvinMap.put(TemperatureScale.NEWTON, 273.15);
		offsetKelvinMap.put(TemperatureScale.RANKINE, 0.0);
		offsetKelvinMap.put(TemperatureScale.REAUMUR, 273.15);
		offsetKelvinMap.put(TemperatureScale.ROMER, -7.5 * 40 / 21 + 273.15);

		assertEquals(offsetKelvinMap.size(), TemperatureScale.values().length);

		List<Double> temps = new ArrayList<>();
		temps.add(-10.0);
		temps.add(0.0);
		temps.add(10.0);

		for (TemperatureScale temperatureScale1 : TemperatureScale.values()) {

			for (TemperatureScale temperatureScale2 : TemperatureScale.values()) {
				for (Double temp : temps) {
					double expectedValue = temp;
					expectedValue *= relativeKelvinMap.get(temperatureScale1);
					expectedValue += offsetKelvinMap.get(temperatureScale1);
					expectedValue -= offsetKelvinMap.get(temperatureScale2);
					expectedValue /= relativeKelvinMap.get(temperatureScale2);
					double actualValue = temperatureScale2.fromAbsolute(temp, temperatureScale1);
					assertEquals(expectedValue, actualValue, 1e-12);
				}
			}
		}
	}

	@Test
	@UnitTestMethod(target = TemperatureScale.class, name = "fromRelative", args = { double.class,
			TemperatureScale.class })
	public void testFromRelative() {
		Map<TemperatureScale, Double> relativeKelvinMap = new LinkedHashMap<>();
		relativeKelvinMap.put(TemperatureScale.CELSIUS, 1.0);
		relativeKelvinMap.put(TemperatureScale.DELISLE, -2.0 / 3);
		relativeKelvinMap.put(TemperatureScale.FAHRENHEIT, 5.0 / 9);
		relativeKelvinMap.put(TemperatureScale.KELVIN, 1.0);
		relativeKelvinMap.put(TemperatureScale.NEWTON, 1 / 0.33);
		relativeKelvinMap.put(TemperatureScale.RANKINE, 5.0 / 9);
		relativeKelvinMap.put(TemperatureScale.REAUMUR, 5.0 / 4);
		relativeKelvinMap.put(TemperatureScale.ROMER, 40.0 / 21);

		assertEquals(relativeKelvinMap.size(), TemperatureScale.values().length);

		for (TemperatureScale temperatureScale1 : TemperatureScale.values()) {
			double a = relativeKelvinMap.get(temperatureScale1);
			for (TemperatureScale temperatureScale2 : TemperatureScale.values()) {
				double b = relativeKelvinMap.get(temperatureScale2);
				double expectedValue = a / b;
				double actualValue = temperatureScale2.fromRelative(1.0, temperatureScale1);
				assertEquals(expectedValue, actualValue, 1e-12);
			}
		}
	}

}
