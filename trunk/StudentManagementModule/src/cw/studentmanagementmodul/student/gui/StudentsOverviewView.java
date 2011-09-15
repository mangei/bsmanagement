package cw.studentmanagementmodul.student.gui;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;

/**
 * @author Manuel Geier
 */
public class StudentsOverviewView
	extends CWView<StudentsOverviewPresentationModel>
{

    private CWTable tStudents;

    public StudentsOverviewView(StudentsOverviewPresentationModel model) {
        super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        tStudents = CWComponentFactory.createTable(
                getModel().createStudentTableModel(getModel().getStudentSelection()),
                "Keine Kunden vorhanden",
                "cw.studentmanagementmodul.StudentsOverviewView.studentTableState"
                );
        tStudents.setSelectionModel(
                new SingleListSelectionAdapter(
                    new CWTableSelectionConverter(
                        getModel().getStudentSelection().getSelectionIndexHolder(),
                        tStudents)));

        getComponentContainer()
                .addComponent(tStudents);
    }

    public void buildView() {
    	super.buildView();

        this.setHeaderInfo(getModel().getHeaderInfo());

        FormLayout layout = new FormLayout(
                "fill:pref:grow",
                "fill:pref:grow"
        );
        
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        builder.add(CWComponentFactory.createScrollPane(tStudents), cc.xy(1, 1));
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
