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
	
	public CommandValue getCommand() {
		return this.c;
	}
	
	public String getOwnerId() {
		return this.ownerId;
	}
	
	public ResolutionCode getResolutionCode() {
		return this.resolutionCode;
	}
	
	public String getWorkNote() {
		return this.note;
	}
	
	public OnHoldReason getOnHoldReason() {
		return this.onHoldReason;
	}
	
	public CancellationCode getCancellationCode() {
		return this.cancellationCode;
	}
	
	//inner classes enum
	//CancellationCode
	//ResolutionCode 
	//OnHoldReason
	//CommandValue
	public enum CommandValue {INVESTIGATE, HOLD, RESOLVE, CONFIRM, REOPEN, CANCEL}
	
	public enum OnHoldReason {AWAITING_CALLER, AWAITING_CHANGE, AWAITING_VENDOR}
	
	public enum ResolutionCode {PERMANENTLY_SOLVED, WORKAROUND, NOT_SOLVED, CALLER_CLOSED}
	
	public enum CancellationCode{DUPLICATE, UNNECESSARY, NOT_AN_INCIDENT}
}
