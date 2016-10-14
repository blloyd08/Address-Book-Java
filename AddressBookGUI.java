//Assignment:	Capstone Project
//Author:		Brian Lloyd
//Date:			Fall 2014
//Description:	Update Address Book assignment so that it uses a GUI

/*	General Function:
 * 
 * 	Two General Views
 * 	1.	List View(Default) - List on one side and a panel with components that display's contact details
 * 		- Clicking on a name in the list will populate the components with that persons details
 * 	2.	Data Entry View - Only the panel with the data entry components are displayed along with a modify or add button
 * 		- Clicking on the add or remove button with have the data entered verified and then appropriate changes to array will be made
 * 
 * 	Menu
 * 	File Menu
 * 		- Open a saved address book
 * 		- Save the current address book
 * 		- Print the details of the address book to a text document saved the computer
 * 		- Exit the program
 * 	List Menu
 * 		- Search for contact by full name to see its details
 * 		- List, return to list view to see all contacts
 * 	Update Menu
 * 		- Add, switch to data entry view with an add button that allows user to enter new contact details and add to array
 * 		- Delete, search for a contact by full name and then delete that contact if found
 * 		- Modify, serch for contact by full name and then switch to data entry view that is filled with contact details, allow
 * 		  user to change the details and then click modify to make the changes in the array
 * 	Help Menu
 * 		-About, display a message that shows, author, date, and description
 * 
 * 
 * 	Pseudo Code
 * *** AddressBookGUI Class*** Extends JFrame
 * 	-	Initialize a pair of static arrays, one String array that holds the menu title and each menu item title, and a char array that holds the mnemonics for
 * 		each menu and menu item. There is a pair for each menu (File, list, Update, Help)
 * 	-	Initialize an integer that will hold an index that relates to the person that will be modified
 * 	-	Initialize a boolean flag that is used to let event handlers know that the list is being updated and a contact has not been chosen
 * 	-	Declare all class level GUI components
 * 		- JTextFields for name,city,zip,email,cell,and fax data
 * 		- JTextArea for address
 * 		- Radio Buttons for Personal Friend or business contact
 * 		- Combo box for birthday. 3 boxes, year, day, month
 * 		- Custom JPanel class "ListPanel" that holds the list
 * 		- Custom Jpanel class "DataPanel" that holds the components that relate to person details
 * 		- Array list that holds all persons
 * 	
 * 	- Constructor()
 * 		- Set size, default close operation, disable resize, set title, set starting location, set layout to column layout
 * 		- Create and set menu bar
 * 		- Initialize new objects of classes ListPanel and DataPanel
 * 		- Add list and data panels and create and add add/Modify buttons
 * 		- Set dataPanel to read only
 * 		- set frame visible and use pack method to resize frame around components
 * 
 * 	- Main(String[] args) void
 * 		- Create an object of AddressBookGUI class
 * 
 * 	- AddButtons()void
 * 		- Initialize the add button, set visibility to false (Hide), set mnemonic and tooltip text
 * 		- Initialize the modify button, set visibility to false, set mnemonic and tool tip text
 * 		- Add both buttons to frame
 * 		- Setup event handling for add Button
 * 			- Call addContact() method when button clicked
 * 		- Setup event handling for modify Button
 * 			- Call modifyContact() method on button click
 * 
 *	- modifyContact() void
 *		- Check if data entered is valid by calling veifyDataByGUI() method
 *		- If data valid, replace the person in the position of modifyIndex variable index witht he new person created from the dataPanel (getPerson)
 *		- Display the newly modified person in the listView
 *
 *	- addContact() void
 *		- Check if person already exists, display error message if contact with same name already exists, continue if person doesn't exist
 *		- Verify data inputed by calling verifyDataByGUI method, continue if data valid
 *		- Add person to arrayList, display sucess message, and return to listView
 *
 *	- openFile() void
 *		- use JFile chooser to have a user select a saved address book
 *		- If file is valid, call loadAddressBook(filePath) pass in path of selected file
 *		- Refresh listView to display new contacts
 *
 *	- saveFile() void
 * 		- use JFileChooser to have a user select a location to save the current address book
 * 		- call saveAddressBook(filePath) and pass in selected path to save the current address book to disk
 * 
 * 	- printToFile() Void
 * 		- use JFileChooser to have a user select a location to save the current address book details as a text file
 * 		- call printAddressBook(file path) method and pass in the selected file path
 * 
 * 	- createMenuBar() JMenuBar
 * 		- call a separate method for each menu (File,List,Update,Help) that creates the appropriate menu and returns the menu, add that menu to menu bar
 * 
 *	- createFileMenu() JMenu
 *		- Declare Array of MenuItems that will hold each file menu menu item
 *		- Initialize the file menu with the title from the file menu string array
 *		- set mnemonic for file menu
 *		- Loop through and create a menu item for each element in the menu item array, pass in title and mnemonic char into constructor then add menu item to file menu
 *		- create event handler for add menu item that will call method openFile()
 *		- create event handler for save menu item that will call method saveFile()
 *		- create event handler for print menu item that will call method printToFile()
 *		- create event handler for exit menu item that will close the application
 *		- return the fileMenu
 *
 *	- createUpdateMenu() JMenu
 *		- Declare Array of MenuItems that will hold each menu item
 *		- Initialize the menu with the title from the menu string array
 *		- set mnemonic for menu
 *		- Loop through and create a menu item for each element in the menu item array, pass in title and mnemonic char into constructor then add menu item to menu
 *		- create event handler for search menu item that will call findPersonGUI() method and then pass the returned index of the person to the displayContact(index) 
 *			method of the listPanel
 *		- create event handler for list menu item that will call displayList() method
 *		- return the listMenu
 *
 * 	- createListMenu() JMenu
 *		- Declare Array of MenuItems that will hold each List menu menu item
 *		- Initialize the list menu with the title from the file menu string array
 *		- set mnemonic for list menu
 *		- Loop through and create a menu item for each element in the menu item array, pass in title and mnemonic char into constructor then add menu item to list menu
 *		- create event handler for add menu item that will call displayAdd() method
 *		- create event handler for Delete menu item that will ask user to enter a name, find a contact that matches the name, confirm with user that delete is ok, then remove
 *			person from array, update the list panel and clear the dataPanel
 *		- create event handler for modify menu item that will ask user to enter a name, find a contact that matches the name, set the modifyIndex variable to the index of that person
 *			call displayModify() method to change to data entry view and fill data panel with the details of the selected person
 *		- return the updateMenu
 *
 *	- createHelpMenu() JMenu
 *		- Declare Array of MenuItems that will hold each menu item
 *		- Initialize the menu with the title from the menu string array
 *		- set mnemonic for menu
 *		- Loop through and create a menu item for each element in the menu item array, pass in title and mnemonic char into constructor then add menu item to menu
 *		- create event handler for search menu item that will call findPersonGUI() method and then pass the returned index of the person to the displayContact(index) 
 *			method of the listPanel
 *		- create event handler for about menu item that will display a message that details the author, date, and description of program
 *		- return the HelpMenu
 *
 *	- displayAdd() void
 *		- hide the list panel if visible
 *		- clear the dataPanel components to defaults
 *		- set data panel to editable
 *		- hide modify button and make add button visible
 *		- change the panels layout to 1 column
 *		- resize the frame to the components using pack()
 *
 *	- displayModify() void
 *		- hide the list panel if visible
 *		- clear the dataPanel components to defaults
 *		- set data panel to editable
 *		- hide add button and make modify button visible
 *		- change the panels layout to 1 column
 *		- resize the panel to the components using pack()
 *
 *	- displayList() void
 *		- clear list panel using clear() method
 *		- set list panel to visible
 *		- clear the data panel using data panels clearAll() method
 *		- set data panel to read only
 *		- hide add and modify buttons
 *		- set panels layout to 2 columns
 *		- resize frame to components
 *
 *	- findPersonGUI() int
 *		- Declare an integer variable to save the index of person
 *		- use JOption pane to ask user to enter a name of a person, save the input as a string
 *		- pass name to findPerson(name) method to find the index of that person. returns -1 if name not found
 *		- return the value returned from findPerson(name), use JOption pane to notify user if name was not found
 *
 *	- isValidName() boolean
 *		- Get text from name text field and pass to isValidString(string) method
 *		- return the boolean value from the isValidString() method
 *
 *	- findPerson(String sourceName) int
 *		- loop through array, if a name matches the element in the array return the index of that element
 *		- return -1 if person could not be found
 *
 *	- isValidZip() boolean
 *		- get text from zip text field
 *		- check if string is valid string
 *		- check if zip matches a zip code patter of 5 or 9 digit zip format
 *		- return false if any part fails
 *
 *	- saveAddressBook(string filePath) void
 *		- create fileoutput and objectOutput steams from filepath
 *		- write the contents of persons array
 *
 *	- loadAddressBook(String filePath) void
 *		- create fileinput and objectinput streams from file path
 *		- create object from file
 *		- check if object is an instance of array list and then save the arraylist as a persons array list in a try catch method
 *
 *	- printAddressBook(string fileName) void
 *		- create printer writer with file name as path
 *		- loop through persons array and write each contacts detailed string to file
 *
 *	- verifyDataByGUI() boolean
 *		- check if name, address, city ,zip strings are valid using isValidName(),isValidAddress(),isValidCity(),isValidZip();
 *		- if personal radio button is checked, check if email is valid using isValidEmail()
 *		- if business radio button is checked, then check if phone and fax is valid using isValidPhone(),isValidFax();
 *		- if any method returns false(Not valid) then concatinate an error message to a string and display one message with all of the error messages
 *		- return true if all is valid, false if anything is invalid
 *
 *	 - isValidAddress() boolean
 *		- return the the return value of isValidString(string) passing in the address text field text
 *
 *	- isValidCity() boolean
 *		- return the return value of isValidString(string) passing in the city text field text
 *
 *	- isValidPhone() boolean
 *		- check if cell text field text is valid string using isValidString(string) method if valid then check text against a pattern of xxx-xxxxxxx where x is a number
 *		- return true if pattern matches, false if not valid string or pattern doesnt match
 *
 *	- isValidFax() boolean
 *		- check if fax text field text is valid string using isValidString(string) method if valid then check text against a pattern of xxx-xxxxxxx where x is a number
 *		- return true if pattern matches, false if not valid string or pattern doesnt match
 *
 *	- isValidEmail() boolean
 *		- check if email text field is valid string using isValidString(string) method if valid then check text against a patter when first is letters, then an @ symbol, then lets, then a period, then letters
 *		- return true if pattern matches, false if not valid string or pattern doesn't match
 *
 *	- isValidString(String input) boolean
 *		- check if string argument has a length greater than 0
 *		- return result
 *
 *
 *	***** ListPanel Inner Class, Extends JPanel ******
 *		- Declare Default list model for JList and a JList
 *
 *	- Constructor ()
 *		- setLayout to a column layout with one column
 *		- call addList() method
 *
 *	- updateListModel() void
 *		- clear defaultListModel
 *		- loop through persons array and add each person to default list model
 *
 *	- clear() void
 *		- set refreshing flag to true
 *		- call updateListModel()
 *		- unselect items in list
 *		- set refreshing falg to false
 *
 *	- displayContact(int index) void
 *		- set refreshing flag to true, update list model, then set refreshing falg to false
 *		- set the selected item in the list to the supplied index
 *		- fill the data panel with the details of the selected person
 *
 *	- addList() void
 *		- update default list model
 *		- Initialize list using default list model
 *		- set properties of list, single selection, tool tip, visible row count, background color, cell width
 *		- create scroll pane, add list to scroll pane, add scroll pane to panel
 ****End ListPanel Class****
 *
 ****DataPanel Inner Class, extends JPanel****
 *		- Define Jpanels for input, personal, and business
 *
 *	- Constuctor()
 *		- set layout to column layout with 1 column
 *		- call addInputPanel(), addPersonalPanel(), addBusinessPanel()
 *
 *	- addInputPanel() void
 *		- Declare JLabel objects for name, address, city, state, and zip
 *		- Initialize a new Jpanel for inputPanel using a column layout with two columns
 *		- add name label and name textfield to panel and set label's tool tip, mnemonic, and component for text field
 *		- add address label and address textarea to panel and set label's tool tip, mnemonic, and component for text area and set border for text area
 *		- add city label and city textfield to panel and set label's tool tip, mnemonic, and component for text field
 *		- create a string array with the state code for every state in the US
 *		- add state label and state combobox to panel and set label's tool tip, mnemonic, and component for combobox. Set combobox items to states string
 *			array and set the selected item (default) to washington
 *		- add zip label and zip textfield to panel and set label's tool tip, mnemonic, and component for text field
 *		- create a button group, create and add to button group two radio buttons, one for personal and one for business, set personal radio button to selected by default
 *			and set mnemonics and tool tips for each radio button
 *		- add event handler to both radio buttons that toggles the visiblity of personalPanel/businessPanel respectively when appropritate radio button is selected
 *		- add inputPanel to dataPanel
 *
 *	- addBusinessPanel() void
 *		- create a new Jpanel with column layout of 2 columns, set visiblity of panel to hidden by default
 *		- add labels and text fields for cell phone and fax numbers, add mnemonics and tool tips
 *
 *	- getPerson() Person
 *		- create a new person using all the data inputed into the components of the data panel
 *		- return the person created
 *
 *	- addPersonalPanel() void
 *		- Initialize new Panel with column layout with 2 columns called personalPanel
 *		- create and add label and text field for email, set mnemonics and tool tips
 *		- create and add label and add datePanel to panel
 *		- add personal panel to dataPanel
 *	- setReadOnly(boolean) void
 *		- if true passed in, set all components in dataPanel to not editable or disabled
 *		- if false passed in set all components to editable and enabled
 *
 *	- setPersonFields(int index)void
 *		- get person in arraylist that has the passed in index
 *		- set the appropriate fields in dataPanel to the data of the person
 *
 *	- clearAll() void
 *		- set all text fields to defaults, city is olympia, state is wa, all other fields are empty or first element
 *
 **** DatePanel Inner Class, extends JPanel
 *		-Integer arrays for years, months, days. Initialize months array to numbers 1-12
 *	
 *	- Constructor()
 *		- fill years array using method calculateYears() then add years combobox using years array, set tool tip
 *		- add month combobox, set tool tip
 *		- fill days with method calculateDays passing in the integer value of the selected day and month, add days combobox using days array, set tool tip
 *		- create action listener that will recalculate days and then update days combobox with updated list
 *		- add action listener to both month and year combobox
 *
 *	- calculateYears() Integer[]
 *		- get the current year and then create an array of Integer that includes 110 years in the past starting with the current year
 *		- return years array
 *
 *	- calculateDays(int year, int month) Integer[]
 *		- create a calender using the supplied year and month
 *		- loop through using the maxium days in the month to assumble an array with each day number
 *		- return array
 *
 ***End DatePanel Class
 ***End DataPanel Class
 ***End AddressBookGUI Class
 */

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.regex.*;

