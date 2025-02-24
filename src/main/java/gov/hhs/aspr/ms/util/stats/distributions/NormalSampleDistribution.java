package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a normal distribution that wraps an
 * org.apache.commons.math3.distribution.NormalDistribution
 */
public final class NormalSampleDistribution implements SampleDistribution {

	private final NormalDistribution distribution;

	private final NormalSampleDistributionData normalSampleDistributionData;

	/**
	 * Creates a new NormalSampleDistribution from a random generator and a
	 * NormalSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>                        
	 *                           
	 *                           </ul>
	 */
	public NormalSampleDistribution(RandomGenerator randomGenerator,
			NormalSampleDistributionData normalSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (normalSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.normalSampleDistributionData = normalSampleDistributionData;
		distribution = new NormalDistribution(randomGenerator, normalSampleDistributionData.getMean(),
				normalSampleDistributionData.getStandardDeviation());
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
	public NormalSampleDistributionData getSampleDistributionData() {
		return normalSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((normalSampleDistributionData == null) ? 0 : normalSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof NormalSampleDistribution)) {
			return false;
		}
		NormalSampleDistribution other = (NormalSampleDistribution) obj;
		if (normalSampleDistributionData == null) {
			if (other.normalSampleDistributionData != null) {
				return false;
			}
		} else if (!normalSampleDistributionData.equals(other.normalSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NormalSampleDistribution [normalSampleDistributionData=");
		builder.append(normalSampleDistributionData);
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
