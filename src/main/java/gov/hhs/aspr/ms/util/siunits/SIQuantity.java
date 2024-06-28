package gov.hhs.aspr.ms.util.siunits;

import org.apache.commons.math3.util.FastMath;

import gov.hhs.aspr.ms.util.errors.ContractException;

public final class SIQuantity {

	private static class Data {
		private int[] dimensions = new int[SIBaseUnit.values().length];
		private double scalar;
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			for (int i = 0; i < dimensions.length; i++) {
				dimensions[i] = data.dimensions[i];
			}
			scalar = data.scalar;
			locked = data.locked;
		}
	}

	private SIQuantity(Data data) {
		this.data = data;
	}

	public static Builder builder() {
		return new Builder(new Data());
	}

	public static class Builder {
		private Data data;

		private void ensureDataMutability() {
			if (data.locked) {
				data = new Data(data);
				data.locked = false;
			}
		}

		private void ensureImmutability() {
			if (!data.locked) {
				data.locked = true;
			}
		}

		private Builder(Data data) {
			this.data = data;
		}

		public SIQuantity build() {
			ensureImmutability();
			return new SIQuantity(data);
		}

		public Builder setScalar(double scalar) {
			ensureDataMutability();
			data.scalar = scalar;
			return this;
		}

		public Builder setDimension(SIBaseUnit siBaseUnit, int power) {
			ensureDataMutability();
			if (siBaseUnit == null) {
				throw new ContractException(SIError.NULL_SI_BASE_UNIT);
			}
			data.dimensions[siBaseUnit.ordinal()] = power;
			return this;
		}

	}

	private final Data data;

	public int getDimension(SIBaseUnit siBaseUnit) {
		if (siBaseUnit == null) {
			throw new ContractException(SIError.NULL_SI_BASE_UNIT);
		}
		return data.dimensions[siBaseUnit.ordinal()];
	}

	public double getScalar() {
		return data.scalar;
	}

	public SIQuantity scalarMultiply(double scalar) {
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit));
		}
		builder.setScalar(getScalar() * scalar);
		return builder.build();
	}

	public SIQuantity multiply(SIQuantity siQuantity) {
		validateDimensionsMatch(siQuantity);
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit) + siQuantity.getDimension(siBaseUnit));
		}
		builder.setScalar(getScalar() * siQuantity.getScalar());
		return builder.build();
	}

	public SIQuantity divide(SIQuantity siQuantity) {
		validateDimensionsMatch(siQuantity);
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit) - siQuantity.getDimension(siBaseUnit));
		}
		builder.setScalar(getScalar() / siQuantity.getScalar());
		return builder.build();
	}

	public static boolean dimensionsMatch(SIQuantity siQuantity_1,SIQuantity siQuantity_2) {
		for (SIBaseUnit siUnit : SIBaseUnit.values()) {
			if (siQuantity_1.getDimension(siUnit) != siQuantity_2.getDimension(siUnit)) {
				return false;
			}
		}
		return true;
	}

	private void validateDimensionsMatch(SIQuantity siQuantity) {
		if(!dimensionsMatch(this,siQuantity)) {
			throw new ContractException(SIError.DIMENSIONAL_MISMATCH);
		}
	}

	public SIQuantity add(SIQuantity siQuantity) {
		validateDimensionsMatch(siQuantity);
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit));
		}
		builder.setScalar(getScalar() + siQuantity.getScalar());
		return builder.build();
	}

	public SIQuantity subtract(SIQuantity siQuantity) {
		validateDimensionsMatch(siQuantity);
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit));
		}
		builder.setScalar(getScalar() - siQuantity.getScalar());
		return builder.build();
	}

	public SIQuantity power(int power) {
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit) * power);
		}
		builder.setScalar(FastMath.pow(getScalar(), power));
		return builder.build();
	}

	public SIQuantity copy(SIQuantity siQuantity) {
		if (siQuantity == null) {
			throw new ContractException(SIError.NULL_SI_QUANTITY);
		}

		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, getDimension(siBaseUnit));
		}
		builder.setScalar(getScalar());
		return builder.build();

	}

	public SIQuantity root(int root) {
		Builder builder = builder();

		if (root < 1) {
			throw new ContractException(SIError.NON_POSITIVE_ROOT);
		}

		for (SIBaseUnit siUnit : SIBaseUnit.values()) {
			if ((getDimension(siUnit) % root) != 0) {
				throw new ContractException(SIError.DIMENSION_IS_NOT_ROOT_COMPATIBLE);
			}
		}

		for (SIBaseUnit siUnit : SIBaseUnit.values()) {
			builder.setDimension(siUnit, getDimension(siUnit) / root);
		}

		builder.setScalar(FastMath.pow(getScalar(), 1.0 / root));

		return builder.build();
	}

	public SIQuantity invert() {
		Builder builder = builder();
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			builder.setDimension(siBaseUnit, -getDimension(siBaseUnit));
		}
		builder.setScalar(1.0 / getScalar());
		return builder.build();
	}

	public boolean isDimensionless() {
		for (int i = 0; i < data.dimensions.length; i++) {
			if (data.dimensions[i] != 0) {
				return false;
			}
		}
		return true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Double.toString(getScalar()));
		sb.append(" ");
		boolean previousDimensionFound = false;
		for (SIBaseUnit siBaseUnit : SIBaseUnit.values()) {
			int dimension = getDimension(siBaseUnit);
			if (dimension != 0) {
				if (previousDimensionFound) {
					sb.append("*");
				} else {
					previousDimensionFound = true;
				}
				sb.append(SIConstants.fromSIBaseUnit(siBaseUnit).getSymbol());
				sb.append(getSuperInt(dimension));
			}
		}
		return sb.toString();
	}

	private static String getSuperChar(char c) {

		switch (c) {
		case '0':
			return "\u2070";
		case '1':
			return "\u00b9";
		case '2':
			return "\u00b2";
		case '3':
			return "\u00b3";
		case '4':
			return "\u2074";
		case '5':
			return "\u2075";
		case '6':
			return "\u2076";
		case '7':
			return "\u2077";
		case '8':
			return "\u2078";
		default:
			return "\u2079";
		}
	}

	private static String getSuperInt(int value) {
		int p = Math.abs(value);
		String normmalInt = Integer.toString(p);
		StringBuilder sb = new StringBuilder();
		if (value < 0) {
			sb.append("\u207B");
		}
		for (int i = 0; i < normmalInt.length(); i++) {
			char digit = normmalInt.charAt(i);
			String superDigit = getSuperChar(digit);
			sb.append(superDigit);
		}
		return sb.toString();
	}

	public boolean isFinite() {
		return Double.isFinite(getScalar());
	}

}
