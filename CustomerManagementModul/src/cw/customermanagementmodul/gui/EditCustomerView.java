package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerView {

    private EditCustomerPresentationModel model;

    private JPanel pGender;
    private JTextField tfAnrede;
    private JTextField tfVorname;
    private JTextField tfVorname2;
    private JTextField tfNachname;
    private JDateChooser dcGeburtsdatum;
    private JTextField tfStrasse;
    private JTextField tfPlz;
    private JTextField tfOrt;
    private JTextField tfStaat;
    private JTextField tfBundesland;
    private JTextField tfMobiltelefon;
    private JTextField tfFestnetztelefon;
    private JTextField tfFax;
    private JTextField tfEmail;
    private JTextArea taBemerkung;
    private JTabbedPane tabs;
    private JButton bSave;
    private JButton bReset;
    private JButton bCancel;
    private JButton bSaveCancel;
    
    public EditCustomerView(EditCustomerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {

        pGender             = CWComponentFactory.createTrueFalsePanel(model.getBufferedModel(Customer.PROPERTYNAME_GENDER), "Herr", "Frau", model.getModel(Customer.PROPERTYNAME_GENDER).booleanValue());
        tfAnrede            = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_TITLE), false);
        tfVorname           = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_FORENAME), false);
        tfVorname2          = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_FORENAME2), false);
        tfNachname          = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_SURNAME), false);

        dcGeburtsdatum      = CWComponentFactory.createDateChooser(model.getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));
        
        tfStrasse           = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_STREET), false);
        tfPlz               = CWComponentFactory.createIntegerTextField(model.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER),9);
        tfOrt               = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_CITY), false);
        tfStaat             = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_COUNTRY), false);
        tfBundesland        = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_PROVINCE), false);
        tfMobiltelefon      = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE), false);
        tfFestnetztelefon   = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE), false);
        tfFax               = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_FAX), false);
        tfEmail             = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_EMAIL), false);
        taBemerkung         = CWComponentFactory.createTextArea(model.getBufferedModel(Customer.PROPERTYNAME_COMMENT), false);

        bSave               = CWComponentFactory.createButton(model.getSaveButtonAction());
        bReset              = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel         = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        
        tabs = new JTabbedPane();
        
    }

    private void initEvents() {
        // If it is a new Customer and there is no id, disable the tabs for the extentions
        // because the id is null
        // If the customer is saved, then enable the tabs
        if(model.getBean().getId() == null) {
            model.getBufferedModel(Customer.PROPERTYNAME_ID).addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    setTabsEnabled(true);
                }
            });
            setTabsEnabled(false);
        }
    }

    private void setTabsEnabled(boolean enabled) {
        for(int i=1, l=tabs.getComponentCount(); i<l; i++) {
            tabs.setEnabledAt(i, enabled);
        }
    }
    
    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel mainPanel = new JViewPanel("Gruppe bearbeiten");
        mainPanel.setHeaderText(model.getHeaderText());
        
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);
        
        JViewPanel panel = new JViewPanel();
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout,panel.getContentPanel());
        
        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein:",  cc.xyw(1, 1, 7));
        builder.addLabel("Geschlecht:",     cc.xy(1, 3));
        builder.add(pGender,                cc.xy(3, 3));
        builder.addLabel("Titel:",          cc.xy(1, 5));
        builder.add(tfAnrede,               cc.xy(3, 5));
        builder.addLabel("Vorname:",        cc.xy(1, 7));
        builder.add(tfVorname,              cc.xy(3, 7));
        builder.addLabel("2. Vorname:",     cc.xy(5, 7));
        builder.add(tfVorname2,             cc.xy(7, 7));
        builder.addLabel("Nachname",        cc.xy(1, 9));
        builder.add(tfNachname,             cc.xyw(3, 9, 5));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, 11));
        builder.add(dcGeburtsdatum,         cc.xy(3, 11));

        builder.addSeparator("Adresse:",    cc.xyw(1, 13, 7));
        builder.addLabel("Straße:",        cc.xy(1, 15));
        builder.add(tfStrasse,              cc.xyw(3, 15, 5));
        builder.addLabel("PLZ:",            cc.xy(1, 17));
        builder.add(tfPlz,                  cc.xy(3, 17));
        builder.addLabel("Ort:",            cc.xy(5, 17));
        builder.add(tfOrt,                  cc.xy(7, 17));
        builder.addLabel("Bundesland:",     cc.xy(1, 19));
        builder.add(tfBundesland,           cc.xy(3, 19));
        builder.addLabel("Staat:",          cc.xy(5, 19));
        builder.add(tfStaat,                cc.xy(7, 19));
        
        builder.addSeparator("Kontakt:",    cc.xyw(1, 21, 7));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, 23));
        builder.add(tfMobiltelefon,         cc.xyw(3, 23, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, 25));
        builder.add(tfFestnetztelefon,      cc.xyw(3, 25, 3));
        builder.addLabel("Fax:",            cc.xy(1, 27));
        builder.add(tfFax,                  cc.xyw(3, 27, 3));
        builder.addLabel("eMail:",          cc.xy(1, 29));
        builder.add(tfEmail,                cc.xyw(3, 29, 3));

        builder.addSeparator("Bemerkung",   cc.xyw(1, 31, 7));
        builder.add(taBemerkung,            cc.xyw(1, 33, 7));

        tabs.addTab("Allgemein", panel);
        
        // Load dynamic components in tabs
        List<JComponent> lComps = model.getExtentionComponents();
        for(JComponent c : lComps) {
            tabs.addTab(c.getName(), c);
        }
        
        mainPanel.getContentPanel().add(tabs, BorderLayout.CENTER);
        
        initEvents();
        
        return mainPanel;
    }
}
