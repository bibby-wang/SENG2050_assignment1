public class Seat implements java.io.Serializable{
	private String seatNumber;
	private String userID;
	private String bookingTime;
	public Seat(){
		userID="";
		bookingTime="";
		seatNumber="";
		}

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
	public String toString(){
		return "seatNumber: "+seatNumber+
				",userID: "+userID+
				",bookingTime: "+bookingTime;

	}

	
}
