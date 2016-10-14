//Assignment:	Homework 6
//Author:		Brian Lloyd
//Date:			Spring 2014
//Description:	Abstract Person class that holds name, address, city, state, phone number,Cell Phone and Email Address



import java.util.Date;

public abstract class Person implements Comparable<Person>,IPersonAddress,java.io.Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private static final long serialVersionUID = -1206206644258069177L;
	private static int totalNumber = 0;//Total number of person objects created
	
	//return 
	public static int getTotalNumber(){
		return totalNumber;
	}
	public static void setTotalNumber(int total){
		totalNumber = total;
	}
	
	//Last time instance was modified
	private Date lastModified;
	public Date getLastModified(){
		return lastModified;
	}
	private void setLastModified(){
		lastModified = new Date();
	}

	//Full name
	private String fullname;
	public String getFullName(){
		return fullname;
	}
	public void setFullName(String name){
		fullname = name;
		setLastModified();//Mark that instance was changed
	}
	
	//Address
	private String address;
	public String getAddress(){
		return address;
	}
	public void setAddress(String Address){
		address = Address;
		setLastModified();//Mark that instance was changed
	}
		//Set each address variable at once
	public void setAddress(String Address,String City,String State,String Zip){
		//Use sets so that changes to private variable is changed in only one spot
		setAddress(Address);
		setCity(City);
		setState(State);
		setZip(Zip);
	}
	
	//City
	private String city;
	public String getCity(){
		return city;
	}
	public void setCity(String City){
		city = City;
		setLastModified();//Mark that instance was changed
	}
	
	//State
	private String state;
	public String getState(){
		return state;
	}
	public void setState(String State){
		state = State;
		setLastModified();//Mark that instance was changed
	}
	
	//Zip
	private String zip;
	public String getZip(){
		return zip;
	}
	public void setZip(String Zip){
		zip = Zip;
		setLastModified();//Mark that instance was changed
	}
	

	//Used to get person type
	public abstract String getType();
	
	//Constructor
	public Person(String Name,String Address,String City,String State, String Zip){
		setFullName(Name);
		setAddress(Address,City,State,Zip);
		totalNumber ++; //Holds count
	}

	
	//reduce class variable totalNumber by one if it is greater than 0
	public static void deletePerson(){
		if (totalNumber>0){
			totalNumber --;
		}
	}
	
	//Override to string, send full name
	public String toString(){
		return getFullName();
	}
	
	//Show name, address, city, state, zip, phone number
	public String toDetailedString(){
		return "\nName: " + getFullName() + "\nAddress: " + getAddress() + "\nCity: " + getCity() +
				"\nState: " + getState() + "\nZip: " + getZip();
	}
	
	//Used to sort names in a list
	public int compareTo(Person person2){
		return this.getFullName().compareToIgnoreCase(person2.getFullName());
	}
	
}
