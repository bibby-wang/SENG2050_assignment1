University of Newcastle
School of Electrical Engineering and Computer Science
SENG2050 Web Engineering
Assignment 1 ONLINE SEATS BOOKING SYSTEM
Sixty Four Seats Theatre 
Author: Binbin Wang
Student No: 3214157
Due Date: 31-03-2019

readme.txt
=========file address:c3214157_assignment1\WEB-INF\
web.xml:
Configuration file

========file address:c3214157_assignment1\WEB-INF\classes\
SixtyFourSeatsTheatre.java:
generate main html web page
main method:
-doGet() create main html web page, automatically generate 8*8 seats matrix
support method:
-getSeatList() get seat list from data file. if not have file, create a new one.
-isBooked() Determine whether to book use for html code in CSS part

==
seatFilter.java:
effect: Unavailable seat Filters 
main method:
-doFilter(); if seat unavailable, return a warning web page. otherwise goto Booking Seat Page
support method:
-getSeatList(); getBookedNumber();
  
==
BookingSeatPage.java:
generate Booking html page and save information to file
main method:
-doGet() get information from main page eg. bookingTime seatNumber
-doPost() post information to sever, 
support method:
-getAllList() get users and seats list
-addNewSeat() add a new seat to lsit
-addNewUser() add a new user to list
-saveSeatInformation()  seat data Serialization save to file seatsData.ser
-createSecurityCode() Generate security code

==
User.java:
User object

Main attributes: userID; phone; address; email; threeSeats	
method:
-getUserID(); getPhone(); getAddress(); getEmail(); getSeats(); get data
-setUserID(); setPhone(); setAddress(); setEmail(); setSeats(); set data
-updateInfo(); update user's information
-addSeat(); add a new booking seat. can not more than 3
-getEmptySeatsNum(); support method
-toString(); format data output

==
Seat.java:
Seat object

Main attributes: 
userID; bookingTime; seatNumber;

method:
-getUserID(); getSeatsNumber(); getBookingTime(); get data
-setUserID(); setSeatsNumber(); setBookingTime(); set data
-toString(); format data output

========File address: c3214157_assignment1\
validateInput.js
external JavaScript code use for check all input.
eg. 
- UserID of the customer (required, no numbers in it),
- Email (required, must have @ in it),
- Security code (must match the system randomly generate security code)

========Data file address: c3214157_assignment1\WEB-INF\data\
Use JAVA Serializable stores all booking data to "seatsData.ser" and "usersData.ser" file.
 seatsData.ser
  -Store a seats array object, each array element is seat object that distinguished by seatNumber.
 usersData.ser
  -Store a users array object, each array element is user object that distinguished by userID.

