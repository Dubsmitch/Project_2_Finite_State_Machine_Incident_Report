package edu.ncsu.csc216.incident_management.model.incident;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.NewState;
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
			ManagedIncident c = new ManagedIncident(null , Category.INQUIRY, Priority.URGENT, "car stuff", "Calling cause stuff");
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			ManagedIncident c = new ManagedIncident("William" , null, Priority.URGENT, "car stuff", "Calling cause stuff");
			fail("Cannot instantiate object without a Category");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			ManagedIncident c = new ManagedIncident(null , Category.INQUIRY, null, "car stuff", "Calling cause stuff");
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			ManagedIncident c = new ManagedIncident(null , Category.INQUIRY, Priority.URGENT, null, "Calling cause stuff");
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
		}
		
		try {
			ManagedIncident c = new ManagedIncident(null , Category.INQUIRY, Priority.URGENT, "car stuff", null);
			fail("Cannot instantiate object without a caller");
		} catch (IllegalArgumentException e) {
		}
	}
	
	/**
	 * testing second constructor somehow B-|
	 */
	@Test
	public void testManagedIncidentTwo() {
		

}
