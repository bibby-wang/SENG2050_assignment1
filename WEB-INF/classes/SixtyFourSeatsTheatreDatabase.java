import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = {"/SixtyFourSeatsTheatreDatabase"})
public class SixtyFourSeatsTheatreDatabase extends HttpServlet {
	private Seat[] seatsList;
	private User[] usersList;
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
	private String addressUesrsData="../webapps/c3214157_assignment1/WEB-INF/data/usersData.ser";		
	private String htmlString;	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter outHTML = response.getWriter();


		//FORMAT HTML 
		htmlString = "<!DOCTYPE HTML> <html>"+
		"<head>"+
			"<title>database</title>"+
		"</head>"+
		"<body>"+
		"<h2>"+"Sixty Four Seats Theatre"+"</h2>"+
		"<h2>"+"online seats booking system"+"</h2>";



		this.getSeatList();
		this.getUsersList();
		htmlString+="<p/>===seats file data====<p/>";
		for (int i=0;i<64;i++){
			if(seatsList[i]!=null){
				htmlString+=seatsList[i].toString();
				htmlString+="<br/>";
			}
		}
		htmlString+="<p/>======================================<p/>";
		htmlString+="<p/>===users flie data====<p/>";
		for (int i=0;i<64;i++){
			if(usersList[i]!=null){
				htmlString+=usersList[i].toString();
				htmlString+="<br/>";
			}
		}	
			
		
		htmlString+="</body></html>";
		try{
			outHTML.println(htmlString);
		}finally{
			outHTML.close(); //always close the output writer
		}

	}
	
	public void getSeatList(){
		
		try{
			FileInputStream seatsFile = new FileInputStream(addressSeatsData);
			ObjectInputStream seatData = new ObjectInputStream(seatsFile);
			seatsList = (Seat[]) seatData.readObject();
			seatData.close();
			seatsFile.close();
		}catch(IOException i){
			System.out.println(" file not found");
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println(" class not found");
			c.printStackTrace();
			return;
		}
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNumber());
	}
	
	public void getUsersList(){
		
		try{
			FileInputStream usersFile = new FileInputStream(addressUesrsData);
			ObjectInputStream usersData = new ObjectInputStream(usersFile);
			usersList = (User[])usersData.readObject();
			usersData.close();
			usersFile.close();
		}catch(IOException i){
			System.out.println(" file not found");
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println(" class not found");
			c.printStackTrace();
			return;
		}
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNumber());
	}		

}