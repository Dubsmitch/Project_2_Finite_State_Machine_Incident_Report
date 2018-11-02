package edu.ncsu.csc216.incident_management.model.incident;

import java.util.ArrayList;

import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;
import edu.ncsu.csc216.incident_management.model.manager.ManagedIncidentList;
//javadoc this//

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
	/** final for the Cancelled Name **/
	public static final String CANCELLED_NAME = "Cancelled";
	
	/** field for counter **/
	private static int counter = 0;
	
	//instances of enums//
	/** enum for priority **/
	private Priority priority;
	/** enum for cancellation code **/
	private CancellationCode cancellationCode;
	/** instance of enum class resolution code **/
	private ResolutionCode resolutionCode;
	/** instance of onHoldReason enum class **/
	private OnHoldReason onHoldreason;
	
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
	/** final instance of the cancelled state **/
	private final IncidentState cancelledState = new CancelledState();
	/** instance of state **/
	private final IncidentState state = newState;
	
	
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
		//make me
	}
	/**
	 * creates an incident
	 * @param incident
	 * 		the new incident
	 */
	public ManagedIncident(Incident incident) {
		//makes an incident
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
		return 0;
	}
	/**
	 * returns the change request
	 * @return String
	 * 		the change request
	 */
	public String getChangeRequest() {
		return null;
	}
	/**
	 * returns the category of the current
	 * Incident
	 * 
	 * @return Category
	 * 		the category of the incident
	 */
	public Category getCategory() {
		return null;
	}
	/**
	 * returns the category of the current Incident
	 * 
	 * @return String
	 * 		the category of the incident in string form
	 */
	public String getCategoryString() {
		return null;
	}
	
	/**
	 * sets the category
	 * @param String
	 * 		the category to set the incident too
	 */
	private void setCategory(String string) {
		//create this
	}
	
	/**
	 * returns the priority of the incident in string form
	 * @return String
	 * 		the priority of the incident
	 */
	public String getPriorityString() {
		return null;
	}
	
	/**
	 * sets the priority of an incident
	 * @param string
	 * 		the string to set the priority of the incident to   
	 */
	private void setPriority(String string) {
		// create this
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
		return null;
	}
	/**
	 * Sets the on hold reason for the current Incident
	 * @param string
	 */
	private void setOnHoldReason(String string) {
		//create this
	}
	/**
	 * returns the cancellation code associated with the current Incident
	 * 
	 * @return String
	 * 		the string of the cancellation
	 * 		code associated with the Incident
	 */
	public String getCancellationCodeString() {
		return null;
	}
	/**
	 * sets the cancellation code to be associated with the current Incident
	 * 
	 * @param string
	 * 		the cancellation code to be associated with the Incident
	 */
	private void setCancellationCode(String string) {
		//create this
	}
	/**
	 * returns the incident state associated with the current Incident
	 * 
	 * @return IncidentState
	 * 		the incident state associated with the Incident
	 */
	public IncidentState getState() {
		return null;
	}
	/**
	 * sets the State of the current Incident
	 * 
	 * @param String
	 * 		the state to set the current state to
	 */
	private void setState (String string) {
		//create this
	}

	/**
	 * returns the resolution code associated with the current Incident
	 * 
	 * @return ResolutionCode
	 * 		the resolution code associated with the Incident
	 */
	public ResolutionCode getResolutionCode() {
		return null;
	}
	/**
	 * returns the resolution code associated with the current Incident
	 * 
	 * @return String
	 * 		the resolution code associated with the Incident
	 */
	public String getResolutionCodeString () {
		return null;
	}
	/**
	 * sets the resolution code of the current Incident
	 * 
	 * @param String
	 * 		the resolution code to be associated with the Incident
	 */
	private void setResolutionCode(String string) {
		//create this
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
		return null;
	}
	/**
	 * updates the incident
	 * @param command
	 * 		the command that updates the incident
	 */
	public void update(Command command) {
		//make this
	}
	/**
	 * returns the current incident 
	 * @return Incident
	 * 		the current XML incident
	 */
	public Incident getXMLIncident() {
		return null;
	}
	/**
	 * allows the user to set the counter
	 * 
	 * @param count
	 * 		the number of incidents
	 */
	public static void setCounter(int count) {
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
			// TODO Auto-generated method stub
		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
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
			// TODO Auto-generated method stub
		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
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
			// TODO Auto-generated method stub
		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
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
		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	/**
	 * creates the class that allows an incident to
	 * be cancelled
	 * 
	 * @author William
	 *
	 */
	public class CancelledState implements IncidentState {
		/**
		 * updates the state 
		 * 
		 * @param command
		 * 		the command that changes the state
		 */
		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}
		/**
		 * returns the state's name
		 * @return String
		 * 		the state's name
		 */
		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
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
			// TODO Auto-generated method stub
		
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
			return null;
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
