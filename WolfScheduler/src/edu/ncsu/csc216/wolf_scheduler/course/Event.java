/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * An object extended from the Activity class which is added to WolfScheduler as an Event
 * Will be used through WolfScheduler for the client to create a schedule 
 * getShortDisplayArray and getLongDisplayArray are used to output contents of the event for the client to 
 * view
 * isDuplicate compares 2 events to see if they're the same  
 * @author yash
 *
 */
public class Event extends Activity {

	/** Details for the event*/
	private String eventDetails;
	
	
	/**
	 * Generates a Event object which extends Activity
	 * 
	 * @param title title of the Event
	 * @param meetingDays days the Event meets
	 * @param startTime start time of the Event
	 * @param endTime End time of the Event
	 * @param eventDetails Specific details for the event
	 */
	public Event(String title, String meetingDays, int startTime, int endTime, String eventDetails) {
        super(title, meetingDays, startTime, endTime);
        setEventDetails(eventDetails);
    }
	/**
	 * Returns the event details
	 * 
	 * @return the eventDetails
	 */
	public String getEventDetails() {
		return eventDetails;
	}

	/**
	 * Sets details for the event
	 * 
	 * @param eventDetails the eventDetails to set
	 */
	public void setEventDetails(String eventDetails) {
		if(eventDetails == null) {
			throw new IllegalArgumentException("Invalid event details.");
		}
		
		this.eventDetails = eventDetails;
	}
	
	/**
	 * Creates a String of the meeting days and times. Utilizes the super class method as well
	 * 
	 * @param meetingDays days the Course meets
	 * @param startTime start time of the Course
	 * @param endTime end time of the Course
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		int mCounter = 0;
		int tCounter = 0;
		int wCounter = 0;
		int hCounter = 0;
		int fCounter = 0;
		int sCounter = 0;
		int uCounter = 0;

		for (int i = 0; i < meetingDays.length(); i++) {
			if (meetingDays.charAt(i) == 'M') {
				mCounter++;
			} else if (meetingDays.charAt(i) == 'T') {
				tCounter++;
			} else if (meetingDays.charAt(i) == 'W') {
				wCounter++;
			} else if (meetingDays.charAt(i) == 'H') {
				hCounter++;
			} else if (meetingDays.charAt(i) == 'F') {
				fCounter++;
			} else if (meetingDays.charAt(i) == 'S') {
				sCounter++;
			} else if (meetingDays.charAt(i) == 'U') {
				uCounter++;
			} else {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}

		if (mCounter > 1 || tCounter > 1 || wCounter > 1 || hCounter > 1 || fCounter > 1 || 
				sCounter > 1 || uCounter > 1) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
		
	}
	
	
	
	/**
	 * Displays a String array of two blanks, title and the meeting string
	 * Overrode from getShortDisplayArray in Activity class to get the details of the selected Event
	 * as the Activity class method doesn't return anything useful
	 * 
	 * @return output the String array containing all the information above
	 */
	@Override
	public String[] getShortDisplayArray() {
		String [] output = new String [4];
		output[0] = "";
		output[1] = "";
		output[2] = getTitle();
		output[3] = getMeetingString();
		
		return output;
	}

	/**
	 * Displays a String array of two blanks, the title, two more blanks, the meeting string
	 * and the event details
	 * Overrode from getLongDisplayArray in Activity class to get the details of the selected Event
	 * as the Activity class method doesn't return anything useful
	 * 
	 * @return output the String array containing all the information above
	 */
	@Override
	public String[] getLongDisplayArray() {
		String [] output = new String [7];
		output[0] = "";
		output[1] = "";
		output[2] = getTitle();
		output[3] = "";
		output[4] = "";
		output[5] = getMeetingString();
		output[6] = this.eventDetails;
		
		return output;
	}
	
	/**
	 * Checks if activity is a duplicate of the "this" 
	 * Overrode isDuplicate from Activity class to compare Events to Events
	 * 
	 * @param activity Activity which is an Event that is compared to the "this" to test if duplicated
	 * @return true if activity is a duplicate of "this". False otherwise
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
		if (this == activity) {
			return true;
		}
		
		if (activity == null) {
			return false;
		}
		
		if(activity instanceof Event) {
			Event temp = (Event) activity;
			if(this.getTitle().equals(temp.getTitle())) {
				return true;
			}
		}
		return false;
	} 
	/**
	 * Creates a String of title, meeting days, start time, end time, and the event details
	 * 
	 * @return a String the the information above separated by commas
	 */
	@Override
	public String toString() {
		String output = getTitle() + "," + getMeetingDays() + "," + getStartTime() 
			+ "," + getEndTime() + "," + this.eventDetails; 
		
		return output;
	}

}
