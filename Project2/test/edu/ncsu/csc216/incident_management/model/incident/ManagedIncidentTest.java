package edu.ncsu.csc216.incident_management.model.incident;

import static org.junit.Assert.*;


import org.junit.Test;


import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident.xml.WorkNotes;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;

public class ManagedIncidentTest {
	/**
	 * testing first constructor
	 */
	@Test
	public void testManagedIncident() {
		//(String caller, Category category, Priority priority, String name, String workNote)
		ManagedIncident a = new ManagedIncident("William", Category.INQUIRY, Priority.URGENT, "car stuff", "Calling cause stuff");
		
		assertEquals(a.getCaller(), "William");
		assertEquals(a.getCategory(), Category.INQUIRY);
		assertEquals(a.getPriorityString(), ManagedIncident.P_URGENT);
		assertEquals(a.getName(), "car stuff");
		assertEquals(a.getNotesString(),"Calling cause stuff\n--\n");
				
		assertEquals(a.getState().getStateName(), ManagedIncident.NEW_NAME);
		assertEquals(a.getOwner(), null);
		assertEquals(a.getIncidentId(), 0);
		
		ManagedIncident b = new ManagedIncident("William", Category.INQUIRY, Priority.URGENT, "car stuff", "Calling cause stuff");
		assertEquals(b.getIncidentId(), 1);
		
		//try to make some impossible objects with null values
		try {
			@SuppressWarnings("unused")
			ManagedIncident c = new ManagedIncident("", Category.INQUIRY, Priority.URGENT, "car stuff", "Calling cause stuff");
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
			//adding caller fixes the problem
			ManagedIncident c = new ManagedIncident("William" , Category.INQUIRY, Priority.URGENT, "car stuff", "Calling cause stuff");
			assertEquals(c.getCaller(), "William");
		}
		
		
		try {
			@SuppressWarnings("unused")
			ManagedIncident c = new ManagedIncident("William" , null, Priority.URGENT, "car stuff", "Calling cause stuff");
			fail("Cannot instantiate object without a Category");
		} catch (IllegalArgumentException e) {
			ManagedIncident c = new ManagedIncident("William" , Category.DATABASE, Priority.URGENT, "car stuff", "Calling cause stuff");
			assertEquals(c.getCategory(), Category.DATABASE);
		}
		
		try {
			@SuppressWarnings("unused")
			ManagedIncident c = new ManagedIncident("William" , Category.INQUIRY, null, "car stuff", "Calling cause stuff");
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
			ManagedIncident c = new ManagedIncident("William" , Category.INQUIRY, Priority.LOW, "car stuff", "Calling cause stuff");
			//adding a priority fixes the problem
			assertEquals(c.getPriorityString(), ManagedIncident.P_LOW);
		}
		
		
		try {
			@SuppressWarnings("unused")
			ManagedIncident c = new ManagedIncident("" , Category.INQUIRY, Priority.URGENT, "car stuff", "notes");
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
			ManagedIncident c = new ManagedIncident("William" , Category.INQUIRY, Priority.URGENT, "car stuff", "notes");
			assertEquals(c.getCaller(), "William");
		}
	}
	
	/**
	 * testing second constructor somehow B-|
	 */
	@Test
	public void testManagedIncidentTwo() {
        WorkNotes workNotes = new WorkNotes();
		Incident a = new Incident();
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		
		ManagedIncident b = new ManagedIncident(a);
		
		assertEquals(b.getCaller(), "William");
		assertEquals(b.getIncidentId(), 0);
		
		a.setId(1);
		ManagedIncident c = new ManagedIncident(a);
		assertEquals(c.getIncidentId(), 1);
	}
	
	/**
	 * testing get XMLIncident
	 */
	@Test
	public void testGetXMLIncident() {
        WorkNotes workNotes = new WorkNotes();
		Incident a = new Incident();
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		
		ManagedIncident b = new ManagedIncident(a);
		
		assertEquals(b.getCaller(), "William");
		assertEquals(b.getIncidentId(), 0);
		assertEquals(b.getChangeRequest(), null);
		
		//test to see if the caller name is the same
		assertEquals(b.getXMLIncident().getCaller(), "William");
	}
	
	/**
	 * testing transitions? maybe here? dunno
	 */
	@Test
	public void testTransitions() {
        WorkNotes workNotes = new WorkNotes();
		Incident a = new Incident();
		a.setCaller("William");
		a.setCategory(ManagedIncident.C_NETWORK);
		a.setPriority(ManagedIncident.P_LOW);
		a.setName("Things");
		a.setWorkNotes(workNotes);
		a.setState(ManagedIncident.IN_PROGRESS_NAME);
		
		ManagedIncident b = new ManagedIncident(a);
		
		assertEquals(b.getCaller(), "William");
		assertEquals(b.getIncidentId(), 0);
		assertEquals(b.getChangeRequest(), null);
		
		//test to see if the caller name is the same
		assertEquals(b.getXMLIncident().getCaller(), "William");
	}
}
