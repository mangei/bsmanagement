package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.AutoCompletion;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Guardian;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeListener;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerEditCustomerView
    implements Disposable {

    private EditCustomerEditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel mainPanel;
    private JPanel pActive;
    private JPanel pGender;
    private JTextField tfTitle;
    private JTextField tfForename;
    private JTextField tfForename2;
    private JTextField tfSurname;
    private JCheckBox cGuardianActive;
    private JPanel pGuardianGender;
    private JTextField tfGuardianTitle;
    private JTextField tfGuardianForename;
    private JTextField tfGuardianSurname;
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

    private PropertyChangeListener guardianActiveListener;
    private PostOfficeNumberAutoCompleteFire ponacf;
    private CityAutoCompleteFire cacf;
    private ProvinceAutoCompleteFire pacf;
    private AutoCompletion titleAutoCompletion;
    private AutoCompletion postOfficeNumberAutoCompletion;
    private AutoCompletion cityAutoCompletion;
    private AutoCompletion provinceAutoCompletion;
    private AutoCompletion countryAutoCompletion;

    public EditCustomerEditCustomerView(EditCustomerEditCustomerPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {

        pActive             = CWComponentFactory.createTrueFalsePanel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_ACTIVE), "Aktiv", "Inaktiv", model.getEditCustomerPresentationModel().getModel(Customer.PROPERTYNAME_ACTIVE).booleanValue());
        pGender             = CWComponentFactory.createTrueFalsePanel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_GENDER), "Herr", "Frau", model.getEditCustomerPresentationModel().getModel(Customer.PROPERTYNAME_GENDER).booleanValue());
        tfTitle             = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_TITLE), false);
        tfForename          = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME), false);
        tfForename2         = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME2), false);
        tfSurname           = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_SURNAME), false);

        dcBirthday          = CWComponentFactory.createDateChooser(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));

        cGuardianActive     = CWComponentFactory.createCheckBox(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_ACTIVE), "Dieser Kunde hat einen Erziehungsberechtigen:");
        pGuardianGender     = CWComponentFactory.createTrueFalsePanel(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_GENDER), "Herr", "Frau", model.getEditCustomerPresentationModel().getGuardianPresentationModel().getModel(Guardian.PROPERTYNAME_GENDER).booleanValue());
        tfGuardianTitle     = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_TITLE), false);
        tfGuardianForename  = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_FORENAME), false);
        tfGuardianSurname   = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_SURNAME), false);

        tfStreet            = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_STREET), false);
        tfPostOfficeNumber  = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER), false);
        tfCity              = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_CITY), false);
        tfCountry           = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COUNTRY), false);
        tfProvince          = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_PROVINCE), false);
        tfMobilphone        = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE), false);
        tfLandlinephone     = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE), false);
        tfFax               = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FAX), false);
        tfEmail             = CWComponentFactory.createTextField(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_EMAIL), false);
        taComment           = CWComponentFactory.createTextArea(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COMMENT), false);

        bClearLocationData = CWComponentFactory.createButton(model.getClearLocationDataAction());
        bClearLocationData.setIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear.png"));
        bClearLocationData.setRolloverIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear_hover.png"));
        bClearLocationData.setContentAreaFilled(false);
        bClearLocationData.setFocusPainted(false);
        bClearLocationData.setBorderPainted(false);


        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(pActive)
                .addComponent(pGender)
                .addComponent(tfTitle)
                .addComponent(tfForename)
                .addComponent(tfForename2)
                .addComponent(tfSurname)
                .addComponent(dcBirthday)
                .addComponent(cGuardianActive)
                .addComponent(pGuardianGender)
                .addComponent(tfGuardianTitle)
                .addComponent(tfGuardianForename)
                .addComponent(tfGuardianSurname)
                .addComponent(tfStreet)
                .addComponent(tfPostOfficeNumber)
                .addComponent(tfCity)
                .addComponent(tfCountry)
                .addComponent(tfProvince)
                .addComponent(tfMobilphone)
                .addComponent(tfLandlinephone)
                .addComponent(tfFax)
                .addComponent(tfEmail)
                .addComponent(taComment)
                .addComponent(bClearLocationData);
    }

    private void initEvents() {
        (titleAutoCompletion = new AutoCompletion(tfTitle, model.getTitleList())).setStrict(false);
        (postOfficeNumberAutoCompletion = new AutoCompletion(tfPostOfficeNumber, model.getPostOfficeNumberList())).setStrict(false);
        (cityAutoCompletion = new AutoCompletion(tfCity, model.getCityList())).setStrict(false);
        (provinceAutoCompletion = new AutoCompletion(tfProvince, model.getProvinceList())).setStrict(false);
        (countryAutoCompletion = new AutoCompletion(tfCountry, model.getCountryList())).setStrict(false);

        ponacf = new PostOfficeNumberAutoCompleteFire();
        cacf = new CityAutoCompleteFire();
        pacf = new ProvinceAutoCompleteFire();

        tfPostOfficeNumber.addFocusListener(ponacf);
        tfPostOfficeNumber.getDocument().addDocumentListener(ponacf);
        tfCity.addFocusListener(cacf);
        tfCity.getDocument().addDocumentListener(cacf);
        tfProvince.addFocusListener(pacf);
        tfProvince.getDocument().addDocumentListener(pacf);

        model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_ACTIVE).addValueChangeListener(guardianActiveListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                setGuardianComponentsActive((Boolean)evt.getNewValue());
            }
        });
        setGuardianComponentsActive((Boolean)model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_ACTIVE).getValue());
    }

    private void setGuardianComponentsActive(boolean b) {
        pGuardianGender.setEnabled(b);
        tfGuardianTitle.setEnabled(b);
        tfGuardianForename.setEnabled(b);
        tfGuardianSurname.setEnabled(b);
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
    
    public JPanel buildPanel() {
        initComponents();
        
        mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
//        mainPanel.getContentScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        mainPanel.getContentScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout,mainPanel.getContentPanel());

        int row = 1;
        row+=2;

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("<html><b>Allgemein</b></html>",  cc.xyw(1, row, 8));
        builder.addLabel("Anrede:",     cc.xy(1, row+=2));
        builder.add(pGender,                cc.xy(3, row));
        builder.add(pActive,                cc.xy(7, row));
        builder.addLabel("Titel:",          cc.xy(1, row+=2));
        builder.add(tfTitle,                cc.xy(3, row));
        builder.addLabel("Vorname:",        cc.xy(1, row+=2));
        builder.add(tfForename,             cc.xy(3, row));
        builder.addLabel("2. Vorname:",     cc.xy(5, row));
        builder.add(tfForename2,            cc.xy(7, row));
        builder.addLabel("Nachname",        cc.xy(1, row+=2));
        builder.add(tfSurname,              cc.xyw(3, row, 5));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, row+=2));
        builder.add(dcBirthday,             cc.xy(3, row));

        builder.addSeparator("<html><b>Erziehungsberechtigter</b></html>",    cc.xyw(1, row+=2, 8));
        builder.add(cGuardianActive,        cc.xyw(1, row+=2, 8));
        builder.addLabel("Anrede:",         cc.xy(1, row+=2));
        builder.add(pGuardianGender,        cc.xy(3, row));
        builder.addLabel("Titel:",          cc.xy(5, row));
        builder.add(tfGuardianTitle,        cc.xy(7, row));
        builder.addLabel("Vorname:",        cc.xy(1, row+=2));
        builder.add(tfGuardianForename,     cc.xy(3, row));
        builder.addLabel("Nachname:",       cc.xy(5, row));
        builder.add(tfGuardianSurname,      cc.xy(7, row));
        
        builder.addSeparator("<html><b>Adresse</b></html>",    cc.xyw(1, row+=2, 8));
        builder.addLabel("Straße:",         cc.xy(1, row+=2));
        builder.add(tfStreet,               cc.xyw(3, row, 5));
        builder.addLabel("PLZ:",            cc.xy(1, row+=2));
        builder.add(tfPostOfficeNumber,     cc.xy(3, row));
        builder.addLabel("Ort:",            cc.xy(5, row));
        builder.add(tfCity,                 cc.xy(7, row));
        builder.add(bClearLocationData,     cc.xywh(8, row, 1, 3, CellConstraints.LEFT, CellConstraints.CENTER));
        builder.addLabel("Bundesland:",     cc.xy(1, row+=2));
        builder.add(tfProvince,             cc.xy(3, row));
        builder.addLabel("Staat:",          cc.xy(5, row));
        builder.add(tfCountry,              cc.xy(7, row));

        
        builder.addSeparator("<html><b>Kontakt</b></html>",    cc.xyw(1, row+=2, 8));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, row+=2));
        builder.add(tfMobilphone,           cc.xyw(3, row, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, row+=2));
        builder.add(tfLandlinephone,        cc.xyw(3, row, 3));
        builder.addLabel("Fax:",            cc.xy(1, row+=2));
        builder.add(tfFax,                  cc.xyw(3, row, 3));
        builder.addLabel("eMail:",          cc.xy(1, row+=2));
        builder.add(tfEmail,                cc.xyw(3, row, 3));

        builder.addSeparator("<html><b>Bemerkung</b></html>",   cc.xyw(1, row+=2, 8));
        builder.add(taComment,              cc.xyw(1, row+=2, 8));

        initEvents();

        mainPanel.addDisposableListener(this);

//        componentContainer.addComponent(mainPanel);
        return mainPanel;
    }

    public void dispose() {

        mainPanel.removeDisposableListener(this);

        componentContainer.dispose();

        tfPostOfficeNumber.removeFocusListener(ponacf);
        tfPostOfficeNumber.getDocument().removeDocumentListener(ponacf);
        tfCity.removeFocusListener(cacf);
        tfCity.getDocument().removeDocumentListener(cacf);
        tfProvince.removeFocusListener(pacf);
        tfProvince.getDocument().removeDocumentListener(pacf);
        model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_ACTIVE).removeValueChangeListener(guardianActiveListener);

        titleAutoCompletion.uninstallListeners();
        postOfficeNumberAutoCompletion.uninstallListeners();
        cityAutoCompletion.uninstallListeners();
        provinceAutoCompletion.uninstallListeners();
        countryAutoCompletion.uninstallListeners();

        model.dispose();
    }
}
