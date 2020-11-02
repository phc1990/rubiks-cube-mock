package phc1990.rubikscubemock.state;

/**
 * <p>
 * 
 * </p>
 * 
 * @author <a href="https://github.com/phc1990">Pau Hebrero Casasayas</a> - Nov
 *         2, 2020
 */
public class Location {

	private final Face face;

	public Location(final Face face, final Face face1, final int depth1, final Face face2, final int depth2) {
		
		if (!face.isOrthogonal(face1) || !face1.isOrthogonal(face2) || !face2.isOrthogonal(face)) {
			throw new IllegalArgumentException("Cannot create instance, all 3 faces must be orthogonal.");
		}
	}

}
