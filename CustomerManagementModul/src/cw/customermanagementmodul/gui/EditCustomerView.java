package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerTabExtention;
import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import cw.customermanagementmodul.pojo.Customer;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerView
    implements Disposable {

    private EditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JTabbedPane tabs;
    private JButton bSave;
    private JButton bCancel;
    private JButton bSaveCancel;

    private PropertyChangeListener tabsEnableListener;

    public EditCustomerView(EditCustomerPresentationModel model) {
        this.model = model;
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
    
    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
//        mainPanel.getContentScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        mainPanel.getContentScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();
        
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
        
        mainPanel.getContentPanel().add(tabs, BorderLayout.CENTER);
        
        initEventhandling();

        mainPanel.addDisposableListener(this);

//        componentContainer.addComponent(mainPanel);
        return mainPanel;
    }

    public void dispose() {
        model.getBufferedModel(Customer.PROPERTYNAME_ID).removePropertyChangeListener(tabsEnableListener);

        componentContainer.dispose();

        model.dispose();
    }
}
