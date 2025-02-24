package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistributionData implementation for normal distributions
 */
public class UniformSampleDistributionData implements SampleDistributionData {

	private static class Data {

		private double lower = 0.0;
		private double uppper = 1.0;
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			lower = data.lower;
			uppper = data.uppper;
			locked = data.locked;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(lower);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(uppper);
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
			if (Double.doubleToLongBits(lower) != Double.doubleToLongBits(other.lower)) {
				return false;
			}
			if (Double.doubleToLongBits(uppper) != Double.doubleToLongBits(other.uppper)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Data [lower=");
			builder.append(lower);
			builder.append(", uppper=");
			builder.append(uppper);
			builder.append("]");
			return builder.toString();
		}
	}

	/**
	 * Returns a new builder instance.
	 */
	public static Builder builder() {
		return new Builder(new Data());
	}

	/**
	 * Builder class for UniformSampleDistributionData
	 */
	public static class Builder implements SampleDistributionDataBuilder {
		private Data data;

		private Builder(Data data) {
			this.data = data;
		}

		/**
		 * Returns the UniformSampleDistributionData built from the collected
		 * parameters.
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#LOWER_EXCEEDS_UPPER}
		 *                           if the lower parameter value exceeds the upper
		 *                           parameter value</li>
		 *                           </ul>
		 * 
		 */
		public UniformSampleDistributionData build() {
			if (!data.locked) {
				validateData();
			}
			ensureImmutability();
			return new UniformSampleDistributionData(data);
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
			if (data.lower > data.uppper) {
				throw new ContractException(DistributionError.LOWER_EXCEEDS_UPPER);
			}
		}

		/**
		 * Sets the lower parameter. Defaults to 0.0
		 * 
		 * 
		 */
		public Builder setLower(double lower) {
			ensureDataMutability();
			data.lower = lower;
			return this;
		}

		/**
		 * Sets the upper parameter. Defaults to 1.0
		 */
		public Builder setUpper(double uppper) {
			ensureDataMutability();
			data.uppper = uppper;
			return this;
		}

	}

	private final Data data;

	private UniformSampleDistributionData(Data data) {
		this.data = data;
	}

	@Override
	public Builder getCloneBuilder() {
		return new Builder(data);
	}

	/**
	 * Returns the lower parameter
	 */
	public double getLower() {
		return data.lower;
	}

	/**
	 * Returns the upper parameter
	 */
	public double getUpper() {
		return data.uppper;
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
		if (!(obj instanceof UniformSampleDistributionData)) {
			return false;
		}
		UniformSampleDistributionData other = (UniformSampleDistributionData) obj;
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
		builder2.append("UniformSampleDistributionData [data=");
		builder2.append(data);
		builder2.append("]");
		return builder2.toString();
	}

	@Override
	public UniformSampleDistribution getSampleDistribution(RandomGenerator randomGenerator) {	
		return new UniformSampleDistribution(randomGenerator,this);
	}

}
