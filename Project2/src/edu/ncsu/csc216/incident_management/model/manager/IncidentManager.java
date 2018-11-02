package edu.ncsu.csc216.incident_management.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;

/**
 * Creates and maintains a list of managed incidents.
 * 
 * @author William
 *
 */
public class IncidentManager {
	/** creates and manages a list of managed incidents **/
	private ArrayList<ManagedIncident> incidents;
	
	/**
	 * creates a singleton of IncidentManager
	 * if there is already an instance of the manager
	 * then the current one is returned, else
	 * one is created
	 */
	private IncidentManager() {
		getInstance();
	}
	
	/**
	 * Checks for an instance of IncidentManager
	 * if there is already an instance of the manager
	 * then the current one is returned, else
	 * one is created
	 */
	public static IncidentManager getInstance() {
		return null;
	}
	
	public void saveManagedIncidentsToFile(String fileName) {
		//To-Do
	}
	
	public void loadManagedIncidentsToFile(String fileName) {
		//To-Do
	}
	
	public void createNewManagedIncidentList() {
		//To-Do
	}
	
	public String[][] getManagedIncidentsAsArrayByCategory(Category category) {
		return null;
	}
	
	public String[][] getManagedIncidentsAsArray() {
		return null;
	}
	
	public ManagedIncident getManagedIncidentById(int id) {
		return null;
	}
	
	public void executeCommand(int i, Command c) {
		//To-Do
	}
	
	public void deleteManagedIncidentById(int id) {
		//To-Do
	}
	
	public void addManagedIncidentToList(String abc, Category category, Priority
								priority, String abcd, String abcde) {
		//ToDo
	}
	
}
	
