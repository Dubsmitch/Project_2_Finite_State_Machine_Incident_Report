package edu.ncsu.csc216.incident_management.model.incident;

import java.util.ArrayList;

import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident.xml.WorkNotes;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.CommandValue;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;


/**
 * creates the class that allows for the creation of an incident
 * and that also manages those incidents (updating and managing them)
 * 
 * @author William
 *
 */
public class ManagedIncident {
	/** instance of the cancellation code **/
	
	/** final for C Inquiry **/
	public static final String C_INQUIRY = "Inquiry";
	/** final for C Software **/
	public static final String C_SOFTWARE = "Software";
	/** final for C hardware **/
	public static final String C_HARDWARE = "Hardware";
	/** final for C Network **/
	public static final String C_NETWORK = "Network";
	/** final for C Database **/
	public static final String C_DATABASE = "Database";
	/** final for Priority == Urgent **/
	public static final String P_URGENT = "Urgent";
	/** final for Priority == High **/
	public static final String P_HIGH = "High";
	/** final for Priority == Medium **/
	public static final String P_MEDIUM = "Medium";
	/** final for Priority == Low **/
	public static final String P_LOW = "Low";
	
	/** field for incident ID **/
	private int incident;
	/** field for caller **/
	private String caller;
	/** field for owner **/
	private String owner;
	/** field for name **/
	private String name;
	/** field for the change request **/
	private String changeRequest;
	/** field for the notes **/
	private ArrayList<String> notes;
	
	/** Final for new name **/
	public static final String NEW_NAME = "New";
	/** Final for the in progress name **/
	public static final String IN_PROGRESS_NAME = "In Progress";
	/** final for the on Hold Name **/
	public static final String ON_HOLD_NAME = "On Hold";
	/** final for the resolved name **/
	public static final String RESOLVED_NAME = "Resolved";
	/** final for the Closed name **/
	public static final String CLOSED_NAME = "Closed";
	/** final for the Canceled Name **/
	public static final String CANCELED_NAME = "Canceled";
	
	/** field for counter **/
	private static int counter = 0;
	
	//instances of enums//
	/** enum for category **/
	private Category category;
	/** enum for priority **/
	private Priority priority;
	/** enum for cancellation code **/
	private CancellationCode cancellationCode;
	/** instance of enum class resolution code **/
	private ResolutionCode resolutionCode;
	/** instance of onHoldReason enum class **/
	private OnHoldReason onHoldReason;
	
