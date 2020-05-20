package phc1990.rubikscubemock.rotation;

import java.util.Collection;
import java.util.Map;

import phc1990.rubikscubemock.state.Color;
import phc1990.rubikscubemock.state.Face;
import phc1990.rubikscubemock.state.Intersection;

/**
 * <p>
 * Composed rotation, constituted by a sequence of one or more rotations.
 * </p>
 * 
 * @author Pau Hebrero Casasayas - May 19, 2020
 */
class ComposedRotation implements Rotation {

	/**
	 * Minimum size amongst the rotation sequence.
	 */
	private final int minimumSize;

	/**
	 * Rotation sequence to be applied.
	 */
	private final Collection<Rotation> rotations;

	/**
	 * Constructor.
	 * 
	 * @param rotations
	 *            rotations sequence to be applied
	 */
	ComposedRotation(Collection<Rotation> rotations) {
		this.minimumSize = rotations.stream().mapToInt(r -> r.getMinimumSize()).min()
				.orElseThrow(() -> new IllegalArgumentException("Collection of rotations cannot be empty."));
		this.rotations = rotations;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinimumSize() {
		return minimumSize;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void apply(Map<Face, Map<Intersection, Color>> state, int cubeSize) {
		rotations.forEach(r -> r.apply(state, cubeSize));
	}
}
