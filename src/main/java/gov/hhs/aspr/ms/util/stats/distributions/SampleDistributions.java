package gov.hhs.aspr.ms.util.stats.distributions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import gov.hhs.aspr.ms.util.errors.ContractException;

public final class SampleDistributions {
	
	
	private SampleDistributions() {}

	private final static Map<Class<?>, Function<SampleDistributionData, String>> SAMPLE_DISTRIBUTION_CONVERSION_MAP = buildSampleDistributionConversionMap();
	private final static Map<String, Function<Map<String, String>, SampleDistributionData>> STRING_CONVERSION_MAP = buildStringConversionMap();

	private static Map<Class<?>, Function<SampleDistributionData, String>> buildSampleDistributionConversionMap() {
		Map<Class<?>, Function<SampleDistributionData, String>> result = new LinkedHashMap<>();
		result.put(BetaSampleDistributionData.class, SampleDistributions::getBetaSampleDistributionDataString);
		result.put(BinomialSampleDistributionData.class, SampleDistributions::getBinomialSampleDistributionDataString);
		result.put(ConstantSampleDistributionData.class, SampleDistributions::getConstantSampleDistributionDataString);
		result.put(ExponentialSampleDistributionData.class,
				SampleDistributions::getExponentialSampleDistributionDataString);
		result.put(GammaSampleDistributionData.class, SampleDistributions::getGammaSampleDistributionDataString);
		result.put(LogNormalSampleDistributionData.class,
				SampleDistributions::getLogNormalSampleDistributionDataString);
		result.put(NormalSampleDistributionData.class, SampleDistributions::getNormalSampleDistributionDataString);
		result.put(TruncatedNormalSampleDistributionData.class, SampleDistributions::getTruncatedNormalSampleDistributionDataString);
		result.put(UniformSampleDistributionData.class, SampleDistributions::getUniformSampleDistributionDataString);
		return result;
	}

	private static String getUniformSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		UniformSampleDistributionData uniformSampleDistributionData = (UniformSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::uniform");
		sb.append("|lower::");
		sb.append(uniformSampleDistributionData.getLower());
		sb.append("|upper::");
		sb.append(uniformSampleDistributionData.getUpper());
		return sb.toString();
	}
	
	private static String getTruncatedNormalSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		TruncatedNormalSampleDistributionData trucatedNormalSampleDistributionData = (TruncatedNormalSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::truncatednormal");
		sb.append("|mean::");
		sb.append(trucatedNormalSampleDistributionData.getMean());
		sb.append("|stdev::");
		sb.append(trucatedNormalSampleDistributionData.getStandardDeviation());
		return sb.toString();
	}

	private static String getNormalSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		NormalSampleDistributionData normalSampleDistributionData = (NormalSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::normal");
		sb.append("|mean::");
		sb.append(normalSampleDistributionData.getMean());
		sb.append("|stdev::");
		sb.append(normalSampleDistributionData.getStandardDeviation());
		return sb.toString();
	}

	private static String getLogNormalSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		LogNormalSampleDistributionData logNormalSampleDistributionData = (LogNormalSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::lognormal");
		sb.append("|shape::");
		sb.append(logNormalSampleDistributionData.getShape());
		sb.append("|scale::");
		sb.append(logNormalSampleDistributionData.getScale());
		return sb.toString();
	}

	private static String getGammaSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		GammaSampleDistributionData gammaSampleDistributionData = (GammaSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::gamma");
		sb.append("|shape::");
		sb.append(gammaSampleDistributionData.getShape());
		sb.append("|scale::");
		sb.append(gammaSampleDistributionData.getScale());
		return sb.toString();
	}

	private static String getExponentialSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		ExponentialSampleDistributionData exponentialSampleDistributionData = (ExponentialSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::exponential");
		sb.append("|mean::");
		sb.append(exponentialSampleDistributionData.getMean());
		return sb.toString();
	}

	private static String getConstantSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		ConstantSampleDistributionData constantSampleDistributionData = (ConstantSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::constant");
		sb.append("|value::");
		sb.append(constantSampleDistributionData.getValue());
		return sb.toString();
	}

