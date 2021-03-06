// University of Newcastle
// School of Electrical Engineering and Computer Science
// SENG2050 Web Engineering
// Assignment 1 ONLINE SEATS BOOKING SYSTEM
// Author: Binbin Wang
// Student No: 3214157
// Due Date: 31-03-2019
// input form page
// userID; phone; address; email; SecurityCode


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Random;

@WebServlet(urlPatterns = {"/BookingSeatPage"})
public class BookingSeatPage extends HttpServlet {
	private Seat[] seatsList;
	private User[] usersList;
	private String bookingTime,seatNumber,userID, phone, address, email, securityCode, verifyCode;;
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";//address of seats data
	private String addressUesrsData="../webapps/c3214157_assignment1/WEB-INF/data/usersData.ser";//address of users data	


	// get information from main page eg. bookingTime seatNumber
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		
		seatNumber=request.getParameter("seatNumber");//get the selected seat Number
		bookingTime=request.getParameter("bookingTime");//get the booking time
		verifyCode=this.createSecurityCode();//create verify Code
		
		PrintWriter outHTML = response.getWriter();		
		// html web page code		
		String htmlString="<!DOCTYPE HTML>\n";		
 		htmlString+=
		"<html>\n"+
			"<head>\n"+
				"<meta charset='utf-8'>\n"+
				"<script src='validateInput.js' type='text/javascript' ></script>\n"+
				
				"<title>Booking a Seat</title>\n"+				
			"</head>\n"+
			"<body>\n"+
			
				"<form name='information' action='#' onsubmit=\"return validateInput('"+verifyCode+"')\" method='post'>\n"+
				
					"<h2>"+"The seat Number: "+seatNumber+"</h2>\n"+
					"<h2>"+"Booking time: "+bookingTime+"</h2>\n"+
					"<h3>"+"Please complete the booking information "+"</h3>\n"+
					"<h3><a href='SixtyFourSeatsTheatre'>"+"Back to choose other seat"+"</a></h3>\n"+
					"<br />\n"+
				// input form 

