package edu.ncsu.csc216.incident_management.model.command;
/**
 * Encapsulates the information about a user command
 * that would lead to a transition. Contains four inner enumerations.
 * 
 * @author William
 *
 */
public class Command {
	/** information about on hold caller code **/
	public static final String OH_CALLER = "Awaiting Caller";
	/** information about on hold change **/
	public static final String OH_CHANGE = "Awaiting Change";
	/** information about on hold vendor **/
	public static final String OH_VENDOR = "Awaiting Vendor";
	/** information regarding resolution of incident **/
	public static final String RC_PERMANENTLY_SOLVED = "Permanently Solved";
	/** information regarding resolution **/
	public static final String RC_WORKAROUND = "Workaround";
	/** information regarding resolution **/
	public static final String RC_NOT_SOLVED = "Not Solved";
	/** information regarding resolution **/
	public static final String RC_CALLER_CLOSED = "Caller Closed";
	/** information regarding resolution **/
	public static final String CC_DUPLICATE = "Duplicate";
	/** information about cancellation **/
	
	
	//inner classes enum
	//CancellationCode
	//ResolutionCode 
	//OnHoldReason
	//CommandValue
}
