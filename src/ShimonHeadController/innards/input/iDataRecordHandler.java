package ShimonHeadController.innards.input;

import ShimonHeadController.innards.data.iDataRecord;

/**
 * iDataRecordHandler should be implemented by any object that wants to receive
 * data from the <code>InputDispatcher</code>
 * 
 * @see InputDispatcher
 */
public interface iDataRecordHandler
{
    /**
	 * @return true if dr is handleable by this DataRecordHandler, false
	 * otherwise
	 */
	public boolean handleDataRecord(iDataRecord dr);
}