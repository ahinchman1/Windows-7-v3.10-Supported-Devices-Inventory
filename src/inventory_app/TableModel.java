package inventory_app;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.table.AbstractTableModel;


public class TableModel extends AbstractTableModel implements TableModelListener {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 4763379999233442908L;
	private final String[] columnNames = { "Brand", "Desktop/Laptop", "Make",
				"Type", "Serial No.", "Status" };
     private ArrayList<Devices> Data = new ArrayList<Devices>();
     private ArrayList<String[]> DataString = new ArrayList<String[]>();
     
     public TableModel() {
    	
     }

     public void AddCSVData(ArrayList<Devices> DataIn) throws Exception{
         this.Data = DataIn;
         this.fireTableDataChanged();
     }// AddCSVData(ArrayList<Devices>
     
     public void AddCSVDataString(ArrayList<String[]> DataIn) throws Exception{
         this.DataString = DataIn;
         this.fireTableDataChanged();
     }// AddCSVData(ArrayList<String[]>

     @Override
     public int getColumnCount() {
         return columnNames.length;// length;
     }// getColumnCount()

     @Override
     public int getRowCount() {
         return Data.size();
     }// getRowCount()

     @Override
     public String getColumnName(int col) {
         return columnNames[col];
     }// getColumnName(int)

     /*@Override
     public void setValueAt(Object value, int row, int col)
     {
    	 
         if (DEBUG) {
             System.out.println("Setting value at " + row + "," + col
                                + " to " + value
                                + " (an instance of "
                                + value.getClass() + ")");
         }

         //Data.set(row, value)[col] = value;
         fireTableCellUpdated(row, col);

         if (DEBUG) {
             System.out.println("New value of data:");
             printDebugData();
         }
     }// setValueAt(Object, int, int)*/
     
     @Override
     public void setValueAt(Object value, int row, int column)
     {
         setValueAt(value, row, column, true);
     }


     public void setValueAt(Object value, int row, int column, boolean undoable)
     {
         UndoableEditListener listeners[] = getListeners(UndoableEditListener.class);
         if (undoable == false || listeners == null)
         {
             super.setValueAt(value, row, column);
             return;
         }


         Object oldValue = getValueAt(row, column);
         super.setValueAt(value, row, column);
         JvCellEdit cellEdit = new JvCellEdit(this, oldValue, value, row, column);
         UndoableEditEvent editEvent = new UndoableEditEvent(this, cellEdit);
         for (UndoableEditListener listener : listeners)
             listener.undoableEditHappened(editEvent);
     }

     
     @Override
     public Object getValueAt(int row, int col) {
    	 Object object = null;
    	 switch(col) {
    	 	case 0:
    	 		object = Data.get(row).getBrand();
    	 		break;
    	 	case 1:
    	 		object = Data.get(row).getDesktopLaptop();
    	 		break;
    	 	case 2:
    	 		object = Data.get(row).getMake();
    	 		break;
    	 	case 3:
    	 		object = Data.get(row).getType();
    	 		break;
    	 	case 4: 
    	 		object = Data.get(row).getSerial();
    	 		break;
    	 	case 5:
    	 		object = Data.get(row).getStatus();
    		 default:
    			 break;
    	 }
    	 return object;
     }// getValueAt(int, int)
     
     @Override
     public boolean isCellEditable(int row, int col) {
    	 if (col == 5) {
    		 return true;
    	 } else {
    		 return false;
    	 }
     }// isCellEditable(int, int)

	@Override
	public void tableChanged(TableModelEvent arg0) {
		System.out.println("TableModelEvent triggered!");
        int row = arg0.getFirstRow();
        int column = arg0.getColumn();
        Object test = getValueAt(row, column);      
        System.out.println("row: " + row + " column: " + column);   
        System.out.println(test.toString());	
	}// tableChanged(TableModelEvent)
	
	
     
}
