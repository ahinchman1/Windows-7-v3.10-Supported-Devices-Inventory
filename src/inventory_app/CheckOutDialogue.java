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
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

public class CheckOutDialogue extends JDialog{
    
    private JButton select = new JButton("select");
    private JButton cancel = new JButton("cancel");
    int rowSelected;
    
    private boolean succeeded;
    
    ArrayList<Devices> devices = new ArrayList<Devices>();
    
    public CheckOutDialogue (final Frame parent) throws Exception{
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
 		final ArrayList<Devices> devices = csv.ReadCSVfiledevices(file);
 		//System.out.println(devices);
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

                  Devices data = new Devices();
                  try {
                	// find the selected Device object
                	//System.out.println(selectedRow);
					Devices checkoutDevice = data.getDeviceCheckedIn(selectedRow);
					// now start writing the device to the Java file 
					checkoutDevice.tempDevice();                                                                                                         
				} catch (IOException e1) {
					e1.printStackTrace();
				}
                  dialog.setTitle("Edit Row: " + selectedRow);
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
 		rowSorter.setRowFilter(RowFilter.regexFilter("In"));
 		
    	panel.add(gui, cs);
    	cs.fill = GridBagConstraints.HORIZONTAL;
    	
    	select.addActionListener(new ActionListener() {
    		   @Override
    		   public void actionPerformed(ActionEvent e) {
    		          CheckOutDialoguePart2 checkoutBoxp2;
					try {
						checkoutBoxp2 = new CheckOutDialoguePart2 (parent);
						checkoutBoxp2.setTitle("Check Out Device");
		    		    checkoutBoxp2.setBackground(Color.DARK_GRAY);
		    		    checkoutBoxp2.setVisible(true);
		    		    dispose();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
	

}// class CheckOutDialogue
