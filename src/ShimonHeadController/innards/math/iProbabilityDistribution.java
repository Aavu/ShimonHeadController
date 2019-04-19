package ShimonHeadController.innards.math;

import ShimonHeadController.innards.math.linalg.Vec;

/**
 * @author mattb
 */
public interface iProbabilityDistribution
{
	/**
	 * returns the density of the distribution at the specified point
	 */
	public double evaluate(Vec point);
}
