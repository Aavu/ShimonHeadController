package ShimonHeadController.innards.namespace.context.key;

import ShimonHeadController.innards.provider.iFilter;

/**
 * @author marc
 */
public class FilterKey extends CKey implements iFilter
{
	
	public FilterKey(String s)
	{
		super(s);
	}

	public float filter(float value)
	{
		return toFloat(run(new Float(value)));
	}
}
