package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class StudentsOverviewView {

    private StudentsOverviewPresentationModel model;
    private CWJXTable tStudents;

    public StudentsOverviewView(StudentsOverviewPresentationModel m) {
        model = m;
    }

    private void initComponents() {
        tStudents = CWComponentFactory.createTable(
                model.createStudentTableModel(model.getStudentSelection()),
                "Keine Kunden vorhanden",
                "cw.studentmanagementmodul.StudentsOverviewView.studentTableState"
                );
        tStudents.setSelectionModel(
                new SingleListSelectionAdapter(
                    new JXTableSelectionConverter(
                        model.getStudentSelection().getSelectionIndexHolder(),
                        tStudents)));
        
    }

    private void initEventHandling() {
        // Nothing to do
    }

    public JPanel buildPanel() {
        initComponents();
        initEventHandling();

        JViewPanel panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(CWComponentFactory.createScrollPane(tStudents), BorderLayout.CENTER);

        return panel;
    }

}
