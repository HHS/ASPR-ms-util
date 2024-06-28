package gov.hhs.aspr.ms.util.siunits;

import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.siunits.SIQuantity.Builder;

public final class SITemperatureConstant {
	private final String name;
	private final String symbol;
	protected final SIQuantity siQuantity;
	private final double offset;
	

	public final String getName() {
		return name;
	}

	public final String getSymbol() {
		return symbol;
	}

	
	public final SIQuantity getRelativeSiQuantity(double value) {		
		return siQuantity.scalarMultiply(value);
	}
	
	public final SIQuantity getAbsoluteSiQuantity(double value) {
		Builder builder = SIQuantity.builder();
		builder.setScalar(value-offset*siQuantity.getScalar());
		for(SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, siQuantity.getDimension(siBaseUnit));
		}
		return builder.build();
	}
	
	public SITemperatureConstant(String name, String symbol, SIQuantity siQuantity, double offset) {
		this.name = name;
		this.symbol = symbol;
		this.siQuantity = siQuantity;
		this.offset = offset;
	}	
	
	public double getRelativeScalarConversion(SIQuantity siQuantity) {
		if(!SIQuantity.dimensionsMatch(this.siQuantity,siQuantity)) {
			throw new ContractException(SIError.DIMENSIONAL_MISMATCH);
		}
		return siQuantity.getScalar()/this.siQuantity.getScalar();
	}	
	
	public double getAbsoluteScalarConversion(SIQuantity siQuantity) {
		if(!SIQuantity.dimensionsMatch(this.siQuantity,siQuantity)) {
			throw new ContractException(SIError.DIMENSIONAL_MISMATCH);
		}		
		return siQuantity.getScalar()/this.siQuantity.getScalar()+offset;
	}

}
