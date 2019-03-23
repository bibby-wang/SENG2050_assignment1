import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = {"/SaveInformation"})
public class SaveInformation extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		String UserID, Phone, Address, Email,SecurityCode;
		UserID=request.getParameter("UserID");
		Phone=request.getParameter("Phone");
		Address=request.getParameter("Address");
		Email=request.getParameter("Email");
		SecurityCode=request.getParameter("inputSecurityCode");
		
		
		//check booking Seat number
		//todo...
		//save information to file
		//todo...
		PrintWriter out = response.getWriter();		
		out.println(UserID+"; "+Phone+"; "+Address+"; "+Email+"; "+SecurityCode);


	}
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
        doGet(request, response);
    }
	//check if current userID reservation seat number is exceeded
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
}