import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = {"/SixtyFourSeatsTheatre"})
public class SixtyFourSeatsTheatre extends HttpServlet {
	private Seat[] seatsList;
	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter outHTML = response.getWriter();
		String htmlString;
		Date currentDate = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat ("dd.MM.YY HH:mm:ss");
		String bookingTime=formatDate.format(currentDate);
		//FORMAT HTML 
		htmlString="<!DOCTYPE HTML> <html>"+
		"<head>"+
			"<style type='text/css'>"+
				"td {background-color: gray}"+
				"td.Booked {background-color: gray}"+
				"td.unBooked {background-color: green}"+
			"</style>"+
			"<title>Sixty Four Seats</title>"+
		"</head>"+
		"<body>"+
		"<h2>"+"Sixty Four Seats Theatre"+"</h2>"+
		"<h2>"+"online seats booking system"+"</h2>"+
		"<h4>"+"Current time: "+bookingTime+"</h4>"+
		"<h4>"+"please choose a seat"+"</h4>"+
		"<table border='1'>";

		this.getSeatList();
		for(int i=65; i<72;i++){
			htmlString+="<tr/>";
			for (int j=1;j<=8;j++){
				char seat;
				seat = (char)i;
				//
				String tempSeatNum=seat+""+j;
				htmlString+=("<td class='"+this.isBooked(tempSeatNum)+"'>"+
								"<h3>"+
									"<a href='BookingSeat?seatNumber="+tempSeatNum+"'bookingTime='"+bookingTime+"'>"+
										seat+""+j+
									"</a>"+
								"</h3>"+
								"</td>");
			}
			htmlString+="<tr/>";
		}
		htmlString+="</table></body></html>";
		try{
			outHTML.println(htmlString);
		}finally{
			outHTML.close(); //always close the output writer
		}

	}
	
	public void getSeatList(){
		
		try{
			FileInputStream seatsFile = new FileInputStream("../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser");
			ObjectInputStream seatData = new ObjectInputStream(seatsFile);
			seatsList = (Seat[]) seatData.readObject();
			seatData.close();
			seatsFile.close();
		}catch(IOException i){
			try{
				
				FileOutputStream seatsDataFileOut = new FileOutputStream("../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser");
				ObjectOutputStream seatsDataOut = new ObjectOutputStream(seatsDataFileOut);
				seatsDataOut.writeObject(seatsList);
				seatsDataOut.close();
				seatsDataFileOut.close();
				
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
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNum());
	}
		
	public String isBooked(String seatNum){
		//System.out.println("seatN: "+seatNum+" .");
		if (seatsList != null){
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]!=null){
					if (seatNum.equals(seatsList[i].getSeatsNum())){
						//System.out.println("find a booked: "+seatNum+" .");
						return "Booked";
						}
				}
			}
		}
		return "unBooked";
	}
}