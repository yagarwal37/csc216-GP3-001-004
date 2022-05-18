/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

/**
 * Exception class which creates a checked exception ConflictException designed to be thrown if
 * a scheduling conflict exists
 * 
 * @author yash
 *
 */
public class ConflictException extends Exception {

	/** ID Used for serialization */
	private static final long serialVersionUID = 1L;

	/** message passed with the exception */
	private static String message = "";
	
	
	/**
	 * ConflictException constructor with a defaulted message 
	 */
	public ConflictException() {
		 message = "Schedule conflict.";
	}
	
	/**
	 * ConflictException constructor with a passed in message 
	 * 
	 * @param message the string that is outputed with the ConflictException
	 */
	public ConflictException (String message) {
		ConflictException.message = message;
	}
	
	/**
	 * Returns the message of the ConflictException object
	 * 
	 * @return message the message that is associated with the ConflictException
	 */
	public String getMessage() {
		return message;
	}
}
