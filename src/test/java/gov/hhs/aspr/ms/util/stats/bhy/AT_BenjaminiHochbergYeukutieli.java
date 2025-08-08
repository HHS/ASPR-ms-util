package gov.hhs.aspr.ms.util.stats.bhy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTag;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.stats.bhy.BenjaminiHochbergYeukutieli.Mode;

public class AT_BenjaminiHochbergYeukutieli {

	@Test
	@UnitTestMethod(target = BenjaminiHochbergYeukutieli.class, name = "getFailedLabels", args = { Map.class,
			double.class, Mode.class }, tags = { UnitTag.INCOMPLETE })
	public void testGetFailedLabels() {
		//postcondition tests: INCOMPLETE
		
		
		//support for precondition tests
		Map<String, Double> labeledPValues = new LinkedHashMap<>();
		labeledPValues.put("a", 0.3);
		double significance = 0.5;
		Mode mode = Mode.BH;

		// precondition test: if the labeledPValues map is null
		ContractException contractException = assertThrows(ContractException.class,
				() -> BenjaminiHochbergYeukutieli.getFailedLabels(null, significance, mode));

		assertEquals(BHYError.NULL_MAP, contractException.getErrorType());

		// precondition test: if the mode is null
		contractException = assertThrows(ContractException.class,
				() -> BenjaminiHochbergYeukutieli.getFailedLabels(labeledPValues, significance, null));

		assertEquals(BHYError.NULL_MODE, contractException.getErrorType());

		// precondition test: if the labeledPValues map contains a null label
		contractException = assertThrows(ContractException.class, () -> {
			Map<String, Double> labelMap = new LinkedHashMap<>();
			labelMap.put(null, 0.3);
			labelMap.put("x", 0.5);
			BenjaminiHochbergYeukutieli.getFailedLabels(labelMap, significance, mode);
		});

		assertEquals(BHYError.NULL_LABEL, contractException.getErrorType());

		
		// precondition test: if the labeledPValues map contains a null p-value
		contractException = assertThrows(ContractException.class, () -> {
			Map<String, Double> labelMap = new LinkedHashMap<>();
			labelMap.put("a", 0.3);
			labelMap.put("b", null);
			BenjaminiHochbergYeukutieli.getFailedLabels(labelMap, significance, mode);
		});

		assertEquals(BHYError.NULL_P_VALUE, contractException.getErrorType());
		
		// precondition test: if the labeledPValues map is empty
		contractException = assertThrows(ContractException.class, () -> {
			Map<String, Double> labelMap = new LinkedHashMap<>();			
			BenjaminiHochbergYeukutieli.getFailedLabels(labelMap, significance, mode);
		});

		assertEquals(BHYError.EMPTY_LABELS, contractException.getErrorType());


		// precondition test: if the significance is negative
		contractException = assertThrows(ContractException.class, () -> {						
			BenjaminiHochbergYeukutieli.getFailedLabels(labeledPValues, -0.4, mode);
		});

		assertEquals(BHYError.NEGATIVE_SIGNIFICANCE, contractException.getErrorType());
		
		
		// precondition test: if the significance exceeds 1
		contractException = assertThrows(ContractException.class, () -> {						
			BenjaminiHochbergYeukutieli.getFailedLabels(labeledPValues, 1.2, mode);
		});

		assertEquals(BHYError.EXCESS_SIGNIFICANCE, contractException.getErrorType());
		
		
		// precondition test: if a p-value is negative
		contractException = assertThrows(ContractException.class, () -> {
			Map<String, Double> labelMap = new LinkedHashMap<>();
			labelMap.put("a", 0.3);
			labelMap.put("b", -0.1);
			BenjaminiHochbergYeukutieli.getFailedLabels(labelMap, significance, mode);
		});

		assertEquals(BHYError.NEGATIVE_P_VALUE, contractException.getErrorType());
		
		// precondition test: if a p-value exceeds 1
		contractException = assertThrows(ContractException.class, () -> {
			Map<String, Double> labelMap = new LinkedHashMap<>();
			labelMap.put("a", 0.3);
			labelMap.put("b", 1.1);
			BenjaminiHochbergYeukutieli.getFailedLabels(labelMap, significance, mode);
		});

		assertEquals(BHYError.EXCESS_P_VALUE, contractException.getErrorType());
		
	}

}
