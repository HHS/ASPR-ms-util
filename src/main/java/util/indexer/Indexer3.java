package util.indexer;

import util.errors.ContractException;

/**
 * A thread safe, immutable index converter that supports treating a 3D array as
 * a 1D array.
 */
public final class Indexer3 {
	private final int size;
	private final int p1;
	private final int p2;

	/**
	 * Constructs an 3D indexer from the given dimension sizes
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
	public Indexer3(final int dim1, final int dim2, final int dim3) {
		if (dim1 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		if (dim2 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		if (dim3 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}

		try {
			p2 = dim3;
			p1 = Math.multiplyExact(p2, dim2);
			size = Math.multiplyExact(p1, dim1);
		} catch (ArithmeticException e) {
			throw new ContractException(IndexerError.EXCEEDS_MAX_ARRAY_SIZE);
		}
	}

	/**
	 * Returns the combined index = (index1*dim2 +index2)*dim3+index3 from the given
	 * index values. No validation of the indexes or the result is provided.
	 */
	public int index(final int index1, final int index2, final int index3) {
		return index1 * p1 + index2 * p2 + index3;
	}
	
	/**
	 * Returns the size (product of the dimensions) of the array supported by this
	 * index.
	 */
	public int size() {
		return size;
	}
}
