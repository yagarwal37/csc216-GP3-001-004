package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Abstract class for Courses and Events which contains the mutual fields and
 * methods between the two subclasses
 * Will be used to make up the schedule in WolfScheduler
 * 
 * @author Yash Agarwal
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/** Max number of hours */
	private static final int UPPER_HOUR = 24;
	/** Minimum number of hours */
	private static final int UPPER_MINUTE = 60;

	/**
	 * Constructor for the Abstract class Activity. Won't be directly used
	 * 
	 * @param title       title of the Activity
	 * @param meetingDays days the Activity meets
	 * @param startTime   starting time of the Activity
	 * @param endTime     ending time of the Activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Displays a smaller amount of information for the Activity
	 * 
	 * @return String [] String array containing the information that needs to be
	 *         displayed in the GUI
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Displays a larger amount of information for the Activity
	 * 
	 * @return String [] String array containing the information that needs to be
	 *         displayed in the GUI
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Returns the Course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException if title is too short or null
	 */
	public void setTitle(String title) {

		// Checks if title is empty or null
		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid title.");
		}

		this.title = title;
	}

	/**
	 * Returns the Course's meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Sets meeting days and times for course object
	 * 
	 * @param meetingDays the meeting days
	 * @param startTime   beginning time of Course
	 * @param endTime     ending time of Course
	 * @throws IllegalArgumentException if meeting days is null or empty
	 * @throws IllegalArgumentException if there are start and end times when
	 *                                  meeting days is "A"
	 * @throws IllegalArgumentException if there are duplicate meeting days or
	 *                                  invalid meeting days
	 * @throws IllegalArgumentException if start time is greater than end time
	 * @throws IllegalArgumentException if the conventional minute/hours variables
	 *                                  are greater than their limits
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
			int startHours = startTime / 100;
			int startMins = startTime % 100;
			int endHours = endTime / 100;
			int endMins = endTime % 100;

			if (startHours < 0 || startHours >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (startMins < 0 || startMins >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endHours < 0 || endHours >= UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endMins < 0 || endMins >= UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			this.meetingDays = meetingDays;
			this.startTime = startTime;
			this.endTime = endTime;
	}
	

	/**
	 * Converts military time of startTime and endTime to conventional time Creates
	 * a String of the meeting days and time for output
	 * 
	 * @return String of meeting days and start/end times
	 */
	public String getMeetingString() {

		if (!("A".equals(meetingDays))) {
			int startHours = startTime / 100;
			int startMins = startTime % 100;
			int endHours = endTime / 100;
			int endMins = endTime % 100;

			String temp = meetingDays + " ";

			String startDayNight = "";
			String endDayNight = "";

			if (startHours > 12) {
				startHours = startHours - 12;
				startDayNight = "PM";

			} else if (startHours < 12 && startHours > 0) {
				startDayNight = "AM";
			} else if(startHours == 12) {
				startDayNight = "PM";
			}

			if (endHours > 12) {
				endHours = endHours - 12;
				endDayNight = "PM";
			} else if (endHours < 12 && endHours > 0) {
				endDayNight = "AM";
			} else if(endHours == 12) {
				endDayNight = "PM";
			}
			

			if (startMins < 10) {
				temp = temp + startHours + ":0" + startMins + startDayNight + "-";
			} else {
				temp = temp + startHours + ":" + startMins + startDayNight + "-";
			}

			if (endMins < 10) {
				temp = temp + endHours + ":0" + endMins + endDayNight;
			} else {
				temp = temp + endHours + ":" + endMins + endDayNight;
			}

			return temp;
		}

		return "Arranged";

	}

	/**
	 * Returns the Course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Checks if the passed in activity is a duplicate
	 * 
	 * @param activity the passed in activity that is checked to see if its a duplicate
	 * @return true if there is a duplicate and false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/**
	 * Compares the "this" Activity instance with possibleConflictingActivity if there an overlapping time between the two
	 * 
	 * Overrides the checkConflict in the Conflict class to compare Activities
	 * 
	 * @param possibleConflictingActivity the activity that could be conflicting with "this"
	 * @throws ConflictException if start times overlap, end times overlap, or if the activity is during "this"
	 */
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		if (!(this == null || possibleConflictingActivity == null)
				&& !(this.getStartTime() == 0 || possibleConflictingActivity.getStartTime() == 0)) {

			for (int i = 0; i < this.getMeetingDays().length(); i++) {
				for (int j = 0; j < possibleConflictingActivity.getMeetingDays().length(); j++) {
					if (this.getMeetingDays().charAt(i) == possibleConflictingActivity.getMeetingDays().charAt(j)) {

						// Direct time overlap with this.starttime
						if (this.getStartTime() == possibleConflictingActivity.getStartTime()
								|| this.getStartTime() == possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
						// Direct time overlap with this.endtime
						if (this.getEndTime() == possibleConflictingActivity.getStartTime()
								|| this.getEndTime() == possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
						// Activity 2 starts between this
						if (this.getStartTime() <= possibleConflictingActivity.getStartTime()
								&& this.getEndTime() >= possibleConflictingActivity.getStartTime()) {
							throw new ConflictException();
						}
						// Activity 2 ends between this
						if (this.getStartTime() <= possibleConflictingActivity.getEndTime()
								&& this.getEndTime() >= possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
						// Activity 1 is encompassed by Activity 2
						if (this.getStartTime() >= possibleConflictingActivity.getStartTime()
								&& this.getEndTime() <= possibleConflictingActivity.getEndTime()) {
							throw new ConflictException();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Generate a hashCode for Course using all fields
	 * 
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares a given object to this object for equality on all fields
	 * 
	 * @param obj Object to compare
	 * @return true if the objects are same on all fields
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

}