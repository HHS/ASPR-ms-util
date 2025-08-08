package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.ConstantRealDistribution;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a constant distribution that wraps an
 * org.apache.commons.math3.distribution.ConstantRealDistribution
 */
public final class ConstantSampleDistribution implements SampleDistribution {

	private final ConstantRealDistribution distribution;

	private final ConstantSampleDistributionData constantSampleDistributionData;

	/**
	 * Creates a new ConstantSampleDistribution from a
	 * ConstantSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public ConstantSampleDistribution(ConstantSampleDistributionData constantSampleDistributionData) {
		if (constantSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.constantSampleDistributionData = constantSampleDistributionData;
		distribution = new ConstantRealDistribution(constantSampleDistributionData.getValue());
	}

	@Override
	public double sample() {
		return distribution.sample();
	}

	@Override
	public double getNumericalMean() {
		return distribution.getNumericalMean();
	}

	@Override
	public ConstantSampleDistributionData getSampleDistributionData() {
		return constantSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((constantSampleDistributionData == null) ? 0 : constantSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ConstantSampleDistribution)) {
			return false;
		}
		ConstantSampleDistribution other = (ConstantSampleDistribution) obj;
		if (constantSampleDistributionData == null) {
			if (other.constantSampleDistributionData != null) {
				return false;
			}
		} else if (!constantSampleDistributionData.equals(other.constantSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConstantSampleDistribution [constantSampleDistributionData=");
		builder.append(constantSampleDistributionData);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public double getNumericalVariance() {
		return distribution.getNumericalVariance();
	}
	
	@Override
	public double getSupportLowerBound() {		
		return distribution.getSupportLowerBound();
	}

	@Override
	public boolean isSupportLowerBoundInclusive() {		
		return distribution.isSupportLowerBoundInclusive();
	}

	@Override
	public double getSupportUpperBound() {
		return distribution.getSupportUpperBound();
	}

	@Override
	public boolean isSupportUpperBoundInclusive() {		
		return distribution.isSupportUpperBoundInclusive();
	}	

	
}
