
package cw.roommanagementmodul.gui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import com.jgoodies.binding.adapter.SingleListSelectionAdapter;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.helper.CWTableSelectionConverter;

/**
 *
 * @author Dominik
 */
public class ZimmerView extends CWView{

    private ZimmerPresentationModel model;
    private CWButton bNew;
    private CWButton bEdit;
    private CWButton bDelete;
    private CWButton bBack;
    private CWButton bPrint;
    private CWTable tZimmer;
    private CWComponentFactory.CWComponentContainer componentContainer;

    public ZimmerView(ZimmerPresentationModel m) {
        this.model = m;
        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        bNew = CWComponentFactory.createButton(model.getNewAction());
        bNew.setText("Neu");
        bEdit = CWComponentFactory.createButton(model.getEditAction());
        bEdit.setText("Bearbeiten");
        bDelete = CWComponentFactory.createButton(model.getDeleteAction());
        bDelete.setText("Loeschen");

        bBack = CWComponentFactory.createButton(model.getBackAction());
        bBack.setText("Zurueck");
        bPrint = CWComponentFactory.createButton(model.getPrintAction());
        bPrint.setText("Drucken");


        String zimmerTableStateName = "cw.roommanagementmodul.ZimmerView.zimmerTableState";
        tZimmer = CWComponentFactory.createTable(model.createZimmerTableModel(model.getZimmerSelection()), "keine Zimmer vorhanden", zimmerTableStateName);


        tZimmer.setSelectionModel(new SingleListSelectionAdapter(new CWTableSelectionConverter(
                model.getZimmerSelection().getSelectionIndexHolder(),
                tZimmer)));

        componentContainer= CWComponentFactory.createComponentContainer();
        componentContainer.addComponent(bNew)
                .addComponent(bEdit)
                .addComponent(bDelete)
                .addComponent(bBack)
                .addComponent(bPrint)
                .addComponent(tZimmer);
    }

    private void initEventHandling() {
        tZimmer.addMouseListener(model.getDoubleClickHandler());
    }

    private void buildView() {

        this.setHeaderInfo(model.getHeaderInfo());
        this.getButtonPanel().add(bNew);
        this.getButtonPanel().add(bEdit);
        this.getButtonPanel().add(bDelete);
        this.getButtonPanel().add(bPrint);
        this.getButtonPanel().add(bBack);
        
        this.getContentPanel().add(new JScrollPane(tZimmer), BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        tZimmer.removeMouseListener(model.getDoubleClickHandler());
        componentContainer.dispose();
        model.dispose();
    }
}
