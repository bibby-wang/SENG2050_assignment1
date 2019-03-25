import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/SaveInformation"})
public class SaveInformation extends HttpServlet {
	private Seat[] seatsList;
	private User[] usersList;

	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
	private String addressUesrsData="../webapps/c3214157_assignment1/WEB-INF/data/usersData.ser";	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//get all information data
		String bookingTime,seatNumber,userID, phone, address, email,securityCode;
		seatNumber=request.getParameter("seatNumber");
		bookingTime=request.getParameter("bookingTime");
		userID=request.getParameter("userID");
		phone=request.getParameter("phone");
		address=request.getParameter("address");
		email=request.getParameter("email");
		securityCode=request.getParameter("inputSecurityCode");
		
		// new a seat object
		Seat newSeat=new Seat();		
		// new a user object
		User newUser=new User();

		// seat information
		newSeat.setBookingTime(bookingTime);
		newSeat.setSeatNumber(seatNumber);		
		newSeat.setUserID(userID);

		// user information
		newUser.setUserID(userID);
		newUser.setPhone(phone);
		newUser.setAddress(address);
		newUser.setEmail(email);
		newUser.setSecurityCode(securityCode);
		newUser.addSeat(seatNumber);
;
		

		//check booking Seat number
		//todo...
		
		//save information to file
		//befor save information, need check is the userID already hava
		addNewSeat(newSeat);
		saveSeatInformation();
		
		addNewUser(newUser);
		saveUserInformation();

		//return message develop used!!!!!
		//Client part message
		//PrintWriter out = response.getWriter();
		//out.println(seatNumber+":"+userID+"; "+phone+"; "+address+"; "+email+"; "+securityCode);
		//sever part message
		System.out.println(seatNumber+":"+userID+"; "+phone+"; "+address+"; "+email+"; "+securityCode);
		

		response.sendRedirect("SixtyFourSeatsTheatre");


	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
        doGet(request, response);
    }
	//check if current userID reservation seat number is exceeded
/*
	public boolean isBookedThreeSeats(String currentID){
		int totalSeats=0;
		while(true){//search data todo...
			//get userID from database
			//todo ...
			//Compar current ID and count
			if (currentID==""){
				totalSeats++;
			}
		}
		//if over booking return false
		if (totalSeats<3){
			return false;	
		}else{
			return true;
		}
	}
*/


	public void addNewSeat(Seat newSeat){
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
		
		
		
		
		if (seatsList != null){
			for (int i=1;i<seatsList.length;i++){
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
		try{
			FileInputStream inputFile = new FileInputStream(addressUsersData);
			ObjectInputStream inputData = new ObjectInputStream(inputFile);
			usersList = (Seat[]) inputData.readObject();
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
			usersDataOut.writeObject(user);
			usersDataOut.close();
			usersDataFileOut.close();
			
			System.out.printf("user information data is saved");
			
		}catch(IOException i){
			i.printStackTrace();
		}

		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
}