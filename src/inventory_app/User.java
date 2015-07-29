package inventory_app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class User {
	
	private static final Map<String, User> USERS = new HashMap
			<String, User>();
	
	// fields
	String firstName;
	String lastName;
	String userID;
	
	// constructors
	public User() {
		this.firstName = "";
		this.lastName = "";
		this.userID = "";
	}// User()
	
	public User(String firstName, String lastName, String userID) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userID = userID;
	}// User(String, STring, String)
	
	public User(String userId) {
		this.userID = userId;
	}
	
	public static User of( 
			String id) {
		User user = USERS.get(id);
		if (user == null) {
			user = new User(id);
			USERS.put(id, user);
		}
		return user;
	}// of(String)
	
	// getters and setters
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setFirstName(String newFirstName) {
		firstName = newFirstName;
	}
	public void setLastName(String newLastName) {
		lastName = newLastName;
	}
	
	public void setUserID(String newUserID) {
		userID = newUserID;
	}
	
	@Override
	public String toString() {
		return getFirstName() + " " + getLastName() + ": " +
				getUserID();
	}
	
	public void tempUser() {
		  try {
			 String data = this.toString();
			 File file = new File("temp.txt");
			 
			 // if the file doesn't exist, then create it
			 if (!file.exists()) {
				 file.createNewFile();
			 }// if
			 
			 FileWriter fileWriter = new FileWriter(file, true);
			 BufferedWriter bw = new BufferedWriter(fileWriter);
			 
			 bw.write(data);
			 bw.flush();
			 bw.close();
			 
			 System.out.println("User added to temp.txt");
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }// tempUser()

	public String getUserToString() {
		return getLastName() + ", " + getFirstName() + ": " + getUserID();
	}
	
	
}
