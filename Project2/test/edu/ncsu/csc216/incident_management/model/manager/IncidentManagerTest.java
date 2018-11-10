package edu.ncsu.csc216.incident_management.model.manager;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CommandValue;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
/**
 * the class that tests the incident Manager
 * class
 * 
 * @author William
 *
 */
public class IncidentManagerTest {
	/**
	 * test the singleton and loading incidents from file
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testloadManagedIncidentsFromFile() {
		IncidentManager manager = IncidentManager.getInstance();
		manager.createNewManagedIncidentList();
		manager.loadManagedIncidentsFromFile("test-files/incident1.xml");
		

		assertEquals(manager.getManagedIncidentById(1).getCaller(), "sesmith5");
		assertEquals(manager.getManagedIncidentById(5).getCaller(), "student2");
		
		assertEquals(manager.getInstance(), manager);
	
		//manager.saveManagedIncidentsToFile("test-files/incident1_resaved.xml");
		//manager.loadManagedIncidentsFromFile("test-files/incident1_resaved.xml");
	}
	
	/**
	 * test the Category array and the general array
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testArrayBuilders() {
		IncidentManager manager = IncidentManager.getInstance();
		manager.createNewManagedIncidentList();
		manager.loadManagedIncidentsFromFile("test-files/incident1.xml");
		
		assertEquals(manager.getList().getManagedIncidents().size(), 6);
		assertEquals(manager.getManagedIncidentById(1).getCaller(), "sesmith5");
		assertEquals(manager.getManagedIncidentById(5).getCaller(), "student2");
		
		assertEquals(manager.getInstance(), manager);
	
		//manager.saveManagedIncidentsToFile("test-files/incident1_resaved.xml");
		//manager.loadManagedIncidentsFromFile("test-files/incident1_resaved.xml");
		String[][] comp = new String[6][5];
		comp = manager.getManagedIncidentsAsArray();
		assertEquals(comp[0][1], "Software");
		assertEquals(comp[1][1], "Hardware");
		assertEquals(comp[2][1], "Hardware");
		assertEquals(comp[3][1], "Software");
		assertEquals(comp[4][1], "Network");
		assertEquals(comp[5][1], "Inquiry");
		
		comp = manager.getManagedIncidentsAsArrayByCategory(Category.SOFTWARE);
		
		assertEquals(comp[0][1], "Software");
		assertEquals(comp[1][1], "Software");
	}
	
	
	/**
	 * test removing by ID
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testRemoveById() {
		IncidentManager manager = IncidentManager.getInstance();
		manager.createNewManagedIncidentList();
		manager.loadManagedIncidentsFromFile("test-files/incident1.xml");
		
		assertEquals(manager.getList().getManagedIncidents().size(), 6);
		assertEquals(manager.getManagedIncidentById(1).getCaller(), "sesmith5");
		assertEquals(manager.getManagedIncidentById(5).getCaller(), "student2");
		
		assertEquals(manager.getInstance(), manager);
		
		manager.deleteManagedIncidentById(6);
		
		assertEquals(manager.getList().getManagedIncidents().size(), 5);
	}
	
	/**
	 * test execute Command
	 */
	@Test
	public void testCommandexecution() {
		Command command = new Command (CommandValue.HOLD, "William", OnHoldReason.AWAITING_CHANGE, null, null, "Got It");

		IncidentManager manager = IncidentManager.getInstance();
		manager.createNewManagedIncidentList();
		manager.loadManagedIncidentsFromFile("test-files/incident1.xml");
		
		assertEquals(manager.getList().getManagedIncidents().size(), 6);
		assertEquals(manager.getManagedIncidentById(1).getCaller(), "sesmith5");
		assertEquals(manager.getManagedIncidentById(5).getCaller(), "student2");
		
		manager.executeCommand(2, command);
		
		assertEquals(manager.getManagedIncidentById(2).getCaller(), "sesmith5");
		
	}
	
	
}
