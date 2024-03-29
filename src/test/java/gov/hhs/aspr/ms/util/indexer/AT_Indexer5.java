package gov.hhs.aspr.ms.util.indexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestConstructor;
import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.errors.ContractException;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_Indexer5 {
	@Test
	@UnitTestConstructor(target = Indexer5.class, args = { int.class, int.class, int.class, int.class, int.class })
	public void testIndexer5() {

		// precondition test: if any dimension has a negative size

		ContractException contractException = assertThrows(ContractException.class, () -> new Indexer5(-1, 1, 1, 1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer5(1, -1, 1, 1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer5(1, 1, -1, 1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer5(1, 1, 1, -1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer5(1, 1, 1, 1, -1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		// precondition test: if the product of the dimensions exceeds max int
		int n = (int) FastMath.pow(Integer.MAX_VALUE, 1.0 / 5) + 1;

		contractException = assertThrows(ContractException.class, () -> new Indexer5(n, n, n, n, n));
		assertEquals(IndexerError.EXCEEDS_MAX_ARRAY_SIZE, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Indexer5.class, name = "index", args = { int.class, int.class, int.class, int.class,
			int.class })
	public void testIndex() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6937151566492160140L);

		// show that index = (((index1*dim2
		// +index2)*dim3+index3)*dim4+index4)*dim5+index5

		int n = 30;

		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(n) + 1;
			int dim2 = randomGenerator.nextInt(n) + 1;
			int dim3 = randomGenerator.nextInt(n) + 1;
			int dim4 = randomGenerator.nextInt(n) + 1;
			int dim5 = randomGenerator.nextInt(n) + 1;

			Indexer5 indexer5 = new Indexer5(dim1, dim2, dim3, dim4, dim5);

			for (int j = 0; j < 100; j++) {

				int index1 = randomGenerator.nextInt(dim1);
				int index2 = randomGenerator.nextInt(dim2);
				int index3 = randomGenerator.nextInt(dim3);
				int index4 = randomGenerator.nextInt(dim4);
				int index5 = randomGenerator.nextInt(dim5);

				int expectedIndex = (((index1 * dim2 + index2) * dim3 + index3) * dim4 + index4) * dim5 + index5;
				int actualIndex = indexer5.index(index1, index2, index3, index4, index5);

				assertEquals(expectedIndex, actualIndex);
			}
		}
	}

	@Test
	@UnitTestMethod(target = Indexer5.class, name = "size", args = {})
	public void testSize() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1286993379829775736L);

		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;
			int dim3 = randomGenerator.nextInt(30) + 1;
			int dim4 = randomGenerator.nextInt(30) + 1;
			int dim5 = randomGenerator.nextInt(30) + 1;

			Indexer5 Indexer5 = new Indexer5(dim1, dim2, dim3, dim4, dim5);

			assertEquals(dim1 * dim2 * dim3 * dim4 * dim5, Indexer5.size());
		}
	}
}
