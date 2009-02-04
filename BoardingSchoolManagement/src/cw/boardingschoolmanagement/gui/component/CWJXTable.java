package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.ObjectSaver;
import cw.boardingschoolmanagement.app.XProperties;
import cw.boardingschoolmanagement.app.XProperties.XTableState;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.ColumnControlButton;

/**
 * This is a extended JXTable with following additional features: <br>
 *  + You can set an text which is shown when the table is empty
 *  + You can save and load the table state (view)
 * @author ManuelG
 */
public class CWJXTable extends JXTable {

    private String emptyText = "";
    private String tableStateName = "";

    private static String tableStateDirectory = PropertiesManager.getProperty("application.gui.tableStateDirectory", "tableStates");
    private static boolean persistenceDelegatesRegisted = false;

    private static String PROPERTYNAME_TABLESTATENAME = "tableStateName";

    public CWJXTable(TableModel dm) {
        super(dm);
        init();
    }

    public CWJXTable() {
        init();
    }

    private void init() {
        installColumnControl();
    }

    private void installColumnControl() {
        setColumnControl(new CWColumnControlButton(this));
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

    public void saveTableState() {
        if(tableStateName == null || tableStateName.isEmpty()) {
            return;
        }

        File dir = new File(tableStateDirectory);
        if(!dir.exists()) {
            dir.mkdir();
        }

        String path = dir.getName() + System.getProperty("file.separator") + tableStateName + ".xml";

        if(!persistenceDelegatesRegisted) {
            new XProperties().registerPersistenceDelegates();
            persistenceDelegatesRegisted = true;
        }
        
        XProperties.XTableProperty x = new XProperties.XTableProperty();
        Object tableState = x.getSessionState(this);
        ObjectSaver.save(tableState, path);
    }

//    Object tableState;

    public void loadTableState() {
        if(tableStateName == null || tableStateName.isEmpty()) {
            return;
        }

        File dir = new File(tableStateDirectory);
        if(!dir.exists()) {
            dir.mkdir();
        }

        String path = dir.getName() + System.getProperty("file.separator") + tableStateName + ".xml";

        if(!persistenceDelegatesRegisted) {
            new XProperties().registerPersistenceDelegates();
            persistenceDelegatesRegisted = true;
        }

        XTableState tableState = (XTableState) ObjectSaver.load(path);
        if (tableState != null) {
            XProperties.XTableProperty x = new XProperties.XTableProperty();
            x.setSessionState(this, tableState);
        }

        // We have to install the column control again, because it got removed by the loading process
        installColumnControl();
    }

    public String getTableStateName() {
        return tableStateName;
    }

    public void setTableStateName(String tableStateName) {
        String old = this.tableStateName;
        this.tableStateName = tableStateName;
        firePropertyChange(PROPERTYNAME_TABLESTATENAME, old, tableStateName);
    }

    private class CWColumnControlButton extends ColumnControlButton {

        private Action saveTableStateAction;
        private Action loadTableStateAction;

        public CWColumnControlButton(JXTable table) {
            super(table);

            List<Action> actions = new ArrayList<Action>();

            saveTableStateAction = new AbstractAction("Ansicht speichern") {
                public void actionPerformed(ActionEvent e) {
                    saveTableState();
                }
            };

            loadTableStateAction = new AbstractAction("Ansicht zurücksetzen") {
                public void actionPerformed(ActionEvent e) {
                    loadTableState();
                }
            };

            actions.add(saveTableStateAction);
            actions.add(loadTableStateAction);

            popup.addAdditionalActionItems(actions);

            // Update the actions
            CWJXTable.this.addPropertyChangeListener(PROPERTYNAME_TABLESTATENAME, new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    updateActions();
                }
            });
            updateActions();

        }

        private void updateActions() {
            String tableStateName = CWJXTable.this.getTableStateName();
            if(tableStateName == null || tableStateName.isEmpty()) {
                saveTableStateAction.setEnabled(false);
                loadTableStateAction.setEnabled(false);
            } else {
                saveTableStateAction.setEnabled(true);
                loadTableStateAction.setEnabled(true);
            }
        }
    }
}
