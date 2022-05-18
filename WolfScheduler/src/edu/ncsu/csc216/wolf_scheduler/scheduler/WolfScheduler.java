package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;
import edu.ncsu.csc216.wolf_scheduler.course.ConflictException;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.course.Event;
import edu.ncsu.csc216.wolf_scheduler.io.ActivityRecordIO;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;
/**
 * Acts as the holder for NCSU course catalog and for a student's schedule
 * Combines the different Activity subclasses to make a 
 * @author yash
 *
 */
public class WolfScheduler {
	/** Course catalog */
	private ArrayList<Course> catalog;
	/** Student schedule defined as an Activity for both Courses and Events*/
	private ArrayList<Activity> schedule;
	/** title of student schedule*/
	private String title;
	
	
	/**
	 * Constructs WolfScheduler Object
	 * 
	 * @param fileName input given by user of the file name for catalog to retrieve courses
	 * @throws IllegalArgumentException if fileName cannot be found
	 */
	public WolfScheduler(String fileName) {
		schedule = new ArrayList<>();
		this.title = "My Schedule";
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch(FileNotFoundException e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
		
	}
	
	/**
	 * Retrieves name, section and title of courses in schedule 
	 * 
	 * @return a 2D array of the name, section, title, and the meeting string of 
	 * courses in catalog or an empty array if catalog is empty
	 */
	public String [][] getCourseCatalog() {
		String [][] temp = new String [catalog.size()][4];
		
		if(catalog.size() == 0) {
			return temp;
		}
		
		for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            temp[i] = c.getShortDisplayArray();
        }
		
		return temp;
	}
	
	/**
	 * Retrieves the ShortDisplayArray of the desired Activity for output from the listed method   
	 *   
	 * @return a 2D array of the name, section, title and the meeting string of the Course or 
	 * two blank strings, the title, and the meeting string of the Event depending on the Activity
	 */
	public String [][] getScheduledActivities() {
		String [][] temp = new String [schedule.size()][4];
		
		if(schedule.size() == 0) {
			return temp;
		}
		
		for(int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			temp[i] = a.getShortDisplayArray();
		}
		
		return temp;
	}
	
	/**
	 * Retrieves all the information details of the Activity for display
	 * 
	 * 
	 * @return a 2D array of the name, section, title, credits, instructorId, meeting string 
	 * and an empty string if a Course and two blanks, the title, two more blanks, the meeting string
	 * and the event details if an Event
	 */
	public String [][] getFullScheduledActivities() {
		String [][] temp = new String [schedule.size()][7];
		
		if(schedule.size() == 0) {
			return temp;
		}
		
		for(int i = 0; i < schedule.size(); i++) {
			Activity a = schedule.get(i);
			temp[i] = a.getLongDisplayArray();
		}
		
		return temp;
	}
	
	/**
	 * Returns title
	 * 
	 * @return returns the schedule's title
	 */
	public String getScheduleTitle() {
		return this.title;
	}
	
	/**
	 * Changes the title to what the user inputs
	 * 
	 * @param title inputed title given by the user to alter default title name
	 * @throws IllegalArgumentException if title is null
	 */
	public void setScheduleTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		
		this.title = title;
	}

	/**
	 * Exports student schedule
	 * 
	 * @param fileName file name of the schedule's destination
	 * @throws IllegalArgumentException is an IOException is caught
	 */
	public void exportSchedule(String fileName) {
		try {
			ActivityRecordIO.writeActivityRecords(fileName, schedule);
		}
		catch(IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
		
		
	}
	
	/**
	 * Retrieves course from catalog 
	 * 
	 * @param name name of the Course being searched for in catalog
	 * @param section section of the Course being searched for in catalog
	 * @return the located Course in catalog or null if not found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for(int i = 0; i < catalog.size(); i++) {
			if(catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {					
				return catalog.get(i);
			}
		}
		return null;
	}
	
	/** 
	 * Adds course to schedule if found in catalog
	 * 
	 * @param name name of Course added
	 * @param section section of Course added
	 * @return true if Course was added to schedule and exists in catalog. False is there is a duplicate Course in catalog
	 * @throws IllegalArgumentException if the desired Course is already in the students schedule 
	 * @throws IllegalArgumentException if there is a scheduling conflict between courses
	 */
	public boolean addCourseToSchedule(String name, String section) {
		
		for(int i = 0; i < catalog.size(); i++) {
			
			if(catalog.get(i).getName().equals(name)) {
				Course addedCourse = getCourseFromCatalog(name, section);
				for(int j = 0; j < schedule.size(); j++) {
					if(schedule.get(j).isDuplicate(addedCourse)) {
						throw new IllegalArgumentException("You are already enrolled in " + name);
					}
					
					try{
						schedule.get(j).checkConflict(addedCourse);
					} catch(ConflictException e) {
						throw new IllegalArgumentException("The course cannot be added due to a conflict.");
					}
					
				}
				schedule.add(addedCourse);
				
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes Activities from student schedule if they are present
	 * 
	 * @param idx index of the activity in the schedule the client wishes to remove
	 * @return true true if course was successfully removed and false otherwise
	 * @throws IndexOutOfBoundsException if idx is not within the index of schedule
	 */
	public boolean removeActivityFromSchedule(int idx) {
		try {
			if(idx <= schedule.size() && idx >= 0) {
				schedule.remove(idx);
				return true;
			}	
			
			return false;
		} catch(IndexOutOfBoundsException e) {
			return false;
		}
		
	}
	/**
	 * Adds a new Event to schedule and checks if that event is already in Schedule
	 * 
	 * @param eventTitle title of the newly added Event
	 * @param eventMeetingDays days the newly added Event meets
	 * @param eventStartTime start time of the newly added Event
	 * @param eventEndTime end time of the newly added Event
	 * @param eventDetails details about the newly added Event
	 * @throws IllegalArgumentException if there is a duplicate event
	 * @throws IllegalArgumentException if there is a scheduling conflict between events
	 */
	public void addEventToSchedule(String eventTitle, String eventMeetingDays, int eventStartTime, int eventEndTime, String eventDetails) {
		Event addEvent = new Event(eventTitle, eventMeetingDays, eventStartTime, eventEndTime, eventDetails);
		
		for(int i = 0; i < schedule.size(); i++) {
			if(schedule.get(i).isDuplicate(addEvent)) {
				throw new IllegalArgumentException("You have already created an event called " + eventTitle);
			}
			
			try{
				schedule.get(i).checkConflict(addEvent);
			} catch(ConflictException e) {
				throw new IllegalArgumentException("The event cannot be added due to a conflict.");
			}
		}
		
		schedule.add(addEvent);
	}
	
	
	/**
	 * Resets schedule to an empty ArrayList
	 * 
	 */
	public void resetSchedule() {
		schedule = new ArrayList<>();
		
	}

}
