package gov.hhs.aspr.ms.util.combinatorics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.random.RandomGenerator;
import org.junit.jupiter.api.Test;

import gov.hhs.aspr.ms.util.annotations.UnitTestMethod;
import gov.hhs.aspr.ms.util.random.RandomGeneratorProvider;

public class AT_TupleGenerator {

	/**
	 * Tests {@link TupleGenerator#size()}
	 */
	@Test
	@UnitTestMethod(target = TupleGenerator.class, name = "size", args = {})
	public void testSize() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7820715406750309229L);
		for (int i = 0; i < 100; i++) {
			TupleGenerator.Builder builder = TupleGenerator.builder();
			int dimensionCount = randomGenerator.nextInt(4) + 1;
			int expectedSize = 1;
			for (int j = 0; j < dimensionCount; j++) {
				int dimSize = randomGenerator.nextInt(10) + 1;
				expectedSize *= dimSize;
				builder.addDimension(dimSize);
			}
			int actualSize = builder.build().size();
			assertEquals(expectedSize, actualSize);
		}
	}

	/**
	 * Tests {@link TupleGenerator#builder()}
	 */
	@Test
	@UnitTestMethod(target = TupleGenerator.class, name = "builder", args = {})
	public void testBuilder() {
		// covered by other tests
	}

	/**
	 * Tests {@link TupleGenerator#dimensions()}
	 */
	@Test
	@UnitTestMethod(target = TupleGenerator.class, name = "dimensions", args = {})
	public void testDimensions() {

		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(7661626069466374878L);
		for (int i = 0; i < 100; i++) {
			TupleGenerator.Builder builder = TupleGenerator.builder();
			int dimensionCount = randomGenerator.nextInt(4) + 1;
			for (int j = 0; j < dimensionCount; j++) {
				int dimSize = randomGenerator.nextInt(10) + 1;
				builder.addDimension(dimSize);
			}
			int actualDimensionCount = builder.build().dimensions();
			assertEquals(dimensionCount, actualDimensionCount);
		}
	}

	/**
	 * Tests {@link TupleGenerator#fillTuple(int, int[])}
	 */
	@Test
	@UnitTestMethod(target = TupleGenerator.class, name = "fillTuple", args = { int.class, int[].class })
	public void testFillTuple() {

		TupleGenerator tupleGenerator = TupleGenerator.builder().addDimension(2).addDimension(3).addDimension(5).build();

		int[] tuple = new int[tupleGenerator.dimensions()];

		List<int[]> expectedArrays = new ArrayList<>();

		expectedArrays.add(new int[] { 0, 0, 0 });
		expectedArrays.add(new int[] { 1, 0, 0 });
		expectedArrays.add(new int[] { 0, 1, 0 });
		expectedArrays.add(new int[] { 1, 1, 0 });
		expectedArrays.add(new int[] { 0, 2, 0 });
		expectedArrays.add(new int[] { 1, 2, 0 });
		expectedArrays.add(new int[] { 0, 0, 1 });
		expectedArrays.add(new int[] { 1, 0, 1 });
		expectedArrays.add(new int[] { 0, 1, 1 });
		expectedArrays.add(new int[] { 1, 1, 1 });
		expectedArrays.add(new int[] { 0, 2, 1 });
		expectedArrays.add(new int[] { 1, 2, 1 });
		expectedArrays.add(new int[] { 0, 0, 2 });
		expectedArrays.add(new int[] { 1, 0, 2 });
		expectedArrays.add(new int[] { 0, 1, 2 });
		expectedArrays.add(new int[] { 1, 1, 2 });
		expectedArrays.add(new int[] { 0, 2, 2 });
		expectedArrays.add(new int[] { 1, 2, 2 });
		expectedArrays.add(new int[] { 0, 0, 3 });
		expectedArrays.add(new int[] { 1, 0, 3 });
		expectedArrays.add(new int[] { 0, 1, 3 });
		expectedArrays.add(new int[] { 1, 1, 3 });
		expectedArrays.add(new int[] { 0, 2, 3 });
		expectedArrays.add(new int[] { 1, 2, 3 });
		expectedArrays.add(new int[] { 0, 0, 4 });
		expectedArrays.add(new int[] { 1, 0, 4 });
		expectedArrays.add(new int[] { 0, 1, 4 });
		expectedArrays.add(new int[] { 1, 1, 4 });
		expectedArrays.add(new int[] { 0, 2, 4 });
		expectedArrays.add(new int[] { 1, 2, 4 });

		for (int i = 0; i < tupleGenerator.size(); i++) {
			tupleGenerator.fillTuple(i, tuple);
			assertTrue(Arrays.equals(expectedArrays.get(i), tuple));
		}
		/**
		 * precondition tests
		 */
		assertThrows(IndexOutOfBoundsException.class, () -> tupleGenerator.fillTuple(-2, tuple));
		assertThrows(IndexOutOfBoundsException.class, () -> tupleGenerator.fillTuple(-1, tuple));
		assertThrows(IndexOutOfBoundsException.class, () -> tupleGenerator.fillTuple(tupleGenerator.size(), tuple));
		assertThrows(IndexOutOfBoundsException.class, () -> tupleGenerator.fillTuple(tupleGenerator.size() + 1, tuple));
		assertThrows(IllegalArgumentException.class, () -> tupleGenerator.fillTuple(0, null));
		assertThrows(IllegalArgumentException.class, () -> tupleGenerator.fillTuple(0, new int[tupleGenerator.dimensions() - 1]));
		assertThrows(IllegalArgumentException.class, () -> tupleGenerator.fillTuple(0, new int[tupleGenerator.dimensions() + 1]));

	}

	@Test
	@UnitTestMethod(target = TupleGenerator.Builder.class, name = "build", args = {})
	public void testBuild() {
		TupleGenerator.Builder builder = TupleGenerator.builder();
		TupleGenerator tupleGenerator = builder.build();

		assertNotNull(tupleGenerator);
	}

	@Test
	@UnitTestMethod(target = TupleGenerator.Builder.class, name = "addDimension", args = { int.class })
	public void testAddDimension() {
		RandomGenerator randomGenerator = RandomGeneratorProvider.getRandomGenerator(1967914502607382607L);
		for (int i = 0; i < 100; i++) {
			TupleGenerator.Builder builder = TupleGenerator.builder();
			int dimensionCount = randomGenerator.nextInt(4) + 1;
			for (int j = 0; j < dimensionCount; j++) {
				int dimSize = randomGenerator.nextInt(10) + 1;
				builder.addDimension(dimSize);
			}
			int actualDimensionCount = builder.build().dimensions();
			assertEquals(dimensionCount, actualDimensionCount);
		}
	}
}
