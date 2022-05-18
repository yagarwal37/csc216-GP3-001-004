package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_scheduler.course.Course;

/**
 * Reads Course records from text files. Writes a set of CourseRecords to a
 * file.
 * 
 * @author Yash Agarwal
 */
public class CourseRecordIO {

	/**
	 * Creates a Course object based off a user input String
	 * 
	 * @param input user input string with all course details
	 * @return Course fully constructed Course object after user gives input string
	 * @throws IllegalArgumentException if input is invalid or an element is absent
	 */
	public static Course readCourse(String input) {
		Scanner reader = new Scanner(input);
		reader.useDelimiter(",");

		String name = "";
		String title = "";
		String section = "";
		int credits = -1;
		String instructor = "";
		String meetingDays = "";

		int startTime = 0;
		int endTime = 0;

		try {
			name = reader.next();
			title = reader.next();
			section = reader.next();
			credits = reader.nextInt();
			instructor = reader.next();
			meetingDays = reader.next();

			if ("A".equals(meetingDays)) {
				if (reader.hasNext()) {
					reader.close();
					throw new IllegalArgumentException();
				} else {
					reader.close();
					return new Course(name, title, section, credits, instructor, meetingDays, 0, 0);
				}
			} else {
				startTime = reader.nextInt();
				endTime = reader.nextInt();
				if (reader.hasNext()) {
					reader.close();
					throw new IllegalArgumentException();
				}
				reader.close();
				return new Course(name, title, section, credits, instructor, meetingDays, startTime, endTime);
			}

		} catch (InputMismatchException e) {
			throw new IllegalArgumentException();

		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static ArrayList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		ArrayList<Course> courses = new ArrayList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the ArrayList with all the courses we read!
		return courses;
	}

}