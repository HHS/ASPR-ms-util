package gov.hhs.aspr.ms.util.measures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestField;

public class AT_StandardUnits {
    @Test
    @UnitTestField(target = StandardUnits.class, name = "METER")
    public void test_METER() {
        assertInstanceOf(Unit.class, StandardUnits.METER);
        assertEquals("meter", StandardUnits.METER.getLongName());
        assertEquals("m", StandardUnits.METER.getShortName());
        assertEquals(StandardUnitTypes.LENGTH,StandardUnits.METER.getUnitType());
        assertEquals(1,StandardUnits.METER.getValue());
        
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "CM")
    public void test_CM() {
        assertInstanceOf(Unit.class, StandardUnits.CM);
        assertEquals("centimeter", StandardUnits.CM.getLongName());
        assertEquals("cm", StandardUnits.CM.getShortName());
        assertEquals(StandardUnitTypes.LENGTH,StandardUnits.CM.getUnitType());
        assertEquals(StandardScalars.CENTI,StandardUnits.CM.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "DM")
    public void test_DM() {
        assertInstanceOf(Unit.class, StandardUnits.DM);
        assertEquals("decimeter", StandardUnits.DM.getLongName());
        assertEquals("dm", StandardUnits.DM.getShortName());
        assertEquals(StandardUnitTypes.LENGTH,StandardUnits.DM.getUnitType());
        assertEquals(StandardScalars.DECI,StandardUnits.DM.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "INCH")
    public void test_INCH() {
        assertInstanceOf(Unit.class, StandardUnits.INCH);
        assertEquals("inch", StandardUnits.INCH.getLongName());
        assertEquals("in", StandardUnits.INCH.getShortName());
        assertEquals(StandardUnitTypes.LENGTH,StandardUnits.INCH.getUnitType());
        assertEquals(2.54*StandardScalars.CENTI,StandardUnits.INCH.getValue());

    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "FOOT")
    public void test_FOOT() {
        assertInstanceOf(Unit.class, StandardUnits.FOOT);
        assertEquals("foot", StandardUnits.FOOT.getLongName());
        assertEquals("ft", StandardUnits.FOOT.getShortName());
        assertEquals(StandardUnitTypes.LENGTH,StandardUnits.FOOT.getUnitType());
        assertEquals(12*2.54*StandardScalars.CENTI,StandardUnits.FOOT.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MILE")
    public void test_MILE() {
        assertInstanceOf(Unit.class, StandardUnits.MILE);
        assertEquals("mile", StandardUnits.MILE.getLongName());
        assertEquals("mi", StandardUnits.MILE.getShortName());
        assertEquals(StandardUnitTypes.LENGTH,StandardUnits.MILE.getUnitType());
        assertEquals(5280*12*2.54*StandardScalars.CENTI,StandardUnits.MILE.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "SECOND")
    public void test_SECOND() {
        assertInstanceOf(Unit.class, StandardUnits.SECOND);
        assertEquals("second", StandardUnits.SECOND.getLongName());
        assertEquals("s", StandardUnits.SECOND.getShortName());
        assertEquals(StandardUnitTypes.TIME,StandardUnits.SECOND.getUnitType());
        assertEquals(1,StandardUnits.SECOND.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MINUTE")
    public void test_MINUTE() {
        assertInstanceOf(Unit.class, StandardUnits.MINUTE);
        assertEquals("minute", StandardUnits.MINUTE.getLongName());
        assertEquals("min", StandardUnits.MINUTE.getShortName());
        assertEquals(StandardUnitTypes.TIME,StandardUnits.MINUTE.getUnitType());
        assertEquals(60,StandardUnits.MINUTE.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "HOUR")
    public void test_HOUR() {
        assertInstanceOf(Unit.class, StandardUnits.HOUR);
        assertEquals("hour", StandardUnits.HOUR.getLongName());
        assertEquals("h", StandardUnits.HOUR.getShortName());
        assertEquals(StandardUnitTypes.TIME,StandardUnits.HOUR.getUnitType());
        assertEquals(60*60,StandardUnits.HOUR.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "DAY")
    public void test_DAY() {
        assertInstanceOf(Unit.class, StandardUnits.DAY);
        assertEquals("day", StandardUnits.DAY.getLongName());
        assertEquals("d", StandardUnits.DAY.getShortName());
        assertEquals(StandardUnitTypes.TIME,StandardUnits.DAY.getUnitType());
        assertEquals(24*60*60,StandardUnits.DAY.getValue());
    }
    
    @Test
    @UnitTestField(target = StandardUnits.class, name = "WEEK")
    public void test_WEEK() {
        assertInstanceOf(Unit.class, StandardUnits.WEEK);
        assertEquals("week", StandardUnits.WEEK.getLongName());
        assertEquals("d", StandardUnits.DAY.getShortName());
        assertEquals(StandardUnitTypes.TIME,StandardUnits.WEEK.getUnitType());
        assertEquals(7*24*60*60,StandardUnits.WEEK.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "KILOGRAM")
    public void test_KILOGRAM() {
        assertInstanceOf(Unit.class, StandardUnits.KILOGRAM);
        assertEquals("kilogram", StandardUnits.KILOGRAM.getLongName());
        assertEquals("kg", StandardUnits.KILOGRAM.getShortName());
        assertEquals(StandardUnitTypes.MASS,StandardUnits.KILOGRAM.getUnitType());
        assertEquals(1000,StandardUnits.KILOGRAM.getValue());

    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "GRAM")
    public void test_GRAM() {
        assertInstanceOf(Unit.class, StandardUnits.GRAM);
        assertEquals("gram", StandardUnits.GRAM.getLongName());
        assertEquals("g", StandardUnits.GRAM.getShortName());
        assertEquals(StandardUnitTypes.MASS,StandardUnits.GRAM.getUnitType());
        assertEquals(1,StandardUnits.GRAM.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MILLIGRAM")
    public void test_MILLIGRAM() {
        assertInstanceOf(Unit.class, StandardUnits.MILLIGRAM);
        assertEquals("milligram", StandardUnits.MILLIGRAM.getLongName());
        assertEquals("mg", StandardUnits.MILLIGRAM.getShortName());
        assertEquals(StandardUnitTypes.MASS,StandardUnits.MILLIGRAM.getUnitType());
        assertEquals(StandardScalars.MILLI,StandardUnits.MILLIGRAM.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "MICROGRAM")
    public void test_MICROGRAM() {
        assertInstanceOf(Unit.class, StandardUnits.MICROGRAM);
        assertEquals("microgram", StandardUnits.MICROGRAM.getLongName());
        assertEquals("mcg", StandardUnits.MICROGRAM.getShortName());
        assertEquals(StandardUnitTypes.MASS,StandardUnits.MICROGRAM.getUnitType());
        assertEquals(StandardScalars.MICRO,StandardUnits.MICROGRAM.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "AMPERE")
    public void test_AMPERE() {
        assertInstanceOf(Unit.class, StandardUnits.AMPERE);
        assertEquals("ampere", StandardUnits.AMPERE.getLongName());
        assertEquals("A", StandardUnits.AMPERE.getShortName());
        assertEquals(StandardUnitTypes.CURRENT,StandardUnits.AMPERE.getUnitType());
        assertEquals(1,StandardUnits.AMPERE.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "KELVIN")
    public void test_KELVIN() {
        assertInstanceOf(Unit.class, StandardUnits.KELVIN);
        assertEquals("kelvin", StandardUnits.KELVIN.getLongName());
        assertEquals("K", StandardUnits.KELVIN.getShortName());
        assertEquals(StandardUnitTypes.TEMPERATURE,StandardUnits.KELVIN.getUnitType());
        assertEquals(1,StandardUnits.KELVIN.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "CANDELA")
    public void test_CANDELA() {
        assertInstanceOf(Unit.class, StandardUnits.CANDELA);
        assertEquals("candela", StandardUnits.CANDELA.getLongName());
        assertEquals("cd", StandardUnits.CANDELA.getShortName());
        assertEquals(StandardUnitTypes.LUMINOSITY,StandardUnits.CANDELA.getUnitType());
        assertEquals(1,StandardUnits.CANDELA.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "RADIAN")
    public void test_RADIAN() {
        assertInstanceOf(Unit.class, StandardUnits.RADIAN);
        assertEquals("raidan", StandardUnits.RADIAN.getLongName());
        assertEquals("rad", StandardUnits.RADIAN.getShortName());
        assertEquals(StandardUnitTypes.ANGLE,StandardUnits.RADIAN.getUnitType());
        assertEquals(1,StandardUnits.RADIAN.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "DEGREE")
    public void test_DEGREE() {
        assertInstanceOf(Unit.class, StandardUnits.DEGREE);
        assertEquals("degree", StandardUnits.DEGREE.getLongName());
        assertEquals("deg", StandardUnits.DEGREE.getShortName());
        assertEquals(StandardUnitTypes.ANGLE,StandardUnits.DEGREE.getUnitType());
        assertEquals(FastMath.PI / 180,StandardUnits.DEGREE.getValue());
    }

    @Test
    @UnitTestField(target = StandardUnits.class, name = "STERADIAN")
    public void test_STERADIAN() {
        assertInstanceOf(Unit.class, StandardUnits.STERADIAN);
        assertEquals("steradian", StandardUnits.STERADIAN.getLongName());
        assertEquals("st", StandardUnits.STERADIAN.getShortName());
        assertEquals(StandardUnitTypes.SOLID_ANGLE,StandardUnits.STERADIAN.getUnitType());
        assertEquals(1,StandardUnits.STERADIAN.getValue());
    }
}
