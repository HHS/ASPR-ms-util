package util.indexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.util.FastMath;
import org.junit.jupiter.api.Test;

import util.annotations.UnitTestConstructor;
import util.annotations.UnitTestMethod;
import util.errors.ContractException;
import util.random.RandomGeneratorProvider;

public class AT_Indexer4 {
	@Test
	@UnitTestConstructor(target = Indexer4.class, args = { int.class, int.class, int.class, int.class })
	public void testIndexer4() {

		// precondition test: if any dimension has a negative size

		ContractException contractException = assertThrows(ContractException.class, () -> new Indexer4(-1, 1, 1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer4(1, -1, 1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer4(1, 1, -1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer4(1, 1, 1, -1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		// precondition test: if the product of the dimensions exceeds max int
		int n = (int) FastMath.pow(Integer.MAX_VALUE, 1.0 / 4) + 1;

		contractException = assertThrows(ContractException.class, () -> new Indexer4(n, n, n, n));
		assertEquals(IndexerError.EXCEEDS_MAX_ARRAY_SIZE, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Indexer4.class, name = "index", args = { int.class, int.class, int.class, int.class })
	public void testIndex() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6937151566492160140L);

		// show that index = ((index1 * dim2 + index2) * dim3 + index3) * dim4 + index4

		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;
			int dim3 = randomGenerator.nextInt(30) + 1;
			int dim4 = randomGenerator.nextInt(30) + 1;

			Indexer4 indexer4 = new Indexer4(dim1, dim2, dim3, dim4);

			for (int j = 0; j < 100; j++) {

				int index1 = randomGenerator.nextInt(dim1);
				int index2 = randomGenerator.nextInt(dim2);
				int index3 = randomGenerator.nextInt(dim3);
				int index4 = randomGenerator.nextInt(dim4);

				assertEquals(((index1 * dim2 + index2) * dim3 + index3) * dim4 + index4,
						indexer4.index(index1, index2, index3, index4));
			}
		}
	}

	@Test
	@UnitTestMethod(target = Indexer4.class, name = "size", args = { })
	public void testSize() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1286993379829775736L);

		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;
			int dim3 = randomGenerator.nextInt(30) + 1;
			int dim4 = randomGenerator.nextInt(30) + 1;

			Indexer4 Indexer4 = new Indexer4(dim1, dim2, dim3, dim4);

			assertEquals(dim1 * dim2 * dim3 * dim4, Indexer4.size());
		}
	}
}
