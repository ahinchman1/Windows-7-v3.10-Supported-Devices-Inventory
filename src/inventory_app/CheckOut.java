
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
				 
				// Update CheckOut in table
				 File otherFile = new File("Inventory.csv");
				 
				 ReadCSV read = new ReadCSV();
				 ArrayList<Devices> devices = read.ReadCSVfiledevices(otherFile);
				 
				 FileWriter fw = new FileWriter(otherFile);
				 BufferedWriter bww = new BufferedWriter(fw);
				 
				 for (int row = 0; row < devices.size(); row++) {
					 if (deviceMatch(this.getDevice(), devices.get(row))) {
						 System.out.println("FOUND");
						 devices.get(row).setStatus("Checked Out");
						 System.out.println(devices.get(row).deviceToString());
						 bww.write(devices.get(row).deviceToString());
					 }// if
					 else {
						 bww.write(devices.get(row).deviceToString());
					 }
				 }// for 
				 
				 bww.flush();
				 bww.close();
						 
			  } catch (Exception e) {
				  e.printStackTrace();
			  }// catch
		  }// writeCheckOut()
	
	  public boolean deviceMatch(Devices one, Devices two) {
		  System.out.println(one.deviceToString() + " == " + two.deviceToString());
		  if (one.getBrand().equals(two.getBrand()) &&
		      one.getDesktopLaptop().equals(two.getDesktopLaptop()) &&
			  one.getMake().equals(two.getMake()) &&
			  one.getType().equals(two.getType()) &&
			  one.getSerial().equals(two.getSerial()) &&
			  one.getStatus().equals(two.getStatus())) {
			  return true;
		  }// if
		  else return false;
	  }// deviceMatch(Devices, Devices)
	
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