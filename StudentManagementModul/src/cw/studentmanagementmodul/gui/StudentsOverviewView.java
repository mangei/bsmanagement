package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.helper.JXTableSelectionConverter;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * @author CreativeWorkers.at
 */
public class StudentsOverviewView
    implements Disposable
{

    private StudentsOverviewPresentationModel model;

    private JViewPanel panel;
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

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        panel.getContentPanel().setLayout(new BorderLayout());
        panel.getContentPanel().add(CWComponentFactory.createScrollPane(tStudents), BorderLayout.CENTER);

        panel.addDisposableListener(this);
        
        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        model.dispose();
    }

}
