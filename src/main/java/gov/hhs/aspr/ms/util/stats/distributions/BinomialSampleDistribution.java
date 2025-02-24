package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a binomial distribution that wraps an
 * org.apache.commons.math3.distribution.BinomialDistribution
 */
public final class BinomialSampleDistribution implements SampleDistribution {

	private final BinomialDistribution distribution;

	private final BinomialSampleDistributionData binomialSampleDistributionData;

	/**
	 * Creates a new BinomialSampleDistribution from a random generator and a
	 * BinomialSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public BinomialSampleDistribution(RandomGenerator randomGenerator,
			BinomialSampleDistributionData binomialSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (binomialSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.binomialSampleDistributionData = binomialSampleDistributionData;
		distribution = new BinomialDistribution(randomGenerator, binomialSampleDistributionData.getTrials(),
				binomialSampleDistributionData.getProbabilityOfSuccess());
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
	public BinomialSampleDistributionData getSampleDistributionData() {
		return binomialSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((binomialSampleDistributionData == null) ? 0 : binomialSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BinomialSampleDistribution)) {
			return false;
		}
		BinomialSampleDistribution other = (BinomialSampleDistribution) obj;
		if (binomialSampleDistributionData == null) {
			if (other.binomialSampleDistributionData != null) {
				return false;
			}
		} else if (!binomialSampleDistributionData.equals(other.binomialSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BinomialSampleDistribution [binomialSampleDistributionData=");
		builder.append(binomialSampleDistributionData);
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
		return true;
	}

	@Override
	public double getSupportUpperBound() {
		return distribution.getSupportUpperBound();
	}

	@Override
	public boolean isSupportUpperBoundInclusive() {		
		return true;
	}	

}
