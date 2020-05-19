package phc1990.rubikscubemock.state;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>
 * {@link Slice} test class.
 * </p>
 * 
 * @author Pau Hebrero Casasayas - May 19, 2020
 */
public class SliceTest {

	private static final int MAX_SIZE = 100;

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFails1() {
		new Slice(Face.U, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructorFails2() {
		new Slice(Face.U, -1);
	}

	@Test
	public void testIsEquivalent() {

		// Different size
		Assert.assertTrue(new Slice(Face.U, 1).isEquivalent(new Slice(Face.U, 1), 2));
		Assert.assertTrue(new Slice(Face.U, 1).isEquivalent(new Slice(Face.U, 1), 3));

		// Different depth
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.U, 3), 2));
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.U, 2), 2));

		// Opposite direction, different size
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.D, 1), 2));
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.D, 1), 3));

		// Opposite direction, different depth
		Assert.assertTrue(new Slice(Face.U, 1).isEquivalent(new Slice(Face.D, 3), 3));
		Assert.assertTrue(new Slice(Face.U, 2).isEquivalent(new Slice(Face.D, 2), 3));
		Assert.assertTrue(new Slice(Face.U, 3).isEquivalent(new Slice(Face.D, 1), 3));

		// Non-equivalent direction
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.R, 1), 2));
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.L, 1), 2));
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.F, 1), 2));
		Assert.assertFalse(new Slice(Face.U, 1).isEquivalent(new Slice(Face.B, 1), 2));

	}

	@Test
	public void testGetEquivalentDepth() {

		// Different size
		Assert.assertEquals(1, new Slice(Face.U, 1).getEquivalentDepth(Face.U, 2));
		Assert.assertEquals(1, new Slice(Face.U, 1).getEquivalentDepth(Face.U, 3));

		// Different depth
		Assert.assertEquals(1, new Slice(Face.U, 1).getEquivalentDepth(Face.U, 2));
		Assert.assertEquals(2, new Slice(Face.U, 2).getEquivalentDepth(Face.U, 2));

		// Opposite direction, different size
		Assert.assertEquals(2, new Slice(Face.U, 1).getEquivalentDepth(Face.D, 2));
		Assert.assertEquals(3, new Slice(Face.U, 1).getEquivalentDepth(Face.D, 3));

		// Opposite direction, different depth
		Assert.assertEquals(3, new Slice(Face.U, 1).getEquivalentDepth(Face.D, 3));
		Assert.assertEquals(2, new Slice(Face.U, 2).getEquivalentDepth(Face.D, 3));
		Assert.assertEquals(1, new Slice(Face.U, 3).getEquivalentDepth(Face.D, 3));

		// Non-equivalent direction
		Assert.assertEquals(0, new Slice(Face.U, 1).getEquivalentDepth(Face.R, 2));
		Assert.assertEquals(0, new Slice(Face.U, 1).getEquivalentDepth(Face.L, 2));
		Assert.assertEquals(0, new Slice(Face.U, 1).getEquivalentDepth(Face.F, 2));
		Assert.assertEquals(0, new Slice(Face.U, 1).getEquivalentDepth(Face.B, 2));

	}

	@Test
	public void testWholeCubeEquivalency() {

		// For each direction
		for (final Face direction : Face.values()) {

			final Face opposite = direction.getOpposite();

			final List<Face> nonEquivalentDirections = new LinkedList<>(Arrays.asList(Face.values()));
			nonEquivalentDirections.remove(direction);
			nonEquivalentDirections.remove(opposite);

			// For all cube sizes
			for (int cubeSize = 2; cubeSize <= MAX_SIZE; cubeSize++) {

				// For each depth level
				for (int i = 1; i <= cubeSize; i++) {

					// Current slice
					final Slice s1 = new Slice(direction, i);
					Assert.assertTrue(s1.isEquivalent(s1, cubeSize));

					// Check equivalent depth
					final int equivalentDepth = s1.getEquivalentDepth(opposite, cubeSize);
					Assert.assertEquals(i, s1.getEquivalentDepth(direction, cubeSize));
					Assert.assertEquals(cubeSize + 1 - i, equivalentDepth);

					// Equivalent slice
					final Slice s2 = new Slice(opposite, equivalentDepth);
					Assert.assertTrue(s1.isEquivalent(s2, cubeSize));

					// For each other depth level
					for (int j = 1; j <= cubeSize; j++) {

						// Other slice
						final Slice s3 = new Slice(direction, j);

						if (j != i) {
							Assert.assertFalse(s1.isEquivalent(s3, cubeSize));
							Assert.assertFalse(s2.isEquivalent(s3, cubeSize));
						} else {
							Assert.assertTrue(s1.isEquivalent(s3, cubeSize));
							Assert.assertTrue(s2.isEquivalent(s3, cubeSize));
						}

						// Non equivalent directions (non dependent on {i,j})
						for (final Face nonEquivalentDirection : nonEquivalentDirections) {

							final Slice s4 = new Slice(nonEquivalentDirection, j);

							Assert.assertFalse(s1.isEquivalent(s4, cubeSize));
							Assert.assertFalse(s2.isEquivalent(s4, cubeSize));
						}
					}
				}
			}
		}
	}
}
