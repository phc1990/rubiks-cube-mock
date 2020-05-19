/**
 * 
 */
package phc1990.rubikscubemock.state;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * Rubik's Cube faces as per World Cube Association
 * <a href="https://www.worldcubeassociation.org/regulations/#12a1a">notation
 * </a>.
 * </p>
 *
 * @author Pau Hebrero Casasayas - May 18, 2020
 */
public enum Face {

	/**
	 * Front face.
	 */
	F,

	/**
	 * Back face.
	 */
	B,

	/**
	 * Right face.
	 */
	R,

	/**
	 * Left face.
	 */
	L,

	/**
	 * Upper face.
	 */
	U,

	/**
	 * Bottom face.
	 */
	D;

	/**
	 * Returns the opposite face of the instance (e.g. right -> left).
	 * 
	 * @return the opposite face of the instance
	 */
	public Face getOpposite() {
		return getOpposite(this);
	}

	/**
	 * Returns the opposite to the given face (e.g. right -> left).
	 * 
	 * @param face
	 *            the face
	 * @return the opposite to the given face
	 */
	private static Face getOpposite(final Face face) {

		switch (face) {
		case F:
			return B;
		case B:
			return F;
		case R:
			return L;
		case L:
			return R;
		case U:
			return D;
		case D:
			return U;
		default:
			throw new IllegalArgumentException("Face " + face.name() + " not considered.");
		}
	}

	/**
	 * Returns a stream representing the complete rotation sequence of the
	 * instance (e.g. U -> R -> D -> L).
	 * 
	 * @param clockwise
	 *            true for clockwise rotation
	 * @return stream of the complete rotation sequence
	 */
	public List<Face> getRotationSequence(boolean clockwise) {
		return getRotationSequence(this, clockwise);
	}

	/**
	 * Returns a stream representing the complete rotation sequence for the
	 * given face (e.g. U -> R -> D -> L).
	 * 
	 * @param face
	 *            the face
	 * @param clockwise
	 *            true for clockwise rotation
	 * @return stream of the complete rotation sequence
	 */
	private static List<Face> getRotationSequence(final Face face, boolean clockwise) {
		switch (face) {
		case F:
			return clockwise ? RotationDirection.F.getSequence() : RotationDirection.F_PRIME.getSequence();
		case R:
			return clockwise ? RotationDirection.R.getSequence() : RotationDirection.R_PRIME.getSequence();
		case U:
			return clockwise ? RotationDirection.U.getSequence() : RotationDirection.U_PRIME.getSequence();
		default:
			return getRotationSequence(face.getOpposite(), !clockwise);
		}
	}

	/**
	 * <p>
	 * Auxiliary class to optimize rotation sequence information, as the number
	 * of rotation directions is finite {F,R,U} and the counterparts {F',R',U'}
	 * (or {B,L,D}), there is no need to create a new instance every time.
	 * </p>
	 * 
	 * @author Pau Hebrero Casasayas - May 19, 2020
	 */
	private static class RotationDirection {

		/**
		 * Front face clockwise rotation.
		 */
		private static final RotationDirection F = new RotationDirection(Face.U, Face.R);

		/**
		 * Front face counterclockwise rotation.
		 */
		private static final RotationDirection F_PRIME = F.revert();

		/**
		 * Right face clockwise rotation.
		 */
		private static final RotationDirection R = new RotationDirection(Face.F, Face.U);

		/**
		 * Right face counterclockwise rotation.
		 */
		private static final RotationDirection R_PRIME = R.revert();

		/**
		 * Upper face clockwise rotation.
		 */
		private static final RotationDirection U = new RotationDirection(Face.R, Face.F);

		/**
		 * Upper face counterclockwise rotation.
		 */
		private static final RotationDirection U_PRIME = U.revert();

		/**
		 * Face to rotate 'from'.
		 */
		private final Face from;

		/**
		 * Face to rotate 'to'.
		 */
		private final Face to;

		/**
		 * Constructor.
		 * 
		 * @param from
		 *            face to rotate from
		 * @param to
		 *            face to rotate to
		 */
		private RotationDirection(Face from, Face to) {
			this.from = from;
			this.to = to;
		}

		/**
		 * Creates a new instance with a reverted direction (e.g. {U,R} ->
		 * {R,U}).
		 * 
		 * @return a new instance with a reverted direction
		 */
		private RotationDirection revert() {
			return new RotationDirection(to, from);
		}

		/**
		 * Returns a stream representing the complete rotation sequence (e.g. U
		 * -> R -> D -> L).
		 * 
		 * @return stream of the complete rotation sequence
		 */
		private List<Face> getSequence() {
			return Arrays.asList(from, to, from.getOpposite(), to.getOpposite());
		}

	}
}
