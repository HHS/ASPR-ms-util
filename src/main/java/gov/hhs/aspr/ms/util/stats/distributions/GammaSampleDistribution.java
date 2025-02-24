package gov.hhs.aspr.ms.util.stats.distributions;

import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.random.RandomGenerator;

import gov.hhs.aspr.ms.util.errors.ContractException;

/**
 * SampleDistribution implementation of a gamma distribution that wraps an
 * org.apache.commons.math3.distribution.GammaDistribution
 */
public final class GammaSampleDistribution implements SampleDistribution {

	private final GammaDistribution distribution;

	private final GammaSampleDistributionData gamaSampleDistributionData;

	/**
	 * Creates a new GamaSampleDistribution from a random generator and a
	 * GamaSampleDistributionData
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_RNG} if the
	 *                           random generator is null</li>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sample distribution data is null</li>
	 *                           </ul>
	 */
	public GammaSampleDistribution(RandomGenerator randomGenerator,
			GammaSampleDistributionData gamaSampleDistributionData) {
		if (randomGenerator == null) {
			throw new ContractException(DistributionError.NULL_RNG);
		}
		if (gamaSampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		this.gamaSampleDistributionData = gamaSampleDistributionData;
		distribution = new GammaDistribution(randomGenerator, gamaSampleDistributionData.getShape(),
				gamaSampleDistributionData.getScale());
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
	public GammaSampleDistributionData getSampleDistributionData() {
		return gamaSampleDistributionData;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((gamaSampleDistributionData == null) ? 0 : gamaSampleDistributionData.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof GammaSampleDistribution)) {
			return false;
		}
		GammaSampleDistribution other = (GammaSampleDistribution) obj;
		if (gamaSampleDistributionData == null) {
			if (other.gamaSampleDistributionData != null) {
				return false;
			}
		} else if (!gamaSampleDistributionData.equals(other.gamaSampleDistributionData)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GammaSampleDistribution [gamaSampleDistributionData=");
		builder.append(gamaSampleDistributionData);
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
