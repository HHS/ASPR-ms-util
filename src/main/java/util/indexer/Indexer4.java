package util.indexer;

import util.errors.ContractException;

/**
 * A thread safe, immutable index converter that supports treating a 4D array as
 * a 1D array.
 */
public final class Indexer4 {
	private final int size;
	private final int p1;
	private final int p2;
	private final int p3;

	/**
	 * Constructs an 4D indexer from the given dimension sizes
	 * 
	 * @throws ContractException
	 *                           <ul>
	 *                           <li>{@linkplain IndexerError#NEGATIVE_DIMENSION_SIZE}
	 *                           if any dimension has a negative size</li>
	 *                           <li>{@linkplain IndexerError#EXCEEDS_MAX_ARRAY_SIZE}
	 *                           if the product of the dimensions exceeds max
	 *                           int</li>
	 *                           </ul>
	 * 
	 * 
	 */
	public Indexer4(final int dim1, final int dim2, final int dim3, final int dim4) {
		if (dim1 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		if (dim2 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		if (dim3 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		if (dim4 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}

		try {
			p3 = dim4;
			p2 = Math.multiplyExact(p3, dim3);
			p1 = Math.multiplyExact(p2, dim2);
			size = Math.multiplyExact(p1, dim1);
		} catch (ArithmeticException e) {
			throw new ContractException(IndexerError.EXCEEDS_MAX_ARRAY_SIZE);
		}
	}

	/**
	 * Returns the combined index = ((index1*dim2 +index2)*dim3+index3)*dim4+index4
	 * from the given index values. No validation of the indexes or the result is
	 * provided.
	 */
	public int index(final int index1, final int index2, final int index3, final int index4) {
		return index1 * p1 + index2 * p2 + index3 * p3 + index4;
	}

	/**
	 * Returns the size (product of the dimensions) of the array supported by this
	 * index.
	 */
	public int size() {
		return size;
	}

}
