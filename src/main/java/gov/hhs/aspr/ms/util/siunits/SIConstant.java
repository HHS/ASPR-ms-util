package gov.hhs.aspr.ms.util.siunits;

import gov.hhs.aspr.ms.util.errors.ContractException;

public final class SIConstant {
	private final String name;
	private final String symbol;
	protected final SIQuantity siQuantity;
	
	public SIConstant(String name, String symbol,SIQuantity siQuantity) {
		this.name = name;
		this.symbol = symbol;
		this.siQuantity = siQuantity;
	}

	public final String getName() {
		return name;
	}

	public final String getSymbol() {
		return symbol;
	}

	public final SIQuantity getSiQuantity(double value) {
		return siQuantity.scalarMultiply(value);
	}
	
	public double getScalarConversion(SIQuantity siQuantity) {
		if(!SIQuantity.dimensionsMatch(this.siQuantity,siQuantity)) {
			throw new ContractException(SIError.DIMENSIONAL_MISMATCH);
		}
		return siQuantity.getScalar()/this.siQuantity.getScalar();
	}
	
}
