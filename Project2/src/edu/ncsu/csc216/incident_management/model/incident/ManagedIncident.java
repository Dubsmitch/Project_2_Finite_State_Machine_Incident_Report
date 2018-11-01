package edu.ncsu.csc216.incident_management.model.incident;

import java.util.ArrayList;

import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;
import edu.ncsu.csc216.incident_management.model.manager.ManagedIncidentList;

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
	
	// instances of other classes
	/** instance of Managed Incident list **/
	private ManagedIncidentList incidents;
	
//	//public ManagedIncident ()

	
	public class NewState implements IncidentState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class InProgressState implements IncidentState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class OnHoldState implements IncidentState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class ResolvedState implements IncidentState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class CancelledState implements IncidentState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public class ClosedState implements IncidentState {

		@Override
		public void updateState(Command command) {
			// TODO Auto-generated method stub
		
		}

		@Override
		public String getStateName() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	//contains the innerClasses:
		//State (Abstract)
	
			//NewState
			//InProgressState
			//OnHoldState
			//ResolvedState
			//ClosedState
			//CancelledState
		//enums:
	
	//Category
	/** enumeration for setting the category **/
	public enum Category { INQUIRY, SOFTWARE, HARDWARE, NETWORK, DATABASE }
	
	//Priority
	/** enumeration for setting the Priority **/
	public enum Priority { URGENT, HIGH, MEDIUM, LOW }
}
