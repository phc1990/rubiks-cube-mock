package phc1990.rubikscubemock.state;

/**
 * <p>
 * Represents the intersection between two slices.
 * </p>
 * 
 * <ul>
 * <li>This instance does <b>not</b> represent an exact facet ('sticker')
 * location, but the set of locations resulting from the intersection (overlap)
 * of the two slices.</li>
 * <li>Only non-parallel slices are allowed (e.g. {U,U}, {U,D}, etc.
 * combinations are not allowed).</li>
 * </ul>
 * 
 * <p>
 * Properties:
 * </p>
 * 
 * <ul>
 * <li>This instance is immutable.</li>
 * <li>Ambiguity: the same intersection might be represented by a different set
 * of slices (e.g. in a N-cube {{1,U},{1,F}} is equivalent to {{1,F},{1,U}},
 * {{1,U},{N,B}}, {{N,D},{1,F}}, {{N,D},{N,B}} and {{N,B},{N,D}}).</li>
 * <li>The amount of locations that the intersection represents depends on both,
 * the size of the cube and the slices depth:</li>
 * <ol>
 * <li>In a cube with no inner layers (i.e. N <= 2), the intersection always
 * represents N locations.</li>
 * <li>In a cube with inner layers (i.e. N > 2), the intersection can represent
 * either:</li>
 * <ul>
 * <li>2 locations (both are inner slices)</li>
 * <li>N + 2 locations (one inner slice and one outer slice)</li>
 * <li>2*N + 2 locations (both are outer slices)</li>
 * </ul>
 * </ol>
 * </ul>
 * 
 * @see Slice
 * 
 * @author Pau Hebrero Casasayas - May 19, 2020
 */
public class Intersection {

	/**
	 * Cube size.
	 */
	private final int cubeSize;

	/**
	 * Slice 1.
	 */
	private final Slice slice1;

	/**
	 * Slice 2.
	 */
	private final Slice slice2;

	/**
	 * Hash-code.
	 */
	private final int hashCode;

	/**
	 * Constructor. Slices must be perpendicular.
	 * 
	 * @param cubeSize
	 *            cube size (e.g. 3)
	 * @param slice1
	 *            slice 1 (must be perpendicular to slice 2)
	 * @param slice2
	 *            slice 2 (must be perpendicular to slice 1)
	 */
	public Intersection(int cubeSize, Slice slice1, Slice slice2) {

		if (cubeSize < 1) {
			throw new IllegalArgumentException("Cannot create instance, cube size must be greater than 0.");
		}

		if (slice1.direction == slice2.direction || slice1.direction == slice2.direction.getOpposite()) {
			throw new IllegalArgumentException("Cannot create instance, slices are not perpendcular.");
		}

		this.cubeSize = cubeSize;
		this.slice1 = slice1;
		this.slice2 = slice2;

		// Get equivalent depths of coordinates with a {F,R,U} criterion.
		final int int1 = slice1.getEquivalentDepth(Face.F, cubeSize) + slice1.getEquivalentDepth(Face.R, cubeSize)
				+ slice1.getEquivalentDepth(Face.U, cubeSize);
		final int int2 = slice2.getEquivalentDepth(Face.F, cubeSize) + slice2.getEquivalentDepth(Face.R, cubeSize)
				+ slice2.getEquivalentDepth(Face.U, cubeSize);

		this.hashCode = int1 * int2;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		final Intersection other = (Intersection) obj;

		if (cubeSize != other.cubeSize) {
			throw new RuntimeException("Instances are not consistent in size.");
		}

		if (slice1.isEquivalent(other.slice1, cubeSize)) {

			// {slice1,slice2} might be equivalent to other's {slice1,slice2}
			return slice2.isEquivalent(other.slice2, cubeSize);

		} else if (slice1.isEquivalent(other.slice2, cubeSize)) {

			// {slice1,slice2} might be equivalent to other's {slice2,slice1}
			return slice2.isEquivalent(other.slice1, cubeSize);
		}

		return false;
	}

}
