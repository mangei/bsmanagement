package cw.studentmanagementmodul.app;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.studentmanagementmodul.gui.StudentClassManagementPresentationModel;
import cw.studentmanagementmodul.gui.StudentClassManagementView;
import cw.boardingschoolmanagement.interfaces.Modul;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.boardingschoolmanagement.manager.MenuManager;
import cw.customermanagementmodul.app.CustomerManagementModul;
import java.util.ArrayList;

/**
 * Das Schülermodul
 * @author CreativeWorkers.at
 */
public class StudentManagementModul
implements Modul
{

    public void init() {
//        ModulManager.addExtention(new StudentCustomerGUIExtention());
//        ModulManager.addExtention(new StudentHomeGUIExtention());
        MenuManager.getSideMenu().addItem(new JButton(new AbstractAction("Klassen", CWUtils.loadIcon("cw/studentmanagementmodul/images/image.png")) {

            public void actionPerformed(ActionEvent e) {
                GUIManager.changeView(new StudentClassManagementView(new StudentClassManagementPresentationModel("Klassenverwaltung")).buildPanel());
            }
        }), "manage");
    }

    public List<Class> getDependencies() {
        List<Class> dependencies = new ArrayList<Class>();
        dependencies.add(CustomerManagementModul.class);
        return dependencies;
    }

}
