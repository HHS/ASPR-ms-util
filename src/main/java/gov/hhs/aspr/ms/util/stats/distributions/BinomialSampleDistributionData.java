package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;
/**
 * SampleDistributionData implementation for binomial distributions 
 */
public class BinomialSampleDistributionData implements SampleDistributionData {

	private static class Data {

		private int trials = 0;
		private double probabilityOfSuccess = 0.5;
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			trials = data.trials;
			probabilityOfSuccess = data.probabilityOfSuccess;
			locked = data.locked;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(probabilityOfSuccess);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + trials;
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
			if (Double.doubleToLongBits(probabilityOfSuccess) != Double.doubleToLongBits(other.probabilityOfSuccess)) {
				return false;
			}
			if (trials != other.trials) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Data [trials=");
			builder.append(trials);
			builder.append(", probabilityOfSuccess=");
			builder.append(probabilityOfSuccess);
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
	 * Builder class for BinomialSampleDistributionData
	 */
	public static class Builder implements SampleDistributionDataBuilder {
		private Data data;

		private Builder(Data data) {
			this.data = data;
		}

		/**
		 * Returns the BinomialSampleDistributionData built from the collected
		 * parameters
		 */
		public BinomialSampleDistributionData build() {
			if (!data.locked) {
				validateData();
			}
			ensureImmutability();
			return new BinomialSampleDistributionData(data);
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
		 * Sets the probabiltyOfSuccess parameter. Defaults to 0.5
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#PARAMETER_LESS_THAN_0}
		 *                           if probabilityOfSuccess is negative</li>
		 *                           <li>{@linkplain DistributionError#PARAMETER_EXCEEDS_1}
		 *                           if probabilityOfSuccess exceeds 1</li>
		 *                           </ul>
		 * 
		 */
		public Builder setProbabilityOfSuccess(double probabilityOfSuccess) {
			ensureDataMutability();

			if (probabilityOfSuccess < 0) {
				throw new ContractException(DistributionError.PARAMETER_LESS_THAN_0,"probabiltyOfSuccess");
			}
			if (probabilityOfSuccess > 1) {
				throw new ContractException(DistributionError.PARAMETER_EXCEEDS_1,"probabiltyOfSuccess");
			}
			data.probabilityOfSuccess = probabilityOfSuccess;
			return this;
		}

		/**
		 * Sets the trials parameter. Defaults to 0
		 * 
		 * @throws ContractException
		 *                           <ul>
		 *                           <li>{@linkplain DistributionError#PARAMETER_LESS_THAN_0}
		 *                           if trials is negative</li>
		 *                           </ul>
		 * 
		 */
		public Builder setTrials(int trials) {
			ensureDataMutability();
			if (trials < 0) {
				throw new ContractException(DistributionError.PARAMETER_LESS_THAN_0, "trials");
			}
			data.trials = trials;
			return this;
		}

	}

	private final Data data;

	private BinomialSampleDistributionData(Data data) {
		this.data = data;
	}

	@Override
	public Builder getCloneBuilder() {
		return new Builder(data);
	}

	/**
	 * Returns the probability of success parameter
	 */
	public double getProbabilityOfSuccess() {
		return data.probabilityOfSuccess;
	}

	public int getTrials() {
		return data.trials;
	}

	@Override
	public String toString() {
		StringBuilder builder2 = new StringBuilder();
		builder2.append("BinomialSampleDistributionData [data=");
		builder2.append(data);
		builder2.append("]");
		return builder2.toString();
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
		if (!(obj instanceof BinomialSampleDistributionData)) {
			return false;
		}
		BinomialSampleDistributionData other = (BinomialSampleDistributionData) obj;
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
	public BinomialSampleDistribution getSampleDistribution(RandomGenerator randomGenerator) {		
		return new BinomialSampleDistribution(randomGenerator, this);
	}

}
