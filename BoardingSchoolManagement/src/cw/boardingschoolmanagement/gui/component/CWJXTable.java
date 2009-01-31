package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.ObjectSaver;
import cw.boardingschoolmanagement.app.XProperties;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author ManuelG
 */
public class CWJXTable extends JXTable {

    private String emptyText = "";

    public CWJXTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    public CWJXTable(TableModel dm) {
        super(dm);
    }

    public CWJXTable() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getModel().getRowCount() == 0) {
            int x = (getWidth() - g.getFontMetrics().stringWidth(emptyText)) / 2;
            int y = (getHeight() - g.getFontMetrics().getHeight()) / 2;
            g.setColor(Color.GRAY);
            g.drawString(emptyText, x, y);
        }
    }

    public void setEmptyText(String emptyText) {
        this.emptyText = emptyText;
        repaint();
    }

    public String getEmptyText() {
        return emptyText;
    }

    public void saveHeader() {
        new XProperties().registerPersistenceDelegates();
        XProperties.XTableProperty x = new XProperties.XTableProperty();
        Object tableState = x.getSessionState(this);
        ObjectSaver.save(tableState, "table.txt");

    }

//    Object tableState;

    public void loadHeader() {
        Object tableState = ObjectSaver.load("table.txt");
        if (tableState != null) {
            XProperties.XTableProperty x = new XProperties.XTableProperty();
            x.setSessionState(this, tableState);
        }
    }
//      /**
//  * Attendance Save Header
//  *
//  *
//  * @param pathname The relative path and filename
//  *
//  * @return Returns true if successful, false if not
//  */
//  public boolean saveHeader(){
//
//    //if(table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).equals(null))
//      //table.getModel().setValueAt("",table.getSelectedRow(), table.getSelectedColumn());
//
//    //if(!(table.getSelectedColumn() == 0)) table.setColumnSelectionInterval(0,0);
//
//    /*try{
//      if (table.isEditing()) { //To keep last value while saving
//        TableCellEditor tc = table.getCellEditor();
//        // To cancel the editing
//        //tc.cancelCellEditing();
//        // Or to keep the editor's current value
//        if(tc != null)tc.stopCellEditing();
//      }
//    }
//    catch(Exception d){
//      d.printStackTrace();
//    }*/
//
//
//
//
//
//    try{
//      FileOutputStream fos2 = new FileOutputStream(pathname + "/"+tablepath);
//      ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
//      this.removeEditor();
//      TableColumnModel tcm = (TableColumnModel)this.getColumnModel();
//      oos2.writeObject(tcm);
//
//        //oos.writeObject(tm);
//      oos2.close();
//      fos2.close();
//      this.setFocusable(true);
//
//    }
//    catch(Exception e){
//      System.out.println(e.getMessage());
//      e.printStackTrace();
//      return false;
//    }
//
//    return true;
//  }
//
//
//  private static String tablepath = "table.txt";
//  private static String pathname = "tablepath";
//
//  /**
//   *
//   * Load a file header
//   *
//   * @param pathname The relative path and filename
//   *
//   * @return Returns true if successful, false if not
//   */
//  public boolean loadHeader(){
//    //create a JTableHeader to hold the model of the header
//    TableColumnModel tcm = null;
//
//    try{
//      //create FileInputStream and ObjectInputStream go get the table header
//      FileInputStream fis2 = new FileInputStream(pathname +
//                                                 "/"+tablepath);
//      ObjectInputStream ois2 = new ObjectInputStream(fis2);
//
//      //get the JTableHeader from the file
//      tcm = (TableColumnModel) (ois2.readObject());
//
//      //set the JTableHeader and close the input streams
//      this.setColumnModel(tcm);
//
//      ois2.close();
//      fis2.close();
//    }
//    catch(Exception e){
//      try{
//        //try to make the file if it didnt exist
//
//        File outputFile2 = new File(pathname + "/"+tablepath);
//        if(outputFile2.exists()) throw new Exception("Fine");
//        else{
//          JTable temp = new JTable(this.getRowCount(), this.getColumnCount());
//          this.setColumnModel(temp.getColumnModel());
////          this.getColumnModel().getColumn(0).setHeaderValue("Last Name, First Name");
//          saveHeader();
//        }
//      }
//      catch(Exception f){
//        System.out.println(e.getMessage());
//        System.out.println(f.getMessage());
//        if(f.getMessage().equals("Fine")) return true;
//        return false;
//      }
//
//    }
//
//    return true;
//  }
//
}
