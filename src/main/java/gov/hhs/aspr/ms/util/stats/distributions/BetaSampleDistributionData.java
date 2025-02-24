package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistributionData implementation for beta distributions 
 */
public class BetaSampleDistributionData implements SampleDistributionData {

	private static class Data {

		private double alpha = 1.0;
		private double beta = 1.0;
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			alpha = data.alpha;
			beta = data.beta;
			locked = data.locked;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Data [alpha=");
			builder.append(alpha);
			builder.append(", beta=");
			builder.append(beta);
			builder.append("]");
			return builder.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(alpha);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(beta);
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
			if (Double.doubleToLongBits(alpha) != Double.doubleToLongBits(other.alpha)) {
				return false;
			}
			if (Double.doubleToLongBits(beta) != Double.doubleToLongBits(other.beta)) {
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
	 * Builder class for BetaSampleDistributionData
	 */
	public static class Builder implements SampleDistributionDataBuilder {
		private Data data;

		private Builder(Data data) {
			this.data = data;
		}

		/**
		 * Returns the BetaSampleDistributionData built from the collected parameters.
		 */
		public BetaSampleDistributionData build() {
			if (!data.locked) {
				validateData();
			}
			ensureImmutability();
			return new BetaSampleDistributionData(data);
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
		 * Sets the alpha parameter. Defaults to 1.0
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#NON_POSITIVE_PARAMETER}
		 *                           if alpha is negative</li>
		 *                           </ul>
		 * 
		 */
		public Builder setAlpha(double alpha) {
			ensureDataMutability();
			if (alpha <= 0) {
				throw new ContractException(DistributionError.NON_POSITIVE_PARAMETER, "alpha");
			}
			data.alpha = alpha;
			return this;
		}

		/**
		 * Sets the beta parameter. Defaults to 1.0
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#NON_POSITIVE_PARAMETER}
		 *                           if beta is negative</li>
		 *                           </ul>
		 * 
		 */
		public Builder setBeta(double beta) {
			ensureDataMutability();
			if (beta <= 0) {
				throw new ContractException(DistributionError.NON_POSITIVE_PARAMETER, "beta");
			}
			data.beta = beta;
			return this;
		}

	}

	private final Data data;

	private BetaSampleDistributionData(Data data) {
		this.data = data;
	}

	@Override
	public Builder getCloneBuilder() {
		return new Builder(data);
	}

	/**
	 * Returns the alpha parameter
	 */
	public double getAlpha() {
		return data.alpha;
	}

	/**
	 * Returns the beta parameter
	 */
	public double getBeta() {
		return data.beta;
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
		if (!(obj instanceof BetaSampleDistributionData)) {
			return false;
		}
		BetaSampleDistributionData other = (BetaSampleDistributionData) obj;
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
		builder2.append("BetaSampleDistributionData [data=");
		builder2.append(data);
		builder2.append("]");
		return builder2.toString();
	}

	@Override
	public BetaSampleDistribution getSampleDistribution(RandomGenerator randomGenerator) {
		return new BetaSampleDistribution(randomGenerator, this);
	}

}
