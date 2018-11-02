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
	private ManagedIncidentList incidentlist;
	
	/** the singleton instance **/
	private IncidentManager singleton;
	
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
	
	/**
	 * saves list to a file
	 * @param fileName
	 * 			the name of the file
	 */
	public void saveManagedIncidentsToFile(String fileName) {
		//To-Do
	}
	/**
	 * loads list from a file
	 * @param fileName
	 * 			the name of the file to load from
	 */
	public void loadManagedIncidentsToFile(String fileName) {
		//To-Do
	}
	/**
	 * creates a new managed incident list
	 */
	public void createNewManagedIncidentList() {
		//To-Do
	}
	/**
	 * returns an array sorted by category
	 * 
	 * @param category
	 * 			the category by which to sort
	 * @return String [][]
	 * 			the array of strings holding the category
	 */
	public String[][] getManagedIncidentsAsArrayByCategory(Category category) {
		return null;
	}
	/**
	 * returns a string array of managed incidents
	 * @return String[][]
	 * 			the String representation of managed incidents
	 */
	public String[][] getManagedIncidentsAsArray() {
		return null;
	}
	/**
	 * retrieves a managed incident by ID
	 * 
	 * @param id
	 * 			the id by which to retrieve a managed incident
	 * @return ManagedIncident
	 * 			the managed incident with the provided id
	 */
	public ManagedIncident getManagedIncidentById(int id) {
		return null;
	}
	/**
	 * executes the command
	 * @param i
	 * @param c
	 * 		the command
	 */
	public void executeCommand(int i, Command c) {
		//To-Do
	}
	/**
	 * deletes a managed incident by id
	 * @param id
	 * 			the id of the incident to be deleted
	 */
	public void deleteManagedIncidentById(int id) {
		//To-Do
	}
	/**
	 * adds a managed incident to the list
	 * 
	 * @param caller
	 * 			the caller for the incident
	 * @param category
	 * 			the category of the incident
	 * @param priority	
	 * 			the priority of the incident
	 * @param name
	 * 			the name of the incident
	 * @param workNote
	 * 			the note associated of the incident
	 */
	public void addManagedIncidentToList(String caller, Category category, Priority
								priority, String name, String workNote) {
		//ToDo
	}
	
}
	
