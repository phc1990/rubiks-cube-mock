package phc1990.rubikscubemock;

import java.util.Map;

import phc1990.rubikscubemock.rotation.Rotation;
import phc1990.rubikscubemock.state.Color;
import phc1990.rubikscubemock.state.Face;
import phc1990.rubikscubemock.state.Intersection;
import phc1990.rubikscubemock.state.Slice;

/**
 * <p>
 * Represents a N-sized Rubik's Cube (NxNxN, with N > 2).
 * </p>
 * 
 * <p>
 * The parameterization leverages on the intersection' ambiguity property, thus
 * the <b>cube' state</b> has been parameterized as a <i>{F,X}</i> map, where:
 * </p>
 * 
 * <ul>
 * <li><i>F</i>: is one of the 6 cube <b>faces</b></li>
 * <li><i>X</i>: is the <b>face' state</b>, which has been parameterized as a
 * <i>{I,C}</i> map, where:</li>
 * <ul>
 * <li><i>I</i>: is the <b>intersection</b> of 2 slices. Note that the
 * intersection per se can represent several facets ('stickers') locations,
 * however this ambiguity </b>collapses into a single facet location</b>, once
 * it is assigned to a specific cube face (i.e. <i>F</i>, its key).</li>
 * <li><i>C</i>: is the value (color) of that specific facet location</li>
 * </ul>
 * </ul>
 *
 * @see Face
 * @see Intersection
 * @see Slice
 * @see Color
 *
 * @author Pau Hebrero Casasayas - May 18, 2020
 */
public class Cube {

	/**
	 * Size of the instance.
	 */
	private final int size;

	/**
	 * Map containing the instance' state.
	 */
	private final Map<Face, Map<Intersection, Color>> state;

	/**
	 * Constructor.
	 * 
	 * @param size
	 *            size of the cube (must be greater than 2)
	 * @param initialState
	 *            map containing the initial state information
	 */
	Cube(int size, Map<Face, Map<Intersection, Color>> initialState) {

		if (size < 2) {
			throw new IllegalArgumentException("Cannot create instance, cube size must be greater than 1.");
		}

		this.size = size;
		this.state = initialState;
	}

	/**
	 * Returns the size of the instance (e.g. 3).
	 * 
	 * @return the size of the instance
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Applies a rotation to the instance.
	 * 
	 * @param rotation
	 *            rotation to apply
	 * @return the instance
	 */
	public Cube apply(final Rotation rotation) {

		// Check cube and rotation size consistency.
		if (rotation.getMinimumSize() > size) {
			throw new RuntimeException("Insufficient cube size to perform rotation. Cube size: " + size
					+ ". Rotation required size: " + rotation.getMinimumSize());
		}

		rotation.apply(state, size);
		return this;
	}

}
