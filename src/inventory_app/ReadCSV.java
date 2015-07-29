package inventory_app;

 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
 
public class ReadCSV {
	  private final ArrayList<String[]> Rs = new ArrayList<String[]>();
	  private final ArrayList<Devices> deviceList = new ArrayList<Devices>();
	  private final ArrayList<CheckOut> checkOutList = new ArrayList<CheckOut>();
      private String[] OneRow;

      public ArrayList<String[]> ReadCSVfile(File DataFile) {
          try {
              BufferedReader brd = new BufferedReader(new FileReader(DataFile));
              while (brd.ready()) {
                  String st = brd.readLine();
                  OneRow = st.split(",");
                  Rs.add(OneRow);
                  //System.out.println(Arrays.toString(OneRow));
              } // end of while
              brd.close();
          } // end of try
          catch (Exception e) {
              String errmsg = e.getMessage();
              System.out.println("File not found:" + errmsg);
          } // end of Catch
          return Rs;
      }// ReadCSVfile(File)
      
      public ArrayList<Devices> ReadCSVfiledevices(File DataFile) throws IOException {
    	  String line = "";
    	  String splitBy = ",";
    	  BufferedReader brd = new BufferedReader(new FileReader(DataFile));
    	  while((line = brd.readLine()) != null) {
    			  //split on comma
    			  String[] dev = line.split(splitBy);
    			  //System.out.println(Arrays.toString(dev));
    		  
    			  Devices newDevice = new Devices();
    		  
    			  newDevice.setBrand(dev[0]);
    			  newDevice.setDesktopLaptop(dev[1]);
    			  newDevice.setMake(dev[2]);
    			  newDevice.setType(dev[3]);
    			  newDevice.setSerial(dev[4]);
    			  newDevice.setStatus(dev[5]);
    		  
    			  //add the object to the array
    			  if (!newDevice.getBrand().equals("brand")) {
    				  deviceList.add(newDevice);
    			  }
    	  }// while
    	  brd.close();
    	  return deviceList;
      }// readCSVfiledevices(File)
      
      public ArrayList<Devices> ReadCSVfiledevicesCheckedIn(File DataFile) throws IOException {
    	  String line = "";
    	  String splitBy = ",";
    	  BufferedReader brd = new BufferedReader(new FileReader(DataFile));
    	  System.out.println("Devices checked in..");
    	  
    	  while((line = brd.readLine()) != null) {
    			  //split on comma
    			  String[] dev = line.split(splitBy);
    			  
    			  //System.out.println(Arrays.toString(dev));
    		  
    			  if (dev[5].equals("In")) {
    			  
    				  Devices newDevice = new Devices();
    		  
    				  newDevice.setBrand(dev[0]);
    				  newDevice.setDesktopLaptop(dev[1]);
    				  newDevice.setMake(dev[2]);
    				  newDevice.setType(dev[3]);
    				  newDevice.setSerial(dev[4]);
    				  newDevice.setStatus(dev[5]);
    		  
    				  //add the object to the array
    				  deviceList.add(newDevice);
    			  }// if
    	  }// while
    	  brd.close();
    	  System.out.println(deviceList);
    	  return deviceList;
      }// readCSVfiledevicesCheckedIn(File)
      
      public ArrayList<Devices> ReadCSVfiledevicesCheckedOut(File DataFile) throws IOException {
    	  String line = "";
    	  String splitBy = ",";
    	  BufferedReader brd = new BufferedReader(new FileReader(DataFile));
    	  
    	  while((line = brd.readLine()) != null) {
    		  //split on comma
    		  String[] dev = line.split(splitBy);
    		  System.out.println(Arrays.toString(dev));
    		  
    		  if (dev[5].equals("Checked Out")) {
    			  
    		  Devices newDevice = new Devices();
    		  
    		  newDevice.setBrand(dev[0]);
    		  newDevice.setDesktopLaptop(dev[1]);
    		  newDevice.setMake(dev[2]);
    		  newDevice.setType(dev[3]);
    		  newDevice.setSerial(dev[4]);
    		  newDevice.setStatus(dev[5]);
    		  
    		  //add the object to the array
    		  deviceList.add(newDevice);
    		  
    		  }//if
    		  
    	  }// while
    	  brd.close();
    	  //System.out.println(deviceList);
    	  return deviceList;
      }// readCSVfiledevices(File)
	
      /* adds an entry to Checkouts.csv */
      public void newCheckout(CheckOut check) throws IOException {
    	  try {
    		  String data = check.CheckOutToString();
    		  File file = new File("Checkouts.csv");
    	  
    		  //	if the file doesn't exist, then create it
    		  if (!file.exists()) {
    			  file.createNewFile();
    		  }// if
    		  
    		  	FileWriter filewriter = new FileWriter(file.getName(), true);
    		  	BufferedWriter bufferedwriter = new BufferedWriter(filewriter);
    		  	bufferedwriter.write(data);
    		  	bufferedwriter.flush();
    		  	bufferedwriter.close();
    	  
    		  	System.out.println("Checkout added.");
    	  } catch (IOException e) {
    		  e.printStackTrace();
    	  }
      }// addToCheckOuts(File, CheckOut)
      
}// class ReadCVS
 
