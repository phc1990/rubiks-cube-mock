package phc1990.rubikscubemock.state;

/**
 * <p>
 * Represents a cube slice, given by its relative location. The location is
 * parameterized via two coordinates:
 * </p>
 * 
 * <ol>
 * <li>Direction: direction towards which the depth is measured (e.g. 'U')</li>
 * <li>Depth: 1-based depth of the slice measured towards the specified
 * direction (e.g. 1, 2, 3, ...)</li>
 * </ol>
 * 
 * <p>
 * For example, in a 3x3x3 cube: {U,1} represents the 'upper layer', whereas
 * {D,1} represents the 'bottom layer'.
 * </p>
 * 
 * <p>
 * Properties:
 * </p>
 * 
 * <ul>
 * <li>This instance is immutable.</li>
 * <li>Ambiguity: the same slice might be represented with different coordinate
 * values (e.g. in a N-cube, {U,1} is equivalent to {D,N}).</li>
 * </ul>
 * 
 * @see Intersection
 * 
 * @author Pau Hebrero Casasayas - May 19, 2020
 */
public class Slice {

	/**
	 * Direction criterion.
	 */
	final Face direction;

	/**
	 * Depth coordinate.
	 */
	final int depth;

	/**
	 * Constructor.
	 * 
	 * @param direction
	 *            direction criterion
	 * @param depth
	 *            1-based depth coordinate
	 */
	public Slice(Face direction, int depth) {

		if (depth < 1) {
			throw new IllegalArgumentException("Depth of the slice must be greater than 1.");
		}

		this.direction = direction;
		this.depth = depth;
	}

	/**
	 * Checks if the current instance is equivalent to the given one.
	 * 
	 * @param other
	 *            slice instance to check against
	 * @param cubeSize
	 *            cube size (e.g. 3)
	 * @return true if the instances are equivalent
	 */
	boolean isEquivalent(final Slice other, final int cubeSize) {

		// Same direction criterion
		if (this.direction == other.direction) {
			return this.depth == other.depth;
		}

		// Opposite direction criterion
		if (this.direction == other.direction.getOpposite()) {
			return this.depth == other.getEquivalentDepth(direction, cubeSize);
		}

		return false;
	}

	/**
	 * <p>
	 * Returns the equivalent depth of the instance in the new direction
	 * criterion or '0' if not applicable.
	 * </p>
	 * 
	 * <i>
	 * <p>
	 * For example, in a 3x3x3 cube:
	 * </p>
	 * 
	 * <ul>
	 * <li>the equivalent depth of {R,1} for the direction criterion 'R' is 1
	 * </li>
	 * <li>the equivalent depth of {R,1} for the direction criterion 'L' is 3
	 * </li>
	 * <li>the equivalent depth of {R,1} for the direction criterion 'U' is 0
	 * (not applicable)</li>
	 * </ul>
	 * </i>
	 * 
	 * @param newDirection
	 *            new direction criterion
	 * @param cubeSize
	 *            cube size (e.g. 3)
	 * @return the equivalent depth
	 */
	int getEquivalentDepth(final Face newDirection, final int cubeSize) {

		int equivalentDepth = 0;

		if (direction == newDirection) {
			equivalentDepth = depth;
		} else if (direction.getOpposite() == newDirection) {
			equivalentDepth = cubeSize + 1 - depth;

			if (equivalentDepth < 1) {
				throw new RuntimeException("Resulting equivalent depth is lower than 1.");
			}
		}

		return equivalentDepth;
	}

}
