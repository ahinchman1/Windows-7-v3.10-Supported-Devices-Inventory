package inventory_app;
//import inventory_app.Authenticator;
//Reference: http://stackoverflow.com/questions/22864095/
	//reading-data-from-a-specific-csv-file-and-displaying-it-in-a-jtable?lq=1


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

 
public class Library extends Application {
	
	public Library instance;
	Stage stage;
	JPanel gui;
	
	public Library() throws Exception {
		instance = this;
		stage = new Stage();
	    //Create and set up the window.
        final JFrame frame = new JFrame("Windows 7 v3.10 Supported Devices Inventory");
        
        // pop up
        Stage checkoutStage = new Stage();
        checkoutStage.setTitle("Checkout Device");
        checkoutStage.initModality(Modality.WINDOW_MODAL);
        checkoutStage.initOwner(stage);

        
        frame.setBackground(Color.DARK_GRAY);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui = new JPanel();
        gui.setBackground(Color.DARK_GRAY);
        JTable table = new JTable(new TableModel());
        ReadCSV csv = new ReadCSV();
		TableModel tableModel = new TableModel();
		table.setModel(tableModel);
		File file = new File("Inventory.csv");
		ArrayList<Devices> devices = csv.ReadCSVfiledevices(file);
		System.out.println(devices);
		tableModel.AddCSVData(devices);
		
		JScrollPane scrollPane = new JScrollPane(table);
		gui.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        
		final TableRowSorter<javax.swing.table.TableModel> rowSorter = 
				new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
		
        //Options Menu
        JPanel optionsMenu = new JPanel();
        optionsMenu.setBackground(Color.black);
        
        // add option buttons
        final JButton checkOut, checkIn, registerDevice, refreshTable;
        checkOut = new JButton("    Check Out    ");
        checkOut.setPreferredSize(new Dimension(150, 100));
        
        checkIn = new JButton("     Check In    ");
        checkIn.setPreferredSize(new Dimension(150, 100));
        
        registerDevice = new JButton(" Register Device ");
        refreshTable = new JButton("  Refresh Table ");
        
        optionsMenu.setBorder(new EmptyBorder(10, 10, 10, 10));
        optionsMenu.setLayout(new BoxLayout(optionsMenu, BoxLayout.Y_AXIS));
        checkOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkIn.setAlignmentX(Component.CENTER_ALIGNMENT);
        registerDevice.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshTable.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        

        // provide listeners for the buttons
        checkOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        CheckOutDialogue checkoutBox;
				try {
					checkoutBox = new CheckOutDialogue(frame);
					checkoutBox.setTitle("Check Out Device");
					checkoutBox.setBackground(Color.DARK_GRAY);
			        checkoutBox.setLocationRelativeTo(frame);
			        checkoutBox.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}// actionPerformed(ActionEvent)
        });
        
        checkIn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        CheckInDialogue checkinBox;
				try {
					checkinBox = new CheckInDialogue(frame);
					checkinBox.setTitle("Check In Device");
					checkinBox.setBackground(Color.DARK_GRAY);
			        checkinBox.setLocationRelativeTo(frame);
			        checkinBox.setVisible(true);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}// actionPerformed(ActionEvent)
        });
        
        registerDevice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterDeviceDialogue registerDevice = new RegisterDeviceDialogue(frame);
				registerDevice.setTitle("Register Device");
				registerDevice.setBackground(Color.DARK_GRAY);
				registerDevice.setLocationRelativeTo(frame);
		        registerDevice.setVisible(true);
			}
        });
        
        refreshTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					reloadData();
					table.repaint();
					tableModel.fireTableDataChanged();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
        });
        
        // add buttons to frame
        optionsMenu.add(checkOut);
        optionsMenu.add(checkIn);
        optionsMenu.add(registerDevice);
        optionsMenu.add(refreshTable);
        
        // add a decorative top panel
        JPanel topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(Color.black);
        
        // add a search bar to top panel
        final JTextField jtFilter = new JTextField(30);
        jtFilter.setBorder(new EmptyBorder(4, 40, 4, 40));
        JButton jbFilter = new JButton("Search");
        topPanel.add(jbFilter, BorderLayout.EAST);
        topPanel.add(jtFilter, BorderLayout.EAST);
        
        
        //credit for the sample filter:
        // http://stackoverflow.com/questions/9941628/
        // swing-jtable-customization-for-filtering-searching
        jtFilter.getDocument().addDocumentListener(new DocumentListener() {
        	
        	@Override
        	public void changedUpdate(DocumentEvent arg0) {}
        	
        	@Override
        	public void insertUpdate(DocumentEvent arg0) {
        		String text = jtFilter.getText();
        		
        		if (text.trim().length() == 0) {
        			rowSorter.setRowFilter(null);
        		} else {
        			rowSorter.setRowFilter(RowFilter.regexFilter(text.trim()));
        		}
        	}// insertUpdate(DocumentEvent)
        	
        	@Override
        	public void removeUpdate(DocumentEvent arg0) {
        		String text = jtFilter.getText();
        		if(text.trim().length() == 0) {
        			rowSorter.setRowFilter(null);
        		} else {
        			rowSorter.setRowFilter(RowFilter.regexFilter(text));
        		}
        	}// removeUpdate(Document)
        	
        	
        });
        
        
        frame.add(gui);
        frame.add(optionsMenu, BorderLayout.WEST);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.setSize(700, 700);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create and you up the content pane.
	}// Library()
	
	public Library getInstance() {
		return instance;
	}// getInstance()
	
	@Override 
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			primaryStage.show();
		} catch (Exception e) {
			//Logger.getLogger(Library.class.getName().log(Level.SEVERE, null, e));
			System.out.println("error");
		}
	}
	
	
	private void reloadData() throws Exception {
	    //Data.clear();
	    //int column = getColumnCount();
        JTable table = new JTable(new TableModel());
        ReadCSV csv = new ReadCSV();
		TableModel tableModel = new TableModel();
		table.setModel(tableModel);
		File file = new File("Inventory.csv");
		ArrayList<Devices> devices = csv.ReadCSVfiledevices(file);
		System.out.println(devices);
		tableModel.AddCSVData(devices);
		
		JScrollPane scrollPane = new JScrollPane(table);
		gui.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        
		final TableRowSorter<javax.swing.table.TableModel> rowSorter = 
				new TableRowSorter<>(table.getModel());
		table.setRowSorter(rowSorter);
	}// reloadData()
	
	public static void main(String[] args) {
		launch(args);
	}

}// class Library