public class AddressBookGUI extends JFrame {
	//New Variables for GUI
	private static final String[] FILE_MENU_TITLES = {"File","Open","Save","Print","Exit"};//{Menu Title, MenuItem Title,Menu Item Title . . . .
	private static final char[] FILE_MENU_CHAR = {'F','O','S','P','X'};//Char used For accelerator for file menu
	private static final String[] LIST_MENU_TITLES = {"List","Search by Name","All"};
	private static final char[] LIST_MENU_CHAR = {'L','N','A'};
	private static final String[] UPDATE_MENU_TITLES = {"Update","Add","Delete","Modify"};
	private static final char[] UPDATE_MENU_CHAR = {'U','A','D','M'};
	private static final String[] HELP_MENU_TITLES = {"Help","About"};
	private static final char[] HELP_MENU_CHAR = {'H','B'};
	int modifyIndex = 0;//Place holder for person that will be modified
	boolean refreshing = false;//Flag to notify methods that list is refreshing and contact has not been chosen

	//GUI Components
	private static JTextField nameTextField, cityTextField, zipTextField, emailTextField,cellTextField,faxTextField;
	private static JTextArea addressTextArea;
	private static JRadioButton personalRB,businessRB; //Radio button to select either Personal or Buisness contact
	private static JComboBox statesCombo,birthDay,birthMonth,birthYear;//Combo Box to hold states
	private static ListPanel listPanel;
	private static DataPanel dataPanel;
	private static JButton addButton,modifyButton;