	private static String getBinomialSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		BinomialSampleDistributionData binomialSampleDistributionData = (BinomialSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::binomial");
		sb.append("|probability::");
		sb.append(binomialSampleDistributionData.getProbabilityOfSuccess());
		sb.append("|trials::");
		sb.append(binomialSampleDistributionData.getTrials());
		return sb.toString();
	}

	private static String getBetaSampleDistributionDataString(SampleDistributionData sampleDistributionData) {
		BetaSampleDistributionData betaSampleDistributionData = (BetaSampleDistributionData) sampleDistributionData;
		StringBuilder sb = new StringBuilder();
		sb.append("type::beta");
		sb.append("|alpha::");
		sb.append(betaSampleDistributionData.getAlpha());
		sb.append("|beta::");
		sb.append(betaSampleDistributionData.getBeta());
		return sb.toString();
	}

	private static Map<String, Function<Map<String, String>, SampleDistributionData>> buildStringConversionMap() {
		Map<String, Function<Map<String, String>, SampleDistributionData>> result = new LinkedHashMap<>();
		result.put("beta", SampleDistributions::getBetaSampleDistributionData);
		result.put("binomial", SampleDistributions::getBinomialSampleDistributionData);
		result.put("constant", SampleDistributions::getConstantSampleDistributionData);
		result.put("exponential", SampleDistributions::getExponentialSampleDistributionData);
		result.put("gamma", SampleDistributions::getGammaSampleDistributionData);
		result.put("lognormal", SampleDistributions::getLogNormalSampleDistributionData);
		result.put("normal", SampleDistributions::getNormalSampleDistributionData);
		result.put("uniform", SampleDistributions::getUniformSampleDistributionData);
		result.put("truncatednormal", SampleDistributions::getTruncatedNormalSampleDistributionData);
		return result;
	};

