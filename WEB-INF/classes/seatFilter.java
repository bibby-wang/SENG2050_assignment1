// University of Newcastle
// School of Electrical Engineering and Computer Science
// SENG2050 Web Engineering
// Assignment 1 ONLINE SEATS BOOKING SYSTEM
// Author: Binbin Wang
// Student No: 3214157
// Due Date: 31-03-2019
// Unusable seat Filters

import java.io.*;
import javax.servlet.*;

public class seatFilter implements Filter {
	private Seat[] seatsList;
	private String seatNumber;
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
	// do filter if seat unavailable, return a warning web page
	public void doFilter(ServletRequest request, ServletResponse response,
	FilterChain chain) throws IOException, ServletException {
		getSeatList();
		String seatNumber=request.getParameter("seatNumber");//get the selected seat Number
		int seatArryNum=this.getBookedNumber(seatNumber);
		//seat Filters
		if(seatArryNum<0){
			chain.doFilter(request, response);
		}else{
			//unavailable seat warning page
			response.setContentType("text/html");
			PrintWriter outHTML = response.getWriter();
			String htmlString="<!DOCTYPE HTML>\n"+
			"<html>\n"+
				"<head>\n"+
					"<meta charset='utf-8'>\n"+
					"<title>Message of booking erro</title>\n"+
				"</head>"+
				"<body>"+
					"<h3>This seat already booked by:</h3><br/>\n"+
					"<h3>UserID: "+seatsList[seatArryNum].getUserID()+"</h3>\n"+
					"<h3>Time: "+seatsList[seatArryNum].getBookingTime()+"</h3>\n"+
					"<h3><a href='SixtyFourSeatsTheatre'>"+"Back to choose other seat"+"</a></h3>\n"+			
				"</body>\n"+
			"</html>\n";
			
			try{
				outHTML.println(htmlString);
			}finally{
				outHTML.close(); //always close the output writer
			}
		}
	}

	//get booked seat number taht in seats[] array
	private int getBookedNumber(String seatNum){

		if (seatsList != null){
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]!=null){
					if (seatNum.equals(seatsList[i].getSeatsNumber())){
						return i; //return the number of seats array 
					}
				}
			}
			
		}
		return -1; //not find return -1
	}
	
	//get seatlist if not find file create a new one
	private void getSeatList(){
		
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
				

			}catch(IOException outE){

				outE.printStackTrace();
			}
			i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println("Class not found-seatFilter");
			c.printStackTrace();
			return;
		}

	}
	
}