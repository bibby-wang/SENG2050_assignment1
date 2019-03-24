import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Date;
import java.text.SimpleDateFormat;

@WebServlet(urlPatterns = {"/SixtyFourSeatsTheatre"})
public class SixtyFourSeatsTheatre extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String htmlString;
		Date currentDate = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat ("d.MM.Y H:m:s");
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
		"<h2>"+"online seats booking system"+"</h3>"+
		
		"<h4>"+"Current time: "+formatDate.format(currentDate)+"</h4>"+
		"<table border='1'>";

		
		for(int i=65; i<72;i++){
			htmlString+="<tr/>";
			for (int j=1;j<=8;j++){
				char seat;
				seat = (char)i;
				//
				htmlString+=("<td class='"+this.isBooked(j)+"'>"+
								"<h3>"+
									"<a href='BookingSeat?seatNumber="+seat+j+"'>"+
										seat+""+j+
									"</a>"+
								"</h3>"+
								"</td>");
			}
			htmlString+="<tr/>";
		}
		htmlString+="</table></body></html>";
		try{
			out.println(htmlString);
		}finally{
			
			out.close(); //always close the output writer
		}

	}
	public String isBooked(int num){
		if ((num%2)<1){return "Booked";}
		return "unBooked";
	}
}