	private static UniformSampleDistributionData getUniformSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Uniform distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Uniform.lower";
		String lowerString = argMap.get("lower");
		if (lowerString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double lower = parseDouble(lowerString, errorMessage);
		errorMessage = "Uniform.upper";
		String upperString = argMap.get("upper");
		if (upperString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double upper = parseDouble(upperString, errorMessage);
		return UniformSampleDistributionData.builder().setLower(lower).setUpper(upper).build();
	}

	private static NormalSampleDistributionData getNormalSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Normal distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Normal.mean";
		String meanString = argMap.get("mean");
		if (meanString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double mean = parseDouble(meanString, errorMessage);
		errorMessage = "Normal.stdev";
		String standardDeviationString = argMap.get("stdev");
		if (standardDeviationString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double standardDeviation = parseDouble(standardDeviationString, errorMessage);
		return NormalSampleDistributionData.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
	}
	
	private static TruncatedNormalSampleDistributionData getTruncatedNormalSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Truncated normal distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Truncatednormal.mean";
		String meanString = argMap.get("mean");
		if (meanString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double mean = parseDouble(meanString, errorMessage);
		errorMessage = "Truncatednormal.stdev";
		String standardDeviationString = argMap.get("stdev");
		if (standardDeviationString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double standardDeviation = parseDouble(standardDeviationString, errorMessage);
		return TruncatedNormalSampleDistributionData.builder().setMean(mean).setStandardDeviation(standardDeviation).build();
	}


	private static LogNormalSampleDistributionData getLogNormalSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Log normal distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "LogNormal.shape";
		String shapeString = argMap.get("shape");
		if (shapeString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double shape = parseDouble(shapeString, errorMessage);
		errorMessage = "LogNormal.scale";
		String scaleString = argMap.get("scale");
		if (scaleString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double scale = parseDouble(scaleString, errorMessage);
		return LogNormalSampleDistributionData.builder().setShape(shape).setScale(scale).build();
	}

	private static GammaSampleDistributionData getGammaSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Gamma distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Gamma.shape";
		String shapeString = argMap.get("shape");
		if (shapeString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double shape = parseDouble(shapeString, errorMessage);
		errorMessage = "Gamma.scale";
		String scaleString = argMap.get("scale");
		if (scaleString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double scale = parseDouble(scaleString, errorMessage);
		return GammaSampleDistributionData.builder().setShape(shape).setScale(scale).build();
	}

	private static double parseDouble(String value, String errorMessage) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new ContractException(DistributionError.INVALID_DOUBLE_VALUE, errorMessage);
		}
	}

	private static int parseInt(String value, String errorMessage) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new ContractException(DistributionError.INVALID_INTEGER_VALUE, errorMessage);
		}
	}

	private static ExponentialSampleDistributionData getExponentialSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Exponential distribution requires two arguments";
		if (argMap.size() != 2) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Exponential.mean";
		String meanString = argMap.get("mean");
		if (meanString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double mean = parseDouble(meanString, errorMessage);

		return ExponentialSampleDistributionData.builder().setMean(mean).build();
	}

	private static ConstantSampleDistributionData getConstantSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Constant distribution requires two arguments";
		if (argMap.size() != 2) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Constant.value";
		String valueString = argMap.get("value");
		if (valueString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double value = parseDouble(valueString, errorMessage);

		return ConstantSampleDistributionData.builder().setValue(value).build();
	}

	private static BetaSampleDistributionData getBetaSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Beta distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}
		errorMessage = "Beta.alpha";
		String alphaString = argMap.get("alpha");
		if (alphaString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double alpha = parseDouble(alphaString, errorMessage);
		errorMessage = "Beta.beta";
		String betaString = argMap.get("beta");
		if (betaString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double beta = parseDouble(betaString, errorMessage);
		return BetaSampleDistributionData.builder().setAlpha(alpha).setBeta(beta).build();
	}

	private static BinomialSampleDistributionData getBinomialSampleDistributionData(Map<String, String> argMap) {
		String errorMessage = "Binomial distribution requires three arguments";
		if (argMap.size() != 3) {
			throw new ContractException(DistributionError.IMPROPER_ARGUMENT_COUNT, errorMessage);
		}

		errorMessage = "Binomial.probability";
		String probabilityString = argMap.get("probability");
		if (probabilityString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		double probabilityOfSuccess = parseDouble(probabilityString, errorMessage);
		errorMessage = "Binomial.trials";
		String trialsString = argMap.get("trials");
		if (trialsString == null) {
			throw new ContractException(DistributionError.MISSING_DISTRIBUTION_ARGUMENT, errorMessage);
		}
		int trials = parseInt(trialsString, errorMessage);
		return BinomialSampleDistributionData.builder().setProbabilityOfSuccess(probabilityOfSuccess).setTrials(trials)
				.build();
	}

	/**
	 * Returns a SampleDistributionData instance matching the given string
	 * specification.
	 * 
	 * <P>
	 * String specification examples:
	 * 
	 * <ul>
	 * <li>"type::binomial|probability::0.5|trials::45"</li>
	 * 
	 * <li>"type::beta|alpha::40.5|beta::545"</li>
	 * 
	 * <li>"type::constant|value::10"</li>
	 * 
	 * <li>"type::exponential|mean::15"</li>
	 * 
	 * <li>"type::gamma|shape::2.4|scale::3.7"</li>
	 * 
	 * <li>"type::lognormal|shape :: 2.4|scale::3.7"</li>
	 * 
	 * <li>"type::normal|mean::5.6|stdev::1.2"</li>
	 * 
	 *  <li>"type::truncatednormal|mean::5.6|stdev::1.2"</li>
	 * 
	 * <li>"type::uniform|lower::5|upper::14"</li>
	 * </ul>
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_STRING}
	 *                           if sampleDistributionSpecification is null</li>
	 * 
	 *                           <li>{@linkplain DistributionError#IMPROPER_ARGUMENT_COUNT}
	 *                           if a distribution string has the wrong number of
	 *                           arguments</li>
	 * 
	 *                           <li>{@linkplain DistributionError#INVALID_INTEGER_VALUE}
	 *                           if a string cannot be parsed as an int value</li>
	 * 
	 *                           <li>{@linkplain DistributionError#INVALID_DOUBLE_VALUE}
	 *                           if a string cannot be parsed as a double value</li>
	 * 
	 *                           <li>{@linkplain DistributionError#MISSING_DISTRIBUTION_ARGUMENT}
	 *                           if an argument is missing</li>
	 * 
	 *                           <li>{@linkplain DistributionError#UNKNOWN_DISTRIBUTION_TYPE_NAME}
	 *                           if the type name is unrecognized</li>
	 * 
	 *                           <li>{@linkplain DistributionError#DUPLICATE_DISTRIBUTION_VALUE_ASSIGNMENT}
	 *                           if an argument is duplicated</li>
	 * 
	 *                           <li>{@linkplain DistributionError#INVALID_DISTRIBUTION_VALUE_ASSIGNMENT}
	 *                           if an argument is not a name-value pair</li>
	 * 
	 *                           <li>{@linkplain DistributionError#LOWER_EXCEEDS_UPPER}
	 *                           if the lower argument value exceeds the upper
	 *                           argument value in a uniform distribution</li>
	 * 
	 *                           <li>{@linkplain DistributionError#NON_POSITIVE_PARAMETER}
	 *                           if any arguments for the beta, exponential, gamma,
	 *                           lognormal and normal.stdev are not positive</li>
	 * 
	 *                           <li>{@linkplain DistributionError#PARAMETER_LESS_THAN_0}
	 *                           if any arguments for the binomial are negative</li>
	 * 
	 *                           <li>{@linkplain DistributionError#PARAMETER_EXCEEDS_1}
	 *                           if binomial.probabilty is greater than one</li>
	 * 
	 *                           </ul>
	 * 
	 */

	@SuppressWarnings("unchecked")
	public static <T extends SampleDistributionData> T fromString(String sampleDistributionSpecification) {
		if (sampleDistributionSpecification == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_STRING);
		}

		Map<String, String> argMap = new LinkedHashMap<>();
		String[] split = sampleDistributionSpecification.split("\\|");

		for (String arg : split) {
			String[] subSplit = arg.strip().split("::");
			if (subSplit.length != 2) {
				throw new ContractException(DistributionError.INVALID_DISTRIBUTION_VALUE_ASSIGNMENT);
			}
			String replacedValue = argMap.put(subSplit[0].strip().toLowerCase(), subSplit[1].strip().toLowerCase());
			if (replacedValue != null) {
				throw new ContractException(DistributionError.DUPLICATE_DISTRIBUTION_VALUE_ASSIGNMENT);
			}
		}

		String type = argMap.get("type");
		Function<Map<String, String>, SampleDistributionData> converter = STRING_CONVERSION_MAP.get(type);
		if (converter == null) {
			throw new ContractException(DistributionError.UNKNOWN_DISTRIBUTION_TYPE_NAME);
		}
		SampleDistributionData sampleDistributionData = converter.apply(argMap);
		return (T) sampleDistributionData;
	}

	/**
	 * Returns the string equivalent for the given SampleDistributionData used for
	 * serialization.
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain DistributionError#NULL_SAMPLE_DISTRIBUTION_DATA}
	 *                           if the sampleDistributionData is null</li>
	 *                           <li>{@linkplain DistributionError#UNSUPPORTED_DISTRIBUTION_CONVERSION}
	 *                           if conversion of the sampleDistributionData is not
	 *                           supported</li>
	 *                           </ul>
	 */
	public static String toString(SampleDistributionData sampleDistributionData) {
		if (sampleDistributionData == null) {
			throw new ContractException(DistributionError.NULL_SAMPLE_DISTRIBUTION_DATA);
		}
		Function<SampleDistributionData, String> converter = SAMPLE_DISTRIBUTION_CONVERSION_MAP
				.get(sampleDistributionData.getClass());
		if (converter == null) {
			throw new ContractException(DistributionError.UNSUPPORTED_DISTRIBUTION_CONVERSION);
		}
		return converter.apply(sampleDistributionData);
	}

}
