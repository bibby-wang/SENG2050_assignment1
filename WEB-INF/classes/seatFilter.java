import java.io.*;
import javax.servlet.*;

public class seatFilter implements Filter {
	private Seat[] seatsList;
	private String seatNumber;

	public void doFilter(ServletRequest request, ServletResponse response,
	FilterChain chain) throws IOException, ServletException {
		getSeatList();
		String seatNumber=request.getParameter("seatNumber");//get the selected seat Number
		int seatArryNum=this.getBookedNumber(seatNumber);
		System.out.println("run filter!!!");
		if(seatArryNum<0){
			

			chain.doFilter(request, response);			
		}else{
			response.setContentType("text/html");
			PrintWriter outHTML = response.getWriter();			
			String htmlString="<!DOCTYPE HTML>"+
			"<html>"+
				"<head>"+
					"<title>Message of booking erro</title>"+
				"</head>"+
				"<body>"+
					"<h3>this seat already booked by:</h3>"+
					"<br/>UserID: "+seatsList[seatArryNum].getUserID()+
					"<br/>Time: "+seatsList[seatArryNum].getBookingTime()+
					"<h3><a href='SixtyFourSeatsTheatre'>"+"Back to choose other seat"+"</a></h3>"+			
				"</body>"+
			"</html>";
			
			try{
				outHTML.println(htmlString);
			}finally{
				outHTML.close(); //always close the output writer
			}
		}
	}

	
	public int getBookedNumber(String seatNum){
		//System.out.println("seatN: "+seatNum+" .");
		if (seatsList != null){
			for (int i=0;i<seatsList.length;i++){
				if (seatsList[i]!=null){
					if (seatNum.equals(seatsList[i].getSeatsNumber())){
						//System.out.println("find a booked: "+seatNum+" .");
						return i;
					}
				}
			}
			
		}
		return -1;
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
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNumber());
	}
	
}