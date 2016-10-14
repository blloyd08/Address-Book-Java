//Assignment:	Homework 7
//Author:		Brian Lloyd
//Date:			Spring 2014
//Description:	Use custom exceptions
public class PhoneException extends Exception {

	public PhoneException(){
		super();
		System.out.println("Number invalid, number format is (Area Code)-(Number) XXX-XXXXXXX");
	}
	public PhoneException(String message){
		super(message);
	}
}
