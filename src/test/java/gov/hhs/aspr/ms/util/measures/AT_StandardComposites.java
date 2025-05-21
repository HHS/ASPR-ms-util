package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardComposites {
    @Test
    @UnitTestField(target = StandardComposites.class, name = "MPH")
    public void test_MPH() {
        assertInstanceOf(ComposedUnit.class, StandardComposites.MPH);
        assertEquals(2, StandardComposites.MPH.getUnits().size());
        // mile
        Optional<Unit> optionalUnit = StandardComposites.MPH.getUnit(StandardUnits.MILE.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.MILE, optionalUnit.get());
        Optional<Integer> optionalPower = StandardComposites.MPH.getPower(StandardUnits.MILE.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(1, optionalPower.get());

        // hour
        optionalUnit = StandardComposites.MPH.getUnit(StandardUnits.HOUR.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.HOUR, optionalUnit.get());
        optionalPower = StandardComposites.MPH.getPower(StandardUnits.HOUR.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(-1, optionalPower.get());
    }

    @Test
    @UnitTestField(target = StandardComposites.class, name = "MPS")
    public void test_MPS() {
        assertInstanceOf(ComposedUnit.class, StandardComposites.MPS);
        assertEquals(2, StandardComposites.MPS.getUnits().size());
        // meter
        Optional<Unit> optionalUnit = StandardComposites.MPS.getUnit(StandardUnits.METER.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.METER, optionalUnit.get());
        Optional<Integer> optionalPower = StandardComposites.MPS.getPower(StandardUnits.METER.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(1, optionalPower.get());

        // second
        optionalUnit = StandardComposites.MPS.getUnit(StandardUnits.SECOND.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.SECOND, optionalUnit.get());
        optionalPower = StandardComposites.MPS.getPower(StandardUnits.SECOND.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(-1, optionalPower.get());
    }

    @Test
    @UnitTestField(target = StandardComposites.class, name = "ACCELERATION_MPSS")
    public void test_ACCELERATION_MPSS() {
        assertInstanceOf(ComposedUnit.class, StandardComposites.ACCELERATION_MPSS);
        assertEquals(2, StandardComposites.ACCELERATION_MPSS.getUnits().size());
        // meter
        Optional<Unit> optionalUnit = StandardComposites.ACCELERATION_MPSS.getUnit(StandardUnits.METER.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.METER, optionalUnit.get());
        Optional<Integer> optionalPower = StandardComposites.ACCELERATION_MPSS
                .getPower(StandardUnits.METER.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(1, optionalPower.get());

        // second
        optionalUnit = StandardComposites.ACCELERATION_MPSS.getUnit(StandardUnits.SECOND.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.SECOND, optionalUnit.get());
        optionalPower = StandardComposites.ACCELERATION_MPSS.getPower(StandardUnits.SECOND.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(-2, optionalPower.get());
    }

    @Test
    @UnitTestField(target = StandardComposites.class, name = "ML")
    public void test_ML() {
        assertInstanceOf(ComposedUnit.class, StandardComposites.ML);
        assertEquals(1, StandardComposites.ML.getUnits().size());
        // centimeter
        Optional<Unit> optionalUnit = StandardComposites.ML.getUnit(StandardUnits.CM.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.CM, optionalUnit.get());
        Optional<Integer> optionalPower = StandardComposites.ML.getPower(StandardUnits.CM.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(3, optionalPower.get());

    }

    @Test
    @UnitTestField(target = StandardComposites.class, name = "LITER")
    public void test_LITER() {
        assertInstanceOf(ComposedUnit.class, StandardComposites.LITER);
        assertEquals(1, StandardComposites.LITER.getUnits().size());
        // decimeter
        Optional<Unit> optionalUnit = StandardComposites.LITER.getUnit(StandardUnits.DM.getUnitType());
        assertTrue(optionalUnit.isPresent());
        assertEquals(StandardUnits.DM, optionalUnit.get());
        Optional<Integer> optionalPower = StandardComposites.LITER.getPower(StandardUnits.DM.getUnitType());
        assertTrue(optionalPower.isPresent());
        assertEquals(3, optionalPower.get());

    }
}
