/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Checks for scheduling conflicts between Activities in WolfScheduler's schedule
 * 
 * @author yash
 *
 */
public interface Conflict {

	/**
	 * Method that actually checks the conflict in schedule
	 *
	 * @param possibleConflictingActivity The activity that is being compared for scheduling conflict 
	 * @throws ConflictException Checked exception thrown if a scheduling conflict exists
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;

}
