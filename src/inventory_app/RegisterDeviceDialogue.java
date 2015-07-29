package inventory_app;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RegisterDeviceDialogue extends JDialog{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel brand;
    private JTextField jbrand;
    private JLabel desktoplaptop;
    private JTextField jdesktoplaptop;
    private JLabel make;
    private JTextField jmake;
    private JLabel mtype;
    private JTextField jtype;
    private JLabel serial;
    private JTextField jserial;
    
    private JButton ok = new JButton("ok");
    private JButton cancel = new JButton("cancel");
    
    private boolean succeeded;
    
    public RegisterDeviceDialogue(Frame parent) {
    	super(parent, "Check out Device", true);
    	
    	JPanel panel = new JPanel(new GridBagLayout());
    	GridBagConstraints cs = new GridBagConstraints();
    	
    	cs.fill = GridBagConstraints.HORIZONTAL;
    	
    	
    	//Brand
    	brand = new JLabel("  Brand ");
    	cs.gridx = 0;
    	cs.gridy = 0;
    	cs.gridwidth = 1;
    	panel.add(brand, cs);
    	
    	jbrand = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 0;
    	cs.gridwidth = 1;
    	panel.add(jbrand, cs);
    	
    	//DesktopLaptop
    	desktoplaptop = new JLabel("  \"Desktop\" or \"Laptop\" ");
    	cs.gridx = 0;
    	cs.gridy = 1;
    	cs.gridwidth = 1;
    	panel.add(desktoplaptop, cs);
    	
    	jdesktoplaptop = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 1;
    	cs.gridwidth = 1;
    	panel.add(jdesktoplaptop, cs);
    	
    	//Make
    	make = new JLabel("  Make ");
    	cs.gridx = 0;
    	cs.gridy = 2;
    	cs.gridwidth = 1;
    	panel.add (make, cs);
    	
    	jmake = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 2;
    	cs.gridwidth = 1;
    	panel.add(jmake, cs);
    	
    	//Type
    	mtype = new JLabel("  Type ");
    	cs.gridx = 0;
    	cs.gridy = 3;
    	cs.gridwidth = 1;
    	panel.add(mtype, cs);
    	
    	jtype = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 3;
    	cs.gridwidth = 1;
    	panel.add(jtype, cs);
    	
    	//Serial number
    	serial = new JLabel("  Serial ");
    	cs.gridx = 0;
    	cs.gridy = 4;
    	cs.gridwidth = 1;
    	panel.add (serial, cs);
    	
    	jserial = new JTextField(20);
    	cs.gridx = 1;
    	cs.gridy = 4;
    	cs.gridwidth = 1;
    	panel.add(jserial, cs);
    	
    	ok.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    			
    			if (getBrand() != null && getDesktopLaptop() != null && getMake() != null
    					&& getType() != null && getSerial() != null) {
    				try {
						register();
					} catch (IOException e) {
						e.printStackTrace();
					}
    				JOptionPane.showMessageDialog(RegisterDeviceDialogue.this, 
    						getBrand() + " " + getMake() + " has been registered in the Inventory");
    				succeeded = true;
    				dispose();
    			} else {
    				JOptionPane.showMessageDialog(RegisterDeviceDialogue.this,
    						JOptionPane.ERROR_MESSAGE);
    				//reset the fields
    				brand.setText("");
    				desktoplaptop.setText("");
    				make.setText("");
    				mtype.setText("");
    				serial.setText("");
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
    
    public String getBrand() {
    	return jbrand.getText().trim();
    }// getBrand()
    
    public String getDesktopLaptop() {
    	return jdesktoplaptop.getText().trim();
    }// getDesktopLaptop()
    
    public String getMake() {
    	return jmake.getText().trim();
    }// getMake()
    
    public String getTypem() {
    	return jtype.getText().trim();
    }// getType()
    
    public String getSerial() {
    	return jserial.getText().trim();
    }// getSerial()
  
    
    public boolean getSuceeded() {
    	return succeeded;
    }// getSuceeded()
    
    public Timestamp getCurrentTime() {
    	Calendar calendar = Calendar.getInstance();
    	Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());

    	return currentTimestamp;
    }// getCurrentTime()
    
    public void register() throws IOException {
    	File file = new File("Inventory.csv");
    	
    	FileWriter fw  = new FileWriter(file, true);
    	BufferedWriter bw = new BufferedWriter(fw);
    	
    	String data = getBrand() + "," + getDesktopLaptop() + "," +
    			getMake() + "," + getTypem() + "," + getSerial() + 
    			",In";
    	
    	bw.write(data);
    	bw.flush();
    	bw.close();
    }// register()

}// class RegisterDeviceDialogue