package ShimonHeadController.innards.sound.basics;

import ShimonHeadController.innards.iLaunchable;
import ShimonHeadController.innards.sound.basics.Midi.Message;

/**
 * @author marc
 */
public class T_DensityFilter implements iLaunchable
{
	public void launch()
	{
		try
		{
//			Midi.outputToNewVirtualSource("source");
			FanOutMidiInputHandler fanOut= new FanOutMidiInputHandler();
			fanOut.register(new Midi.InputHandler()
			{
				public void handle(long hostTime, Message message)
				{
					System.out.println(message);
				}
			});
//			TaskQueue q= Midi.getMidi().registerInputHandler(fanOut);
//			Launcher.getLauncher().registerUpdateable(q);

			fanOut.setFilter(new FanOutMidiInputHandler.DensityFilter(1, (float) 1.5)
			{
				/**
				 * @see ShimonHeadController.innards.sound.basics.FanOutMidiInputHandler.DensityFilter#pass(Message)
				 */
				public boolean pass(Message m)
				{
					boolean b = super.pass(m);
					System.out.println(" got message <"+m+"> <"+b+">");
					return b;
				}
			});
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
