// University of Newcastle
// School of Electrical Engineering and Computer Science
// SENG2050 Web Engineering
// Assignment 1 ONLINE SEATS BOOKING SYSTEM
// Author: Binbin Wang
// Student No: 3214157
// Due Date: 31-03-2019

public class User implements java.io.Serializable{
	private String userID;
	private String phone;
	private String address;
	private String email;
	//private String SecurityCode;
	private String[] threeSeats;
	public User(){
		userID="";
		phone="";
		address="";
		email="";
		threeSeats=new String[3];	
	}
	

	//set each data
	public void setUserID(String ID){
		this.userID=ID;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}
	public void setAddress(String address){
		this.address=address;
	}
	public void setEmail(String email){
		this.email=email;
	}

	public boolean addSeat(String SeatNum){
		//check over 3 bookings
		if (getEmptySeatsNum()<3){
			this.threeSeats[getEmptySeatsNum()]=SeatNum;
			return true;
		}else{
			return false;
		}
	}
	
	//get each data
	public String getUserID(){
		return userID;
	}
	public String getPhone(){
		return phone;
	}
	public String getAddress(){
		return address;
	}
	public String getEmail(){
		return email;
	}
	public String[] getSeats(){
		return threeSeats;
	}
	
	//update all Infomation 
	public void updateInfo(String phone, String address, String email){
		this.phone=phone;
		this.address=address;
		this.email=email;
	}
	
	//get the empty seats[] element mubler
	// no empty return 4, Requirement is not more than 3 seats
	private int getEmptySeatsNum(){
		
		int countNum=0;
		for(int i=0;i<3;i++){
			if (threeSeats[i]!=null)countNum++;
		}
		return countNum;

	}
	
	// format output
	public String toString(){
		String seats="null";
		if (threeSeats[0]!=null){seats=" ,Seats[0]: "+threeSeats[0];}
		if (threeSeats[1]!=null){seats+=" ,Seats[1]: "+threeSeats[1];}
		if (threeSeats[2]!=null){seats+=" ,Seats[2]: "+threeSeats[2];}
		return "userID: "+userID+
				" ,phone: "+phone+
				" ,address: "+address+
				" ,email: "+email+
				//",SecurityCode: "+SecurityCode+
				seats;

		
	}
	
}
