package gov.hhs.aspr.ms.util.measures;

public final class StandardComposites {
    // speed
    public static ComposedUnit MPH = ComposedUnit.builder()//
    		.setUnit(StandardUnits.MILE, 1)//
            .setUnit(StandardUnits.HOUR, -1)//
            .build();
    
    public static ComposedUnit MPS = ComposedUnit.builder()//
    		.setUnit(StandardUnits.METER, 1)//
            .setUnit(StandardUnits.SECOND, -1)//
            .build();

    // acceleration
    public static ComposedUnit ACCELERATION_MPSS = ComposedUnit.builder()//
    		.setUnit(StandardUnits.METER, 1)//
            .setUnit(StandardUnits.SECOND, -2)//
            .build();

    // Liquid volume
    public static ComposedUnit ML = ComposedUnit.builder()//
    		.setUnit(StandardUnits.CM, 3)//
    		.setShortName("ml")//
            .setLongName("milliliter")//
            .build();

    public static ComposedUnit LITER = ComposedUnit.builder()//
    		.setUnit(StandardUnits.DM, 3)//
    		.setShortName("L")//
            .setLongName("liter")//
            .build();

    private StandardComposites() {
    }
}