	//instances of states//
	/** instance of new state **/
	private final IncidentState newState = new NewState();
	/** final instance of in progress state **/
	private final IncidentState inProgressState = new InProgressState();
	/** final instance of the on Hold State **/
	private final IncidentState onHoldState = new OnHoldState();
	/** final instance of the Resolved State **/
	private final IncidentState resolvedState = new ResolvedState();
	/** final instance of the closed State **/
	private final IncidentState closedState = new ClosedState();
	/** final instance of the canceled state **/
	private final IncidentState canceledState = new CanceledState();
	/** instance of state **/
	private IncidentState state;
	
	
	/**
	 * Constructs a managed incident
	 * 
	 * @param caller
	 * 		The caller creating the incident
	 * @param category
	 * 		the category of the incident
	 * @param priority
	 * 		the priority of the incident
	 * @param name
	 * 		the name of the incident
	 * @param workNote
	 * 		the work note associated with the incident
	 */
	public ManagedIncident (String caller, Category category, Priority priority, String name, String workNote) {
		if (caller.equals("") ||  
				category == null || priority == null || name.equals("") || workNote.equals("")) {
			throw new IllegalArgumentException ("no fields can be null or empty");
		}
		
		//incidentId, caller, category, state, priority, owner, name, onHoldReason, 
		//changeRequest, resolutionCode, cancellationCode, and all of its notes
		if (caller.length() < 1) {
			throw new IllegalArgumentException ("Caller must have more than 1 letter");
		}
		//if (category != Category.DATABASE || category != Category.HARDWARE || category != Category.INQUIRY
		//		|| category != Category.NETWORK || category != Category.SOFTWARE) {
		//	throw new IllegalArgumentException ("category must be set");
		//}
		
		this.caller = caller;
		this.category = category;
		this.priority = priority;
		this.name = name;
		this.owner = null;
		
		ArrayList<String> note = new ArrayList<String>(1);
		note.add(workNote);
		this.notes = note;
		
		this.changeRequest = null;
		this.state = newState;
		this.changeRequest = null;
		this.setCancellationCode(null);
		this.setResolutionCode(null);
		this.onHoldReason = null;
		this.incident = counter;
		incrementCounter();
		
		
	}
	/**
	 * creates an incident
	 * @param incident
	 * 		the new incident
	 */
	public ManagedIncident(Incident incident) {
		//incidentId, caller, category, state, priority, owner, name, onHoldReason, 
		//changeRequest, resolutionCode, cancellationCode, and all of its notes
		

		this.caller = incident.getCaller();
		this.setState(incident.getState());
		this.setPriority(incident.getPriority());
		this.name = incident.getName();
		this.notes =  (ArrayList<String>) incident.getWorkNotes().getNotes();
		this.setCategory(incident.getCategory());
		this.owner = incident.getOwner();
		this.changeRequest = incident.getChangeRequest();
		this.setOnHoldReason(incident.getOnHoldReason());
		this.setResolutionCode(incident.getResolutionCode());
		this.setCancellationCode(incident.getCancellationCode());
		this.incident = incident.getId();
	}
	
	
	/**
	 * increments the counter (keep track of incident)
	 */
	public static void incrementCounter() {
		counter = counter + 1;
	}
	/**
	 * gets the incident id
	 * @return int
	 * 		the incident id
	 */
	public int getIncidentId() {
		return this.incident;
	}
	/**
	 * returns the change request
	 * @return String
	 * 		the change request
	 */
	public String getChangeRequest() {
		return this.changeRequest;
	}
	/**
	 * returns the category of the current
	 * Incident
	 * 
	 * @return Category
	 * 		the category of the incident
	 */
	public Category getCategory() {
		return this.category;
	}
	/**
	 * returns the category of the current Incident
	 * 
	 * @return String
	 * 		the category of the incident in string form
	 */
	public String getCategoryString() {
				
		if (this.category == Category.INQUIRY) {
			return C_INQUIRY;
		} else if (this.category == Category.SOFTWARE) {
			return C_SOFTWARE;
		} else if (this.category == Category.HARDWARE) {
			return C_HARDWARE;
		} else if (this.category == Category.NETWORK) {
			return C_NETWORK;
		} else if (this.category == Category.DATABASE) {
			return C_DATABASE;
		} else {
			throw new IllegalArgumentException ("Incident must have a category");
		}
	}
	/**
	 * sets the category
	 * @param String
	 * 		the category to set the incident too
	 */
	private void setCategory(String string) {
	//Category
			// enumeration for setting the category **/
			//public enum Category { INQUIRY, SOFTWARE, HARDWARE, NETWORK, DATABASE }
			
			//if category is inquiry
			if (string.equals(C_INQUIRY)) {
				this.category = Category.INQUIRY;
			//if category is software
			} else if (string.equals(C_SOFTWARE)) {
				this.category = Category.SOFTWARE;
			//if category is hardware
			} else if (string.equals(C_HARDWARE)) {
				this.category = Category.HARDWARE;
			//if category is network
			} else if (string.equals(C_NETWORK)) {
				this.category = Category.NETWORK;
			//if category is database
			} else if (string.equals(C_DATABASE)) {
				this.category = Category.DATABASE;
			} else {
				throw new IllegalArgumentException ("incident must have a category");
			}
		
	}
	
	/**
	 * returns the priority of the incident in string form
	 * @return String
	 * 		the priority of the incident
	 */
	public String getPriorityString() {
		
		if (this.priority == Priority.URGENT) {
			return P_URGENT;
		} else if (this.priority == Priority.HIGH) {
			return P_HIGH;
		} else if (this.priority == Priority.MEDIUM) {
			return P_MEDIUM;
		} else if (this.priority == Priority.LOW) {
			return P_LOW;
		} else {
			return null;
		}
	}

	
	/**
	 * sets the priority of an incident
	 * @param string
	 * 		the string to set the priority of the incident to   
	 */
	private void setPriority(String string) {

		
		//if priority is urgent
		if (string.equals(P_URGENT)) {
			
			this.priority =  Priority.URGENT;
		//if priority is High
		} else if (string.equals(P_HIGH)) {
				
			this.priority = Priority.HIGH;
		//if priority is Medium
		} else if (string.equals(P_MEDIUM)) {
								
			this.priority =  Priority.MEDIUM;
		//if priority is Medium
		} else if (string.equals(P_LOW)) {
									
			this.priority =  Priority.LOW;
		} else {
			throw new IllegalArgumentException ("can't convert into a priority");
		}
		
	}

