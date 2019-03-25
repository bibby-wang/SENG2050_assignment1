import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/SaveInformation"})
public class SaveInformation extends HttpServlet {
	private Seat[] seatsList;
	private User[] usersList;
	private String bookingTime,seatNumber,userID, phone, address, email,securityCode;
	
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
	private String addressUesrsData="../webapps/c3214157_assignment1/WEB-INF/data/usersData.ser";	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		//get all information data
		
		seatNumber=request.getParameter("seatNumber");
		bookingTime=request.getParameter("bookingTime");
		userID=request.getParameter("userID");
		phone=request.getParameter("phone");
		address=request.getParameter("address");
		email=request.getParameter("email");
		securityCode=request.getParameter("inputSecurityCode");
		
		
		//get users and seats list from data file

		getAllList();
		for (int i=0;i<seatsList.length;i++){
			if (seatsList[i]!=null && seatsList[i].getSeatsNumber().equals(seatNumber)){
				System.out.println("The seat already booked!");
				return;
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
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
}