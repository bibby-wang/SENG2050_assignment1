public class User implements java.io.Serializable{
	private String UserID, Phone, Address, Email,SecurityCode;

	public User(String UserID, String Phone, String Address, String Email, String SecurityCode){
		this.UserID=UserID;
		this.Phone=Phone;
		this.Address=Address;
		this.Email=Email;
		this.SecurityCode=SecurityCode;
	}	

	public void setInformation(String UserID, String Phone, String Address, String Email, String SecurityCode){
		this.setUserID(UserID);
		this.setPhone(Phone);
		this.setAddress(Address);
		this.setEmail(Email);
		this.setSecurityCode(SecurityCode);
	}
	
	//set each data
	public void setUserID(String UserID){
		this.UserID=UserID;
	}
	public void setPhone(String Phone){
		this.Phone=Phone;
	}
	public void setAddress(String Address){
		this.Address=Address;
	}
	public void setEmail(String Email){
		this.Email=Email;
	}
	public void setSecurityCode(String SecurityCode){
		this.SecurityCode=SecurityCode;
	}
	
	//get each data
	public String getUserID(){
		return UserID;
	}
	public String getPhone(){
		return Phone;
	}
	public String getAddress(){
		return Address;
	}
	public String getEmail(){
		return UserID;
	}
	public String getSecurityCode(){
		return SecurityCode;
	}

	
}
