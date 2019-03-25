public class User implements java.io.Serializable{
	private String userID;
	private String phone;
	private String address;
	private String email;
	private String SecurityCode;
	private String[] threeSeats;
	public User(){
		userID="";
		phone="";
		address="";
		email="";
		SecurityCode="";
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
	public void setSecurityCode(String SecurityCode){
		this.SecurityCode=SecurityCode;
	}
	public boolean addSeat(String SeatNum){
		
		if (getEmputySeatsNum()<3){
			this.threeSeats[getEmputySeatsNum()]=SeatNum;
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
	public String getSecurityCode(){
		return SecurityCode;
	}
	public String[] getSeats(){
		return threeSeats;
	}
	
	//get Emputy seats Num Requirement: no more than 3
	public int getEmputySeatsNum(){
		
		int countNum=0;
		for(int i=0;i<3;i++){
			if (threeSeats[i]!="")countNum++;
		}
		return countNum;

	}
	public String toString(){
		return "userID: "+userID+
				",phone: "+phone+
				",address: "+address+
				",email: "+email+
				",SecurityCode: "+SecurityCode+
				",Seats[0]: "+threeSeats[0].toString()+
				",Seats[1]: "+threeSeats[1].toString()+
				",Seats[2]: "+threeSeats[2].toString();
		
	}
	
}
