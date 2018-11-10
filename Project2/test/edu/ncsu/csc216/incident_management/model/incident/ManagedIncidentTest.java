package edu.ncsu.csc216.incident_management.model.incident;

import static org.junit.Assert.*;


import org.junit.Test;


import edu.ncsu.csc216.incident.xml.Incident;
import edu.ncsu.csc216.incident.xml.WorkNotes;
import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.CommandValue;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;
/**
 * tests the Managed Incident class
 * 
 * @author William
 *
 */
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
		assertEquals(a.getNotesString(), "Calling cause stuff\n--\n");
				
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
		
		Incident bc = new Incident();
		bc.setCaller("William");
		bc.setCategory(ManagedIncident.C_NETWORK);
		bc.setPriority(ManagedIncident.P_LOW);
		bc.setName("Things");
		bc.setWorkNotes(workNotes);
		bc.setState(ManagedIncident.NEW_NAME);
		
		ManagedIncident cc = new ManagedIncident(bc);
		
		assertEquals(b.getCaller(), "William");
		assertEquals(b.getIncidentId(), 0);
		assertEquals(b.getChangeRequest(), null);
		
		//test to see if the caller name is the same
		assertEquals(b.getXMLIncident().getCaller(), "William");
		

		Command commandToProgress = new Command (CommandValue.INVESTIGATE, "William", null, null, CancellationCode.DUPLICATE, "note");
		b.update(commandToProgress);
		assertEquals(b.getState().getStateName(), ManagedIncident.IN_PROGRESS_NAME);
		
		Command commandToResolve = new Command (CommandValue.RESOLVE, "William", null,  ResolutionCode.CALLER_CLOSED, CancellationCode.DUPLICATE, "note");
		b.update(commandToResolve);
		assertEquals(b.getState().getStateName(), ManagedIncident.RESOLVED_NAME);
		
		Command commandToREOPEN = new Command (CommandValue.REOPEN, "William", null, null, CancellationCode.DUPLICATE, "note");
		b.update(commandToREOPEN);
		assertEquals(b.getState().getStateName(), ManagedIncident.IN_PROGRESS_NAME);
		
		Command commandToOnHold = new Command (CommandValue.HOLD, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		b.update(commandToOnHold);
		assertEquals(b.getState().getStateName(), ManagedIncident.ON_HOLD_NAME);

		
		//CommandValue c, String ownerId,
		//OnHoldReason onHoldReason, ResolutionCode resolutionCode, 
		//CancellationCode cancellationCode, String note)
		Command commandToCancel = new Command (CommandValue.CANCEL, "William", null, null, CancellationCode.DUPLICATE, "note");
		
		b.update(commandToCancel);
		assertEquals(b.getState().getStateName(), ManagedIncident.CANCELED_NAME);
		
		Command newStateCommand1 = new Command (CommandValue.CANCEL, "William", null, null, CancellationCode.DUPLICATE, "note");
		b.update(newStateCommand1);
		assertEquals(b.getState().getStateName(), ManagedIncident.CANCELED_NAME);
				
		Incident q = new Incident();
		
		q.setCaller("William");
		q.setCategory(ManagedIncident.C_NETWORK);
		q.setPriority(ManagedIncident.P_LOW);
		q.setName("Things");
		q.setWorkNotes(workNotes);
		q.setState(ManagedIncident.NEW_NAME);
		
		ManagedIncident qc = new ManagedIncident(q);
		
		Command newStateCommand2 = new Command (CommandValue.INVESTIGATE, "William", null, null, CancellationCode.DUPLICATE, "note");
		qc.update(newStateCommand2);
		assertEquals(qc.getState().getStateName(), ManagedIncident.IN_PROGRESS_NAME);
				
		
	}
}
