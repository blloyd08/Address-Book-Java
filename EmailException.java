
//Assignment:	Homework 7
//Author:		Brian Lloyd
//Date:			Spring 2014
//Description:	Use custom exceptions
public class EmailException extends Exception{

		public EmailException(){
			super();
			System.out.println("Email format is incorrect, email must contain a \"@\", a \".\", and a legal domain extention");
		}
		
		public EmailException(String message){
			super(message);
		}
}
