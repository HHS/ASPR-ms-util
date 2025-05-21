package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardConstants {
    @Test
    @UnitTestField(target = StandardConstants.class, name = "EARTH_GRAVITY")
    public void test_EARTH_GRAVITY() {
        assertInstanceOf(Constant.class, StandardConstants.EARTH_GRAVITY);
        Quantity quantity = StandardConstants.EARTH_GRAVITY.getQuantity();
        ComposedUnit composedUnit = quantity.getComposedUnit();

        assertEquals(9.80665, quantity.getValue());

        assertEquals(2, composedUnit.getUnits().size());

        // meter
        Optional<Unit> optionalUnit = composedUnit.getUnit(StandardUnits.METER.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.METER, optionalUnit.get());
        Optional<Integer> optionalPower = StandardComposites.ACCELERATION_MPSS
                .getPower(StandardUnits.METER.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(1, optionalPower.get());

        // second
        optionalUnit = composedUnit.getUnit(StandardUnits.SECOND.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.SECOND, optionalUnit.get());
        optionalPower = StandardComposites.ACCELERATION_MPSS.getPower(StandardUnits.SECOND.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(-2, optionalPower.get());
    }
}
