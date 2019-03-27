import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Random;

@WebServlet(urlPatterns = {"/BookingSeatPage"})
public class BookingSeatPage extends HttpServlet {
	private Seat[] seatsList;
	private User[] usersList;
	private String bookingTime,seatNumber,userID, phone, address, email,securityCode;
	
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
	private String addressUesrsData="../webapps/c3214157_assignment1/WEB-INF/data/usersData.ser";	
	private String verifyCode;//get verify Code
	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter outHTML = response.getWriter();
		String htmlString="<!DOCTYPE HTML>";
		seatNumber=request.getParameter("seatNumber");//get the selected seat Number
		bookingTime=request.getParameter("bookingTime");//get the booking time
		verifyCode=this.createSecurityCode();//get verify Code
		htmlString+=
		"<html>"+
			"<head>"+

				"<title>Booking a Seat</title>"+
				"<script type='text/javascript'>"+
					//javascript validate security Code
					"function validateCode() {"+
						
						"var inputCode=document.forms['information']['inputSecurityCode'].value.toUpperCase();"+ //get the input 
						"var verifyCode='"+verifyCode+"';"+ //set the verify Code
						"if (verifyCode != inputCode){"+ //Compared
							"alert('Security Code: '+inputCode+' not match!');"+ //warning messages
							"return false;"+ //stop transfer data
							"}"+
						"}"+
				
				"</script>"+
			"</head>"+
			"<body>"+
			
				"<form name='information' action='#' onsubmit='return validateCode()' method='post'>"+
					"<h2>"+"The seat Number: "+seatNumber+"</h2>"+
					"<h2>"+"Booking time: "+bookingTime+"</h2>"+
					"<h3>"+"Please complete the booking information "+"</h3>"+
					"<h3><a href='SixtyFourSeatsTheatre'>"+"Back to choose other seat"+"</a></h3>"+
					"<br />"+
				//this is for Development
					"<input type='hidden' name='seatNumber' value='"+seatNumber+"' />"+
					"<input type='hidden' name='bookingTime' value='"+bookingTime+"' />"+
					"UserID: <input type='text' name='userID' /><br />"+
					"Phone: <input type='text' name='phone' /><br />"+
					"Address: <input type='text' name='address' /><br />"+
					"Email: <input type='text' name='email' /><br />"+
					"Security code: <input type='text' name='inputSecurityCode' value='"+verifyCode+"' />"+
					verifyCode+"<br />"+ //Security code
				//this is for Release
					
					// "UserID: <input type='text' name='userID' /><br />"+
					// "Phone: <input type='text' name='Phone' /><br />"+
					// "Address: <input type='text' name='Address' /><br />"+
					// "Email: <input type='text' name='Email' /><br />"+
					// "Security code: <input type='text' name='inputSecurityCode' />"+
					// verifyCode+"<br />"+ //Security code
					
