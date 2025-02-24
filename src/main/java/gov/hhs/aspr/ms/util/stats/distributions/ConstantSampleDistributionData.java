package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.random.RandomGenerator;

/**
 * SampleDistributionData implementation for constant distributions 
 */
public class ConstantSampleDistributionData implements SampleDistributionData {

	private static class Data {

		private double value = 0.0;		
		private boolean locked;

		private Data() {
		}

		private Data(Data data) {
			value = data.value;			
			locked = data.locked;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(value);
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
			if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Data [value=");
			builder.append(value);
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
	 * Builder class for ConstantSampleDistributionData
	 */
	public static class Builder implements SampleDistributionDataBuilder {
		private Data data;

		private Builder(Data data) {
			this.data = data;
		}

		/**
		 * Returns the ConstantSampleDistributionData built from the collected parameters.
		 */
		public ConstantSampleDistributionData build() {
			if (!data.locked) {
				validateData();
			}
			ensureImmutability();
			return new ConstantSampleDistributionData(data);
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
		 * Sets the value parameter. Defaults to 0.0
		 * 
		 * 
		 */
		public Builder setValue(double value) {
			ensureDataMutability();			
			data.value = value;
			return this;
		}
	}

	private final Data data;

	private ConstantSampleDistributionData(Data data) {
		this.data = data;
	}

	@Override
	public Builder getCloneBuilder() {
		return new Builder(data);
	}

	/**
	 * Returns the value parameter
	 */
	public double getValue() {
		return data.value;
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
		if (!(obj instanceof ConstantSampleDistributionData)) {
			return false;
		}
		ConstantSampleDistributionData other = (ConstantSampleDistributionData) obj;
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
		builder2.append("ConstantSampleDistributionData [data=");
		builder2.append(data);
		builder2.append("]");
		return builder2.toString();
	}

	@Override
	public ConstantSampleDistribution getSampleDistribution(RandomGenerator randomGenerator) {
		return new ConstantSampleDistribution(this);
	}
	
}
