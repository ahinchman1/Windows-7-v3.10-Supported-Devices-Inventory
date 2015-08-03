package inventory_app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

public class CheckInDialogue extends JDialog{
    
    private JButton select = new JButton("select");
    private JButton cancel = new JButton("cancel");
    int rowSelected;
    
    private boolean succeeded;
    
    ArrayList<Devices> devices = new ArrayList<Devices>();
    ArrayList<CheckOut> checkOutList = new ArrayList<CheckOut>();
    Devices data = new Devices();
    
    public CheckInDialogue (Frame parent) throws Exception {
    	super(parent, "Check out Device", true);
    	
    	JPanel panel = new JPanel(new GridBagLayout());
    	JPanel gui = new JPanel();
    	GridBagConstraints cs = new GridBagConstraints();
    	
        gui.setBackground(Color.DARK_GRAY);
        final JTable table = new JTable(new TableModel());
        ReadCSV csv = new ReadCSV();
 		TableModel tableModel = new TableModel();
 		table.setModel(tableModel);
 		File file = new File("Inventory.csv");
 		ArrayList<Devices> devices = csv.ReadCSVfiledevices(file);
 		System.out.println(devices);
 		tableModel.AddCSVData(devices);
 		
 		table.setRowSelectionAllowed(true);
 		table.setColumnSelectionAllowed(false);
 		
  	   class RowColumnListSelectionListener implements ListSelectionListener {
	        public void valueChanged(ListSelectionEvent e) {
	            if (table.getRowSelectionAllowed() &&
	                    !table.getColumnSelectionAllowed()) {
	                int[] rows = table.getSelectedRows();
	                System.out.println("Selected Rows: " + Arrays.toString(rows));
	                System.out.println("Device at Selected Row: " + devices.get(rows[0]));
	            } if (table.getColumnSelectionAllowed() &&
	                    !table.getRowSelectionAllowed()) {
	                int[] cols = table.getSelectedColumns();
	                System.out.println("Selected Columns: " + Arrays.toString(cols));
	            } else if (table.getCellSelectionEnabled()) {
	                int selectionMode = table.getSelectionModel().getSelectionMode();
	                System.out.println("selectionMode = " + selectionMode);
	                if (selectionMode == ListSelectionModel.SINGLE_SELECTION) {
	                    int rowIndex = table.getSelectedRow();
	                    int colIndex = table.getSelectedColumn();
	                    System.out.printf("Selected [Row,Column] = [%d,%d]n", rowIndex, colIndex);
	                } else if (selectionMode == ListSelectionModel.SINGLE_INTERVAL_SELECTION || 
	                        selectionMode == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
	                    int rowIndexStart = table.getSelectedRow();
	                    int rowIndexEnd = table.getSelectionModel().getMaxSelectionIndex();
	                    int colIndexStart = table.getSelectedColumn();
	                    int colIndexEnd = table.getColumnModel().getSelectionModel().getMaxSelectionIndex();
	 
	                    for (int i = rowIndexStart; i <= rowIndexEnd; i++) {
	                        for (int j = colIndexStart; j <= colIndexEnd; j++) {
	                            if (table.isCellSelected(i, j)) {
	                                System.out.printf("Selected [Row,Column] = [%d,%d]n", i, j);
	                                rowSelected = i;
	                            }// if
	                        }// for
	                    }// for
	                }// else if
	            }// if
	        }// valueChanged(ListSelctionEvent
	    }// class RowColumnListSelectionListener()
 	   
 	  table.getSelectionModel().addListSelectionListener(
              new RowColumnListSelectionListener());
 	  
 	  table.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseReleased(MouseEvent e) {
              int r = table.rowAtPoint(e.getPoint());
              if (r >= 0 && r < table.getRowCount()) {
                  table.setRowSelectionInterval(r, r);
              } else {
                  table.clearSelection();
              }

              int rowindex = table.getSelectedRow();
              if (rowindex < 0)
                  return;
              if (e.getComponent() instanceof JTable) {
                  JDialog dialog = new JDialog();
                  int selectedRow = table.getSelectedRow();

                  dialog.setTitle("Edit Row: " + selectedRow);
                  
                  
                  try {
					Devices checkinDevice = data.getDeviceCheckedOut(selectedRow);
					checkinDevice.tempDevice(); 
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                		  //((TableModel) table.getModel()).AddCSVData(devices).get(rowSelected);
                  ArrayList<Devices> tempData = new ArrayList<Devices>();
                  tempData.add(data);

                  TableModel tempModel = new TableModel();

                  JTable table = new JTable(tempModel);
                  dialog.add(new JScrollPane(table));
                  dialog.setVisible(true);
              }// if
          }// mouseReleased(MouseAdapter())
 	  });

 		
 		JScrollPane scrollPane = new JScrollPane(table);
 		gui.add(scrollPane, BorderLayout.CENTER);
 		scrollPane.setBackground(Color.DARK_GRAY);
 		scrollPane.setBorder(new EmptyBorder(10, 5, 10, 5));
         