					"<input type='submit' value='Submit' /> "+
					"<input type='reset' value='clear' />"+
				"</form>"+
			"</body>"+
		"</html>";
		getAllList();
		try{
			outHTML.println(htmlString);
		}finally{
			outHTML.close(); //always close the output writer
		}

	} 



    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//get all information data
		

		userID=request.getParameter("userID");
		phone=request.getParameter("phone");
		address=request.getParameter("address");
		email=request.getParameter("email");
		securityCode=request.getParameter("inputSecurityCode");

		//get users and seats list from data file

		if (seatsList!=null){
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]!=null && seatsList[i].getSeatsNumber().equals(seatNumber)){
					System.out.println("The seat already booked!");
					return;
				}
			}	
		}
		if (usersList!=null){
			boolean notFindID=true;
			for (int i=0;i<usersList.length;i++){
				if(usersList[i]!=null && usersList[i].getUserID().equals(userID)){
					notFindID=false;
					if (usersList[i].addSeat(seatNumber)){
						//updata information
						usersList[i].updateInfo(phone,address,email);
						System.out.println("=="+userID+"====done");						
					}else{
						System.out.println("=="+userID+"====seat 3 full!!");
						System.out.println("=="+usersList[i].toString()+"==");

						
						
						return;
					}
					
				}
				
			}
			if(notFindID){
				// new a user object
				User newUser=new User();

				// user information
				newUser.setUserID(userID);
				newUser.setPhone(phone);
				newUser.setAddress(address);
				newUser.setEmail(email);
				newUser.setSecurityCode(securityCode);
				newUser.addSeat(seatNumber);
				addNewUser(newUser);
			}
			
		}else{
			// new a user object
			User newUser=new User();

			// user information
			newUser.setUserID(userID);
			newUser.setPhone(phone);
			newUser.setAddress(address);
			newUser.setEmail(email);
			newUser.setSecurityCode(securityCode);
			newUser.addSeat(seatNumber);
			addNewUser(newUser);
		}
			
	

		
		// new a seat object
		Seat newSeat=new Seat();			

		// seat information
		newSeat.setBookingTime(bookingTime);
		newSeat.setSeatNumber(seatNumber);		
		newSeat.setUserID(userID);


		addNewSeat(newSeat);
		saveSeatInformation();
		saveUserInformation();


		//check booking Seat number
		//todo...
		
		//save information to file
		//befor save information, need check is the userID already hava


		




		//return message develop used!!!!!
		//Client part message
		//PrintWriter out = response.getWriter();
		//out.println(seatNumber+":"+userID+"; "+phone+"; "+address+"; "+email+"; "+securityCode);
		//sever part message
		System.out.println(seatNumber+":"+userID+"; "+phone+"; "+address+"; "+email+"; "+securityCode);
		System.out.println("============DONE==============");

		response.sendRedirect("SixtyFourSeatsTheatre");		


	}
	public void getAllList(){
		try{
			FileInputStream inputFile = new FileInputStream(addressSeatsData);
			ObjectInputStream inputData = new ObjectInputStream(inputFile);
			
			seatsList = (Seat[]) inputData.readObject();
			inputData.close();
			inputFile.close();
		}catch(IOException i){
			System.out.println(" file not found");
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println(" class not found");
			c.printStackTrace();
			return;
		}
		
		try{
			FileInputStream usersFile = new FileInputStream(addressUesrsData);
			ObjectInputStream usersData = new ObjectInputStream(usersFile);
			usersList = (User[])usersData.readObject();
			usersData.close();
			usersFile.close();
		}catch(IOException i){
			try{
				
				FileOutputStream usersDataFileOut = new FileOutputStream(addressUesrsData);
				ObjectOutputStream usersDataOut = new ObjectOutputStream(usersDataFileOut);
				usersDataOut.writeObject(usersList);
				usersDataOut.close();
				usersDataFileOut.close();
				
				//System.out.printf("seat information data is saved");
				
			}catch(IOException outE){
				outE.printStackTrace();

			}
			//i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println(" class not found");
			c.printStackTrace();
			return;
		}
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNumber());
	}
	public void addNewSeat(Seat newSeat){
		// try{
			// FileInputStream inputFile = new FileInputStream(addressSeatsData);
			// ObjectInputStream inputData = new ObjectInputStream(inputFile);
			
			// seatsList = (Seat[]) inputData.readObject();
			// inputData.close();
			// inputFile.close();
		// }catch(IOException i){
			// System.out.println(" file not found");
			// i.printStackTrace();
			// return;
		// }catch(ClassNotFoundException c){
			// System.out.println(" class not found");
			// c.printStackTrace();
			// return;
		// }
		

		
		
		if (seatsList != null){
	
			
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]==null){
					seatsList[i]=newSeat;
					System.out.println(" newSeat: ["+i+"]");
					break;
				}
			}
		}else{
			
			seatsList=new Seat[64];
			seatsList[0]=newSeat;
		}
		
	}

	public void addNewUser(User newUser){
		// try{
			// FileInputStream inputFile = new FileInputStream(addressUesrsData);
			// ObjectInputStream inputData = new ObjectInputStream(inputFile);
			// usersList = (User[]) inputData.readObject();
			// inputData.close();
			// inputFile.close();
		// }catch(IOException i){
			// System.out.println(" file not found");
			// i.printStackTrace();
			// return;
		// }catch(ClassNotFoundException c){
			// System.out.println(" class not found");
			// c.printStackTrace();
			// return;
		// }
		if (usersList != null){
			for (int i=1;i<usersList.length;i++){
				if (usersList[i]==null){
					usersList[i]=newUser;
					System.out.println(" newUser: ["+i+"]");
					break;
				}
			}
		}else{
			
			usersList=new User[64];
			usersList[0]=newUser;
		}
	}
	
	
	//data Serialization
	public void saveSeatInformation(){
		
		try{
			
			FileOutputStream seatsDataFileOut = new FileOutputStream(addressSeatsData);
			ObjectOutputStream seatsDataOut = new ObjectOutputStream(seatsDataFileOut);
			seatsDataOut.writeObject(seatsList);
			seatsDataOut.close();
			seatsDataFileOut.close();
			
			System.out.printf("seat information data is saved");
			
		}catch(IOException i){
			i.printStackTrace();
		}

		
	}	
	//data Serialization
	public void saveUserInformation(){
		try{

			FileOutputStream usersDataFileOut = new FileOutputStream(addressUesrsData);
			ObjectOutputStream usersDataOut = new ObjectOutputStream(usersDataFileOut);
			usersDataOut.writeObject(usersList);
			usersDataOut.close();
			usersDataFileOut.close();
			
			System.out.printf("user information data is saved");
			
		}catch(IOException i){
			i.printStackTrace();
		}

		
	}	
	
	//create the Security Code
	public String createSecurityCode(){
		int codeLength=6;
		String SecurityCode="";
		//Set the source character
		String[] sourceCode = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for (int i=0; i<codeLength;i++){
			
			int rNum=new Random().nextInt(sourceCode.length); //Randomly get a character in sourceCode array
			SecurityCode+=sourceCode[rNum]; //link the random characters
		}
		return SecurityCode; // return Security Code
		
	}
	

}