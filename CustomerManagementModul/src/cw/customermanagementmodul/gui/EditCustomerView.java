package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.AutoCompletion;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import java.awt.BorderLayout;
import java.awt.event.FocusEvent;
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
import java.awt.event.FocusListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerView {

    private EditCustomerPresentationModel model;

    private JPanel pGender;
    private JTextField tfTitle;
    private JTextField tfForename;
    private JTextField tfForename2;
    private JTextField tfSurname;
    private JDateChooser dcBirthday;
    private JTextField tfStreet;
    private JTextField tfPostOfficeNumber;
    private JTextField tfCity;
    private JTextField tfCountry;
    private JTextField tfProvince;
    private JTextField tfMobilphone;
    private JTextField tfLandlinephone;
    private JTextField tfFax;
    private JTextField tfEmail;
    private JTextArea taComment;
    private JButton bClearLocationData;
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
        tfTitle             = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_TITLE), false);
        tfForename          = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_FORENAME), false);
        tfForename2         = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_FORENAME2), false);
        tfSurname           = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_SURNAME), false);

        dcBirthday          = CWComponentFactory.createDateChooser(model.getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));
        
        tfStreet            = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_STREET), false);
        tfPostOfficeNumber  = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER), false);
        tfCity              = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_CITY), false);
        tfCountry           = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_COUNTRY), false);
        tfProvince          = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_PROVINCE), false);
        tfMobilphone        = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE), false);
        tfLandlinephone     = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE), false);
        tfFax               = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_FAX), false);
        tfEmail             = CWComponentFactory.createTextField(model.getBufferedModel(Customer.PROPERTYNAME_EMAIL), false);
        taComment           = CWComponentFactory.createTextArea(model.getBufferedModel(Customer.PROPERTYNAME_COMMENT), false);

        bSave               = CWComponentFactory.createButton(model.getSaveButtonAction());
        bReset              = CWComponentFactory.createButton(model.getResetButtonAction());
        bCancel             = CWComponentFactory.createButton(model.getCancelButtonAction());
        bSaveCancel         = CWComponentFactory.createButton(model.getSaveCancelButtonAction());
        
        tabs = new JTabbedPane();

        bClearLocationData = CWComponentFactory.createButton(model.getClearLocationDataAction());
        bClearLocationData.setIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear.png"));
        bClearLocationData.setRolloverIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear_hover.png"));
        bClearLocationData.setContentAreaFilled(false);
        bClearLocationData.setFocusPainted(false);
        bClearLocationData.setBorderPainted(false);
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

        new AutoCompletion(tfTitle,             model.getTitleList())           .setStrict(false);
        new AutoCompletion(tfPostOfficeNumber,  model.getPostOfficeNumberList()).setStrict(false);
        new AutoCompletion(tfCity,              model.getCityList())            .setStrict(false);
        new AutoCompletion(tfProvince,          model.getProvinceList())        .setStrict(false);
        new AutoCompletion(tfCountry,           model.getCountryList())         .setStrict(false);

        PostOfficeNumberAutoCompleteFire ponacf = new PostOfficeNumberAutoCompleteFire();
        CityAutoCompleteFire cacf               = new CityAutoCompleteFire();
        ProvinceAutoCompleteFire pacf           = new ProvinceAutoCompleteFire();

        tfPostOfficeNumber.addFocusListener(ponacf);
        tfPostOfficeNumber.getDocument().addDocumentListener(ponacf);
        tfCity.addFocusListener(cacf);
        tfCity.getDocument().addDocumentListener(cacf);
        tfProvince.addFocusListener(pacf);
        tfProvince.getDocument().addDocumentListener(pacf);

    }

    private class PostOfficeNumberAutoCompleteFire implements FocusListener, DocumentListener {

        private boolean fireable;

        public void focusGained(FocusEvent e) {
            fireable = tfPostOfficeNumber.getDocument().getLength() == 0;
        }

        public void focusLost(FocusEvent e) {
            if(fireable) {
                model.firePostOfficeNumberLostFocus();
                fireable = false;
            }
        }

        public void insertUpdate(DocumentEvent e) { updateDocument(e); }

        public void removeUpdate(DocumentEvent e) { updateDocument(e); }

        public void changedUpdate(DocumentEvent e) { updateDocument(e); }

        private void updateDocument(DocumentEvent e) {
            if(e.getDocument().getLength() == 0) {
                fireable = true;
            }
        }

    }

    private class CityAutoCompleteFire implements FocusListener, DocumentListener {

        private boolean fireable;

        public void focusGained(FocusEvent e) {
            fireable = tfCity.getDocument().getLength() == 0;
        }

        public void focusLost(FocusEvent e) {
            if(fireable) {
                model.fireCityLostFocus();
                fireable = false;
            }
        }

        public void insertUpdate(DocumentEvent e) { updateDocument(e); }

        public void removeUpdate(DocumentEvent e) { updateDocument(e); }

        public void changedUpdate(DocumentEvent e) { updateDocument(e); }

        private void updateDocument(DocumentEvent e) {
            if(e.getDocument().getLength() == 0) {
                fireable = true;
            }
        }

    }

    private class ProvinceAutoCompleteFire implements FocusListener, DocumentListener {

        private boolean fireable;

        public void focusGained(FocusEvent e) {
            fireable = tfProvince.getDocument().getLength() == 0;
        }

        public void focusLost(FocusEvent e) {
            if(fireable) {
                model.fireProvinceLostFocus();
                fireable = false;
            }
        }

        public void insertUpdate(DocumentEvent e) { updateDocument(e); }

        public void removeUpdate(DocumentEvent e) { updateDocument(e); }

        public void changedUpdate(DocumentEvent e) { updateDocument(e); }

        private void updateDocument(DocumentEvent e) {
            if(e.getDocument().getLength() == 0) {
                fireable = true;
            }
        }

    }

    private void setTabsEnabled(boolean enabled) {
        for(int i=1, l=tabs.getComponentCount(); i<l; i++) {
            tabs.setEnabledAt(i, enabled);
        }
    }
    
    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel mainPanel = new JViewPanel(model.getHeaderInfo());
        
        JButtonPanel buttonPanel = mainPanel.getButtonPanel();
        
        buttonPanel.add(bSave);
        buttonPanel.add(bSaveCancel);
        buttonPanel.add(bReset);
        buttonPanel.add(bCancel);
        
        JViewPanel panel = new JViewPanel();
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout,panel.getContentPanel());
        
        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein:",  cc.xyw(1, 1, 8));
        builder.addLabel("Geschlecht:",     cc.xy(1, 3));
        builder.add(pGender,                cc.xy(3, 3));
        builder.addLabel("Titel:",          cc.xy(1, 5));
        builder.add(tfTitle,               cc.xy(3, 5));
        builder.addLabel("Vorname:",        cc.xy(1, 7));
        builder.add(tfForename,              cc.xy(3, 7));
        builder.addLabel("2. Vorname:",     cc.xy(5, 7));
        builder.add(tfForename2,             cc.xy(7, 7));
        builder.addLabel("Nachname",        cc.xy(1, 9));
        builder.add(tfSurname,             cc.xyw(3, 9, 5));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, 11));
        builder.add(dcBirthday,         cc.xy(3, 11));

        builder.addSeparator("Adresse:",    cc.xyw(1, 13, 8));
        builder.addLabel("Straße:",        cc.xy(1, 15));
        builder.add(tfStreet,              cc.xyw(3, 15, 5));
        builder.addLabel("PLZ:",            cc.xy(1, 17));
        builder.add(tfPostOfficeNumber,                  cc.xy(3, 17));
        builder.addLabel("Ort:",            cc.xy(5, 17));
        builder.add(tfCity,                  cc.xy(7, 17));
        builder.addLabel("Bundesland:",     cc.xy(1, 19));
        builder.add(tfProvince,           cc.xy(3, 19));
        builder.addLabel("Staat:",          cc.xy(5, 19));
        builder.add(tfCountry,                cc.xy(7, 19));

        builder.add(bClearLocationData, cc.xywh(8, 17, 1, 3, CellConstraints.LEFT, CellConstraints.CENTER));
        
        builder.addSeparator("Kontakt:",    cc.xyw(1, 21, 8));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, 23));
        builder.add(tfMobilphone,         cc.xyw(3, 23, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, 25));
        builder.add(tfLandlinephone,      cc.xyw(3, 25, 3));
        builder.addLabel("Fax:",            cc.xy(1, 27));
        builder.add(tfFax,                  cc.xyw(3, 27, 3));
        builder.addLabel("eMail:",          cc.xy(1, 29));
        builder.add(tfEmail,                cc.xyw(3, 29, 3));

        builder.addSeparator("Bemerkung",   cc.xyw(1, 31, 8));
        builder.add(taComment,            cc.xyw(1, 33, 8));

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
