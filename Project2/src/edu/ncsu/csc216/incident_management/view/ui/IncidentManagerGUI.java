package edu.ncsu.csc216.incident_management.view.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import edu.ncsu.csc216.incident_management.model.command.Command;
import edu.ncsu.csc216.incident_management.model.command.Command.CancellationCode;
import edu.ncsu.csc216.incident_management.model.command.Command.OnHoldReason;
import edu.ncsu.csc216.incident_management.model.command.Command.ResolutionCode;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Category;
import edu.ncsu.csc216.incident_management.model.incident.ManagedIncident.Priority;
import edu.ncsu.csc216.incident_management.model.manager.IncidentManager;

/**
 * Container for the IncidentManager that has the menu options for new incident 
 * tracking files, loading existing files, saving files and quitting.
 * Depending on user actions, other JPanels are loaded for the
 * different ways users interact with the UI.
 * 
 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
 */
public class IncidentManagerGUI extends JFrame implements ActionListener {
	
	/** ID number used for object serialization. */
	private static final long serialVersionUID = 1L;
	/** Title for top of GUI. */
	private static final String APP_TITLE = "Incident Manager";
	/** Text for the File Menu. */
	private static final String FILE_MENU_TITLE = "File";
	/** Text for the New Incident XML menu item. */
	private static final String NEW_XML_TITLE = "New";
	/** Text for the Load Incident XML menu item. */
	private static final String LOAD_XML_TITLE = "Load";
	/** Text for the Save menu item. */
	private static final String SAVE_XML_TITLE = "Save";
	/** Text for the Quit menu item. */
	private static final String QUIT_TITLE = "Quit";
	/** Menu bar for the GUI that contains Menus. */
	private JMenuBar menuBar;
	/** Menu for the GUI. */
	private JMenu menu;
	/** Menu item for creating a new file containing  ManagedIncidents. */
	private JMenuItem itemNewIncidentXML;
	/** Menu item for loading a file containing  ManagedIncidents. */
	private JMenuItem itemLoadIncidentXML;
	/** Menu item for saving the incident list. */
	private JMenuItem itemSaveIncidentXML;
	/** Menu item for quitting the program. */
	private JMenuItem itemQuit;
	/** Panel that will contain different views for the application. */
	private JPanel panel;
	/** Constant to identify incidententListPanel for  CardLayout. */
	private static final String INCIDENT_LIST_PANEL = "IncidentListPanel";
	/** Constant to identify NewPanel for  CardLayout. */
	private static final String NEW_PANEL = "NewPanel";
	/** Constant to identify InProgress for  CardLayout. */
	private static final String IN_PROGRESS_PANEL = "InProgressPanel";
	/** Constant to identify OnHoldPanel for  CardLayout. */
	private static final String ON_HOLD_PANEL = "OnHoldPanel";
	/** Constant to identify ResolvedPanel for  CardLayout. */
	private static final String RESOLVED_PANEL = "ResolvedPanel";
	/** Constant to identify ClosedPanel for  CardLayout. */
	private static final String CLOSED_PANEL = "ClosedPanel";
	/** Constant to identify CanceledPanel for  CardLayout. */
	private static final String CANCELED_PANEL = "CanceledPanel";
	/** Constant to identify CreateIncidentPanel for  CardLayout. */
	private static final String CREATE_INCIDENT_PANEL = "CreateIncidentPanel";
	/** Incident List panel - we only need one instance, so it's final. */
	private final IncidentListPanel pnlIncidentList = new IncidentListPanel();
	/** New panel - we only need one instance, so it's final. */
	private final NewPanel pnlNew = new NewPanel();
	/** In Progress panel - we only need one instance, so it's final. */
	private final InProgressPanel pnlInProgress = new InProgressPanel();
	/** On Hold panel - we only need one instance, so it's final. */
	private final OnHoldPanel pnlOnHold = new OnHoldPanel();
	/** Resolved panel - we only need one instance, so it's final. */
	private final ResolvedPanel pnlResolved = new ResolvedPanel();
	/** Closed panel - we only need one instance, so it's final. */
	private final ClosedPanel pnlClosed = new ClosedPanel();
	/** Canceled panel - we only need one instance, so it's final. */
	private final CanceledPanel pnlCanceled = new CanceledPanel();
	/** Add Incident panel - we only need one instance, so it's final. */
	private final AddIncidentPanel pnlAddIncident = new AddIncidentPanel();
	/** Reference to  CardLayout for panel.  Stacks all of the panels. */
	private CardLayout cardLayout;
	
	
	/**
	 * Constructs a  IncidentManagerGUI object that will contain a  JMenuBar and a
	 *  JPanel that will hold different possible views of the data in
	 * the  IncidentManager.
	 */
	public IncidentManagerGUI() {
		super();
		
		//Set up general GUI info
		setSize(500, 700);
		setLocation(50, 50);
		setTitle(APP_TITLE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpMenuBar();
		
		//Create JPanel that will hold rest of GUI information.
		//The JPanel utilizes a CardLayout, which stack several different
		//JPanels.  User actions lead to switching which "Card" is visible.
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(pnlIncidentList, INCIDENT_LIST_PANEL);
		panel.add(pnlNew, NEW_PANEL);
		panel.add(pnlInProgress, IN_PROGRESS_PANEL);
		panel.add(pnlOnHold, ON_HOLD_PANEL);
		panel.add(pnlResolved, RESOLVED_PANEL);
		panel.add(pnlClosed, CLOSED_PANEL);
		panel.add(pnlCanceled, CANCELED_PANEL);
		panel.add(pnlAddIncident, CREATE_INCIDENT_PANEL);
		cardLayout.show(panel, INCIDENT_LIST_PANEL);
		
		//Add panel to the container
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		//Set the GUI visible
		setVisible(true);
	}
	
	/**
	 * Makes the GUI Menu bar that contains options for loading a file
	 * containing incidents or for quitting the application.
	 */
	private void setUpMenuBar() {
		//Construct Menu items
		menuBar = new JMenuBar();
		menu = new JMenu(FILE_MENU_TITLE);
		itemNewIncidentXML = new JMenuItem(NEW_XML_TITLE);
		itemLoadIncidentXML = new JMenuItem(LOAD_XML_TITLE);
		itemSaveIncidentXML = new JMenuItem(SAVE_XML_TITLE);
		itemQuit = new JMenuItem(QUIT_TITLE);
		itemNewIncidentXML.addActionListener(this);
		itemLoadIncidentXML.addActionListener(this);
		itemSaveIncidentXML.addActionListener(this);
		itemQuit.addActionListener(this);
		
		//Start with save button disabled
		itemSaveIncidentXML.setEnabled(false);
		
		//Build Menu and add to GUI
		menu.add(itemNewIncidentXML);
		menu.add(itemLoadIncidentXML);
		menu.add(itemSaveIncidentXML);
		menu.add(itemQuit);
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Performs an action based on the given  ActionEvent.
	 * @param e user event that triggers an action.
	 */
	public void actionPerformed(ActionEvent e) {
		//Use IncidentManager's singleton to create/get the sole instance.
		IncidentManager model = IncidentManager.getInstance();
		if (e.getSource() == itemNewIncidentXML) {
			//Create a new incident list
			model.createNewManagedIncidentList();
			itemSaveIncidentXML.setEnabled(true);
			pnlIncidentList.updateTable(null);
			cardLayout.show(panel, INCIDENT_LIST_PANEL);
			validate();
			repaint();			
		} else if (e.getSource() == itemLoadIncidentXML) {
			//Load an existing incident list
			try {
				model.loadManagedIncidentsFromFile(getFileName(true));
				itemSaveIncidentXML.setEnabled(true);
				pnlIncidentList.updateTable(null);
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				validate();
				repaint();
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to load incident file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemSaveIncidentXML) {
			//Save current incident list
			try {
				model.saveManagedIncidentsToFile(getFileName(false));
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save incident file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		} else if (e.getSource() == itemQuit) {
			//Quit the program
			try {
				model.saveManagedIncidentsToFile(getFileName(false));
				System.exit(0);  //Ignore SpotBugs warning here - this is the only place to quit the program!
			} catch (IllegalArgumentException exp) {
				JOptionPane.showMessageDialog(this, "Unable to save incident file.");
			} catch (IllegalStateException exp) {
				//Don't do anything - user canceled (or error)
			}
		}
	}
	
	/**
	 * Returns a file name generated through interactions with a  JFileChooser
	 * object.
	 * @return the file name selected through  JFileChooser
	 */
	private String getFileName(boolean load) {
		JFileChooser fc = new JFileChooser("./");  //Open JFileChoose to current working directory
		int returnVal = Integer.MIN_VALUE;
		if (load) {
			returnVal = fc.showOpenDialog(this);
		} else {
			returnVal = fc.showSaveDialog(this);
		}
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			//Error or user canceled, either way no file name.
			throw new IllegalStateException();
		}
		File gameFile = fc.getSelectedFile();
		return gameFile.getAbsolutePath();
	}

	/**
	 * Starts the GUI for the IncidentManager application.
	 * @param args command line arguments
	 */
	public static void main(String [] args) {
		new IncidentManagerGUI();
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * shows the list of incidents.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class IncidentListPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Button for creating a new incident */
		private JButton btnAddNewIncident;
		/** Button for deleting the selected incident in the list */
		private JButton btnDeleteIncident;
		/** Button for editing the selected incident in the list */
		private JButton btnEditIncident;
		/** Drop down for categories */
		private JComboBox<String> comboCategory;
		/** Button for filtering by category */
		private JButton btnFilterByCategory;
		/** Button that will show all incidents that are currently tracked */
		private JButton btnShowAllIncident;
		/** JTable for displaying the list of incidents */
		private JTable table;
		/** TableModel for Incidents */
		private IncidentTableModel incidentTableModel;
		
		/**
		 * Creates the incident list.
		 */
		public IncidentListPanel() {
			super(new BorderLayout());
			
			//Set up the JPanel that will hold action buttons		
			btnAddNewIncident = new JButton("Add New Incident");
			btnAddNewIncident.addActionListener(this);
			btnDeleteIncident = new JButton("Delete Selected Incident");
			btnDeleteIncident.addActionListener(this);
			btnEditIncident = new JButton("Edit Selected Incident");
			btnEditIncident.addActionListener(this);
			comboCategory = new JComboBox<String>();
			comboCategory.addItem(ManagedIncident.C_INQUIRY);
			comboCategory.addItem(ManagedIncident.C_SOFTWARE);
			comboCategory.addItem(ManagedIncident.C_HARDWARE);
			comboCategory.addItem(ManagedIncident.C_NETWORK);
			comboCategory.addItem(ManagedIncident.C_DATABASE);
			btnFilterByCategory = new JButton("List by Category");
			btnFilterByCategory.addActionListener(this);
			btnShowAllIncident = new JButton("Show All Incidents");
			btnShowAllIncident.addActionListener(this);
			
			JPanel pnlActions = new JPanel();
			pnlActions.setLayout(new GridLayout(2, 3));
			pnlActions.add(btnAddNewIncident);
			pnlActions.add(btnDeleteIncident);
			pnlActions.add(btnEditIncident);
			pnlActions.add(comboCategory);
			pnlActions.add(btnFilterByCategory);
			pnlActions.add(btnShowAllIncident);
						
			//Set up table
			incidentTableModel = new IncidentTableModel();
			table = new JTable(incidentTableModel);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setPreferredScrollableViewportSize(new Dimension(500, 500));
			table.setFillsViewportHeight(true);
			
			JScrollPane listScrollPane = new JScrollPane(table);
			
			add(pnlActions, BorderLayout.NORTH);
			add(listScrollPane, BorderLayout.CENTER);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btnAddNewIncident) {
				//If the add button is pressed switch to the createIncidentPanel
				cardLayout.show(panel, CREATE_INCIDENT_PANEL);
			} else if (e.getSource() == btnDeleteIncident) {
				//If the delete button is pressed, delete the incident
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No incident selected");
				} else {
					try {
						int incidentId = Integer.parseInt(incidentTableModel.getValueAt(row, 0).toString());
						IncidentManager.getInstance().deleteManagedIncidentById(incidentId);
					} catch (NumberFormatException nfe ) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid incident id");
					}
				}
				updateTable(null);
			} else if (e.getSource() == btnEditIncident) {
				//If the edit button is pressed, switch panel based on state
				int row = table.getSelectedRow();
				if (row == -1) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No incident selected");
				} else {
					try {
						int incident = Integer.parseInt(incidentTableModel.getValueAt(row, 0).toString());
						String stateName = IncidentManager.getInstance().getManagedIncidentById(incident).getState().getStateName();
						if (stateName.equals(ManagedIncident.ON_HOLD_NAME)) {
							cardLayout.show(panel, ON_HOLD_PANEL);
							pnlOnHold.setIncidentInfo(incident);
						} 
						if (stateName.equals(ManagedIncident.NEW_NAME)) {
							cardLayout.show(panel, NEW_PANEL);
							pnlNew.setIncidentInfo(incident);
						} 
						if (stateName.equals(ManagedIncident.IN_PROGRESS_NAME)) {
							cardLayout.show(panel, IN_PROGRESS_PANEL);
							pnlInProgress.setIncidentInfo(incident);
						} 
						if (stateName.equals(ManagedIncident.RESOLVED_NAME)) {
							cardLayout.show(panel, RESOLVED_PANEL);
							pnlResolved.setIncidentInfo(incident);
						} 
						if (stateName.equals(ManagedIncident.CLOSED_NAME)) {
							cardLayout.show(panel, CLOSED_PANEL);
							pnlClosed.setIncidentInfo(incident);
						} 
						if (stateName.equals(ManagedIncident.CANCELED_NAME)) {
							cardLayout.show(panel, CANCELED_PANEL);
							pnlCanceled.setIncidentInfo(incident);
						} 
					} catch (NumberFormatException nfe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid incident id");
					} catch (NullPointerException npe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid incident id");
					}
				}
			} else if (e.getSource() == btnFilterByCategory) {
				int categoryIdx = comboCategory.getSelectedIndex();
				Category category = null;
				switch(categoryIdx) {
				case 0: category = Category.INQUIRY; break;
				case 1: category = Category.SOFTWARE; break;
				case 2: category = Category.HARDWARE; break;
				case 3: category = Category.NETWORK; break;
				case 4: category = Category.DATABASE; break;
				default: category = null;
				}
				updateTable(category);
			} else if (e.getSource() == btnShowAllIncident) {
				updateTable(null);
			}
			IncidentManagerGUI.this.repaint();
			IncidentManagerGUI.this.validate();
		}
		