	/**
	 * returns the on hold reason associated with
	 * the current incident
	 * 
	 * @return String
	 * 		the on hold reason associated with 
	 * 		the incident
	 */
	public String getOnHoldReasonString() {
		//public enum OnHoldReason { AWAITING_CALLER, AWAITING_CHANGE, AWAITING_VENDOR }
//		/** information about on hold caller code **/
//		public static final String OH_CALLER = "Awaiting Caller";
//		/** information about on hold change **/
//		public static final String OH_CHANGE = "Awaiting Change";
//		/** information about on hold vendor **/
//		public static final String OH_VENDOR = "Awaiting Vendor";
//		/** information regarding resolution of incident **/
		
//		if (onHoldReasonString == Command.OH_CALLER) {
//			return OnHoldReason.AWAITING_CALLER;
//		} else if (onHoldReasonString == Command.OH_CHANGE) {
//			return OnHoldReason.AWAITING_CHANGE;
//		} else if (onHoldReasonString == Command.OH_VENDOR) {
//			return OnHoldReason.AWAITING_VENDOR;
//		} else {
//			return null;
//		}
		
		if (this.onHoldReason == Command.OnHoldReason.AWAITING_CALLER) {
			return Command.OH_CALLER;
		} else if (this.onHoldReason == Command.OnHoldReason.AWAITING_CHANGE) {
			return Command.OH_CHANGE;
		} else if (this.onHoldReason == Command.OnHoldReason.AWAITING_VENDOR) {
			return Command.OH_VENDOR;

		} else {
			return null;
		}
	}
	//maybe go change the other private methods
	/**
	 * Sets the on hold reason for the current Incident
	 * @param string
	 */
	private void setOnHoldReason(String string) {

		if(string == null) {
			this.onHoldReason = null;
		} else if (string.equals(Command.OH_CALLER)) {

			this.onHoldReason = OnHoldReason.AWAITING_CALLER;
		} else if (string.equals(Command.OH_CHANGE)) {
			this.onHoldReason = OnHoldReason.AWAITING_CHANGE;
		} else if (string.equals(Command.OH_VENDOR)) {
			this.onHoldReason = OnHoldReason.AWAITING_VENDOR;
		}
	}
	/**
	 * returns the cancellation code associated with the current Incident
	 * 
	 * @return String
	 * 		the string of the cancellation
	 * 		code associated with the Incident
	 */
	public String getCancellationCodeString() {
		//public enum CancellationCode { DUPLICATE, UNNECESSARY, NOT_AN_INCIDENT }
//		/** information regarding resolution **/
//		public static final String CC_DUPLICATE = "Duplicate";
//		/** information about cancellation **/
//		public static final String CC_UNNECESSARY = "Unnecessary";
//		/** information about cancellation **/
//		public static final String CC_NOT_AN_INCIDENT = "Not and incident";
		
		if (this.cancellationCode == CancellationCode.DUPLICATE) {
			return Command.CC_DUPLICATE;
		} else if (this.cancellationCode == CancellationCode.UNNECESSARY) {
			return Command.CC_UNNECESSARY;
		} else if (this.cancellationCode == CancellationCode.NOT_AN_INCIDENT) {
			return Command.CC_NOT_AN_INCIDENT;
		} else {
			return null;
		}
	}
	/**
	 * sets the cancellation code to be associated with the current Incident
	 * 
	 * @param string
	 * 		the cancellation code to be associated with the Incident
	 */
	private void setCancellationCode(String string) {
		//public enum CancellationCode { DUPLICATE, UNNECESSARY, NOT_AN_INCIDENT }
//		/** information regarding resolution **/
//		public static final String CC_DUPLICATE = "Duplicate";
//		/** information about cancellation **/
//		public static final String CC_UNNECESSARY = "Unnecessary";
//		/** information about cancellation **/
//		public static final String CC_NOT_AN_INCIDENT = "Not and incident";
		 if (string == null){
				this.cancellationCode = null;
	 	} else if (string.equals(Command.CC_DUPLICATE)) {
			this.cancellationCode = CancellationCode.DUPLICATE;
		} else if (string.equals(Command.CC_UNNECESSARY)) {
			this.cancellationCode = CancellationCode.UNNECESSARY;
		} else if (string.equals(Command.CC_NOT_AN_INCIDENT)) {
			this.cancellationCode = CancellationCode.NOT_AN_INCIDENT;
		}
	}
	/**
	 * returns the incident state associated with the current Incident
	 * 
	 * @return IncidentState
	 * 		the incident state associated with the Incident
	 */
	public IncidentState getState() {
		return this.state;
	}
	/**
	 * sets the State of the current Incident
	 * 
	 * @param String
	 * 		the state to set the current state to
	 */
	private void setState (String string) {
		if (string.equals(NEW_NAME)) {

			this.state = newState;
		} else if (string.equals(IN_PROGRESS_NAME)) {
			this.state = inProgressState;
		} else if (string.equals(ON_HOLD_NAME)) {
			this.state = onHoldState;
		} else if (string.equals(RESOLVED_NAME)) {
			this.state = resolvedState;
		} else if (string.equals(CLOSED_NAME)) {
			this.state = closedState;
		} else if (string.equals(CANCELED_NAME)) {
			this.state = canceledState;
		} else {
			throw new IllegalArgumentException ("must have a state");
		}
	}

