package edu.ncsu.csc216.incident_management.model.manager;


import java.util.ArrayList;

import edu.ncsu.csc216.incident.io.IncidentIOException;
import edu.ncsu.csc216.incident.io.IncidentReader;
import edu.ncsu.csc216.incident.io.IncidentWriter;
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
	private ManagedIncidentList incidentList;
	
	/** the singleton instance **/
	private static IncidentManager singleton;
	
	/**
	 * creates a singleton of IncidentManager
	 * if there is already an instance of the manager
	 * then the current one is returned, else
	 * one is created
	 */
	private IncidentManager() {
		incidentList = new ManagedIncidentList();
	}
	
	/**
	 * checks for an instance of the incidentmanager,
	 * if there is not one, one is made, else the
	 * existing on is returned
	 * 
	 * @return IncidentManager
	 * 			the single instance of the manager (or a new one if there
	 * 			isnt already one
	 */
	public static IncidentManager getInstance() {
		if (singleton == null) {
			singleton = new IncidentManager();
		} 
		
		return singleton;
	}
	
	/**
	 * saves list to a file
	 * @param fileName
	 * 			the name of the file
	 */
	public void saveManagedIncidentsToFile(String fileName) {
		IncidentWriter writer = new IncidentWriter(fileName);
		for (int i = 0; i < incidentList.getManagedIncidents().size(); i++) {
			writer.addItem(incidentList.getManagedIncidents().get(i).getXMLIncident());
		}
		
	}
	
	/**
	 * loads list from a file
	 * @param fileName
	 * 			the name of the file to load from
	 */
	public void loadManagedIncidentsFromFile(String fileName) {
		try {
			IncidentReader reader = new IncidentReader(fileName);
			//System.out.println(reader.getIncidents().size());
			//for testing
			//for (int i = 0; i < reader.getIncidents().size(); i++) {

				
				//newList.add(reader.getIncidents().get(i));
			//}
			//reader.getIncidents().get(i)
			this.incidentList.addXMLIncidents(reader.getIncidents());
			
				
		
		} catch (IncidentIOException e) {
			throw new IllegalArgumentException ("Cannot read from file");
		}
		
		
	}
	/**
	 * This method is for testing only, to return
	 * the list that I need
	 * @return ManagedIncidentList
	 * 			the list of managedIncidents that is instantiated here
	 */
	//for testing only
	protected ManagedIncidentList getList() {
		return this.incidentList;
	}
	/**
	 * creates a new managed incident list
	 */
	public void createNewManagedIncidentList() {
		incidentList = new ManagedIncidentList();
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
		//this should gets an array of only a specific category
		if (category == null) {
			throw new IllegalArgumentException ("category cannot be null");
		}
		
		ArrayList<ManagedIncident> newIncidentList = new ArrayList<ManagedIncident>();
		newIncidentList = (ArrayList<ManagedIncident>) incidentList.getIncidentsByCategory(category);
		String[][] incidentArray = new String[newIncidentList.size()][5];
			for (int j = 0; j < newIncidentList.size(); j++) {
				for (int i = 0; i < 5; i++) {
					if (i == 0) {
						incidentArray[j][0] = Integer.toString(newIncidentList.get(j).getIncidentId());
					} else if (i == 1) {
						incidentArray[j][1] = newIncidentList.get(j).getCategoryString();
					} else if (i == 2) {
						incidentArray[j][2] = newIncidentList.get(j).getState().getStateName();
					} else if (i == 3) {
						incidentArray[j][3] = newIncidentList.get(j).getPriorityString();
					} else if (i == 4) {
						incidentArray[j][4] = newIncidentList.get(j).getName();
					}
				}
			}
		return incidentArray;
	}
	/**
	 * returns a string array of managed incidents
	 * @return String[][]
	 * 			the String representation of managed incidents
	 */
	public String[][] getManagedIncidentsAsArray() {
		String[][] incidentArray = new String[incidentList.getManagedIncidents().size()][5];
		for (int j = 0; j < incidentList.getManagedIncidents().size(); j++) {
			for (int i = 0; i < 5; i++) {
				if (i == 0) {
					incidentArray[j][0] = Integer.toString(incidentList.getManagedIncidents().get(j).getIncidentId());
				} else if (i == 1) {
					incidentArray[j][1] = incidentList.getManagedIncidents().get(j).getCategoryString();
				} else if (i == 2) {
					incidentArray[j][2] = incidentList.getManagedIncidents().get(j).getState().getStateName();
				} else if (i == 3) {
					incidentArray[j][3] = incidentList.getManagedIncidents().get(j).getPriorityString();
				} else if (i == 4) {
					incidentArray[j][4] = incidentList.getManagedIncidents().get(j).getName();
				}
			}
		}
	return incidentArray;
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
		return incidentList.getIncidentById(id);
	}
	/**
	 * executes the command
	 * @param i
	 * 		the id of the incident
	 * @param c
	 * 		the command
	 */
	public void executeCommand(int i, Command c) {
		incidentList.getIncidentById(i).update(c);
	}
	/**
	 * deletes a managed incident by id
	 * @param id
	 * 			the id of the incident to be deleted
	 */
	public void deleteManagedIncidentById(int id) {
		incidentList.deleteIncidentById(id);
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
		incidentList.addIncident(caller, category, priority, name, workNote);
	}
	
}
	
