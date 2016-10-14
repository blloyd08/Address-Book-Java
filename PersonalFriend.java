//Assignment:	Homework 6
//Author:		Brian Lloyd
//Date:			Spring 2014
//Description:	Extends Person class, adds birthday, age, and zodiac sign

import java.util.Date;
import java.text.*;

public class PersonalFriend extends Person {

	private static final long serialVersionUID = 1L;
	//Birth day
	private Date birthday;
	public void setBirthday(int year,int month, int day){//Read only
		birthday = new Date(year-1900,month -1,day);//Minus 1900 for depreicated date method, minus 1 for same reason
	}
	public Date getBirthday(){
		return birthday;
	}
	
	//Age - Calculated from birthday
	public int getAge(){
		Date birth = getBirthday(),curDate = new Date();
		int month = birth.getMonth(), curMonth = curDate.getMonth();//Get months as variables
		int day = birth.getDate(), curDay = curDate.getDate();//Get days as variables for easy use
		int age = curDate.getYear() - birth.getYear();//Find year difference
		
		//Check if birthday has passed for this year
		if (month>curMonth || (month ==curMonth && day>curDay)){
			age--; //Birthday has not passed for this year, reduce by one
		}
		
		return age;
	}
	
	//Email
	private String email;
	public String getEmail(){
		return email;
	}
	public void setEmail(String address){
		email=address;
	}
	
	//Get person type
	public String getType(){
		return "Personal Friend";
	}
	
	//Constructor
	public PersonalFriend(String name,String address,String city,String state, String zip,
			String eMail, int year,int month,int day){
		super(name,address,city,state,zip);//Superclass constructor
		setBirthday(year,month,day);
		setEmail(eMail);
	}
	
	
	//To String method, append personal friend specific info onto string
	public String toDetailedString(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		return super.toDetailedString() + "\nBirthday(YYYY/MM/DD): " + sdf.format(getBirthday()) +"\nAge: " + getAge() + "\nEmail: " + getEmail();
	}

}
