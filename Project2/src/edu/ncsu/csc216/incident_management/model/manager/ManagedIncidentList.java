package edu.ncsu.csc216.incident_management.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;


public class ManagedIncidentList {

	/** the array list of managed incidents **/
	private ArrayList<ManagedIncident> incidents;
	
	/**
	 * the constructor for the managed incident list
	 */
	public ManagedIncidentList() {
		//To-Do
	}
	/**
	 * adds incidents to the list
	 * 
	 * @param incidentList
	 * 			the list of incidents to be added
	 */
	public void addXMLIncidents(List<Incident> incidentList) {
		//to-do
	}
	/**
	 * adds an incident to the list
	 * @param caller
	 * 			the caller
	 * @param category
	 * 			the category
	 * @param priority
	 * 			the priority
	 * @param name
	 * 			the name of the incident
	 * @param workNote
	 * 			worknote associated with the incident
	 * @return int
	 * 			the number of the incident
	 */
	public int addIncident(String caller, Category category, Priority
								priority, String name, String workNote) {
		return 0;
	}
	/**
	 * returns the list of all managed incidents
	 * @return List<ManagedIncident>
	 * 			the list of all managed incidents
	 */
	public List<ManagedIncident> getManagedIncidents(){
		return null;
	}
	/**
	 * filters incidents by category
	 * @param category
	 * 			category to filter incidents by
	 * @return List<ManagedIncident>
	 * 			the filtered list of managed incidents
	 */
	public List<ManagedIncident> getIncidentsByCategory(Category category){
		return null;
	}
	/**
	 * gets an incident by the id number
	 * @param id
	 * 			the id of the incident
	 * @return ManagedIncident
	 * 			the managed incident
	 */
	public ManagedIncident getIncidentById(int id) {
		return null;
	}
	/**
	 * executes a given command
	 * @param i
	 * 			unsure
	 * @param c
	 * 			the command to be executed
	 */
	public void executeCommant (int i, Command c) {
		//to-do
	}
	/**
	 * deletes an incident from the list by the id
	 * @param id
	 * 			the id of the incident to delete
	 */
	public void deleteIncidentById(int id) {
		//to-do
	}
	
}