	/**
	 * returns the resolution code associated with the current Incident
	 * 
	 * @return ResolutionCode
	 * 		the resolution code associated with the Incident
	 */
	public ResolutionCode getResolutionCode() {
		return this.resolutionCode;
	}
	/**
	 * returns the resolution code associated with the current Incident
	 * 
	 * @return String
	 * 		the resolution code associated with the Incident
	 */
	public String getResolutionCodeString () {
		//public enum ResolutionCode { PERMANENTLY_SOLVED, WORKAROUND, NOT_SOLVED, CALLER_CLOSED }
//		public static final String RC_PERMANENTLY_SOLVED = "Permanently Solved";
//		/** information regarding resolution **/
//		public static final String RC_WORKAROUND = "Workaround";
//		/** information regarding resolution **/
//		public static final String RC_NOT_SOLVED = "Not Solved";
//		/** information regarding resolution **/
//		public static final String RC_CALLER_CLOSED = "Caller Closed";

		if (resolutionCode == ResolutionCode.PERMANENTLY_SOLVED) {
			return Command.RC_PERMANENTLY_SOLVED;
		} else if (resolutionCode == ResolutionCode.WORKAROUND) {
			return Command.RC_WORKAROUND;
		} else if (resolutionCode == ResolutionCode.NOT_SOLVED) {
			return Command.RC_NOT_SOLVED;
		} else if (resolutionCode == ResolutionCode.CALLER_CLOSED) {
			return Command.RC_CALLER_CLOSED;
		} else {
			return null;
		}
	}
	/**
	 * sets the resolution code of the current Incident
	 * 
	 * @param String
	 * 		the resolution code to be associated with the Incident
	 */
	private void setResolutionCode(String string) {
		//public enum ResolutionCode { PERMANENTLY_SOLVED, WORKAROUND, NOT_SOLVED, CALLER_CLOSED }
//		public static final String RC_PERMANENTLY_SOLVED = "Permanently Solved";
//		/** information regarding resolution **/
//		public static final String RC_WORKAROUND = "Workaround";
//		/** information regarding resolution **/
//		public static final String RC_NOT_SOLVED = "Not Solved";
//		/** information regarding resolution **/
//		public static final String RC_CALLER_CLOSED = "Caller Closed";
		if(string == null) {
			this.resolutionCode = null;
		} else if (string.equals(Command.RC_PERMANENTLY_SOLVED)) {
			this.resolutionCode =  ResolutionCode.PERMANENTLY_SOLVED;
		} else if (string.equals(Command.RC_CALLER_CLOSED)) {
			this.resolutionCode =  ResolutionCode.CALLER_CLOSED;
		} else if (string.equals(Command.RC_WORKAROUND)) {
			this.resolutionCode =  ResolutionCode.WORKAROUND;
		} else if (string.equals( Command.RC_NOT_SOLVED)) {
			this.resolutionCode =  ResolutionCode.NOT_SOLVED;
		}
	}
	/**
	 * returns the Owner associated with the current Incident
	 * 
	 * @return String
	 * 		the owner associated with the Incident
	 */
	public String getOwner() {
		return owner;
	}
	/**
	 * returns the name associated with the current Incident
	 * 
	 * @return String
	 * 		the name associated with the Incident
	 */
	public String getName() {
		return name;
	}
	/**
	 * returns the caller information associated
	 * with the Incident
	 * @return String
	 * 		the caller
	 */
	public String getCaller() {
		return caller;
	}
	/**
	 * returns the arraylist (a list) of strings
	 * associated with the incident
	 * @return ArrayList<String>
	 * 			the array of strings containing notes 
	 * 			associated with the current incident
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}
	/**
	 * retrieves the work notes associated with the incident
	 * @return String
	 * 		the notes associated with an incident
	 */
	public String getNotesString() {
		int lengthOfNotes = this.notes.size();
		String stringOfNotes = "";
		
		for (int i = 0; i < lengthOfNotes; i++) {
			stringOfNotes = stringOfNotes + this.notes.get(i) + "\n-------\n";
		}
		return stringOfNotes;
	}
	/**
	 * updates the incident
	 * @param command
	 * 		the command that updates the incident
	 */
	public void update(Command command) {
		state.updateState(command);
	}
	/**
	 * returns the current incident for saving
	 * @return Incident
	 * 		the current XML incident
	 */
	public Incident getXMLIncident() {
//      <element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
//      <element name="caller" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="category" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="priority" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="owner" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="onhold_reason" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="change_request" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="resolution_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="cancellation_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
//      <element name="work_notes" type="{}WorkNotes"/>
        
        WorkNotes workNotes = new WorkNotes();
        workNotes.getNotes().addAll(notes);
        
		Incident a = new Incident();
		a.setCaller(this.getCaller());
		a.setId(this.getIncidentId());
		a.setCategory(this.getCategoryString());
		a.setState(this.state.getStateName());
		a.setPriority(this.getPriorityString());
		a.setOwner(this.getOwner());
		a.setName(this.getName());
		a.setOnHoldReason(this.getOnHoldReasonString());
		a.setChangeRequest(this.getChangeRequest());
		a.setResolutionCode(this.getResolutionCodeString());
		a.setCancellationCode(this.getResolutionCodeString());
		a.setWorkNotes(workNotes);
		return a;
	}
	
