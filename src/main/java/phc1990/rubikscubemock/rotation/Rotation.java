package phc1990.rubikscubemock.rotation;

import java.util.Map;

import phc1990.rubikscubemock.state.Color;
import phc1990.rubikscubemock.state.Face;
import phc1990.rubikscubemock.state.Intersection;

/**
 * <p>
 * Represents a rotation applied on a cube.
 * </p>
 *
 * @author <a href="https://github.com/phc1990">Pau Hebrero Casasayas</a> - May 18, 2020
 */
public interface Rotation {

	/**
	 * Returns the minimum size of the cube for the instance to make sense (e.g.
	 * 3).
	 * 
	 * @return the minimum size of the cube for the instance to make sense
	 */
	int getMinimumSize();

	/**
	 * Applies the instance to the given state.
	 * 
	 * @param state
	 *            map containing the state upon which the instance will be
	 *            applied
	 * @param cubeSize
	 *            size of the cube
	 */
	void apply(Map<Face, Map<Intersection, Color>> state, int cubeSize);

}
