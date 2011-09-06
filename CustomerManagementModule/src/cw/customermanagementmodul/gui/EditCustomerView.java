package cw.customermanagementmodul.gui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import com.jidesoft.swing.JideTabbedPane;

import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWButtonPanel;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.persistence.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerView extends CWView
{

    private EditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JideTabbedPane tabs;
    private CWButton bSave;
    private CWButton bCancel;

    private PropertyChangeListener tabsEnableListener;

    public EditCustomerView(EditCustomerPresentationModel model) {
        super(false);
        this.model = model;

        initComponents();
        buildView();
        initEventhandling();
    }

    private void initComponents() {
        bSave               = CWComponentFactory.createButton(model.getSaveAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelAction());

        componentContainer = CWComponentFactory.createComponentContainer()
                .addComponent(bSave)
                .addComponent(bCancel);

        tabs = new JideTabbedPane();
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
        for(int i=2, l=tabs.getTabCount(); i<l; i++) {
            tabs.setEnabledAt(i, enabled);
        }
    }
    
    private void buildView() {
        this.setHeaderInfo(model.getHeaderInfo());
//        mainPanel.getContentScrollPane().setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//        mainPanel.getContentScrollPane().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        
        CWButtonPanel buttonPanel = this.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bCancel);
        
        // Load dynamic components in tabs
        List<EditCustomerTabExtentionPoint> lEx = model.getExtentions();
        EditCustomerTabExtentionPoint ex;
        Class activeEx = (Class) model.getProperties().get("activeExtention");
        for(int i=0, l=lEx.size(); i<l; i++) {
            ex = lEx.get(i);
//            ex.getView().setPreferredSize(tabs.getSize());
//            ex.getView().setMaximumSize(tabs.getSize());
            tabs.addTab(ex.getView().getName(), ex.getView());

            // Set the icon if it an instance of CWView
            if(ex.getView() instanceof CWView) {
                tabs.setIconAt(i, ((CWView) ex.getView()).getHeaderInfo().getIcon());
            }
            
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
