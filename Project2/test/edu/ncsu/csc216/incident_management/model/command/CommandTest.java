package edu.ncsu.csc216.incident_management.model.command;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.CommandValue;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;

public class CommandTest {
	/**
	 * test command constructor
	 */
	@Test
	public void test() {
		//CommandValue c, String ownerId,
		//OnHoldReason onHoldReason, ResolutionCode resolutionCode, 
		//CancellationCode cancellationCode, String note) 	}
		Command command = new Command (CommandValue.HOLD, "William", OnHoldReason.AWAITING_CHANGE, null, null, "Got It");
		
		assertEquals(command.getCommand(), CommandValue.HOLD);
		
		assertEquals(command.getOwnerId(), "William");
		
		assertEquals(command.getResolutionCode(), null);
		
		assertEquals(command.getOnHoldReason(), OnHoldReason.AWAITING_CHANGE);
		
		assertEquals(command.getCancellationCode(), null);
		
		assertEquals(command.getWorkNote(), "Got It");
		
		try {
			Command fake = new Command (null, "William",
					OnHoldReason.AWAITING_CHANGE, null, null, "Got It");
			fail("command must have a value");
		} catch (IllegalArgumentException e) {
			
		}
		
		try {
			Command fake = new Command (CommandValue.INVESTIGATE, null,
					OnHoldReason.AWAITING_CHANGE, null, null, "Got It");
			fail("owner must have a value if commandvalue=investigate");
		} catch (IllegalArgumentException e) {
			
		}
		
		try {
			Command fake = new Command (CommandValue.HOLD, "William", 
					null, null, null, "Got It");
			fail("command of hold must have an onholdreason");
		} catch (IllegalArgumentException e) {
			
		}
		
		try {
			Command fake = new Command (CommandValue.RESOLVE, "William",
					OnHoldReason.AWAITING_CHANGE, null, null, "Got It");
			fail("command of resolve must have a resolutionodew");
		} catch (IllegalArgumentException e) {
			
		}
		
		try {
			Command fake = new Command (CommandValue.CANCEL, "William", 
					OnHoldReason.AWAITING_CHANGE, null, null, "Got It");
			fail("command must have a value");
		} catch (IllegalArgumentException e) {
			
		}
		
		Command testing = new Command(CommandValue.CANCEL, "William", OnHoldReason.AWAITING_CHANGE, 
				null, CancellationCode.DUPLICATE, "Got It");
		assertEquals(testing.getCancellationCode(), CancellationCode.DUPLICATE);
		
		Command testingT = new Command(CommandValue.RESOLVE, "William", OnHoldReason.AWAITING_CHANGE, 
				ResolutionCode.CALLER_CLOSED, null, "Got It");
		assertEquals(testingT.getResolutionCode(), ResolutionCode.CALLER_CLOSED);
	
	}
}
