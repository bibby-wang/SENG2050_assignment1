
function validateEmpty(inputVar){

	if (inputVar==null||inputVar==""){
		return true;
	}else{
		return false;
	}
}


function validateEmail(){
	

	var email=document.forms["information"]["email"].value; //get the userID 
	var symbolAt=email.indexOf("@");
	
	if (validateEmpty(email)){
		alert("Email are required");
		return false;
	}else if (symbolAt<1){
		alert("Email address must have @ in it");
  		return false;
	}else{
		return true;
	}
}

function validateUserID(){
	var userID=document.forms["information"]["userID"].value; //get the userID 
	var findNumber=userID.match(/\d+/g);
	if (validateEmpty(userID)){
		alert("UserID are required");
		return false;
	}else if (findNumber != null){
		alert("UserID can not have numbers!");
		return false;
	}else{
		return true;
	}

}

function validateInput(){
	if(validateUserID() && validateEmail()){
		return true;
	}else{

		return false;
	}
}