		public void updateTable(Category category) {
			if (category == null) {
				incidentTableModel.updateIncidentData();
			} else {
				incidentTableModel.updateIncidentDataByCategory(category);
			}
		}
		
		/**
		 *  IncidentTableModel is the object underlying the  JTable object that displays
		 * the list of  ManagedIncidents to the user.
		 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
		 */
		private class IncidentTableModel extends AbstractTableModel {
			
			/** ID number used for object serialization. */
			private static final long serialVersionUID = 1L;
			/** Column names for the table */
			private String [] columnNames = {"Incident ID", "Category", "State", "Priority", "Name"};
			/** Data stored in the table */
			private Object [][] data;
			
			/**
			 * Constructs the  IncidentTableModel by requesting the latest information
			 * from the  IncidentTableModel.
			 */
			public IncidentTableModel() {
				updateIncidentData();
			}

			/**
			 * Returns the number of columns in the table.
			 * @return the number of columns in the table.
			 */
			public int getColumnCount() {
				return columnNames.length;
			}

			/**
			 * Returns the number of rows in the table.
			 * @return the number of rows in the table.
			 */
			public int getRowCount() {
				if (data == null) 
					return 0;
				return data.length;
			}
			
			/**
			 * Returns the column name at the given index.
			 * @return the column name at the given column.
			 */
			public String getColumnName(int col) {
				return columnNames[col];
			}

