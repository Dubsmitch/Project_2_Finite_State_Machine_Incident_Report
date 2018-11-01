package edu.ncsu.csc216.incident_management.model.command;
//TO-DO
	// finish the command constructor

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
	public static final String CC_UNNECESSARY = "Unnecessary";
	/** information about cancellation **/
	public static final String CC_NOT_AN_INCIDENT = "Not and incident";
	
	/** field for ownerID **/
	private String ownerId;
	/** field for note **/
	private String note;
	
	/** instance of the cancelation code **/
	//need to make the inner class first
	private final CancellationCode cancellationCode;
	/** instance of the resolution code **/
	//need to make the inner class before this will compile
	private final ResolutionCode resolutionCode;
	/** instance of the on hold reason **/
	//need to make the inner class
	private final OnHoldReason onHoldReason;
	/** instance of command value **/
	//need to make the inner class first
	private final CommandValue c;
	/**
	 * constructor for a command
	 * 
	 * @param c
	 * 		the command value
	 * @param ownerId
	 * 		the owner Id
	 * @param onHoldReason
	 * 		The reason that the incident is on hold
	 * @param resolutionCode
	 * 		The reason that the incident has been resolved
	 * @param cancellationCode
	 * 		the reason that the incident has been  cancelled
	 * @param note
	 * 		the work note associated with the incident
	 */
	public Command (CommandValue c, String ownerId,
			OnHoldReason onHoldReason, ResolutionCode resolutionCode, 
			CancellationCode cancellationCode, String note) {
	
	this.c = c;
	this.onHoldReason = onHoldReason;
	this.resolutionCode = resolutionCode;
	this.cancellationCode = cancellationCode;
	
	this.ownerId = ownerId;
	this.note = note;
	}
	/**
	 * the getter for the command value
	 * 
	 * @return CommandValue
	 * 	the command value
	 */
	public CommandValue getCommand() {
		return this.c;
	}
	/**
	 * the getter for owner Id
	 * @return String
	 * 			the incident's owner ID
	 */
	public String getOwnerId() {
		return this.ownerId;
	}
	/**
	 * the getter for the resolution code
	 * @return Resolution code
	 * 			the Incident's resolution code
	 */
	public ResolutionCode getResolutionCode() {
		return this.resolutionCode;
	}
	/**
	 * the getter for the work note
	 * 
	 * @return String
	 * 			the work note associated with the incident
	 */
	public String getWorkNote() {
		return this.note;
	}
	/**
	 * getter for OnHoldReason
	 * @return OnHoldReason
	 * 			returns the incident's on hold reason
	 */
	public OnHoldReason getOnHoldReason() {
		return this.onHoldReason;
	}
	/**
	 * getter for the cancellation code
	 * @return CancellationCode
	 * 			returns the incident's cancellation code
	 */
	public CancellationCode getCancellationCode() {
		return this.cancellationCode;
	}
	
	//inner classes enum
	//CancellationCode
	//ResolutionCode 
	//OnHoldReason
	//CommandValue
	/** the enumeration for the command value **/
	public enum CommandValue { INVESTIGATE, HOLD, RESOLVE, CONFIRM, REOPEN, CANCEL }
	/** the enumeration for the on hold reason **/
	public enum OnHoldReason { AWAITING_CALLER, AWAITING_CHANGE, AWAITING_VENDOR }
	/** the enumeration for the resolution code **/
	public enum ResolutionCode { PERMANENTLY_SOLVED, WORKAROUND, NOT_SOLVED, CALLER_CLOSED }
	/** the enumeration for the cancellation code **/
	public enum CancellationCode { DUPLICATE, UNNECESSARY, NOT_AN_INCIDENT }
	
}
