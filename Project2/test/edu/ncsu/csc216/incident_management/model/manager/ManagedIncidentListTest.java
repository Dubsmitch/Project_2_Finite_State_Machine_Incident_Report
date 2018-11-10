package edu.ncsu.csc216.incident_management.model.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident.xml.WorkNotes;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;
/**
 * this class tests the managed incident list.
 * @author William
 *
 */
public class ManagedIncidentListTest {
	/**
	 * tests the constructor
	 */
	@Test
	public void testConstructor() {
		//create the new list 'a'
		ManagedIncidentList a = new ManagedIncidentList();
		
		//test to make sure the list is at size 0.
		assertEquals(a.getManagedIncidents().size(), 0);
		
	}
	/**
	 * tests the addXMLIncidents
	 */
	@Test
	public void testAddXMLIncidents() {
		//create the new list 'a'
		ManagedIncidentList managedIncidentList = new ManagedIncidentList();
		
		//test to make sure the list is at size 0.
		assertEquals(managedIncidentList.getManagedIncidents().size(), 0);
		
		//create a new arrayList to hold incidents
		ArrayList<Incident> thing1 = new ArrayList<Incident>();
		
		//create an incident using incident constructor
		WorkNotes workNotes = new WorkNotes();
		
		Incident a = new Incident();
		
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		a.setId(100);
		
		//add the new incident to the arraylist that was made
		thing1.add(a);
		
		//add the array list to the managedincidentlist
		managedIncidentList.addXMLIncidents(thing1);
		
		//check that the incident was added to the list and has the same ID as the incident
		
		assertEquals(managedIncidentList.getIncidentById(100).getIncidentId(), a.getId());
		
		//String caller, Category category, Priority priority, String name, String workNote) {

		ManagedIncident testCounter = new ManagedIncident("William", Category.DATABASE, Priority.HIGH, "Thing", "Note");
		assertEquals(testCounter.getIncidentId(), 101);
		
		//test for an id that doesnt exist
		assertEquals(managedIncidentList.getIncidentById(2136), null);
	}
	/**
	 * tests adding an incident
	 */
	@Test
	public void testAddIncident() {
		//create the new list 'a'
		ManagedIncidentList managedIncidentList = new ManagedIncidentList();
		
		//test to make sure the list is at size 0.
		assertEquals(managedIncidentList.getManagedIncidents().size(), 0);
		
		//create an incident using incident constructor
		WorkNotes workNotes = new WorkNotes();
		
		Incident a = new Incident();
		
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		a.setId(100);
		//check to make sure that the added incident has returned the correct id
		assertEquals(managedIncidentList.addIncident("William", Category.DATABASE, Priority.LOW, "Things", "notes"), 0);
		
		//check that the caller is the same between the two incidents that have the same caller
		assertEquals(managedIncidentList.getIncidentById(0).getCaller(), a.getCaller());
	}
	/**
	 * tests getting incidents by category
	 */
	@Test
	public void testgetIncidentsByCategory() {
		//workflow
			//1) create a new managedincidentlist
			//2) test that it was created
			//3) create a new list into which to append a bunch of different incidents (which are really just the same incident
				//with different categories
			//4)then test the return size of the returned arraylist
		
		//1)
		//create the new list 'a'
		ManagedIncidentList managedIncidentList = new ManagedIncidentList();
		//2)
		//test to make sure the list is at size 0.
		assertEquals(managedIncidentList.getManagedIncidents().size(), 0);
		//3)
		//create a new arrayList to hold incidents
		ArrayList<Incident> thing1 = new ArrayList<Incident>();
		//3b (create an incident)
		//create an incident using incident constructor
		WorkNotes workNotes = new WorkNotes();
		
		Incident a = new Incident();
		
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		a.setId(100);
		thing1.add(a);
		
		//3c, add incident to the list
		//add the new incident to the arraylist that was made
		
		Incident b = new Incident();
		
		b.setCaller("Billiam");
		b.setCategory(ManagedIncident.C_NETWORK);
		b.setPriority(ManagedIncident.P_LOW);
		b.setName("Things");
		b.setWorkNotes(workNotes);
		b.setState(ManagedIncident.IN_PROGRESS_NAME);
		b.setId(101);
		thing1.add(b);
		
		//3d, change incident and then add it to the list (and repeat)
		//add the array list to the managedincidentlist
		Incident c = new Incident();
		
		c.setCaller("Jilliam");
		c.setCategory(ManagedIncident.C_NETWORK);
		c.setPriority(ManagedIncident.P_LOW);
		c.setName("Things");
		c.setWorkNotes(workNotes);
		c.setState(ManagedIncident.IN_PROGRESS_NAME);
		c.setCaller("Josh");
		c.setId(200);
		thing1.add(c);
		
		Incident d = new Incident();
		
		d.setCaller("Lilliam");
		d.setPriority(ManagedIncident.P_LOW);
		d.setName("Things");
		d.setWorkNotes(workNotes);
		d.setState(ManagedIncident.IN_PROGRESS_NAME);
		d.setId(100);
		d.setCaller("Satabaseguy");
		d.setId(201);
		d.setCategory(ManagedIncident.C_DATABASE);
		thing1.add(d);
		
		managedIncidentList.addXMLIncidents(thing1);
		
		//check that the incidents were added to the list and has the same ID as the incident
		
		assertEquals(managedIncidentList.getManagedIncidents().size(), 4);
		
		assertEquals(managedIncidentList.getIncidentsByCategory(Category.NETWORK).get(1).getCategory(), Category.NETWORK);
		assertEquals(managedIncidentList.getIncidentsByCategory(Category.DATABASE).size(), 1);
		assertEquals(managedIncidentList.getIncidentsByCategory(Category.DATABASE).get(0).getCaller(), "Satabaseguy");
	

	}
	
	/**
	 * tests removing an incident
	 */
	@Test
	public void testDeleteIncidentById() {
		//create the new list 'a'
		ManagedIncidentList managedIncidentList = new ManagedIncidentList();
		
		//test to make sure the list is at size 0.
		assertEquals(managedIncidentList.getManagedIncidents().size(), 0);
		
		//create an incident using incident constructor
		WorkNotes workNotes = new WorkNotes();
		
		Incident a = new Incident();
		
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		a.setId(100);
		//check to make sure that the added incident has returned the correct id
		assertEquals(managedIncidentList.addIncident("William", Category.DATABASE, Priority.LOW, "Things", "notes"), 0);
		
		//check that the caller is the same between the two incidents that have the same caller
		assertEquals(managedIncidentList.getIncidentById(0).getCaller(), a.getCaller());
		
		//check the size
		assertEquals(managedIncidentList.getManagedIncidents().size(), 1);
		
		managedIncidentList.deleteIncidentById(0);
		
		assertEquals(managedIncidentList.getManagedIncidents().size(), 0);
	}
}