			/**
			 * Returns the data at the given {row, col} index.
			 * @return the data at the given location.
			 */
			public Object getValueAt(int row, int col) {
				if (data == null)
					return null;
				return data[row][col];
			}
			
			/**
			 * Sets the given value to the given {row, col} location.
			 * @param value Object to modify in the data.
			 * @param row location to modify the data.
			 * @param column location to modify the data.
			 */
			public void setValueAt(Object value, int row, int col) {
				data[row][col] = value;
				fireTableCellUpdated(row, col);
			}
			
			/**
			 * Updates the given model with  ManagedIncident information from the  IncidentManager.
			 */
			private void updateIncidentData() {
				IncidentManager m = IncidentManager.getInstance();
				data = m.getManagedIncidentsAsArray();
			}
			
			/**
			 * Updates the given model with  ManagedIncident information for the 
			 * given incident type from the  IncidentManager.
			 * @param category category type to search for.
			 */
			private void updateIncidentDataByCategory(Category category) {
				try {
					IncidentManager m = IncidentManager.getInstance();
					data = m.getManagedIncidentsAsArrayByCategory(category);
				} catch (IllegalArgumentException e) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid category");
				}
			}
		}
	}
	
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * interacts with a new incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class NewPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/**  IncidentInfoPanel that presents the  ManagedIncident's information to the user */
		private IncidentInfoPanel pnlIncidentInfo;
		/** Label for owner id field */
		private JLabel lblOwnerId;
		/** Text field for owner id */
		private JTextField txtOwnerId;
		/** Label for resolution */
		private JLabel lblCancellationCode;
		/** ComboBox for resolution options */
		private JComboBox<String> comboCancellationCode;
		/** Note label for the state update */
		private JLabel lblNote;
		/** Note for the state update */
		private JTextArea txtNote;
		/** Investigate action */
		private JButton btnInvestigate;
		/** Cancel action */
		private JButton btnCancel;
		/** Return action */
		private JButton btnReturn;
		/** Current  ManagedIncident's id */
		private int incidentId;
		
		/**
		 * Constructs the JPanel for editing a  ManagedIncident in the NewState.
		 */
		public NewPanel() {
			pnlIncidentInfo = new IncidentInfoPanel();
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Incident Information");
			pnlIncidentInfo.setBorder(border);
			pnlIncidentInfo.setToolTipText("Incident Information");
			
			lblOwnerId = new JLabel("Owner Id");
			txtOwnerId = new JTextField(15);
			lblCancellationCode = new JLabel("Cancellation Code");
			comboCancellationCode = new JComboBox<String>();
			lblNote = new JLabel("Work Note");
			txtNote = new JTextArea(30, 1);

			btnInvestigate = new JButton("Investigate");
			btnCancel = new JButton("Cancel");
			btnReturn = new JButton("Return");
			
			btnInvestigate.addActionListener(this);
			btnCancel.addActionListener(this);
			btnReturn.addActionListener(this);
			
			JPanel pnlCommands = new JPanel();
			lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			border = BorderFactory.createTitledBorder(lowerEtched, "Commands");
			pnlCommands.setBorder(border);
			pnlCommands.setToolTipText("Commands");
			
			pnlCommands.setLayout(new GridBagLayout());
			
			JPanel pnlOwner = new JPanel();
			pnlOwner.setLayout(new GridLayout(1, 2));
			pnlOwner.add(lblOwnerId);
			pnlOwner.add(txtOwnerId);
			
			JPanel pnlCancellation = new JPanel();
			pnlCancellation.setLayout(new GridLayout(1, 2));
			pnlCancellation.add(lblCancellationCode);
			pnlCancellation.add(comboCancellationCode);
			
			JScrollPane scrollNote = new JScrollPane(txtNote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel pnlBtnRow = new JPanel();
			pnlBtnRow.setLayout(new GridLayout(1, 3));
			pnlBtnRow.add(btnInvestigate);
			pnlBtnRow.add(btnCancel);
			pnlBtnRow.add(btnReturn);
			
			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlOwner, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlCancellation, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(lblNote, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 3;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(scrollNote, c);
			
			c.gridx = 0;
			c.gridy = 5;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlBtnRow, c);
			
			
			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlIncidentInfo, c);
			
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCommands, c);
			
		}
		
		/**
		 * Set the  IncidentInfoPanel with the given incident data.
		 * @param incidentId id of the Incident
		 */
		public void setIncidentInfo(int incidentId) {
			this.incidentId = incidentId;
			pnlIncidentInfo.setIncidentInfo(this.incidentId);
			
			comboCancellationCode.removeAllItems();
			
			comboCancellationCode.addItem(Command.CC_DUPLICATE);
			comboCancellationCode.addItem(Command.CC_UNNECESSARY);
			comboCancellationCode.addItem(Command.CC_NOT_AN_INCIDENT);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean reset = true;
			//Take care of note.
			String note = txtNote.getText();
			if (note.equals("")) {
				note = null;
				JOptionPane.showMessageDialog(IncidentManagerGUI.this, "A work note is required");
				reset = false;
			} else if (e.getSource() == btnInvestigate) {
				String ownerId = txtOwnerId.getText();
				if (ownerId == null || ownerId.equals("")) {
					//If owner id is invalid, show an error message
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid owner id");
					reset = false;
				} else {
					//Otherwise, try a Command.  If command fails, go back to Incident list
					try {
						Command c = new Command(Command.CommandValue.INVESTIGATE, ownerId, null, null, null, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}		
				}
			} else if (e.getSource() == btnCancel) {
				int idx = comboCancellationCode.getSelectedIndex();
				CancellationCode cancellationCode = null;
				switch (idx) {
				case 0:
					cancellationCode = CancellationCode.DUPLICATE;
					break;
				case 1:
					cancellationCode = CancellationCode.UNNECESSARY;
					break;
				case 2:
					cancellationCode = CancellationCode.NOT_AN_INCIDENT;
					break;
				default:
					cancellationCode = null;
				}
				//Try a Command.  If command fails, go back to Incident list
				try {
					Command c = new Command(Command.CommandValue.CANCEL, null, null, null, cancellationCode, note);
					IncidentManager.getInstance().executeCommand(incidentId, c);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
				} catch (UnsupportedOperationException uoe) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
				}
			} 
			if (reset) {
				//All buttons lead to incident list if valid info for owner
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				pnlIncidentList.updateTable(null);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
				//Reset fields
				comboCancellationCode.setSelectedIndex(0);
				txtOwnerId.setText("");
				txtNote.setText("");
			}
			//Otherwise, do not refresh the GUI panel and wait for correct owner input.
		}
		
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * interacts with an in progress incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class InProgressPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/**  IncidentInfoPanel that presents the  ManagedIncident's information to the user */
		private IncidentInfoPanel pnlIncidentInfo;
		/** Note label for the state update */
		private JLabel lblNote;
		/** Note for the state update */
		private JTextArea txtNote;
		/** Label for selecting a hold reason */
		private JLabel lblOnHoldReason;
		/** ComboBox for on hold reasons */
		private JComboBox<String> comboOnHoldReason;
		/** Hold action */
		private JButton btnHold;
		/** Label for selecting a resolution code */
		private JLabel lblResolutionCode;
		/** ComboBox for resolution code */
		private JComboBox<String> comboResolutionCode;
		/** Resolve action */
		private JButton btnResolve;
		/** Label for selecting a cancellation code */
		private JLabel lblCancellationCode;
		/** ComboBox for cancellationCodes */
		private JComboBox<String> comboCancellationCode;
		/** Cancel action */
		private JButton btnCancel;
		/** Return action */
		private JButton btnReturn;
		/** Current Incident's id */
		private int incidentId;

		/**
		 * Constructs a JPanel for editing a  ManagedIncident in the AssignedState.
		 */
		public InProgressPanel() {
			pnlIncidentInfo = new IncidentInfoPanel();
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Incident Information");
			pnlIncidentInfo.setBorder(border);
			pnlIncidentInfo.setToolTipText("Incident Information");
			
			lblOnHoldReason = new JLabel("On Hold Reason");
			comboOnHoldReason = new JComboBox<String>();
			lblResolutionCode = new JLabel("Resolution Code");
			comboResolutionCode = new JComboBox<String>();
			lblCancellationCode = new JLabel("Cancellation Code");
			comboCancellationCode = new JComboBox<String>();
			lblNote = new JLabel("Work Note");
			txtNote = new JTextArea(30, 5);
			
			btnHold = new JButton("Hold");
			btnResolve = new JButton("Resolve");
			btnCancel = new JButton("Cancel");
			btnReturn = new JButton("Return");
			
			btnHold.addActionListener(this);
			btnResolve.addActionListener(this);
			btnCancel.addActionListener(this);
			btnReturn.addActionListener(this);
			
			JPanel pnlCommands = new JPanel();
			pnlCommands.setLayout(new GridBagLayout());
			lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			border = BorderFactory.createTitledBorder(lowerEtched, "Commands");
			pnlCommands.setBorder(border);
			pnlCommands.setToolTipText("Commands");
			
			JPanel pnlOnHoldReason = new JPanel();
			pnlOnHoldReason.setLayout(new GridLayout(1, 2));
			pnlOnHoldReason.add(lblOnHoldReason);
			pnlOnHoldReason.add(comboOnHoldReason);
			
			JPanel pnlResolution = new JPanel();
			pnlResolution.setLayout(new GridLayout(1, 2));
			pnlResolution.add(lblResolutionCode);
			pnlResolution.add(comboResolutionCode);
			
			JPanel pnlCancellationCode = new JPanel();
			pnlCancellationCode.setLayout(new GridLayout(1, 2));
			pnlCancellationCode.add(lblCancellationCode);
			pnlCancellationCode.add(comboCancellationCode);
			
			JScrollPane scrollNote = new JScrollPane(txtNote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel pnlBtnRow = new JPanel();
			pnlBtnRow.setLayout(new GridLayout(1, 4));
			pnlBtnRow.add(btnHold);
			pnlBtnRow.add(btnResolve);
			pnlBtnRow.add(btnCancel);
			pnlBtnRow.add(btnReturn);

			GridBagConstraints c = new GridBagConstraints();
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlOnHoldReason, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlResolution, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlCancellationCode, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(lblNote, c);
			
			c.gridx = 0;
			c.gridy = 4;
			c.weightx = 1;
			c.weighty = 3;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(scrollNote, c);
			
			c.gridx = 0;
			c.gridy = 7;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlBtnRow, c);
			
			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlIncidentInfo, c);
			
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCommands, c);
		}
		
		/**
		 * Set the  IncidentInfoPanel with the given Incident data.
		 * @param incidentId id of the Incident
		 */
		public void setIncidentInfo(int incidentId) {
			this.incidentId = incidentId;
			pnlIncidentInfo.setIncidentInfo(this.incidentId);
			
			comboOnHoldReason.removeAllItems();
			comboOnHoldReason.addItem(Command.OH_CALLER);
			comboOnHoldReason.addItem(Command.OH_CHANGE);
			comboOnHoldReason.addItem(Command.OH_VENDOR);
			
			comboResolutionCode.removeAllItems();
			comboResolutionCode.addItem(Command.RC_PERMANENTLY_SOLVED);
			comboResolutionCode.addItem(Command.RC_WORKAROUND);
			comboResolutionCode.addItem(Command.RC_NOT_SOLVED);
			comboResolutionCode.addItem(Command.RC_CALLER_CLOSED);
			
			comboCancellationCode.removeAllItems();
			comboCancellationCode.addItem(Command.CC_DUPLICATE);
			comboCancellationCode.addItem(Command.CC_UNNECESSARY);
			comboCancellationCode.addItem(Command.CC_NOT_AN_INCIDENT);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean reset = true;
			//Handle note
			String note = txtNote.getText();
			if (note.equals("")) {
				note = null;
				JOptionPane.showMessageDialog(IncidentManagerGUI.this, "A work note is required");
				reset = false;
			} else if (e.getSource() == btnResolve) {
				//Get resolution
				int idx = comboResolutionCode.getSelectedIndex();
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No resolution selected");
				} else {
					ResolutionCode resolutionCode = null;
					switch (idx) {
					case 0: 
						resolutionCode = ResolutionCode.PERMANENTLY_SOLVED;
						break;
					case 1:
						resolutionCode = ResolutionCode.WORKAROUND;
						break;
					case 2:
						resolutionCode = ResolutionCode.NOT_SOLVED;
						break;
					case 3:
						resolutionCode = ResolutionCode.CALLER_CLOSED;
						break;
					default:
						resolutionCode = null;
					}
					//Try a command.  If problem, go back to Incident list.
					try {
						Command c = new Command(Command.CommandValue.RESOLVE, null, null, resolutionCode, null, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			} else if (e.getSource() == btnHold) {
				int idx = comboOnHoldReason.getSelectedIndex();
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No on hold reason selected");
				} else {
					OnHoldReason onHoldReason = null;
					switch (idx) {
					case 0: 
						onHoldReason = OnHoldReason.AWAITING_CALLER;
						break;
					case 1:
						onHoldReason = OnHoldReason.AWAITING_CHANGE;
						break;
					case 2:
						onHoldReason = OnHoldReason.AWAITING_VENDOR;
						break;
					default:
						onHoldReason = null;
					}
					//Try a command.  If problem, go back to Incident list.
					try {
						Command c = new Command(Command.CommandValue.HOLD, null, onHoldReason , null, null, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			} else if (e.getSource() == btnCancel) {
				int idx = comboCancellationCode.getSelectedIndex();
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No cancellation code selected");
				} else {
					CancellationCode canellationCode = null;
					switch (idx) {
					case 0: 
						canellationCode = CancellationCode.DUPLICATE;
						break;
					case 1:
						canellationCode = CancellationCode.UNNECESSARY;
						break;
					case 2:
						canellationCode = CancellationCode.NOT_AN_INCIDENT;
						break;
					default:
						canellationCode = null;
					}
					//Try a command.  If problem, go back to Incident list.
					try {
						Command c = new Command(Command.CommandValue.CANCEL, null, null, null, canellationCode, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			}
			if (reset) {
				//All buttons lead to back Incident list
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				pnlIncidentList.updateTable(null);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
				//Reset fields
				comboOnHoldReason.setSelectedIndex(0);
				comboResolutionCode.setSelectedIndex(0);
				comboCancellationCode.setSelectedIndex(0);
				txtNote.setText("");
			}
			//Otherwise, stay on panel
		}
		
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * interacts with a confirmed Incident
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class OnHoldPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/**  IncidentInfoPanel that presents the  ManagedIncident's information to the user */
		private IncidentInfoPanel pnlIncidentInfo;
		/** Note label for the state update */
		private JLabel lblNote;
		/** Note for the state update */
		private JTextArea txtNote;
		/** Label for resolution */
		private JLabel lblResolutionCode;
		/** ComboBox for resolution options */
		private JComboBox<String> comboResolutionCode;
		/** Label for selecting a cancellation code */
		private JLabel lblCancellationCode;
		/** ComboBox for cancellationCodes */
		private JComboBox<String> comboCancellationCode;
		/** Reopen action */
		private JButton btnReopen;
		/** Resolve action */
		private JButton btnResolve;
		/** Cancel action */
		private JButton btnCancel;
		/** Return action */
		private JButton btnReturn;
		/** Current  ManagedIncident}'s id */
		private int incidentId;
		
		/**
		 * Constructs the JPanel for editing a  ManagedIncident in the UnconfirmedState.
		 */
		public OnHoldPanel() {
			pnlIncidentInfo = new IncidentInfoPanel();
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Incident Information");
			pnlIncidentInfo.setBorder(border);
			pnlIncidentInfo.setToolTipText("Incident Information");
			
			lblNote = new JLabel("Note");
			txtNote = new JTextArea(30, 1);
			lblCancellationCode = new JLabel("Cancellation Code");
			comboCancellationCode = new JComboBox<String>();
			lblResolutionCode = new JLabel("Resolution");
			comboResolutionCode = new JComboBox<String>();

			btnReopen = new JButton("Reopen");
			btnResolve = new JButton("Resolve");
			btnCancel = new JButton("Cancel");
			btnReturn = new JButton("Return");
			
			btnReopen.addActionListener(this);
			btnResolve.addActionListener(this);
			btnCancel.addActionListener(this);
			btnReturn.addActionListener(this);
			
			JPanel pnlCommands = new JPanel();
			pnlCommands.setLayout(new GridBagLayout());
			lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			border = BorderFactory.createTitledBorder(lowerEtched, "Commands");
			pnlCommands.setBorder(border);
			pnlCommands.setToolTipText("Commands");
			
			GridBagConstraints c = new GridBagConstraints();
					
			JPanel pnlResolutionCode = new JPanel();
			pnlResolutionCode.setLayout(new GridLayout(1, 2));
			pnlResolutionCode.add(lblResolutionCode);
			pnlResolutionCode.add(comboResolutionCode);
			
			JPanel pnlCancellationCode = new JPanel();
			pnlCancellationCode.setLayout(new GridLayout(1, 2));
			pnlCancellationCode.add(lblCancellationCode);
			pnlCancellationCode.add(comboCancellationCode);

			JScrollPane scrollNote = new JScrollPane(txtNote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel pnlBtnRow = new JPanel();
			pnlBtnRow.setLayout(new GridLayout(1, 4));
			pnlBtnRow.add(btnReopen);
			pnlBtnRow.add(btnResolve);
			pnlBtnRow.add(btnCancel);
			pnlBtnRow.add(btnReturn);
				
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlResolutionCode, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlCancellationCode, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(lblNote, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 3;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(scrollNote, c);
			
			c.gridx = 0;
			c.gridy = 5;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlBtnRow, c);
			
			
			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlIncidentInfo, c);
			
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCommands, c);		
		}
		
		/**
		 * Set the  IncidentInfoPanel with the given Incident data.
		 * @param incidentId id of the Incident
		 */
		public void setIncidentInfo(int incidentId) {
			this.incidentId = incidentId;
			pnlIncidentInfo.setIncidentInfo(this.incidentId);
			
			comboResolutionCode.removeAllItems();
			comboResolutionCode.addItem(Command.RC_PERMANENTLY_SOLVED);
			comboResolutionCode.addItem(Command.RC_WORKAROUND);
			comboResolutionCode.addItem(Command.RC_NOT_SOLVED);
			comboResolutionCode.addItem(Command.RC_CALLER_CLOSED);
			
			comboCancellationCode.removeAllItems();
			comboCancellationCode.addItem(Command.CC_DUPLICATE);
			comboCancellationCode.addItem(Command.CC_UNNECESSARY);
			comboCancellationCode.addItem(Command.CC_NOT_AN_INCIDENT);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean reset = true;
			//Take care of note
			String note = txtNote.getText();
			if (note.equals("")) {
				note = null;
				JOptionPane.showMessageDialog(IncidentManagerGUI.this, "A work note is required");
				reset = false;
			} else if (e.getSource() == btnResolve) {
				//Get resolution
				int idx = comboResolutionCode.getSelectedIndex();
				
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No resolution selected");
				} else {				
					ResolutionCode resolutionCode = null;
					switch (idx) {
					case 0: 
						resolutionCode = ResolutionCode.PERMANENTLY_SOLVED;
						break;
					case 1:
						resolutionCode = ResolutionCode.WORKAROUND;
						break;
					case 2:
						resolutionCode = ResolutionCode.NOT_SOLVED;
						break;
					case 3:
						resolutionCode = ResolutionCode.CALLER_CLOSED;
						break;
					default:
						resolutionCode = null;
					}
					//Try a command.  If problem, go back to Incident list.
					try {
						Command c = new Command(Command.CommandValue.RESOLVE, null, null, resolutionCode, null, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			} else if (e.getSource() == btnReopen) {
					//Otherwise, try a Command.  If command fails, go back to Incident list
					try {
						Command c = new Command(Command.CommandValue.REOPEN, null, null, null, null, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}		
			} else if (e.getSource() == btnCancel) {
				int idx = comboCancellationCode.getSelectedIndex();
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No cancellation code selected");
				} else {
					CancellationCode canellationCode = null;
					switch (idx) {
					case 0: 
						canellationCode = CancellationCode.DUPLICATE;
						break;
					case 1:
						canellationCode = CancellationCode.UNNECESSARY;
						break;
					case 2:
						canellationCode = CancellationCode.NOT_AN_INCIDENT;
						break;
					default:
						canellationCode = null;
					}
					//Try a command.  If problem, go back to Incident list.
					try {
						Command c = new Command(Command.CommandValue.CANCEL, null, null, null, canellationCode, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			}
			if (reset) {
				//Add buttons lead to back Incident list
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				pnlIncidentList.updateTable(null);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
				//Reset elements
				txtNote.setText("");
				comboResolutionCode.setSelectedIndex(0);
				comboCancellationCode.setSelectedIndex(0);
			}
		}
		
	}

	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * interacts with a resolved Incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class ResolvedPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/**  IncidentInfoPanel that presents the  ManagedIncident's information to the user */
		private IncidentInfoPanel pnlIncidentInfo;
		/** Note label for the state update */
		private JLabel lblNote;
		/** Note for the state update */
		private JTextArea txtNote;
		/** Label for selecting a hold reason */
		private JLabel lblOnHoldReason;
		/** ComboBox for on hold reasons */
		private JComboBox<String> comboOnHoldReason;
		/** Label for selecting a cancellation code */
		private JLabel lblCancellationCode;
		/** ComboBox for cancellationCodes */
		private JComboBox<String> comboCancellationCode;
		/** Resolve action */
		private JButton btnHold;
		/** Reopen action */
		private JButton btnReopen;
		/** Confirm action */
		private JButton btnConfirm;
		/** Cancel action */
		private JButton btnCancel;
		/** Return action */
		private JButton btnReturn;
		/** Current incident's id */
		private int incidentId;

		/**
		 * Constructs a JFrame for editing a  ManagedIncident in the ResolvedState.
		 */
		public ResolvedPanel() {
			pnlIncidentInfo = new IncidentInfoPanel();
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Incident Information");
			pnlIncidentInfo.setBorder(border);
			pnlIncidentInfo.setToolTipText("Incident Information");
			
			lblOnHoldReason = new JLabel("On Hold Reason");
			comboOnHoldReason = new JComboBox<String>();
			lblCancellationCode = new JLabel("Cancellation Code");
			comboCancellationCode = new JComboBox<String>();
			lblNote = new JLabel("Note");
			txtNote = new JTextArea(30, 5);
			
			btnHold = new JButton("Hold");
			btnReopen = new JButton("Reopen");
			btnConfirm = new JButton("Confirm");
			btnCancel = new JButton("Cancel");
			btnReturn = new JButton("Return");
			
			btnHold.addActionListener(this);
			btnReopen.addActionListener(this);
			btnConfirm.addActionListener(this);
			btnCancel.addActionListener(this);
			btnReturn.addActionListener(this);
			
			JPanel pnlCommands = new JPanel();
			pnlCommands.setLayout(new GridBagLayout());
			lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			border = BorderFactory.createTitledBorder(lowerEtched, "Commands");
			pnlCommands.setBorder(border);
			pnlCommands.setToolTipText("Commands");

			GridBagConstraints c = new GridBagConstraints();
			
			JPanel pnlOnHoldReason = new JPanel();
			pnlOnHoldReason.setLayout(new GridLayout(1, 2));
			pnlOnHoldReason.add(lblOnHoldReason);
			pnlOnHoldReason.add(comboOnHoldReason);
			
			JPanel pnlCancellationCode = new JPanel();
			pnlCancellationCode.setLayout(new GridLayout(1, 2));
			pnlCancellationCode.add(lblCancellationCode);
			pnlCancellationCode.add(comboCancellationCode);
						
			JScrollPane scrollNote = new JScrollPane(txtNote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel pnlBtnRow = new JPanel();
			pnlBtnRow.setLayout(new GridLayout(1, 5));
			pnlBtnRow.add(btnHold);
			pnlBtnRow.add(btnReopen);
			pnlBtnRow.add(btnConfirm);
			pnlBtnRow.add(btnCancel);
			pnlBtnRow.add(btnReturn);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlOnHoldReason, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlCancellationCode, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(lblNote, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 3;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(scrollNote, c);
			
			c.gridx = 0;
			c.gridy = 7;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlBtnRow, c);
			
			
			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlIncidentInfo, c);
			
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCommands, c);
		}
		
		/**
		 * Set the  IncidentInfoPanel with the given Incident data.
		 * @param incidentId id of the Incident
		 */
		public void setIncidentInfo(int incidentId) {
			this.incidentId = incidentId;
			pnlIncidentInfo.setIncidentInfo(this.incidentId);
			
			comboOnHoldReason.removeAllItems();
			comboOnHoldReason.addItem(Command.OH_CALLER);
			comboOnHoldReason.addItem(Command.OH_CHANGE);
			comboOnHoldReason.addItem(Command.OH_VENDOR);
			
			comboCancellationCode.removeAllItems();
			comboCancellationCode.addItem(Command.CC_DUPLICATE);
			comboCancellationCode.addItem(Command.CC_UNNECESSARY);
			comboCancellationCode.addItem(Command.CC_NOT_AN_INCIDENT);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean reset = true;
			//Handle note
			String note = txtNote.getText();
			if (note.equals("")) {
				note = null;
				JOptionPane.showMessageDialog(IncidentManagerGUI.this, "A work note is required");
				reset = false;
			} else if (e.getSource() == btnHold) {
				int idx = comboOnHoldReason.getSelectedIndex();
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No on hold reason selected");
				} else {
					OnHoldReason onHoldReason = null;
					switch (idx) {
					case 0: 
						onHoldReason = OnHoldReason.AWAITING_CALLER;
						break;
					case 1:
						onHoldReason = OnHoldReason.AWAITING_CHANGE;
						break;
					case 2:
						onHoldReason = OnHoldReason.AWAITING_VENDOR;
						break;
					default:
						onHoldReason = null;
					}
					//Try command.  If problem, go to Incident list.
					try {
						Command c = new Command(Command.CommandValue.HOLD, null, onHoldReason, null, null, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			} else if (e.getSource() == btnReopen) {
				try {
					Command c = new Command(Command.CommandValue.REOPEN, null, null, null, null, note);
					IncidentManager.getInstance().executeCommand(incidentId, c);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
				} catch (UnsupportedOperationException uoe) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
				}
			} else if (e.getSource() == btnConfirm) {
				try {
					Command c = new Command(Command.CommandValue.CONFIRM, null, null, null, null, note);
					IncidentManager.getInstance().executeCommand(incidentId, c);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
				} catch (UnsupportedOperationException uoe) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
				}
			} else if (e.getSource() == btnCancel) {
				int idx = comboCancellationCode.getSelectedIndex();
				if (idx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No cancellation code selected");
				} else {
					CancellationCode canellationCode = null;
					switch (idx) {
					case 0: 
						canellationCode = CancellationCode.DUPLICATE;
						break;
					case 1:
						canellationCode = CancellationCode.UNNECESSARY;
						break;
					case 2:
						canellationCode = CancellationCode.NOT_AN_INCIDENT;
						break;
					default:
						canellationCode = null;
					}
					//Try a command.  If problem, go back to Incident list.
					try {
						Command c = new Command(Command.CommandValue.CANCEL, null, null, null, canellationCode, note);
						IncidentManager.getInstance().executeCommand(incidentId, c);
					} catch (IllegalArgumentException iae) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
					} catch (UnsupportedOperationException uoe) {
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
					}
				}
			}
			if (reset) {
				//Add buttons lead to back Incident list
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				pnlIncidentList.updateTable(null);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
				//Reset elements
				txtNote.setText("");
				comboOnHoldReason.setSelectedIndex(0);
				comboCancellationCode.setSelectedIndex(0);
			}
		}
		
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * interacts with a closed incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class ClosedPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/**  IncidentInfoPanel that presents the  ManagedIncident's information to the user */
		private IncidentInfoPanel pnlIncidentInfo;
		/** Note label for the state update */
		private JLabel lblNote;
		/** Note for the state update */
		private JTextArea txtNote;
		/** Reopen action */
		private JButton btnReopen;
		/** Return action */
		private JButton btnReturn;
		/** Current incident's id */
		private int incidentId;

		/**
		 * Constructs a JPanel for editing a  ManagedIncident in the ClosedState.
		 */
		public ClosedPanel() {
			pnlIncidentInfo = new IncidentInfoPanel();
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Incident Information");
			pnlIncidentInfo.setBorder(border);
			pnlIncidentInfo.setToolTipText("Incident Information");
			
			lblNote = new JLabel("Note");
			txtNote = new JTextArea(30, 5);
			
			btnReopen = new JButton("Reassign");
			btnReturn = new JButton("Return");
			
			btnReopen.addActionListener(this);
			btnReturn.addActionListener(this);
			
			JPanel pnlCommands = new JPanel();
			lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			border = BorderFactory.createTitledBorder(lowerEtched, "Commands");
			pnlCommands.setBorder(border);
			pnlCommands.setToolTipText("Commands");
			
			pnlCommands.setLayout(new GridBagLayout());
			
			JScrollPane scrollNote = new JScrollPane(txtNote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			JPanel pnlBtnRow = new JPanel();
			pnlBtnRow.setLayout(new GridLayout(1, 2));
			pnlBtnRow.add(btnReopen);
			pnlBtnRow.add(btnReturn);
			
			GridBagConstraints c = new GridBagConstraints();
						
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(lblNote, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 3;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(scrollNote, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlBtnRow, c);
			
			
			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlIncidentInfo, c);
			
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCommands, c);
		}
		
		/**
		 * Set the  IncidentInfoPanel with the given Incident data.
		 * @param incidentId id of the Incident
		 */
		public void setIncidentInfo(int incidentId) {
			this.incidentId = incidentId;
			pnlIncidentInfo.setIncidentInfo(this.incidentId);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean reset = true;
			//Handle note
			String note = txtNote.getText();
			if (note.equals("")) {
				note = null;
				JOptionPane.showMessageDialog(IncidentManagerGUI.this, "A work note is required");
				reset = false;
			}
			if (e.getSource() == btnReopen) {
				
				//Try command.  If problem, return to Incident list.
				try {
					Command c = new Command(Command.CommandValue.REOPEN, null, null, null, null, note);
					IncidentManager.getInstance().executeCommand(incidentId, c);
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid command");
				} catch (UnsupportedOperationException uoe) {
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid state transition");
				}
			}
			if (reset) {
				//All buttons lead to back Incident list
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				pnlIncidentList.updateTable(null);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
				//Reset fields
				txtNote.setText("");
			} 
			//Otherwise, stay on panel so user can fix
		}
		
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * interacts with a canceled incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class CanceledPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/**  IncidentInfoPanel that presents the  ManagedIncident's information to the user */
		private IncidentInfoPanel pnlIncidentInfo;
		/** Return action */
		private JButton btnReturn;
		/** Current incident's id */
		private int incidentId;

		/**
		 * Constructs a JPanel for editing a  ManagedIncident in the ClosedState.
		 */
		public CanceledPanel() {
			pnlIncidentInfo = new IncidentInfoPanel();
			
			Border lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			TitledBorder border = BorderFactory.createTitledBorder(lowerEtched, "Incident Information");
			pnlIncidentInfo.setBorder(border);
			pnlIncidentInfo.setToolTipText("Incident Information");
			
			btnReturn = new JButton("Return");
			btnReturn.addActionListener(this);
			
			JPanel pnlCommands = new JPanel();
			pnlCommands.setLayout(new GridBagLayout());
			lowerEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
			border = BorderFactory.createTitledBorder(lowerEtched, "Commands");
			pnlCommands.setBorder(border);
			pnlCommands.setToolTipText("Commands");

			GridBagConstraints c = new GridBagConstraints();
			
			JPanel pnlBtnRow = new JPanel();
			pnlBtnRow.setLayout(new GridLayout(1, 1));
			pnlBtnRow.add(btnReturn);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			pnlCommands.add(pnlBtnRow, c);
			
			
			setLayout(new GridBagLayout());
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 5;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlIncidentInfo, c);
			
			
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCommands, c);
		}
		
		/**
		 * Set the  IncidentInfoPanel with the given Incident data.
		 * @param incidentId id of the Incident
		 */
		public void setIncidentInfo(int incidentId) {
			this.incidentId = incidentId;
			pnlIncidentInfo.setIncidentInfo(this.incidentId);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			//All buttons lead to back Incident list
			cardLayout.show(panel, INCIDENT_LIST_PANEL);
			pnlIncidentList.updateTable(null);
			IncidentManagerGUI.this.repaint();
			IncidentManagerGUI.this.validate();
		}
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * shows information about the incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class IncidentInfoPanel extends JPanel {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Label for id */
		private JLabel lblId;
		/** Field for id */
		private JTextField txtId;
		/** Label for caller */
		private JLabel lblCaller;
		/** Field for caller */
		private JTextField txtCaller;
		/** Label for category */
		private JLabel lblCategory;
		/** Field for category */
		private JTextField txtCategory;
		/** Label for state */
		private JLabel lblState;
		/** Field for state */
		private JTextField txtState;
		/** Label for priority */
		private JLabel lblPriority;
		/** Field for priority */
		private JTextField txtPriority;
		/** Label for owner */
		private JLabel lblOwner;
		/** Field for owner */
		private JTextField txtOwner;
		/** Label for name */
		private JLabel lblName;
		/** Field for name */
		private JTextField txtName;
		/** Label for on hold reason */
		private JLabel lblOnHoldReason;
		/** Field for on hold reason */
		private JTextField txtOnHoldReason;
		/** Label for change request */
		private JLabel lblChangeRequest;
		/** Field for change request */
		private JTextField txtChangeRequest;
		/** Label for resolution code */
		private JLabel lblResolutionCode;
		/** Field for resolution code */
		private JTextField txtResolutionCode;
		/** Label for cancellation code */
		private JLabel lblCancellationCode;
		/** Field for cancellation Code */
		private JTextField txtCancellationCode;
		/** Label for notes */
		private JLabel lblNotes;
		/** Field for notes */
		private JTextArea txtNotes;
		
		/** 
		 * Construct the panel for the incident information.
		 */
		public IncidentInfoPanel() {
			super(new GridBagLayout());
			
			lblId = new JLabel("Incident Id");
			lblCaller = new JLabel("Caller");
			lblCategory = new JLabel("Category");
			lblState = new JLabel("State");
			lblPriority = new JLabel("Priority");
			lblOwner = new JLabel("Owner");
			lblName = new JLabel("Name");
			lblOnHoldReason = new JLabel("On Hold Reason");
			lblChangeRequest = new JLabel("Change Request");
			lblResolutionCode = new JLabel("Resolution Code");
			lblCancellationCode = new JLabel("Cancellation Code");
			lblNotes = new JLabel("Notes");
			
			txtId = new JTextField(15);
			txtCaller = new JTextField(15);
			txtCategory = new JTextField(15);
			txtState = new JTextField(15);
			txtPriority = new JTextField(15);
			txtOwner = new JTextField(15);
			txtName = new JTextField(40);
			txtOnHoldReason = new JTextField(15);
			txtChangeRequest = new JTextField(40);
			txtResolutionCode = new JTextField(15);
			txtCancellationCode = new JTextField(15);
			txtNotes = new JTextArea(30, 5);
			
			txtId.setEditable(false);
			txtCaller.setEditable(false);
			txtCategory.setEditable(false);
			txtState.setEditable(false);
			txtPriority.setEditable(false);
			txtOwner.setEditable(false);
			txtName.setEditable(false);
			txtOnHoldReason.setEditable(false);
			txtChangeRequest.setEditable(false);
			txtResolutionCode.setEditable(false);
			txtCancellationCode.setEditable(false);
			txtNotes.setEditable(false);
			
			JScrollPane notesScrollPane = new JScrollPane(txtNotes, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			GridBagConstraints c = new GridBagConstraints();
						
			//Row 1 - ID and Caller
			JPanel row1 = new JPanel();
			row1.setLayout(new GridLayout(1, 4));
			row1.add(lblId);
			row1.add(txtId);
			row1.add(lblCaller);
			row1.add(txtCaller);
			
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row1, c);
			
			//Row 2 - Category and State
			JPanel row2 = new JPanel();
			row2.setLayout(new GridLayout(1, 4));
			row2.add(lblCategory);
			row2.add(txtCategory);
			row2.add(lblState);
			row2.add(txtState); 
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row2, c);
						
			//Row 3 - Priority & Owner
			JPanel row3 = new JPanel();
			row3.setLayout(new GridLayout(1, 4));
			row3.add(lblPriority);
			row3.add(txtPriority);
			row3.add(lblOwner);
			row3.add(txtOwner);
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row3, c);
			
			//Row 4 - Name
			JPanel row4 = new JPanel();
			row4.setLayout(new GridLayout(1, 2));
			row4.add(lblName);
			row4.add(txtName);
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row4, c);
			
			//Row 5 - On Hold Reason
			JPanel row5 = new JPanel();
			row5.setLayout(new GridLayout(1, 2));
			row5.add(lblOnHoldReason);
			row5.add(txtOnHoldReason);
			c.gridx = 0;
			c.gridy = 4;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row5, c);
			
			//Row 6 - Change request
			JPanel row6 = new JPanel();
			row6.setLayout(new GridLayout(1, 2));
			row6.add(lblChangeRequest);
			row6.add(txtChangeRequest);
			c.gridx = 0;
			c.gridy = 5;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row6, c);
			
			//Row 7 - resolution and cancellation codes
			JPanel row7 = new JPanel();
			row7.setLayout(new GridLayout(1, 4));
			row7.add(lblResolutionCode);
			row7.add(txtResolutionCode);
			row7.add(lblCancellationCode);
			row7.add(txtCancellationCode);
			c.gridx = 0;
			c.gridy = 6;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(row7, c);
			
			//Row 8 - Notes title
			c.gridx = 0;
			c.gridy = 7;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(lblNotes, c);
			
			//Row 9 - Notes text area
			c.gridx = 0;
			c.gridy = 8;
			c.weightx = 1;
			c.weighty = 4;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(notesScrollPane, c);
		}
		
		/**
		 * Adds information about the incident to the display.  
		 * @param incidentId the id for the incident to display information about.
		 */
		public void setIncidentInfo(int incidentId) {
			//Get the incident from the model
			ManagedIncident i = IncidentManager.getInstance().getManagedIncidentById(incidentId);
			if (i == null) {
				//If the incident doesn't exist for the given id, show an error message
				JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid incident id");
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
			} else {
				//Otherwise, set all of the fields with the information
				txtId.setText("" + i.getIncidentId());
				txtCaller.setText(i.getCaller());
				txtCategory.setText(i.getCategoryString());
				txtState.setText(i.getState().getStateName());
				txtPriority.setText(i.getPriorityString());
				txtOwner.setText(i.getOwner());
				txtName.setText(i.getName());
				String onHoldReason = i.getOnHoldReasonString();
				if (onHoldReason == null) {
					txtOnHoldReason.setText("");
				} else {
					txtOnHoldReason.setText(onHoldReason);
				}
				String changeRequest = i.getChangeRequest();
				if (changeRequest == null) {
					txtChangeRequest.setText("");
				} else {
					txtChangeRequest.setText(changeRequest);
				}
				String resolutionCodeString = i.getResolutionCodeString();
				if (resolutionCodeString == null) {
					txtResolutionCode.setText("");
				} else {
					txtResolutionCode.setText("" + resolutionCodeString);
				}
				String cancellationCodeString = i.getCancellationCodeString();
				if (cancellationCodeString == null) {
					txtCancellationCode.setText("");
				} else {
					txtCancellationCode.setText("" + cancellationCodeString);
				}
				txtNotes.setText(i.getNotesString());
			}
		}
	}
	
	/**
	 * Inner class that creates the look and behavior for the  JPanel that 
	 * allows for creation of a new incident.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private class AddIncidentPanel extends JPanel implements ActionListener {
		/** ID number used for object serialization. */
		private static final long serialVersionUID = 1L;
		/** Label for identifying caller text field */
		private JLabel lblCaller;
		/** Text field for entering caller id */
		private JTextField txtCaller;
		/** Label for category text field */
		private JLabel lblCategory;
		/** Combo box for category */
		private JComboBox<String> comboCategory;
		/** Label for priority */
		private JLabel lblPriority;
		/** Combo box for priority */
		private JComboBox<String> comboPriority;
		/** Label for name */
		private JLabel lblName;
		/** Text field for name */
		private JTextField txtName;
		/** Label for identifying work note */
		private JLabel lblWorkNote;
		/** Text field for entering work note information */
		private JTextArea txtWorkNote;
		
		/** Button to add a incident */
		private JButton btnAdd;
		/** Button for returning to menu with out creating a new incident */
		private JButton btnReturn;
		
		/**
		 * Creates the  JPanel for adding new incident to the 
		 * manager.
		 */
		public AddIncidentPanel() {
			super(new GridBagLayout());  
			
			//Construct widgets
			lblCaller = new JLabel("Caller");
			txtCaller = new JTextField(30);
			lblCategory = new JLabel("Category");
			comboCategory = new JComboBox<String>();
			comboCategory.addItem(ManagedIncident.C_INQUIRY);
			comboCategory.addItem(ManagedIncident.C_SOFTWARE);
			comboCategory.addItem(ManagedIncident.C_HARDWARE);
			comboCategory.addItem(ManagedIncident.C_NETWORK);
			comboCategory.addItem(ManagedIncident.C_DATABASE);
			lblPriority = new JLabel("Priority");
			comboPriority = new JComboBox<String>();
			comboPriority.addItem(ManagedIncident.P_URGENT);
			comboPriority.addItem(ManagedIncident.P_HIGH);
			comboPriority.addItem(ManagedIncident.P_MEDIUM);
			comboPriority.addItem(ManagedIncident.P_LOW);
			lblName = new JLabel("Name");
			txtName = new JTextField(30);
			lblWorkNote = new JLabel("Work Note");
			txtWorkNote = new JTextArea(5, 30);
			
			btnAdd = new JButton("Add Incident");
			btnReturn = new JButton("Return");
			
			//Adds action listeners
			btnAdd.addActionListener(this);
			btnReturn.addActionListener(this);
			
			GridBagConstraints c = new GridBagConstraints();
			
			//Builds reporter panel, which is a 1 row, 2 col grid
			JPanel pnlCaller = new JPanel();
			pnlCaller.setLayout(new GridLayout(1, 2));
			pnlCaller.add(lblCaller);
			pnlCaller.add(txtCaller);
			
			//Builds category panel, which is a 1 row, 2 col grid
			JPanel pnlCategory = new JPanel();
			pnlCategory.setLayout(new GridLayout(1, 2));
			pnlCategory.add(lblCategory);
			pnlCategory.add(comboCategory);
			
			//Builds priority panel, which is a 1 row, 2 col grid
			JPanel pnlPriority = new JPanel();
			pnlPriority.setLayout(new GridLayout(1, 2));
			pnlPriority.add(lblPriority);
			pnlPriority.add(comboPriority);
			
			//Builds name panel, which is a 1 row, 2 col grid
			JPanel pnlName = new JPanel();
			pnlName.setLayout(new GridLayout(1, 2));
			pnlName.add(lblName);
			pnlName.add(txtName);
			
			//Creates scroll for work notes area
			JScrollPane scrollSummary = new JScrollPane(txtWorkNote, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			//Build button panel, which is a 1 row, 2 col grid
			JPanel pnlButtons = new JPanel();
			pnlButtons.setLayout(new GridLayout(1, 2));
			pnlButtons.add(btnAdd);
			pnlButtons.add(btnReturn);
			
			//Adds all panels to main panel
			c.gridx = 0;
			c.gridy = 0;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCaller, c);
			
			c.gridx = 0;
			c.gridy = 1;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlCategory, c);
			
			c.gridx = 0;
			c.gridy = 2;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlPriority, c);
			
			c.gridx = 0;
			c.gridy = 3;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlName, c);
			
			c.gridx = 0;
			c.gridy = 4;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(lblWorkNote, c);
			
			c.gridx = 0;
			c.gridy = 5;
			c.weightx = 1;
			c.weighty = 2;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(scrollSummary, c);
			
			
			c.gridx = 0;
			c.gridy = 7;
			c.weightx = 1;
			c.weighty = 1;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlButtons, c);
			
			//Empty panel to cover the bottom portion of the screen
			JPanel pnlFiller = new JPanel();
			c.gridx = 0;
			c.gridy = 8;
			c.weightx = 1;
			c.weighty = 10;
			c.anchor = GridBagConstraints.LINE_START;
			c.fill = GridBagConstraints.BOTH;
			add(pnlFiller, c);
		}

		/**
		 * Performs an action based on the given  ActionEvent.
		 * @param e user event that triggers an action.
		 */
		public void actionPerformed(ActionEvent e) {
			boolean reset = true; //Assume done unless error
			if (e.getSource() == btnAdd) {
				//Add incident to the list
				int categoryIdx = comboCategory.getSelectedIndex();
				int priorityIdx = comboPriority.getSelectedIndex();
				if (categoryIdx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No category selected");
				} else if (priorityIdx == -1) {
					reset = false;
					JOptionPane.showMessageDialog(IncidentManagerGUI.this, "No priority selected");
				} else {
					String caller = txtCaller.getText();
					String name = txtName.getText();
					String workNote = txtWorkNote.getText();
					
					Category category = null;
					switch (categoryIdx) {
					case 0: category = Category.INQUIRY; break;
					case 1: category = Category.SOFTWARE; break;
					case 2: category = Category.HARDWARE; break;
					case 3: category = Category.NETWORK; break;
					case 4: category = Category.DATABASE; break;
					default: category = null;
					}
					
					Priority priority = null;
					switch(priorityIdx) {
					case 0: priority = Priority.URGENT; break;
					case 1: priority = Priority.HIGH; break;
					case 2: priority = Priority.MEDIUM; break;
					case 3: priority = Priority.LOW; break;
					default: priority = null;
					}
					
					//Get instance of model and add incident
					try {
						IncidentManager.getInstance().addManagedIncidentToList(caller, category, priority, name, workNote);
					} catch (IllegalArgumentException exp) {
						reset = false;
						JOptionPane.showMessageDialog(IncidentManagerGUI.this, "Invalid incident information.");
					}
				}
			} 
			if (reset) {
				//All buttons lead to back incident list
				cardLayout.show(panel, INCIDENT_LIST_PANEL);
				pnlIncidentList.updateTable(null);
				IncidentManagerGUI.this.repaint();
				IncidentManagerGUI.this.validate();
				txtCaller.setText("");
				txtName.setText("");
				txtWorkNote.setText("");
			}
		}
	}
}
