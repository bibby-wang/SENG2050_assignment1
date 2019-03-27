//check is input emputy
function validateEmpty(inputVar){

	if (inputVar==null||inputVar==""){
		return true;
	}else{
		return false;
	}
}

//check emal
function validateEmail(){

	var email=document.forms["information"]["email"].value; //get the userID 
	var symbolAt=email.indexOf("@");//find @ symbol
	
	if (validateEmpty(email)){
		alert("Email are required");//warning messages
		return false;
	}else if (symbolAt<1){
		alert("Email address must have @ in it");//warning messages
  		return false;
	}else{
		return true;
	}
}
//check userID
function validateUserID(){
	var userID=document.forms["information"]["userID"].value; //get the userID 
	var findNumber=userID.match(/\d+/g);
	if (validateEmpty(userID)){
		alert("UserID are required");//warning messages
		return false;
	}else if (findNumber != null){
		alert("UserID can not have numbers!");//warning messages
		return false;
	}else{
		return true;
	}

}
//check Security Code
function validateSecurityCode(verifyCode){
	var inputCode=document.forms["information"]["inputSecurityCode"].value.toUpperCase(); //get the userID 
	if (verifyCode != inputCode){ //Compared
		alert("Security Code: "+verifyCode+" not match!"); //warning messages
		return false; //stop transfer data
		
	}else{
		return true; //stop transfer data
	}

}

//check input
function validateInput(sCode){
	if(validateUserID() && validateEmail() && validateSecurityCode(sCode)){
		return true;
	}else{

		return false;
	}
}



