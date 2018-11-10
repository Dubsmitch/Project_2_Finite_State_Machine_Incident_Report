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
		assertEquals(a.getNotesString(), "Calling cause stuff\n-------\n");
				
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
		
		@SuppressWarnings("unused")
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
		try {
			b.update(newStateCommand1);
			fail("invalid Transition");
		} catch (UnsupportedOperationException e) {
			assertEquals(b.getState().getStateName(), ManagedIncident.CANCELED_NAME);
		}
				
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
		
		Command newStateCommand3 = new Command (CommandValue.RESOLVE, "William", null, ResolutionCode.CALLER_CLOSED, CancellationCode.DUPLICATE, "note");
		qc.update(newStateCommand3);
		assertEquals(qc.getState().getStateName(), ManagedIncident.RESOLVED_NAME);
		
		Command newStateCommand4 = new Command (CommandValue.CONFIRM, "William", null, ResolutionCode.CALLER_CLOSED, CancellationCode.DUPLICATE, "note");
		qc.update(newStateCommand4);
		assertEquals(qc.getState().getStateName(), ManagedIncident.CLOSED_NAME);
		
		Command newStateCommand5 = new Command (CommandValue.REOPEN, "William", null, ResolutionCode.CALLER_CLOSED, CancellationCode.DUPLICATE, "note");
		qc.update(newStateCommand5);
		assertEquals(qc.getState().getStateName(), ManagedIncident.IN_PROGRESS_NAME);
		
		Incident tooMuch = new Incident();
		
		tooMuch.setCaller("William");
		tooMuch.setCategory(ManagedIncident.C_NETWORK);
		tooMuch.setPriority(ManagedIncident.P_LOW);
		tooMuch.setName("Things");
		tooMuch.setWorkNotes(workNotes);
		tooMuch.setState(ManagedIncident.ON_HOLD_NAME);
		
		ManagedIncident noPartners = new ManagedIncident(tooMuch);
		
		//(CommandValue c, String ownerId,
		//		OnHoldReason onHoldReason, ResolutionCode resolutionCode, 
		//		CancellationCode cancellationCode, String note)
		
		Command test500 = new Command (CommandValue.REOPEN, 
				"William", OnHoldReason.AWAITING_CALLER, ResolutionCode.CALLER_CLOSED, CancellationCode.DUPLICATE,
				"note");
		noPartners.update(test500);
		assertEquals(noPartners.getState().getStateName(), ManagedIncident.IN_PROGRESS_NAME);
		
		Incident tooMuchtoo = new Incident();
		
		tooMuchtoo.setCaller("William");
		tooMuchtoo.setCategory(ManagedIncident.C_NETWORK);
		tooMuchtoo.setPriority(ManagedIncident.P_LOW);
		tooMuchtoo.setName("Things");
		tooMuchtoo.setWorkNotes(workNotes);
		tooMuchtoo.setState(ManagedIncident.ON_HOLD_NAME);
		
		ManagedIncident noPartnersno = new ManagedIncident(tooMuchtoo);

		Command test501 = new Command (CommandValue.RESOLVE, 
				"William", OnHoldReason.AWAITING_CHANGE, ResolutionCode.CALLER_CLOSED, CancellationCode.DUPLICATE,
				"note");
		noPartnersno.update(test501);
		assertEquals(noPartnersno.getState().getStateName(), ManagedIncident.RESOLVED_NAME);
	
		Incident q1 = new Incident();
		
		q1.setCaller("William");
		q1.setCategory(ManagedIncident.C_NETWORK);
		q1.setPriority(ManagedIncident.P_LOW);
		q1.setName("Things");
		q1.setWorkNotes(workNotes);
		q1.setState(ManagedIncident.NEW_NAME);
		
		ManagedIncident qc1 = new ManagedIncident(q);
		
		Command newStateCommand21 = new Command (CommandValue.CANCEL, "William", null, null, CancellationCode.DUPLICATE, "note");
		qc1.update(newStateCommand21);
		assertEquals(qc1.getState().getStateName(), ManagedIncident.CANCELED_NAME);
		
		Incident resolved1 = new Incident();
		
		resolved1.setCaller("William");
		resolved1.setCategory(ManagedIncident.C_NETWORK);
		resolved1.setPriority(ManagedIncident.P_LOW);
		resolved1.setName("Things");
		resolved1.setWorkNotes(workNotes);
		resolved1.setState(ManagedIncident.RESOLVED_NAME);
		
		ManagedIncident resolved2 = new ManagedIncident(resolved1);
		
		Command newStateCommand211 = new Command (CommandValue.HOLD, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		resolved2.update(newStateCommand211);
		assertEquals(resolved2.getState().getStateName(), ManagedIncident.ON_HOLD_NAME);
		
		Incident resolved5 = new Incident();
		
		resolved5.setCaller("William");
		resolved5.setCategory(ManagedIncident.C_NETWORK);
		resolved5.setPriority(ManagedIncident.P_LOW);
		resolved5.setName("Things");
		resolved5.setWorkNotes(workNotes);
		resolved5.setState(ManagedIncident.RESOLVED_NAME);
		
		ManagedIncident resolvedNow = new ManagedIncident(resolved5);
		
		Command newStateCommand212 = new Command (CommandValue.CANCEL, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		resolvedNow.update(newStateCommand212);
		assertEquals(resolvedNow.getState().getStateName(), ManagedIncident.CANCELED_NAME);
		
		
		Incident closeStateTest = new Incident();
		
		closeStateTest.setCaller("William");
		closeStateTest.setCategory(ManagedIncident.C_NETWORK);
		closeStateTest.setPriority(ManagedIncident.P_LOW);
		closeStateTest.setName("Things");
		closeStateTest.setWorkNotes(workNotes);
		closeStateTest.setState(ManagedIncident.CANCELED_NAME);
		
		ManagedIncident closedStateTest = new ManagedIncident(closeStateTest);
		
		Command closeMe = new Command (CommandValue.CANCEL, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		try {
			closedStateTest.update(closeMe);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(closedStateTest.getCaller(), "William");
		}
		
		Command closeMe1 = new Command (CommandValue.CONFIRM, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		
		try {
			closedStateTest.update(closeMe1);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(closedStateTest.getCaller(), "William");
		}
		
		Command closeMe2 = new Command (CommandValue.RESOLVE, "William", OnHoldReason.AWAITING_CALLER, ResolutionCode.NOT_SOLVED, CancellationCode.DUPLICATE, "note");

		
		try {
			closedStateTest.update(closeMe2);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(closedStateTest.getCaller(), "William");
		}
		
		Command closeMe3 = new Command (CommandValue.REOPEN, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");

		try {
			closedStateTest.update(closeMe3);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(closedStateTest.getCaller(), "William");
		}
		
		Command closeMe4 = new Command (CommandValue.HOLD, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		
		try {
			closedStateTest.update(closeMe4);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(closedStateTest.getCaller(), "William");
		}
		
		Incident actualcloseStateTest = new Incident();
		
		actualcloseStateTest.setCaller("William");
		actualcloseStateTest.setCategory(ManagedIncident.C_NETWORK);
		actualcloseStateTest.setPriority(ManagedIncident.P_LOW);
		actualcloseStateTest.setName("Things");
		actualcloseStateTest.setWorkNotes(workNotes);
		actualcloseStateTest.setState(ManagedIncident.CLOSED_NAME);
		
		ManagedIncident actuaulclosedStateTest = new ManagedIncident(actualcloseStateTest);
		
		Command closedMe = new Command (CommandValue.CONFIRM, "William", OnHoldReason.AWAITING_CALLER, null, CancellationCode.DUPLICATE, "note");
		
		try {
			actuaulclosedStateTest.update(closedMe);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(closedStateTest.getCaller(), "William");
		}
		
		ManagedIncident actuaulclosedStateTest1 = new ManagedIncident(actualcloseStateTest);
		
		Command closedMe1 = new Command (CommandValue.RESOLVE, "William", OnHoldReason.AWAITING_CALLER, ResolutionCode.NOT_SOLVED, CancellationCode.DUPLICATE, "note");
		
		try {
			actuaulclosedStateTest1.update(closedMe1);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(actuaulclosedStateTest1.getCaller(), "William");
		}
		
		ManagedIncident actuaulclosedStateTest2 = new ManagedIncident(actualcloseStateTest);
		
		Command closedMe2 = new Command (CommandValue.HOLD, "William", OnHoldReason.AWAITING_CALLER, ResolutionCode.NOT_SOLVED, CancellationCode.DUPLICATE, "note");
		
		try {
			actuaulclosedStateTest2.update(closedMe2);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(actuaulclosedStateTest2.getCaller(), "William");
		}
		
		ManagedIncident actuaulclosedStateTest3 = new ManagedIncident(actualcloseStateTest);
		
		Command closedMe3 = new Command (CommandValue.CANCEL, "William", OnHoldReason.AWAITING_CALLER, ResolutionCode.NOT_SOLVED, CancellationCode.DUPLICATE, "note");
		
		try {
			actuaulclosedStateTest3.update(closedMe3);
			fail("should thow an exception");
		} catch (UnsupportedOperationException e) {
			assertEquals(actuaulclosedStateTest2.getCaller(), "William");
		}
	}
}
