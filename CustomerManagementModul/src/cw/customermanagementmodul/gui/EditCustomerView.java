package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import cw.customermanagementmodul.pojo.Customer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerView extends CWView
{

    private EditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JTabbedPane tabs;
    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    private PropertyChangeListener tabsEnableListener;

    public EditCustomerView(EditCustomerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventhandling();
    }

    private void initComponents() {
        bSave               = CWComponentFactory.createButton(model.getSaveAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelAction());
        bSaveCancel         = CWComponentFactory.createButton(model.getSaveCancelAction());

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel)
                .addComponent(bSaveCancel);
        
        tabs = new JTabbedPane();
    }

    private void initEventhandling() {
        // If it is a new Customer and there is no id, disable the tabs for the extentions
        // because the id is null
        // If the customer is saved, then enable the tabs
        if(model.getBean().getId() == null) {
            model.getBufferedModel(Customer.PROPERTYNAME_ID).addPropertyChangeListener(tabsEnableListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    setTabsEnabled(true);
                }
            });
            setTabsEnabled(false);
        }

        model.getUnsaved().setValue(false);
    }

    private void setTabsEnabled(boolean enabled) {
        for(int i=2, l=tabs.getComponentCount(); i<l; i++) {
            tabs.setEnabledAt(i, enabled);
        }
    }
    
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
//        mainPanel.getContentScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        mainPanel.getContentScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        CWButtonPanel buttonPanel = this.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bCancel);
        
        // Load dynamic components in tabs
        List<EditCustomerTabExtention> lEx = model.getExtentions();
        EditCustomerTabExtention ex;
        Class activeEx = (Class) model.getProperties().get("activeExtention");
        for(int i=0, l=lEx.size(); i<l; i++) {
            ex = lEx.get(i);
//            ex.getView().setPreferredSize(tabs.getSize());
//            ex.getView().setMaximumSize(tabs.getSize());
            tabs.addTab(ex.getView().getName(), ex.getView());
            if (activeEx != null && ex.getClass().equals(activeEx)) {
                tabs.setSelectedIndex(i);
            }
        }
        
        this.getContentPanel().add(tabs, BorderLayout.CENTER);
    }

    @Override
    public void dispose() {
        model.getBufferedModel(Customer.PROPERTYNAME_ID).removePropertyChangeListener(tabsEnableListener);
        tabsEnableListener = null;

        componentContainer.dispose();

        model.dispose();
    }
}
