package inventory_app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Devices {
	
	// fields
	String brand, desktop_laptop, make, type, serial_no, status; 
	Devices instance;
	
	// constructors
	public Devices() {
		instance = this;
		this.brand = "";
		this.desktop_laptop = "";
		this.make = "";
		this.type = "";
		this.serial_no = "";
		this.status = "";
	}// Devices()
	
	public Devices(String brand, String desktop_laptop, 
			String make, String type, String serial_no, String status) {
		instance = this;
		this.brand = brand;
		this.desktop_laptop = desktop_laptop;
		this.make = make;
		this.type = type;
		this.serial_no = serial_no;
		this.status = status;
	}// Devices(String, String, String, String, String, String)
	
	public Devices(String brand, String make, String serial_no) {
		instance = this;
		this.brand = brand;
		this.make = make;
		this.serial_no = serial_no;
	}// Devices(String, String, String)
	
	
	//getters and setters
	public String getBrand() {
		return brand;
	}// getBrand()
	
	public String getDesktopLaptop() {
		return desktop_laptop;
	}// getDesktopLaptop()
	
	public String getMake() {
		return make;
	}// getMake()
	
	public String getType() {
		return type;
	}// getType()
	
	public String getSerial() {
		return serial_no;
	}// getSerial()
	
	public String getStatus() {
		return status;
	}// getStatus()
	
	public void setBrand(String newBrand) {
		brand = newBrand;
	}// setBrand(String)
	
	public void setDesktopLaptop(String desklap) {
		desktop_laptop = desklap;
	}// setDesktopLaptop(String)
	
	public void setMake(String newMake) {
		make = newMake;
	}// setMake(String)
	
	public void setType(String newType) {
		type = newType;
	}// setType(String)
	
	public void setSerial(String newSerial) {
		serial_no = newSerial;
	}// setSerial(String)
	
	public void setStatus(String newStat) {
		status = newStat;
	}// setStatus(String)
	
	public String toString() {
		return "[ " + getBrand() + ", " + getDesktopLaptop() + ", " + getMake() + ", " + getType() + ", "
				+ getSerial() + ", " + getStatus() + " ] \n" ;
	}// toString()
	
	public String deviceToString() {
		return getBrand() + "," + getDesktopLaptop() + "," + getMake() +  "," 
				+ getType() + "," + getSerial() + "," + getStatus() + "\n";
	}
	
	  /* findDevice(int)
	   *   gets the info for the row selected by reading the
	   *   CSV file and retrieving the info
	   */
	  public Devices getDevice(int rowIndex) throws IOException {
		  // read CVS file and retrieve data 
		  ReadCSV csv = new ReadCSV();
		  File file = new File("Inventory.csv");
		  ArrayList<Devices> devices = csv.ReadCSVfiledevices(file);
		  
		  System.out.println("GET DEVICES");
		  System.out.println(devices);
		  
		  Devices output = new Devices();
		  for (int row = 0; row < devices.size(); row++) {
			  if (row == rowIndex) {
				  output.setBrand(devices.get(rowIndex).getBrand());
				  output.setDesktopLaptop(devices.get(rowIndex).getDesktopLaptop());
				  output.setMake(devices.get(rowIndex).getMake());
				  output.setType(devices.get(rowIndex).getType());
				  output.setSerial(devices.get(rowIndex).getSerial());
				  output.setStatus(devices.get(rowIndex).getStatus());
			  }// if
		  }// for
		 
		  System.out.println("Device found: " + output.deviceToString());
		  return output;
	  }// getDevice(int)
	  
	  /* findDevice(int)
	   *   gets the info for the row selected by reading the
	   *   CSV file and retrieving the info
	   */
	  public Devices getDeviceCheckedIn(int rowIndex) throws IOException {
		  // read CVS file and retrieve data 
		  ReadCSV csv = new ReadCSV();
		  File file = new File("Inventory.csv");
		  ArrayList<Devices> devices = csv.ReadCSVfiledevicesCheckedIn(file);
		  
		  Devices output = new Devices();
		  for (int row = 0; row < devices.size(); row++) {
			  if (row == rowIndex) {
				  output.setBrand(devices.get(rowIndex).getBrand());
				  output.setDesktopLaptop(devices.get(rowIndex).getDesktopLaptop());
				  output.setMake(devices.get(rowIndex).getMake());
				  output.setType(devices.get(rowIndex).getType());
				  output.setSerial(devices.get(rowIndex).getSerial());
				  output.setStatus(devices.get(rowIndex).getStatus());
			  }// if
		  }// for
		 
		  System.out.println("Device found: " + output.deviceToString());
		  return output;
	  }// findDevice(int)
	  
	  /* findDeviceCheckedOut(int)
	   *   gets the info for the row selected by reading the
	   *   CSV file and retrieving the info
	   */
	  public Devices getDeviceCheckedOut(int rowIndex) throws IOException {
		  // read CVS file and retrieve data 
		  ReadCSV csv = new ReadCSV();
		  File file = new File("Inventory.csv");
		  ArrayList<Devices> devices = csv.ReadCSVfiledevicesCheckedOut(file);
		  
		  //System.out.println("GET DEVICES");
		  //System.out.println(devices);
		  
		  Devices output = new Devices();
		  for (int row = 0; row < devices.size(); row++) {
			  if (row == rowIndex) {
				  output.setBrand(devices.get(rowIndex).getBrand());
				  output.setDesktopLaptop(devices.get(rowIndex).getDesktopLaptop());
				  output.setMake(devices.get(rowIndex).getMake());
				  output.setType(devices.get(rowIndex).getType());
				  output.setSerial(devices.get(rowIndex).getSerial());
				  
				  output.setStatus(devices.get(rowIndex).getStatus());
			  }// if
		  }// for
		 
		  System.out.println("Device found: " + output.deviceToString());
		  //selectedDevice = output;
		  instance = output;
		  return output;
	  }// findDevice(int)
	  
	  public boolean deviceMatch(Devices one, Devices two) {
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
	  
	  // write a file with a single entry, replacing the entry every time
	  public void tempDevice() {
		  try {
			 String data = getDeviceToString();
			 File file = new File("temp.txt");
			 
			 // if the file doesn't exist, then create it
			 if (!file.exists()) {
				 file.createNewFile();
			 }// if
			 
			 FileWriter fileWriter = new FileWriter(file);
			 BufferedWriter bw = new BufferedWriter(fileWriter);
			 
			 bw.write(data);
			 bw.flush();
			 bw.close();
			 
			 System.out.println("Entry added to temp.txt");
			 
			 // update device status in the Inventory file 
			 ArrayList<Devices> devices = new ArrayList<Devices>();
			 File datafile = new File("Inventory.csv");
			 BufferedReader buffered = new BufferedReader(new FileReader(datafile));
			 String line = "";
			 String splitby = ",";
			 while ((line = buffered.readLine()) != null) {
				 Devices newDevice = new Devices();
				 
				 String[] OneRow = line.split(splitby);
				 //System.out.println(Arrays.toString(OneRow));
				 
				 newDevice.setBrand(OneRow[0]);
				 newDevice.setDesktopLaptop(OneRow[1]);
				 newDevice.setMake(OneRow[2]);
				 newDevice.setType(OneRow[3]);
				 newDevice.setSerial(OneRow[4]);
				 newDevice.setStatus(OneRow[5]);
				 
				 devices.add(newDevice);
				 
			 }// while
			 
			 buffered.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }// tempDevice()
	  
	  public String getDevicegetDevice() throws IOException {
		  Devices newb = getDevice();
		  return newb.getBrand() + " " + newb.getMake() + " (Serial no. " + newb.getSerial() + ")";  
	  }// getDevicegetDevice
	  
		public String getDeviceToString() {
			return getBrand() + ":" + getDesktopLaptop()
					+ ":" + getMake() + ":" + getType() + ":" +
					getSerial() + ":" + getStatus() + " | ";
		}// getDeviceToString()
	  
	  // get the single entry from the file
	  public Devices getDevice() throws IOException {
		  Devices newnew = new Devices();
		  // go to file to grab contents
		  File file = new File("temp.txt");
		  String line = "";
    	  String splitBy = ":";
    	  BufferedReader brd = new BufferedReader(new FileReader(file));
    	  
    	  while((line = brd.readLine()) != null) {
    		  String newString[] = line.split(splitBy);
    		  String device[] = newString[5].split(" ");
    		  String deviceStatus = device[0];
    		  System.out.println(Arrays.toString(newString));
    		  newnew.setBrand(newString[0]);
    		  newnew.setDesktopLaptop(newString[1]);
    		  newnew.setType(newString[2]);
    		  newnew.setMake(newString[3]);
    		  newnew.setSerial(newString[4]);
    		  newnew.setStatus(deviceStatus);
    	  }//while
    	  System.out.println("Device found in file...");
    	  System.out.println("   " + newnew.getDeviceToString());
    	  brd.close();
		  return newnew;
	  }// getDevice()
	
}// class Devices
