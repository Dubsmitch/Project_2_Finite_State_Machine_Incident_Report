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
		incidents = new ArrayList<ManagedIncident>(1);
		
		ManagedIncident.setCounter(0);
		
	}
	/**
	 * adds incidents to the list
	 * 
	 * @param incidentList
	 * 			the list of incidents to be added
	 */
	public void addXMLIncidents(List<Incident> incidentList) {
		int sizeOfNewList = incidentList.size();
		int maxi = 0;
		for (int i = 0; i < sizeOfNewList; i++) {
			ManagedIncident q = new ManagedIncident(incidentList.get(i));
			incidents.add(q);
			if (q.getIncidentId() > maxi) {
				maxi = q.getIncidentId();
			}
		}
		ManagedIncident.setCounter(maxi + 1);
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
		ManagedIncident a = new ManagedIncident (caller, category, priority, name, workNote);
		incidents.add(a);		
		return a.getIncidentId();
	}
	/**
	 * returns the list of all managed incidents
	 * @return List<ManagedIncident>
	 * 			the list of all managed incidents
	 */
	public List<ManagedIncident> getManagedIncidents(){

		return (List<ManagedIncident>) incidents;
	}
	/**
	 * filters incidents by category
	 * @param category
	 * 			category to filter incidents by
	 * @return List<ManagedIncident>
	 * 			the filtered list of managed incidents
	 */
	public List<ManagedIncident> getIncidentsByCategory(Category category){
		ArrayList<ManagedIncident> thingList = new ArrayList<ManagedIncident>(incidents.size());
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getCategory() == category) {
				thingList.add(incidents.get(i));
			}
		}
		
		return (List<ManagedIncident>) thingList;
	}
	/**
	 * gets an incident by the id number
	 * @param id
	 * 			the id of the incident
	 * @return ManagedIncident
	 * 			the managed incident
	 */
	public ManagedIncident getIncidentById(int id) {
		
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getIncidentId() == id) {
				return incidents.get(i);
			}
		}
		return null;
	}
	/**
	 * executes a given command
	 * @param i
	 * 			unsure
	 * @param c
	 * 			the command to be executed
	 */
	public void executeCommand (int id, Command c) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getIncidentId() == id) {
				incidents.get(i).update(c);
			}
		}
	}
	/**
	 * deletes an incident from the list by the id
	 * @param id
	 * 			the id of the incident to delete
	 */
	public void deleteIncidentById(int id) {
		for (int i = 0; i < incidents.size(); i++) {
			if (incidents.get(i).getIncidentId() == id) {
				incidents.remove(i);
			}
		}
	}
	
}
