import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.Random;

@WebServlet(urlPatterns = {"/BookingSeat"})
public class BookingSeat extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String htmlString;
		String verifyCode=this.createSecurityCode();
		htmlString="<!DOCTYPE HTML>"+
		"<html>"+
			"<head>"+
			
		
				// "<style type='text/css'>"+
					// "td {background-color: gray}"+
					// "td.Booked {background-color: gray}"+
					// "td.unBooked {background-color: green}"+
				// "</style>"+
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
					"<h2>"+"The seat Number: "+request.getParameter("seatNumber")+"</h2>"+//get the selected seat Number
					"<h3>"+"Please complete the booking information "+"</h3>"+

					"<br />"+
				//this is for Development
					"UserID: <input type='text' name='UserID' value='ABC' /><br />"+
					"Phone: <input type='text' name='Phone' value='0123' /><br />"+
					"Address: <input type='text' name='Address' value='ABC' /><br />"+
					"Email: <input type='text' name='Email' value='ABC@ABC.AC' /><br />"+
					"Security code: <input type='text' name='inputSecurityCode' value='"+verifyCode+"' />"+
					verifyCode+"<br />"+ //Security code
				//this is for Release
					
					// "UserID: <input type='text' name='UserID' /><br />"+
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
		out.println(htmlString);


	} 
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
	public String isBooked(int num){
		if ((num%2)<1){return "Booked";}
		return "unBooked";
	}
}