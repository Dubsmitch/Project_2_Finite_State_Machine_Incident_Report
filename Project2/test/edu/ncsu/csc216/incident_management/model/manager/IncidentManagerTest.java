package edu.ncsu.csc216.incident_management.model.manager;

import static org.junit.Assert.*;

import org.junit.Test;

public class IncidentManagerTest {

	@Test
	public void testloadManagedIncidentsFromFile() {
		IncidentManager manager = IncidentManager.getInstance();
		manager.loadManagedIncidentsFromFile("test-files/incident1.xml");
	}

}
