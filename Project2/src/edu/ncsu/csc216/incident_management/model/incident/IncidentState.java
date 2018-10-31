package edu.ncsu.csc216.incident_management.model.incident;

import edu.ncsu.csc216.incident_management.model.command.Command;

/**
 * Interface for states in the Incident Manager State Pattern.  All 
 * concrete incident states must implement the IncidentState interface.
 * 
 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu) 
 */
public interface IncidentState {
	
	/**
	 * Update the ManagedIncident based on the given Command.
	 * An UnsupportedOperationException is thrown if the CommandValue
	 * is not a valid action for the given state.  
	 * @param command Command describing the action that will update the ManagedIncident's
	 * state.
	 * @throws UnsupportedOperationException if the CommandValue is not a valid action
	 * for the given state.
	 */
	void updateState(Command command);
	
	/**
	 * Returns the name of the current state as a String.
	 * @return the name of the current state as a String.
	 */
	String getStateName();

}