	//Hold all persons
	public static ArrayList<Person> persons = new ArrayList<Person>(2);//Holds all Person instances

	//Constructor
	public AddressBookGUI(){

		//Initilize Frame
		setSize(500,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("Address Book");
		setLocationRelativeTo(null);
		setLayout(new ColumnLayout (2,5,5));

		//Set Menu bar
		setJMenuBar(createMenuBar());

		//initilize components (ListPanel,DataPanel)
		listPanel = new ListPanel();//List panel contains list of all contacts
		dataPanel = new DataPanel(); //Data panel displays and retrieves person data

		//Add Components
		add(listPanel);
		add(dataPanel);
		addButtons();//Add add and modify button
		dataPanel.setReadOnly(true);
		setVisible(true);
		pack();
	}

	//Create frame
	public static void main(String[] args) throws ClassNotFoundException,IOException {		
		AddressBookGUI frame = new AddressBookGUI();
	}

	//Initialize and add add and modify buttons
	private void addButtons(){
		//Initilize buttons
		addButton = new JButton("Add");
		addButton.setVisible(false);//Hidden by default
		addButton.setMnemonic('D');
		addButton.setToolTipText("Add person to address book");
		modifyButton = new JButton("Modify");
		modifyButton.setVisible(false);
		modifyButton.setMnemonic('M');
		modifyButton.setToolTipText("Change person's information");

		//add buttons to frame
		add(modifyButton);
		add(addButton);

		//Event Handling Add Button
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addContact();
			}
		});

		//Event Handling Modify Button
		modifyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				modifyContact();
			}
		});
	}

	private void modifyContact(){
		if(verifyDataByGUI()){
			persons.set(modifyIndex, dataPanel.getPerson());
			displayList();
			listPanel.displayContact(modifyIndex);
		}
	}

	//Verify that data is valid then add person to address book
	private void addContact(){
		if (findPerson(nameTextField.getText()) != -1){
			JOptionPane.showMessageDialog(null, "There is already a person with the same name in the address book","Failed",JOptionPane.ERROR_MESSAGE);
		}else{
			if(verifyDataByGUI()){
				persons.add(dataPanel.getPerson());
				JOptionPane.showMessageDialog(null, "Person has been added","Success",JOptionPane.INFORMATION_MESSAGE);
				displayList();
			}
		}
	}

	//Use JFileChooser dialog to have user select a file to load as address book
	private  void openFile(){
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogTitle("Choose address book");
		int retval = chooser.showOpenDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION){
			File theFile = chooser.getSelectedFile();
			if (theFile != null){
				if(! theFile.isDirectory())
					loadAddressBook(theFile.getPath());
				listPanel.clear();
			}
		}
	}

	//Use JFileChooser dialog to have user select a file to save to and the file name
	private  void saveFile(){
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogTitle("Select save location and enter name and extention");
		int retval  = chooser.showSaveDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION){
			File theFile = chooser.getSelectedFile();
			if(theFile!=null){
				if(! theFile.isDirectory())
					saveAddressBook(theFile.getPath());
			}
		}
	}
	
	//Use JFileChooser dialog to have user select a file to save to and the file name
	private  void printToFile(){
		JFileChooser chooser = new JFileChooser(".");
		chooser.setDialogTitle("Select save location to print to. Enter a name without an extention");
		int retval  = chooser.showSaveDialog(null);
		if (retval == JFileChooser.APPROVE_OPTION){
			File theFile = chooser.getSelectedFile();
			if(theFile!=null){
				if(! theFile.isDirectory())
					printAddressBook(theFile.getPath());
			}
		}
	}

	//Add MenuBar, Menus, and MenuItems (File, List, Update, Help) to frame
	private  JMenuBar createMenuBar(){
		JMenuBar menuBar = new JMenuBar();

		//Add File,List,Update, and Help Menu to menu bar
		menuBar.add(createFileMenu());//File Menu
		menuBar.add(createListMenu());//List Menu
		menuBar.add(createUpdateMenu());//Update Menu
		menuBar.add(createHelpMenu());//Help Menu
		return menuBar;
	}

	//create File menu and File menu Items, return the menu when complete
	private  JMenu createFileMenu(){
		JMenuItem[] fileItems = new JMenuItem[FILE_MENU_TITLES.length - 1];//File Menu Items (-1 due to first element being title for file menu)

		//Create File Menu and File Menu Items
		//File Menu
		JMenu fileMenu = new JMenu(FILE_MENU_TITLES[0]);
		fileMenu.setMnemonic(FILE_MENU_CHAR[0]);
		//Create File Menu Items
		for (int i = 0;i<FILE_MENU_TITLES.length -1;i++){
			fileItems[i] = new JMenuItem(FILE_MENU_TITLES[i + 1],FILE_MENU_CHAR[i+1]);//FILE_MENU_TITLES + 1, MenuItems Titles start at element 1 not 0
			fileMenu.add(fileItems[i]);
		}

		//Add Open Operation for Open Button
		fileItems[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				openFile();
			}
		});

		//Add Save Operation for Save Button
		fileItems[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				saveFile();
			}
		});

		//Add Print Operation for Print Button
		fileItems[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				printToFile();
			}
		});

		//Add Close Operation for Exit Button
		fileItems[3].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(EXIT_ON_CLOSE);
			}
		});

		//Return completed menu
		return fileMenu;
	}

	//Display dataPanel with an add button
	private   void displayAdd(){
		if (listPanel.isVisible()){
			listPanel.setVisible(false);
		}
		dataPanel.clearAll();
		dataPanel.setReadOnly(false);

		//Set add button visible
		addButton.setVisible(true);
		modifyButton.setVisible(false);

		//Resize Frame
		setLayout(new ColumnLayout(1,5,5));//Change layout to one column
		this.pack();
	}

	//Set components so only data panel and modify button are visible
	private void displayModify(){
		if (listPanel.isVisible()){
			listPanel.setVisible(false);
		}
		dataPanel.clearAll();
		dataPanel.setReadOnly(false);

		//Set add button visible
		modifyButton.setVisible(true);
		addButton.setVisible(false);

		//Resize Frame
		setLayout(new ColumnLayout(1,5,5));//Change layout to one column
		this.pack();
	}

	//Setup listPanel and dataPanel so they can be used to display current contact information
	private  void displayList(){
		//Set list visible
		listPanel.clear();
		listPanel.setVisible(true);

		//Setup data panel
		dataPanel.clearAll();
		dataPanel.setReadOnly(true);

		//Hide add or remove buttons
		addButton.setVisible(false);
		modifyButton.setVisible(false);

		//Resize Frame
		setLayout(new ColumnLayout(2,5,5));//Set list panel and data panel side by side
		pack();
	}

	//Create List menu with Search by name, All menu items
	private JMenu createListMenu(){
		JMenu listMenu = new JMenu(LIST_MENU_TITLES[0]);//List Menu
		JMenuItem[] listMenuItems = new JMenuItem[LIST_MENU_TITLES.length - 1];//List Menu Items (-1 due to first element being title for file menu)

		//Set menu mnemonic
		listMenu.setMnemonic(LIST_MENU_CHAR[0]);

		//Loop through and create menu items
		for (int i = 0;i<LIST_MENU_TITLES.length -1;i++){
			listMenuItems[i] = new JMenuItem(LIST_MENU_TITLES[i + 1],LIST_MENU_CHAR[i+1]);//List_MENU_TITLES + 1, MenuItems Titles start at element 1 not 0
			listMenu.add(listMenuItems[i]);
		}

		//Add Search Operation for Search by Name Button
		listMenuItems[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = findPersonGUI();
				if (index !=-1){
					listPanel.displayContact(index);
				}
			}
		});

		//Add list all Operation for All Button
		listMenuItems[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				displayList();//Display listPanel
			}
		});

		//Return completed menu
		return listMenu;
	}

	//Create update menu with menu items (Add,Delete,Modify) return the menu when created
	private JMenu createUpdateMenu(){
		JMenu updateMenu = new JMenu(UPDATE_MENU_TITLES[0]);
		JMenuItem[] updateItems = new JMenuItem[UPDATE_MENU_TITLES.length - 1];//File Menu Items (-1 due to first element being title for file menu)

		//set Menu Mnemonic
		updateMenu.setMnemonic(UPDATE_MENU_CHAR[0]);
		//Loop through and create each Update Menu Items
		for (int i = 0;i<UPDATE_MENU_TITLES.length -1;i++){
			updateItems[i] = new JMenuItem(UPDATE_MENU_TITLES[i + 1],UPDATE_MENU_CHAR[i+1]);//FILE_MENU_TITLES + 1, MenuItems Titles start at element 1 not 0
			updateMenu.add(updateItems[i]);
		}

		//Add add Operation for Add Button
		updateItems[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				displayAdd();
			}
		});


		//Add delete Operation for Delete Button
		updateItems[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = findPersonGUI();
				if (index!= -1){
					if(JOptionPane.showConfirmDialog(null, "Name was found, are you sure you want to remove " + persons.get(index).getFullName() + " from address book","Confirm",JOptionPane.YES_NO_OPTION) ==
							JOptionPane.OK_OPTION){
						//Remove Person
						persons.remove(index);
						Person.deletePerson();//Reduce total number of persons by 1
						listPanel.clear();
						dataPanel.clearAll();
					}
				}
			}
		});

		//Add Modify Operation for Modify Button
		updateItems[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int index = findPersonGUI();
				if (index != -1){
					modifyIndex = index;//Index of element to modify
					displayModify();//Display dataPanel and modify button
					dataPanel.setPersonFields(modifyIndex);
				}
			}
		});

		//Return completed menu
		return updateMenu;
	}

	//Method that uses GUI to find Person
	private int findPersonGUI(){
		int personIndex = -1;//-1 means not found
		String name = JOptionPane.showInputDialog(null,"Enter user name","Find Contact",JOptionPane.INFORMATION_MESSAGE);
		if (name != null){
			personIndex = findPerson(name);
			if (personIndex == -1){
				JOptionPane.showMessageDialog(null, name + " could not be found in address book","Name not found",JOptionPane.ERROR_MESSAGE);
			}
		}
		return personIndex;
	}

	//Create update menu with menu items (Add,Delete,Modify) return the menu when created
	private JMenu createHelpMenu(){
		JMenu helpMenu = new JMenu(HELP_MENU_TITLES[0]);
		JMenuItem[] helpItems = new JMenuItem[HELP_MENU_TITLES.length - 1];//Help Menu Items (-1 due to first element being title for file menu)

		//Set help menu mnemonic
		helpMenu.setMnemonic(HELP_MENU_CHAR[0]);
		//Loop through and create each update Menu Items
		for (int i = 0;i<HELP_MENU_TITLES.length -1;i++){
			helpItems[i] = new JMenuItem(HELP_MENU_TITLES[i + 1],HELP_MENU_CHAR[i+1]);//HELP_MENU_TITLES + 1, MenuItems Titles start at element 1 not 0
			helpMenu.add(helpItems[i]);
		}

		//Add about Operation for about button
		helpItems[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null, "Author: Brian Lloyd\nCreated: Fall 2014\nAddress Book is a program that stores contact information for personal friends and business associates",
						"About",JOptionPane.INFORMATION_MESSAGE);
			}
		});

		//Return completed menu
		return helpMenu;
	}


	//Get full name
	private boolean isValidName(){
		return isValidString(nameTextField.getText());
	}

	//Search through person array list to find name that matches entered name
	//Return -1 if name not found
	private int findPerson(String sourceName){
		int personIndex = -1; //Return value, index of person in array list

		//Run through persons array and look for matching name
		for (int i = 0; i < Person.getTotalNumber(); i++){
			if (persons.get(i).getFullName().equalsIgnoreCase(sourceName)){
				personIndex = i;
				break;
			}
		}
		return personIndex;//Return the index of the person that was found
	}

	//Validate zip code to 5 digits
	private boolean isValidZip(){
		String zip = zipTextField.getText();
		if (isValidString(zip)){
			//Check if string matches zip code pattern
			Pattern zipPattern = Pattern.compile("^\\d{5}\\p{Punct}?\\s?(?:\\d{4})?$");
			Matcher match = zipPattern.matcher(zip);
			return match.matches();
		}else{
			return false;//Not valid string
		}
	}

	//Save address book persons in array to a file on disk
	private static void saveAddressBook(String filePath) {
		try{
			FileOutputStream f_out = new 
					FileOutputStream(filePath);

			// Write object with ObjectOutputStream
			ObjectOutputStream obj_out = new
					ObjectOutputStream (f_out);

			// Write object out to disk
			obj_out.writeObject (persons);
			JOptionPane.showMessageDialog(null, "Address book saved to " + filePath,"Save Sucessful",JOptionPane.INFORMATION_MESSAGE);
		} catch(IOException e){
			JOptionPane.showMessageDialog(null, "Could not save to disk, failed to compelte write\nError Message: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	//Load address book persons from a file saved on disk
	private static void loadAddressBook(String filePath){

		try{
			// Read from disk using FileInputStream
			FileInputStream f_in = new 
					FileInputStream(filePath);

			// Read object using ObjectInputStream
			ObjectInputStream obj_in = 
					new ObjectInputStream (f_in);
			try{
				// Read an object
				Object obj = obj_in.readObject();

				if (obj instanceof ArrayList)
				{
					// Cast object to an person array list
					ArrayList<Person> savedPersons = (ArrayList<Person>) obj;

					persons = savedPersons;
					Person.setTotalNumber(persons.size());//Set static variable total number to new total number
					JOptionPane.showMessageDialog(null, Person.getTotalNumber() + " persons have been uploaded to program","Sucess",JOptionPane.INFORMATION_MESSAGE);
				} else{
					JOptionPane.showMessageDialog(null, "File does not contain data for Person array","Error",JOptionPane.ERROR_MESSAGE);
				}
			} catch(ClassNotFoundException e){
				JOptionPane.showMessageDialog(null, "Can't read data","Error",JOptionPane.ERROR_MESSAGE);
			}
		}catch (EOFException ex){
			JOptionPane.showMessageDialog(null, "End of file error","Error",JOptionPane.ERROR_MESSAGE);
		}
		catch(IOException e){
			JOptionPane.showMessageDialog(null, "File could not be read, something went wrong.\nError Message: " + e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
		}
	}

	//Print address book
	private static void printAddressBook(String fileName){
		if (Person.getTotalNumber() > 0){//Check if address book has someone before printing
			String[] personFields;


			fileName += ".txt"; //Add file extension

			try{
				///Write to file
				PrintWriter printWrite = new PrintWriter(fileName);
				for (int i=0;i<persons.size();i++){
					personFields = persons.get(i).toDetailedString().split("\n");
					printWrite.println("Person: " + i);
					for (int valPos = 0;valPos < personFields.length;valPos++){
						printWrite.println(personFields[valPos]);
					}
					printWrite.println();
				}
				printWrite.close();
				JOptionPane.showMessageDialog(null, "Address book saved to " + fileName,"Success",JOptionPane.INFORMATION_MESSAGE);
				System.out.println();
			} catch(FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "Print file not found","Print Failed",JOptionPane.ERROR_MESSAGE);
				System.out.println("Print file not found");
			}
		}else{//No persons to print info
			JOptionPane.showMessageDialog(null, "Please add someone to address book before printing","No contacts",JOptionPane.ERROR_MESSAGE);
		}
	}

	//Check all data fields for valid data, show error message if not valid
	public boolean verifyDataByGUI(){
		boolean valid = true;
		String errorMessage = "";//Error messages are concatenated to this string

		//Name
		if (!isValidName()){
			valid = false;
			errorMessage += "\nName is not valid, no text entered";
		}
		//Address
		if (!isValidAddress()){
			valid = false;
			errorMessage += "\nAddress is not valid, no text entered";
		}
		//City
		if (!isValidCity()){
			valid = false;
			errorMessage += "\nCity is not valid, no text entered";
		}
		//Zip
		if (!isValidZip()){
			valid = false;
			errorMessage += "\nZip code is not valid, must be 5 digit zip code";
		}

		//Personal Fields
		if (personalRB.isSelected()){
			if (!isValidEmail()){
				valid = false;
				errorMessage += "\nEmail is not valid, must have an @ symbol, a ., and a qualified domain name after address";
			}
		}else{//Business fields
			if (!isValidPhone()){
				valid = false;
				errorMessage += "\nCell phone number is not valid, must be in format XXX-XXXXXXX(ex. 360-1234567)";
			}
			if (!isValidFax()){
				valid = false;
				errorMessage += "\nFax number is not valid, must be in format XXX-XXXX(ex. 360-1234567)";
			}
		}

		//Display error message if not valid
		if (!valid){
			JOptionPane.showMessageDialog(null, errorMessage,"Error",JOptionPane.ERROR_MESSAGE);
		}

		return valid;
	}

	//Check if address in dataPanal is a valid address
	private boolean isValidAddress(){
		return isValidString(addressTextArea.getText());
	}

	//Check if city in dataPanal is a valid city
	private boolean isValidCity(){
		return isValidString(cityTextField.getText());
	}

	// Get input user and validate phone number to XXX-XXXXXXX format
	public boolean isValidPhone(){
		String number = cellTextField.getText();
		Pattern regPattern = Pattern.compile("\\d{3}-\\d{7}");
		if (isValidString(number)){
			Matcher matcher = regPattern.matcher(number);
			return matcher.matches();//Return whether or not string matches pattern
		}else
			return false;//Not valid string

	}

	// Get input user and validate phone number to XXX-XXXXXXX format
	public boolean isValidFax(){
		String number = faxTextField.getText();
		Pattern regPattern = Pattern.compile("\\d{3}-\\d{7}");
		if (isValidString(number)){
			Matcher matcher = regPattern.matcher(number);
			return matcher.matches();//Return whether or not string matches pattern
		}else
			return false;//Not valid string
	}

	// Get input user and validate email
	public boolean isValidEmail(){
		String email = emailTextField.getText();
		Pattern regPattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		if (isValidString(email)){
			Matcher matcher = regPattern.matcher(email);
			return matcher.matches();//Return boolean match to pattern
		}else
			return false;//Not valid string
	}

	//Keep asking for input until input has at least one character
	public boolean isValidString(String input){
		return input.length()>0;
	}

	private class ListPanel extends JPanel{
		DefaultListModel contactListModel = new DefaultListModel();
		JList contactList;

		//Constuctor
		ListPanel(){
			setLayout(new ColumnLayout(1,5,5));
			addList();

		}

		//Fill List model with Persons ArrayList
		public void updateListModel(){
			contactListModel.clear();;
			//Fill List Model with personsArrayList itmes
			for (Person p:persons){
				contactListModel.addElement(p);
			}
		}

		//Refresh list an set to default
		public void clear(){
			refreshing =true;//Flags to prevent event handlers from activating
			updateListModel();//Update list
			contactList.setSelectedIndex(-1);//Unselect items
			refreshing = false;//Flags to prevent event handlers from activating
		}
		//Called from outside class to display a specific contact
		public void displayContact(int index){
			refreshing = true;
			updateListModel();//Make sure list is up to date
			refreshing  = false;
			contactList.setSelectedIndex(index);//Select contact in list
			dataPanel.setPersonFields(index);//Display contact info in components
		}

		private void addList(){
			updateListModel();//Fill List model

			//Initialize contact list
			contactList = new JList(contactListModel);
			contactList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//Allow only one selection
			contactList.setToolTipText("Select a contact to view information");
			contactList.setVisibleRowCount(15);
			contactList.setBackground(Color.WHITE);
			contactList.setFixedCellWidth(200);

			//Create Scroll pane, add list to scroll pane, add scroll pane to list Panel;
			JScrollPane scroll = new JScrollPane();
			scroll.getViewport().add(contactList);
			add(scroll);

			//Event handling
			contactList.addListSelectionListener(new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent e){
					if(!e.getValueIsAdjusting()&& !refreshing){

						int index = contactList.getSelectedIndex();
						dataPanel.setPersonFields(index);
					}
				}
			});
		}
	}

	//dataPanel displays fields that resemble the data fields of a contact
	private class DataPanel extends JPanel{
		JPanel inputPanel,personalPanel,businessPanel;

		//Constructor 
		public DataPanel(){
			setLayout(new ColumnLayout(1,5,5));
			addInputPanel();//Add general contact info fields
			addPersonalPanel();//Add fields specific to personal friend contacts
			addBusinessPanel();//Hidden by default, changed by Radio button
		}

		//Add components for generic contact info (Shared by personal and business contacts)
		//Handle radio button events (Toggle persoanl/business panels visibility)
		private void addInputPanel(){
			JLabel nameLabel,addressLabel,cityLabel,stateLabel,zipLabel;

			inputPanel = new JPanel(new ColumnLayout(2,5,5,ColumnLayout.HEIGHT));
			//Name Field
			inputPanel.add(nameLabel =new JLabel("Name"));
			inputPanel.add(nameTextField = new JTextField(10));
			nameTextField.setToolTipText("Enter contact's name");
			nameLabel.setLabelFor(nameTextField);
			nameLabel.setDisplayedMnemonic('N');

			//Address Field
			inputPanel.add(addressLabel = new JLabel("Address"));
			inputPanel.add(addressTextArea = new JTextArea(1,15));
			addressTextArea.setBorder(nameTextField.getBorder());
			addressTextArea.setToolTipText("Enter contact's address");
			addressLabel.setLabelFor(addressTextArea);
			addressLabel.setDisplayedMnemonic('A');

			//City Field
			inputPanel.add(cityLabel = new JLabel ("City"));
			inputPanel.add(cityTextField = new JTextField("Olympia",10));
			cityTextField.setToolTipText("Enter contact's city");
			cityLabel.setLabelFor(cityTextField);
			cityLabel.setDisplayedMnemonic('C');

			//State Field
			String[] states = new String[]{"AL","AK","AR","AZ","CA","CO","CT","DC","DE","FL","GA","HI","IA","ID","IL","IN","KS","KY","LA","MA","MD","ME",
					"MI","MN","MO","MS","MT","NC","ND","NE","NH","NJ","NM","NV","NY","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VA","VT","WA","WI","WV","WY"};
			inputPanel.add(stateLabel = new JLabel("State"));
			inputPanel.add(statesCombo = new JComboBox(states));
			statesCombo.setToolTipText("Select contact's state");
			statesCombo.setSelectedItem("WA");//Set default state
			stateLabel.setLabelFor(statesCombo);
			stateLabel.setDisplayedMnemonic('S');

			//Zip Field
			inputPanel.add(zipLabel = new JLabel("Zip Code"));
			inputPanel.add(zipTextField = new JTextField(5));
			zipTextField.setToolTipText("Enter 5 digit zip code");
			zipLabel.setLabelFor(zipTextField);
			zipLabel.setDisplayedMnemonic('Z');

			//Contact Type Radio Buttons business and personal, add to button group
			ButtonGroup rbGroup = new ButtonGroup();
			//Personal Radio Button
			personalRB = new JRadioButton("Personal Friend");
			personalRB.setToolTipText("Contact is a personal friend");
			personalRB.setMnemonic('P');
			//Business Radio Button
			businessRB = new JRadioButton("Buisness");
			businessRB.setToolTipText("Contact is a business contact");
			businessRB.setMnemonic('B');
			//Add to ButtonGroup
			rbGroup.add(personalRB);
			rbGroup.add(businessRB);
			personalRB.setSelected(true);//Default selection
			//Add to panel
			inputPanel.add(personalRB);
			inputPanel.add(businessRB);

			//Setup Event handling for BOTH radio buttons
			ActionListener toggleTypeListener = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					personalPanel.setVisible(personalRB.isSelected());//Set personal panel visibility
					businessPanel.setVisible(businessRB.isSelected());//Set business panel visibility
				}
			};
			personalRB.addActionListener(toggleTypeListener);
			businessRB.addActionListener(toggleTypeListener);

			//Add input panel to data panel
			add(inputPanel);
		}

		//Add components specified to business contacts (Cell and fax numbers)
		private void addBusinessPanel(){
			JLabel phoneLabel,faxLabel;

			//Business Panel Creation(Hidden by Default)
			businessPanel = new JPanel(new ColumnLayout(2,5,5));
			businessPanel.setVisible(false);//Hidden by Default
			//Add Phone Field
			//Phone Label
			businessPanel.add(phoneLabel = new JLabel("Phone"));
			phoneLabel.setDisplayedMnemonic('H');
			phoneLabel.setLabelFor(cellTextField);
			//Phone Text Field
			businessPanel.add(cellTextField = new JTextField(11));
			cellTextField.setToolTipText("Enter buisness contacts cell phone number must be in format XXX-XXXXXXX (ex. 360-1234567)");
			//Add Fax Field
			//Fax Label
			businessPanel.add(faxLabel = new JLabel("Fax"));
			faxLabel.setDisplayedMnemonic('F');
			faxLabel.setLabelFor(faxTextField);
			//Fax Text Field
			businessPanel.add(faxTextField = new JTextField(11));
			faxTextField.setToolTipText("Enter business contact fax number, must be in format XXX-XXXXXXXX(ex.360-11234567)");
			//Add Business panel to input panel
			add(businessPanel);
		}

		//Collect data from fields and return as a Person
		public Person getPerson(){
			String name,address,city,state,zip,email,cell,fax;//Temporarily holds data
			int year,month,day;//value of birthday combo boxes
			Person returnPerson;//Value to be returned

			name = nameTextField.getText();
			address = addressTextArea.getText();
			city = cityTextField.getText();
			state = statesCombo.getSelectedItem().toString();
			zip = zipTextField.getText();
			//Get Personal/Business data
			if(personalRB.isSelected()){//Personal Friend
				email = emailTextField.getText();
				year = (int)birthYear.getSelectedItem();
				month= (int)birthMonth.getSelectedItem();
				day = (int)birthDay.getSelectedItem();
				returnPerson = new PersonalFriend(name,address,city,state,zip,email,year,month,day);
			}else{//Business Associate
				cell = cellTextField.getText();
				fax = cellTextField.getText();
				returnPerson = new BusinessAssociate(name,address,city,state,zip,cell,fax);
			}
			return returnPerson;
		}

		//Add personal fields to dataPanel. (Email, birthdate with comboboxes)
		//Visible by default since personal is default Radio Button, is hidden when Radio Button is business
		private void addPersonalPanel(){
			JLabel emailLabel,birthDayLabel;
			//Personal Panel Creation(Visible by Default)
			personalPanel = new JPanel(new ColumnLayout(2,5,5));

			//Add Email Field
			//Email Label
			personalPanel.add(emailLabel = new JLabel("Email"));
			emailLabel.setDisplayedMnemonic('E');
			emailLabel.setLabelFor(emailTextField);
			//Email Text Field
			personalPanel.add(emailTextField = new JTextField(20));
			emailTextField.setToolTipText("Enter email, must include an @ symbol before the email and then a qualified domain");

			//Add birthday Field
			//Birthday label
			personalPanel.add(birthDayLabel = new JLabel("Birth Date"));
			birthDayLabel.setDisplayedMnemonic('D');
			birthDayLabel.setLabelFor(birthYear);
			//Add Birthday Combo boxes
			personalPanel.add(new DatePanel());

			//Add to data panel
			add(personalPanel);
		}

		//Change Editable fields
		public void setReadOnly(boolean lock){//Not editable when passed in true
			boolean enable = true;

			if (lock)//If not editable is requested
				enable = !lock;

			//Set editable Generic Fields
			nameTextField.setEditable(enable);
			addressTextArea.setEditable(enable);
			cityTextField.setEditable(enable);
			statesCombo.setEnabled(enable);
			zipTextField.setEditable(enable);
			personalRB.setEnabled(enable);
			businessRB.setEnabled(enable);

			//Personal Fields
			emailTextField.setEditable(enable);

			//Birth date combo boxes
			birthYear.setEnabled(enable);
			birthMonth.setEnabled(enable);
			birthDay.setEnabled(enable);

			//Business Fields
			cellTextField.setEditable(enable);
			faxTextField.setEditable(enable);
		}

		public void setPersonFields(int index){
			Person viewPerson = persons.get(index);

			//Clear all fields to ensure no residual data
			clearAll();//Clears all fields of data

			//Fill Generic Fields
			nameTextField.setText(viewPerson.getFullName());
			addressTextArea.setText(viewPerson.getAddress());
			cityTextField.setText(viewPerson.getCity());
			statesCombo.setSelectedItem(viewPerson.getState());
			zipTextField.setText(viewPerson.getZip());

			//Check Type and fill appropriate fields
			if(viewPerson.getType().equals("Personal Friend")){//Personal Friend
				//Handle visible panels
				personalPanel.setVisible(true);
				businessPanel.setVisible(false);

				personalRB.setSelected(true);
				emailTextField.setText(((PersonalFriend)viewPerson).getEmail());
				//Set Birthday ComboBoxes
				Date birthdate = ((PersonalFriend)viewPerson).getBirthday();
				birthYear.setSelectedItem(birthdate.getYear()+1900);//+1900 due to depricated method
				birthMonth.setSelectedItem(birthdate.getMonth()+1);//+1 due to depricated method
				birthDay.setSelectedIndex(birthdate.getDate()-1);
			}else{//Business type
				personalPanel.setVisible(false);
				businessPanel.setVisible(true);
				businessRB.setSelected(true);
				cellTextField.setText(((BusinessAssociate)viewPerson).getCell());
				faxTextField.setText(((BusinessAssociate)viewPerson).getFax());
			}
		}

		//Set all fields to default value
		public void clearAll(){
			//Clear Generic Fields
			nameTextField.setText("");
			addressTextArea.setText("");
			cityTextField.setText("Olympia");//Olympia is Default city
			statesCombo.setSelectedItem("WA");//WA is default state
			zipTextField.setText("");
			personalRB.setSelected(true);//Personal Radio Button is default
			//Toggle personal/Business Panel
			personalPanel.setVisible(true);
			businessPanel.setVisible(false);

			//Personal Fields
			emailTextField.setText("");
			//Birth date combo boxes
			birthYear.setSelectedIndex(0);
			birthMonth.setSelectedIndex(0);
			birthDay.setSelectedIndex(0);

			//Business Fields
			cellTextField.setText("");
			faxTextField.setText("");
		}

		//Date panel contains the combobox for birthdate (year,month,day) and handles events and calculates appropriate days according to month and year
		private class DatePanel extends JPanel{
			Integer[] years;//Hold years for yearCombobox
			Integer[] months = new Integer[] {1,2,3,4,5,6,7,8,9,10,11,12};//Months 1-12 for month combobox
			Integer[] days;//Holds days for days combobox, recalculated when month or year changes

			//Constructor
			DatePanel(){
				setLayout(new ColumnLayout (3,5,5));
				years = calculateYears();
				//Year Combobox
				add(birthYear = new JComboBox(years));
				birthYear.setToolTipText("Select birth year");
				//Month ComboBox
				add(birthMonth = new JComboBox(months));
				birthMonth.setToolTipText("Select birth month");
				//Day ComboBox
				days = calculateDays((int)birthYear.getSelectedItem(),(int)birthMonth.getSelectedItem()-1);//Minus 1 calendar class month starts at 0
				add(birthDay = new JComboBox(days));
				birthDay.setToolTipText("Select Birth Day");

				//Add actionListeners
				//Update Days Listener, fills days combobox with the appropriate days when month or year changes
				ActionListener updateDaysListener =new ActionListener(){
					public void actionPerformed(ActionEvent e){
						days = calculateDays((int)birthYear.getSelectedItem(),(int)birthMonth.getSelectedItem()-1);//Minus 1 calendar class month starts at 0
						birthDay.setModel(new DefaultComboBoxModel(days));//Fill days combobox with array of new days in month
					}
				};
				birthYear.addActionListener(updateDaysListener);
				birthMonth.addActionListener(updateDaysListener);
			}

			//Create an integer array containing the current year back to a specified number of years
			private Integer[] calculateYears(){
				int numYears = 110; //Number of years to put in array
				Integer[] years = new Integer[numYears];
				int currYear = Calendar.getInstance().get(Calendar.YEAR);
				//Loop through and add each year to array up to numYears
				for (int i = 0;i<numYears;i++){
					years[i] = currYear - i;
				}
				return years;
			}

			//Create an integer array contains the number of days in a given month in a given year
			private Integer[] calculateDays(int year,int month){
				Integer[] calcDays;//Hold all days in the month
				int daysInMonth;//Total number of days in a month

				Calendar newCal = new GregorianCalendar(year,month,1);//Calendar used to find max days
				daysInMonth = newCal.getActualMaximum(Calendar.DAY_OF_MONTH);//Get max days in the month

				calcDays = new Integer[daysInMonth];//Initialize array
				//Loop through and add integers to array starting from day 1 unti the end of days
				for (int i = 0;i<daysInMonth;i++){
					calcDays[i] = 1+i;//1 + i because days start at 1 not at 0
				}

				return calcDays;
			}
		}

	}

}