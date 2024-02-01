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

public class AT_Indexer3 {
	@Test
	@UnitTestConstructor(target = Indexer3.class, args = { int.class, int.class, int.class })
	public void testIndexer3() {

		// precondition test: if any dimension has a negative size

		ContractException contractException = assertThrows(ContractException.class, () -> new Indexer3(-1, 1,1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());

		contractException = assertThrows(ContractException.class, () -> new Indexer3(1, -1,1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());
		
		contractException = assertThrows(ContractException.class, () -> new Indexer3(1, 1,-1));
		assertEquals(IndexerError.NEGATIVE_DIMENSION_SIZE, contractException.getErrorType());


		// precondition test: if the product of the dimensions exceeds max int
		int n = (int) FastMath.pow(Integer.MAX_VALUE, 1.0 / 3) + 1;

		contractException = assertThrows(ContractException.class, () -> new Indexer3(n, n, n));
		assertEquals(IndexerError.EXCEEDS_MAX_ARRAY_SIZE, contractException.getErrorType());

	}

	@Test
	@UnitTestMethod(target = Indexer3.class, name = "index", args = { int.class, int.class, int.class })
	public void testIndex() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(6937151566492160140L);

		//show that index =  (index1*dim2 +index2)*dim3+index3
		
		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;
			int dim3 = randomGenerator.nextInt(30) + 1;

			Indexer3 indexer3 = new Indexer3(dim1, dim2,dim3);

			for (int j = 0; j < 100; j++) {
				
				int index1 = randomGenerator.nextInt(dim1);				
				int index2 = randomGenerator.nextInt(dim2);
				int index3 = randomGenerator.nextInt(dim3);
				
				assertEquals((index1*dim2 +index2)*dim3+index3, indexer3.index(index1, index2,index3));
			}
		}
	}

	@Test
	@UnitTestMethod(target = Indexer3.class, name = "size", args = {})
	public void testSize() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1286993379829775736L);
		
		for (int i = 0; i < 30; i++) {
			int dim1 = randomGenerator.nextInt(30) + 1;
			int dim2 = randomGenerator.nextInt(30) + 1;
			int dim3 = randomGenerator.nextInt(30) + 1;

			Indexer3 indexer3 = new Indexer3(dim1, dim2,dim3);

			assertEquals(dim1*dim2*dim3, indexer3.size());
		}
	}
}