	/**
	 * allows the user to set the counter
	 * 
	 * @param count
	 * 		the number of incidents
	 */
	public static void setCounter(int count) {
		if (count < 0) {
			throw new IllegalArgumentException ("incident id must be greater than or equal to 0");
		}
		counter = count;
	}
	//contains the innerClasses:
	//State (Abstract)

		//NewState
		//InProgressState
		//OnHoldState
		//ResolvedState
		//ClosedState
		//CancelledState
	
	/**
	 * inner class that allows the creating of the "new state"
	 * 
	 * @author William
	 *
	 */
	public class NewState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.CANCEL) {
				notes.add(command.getWorkNote());
				cancellationCode = command.getCancellationCode();
				state = canceledState;

			} else if (command.getCommand() == CommandValue.INVESTIGATE) {
				owner = command.getOwnerId();
				notes.add(command.getWorkNote());
				state = inProgressState;

			}
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
		
			return NEW_NAME;
		}
	}
	/**
	 * state that allows for the state "in progress"
	 * 
	 * @author William
	 *
	 */
	public class InProgressState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		@Override
		public void updateState(Command command) {
			//S3
			if (command.getCommand() == CommandValue.CANCEL) {
				notes.add(command.getWorkNote());
				cancellationCode = command.getCancellationCode();
				state = canceledState;
			//S1
			} else if (command.getCommand() == CommandValue.HOLD) {
				onHoldReason = command.getOnHoldReason();
				
				notes.add(command.getWorkNote());
				state = onHoldState;
			//S2 (resolution)
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				resolutionCode = command.getResolutionCode();
				
				notes.add(command.getWorkNote());
				state = resolvedState;
			// TODO Auto-generated method stub
			}
		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			return IN_PROGRESS_NAME;
		}
	}
	
	/**
	 * creates the class that allows for the state
	 * "On hold"
	 * 
	 * @author William
	 *
	 */
	public class OnHoldState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		@Override
		public void updateState(Command command) {
			//[S1] The user clicks Reopen. The incident’s state is updated to In Progress. 
			//The note is saved with the incident. If the On Hold reason was Awaiting Change, 
			//then the note is saved as the change request for the incident. 
			//The user is returned to the incident list [UC2] and the incident’s 
			//listing reflects the updated state.
			if (command.getCommand() == CommandValue.REOPEN) {
				notes.add(command.getWorkNote());
				if (command.getOnHoldReason() == OnHoldReason.AWAITING_CHANGE) {
					changeRequest = command.getWorkNote();
				}
				state = inProgressState;
			//S2 user selects resolution codes
			//clicks resolve; updated to resolved; note is saved
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				if (command.getOnHoldReason() == OnHoldReason.AWAITING_CHANGE) {
					changeRequest = command.getWorkNote();
				}
				onHoldReason = command.getOnHoldReason();
				notes.add(command.getWorkNote());
				state = resolvedState;
			//S3 user determines the incident should be cancelled
			//clicks cancel; updates cancellation code;
			} else if (command.getCommand() == CommandValue.CANCEL) {
				cancellationCode = command.getCancellationCode();
				
				notes.add(command.getWorkNote());
				state = canceledState;
			// TODO Auto-generated method stub
			}		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			return ON_HOLD_NAME;
		}
	}
	/**
	 * creates the class that allows for the state
	 * "Resolved State"
	 * 
	 * @author William
	 *
	 */
	public class ResolvedState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		public void updateState(Command command) {
			//[S1] selects one of 4 on hold reasons
			//selects hold; updated to on hold (done), note saved
			if (command.getCommand() == CommandValue.HOLD) {
				notes.add(command.getWorkNote());
				//if (command.getOnHoldReason() == OnHoldReason.AWAITING_CHANGE) {
				//	changeRequest = command.getWorkNote();
				//}
				state = onHoldState;
			//S2 user selects to reopen
			//state set to INPROGRESS, note is saved 
			} else if (command.getCommand() == CommandValue.REOPEN) {
				//if (command.getOnHoldReason() == OnHoldReason.AWAITING_CHANGE) {
				//	changeRequest = command.getWorkNote();
				//}
				notes.add(command.getWorkNote());
				state = inProgressState;
			//S3 user confirms
			//state to closed, note saved,  
			} else if (command.getCommand() == CommandValue.CONFIRM) {				
				resolutionCode = command.getResolutionCode();
				notes.add(command.getWorkNote());
				state = closedState;
			} else if (command.getCommand() == CommandValue.CANCEL) {
				resolutionCode = command.getResolutionCode();
				cancellationCode = command.getCancellationCode();
				notes.add(command.getWorkNote());
				state = canceledState;
				
			}
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			return RESOLVED_NAME;
		}
	}
	/**
	 * creates the class that allows an incident to
	 * be cancelled
	 * 
	 * @author William
	 *
	 */
	public class CanceledState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		@Override
		public void updateState(Command command) {
			if(command.getCommand() == CommandValue.CONFIRM) {
				throw new UnsupportedOperationException ("invaild command");
			}else if(command.getCommand() == CommandValue.RESOLVE) {
				throw new UnsupportedOperationException ("invaild command");
			}else if(command.getCommand() == CommandValue.CANCEL) {
				throw new UnsupportedOperationException ("invaild command");
			}else if(command.getCommand() == CommandValue.REOPEN) {
				throw new UnsupportedOperationException ("invaild command");
			}else if(command.getCommand() == CommandValue.HOLD) {
				throw new UnsupportedOperationException ("invaild command");
			}else if(command.getCommand() == CommandValue.INVESTIGATE) {
				throw new UnsupportedOperationException ("invaild command");
			}
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			return CANCELED_NAME;
		}
	}
	/**
	 * creates a class that allows an incident to be closed
	 * 
	 * @author William
	 *
	 */
	public class ClosedState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.REOPEN) {
				notes.add(command.getWorkNote());
				//if (command.getOnHoldReason() == OnHoldReason.AWAITING_CHANGE) {
				//	changeRequest = command.getWorkNote();
				//}
				state = inProgressState;
			} else if (command.getCommand() == CommandValue.CONFIRM) {
				throw new UnsupportedOperationException ("invaild command");
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				throw new UnsupportedOperationException ("invaild command");
			} else if (command.getCommand() == CommandValue.HOLD) {
				throw new UnsupportedOperationException ("invaild command");
			} else if (command.getCommand() == CommandValue.CANCEL) {
				throw new UnsupportedOperationException ("invaild command");
			} else if (command.getCommand() == CommandValue.INVESTIGATE) {
				throw new UnsupportedOperationException ("invaild command");
			}
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			// using an enumeration 
			// Category c = Category.INQUIRY;
			return CLOSED_NAME;
		}
	}
		//enums:
	
	//Category
	/** enumeration for setting the category **/
	public enum Category { INQUIRY, SOFTWARE, HARDWARE, NETWORK, DATABASE }
	
	
	
	//Priority
	/** enumeration for setting the Priority **/
	public enum Priority { URGENT, HIGH, MEDIUM, LOW }
}