					"UserID: <input type='text' name='userID' /><br />\n"+
					"Phone: <input type='text' name='phone' /><br />\n"+
					"Address: <input type='text' name='address' /><br />\n"+
					"Email: <input type='text' name='email' /><br />\n"+
					"Security code: <input type='text' name='inputSecurityCode' />"+
					verifyCode+"<br />\n"+ //Security code
					// two buttons one for submit other for clean all input
					"<input type='submit' value='Submit' />\n"+
					" <input type='reset' value='clear' />\n"+
				"</form>\n"+
			"</body>\n"+
		"</html>";
		getAllList();
		try{
			outHTML.println(htmlString);
		}finally{
			outHTML.close(); //always close the output writer
		}

	}


	//post information to sever 
    public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		//get information data from request
		
		userID=request.getParameter("userID");
		phone=request.getParameter("phone");
		address=request.getParameter("address");
		email=request.getParameter("email");
		

		//get users and seats list from data file
		//seats list
		if (seatsList!=null){
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]!=null && seatsList[i].getSeatsNumber().equals(seatNumber)){
					System.out.println("The seat already booked!");
					return;
				}
			}	
		}
		
		//users list
		if (usersList!=null){
			
			boolean notFindID=true;
			boolean threSeatFull=false;
			for (int i=0;i<usersList.length;i++){
				if(usersList[i]!=null && usersList[i].getUserID().equals(userID)){
					notFindID=false;
					if (usersList[i].addSeat(seatNumber)){
						//updata information
						usersList[i].updateInfo(phone,address,email);
						System.out.println("=="+userID+"===update=Done==");	//message in sever part					
					}else{
						threSeatFull=true;
						
						PrintWriter outHTML = response.getWriter();
						//warning have more than 3 bookings
						String htmlString="<!DOCTYPE HTML>\n"+
						"<html>\n"+
							"<head>\n"+
								"<meta charset='utf-8'>\n"+
								"<title>Booking a Seat</title>\n"+
							"</head>\n"+
							"<body>\n"+
								"<h2>Your can not have more than 3 bookings</h2><br />\n"+
								"<h3><a href='SixtyFourSeatsTheatre'>Back to main page</a></h3>\n"+
							"</body>\n"+
						"</html>";
						
						try{
							outHTML.println(htmlString);
						}finally{
							outHTML.close(); //always close the output writer
						}
						// exit booking
						return;
					}
					
				}
				
			}
			
			if(notFindID){
				// do not have same UserID
				// new a user object
				User newUser=new User();

				// user information
				newUser.setUserID(userID);
				newUser.setPhone(phone);
				newUser.setAddress(address);
				newUser.setEmail(email);
				//newUser.setSecurityCode(securityCode);
				newUser.addSeat(seatNumber);
				addNewUser(newUser);
			}
			
		}else{
			
			//do not have any data in list
			// new a user object
			User newUser=new User();

			// user information
			newUser.setUserID(userID);
			newUser.setPhone(phone);
			newUser.setAddress(address);
			newUser.setEmail(email);
			//newUser.setSecurityCode(securityCode);
			newUser.addSeat(seatNumber);
			addNewUser(newUser);
		}
		// Reservation
		// new a seat object
		Seat newSeat=new Seat();			

		// seat information
		newSeat.setBookingTime(bookingTime);
		newSeat.setSeatNumber(seatNumber);		
		newSeat.setUserID(userID);

		//save all information to file
		addNewSeat(newSeat);

		saveSeatInformation();
		saveUserInformation();
		

		System.out.println("============DONE=============="); // message in sever part	
		//done all booking back to main page
		response.sendRedirect("SixtyFourSeatsTheatre");		


	}
	//get users and seats list
	private void getAllList(){
		//get seats to seatsList[]
		try{
			FileInputStream seatsFile = new FileInputStream(addressSeatsData);
			ObjectInputStream inputData = new ObjectInputStream(seatsFile);
			
			seatsList = (Seat[]) inputData.readObject();
			inputData.close();
			seatsFile.close();
		}catch(IOException i){
			System.out.println(" Seats File not found");// message in sever part
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println(" Class not found");
			c.printStackTrace();
			return;
		}
		
		//get users to usersList[]
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
				
			}catch(IOException outE){

				outE.printStackTrace();

			}
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println(" class not found");
			c.printStackTrace();
			return;
		}
		  

	}
	
	//add a new seat to list
	private void addNewSeat(Seat newSeat){
		//if not have seatslist yet, make a new one
		if (seatsList != null){
			
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]==null){
					seatsList[i]=newSeat;
					break;
				}
			}
		}else{
			
			seatsList=new Seat[64];
			seatsList[0]=newSeat;
		}
		
	}
	
	// add a new user to list
	private void addNewUser(User newUser){
		//if not have suerslist yet, make a new one
		if (usersList != null){
			for (int i=1;i<usersList.length;i++){
				if (usersList[i]==null){
					usersList[i]=newUser;
					break;
				}
			}
		}else{
			
			usersList=new User[64];
			usersList[0]=newUser;
		}
	}
	
	
	//seat data Serialization save to file seatsData.ser
	private void saveSeatInformation(){
		
		try{
			
			FileOutputStream seatsDataFileOut = new FileOutputStream(addressSeatsData);
			ObjectOutputStream seatsDataOut = new ObjectOutputStream(seatsDataFileOut);
			seatsDataOut.writeObject(seatsList);
			seatsDataOut.close();
			seatsDataFileOut.close();
			
			System.out.println("seat information data is saved");
			
		}catch(IOException i){
			i.printStackTrace();
		}

		
	}	
	//User data Serialization save to file usersData.ser
	private void saveUserInformation(){
		try{

			FileOutputStream usersDataFileOut = new FileOutputStream(addressUesrsData);
			ObjectOutputStream usersDataOut = new ObjectOutputStream(usersDataFileOut);
			usersDataOut.writeObject(usersList);
			usersDataOut.close();
			usersDataFileOut.close();
			
			System.out.println("user information data is saved");
			
		}catch(IOException i){
			i.printStackTrace();
		}

		
	}	
	
	//create the Security Code
	private String createSecurityCode(){
		int codeLength=6;//total code number
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