 		TableRowSorter<javax.swing.table.TableModel> rowSorter = 
 				new TableRowSorter<>(table.getModel());
 		table.setRowSorter(rowSorter);
 		rowSorter.setRowFilter(RowFilter.regexFilter("Checked Out"));
 		
    	panel.add(gui, cs);
    	cs.fill = GridBagConstraints.HORIZONTAL;
    	
    	select.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
				try {
					ReadCSV read = new ReadCSV();
					Devices string = read.gettempdevice();
					
					string.setStatus("In");
					
					//rewrite inventory
					 File otherFile = new File("Inventory.csv");
					 
					 ReadCSV d = new ReadCSV();
					 ArrayList<Devices> devices = d.ReadCSVfiledevices(otherFile);
					 
					 FileWriter fw = new FileWriter(otherFile);
					 BufferedWriter bww = new BufferedWriter(fw);
					 
					 for (int row = 0; row < devices.size(); row++) {
						 if (deviceMatch(this.getDevice(), devices.get(row))) {
							 devices.get(row).setStatus("In");
							 bww.write(devices.get(row).deviceToString());
						 }// if
						 else {
							 bww.write(devices.get(row).deviceToString());
						 }
					 }// for 
					 
					 bww.flush();
					 bww.close();
					 
					//rewrite checkouts
					 d.editCheckOuts();
					 
					JOptionPane.showMessageDialog(CheckInDialogue.this, 
							string.getBrand() + " " + string.getMake() + " has been checked back in.            ");
					dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		
    		  public boolean deviceMatch(Devices one, Devices two) {
    			  System.out.println(one.deviceToString() + " == " + two.deviceToString());
    			  if (one.getBrand().equals(two.getBrand()) &&
    			      one.getDesktopLaptop().equals(two.getDesktopLaptop()) &&
    				  one.getMake().equals(two.getMake()) &&
    				  one.getType().equals(two.getType()) &&
    				  one.getSerial().equals(two.getSerial()) &&
    				  one.getStatus().equals(two.getStatus())) {
    				  System.out.println("DEVICE FOUND");
    				  return true;
    			  }// if
    			  else return false;
    		  }// deviceMatch(Devices, Devices)

    	    
    		  // get the single entry from the file
    		  public Devices getDevice() throws IOException {
    			  Devices newnew = new Devices();
    			  // go to file to grab contents
    			  File file = new File("temp.txt");
    			  String line = "";
    			  String splitByAgain = ":";
    			  BufferedReader brd = new BufferedReader(new FileReader(file));
    		  
    			  while((line = brd.readLine()) != null) {
    				  System.out.println(line);
    				  String newString[] = line.split(splitByAgain);
    				  String device[] = newString[5].split(" ");
    				  String deviceStat = device[0] + " " + device[1];
    				  newnew.setBrand(newString[0]);
    				  newnew.setDesktopLaptop(newString[1]);
    				  newnew.setType(newString[3]);
    				  newnew.setMake(newString[2]);
    				  newnew.setSerial(newString[4]);
    				  newnew.setStatus(deviceStat);
    			  }//while
    			  brd.close();
    			  return newnew;
    		  }// getDevice()
    	});
    	
    	cancel.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent event) {
    		dispose();
    		}
    	});
    	
    	JPanel buttonlayout = new JPanel();
    	buttonlayout.add(select);
    	buttonlayout.add(cancel);
    	
    	getContentPane().add(gui, BorderLayout.CENTER);
    	getContentPane().add(buttonlayout, BorderLayout.PAGE_END);
    	
    	pack();
    	setResizable(false);
    	setLocationRelativeTo(parent);
    }// CheckOutDialogue
    
    
    public boolean getSuceeded() {
    	return succeeded;
    }
	  
    public Timestamp getCurrentTime() {
    	Calendar calendar = Calendar.getInstance();
    	Timestamp currentTimestamp = new Timestamp(calendar.getTime().getTime());

    	return currentTimestamp;
    }
	private static final long serialVersionUID = 1L;
	

}// class CheckInDialogue
