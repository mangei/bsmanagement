package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Guardian;
import java.awt.event.FocusListener;
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
                .addComponent(tfGuardianForename)
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
//        new AutoCompletion(tfTitle,             model.getTitleList())           .setStrict(false);
//        new AutoCompletion(tfPostOfficeNumber,  model.getPostOfficeNumberList()).setStrict(false);
//        new AutoCompletion(tfCity,              model.getCityList())            .setStrict(false);
//        new AutoCompletion(tfProvince,          model.getProvinceList())        .setStrict(false);
//        new AutoCompletion(tfCountry,           model.getCountryList())         .setStrict(false);

//        PostOfficeNumberAutoCompleteFire ponacf = new PostOfficeNumberAutoCompleteFire();
//        CityAutoCompleteFire cacf               = new CityAutoCompleteFire();
//        ProvinceAutoCompleteFire pacf           = new ProvinceAutoCompleteFire();

//        tfPostOfficeNumber.addFocusListener(ponacf);
//        tfPostOfficeNumber.getDocument().addDocumentListener(ponacf);
//        tfCity.addFocusListener(cacf);
//        tfCity.getDocument().addDocumentListener(cacf);
//        tfProvince.addFocusListener(pacf);
//        tfProvince.getDocument().addDocumentListener(pacf);

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
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout,mainPanel.getContentPanel());
        
        CellConstraints cc = new CellConstraints();
        builder.addSeparator("<html><b>Allgemein</b></html>",  cc.xyw(1, 1, 8));
        builder.addLabel("Geschlecht:",     cc.xy(1, 3));
        builder.add(pGender,                cc.xy(3, 3));
        builder.add(pActive,                cc.xy(7, 3));
        builder.addLabel("Titel:",          cc.xy(1, 5));
        builder.add(tfTitle,                cc.xy(3, 5));
        builder.addLabel("Vorname:",        cc.xy(1, 7));
        builder.add(tfForename,             cc.xy(3, 7));
        builder.addLabel("2. Vorname:",     cc.xy(5, 7));
        builder.add(tfForename2,            cc.xy(7, 7));
        builder.addLabel("Nachname",        cc.xy(1, 9));
        builder.add(tfSurname,              cc.xyw(3, 9, 5));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, 11));
        builder.add(dcBirthday,             cc.xy(3, 11));

        builder.addSeparator("<html><b>Erziehungsberechtigter</b></html>",    cc.xyw(1, 13, 8));
        builder.addLabel("Vorname:",        cc.xy(1, 15));
        builder.add(tfGuardianForename,     cc.xy(3, 15));
        builder.addLabel("Nachname:",       cc.xy(5, 15));
        builder.add(tfGuardianSurname,      cc.xy(7, 15));
        
        builder.addSeparator("<html><b>Adresse</b></html>",    cc.xyw(1, 17, 8));
        builder.addLabel("Straße:",         cc.xy(1, 19));
        builder.add(tfStreet,               cc.xyw(3, 19, 5));
        builder.addLabel("PLZ:",            cc.xy(1, 21));
        builder.add(tfPostOfficeNumber,     cc.xy(3, 21));
        builder.addLabel("Ort:",            cc.xy(5, 21));
        builder.add(tfCity,                 cc.xy(7, 21));
        builder.addLabel("Bundesland:",     cc.xy(1, 23));
        builder.add(tfProvince,             cc.xy(3, 23));
        builder.addLabel("Staat:",          cc.xy(5, 23));
        builder.add(tfCountry,              cc.xy(7, 23));

        builder.add(bClearLocationData,     cc.xywh(8, 21, 1, 3, CellConstraints.LEFT, CellConstraints.CENTER));
        
        builder.addSeparator("<html><b>Kontakt</b></html>",    cc.xyw(1, 25, 8));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, 27));
        builder.add(tfMobilphone,           cc.xyw(3, 27, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, 29));
        builder.add(tfLandlinephone,        cc.xyw(3, 29, 3));
        builder.addLabel("Fax:",            cc.xy(1, 31));
        builder.add(tfFax,                  cc.xyw(3, 31, 3));
        builder.addLabel("eMail:",          cc.xy(1, 33));
        builder.add(tfEmail,                cc.xyw(3, 33, 3));

        builder.addSeparator("<html><b>Bemerkung</b></html>",   cc.xyw(1, 35, 8));
        builder.add(taComment,              cc.xyw(1, 37, 8));

        initEvents();

        mainPanel.addDisposableListener(this);

//        componentContainer.addComponent(mainPanel);
        return mainPanel;
    }

    public void dispose() {

        mainPanel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}
