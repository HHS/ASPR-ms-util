package util.indexer;

import net.jcip.annotations.ThreadSafe;
import util.errors.ContractException;

/**
 * A thread safe, immutable index converter that supports treating a 2D array as
 * a 1D array.
 */

@ThreadSafe
public final class Indexer2 {

	private final int size;

	private final int p1;

	/**
	 * Constructs an 2D indexer from the given dimension sizes
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
	public Indexer2(final int dim1, final int dim2) {
		if (dim1 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		if (dim2 < 0) {
			throw new ContractException(IndexerError.NEGATIVE_DIMENSION_SIZE);
		}
		try {
			p1 = dim2;
			size = Math.multiplyExact(p1, dim1);
		} catch (ArithmeticException e) {
			throw new ContractException(IndexerError.EXCEEDS_MAX_ARRAY_SIZE);
		}
	}

	/**
	 * Returns the combined index = index1*dim2+index2 from the given index values.
	 * No validation of the indexes or the result is provided.
	 */
	public int index(final int index1, final int index2) {
		return index1 * p1 + index2;
	}

	/**
	 * Returns the size (product of the dimensions) of the array supported by this
	 * index.
	 */
	public int size() {
		return size;
	}

}
