
package inventory_app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CheckOut {
	
	private static final Map<String, CheckOut> CHECKOUT = new HashMap
			<String, CheckOut>();
	
	// fields
	User user;
	Devices device;
	Time time; // converted from Timestamp time
	
	String users;
	String devices;
	String times;
	
	
	// constructors
	/* Used to create a temporary checkout between CheckOutDialogue and CheckOutDialoguePart2 */
	public CheckOut(Devices newDevice) {
		user = null;
		device = newDevice;
		time = null;
	}
	
	public CheckOut(String users, String devices, String times) {
		this.users = users;
		this.devices = devices;
		this.times = times;
	}
	
	public CheckOut(User newUser, Devices newDevice, Time newTime) {
		this.user = newUser;
		this.device = newDevice;
		this.time = newTime;
	}// CheckOut
	
	public String getUsers() {
		return users;
	}// getUsers()
	
	public String getDevices() {
		return devices;
	}// getDevices()
	
	public String getTimes() {
		return times;
	}// getTimes()
	
	public User getUser() {
		return user;
	}// getUser()
	
	public Devices getDevice() {
		return device;
	}// getDevice()
	
	public Time getTime() {
		return time;
	}// getTime()
	
	// create a user right here if you want
	public void setUser(String firstName, String lastName, String userId) {
		user.setFirstName(firstName);
		user.setLastName(firstName);
		user.setUserID(userId);
	}// setUser(String, String, String)
	
	public void setUser(User newUser) {
		user = newUser;
	}// setUser(User)
	
	// create a device right here if you want
	public void setDevice(String brand, String desktop_laptop, 
			String make, String type, String serial_no, String status) {
		device.setBrand(brand);
		device.setDesktopLaptop(desktop_laptop);
		device.setMake(make);
		device.setType(type);
		device.setSerial(serial_no);
		device.setStatus(status);
	}// setDevice(String, String, String, String, String)
	
	public void setDevice(Devices newDevice) {
		device = newDevice;
	}// setDevice(Devices)
	
	public void setTime(Time newtime) {
		time = newtime;
	}// setTime(Time)

	public void setUsers(String string) {
		users = string;
	}// setUsers(String)
	
	public void setDevices(String string) {
		devices = string;
	}// setDevices(String)
	
	public void setTimes(String string) {
		times = string;
	}// getTimes(String)
 	
	public static Map<String, CheckOut> getCheckout() {
		return CHECKOUT;
	}// getCheckout()
	
	public String getUserToString() {
		return "\n" + user.getLastName() + ", " + 
				user.getFirstName() + ": " +
				user.getUserID();
	}// getUserToString()
	
	  public String getDevicegetDevice() throws IOException {
		  Devices newb = getDevice();
		  System.out.println(newb.getSerial());
		  return newb.getBrand() + " " + newb.getType() + " (Serial no. " + newb.getSerial() + ")";  
	  }// getDevicegetDevice
	
	public String getTimeToString() {
		return time.getMonth() + "/" + time.getDay() + "/" +
				time.getYear() + " " + time.getHours() + ":" +
				time.getMinutes();
	}// getTimeToString()
	
	public String CheckOutToString() throws IOException {
		return getUserToString() + "|" +  getDevicegetDevice() + "|" + 
				getTimeToString();
	}// CheckOutToString()
	
	public void writeCheckOut() {
			  try {
				 String data = CheckOutToString();
				 File file = new File("Checkout.txt");
				 
				 // if the file doesn't exist, then create it
				 if (!file.exists()) {
					 file.createNewFile();
				 }// if
				 
				 FileWriter fileWriter = new FileWriter(file, true);
				 BufferedWriter bw = new BufferedWriter(fileWriter);
				 
				 bw.write(data);
				 bw.flush();
				 bw.close();
				 
				 System.out.println("Entry added to Checkout.txt");
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }// tempDevice()
	
	public boolean checkoutMatch(CheckOut two) {
		if (users.equals(two.users) && devices.equals(two.devices)
				&& times.equals(two.times)) {
			return true;
		}// if 
		else return false;
	}// checkOutMatch(Checkout)
	
	public void findAndRemoveCheckout() throws IOException {
		ArrayList<CheckOut> checkoutList = new ArrayList<CheckOut>();
		String line = "";
		String splitBy ="|";
		File file = new File("Checkout.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while ((line = br.readLine()) != null) {
			String[] object = line.split(splitBy);
			String user = object[0];
			String device = object[1];
			String datetime = object[2];
			

			CheckOut newCheckOut = new CheckOut(user, device, datetime);
			checkoutList.add(newCheckOut);
		}// while 
		for (int row = 0; row < checkoutList.size(); row++) {
			if (this.checkoutMatch(checkoutList.get(row))) {
				checkoutList.remove(checkoutList.get(row));
			}// if
		}// for
		
		br.close();
		
		// NOW REWRITE THE CHECKOUTS FILE
		
		// NOW REWRITE THE INVENTORY FILE TO UPDATE DEVICE TO "CHECKED IN"
	}// findAndRemoveCheckout()
	
}// class CheckOut

/**
package inventory_app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CheckOut {
	
	private static final Map<String, CheckOut> CHECKOUT = new HashMap
			<String, CheckOut>();
	
	// fields
	User user;
	Devices device;
	Time time; // converted from Timestamp time
	
	CheckOut instance;
	
	String users;
	String devices;
	String times;
	
	
	// constructors
	
	public CheckOut() {
		instance = this;
	}// CheckOut()
	
	/* Used to create a temporary checkout between CheckOutDialogue and CheckOutDialoguePart2 */
/*
	public CheckOut(Devices newDevice) {
		user = null;
		device = newDevice;
		time = null;
		instance = this;
	}
	
	public CheckOut(String users, String devices, String times) {
		this.users = users;
		this.devices = devices;
		this.times = times;
		instance = this;
	}
	
	public CheckOut(User newUser, Devices newDevice, Time newTime) {
		this.user = newUser;
		this.device = newDevice;
		this.time = newTime;
		instance = this;
	}// CheckOut
	
	public String getUsers() {
		return users;
	}// getUsers()
	
	public String getDevices() {
		return devices;
	}// getDevices()
	
	public String getTimes() {
		return times;
	}// getTimes()
	
	public User getUser() {
		return user;
	}// getUser()
	
	public Devices getDevice() {
		return device;
	}// getDevice()
	
	public Time getTime() {
		return time;
	}// getTime()
	
	// create a user right here if you want
	public void setUser(String firstName, String lastName, String userId) {
		user.setFirstName(firstName);
		user.setLastName(firstName);
		user.setUserID(userId);
	}// setUser(String, String, String)
	
	public void setUser(User newUser) {
		user = newUser;
	}// setUser(User)
	
	// create a device right here if you want
	public void setDevice(String brand, String desktop_laptop, 
			String make, String type, String serial_no, String status) {
		device.setBrand(brand);
		device.setDesktopLaptop(desktop_laptop);
		device.setMake(make);
		device.setType(type);
		device.setSerial(serial_no);
		device.setStatus(status);
	}// setDevice(String, String, String, String, String)
	
	public void setDevice(Devices newDevice) {
		device = newDevice;
	}// setDevice(Devices)
	
	public void setTime(Time newtime) {
		time = newtime;
	}// setTime(Time)

	public void setUsers(String string) {
		users = string;
	}// setUsers(String)
	
	public void setDevices(String string) {
		devices = string;
	}// setDevices(String)
	
	public void setTimes(String string) {
		times = string;
	}// getTimes(String)
 	
	public static Map<String, CheckOut> getCheckout() {
		return CHECKOUT;
	}// getCheckout()
	
	public String getUserToString() {
		return "\n" + user.getLastName() + ", " + 
				user.getFirstName() + ": " +
				user.getUserID();
	}// getUserToString()
	
	public User toCheckOutUser() {
		String data = instance.getUserToString();
		System.out.println(data);
		String[] nameuserid = data.split(":");
		String[] firstlast = nameuserid[0].split(",");
		String first = firstlast[1];
		String last = firstlast[0];
		String userid = nameuserid[1];
		
		User user = new User(first, last, userid);
		return user;
	}// toCheckOutUser()
	
	public String getDevicegetDevice() throws IOException {
		  Devices newb = getDevice();
		  newb.setStatus("Checked Out");
		  return newb.getBrand() + ":" + newb.getType() + ":" +  newb.getSerial();  
	}// getDevicegetDeviceCheckedOut()
	
	public String getDevicegetDeviceCheckedOut() throws IOException {
		  Devices newb = getDevice();
		  newb.setStatus("Checked Out");
		  return newb.getBrand() + newb.getType() + " (Serial no. " + newb.getSerial() + ")";  
	}// getDevicegetDeviceCheckedOut()
	
	public String getDevicegetDeviceCheckedIn() throws IOException {
		Devices newb = getDevice();
		newb.setStatus("In");
		return newb.getBrand() + newb.getType() + " (Serial no. " + newb.getSerial() + ")"; 
	}// getDevicegetDeviceCheckedIn()
	
	public Devices toCheckOutDevice() throws IOException {
		String data = instance.getDevicegetDevice();
		System.out.println(data);
		String[] device = data.split("(Serial no. ");
		String[] brandtype = device[0].split(" ");
		String brand = brandtype[0];
		String type = brandtype[1];
		String[] serialpare = device[1].split(")");
		String serial = serialpare[0];
		
		Devices devices = new Devices (brand, type, serial);
		return devices;
	}// toCheckOutDevice()
	
	public String getTimeToString() {
		return time.getMonth() + "/" + time.getDay() + "/" +
				time.getYear() + " " + time.getHours() + ":" +
				time.getMinutes();
	}// getTimeToString()
	
	public Time toCheckOutTime() {
		String data = instance.getTimeToString();
		String[] splitdatetime = data.split(" ");
		String[] date = splitdatetime[0].split("/");
		String[] time = splitdatetime[1].split(":");
		String minutes = time[1];
		String hours = time[0];
		String day = date[1];
		String month = date[0];
		String year = date[2];
		
		Time newtime = new Time(minutes, hours, day, month, year);
		return newtime;
	}// toCheckOutTime()
	
	public String CheckOutToString() throws IOException {
		return getUserToString() + "|" +  getDevicegetDeviceCheckedOut() + "|" + 
				getTimeToString();
	}// CheckOutToString()
	
	public String toCheckOuttoCheckOut() throws IOException {
		return toCheckOutUser() + "|" + toCheckOutDevice() + "|" + getTimeToString();
	}
	
	public void writeCheckOut() {
			  try {

				  CheckOut checkout = instance;
				 File file = new File("Checkout.txt");
				 
				 // if the file doesn't exist, then create it
				 if (!file.exists()) {
					 file.createNewFile();
				 }// if
				 
				 // now write to checkouts
				 FileWriter fileWriter = new FileWriter(file, true);
				 BufferedWriter bw = new BufferedWriter(fileWriter);
				 
				 
				 bw.write(toCheckOuttoCheckOut());
				 bw.flush();
				 bw.close();
				 
				 System.out.println("Entry added to Checkout.txt");
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }// tempDevice()
	
	public boolean checkoutMatch(CheckOut two) {
		if (users.equals(two.users) && devices.equals(two.devices)
				&& times.equals(two.times)) {
			return true;
		}// if 
		else return false;
	}// checkOutMatch(Checkout)
	
	//find check out based on device alone
	public CheckOut findCheckOutBasedonDevice(CheckOut check) {
		if (check.getDevice().deviceMatch(instance.getDevice())) {
			return check;
		}
		else return null;
	}// findCheckOutBasedonDevice(CheckOut)
	
	
	public void findAndRemoveCheckout() throws IOException {
		CheckOut findCheckOut = new CheckOut(instance.getDevice());
		ArrayList<CheckOut> checkoutList = new ArrayList<CheckOut>();
		String line = "";
		String splitBy ="|";
		File file = new File("Checkout.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		
		while ((line = br.readLine()) != null) {
			String[] object = line.split(splitBy);
			String user = object[0];
			String device = object[1];
			String datetime = object[2];
			

			CheckOut newCheckOut = new CheckOut(toCheckOutUser(), 
						toCheckOutDevice(), toCheckOutTime());
			checkoutList.add(newCheckOut);
		}// while 
		
		br.close(); 
		
		for (int row = 0; row < checkoutList.size(); row++) {
			if (findCheckOut.equals(checkoutList.get(row).getDevice())) {
				checkoutList.remove(checkoutList.get(row));
			}// if
		}// for
		
		// find checkout, delete from list, and overwrite file
		FileWriter fw = new FileWriter("Checkout.txt", true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		
		for (int i = 0; i < checkoutList.size(); i++) {
			String data = checkoutList.get(i).CheckOutToString();
			bw.write(data);
			bw.flush();
			bw.close();
		}// for 
	}// findAndRemoveCheckout()
	
}// class CheckOut
*/