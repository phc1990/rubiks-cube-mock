package phc1990.rubikscubemock.rotation;

import java.util.List;
import java.util.Map;

import phc1990.rubikscubemock.state.Color;
import phc1990.rubikscubemock.state.Slice;
import phc1990.rubikscubemock.state.Face;
import phc1990.rubikscubemock.state.Intersection;

/**
 * <p>
 * Elementary rotation, constituted by the motion of a single slice (either a
 * 'face rotation' or a 'slice turn').
 * </p>
 *
 * @author <a href="https://github.com/phc1990">Pau Hebrero Casasayas</a> - May 18, 2020
 */
class ElementaryRotation implements Rotation {

	/**
	 * Face to rotate.
	 */
	private final Face face;

	/**
	 * Direction flag.
	 */
	private final boolean clockwise;

	/**
	 * Depth of the layer to rotate (e.g. '1' for a face rotation).
	 */
	private final int depth;

	/**
	 * Constructor.
	 * 
	 * @param face
	 *            face to rotate
	 * @param clockwise
	 *            true for clockwise rotation
	 * @param depth
	 *            depth of the layer to rotate (e.g. '1' for a face rotation)
	 */
	ElementaryRotation(Face face, boolean clockwise, int depth) {

		if (depth < 0) {
			throw new IllegalArgumentException("Rotation depth must be greater than 0.");
		}

		this.face = face;
		this.clockwise = clockwise;
		this.depth = depth;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMinimumSize() {
		return depth;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void apply(Map<Face, Map<Intersection, Color>> state, int cubeSize) {

		// Rotation sequence
		final List<Face> sequence = face.getRotationSequence(clockwise);

		/*
		 * SLICE TURN
		 */
		// Coordinate of the slice that will be moved {face, depth}
		final Slice sliceCoordinate = new Slice(face, depth);

		final Face face0 = sequence.get(0);
		final Face face1 = sequence.get(1);
		final Face face2 = sequence.get(2);
		final Face face3 = sequence.get(3);

		final Map<Intersection, Color> map0 = state.get(face0);
		final Map<Intersection, Color> map1 = state.get(face1);
		final Map<Intersection, Color> map2 = state.get(face2);
		final Map<Intersection, Color> map3 = state.get(face3);

		for (int i = 1; i <= cubeSize; i++) {

			// Second location coordinates are found by proximity to the 'next
			// face' (i.e. the face where the piece will be moved to)
			final Intersection location0 = new Intersection(cubeSize, sliceCoordinate, new Slice(face1, i));
			final Intersection location1 = new Intersection(cubeSize, sliceCoordinate, new Slice(face2, i));
			final Intersection location2 = new Intersection(cubeSize, sliceCoordinate, new Slice(face3, i));
			final Intersection location3 = new Intersection(cubeSize, sliceCoordinate, new Slice(face0, i));

			// Start rotation
			final Color newColor1 = map0.get(location0);
			final Color newColor2 = map1.put(location1, newColor1);
			final Color newColor3 = map2.put(location2, newColor2);
			final Color newColor0 = map3.put(location3, newColor3);

			// End rotation
			map0.put(location0, newColor0);
		}

		/*
		 * FACE ROTATION (only applicable at the 'edges')
		 */
		if (depth == 1 || depth == cubeSize) {
			// TODO implement
		}
	}
}
