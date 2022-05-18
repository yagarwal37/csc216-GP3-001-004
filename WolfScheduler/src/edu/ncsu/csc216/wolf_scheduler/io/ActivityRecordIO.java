package edu.ncsu.csc216.wolf_scheduler.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.wolf_scheduler.course.Activity;

/**
 * Creates a file with a list of activities
 * 
 * @author yash
 *
 */
public class ActivityRecordIO {

	/**
	 * Writes the given list of Courses to
	 * 
	 * @param fileName file to write schedule of Courses to
	 * @param activities list of Courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeActivityRecords(String fileName, ArrayList<Activity> activities) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
	
		for (Activity a : activities) {
    		fileWriter.println(a.toString());
    	}
	
		fileWriter.close();
	}

}
