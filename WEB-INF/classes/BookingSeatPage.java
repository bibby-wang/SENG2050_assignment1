import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Random;

@WebServlet(urlPatterns = {"/BookingSeatPage"})
public class BookingSeatPage extends HttpServlet {


	
	
	
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter outHTML = response.getWriter();
		String htmlString="<!DOCTYPE HTML>";
		String seatNumber=request.getParameter("seatNumber");//get the selected seat Number
		String bookingTime=request.getParameter("bookingTime");//get the booking time
		public String verifyCode=this.createSecurityCode();//get verify Code
		htmlString+=
		"<html>"+
			"<head>"+

				"<title>Booking a Seat</title>"+
				"<script type='text/javascript'>"+
					//javascript validate security Code
					"function validateCode() {"+
						
						"var inputCode=document.forms['information']['inputSecurityCode'].value.toUpperCase();"+ //get the input 
						"var verifyCode='"+verifyCode+"';"+ //set the verify Code
						"if (verifyCode != inputCode){"+ //Compared
							"alert('Security Code: '+inputCode+' not match!');"+ //warning messages
							"return false;"+ //stop transfer data
							"}"+
						"}"+
				
				"</script>"+
			"</head>"+
			"<body>"+
			
				"<form name='information' action='SaveInformation' onsubmit='return validateCode()' method='GET'>"+
					"<h2>"+"The seat Number: "+seatNumber+"</h2>"+
					"<h2>"+"Booking time: "+bookingTime+"</h2>"+
					"<h3>"+"Please complete the booking information "+"</h3>"+
					"<h3><a href='SixtyFourSeatsTheatre'>"+"Back to choose other seat"+"</a></h3>"+
					"<br />"+
				//this is for Development
					"<input type='hidden' name='seatNumber' value='"+seatNumber+"' />"+
					"<input type='hidden' name='bookingTime' value='"+bookingTime+"' />"+
					"UserID: <input type='text' name='userID' /><br />"+
					"Phone: <input type='text' name='phone' /><br />"+
					"Address: <input type='text' name='address' /><br />"+
					"Email: <input type='text' name='email' /><br />"+
					"Security code: <input type='text' name='inputSecurityCode' value='"+verifyCode+"' />"+
					verifyCode+"<br />"+ //Security code
				//this is for Release
					
					// "UserID: <input type='text' name='userID' /><br />"+
					// "Phone: <input type='text' name='Phone' /><br />"+
					// "Address: <input type='text' name='Address' /><br />"+
					// "Email: <input type='text' name='Email' /><br />"+
					// "Security code: <input type='text' name='inputSecurityCode' />"+
					// verifyCode+"<br />"+ //Security code
					
					"<input type='submit' value='Submit' /> "+
					"<input type='reset' value='clear' />"+
				"</form>"+
			"</body>"+
		"</html>";
		try{
			outHTML.println(htmlString);
		}finally{
			outHTML.close(); //always close the output writer
		}

	} 






	
	//create the Security Code
	public String createSecurityCode(){
		int codeLength=6;
		String SecurityCode="";
		//Set the source character
		String[] sourceCode = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for (int i=0; i<codeLength;i++){
			
			int rNum=new Random().nextInt(sourceCode.length); //Randomly get a character in sourceCode array
			SecurityCode+=sourceCode[rNum]; //link the random characters
		}
		return SecurityCode; // return Security Code
		
	}
	

}