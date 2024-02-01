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

public class AT_Indexer2 {
	@Test
	@UnitTestConstructor(target = Indexer2.class, args = { int.class, int.class })
	public void testIndexer2() {

		// precondition test: if any dimension has a negative size

		ContractException contractException = assertThrows(ContractException.class, () -> new Indexer2(-1, 1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer2(1, -1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		// precondition test: if the product of the dimensions exceeds max int
		int n = (int) FastMath.pow(Integer.MAX_VALUE, 1.0 / 2) + 1;

		contractException = assertThrows(ContractException.class, () -> new Indexer2(n, n));
		assertEquals(IndexerError.EXCEEDS_MAX_ARRAY_SIZE, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Indexer2.class, name = "index", args = { int.class, int.class })
	public void testIndex() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6937151566492160140L);

		//show that index =  index1*dim2+index2
		
		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;

			Indexer2 indexer2 = new Indexer2(dim1, dim2);

			for (int j = 0; j < 100; j++) {
				
				int index1 = randomGenerator.nextInt(dim1);				
				int index2 = randomGenerator.nextInt(dim2);
				
				assertEquals(index1 * dim2 + index2, indexer2.index(index1, index2));
			}
		}
	}

	@Test
	@UnitTestMethod(target = Indexer2.class, name = "size", args = {})
	public void testSize() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1286993379829775736L);
		
		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;

			Indexer2 indexer2 = new Indexer2(dim1, dim2);

			assertEquals(dim1*dim2, indexer2.size());
		}
	}
}
