import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = {"/SixtyFourSeatsTheatre"})
public class SixtyFourSeatsTheatre extends HttpServlet {
	private Seat[] seatsList;
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter outHTML = response.getWriter();
		String htmlString;
		Date currentDate = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat ("dd-MM-YY HH-mm-ss");
		String bookingTime=formatDate.format(currentDate);
		//FORMAT HTML 
		htmlString="<!DOCTYPE html>\n <html>\n"+
		"<head>\n"+
		"<meta charset='utf-8'>\n"+
			"<style type='text/css'>\n"+
				"table {border: 1px}\n"+
				"td {background-color: gray}\n"+
				"td.Booked {background-color: gray}\n"+
				"td.unBooked {background-color: green}\n"+
			"</style>\n"+
			"<title>Sixty Four Seats</title>\n"+
		"</head>\n"+
		"<body>\n"+
		"<h2>"+"Sixty Four Seats Theatre"+"</h2>\n"+
		"<h2>"+"online seats booking system"+"</h2>\n"+
		"<h4>"+"Current time: "+bookingTime+"</h4>\n"+
		"<h4>"+"please choose a seat"+"</h4>\n"+
		"<table>\n";

		this.getSeatList();
		for(int i=65; i<72;i++){
			htmlString+="<tr>\n";
			for (int j=1;j<=8;j++){
				char seat;
				seat = (char)i;
				//output all seats 8*8 matrix 
				String tempSeatNum=seat+""+j;
				htmlString+=("<td class='"+this.isBooked(tempSeatNum)+"'>\n"+
								"<h3>"+
									"<a href='BookingSeatPage?seatNumber="+tempSeatNum+
									"&bookingTime="+bookingTime+"'>"+tempSeatNum+
									"</a>"+
								"</h3>\n"+
								"</td>\n");
			}
			htmlString+="</tr>\n";
		}
		htmlString+="</table>\n</body>\n</html>\n";
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
			try{
				
				FileOutputStream seatsDataFileOut = new FileOutputStream(addressSeatsData);
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
		}catch(ClassNotFoundException classE){
			System.out.println(" class not found");
			classE.printStackTrace();
			return;
		}
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNumber());
	}
		
	public String isBooked(String seatNum){
		//System.out.println("seatN: "+seatNum+" .");
		if (seatsList != null){
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]!=null){
					if (seatNum.equals(seatsList[i].getSeatsNumber())){
						//System.out.println("find a booked: "+seatNum+" .");
						return "Booked";
					}
				}
			}
		}
		return "unBooked";
	}
}