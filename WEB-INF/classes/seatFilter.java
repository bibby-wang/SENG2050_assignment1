import java.io.*;
import javax.servlet.*;

public class seatFilter implements Filter {
	private Seat[] seatsList;
	private String seatNumber;
	private String addressSeatsData="../webapps/c3214157_assignment1/WEB-INF/data/seatsData.ser";
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

	//get booked seat number taht in seats[] 
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
	
	//get seatlist if not find file create a new one
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
				System.out.println("File not found-seatFilter");
				outE.printStackTrace();
			}
			//i.printStackTrace();
			return;
		}catch(ClassNotFoundException c){
			System.out.println("Class not found-seatFilter");
			c.printStackTrace();
			return;
		}
		  
		  //System.out.println("get a seatNum: " + seatsList[0].getSeatsNumber());
	}
	
}