// University of Newcastle
// School of Electrical Engineering and Computer Science
// SENG2050 Web Engineering
// Assignment 1 ONLINE SEATS BOOKING SYSTEM
// Author: Binbin Wang
// Student No: 3214157
// Due Date: 31-03-2019

public class Seat implements java.io.Serializable{
	private String seatNumber;
	private String userID;
	private String bookingTime;
	//Constructor
	public Seat(){
		userID="";
		bookingTime="";
		seatNumber="";
	}
	//set all data
	public void setData(String seatNumber, String userID,String bookingTime){
		this.setSeatNumber(seatNumber);
		this.setUserID(userID);
		this.setBookingTime(bookingTime);
	}
	
	//set each data
	public void setSeatNumber(String seatNumber){
		this.seatNumber=seatNumber;
	}
	public void setUserID(String userID){
		this.userID=userID;
	}
	public void setBookingTime(String bookingTime){
		this.bookingTime=bookingTime;
	}

	//get each data
	public String getUserID(){
		return userID;
	}
	public String getSeatsNumber(){
		return seatNumber;
	}
	public String getBookingTime(){
		return bookingTime;
	}
	
	//output foramt
	public String toString(){
		return "seatNumber: "+seatNumber+
				" ,userID: "+userID+
				" ,bookingTime: "+bookingTime;

	}


	
}
