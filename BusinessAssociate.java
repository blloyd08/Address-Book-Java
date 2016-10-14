//Assignment:	Homework 6
//Author:		Brian Lloyd
//Date:			Spring 2014
//Description:	Extends Person class, adds Job Title, fax number, company name
public class BusinessAssociate extends Person {
	private static final long serialVersionUID = 1L;
	//Cell Phone Number
	private String cell;
	public String getCell(){
		return cell;
	}
	public void setCell(String cellNum){
		cell = cellNum;
	}
	
	//Fax Number
	private String fax;
	public String getFax(){
		return fax;
	}
	public void setFax(String number){
		fax=number;
	}
	
	//Person Type
	public String getType(){
		return "Business Associate";
	}
	//Constructor
	public BusinessAssociate(String name,String address,String city,String state, String zip,String cell,String fax){
		super(name,address,city,state,zip);
		setCell(cell);
		setFax(fax);
	}
	
	//ToString OverRide, Add job title, fax, and company name to the super.toString
	public String toDetailedString(){
		return super.toDetailedString() + "\nCell Number: " + getCell() + "\nFax Number: " + getFax();
	}
}
