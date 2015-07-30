package inventory_app;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CheckOutDialoguePart2 extends JDialog {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private JLabel firstname;
    private JTextField firstName;
    private JLabel lastname;
    private JTextField lastName;
    private JLabel userid;
    private JTextField userId;;
    private JButton ok = new JButton("ok");
    private JButton cancel = new JButton("cancel");
    
    private boolean succeeded;
    
    public CheckOutDialoguePart2(Frame parent) throws Exception {
    	super(parent);
    	
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints cs = new GridBagConstraints();
    	
    	cs.fill = GridBagConstraints.HORIZONTAL;
    	
    	firstname = new JLabel("  First Name ");
    	cs.gridx = 0;
    	cs.gridy = 0;
    	cs.gridwidth = 1;
    	panel.add(firstname, cs);
    	
    	firstName = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 0;
    	cs.gridwidth = 1;
    	panel.add(firstName, cs);
    	
    	lastname = new JLabel("  Last Name ");
    	cs.gridx = 0;
    	cs.gridy = 1;
    	cs.gridwidth = 1;
    	panel.add(lastname, cs);
    	
    	lastName = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 1;
    	cs.gridwidth = 1;
    	panel.add(lastName, cs);
    	
    	userid = new JLabel("  User ID ");
    	cs.gridx = 0;
    	cs.gridy = 2;
    	cs.gridwidth = 1;
    	panel.add (userid, cs);
    	
    	userId = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 2;
    	cs.gridwidth = 1;
    	panel.add(userId, cs);
    	
    	ok.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    			if (getFirstName() != null && getLastName() != null && getUserId() != null) {
    				User newUser = new User(getFirstName(), getLastName(), getUserId());
					Time newTime = getCurrentTimefromTimeStamp(getCurrentTime());
    				try {
    					
    					newUser.tempUser();
    					newTime.tempTime();
    					
						JOptionPane.showMessageDialog(CheckOutDialoguePart2.this, 
								getFirstName() + " " + getLastName() + " has checked out a " + getDevice().getDevicegetDevice() +
								" at " + getCurrentTimeString() + "                ");
					} catch (HeadlessException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    				succeeded = true;
    				//Devices checkoutFinish = new Devices();
    				CheckOut checkoutfinish;
					try {
						checkoutfinish = new CheckOut(newUser, getDevice(), newTime);
						checkoutfinish.writeCheckOut();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    				dispose();
    			} else {
    				JOptionPane.showMessageDialog(CheckOutDialoguePart2.this,
    						JOptionPane.ERROR_MESSAGE);
    				//reset the fields
    				firstName.setText("");
    				lastName.setText("");
    				userId.setText("");
    				succeeded = false;
    			}
    		}
    	});
    	
    	cancel.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    		dispose();
    		}
    	});
    	
    	JPanel buttonlayout = new JPanel();
    	buttonlayout.add(ok);
    	buttonlayout.add(cancel);
    	
    	getContentPane().add(panel, BorderLayout.CENTER);
    	getContentPane().add(buttonlayout, BorderLayout.PAGE_END);
    	
    	pack();
    	setResizable(false);
    	setLocationRelativeTo(parent);
    }// CheckOutDialogue
    
    public String getFirstName() {
    	return firstName.getText().trim();
    }
    
    public String getLastName() {
    	return lastName.getText().trim();
    }
    
    public String getUserId() {
    	return userId.getText().trim();
    }
    
    public boolean getSuceeded() {
    	return succeeded;
    }
    
    public Timestamp getCurrentTime() {
    	Calendar calendar = Calendar.getInstance();
    	Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());

    	return currentTimestamp;
    }// getCurrentTime()
    
    public String getCurrentTimeString() {
    	Calendar calendar = Calendar.getInstance();
    	Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());
    	
    	return currentTimestamp.toString();
    }// getCurrentTimeString()
    
    public Time getCurrentTimefromTimeStamp(Timestamp time) {
    	String newTime = time.toString();
    	String[] split = newTime.split(" ");
    	String[] date = split[0].split("-");
    	String[] recordedTime = split[1].split(":");
    	Time getTime = new Time(recordedTime[1], recordedTime[0], date[2], date[1], date[0]);
    	
    	return getTime;
    }// getCurrentTimefromTimeStamp(Timestamp)

	public void show(Component component, int x, int y) {
		System.out.println();	
	}// show(Component, int, int)
	
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
  		  newnew.setType(newString[3]);
  		  newnew.setMake(newString[2]);
  		  newnew.setSerial(newString[4]);
  		  newnew.setStatus(deviceStatus);
  	  }//while
  	  System.out.println("Device found in file...");
  	  System.out.println("   " + newnew.getDeviceToString());
  	  brd.close();
		  return newnew;
	  }// getDevice()
	  
	  public User getUser() throws IOException {
		  User newnew = new User();
		  // go to file to grab contents
		  File file = new File("temp.txt");
		  String line = "";
		  String splitBy = "|";
		  String splitByAgain = ":";
		  String splitsplit = " ";
		  BufferedReader brd = new BufferedReader(new FileReader(file));
  	  
		  while((line = brd.readLine()) != null) {
			  String newString[] = line.split(splitBy);
			  String usersplitid[] = newString[1].split(splitByAgain);
			  String name[] = usersplitid[0].split(splitsplit);
			  String firstNamee = name[0];
			  String lastNamee = name[1];
			  String ide = usersplitid[1];
			  
			  newnew.setFirstName(firstNamee);
			  newnew.setLastName(lastNamee);
			  newnew.setUserID(ide);
		  }//while
		  System.out.println("Device found in file...");
		  System.out.println("   " + newnew.getUserToString());
		  brd.close();
		  return newnew;
	  }// getDevice()

}// class CheckOutDialoguePart2
