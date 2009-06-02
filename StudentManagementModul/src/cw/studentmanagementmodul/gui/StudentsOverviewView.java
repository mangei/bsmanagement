package cw.studentmanagementmodul.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;
import java.awt.BorderLayout;

/**
 * @author CreativeWorkers.at
 */
public class StudentsOverviewView extends CWView
{

    private StudentsOverviewPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWTable tStudents;

    public StudentsOverviewView(StudentsOverviewPresentationModel m) {
        model = m;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {
        tStudents = CWComponentFactory.createTable(
                model.createStudentTableModel(model.getStudentSelection()),
                "Keine Kunden vorhanden",
                "cw.studentmanagementmodul.StudentsOverviewView.studentTableState"
                );
        tStudents.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        model.getStudentSelection().getSelectionIndexHolder(),
                        tStudents)));

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(tStudents);
    }

    private void initEventHandling() {
        // Nothing to do
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());

        this.getContentPanel().setLayout(new BorderLayout());
        this.getContentPanel().add(CWComponentFactory.createScrollPane(tStudents), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }

}
