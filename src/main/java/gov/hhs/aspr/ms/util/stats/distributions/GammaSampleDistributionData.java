package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistributionData implementation for gamma distributions 
 */
public class GammaSampleDistributionData implements SampleDistributionData {

	private static class Data {

		private double shape = 1.0;
		private double scale = 1.0;
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			shape = data.shape;
			scale = data.scale;
			locked = data.locked;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Data [shape=");
			builder.append(shape);
			builder.append(", scale=");
			builder.append(scale);
			builder.append("]");
			return builder.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(scale);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(shape);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Data)) {
				return false;
			}
			Data other = (Data) obj;
			if (Double.doubleToLongBits(scale) != Double.doubleToLongBits(other.scale)) {
				return false;
			}
			if (Double.doubleToLongBits(shape) != Double.doubleToLongBits(other.shape)) {
				return false;
			}
			return true;
		}

		

	}

	/**
	 * Returns a new builder instance.
	 */
	public static Builder builder() {
		return new Builder(new Data());
	}

	/**
	 * Builder class for GamaSampleDistributionData
	 */
	public static class Builder implements SampleDistributionDataBuilder {
		private Data data;

		private Builder(Data data) {
			this.data = data;
		}

		/**
		 * Returns the GamaSampleDistributionData built from the collected parameters.
		 */
		public GammaSampleDistributionData build() {
			if (!data.locked) {
				validateData();
			}
			ensureImmutability();
			return new GammaSampleDistributionData(data);
		}

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

		private void validateData() {

		}

		/**
		 * Sets the shape parameter. Defaults to 1.0
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#NON_POSITIVE_PARAMETER}
		 *                           if shape is negative</li>
		 *                           </ul>
		 * 
		 */
		public Builder setShape(double shape) {
			ensureDataMutability();
			if (shape <= 0) {
				throw new ContractException(DistributionError.NON_POSITIVE_PARAMETER, "shape");
			}
			data.shape = shape;
			return this;
		}

		/**
		 * Sets the scale parameter. Defaults to 1.0
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#NON_POSITIVE_PARAMETER}
		 *                           if scale is negative</li>
		 *                           </ul>
		 * 
		 */
		public Builder setScale(double scale) {
			ensureDataMutability();
			if (scale <= 0) {
				throw new ContractException(DistributionError.NON_POSITIVE_PARAMETER, "beta");
			}
			data.scale = scale;
			return this;
		}

	}

	private final Data data;

	private GammaSampleDistributionData(Data data) {
		this.data = data;
	}

	@Override
	public Builder getCloneBuilder() {
		return new Builder(data);
	}

	/**
	 * Returns the shape parameter
	 */
	public double getShape() {
		return data.shape;
	}

	/**
	 * Returns the scale parameter
	 */
	public double getScale() {
		return data.scale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GammaSampleDistributionData)) {
			return false;
		}
		GammaSampleDistributionData other = (GammaSampleDistributionData) obj;
		if (data == null) {
			if (other.data != null) {
				return false;
			}
		} else if (!data.equals(other.data)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("GammaSampleDistributionData [data=");
		builder2.append(data);
		builder2.append("]");
		return builder2.toString();
	}

	@Override
	public GammaSampleDistribution getSampleDistribution(RandomGenerator randomGenerator) {
		return new GammaSampleDistribution(randomGenerator,this);
	}


}
