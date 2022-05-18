package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Course Class: Designed to hold the properties of a course and be able to be
 * manipulated through getters/setters
 * A subclass of Activity as to be incorporated into a schedule through the user using WolfScheduler
 * getShortDisplayArray and getLongDisplayArray are used to output contents of the course for the client to 
 * view
 * isDuplicate compares 2 courses to see if they're the same  
 * @author yash
 */

public class Course extends Activity {

	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's minimum name length */
	private static final int MIN_NAME_LENGTH = 5;
	/** Course's maximum name length */
	private static final int MAX_NAME_LENGTH = 8;
	/** Course's minimum letter count */
	private static final int MIN_LETTER_COUNT = 1;
	/** Course's maximum letter count */
	private static final int MAX_LETTER_COUNT = 4;
	/** Course's digit amount */
	private static final int DIGIT_COUNT = 3;
	/** Course's section length */
	private static final int SECTION_LENGTH = 3;
	/** Max credits for a Course */
	private static final int MAX_CREDITS = 5;
	/** Minimum credits for a Course */
	private static final int MIN_CREDITS = 1;

	/**
	 * Constructs a Course object with values for all fields
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's ID for Course
	 * @param meetingDays  meeting days for Course as a series of chars
	 * @param startTime    start time of Course
	 * @param endTime      end time of Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		super(title, meetingDays, startTime, endTime);
		setName(name);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
	}

	/**
	 * Creates a Course with the given name, title, section, credit hours,
	 * instructorID, and meeting days for courses that are arranged
	 * 
	 * @param name         name of Course
	 * @param title        title of Course
	 * @param section      section of Course
	 * @param credits      credit hours for Course
	 * @param instructorId instructor's ID for Course
	 * @param meetingDays  meeting days for Course as a series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name
	 * 
	 * @param name the name to set
	 * @throws IllegalArgumentException if name is null, empty, too short, or too
	 *                                  long
	 * @throws IllegalArgumentException if letters or digits are in the wrong
	 *                                  location
	 * @throws IllegalArgumentException if there are too few or too many
	 *                                  letters/digits
	 */
	private void setName(String name) {

		// Throws exception if name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Throws exception is name length is <5 or >8 characters or is empty
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Initialized counters and boolean
		int numLetters = 0;
		int numDigits = 0;
		boolean flag = false;

		// Checks for the L LLL NNN pattern in course name
		for (int i = 0; i < name.length(); i++) {
			if (!flag) {
				if (Character.isLetter(name.charAt(i))) {
					numLetters++;
				} else if (name.charAt(i) == ' ') {
					flag = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (flag) {
				if (Character.isDigit(name.charAt(i))) {
					numDigits++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}

		// Checks if numLetters is valid
		if (numLetters < MIN_LETTER_COUNT || numLetters > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Checks if numDigits is valid
		if (numDigits != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;
	}

	/**
	 * Returns the Course's section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Set the Course's section
	 * 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is not 3 characters, empty, or
	 *                                  null
	 * @throws IllegalArgumentException if section is not all digits
	 */
	public void setSection(String section) {

		if ("".equals(section) || section == null || section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}

		for (int i = 0; i < section.length(); i++) {
			if (!Character.isDigit(section.charAt(i))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}

		this.section = section;
	}

	/**
	 * Returns the Course's credits
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credits
	 * 
	 * @param credits the credits to set
	 * @throws IllegalArgumentException if there are too many or too few credits for
	 *                                  a Course
	 */
	public void setCredits(int credits) {
		// Checks if credits is <1 or >5
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;
	}

	/**
	 * Returns the Course's instructorID
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructorID
	 * 
	 * @param instructorId the instructorId to set
	 * @throws IllegalArgumentException if instructor Id is null or empty
	 */
	public void setInstructorId(String instructorId) {
		// Checks is instructor Id is null or empty
		if (instructorId == null || "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}

		this.instructorId = instructorId;
	}
	
	/**
	 * Creates a String of the meeting days and times. Utilizes the super class method as well
	 * 
	 * @param meetingDays days the Course meets
	 * @param startTime start time of the Course
	 * @param endTime end time of the Course
	 * @throws IllegalArgumentException if there are duplicate meeting days or invalid meeting times
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}	
			super.setMeetingDaysAndTime(meetingDays, 0, 0);
		}
		else {
			int mCounter = 0;
			int tCounter = 0;
			int wCounter = 0;
			int hCounter = 0;
			int fCounter = 0;

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
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

			if (mCounter > 1 || tCounter > 1 || wCounter > 1 || hCounter > 1 || fCounter > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			if (startTime > endTime) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
			super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
			
			
			
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
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated by value String of all Course fields
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {

		if ("A".equals(getMeetingDays())) {
			return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + ","
					+ getMeetingDays();
		}
		return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays()
				+ "," + getStartTime() + "," + getEndTime();

	}

	/**
	 * Displays a String array of name, section, title, and the meeting string
	 * Overrode from getShortDisplayArray in Activity class to get the details of the selected Course
	 * as the Activity class method doesn't return anything useful
	 * 
	 * @return output the String array containing all the information above
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] output = new String[4];
		output[0] = getName();
		output[1] = getSection();
		output[2] = getTitle();
		output[3] = getMeetingString();

		return output;
	}

	/**
	 * Displays a String array of name, section, title, credits, instructorId,
	 * meeting string and an empty string
	 * Overrode from getLongDisplayArray in Activity class to get the details of the selected Course
	 * as the Activity class method doesn't return anything useful
	 * 
	 * @return output the String array containing all the information above
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] output = new String[7];
		output[0] = getName();
		output[1] = getSection();
		output[2] = getTitle();
		output[3] = String.valueOf(this.credits);
		output[4] = getInstructorId();
		output[5] = getMeetingString();
		output[6] = "";

		return output;
	}

	/**
	 * Checks if activity is a duplicate of "this"
	 * Overrode isDuplicate from Activity class to compare Courses to Courses
	 * 
	 * @param activity an activity which is checked if its a duplicate
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
		
		if(activity instanceof Course) {
			Course temp = (Course) activity;
			if(this.getName().equals(temp.getName())) {
				return true;
			}
		}
		return false;
	}

}
