package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistributionData implementation for normal distributions
 */
public class TruncatedNormalSampleDistributionData implements SampleDistributionData {

	private static class Data {

		private double mean = 0.0;
		private double standardDeviation = 1.0;
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			mean = data.mean;
			standardDeviation = data.standardDeviation;
			locked = data.locked;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(mean);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(standardDeviation);
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
			if (Double.doubleToLongBits(mean) != Double.doubleToLongBits(other.mean)) {
				return false;
			}
			if (Double.doubleToLongBits(standardDeviation) != Double.doubleToLongBits(other.standardDeviation)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Data [mean=");
			builder.append(mean);
			builder.append(", standardDeviation=");
			builder.append(standardDeviation);
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
	 * Builder class for NormalSampleDistributionData
	 */
	public static class Builder implements SampleDistributionDataBuilder {
		private Data data;

		private Builder(Data data) {
			this.data = data;
		}

		/**
		 * Returns the NormalSampleDistributionData built from the collected parameters.
		 */
		public TruncatedNormalSampleDistributionData build() {
			if (!data.locked) {
				validateData();
			}
			ensureImmutability();
			return new TruncatedNormalSampleDistributionData(data);
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
		 * Sets the mean parameter. Defaults to 0.0
		 * 
		 * 
		 */
		public Builder setMean(double mean) {
			ensureDataMutability();
			data.mean = mean;
			return this;
		}

		/**
		 * Sets the standard deviation parameter. Defaults to 1.0
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#NON_POSITIVE_PARAMETER}
		 *                           if standard deviation is not positive</li>
		 *                           </ul>
		 * 
		 */
		public Builder setStandardDeviation(double standardDeviation) {
			ensureDataMutability();
			if (standardDeviation <= 0) {
				throw new ContractException(DistributionError.NON_POSITIVE_PARAMETER, "standard deviation");
			}
			data.standardDeviation = standardDeviation;
			return this;
		}

	}

	private final Data data;

	private TruncatedNormalSampleDistributionData(Data data) {
		this.data = data;
	}

	@Override
	public Builder getCloneBuilder() {
		return new Builder(data);
	}

	/**
	 * Returns the mean parameter
	 */
	public double getMean() {
		return data.mean;
	}

	/**
	 * Returns the standard deviation parameter
	 */
	public double getStandardDeviation() {
		return data.standardDeviation;
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
		if (!(obj instanceof TruncatedNormalSampleDistributionData)) {
			return false;
		}
		TruncatedNormalSampleDistributionData other = (TruncatedNormalSampleDistributionData) obj;
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
		builder2.append("TruncatedNormalSampleDistributionData [data=");
		builder2.append(data);
		builder2.append("]");
		return builder2.toString();
	}

	@Override
	public TruncatedNormalSampleDistribution getSampleDistribution(RandomGenerator randomGenerator) {
		return new TruncatedNormalSampleDistribution(randomGenerator, this);
	}

}
