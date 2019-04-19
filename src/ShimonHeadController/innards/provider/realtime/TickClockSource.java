package ShimonHeadController.innards.provider.realtime;

import ShimonHeadController.innards.*;


/** good for testing things
*/
public class TickClockSource
	implements ClockSource, iUpdateable
	
{
	double t;
	
	public TickClockSource()
	{
	}
	
	public void tick(){t++;}
	
	public void update(){this.t = t;}

	public double getTime()
	{
		return t;
	}
}