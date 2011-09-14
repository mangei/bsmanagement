package cw.customermanagementmodul.customer.gui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.swing.AutoCompletion;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWDateChooser;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWTextArea;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author Manuel Geier
 */
public class EditCustomerEditCustomerView 
	extends CWView<EditCustomerEditCustomerPresentationModel>
{

    private CWPanel pActive;
    private CWPanel pGender;
    private CWTextField tfTitle;
    private CWTextField tfForename;
    private CWTextField tfSurname;
    private CWDateChooser dcBirthday;
    private CWTextField tfStreet;
    private CWTextField tfPostOfficeNumber;
    private CWTextField tfCity;
    private CWTextField tfCountry;
    private CWTextField tfProvince;
    private CWTextField tfMobilphone;
    private CWTextField tfLandlinephone;
    private CWTextField tfFax;
    private CWTextField tfEmail;
    private CWTextArea taComment;
    private CWButton bClearLocationData;

    private PostOfficeNumberAutoCompleteFire ponacf;
    private CityAutoCompleteFire cacf;
    private ProvinceAutoCompleteFire pacf;
    private AutoCompletion titleAutoCompletion;
    private AutoCompletion postOfficeNumberAutoCompletion;
    private AutoCompletion cityAutoCompletion;
    private AutoCompletion provinceAutoCompletion;
    private AutoCompletion countryAutoCompletion;

    public EditCustomerEditCustomerView(EditCustomerEditCustomerPresentationModel model) {
        super(model, true);
    }

    public void initComponents() {
    	super.initComponents();

        pActive             = CWComponentFactory.createTrueFalsePanel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_ACTIVE), "Aktiv", "Inaktiv", getModel().getEditCustomerPresentationModel().getModel(Customer.PROPERTYNAME_ACTIVE).booleanValue());
        pGender             = CWComponentFactory.createTrueFalsePanel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_GENDER), "Herr", "Frau", getModel().getEditCustomerPresentationModel().getModel(Customer.PROPERTYNAME_GENDER).booleanValue());
        tfTitle             = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_TITLE), false);
        tfForename          = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME), false);
        tfSurname           = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_SURNAME), false);

        dcBirthday          = CWComponentFactory.createDateChooser(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));

        tfStreet            = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_STREET), false);
        tfPostOfficeNumber  = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER), false);
        tfCity              = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_CITY), false);
        tfCountry           = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COUNTRY), false);
        tfProvince          = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_PROVINCE), false);
        tfMobilphone        = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE), false);
        tfLandlinephone     = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE), false);
        tfFax               = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FAX), false);
        tfEmail             = CWComponentFactory.createTextField(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_EMAIL), false);
        taComment           = CWComponentFactory.createTextArea(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COMMENT), false);

        bClearLocationData = CWComponentFactory.createButton(getModel().getClearLocationDataAction());
        bClearLocationData.setIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear.png"));
        bClearLocationData.setRolloverIcon(CWUtils.loadIcon("cw/customermanagementmodul/images/clear_hover.png"));
        bClearLocationData.setContentAreaFilled(false);
        bClearLocationData.setFocusPainted(false);
        bClearLocationData.setBorderPainted(false);


        getComponentContainer()
                .addComponent(pActive)
                .addComponent(pGender)
                .addComponent(tfTitle)
                .addComponent(tfForename)
                .addComponent(tfSurname)
                .addComponent(dcBirthday)
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
        
        initEventHandling();
    }

    private void initEventHandling() {
        (titleAutoCompletion = new AutoCompletion(tfTitle, getModel().getTitleList())).setStrict(false);
        (postOfficeNumberAutoCompletion = new AutoCompletion(tfPostOfficeNumber, getModel().getPostOfficeNumberList())).setStrict(false);
        (cityAutoCompletion = new AutoCompletion(tfCity, getModel().getCityList())).setStrict(false);
        (provinceAutoCompletion = new AutoCompletion(tfProvince, getModel().getProvinceList())).setStrict(false);
        (countryAutoCompletion = new AutoCompletion(tfCountry, getModel().getCountryList())).setStrict(false);

        ponacf = new PostOfficeNumberAutoCompleteFire();
        cacf = new CityAutoCompleteFire();
        pacf = new ProvinceAutoCompleteFire();

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
            	getModel().firePostOfficeNumberLostFocus();
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
            	getModel().fireCityLostFocus();
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
            	getModel().fireProvinceLostFocus();
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
    
    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());
//        mainPanel.getContentScrollPane().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//        mainPanel.getContentScrollPane().setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout);

        int row = 1;
        row+=2;

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("<html><b>Allgemein</b></html>",  cc.xyw(1, row, 8));
        builder.addLabel("Anrede:",     cc.xy(1, row+=2));
        builder.add(pGender,                cc.xy(3, row));
        builder.add(pActive,                cc.xy(7, row));
        builder.addLabel("Titel:",          cc.xy(1, row+=2));
        builder.add(tfTitle,                cc.xy(3, row));
        builder.addLabel("Nachname",        cc.xy(1, row+=2));
        builder.add(tfSurname,              cc.xy(3, row));
        builder.addLabel("Vorname:",        cc.xy(5, row));
        builder.add(tfForename,             cc.xy(7, row));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, row+=2));
        builder.add(dcBirthday,             cc.xy(3, row));
        
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
        
        addToContentPanel(builder.getPanel());
    }

    @Override
    public void dispose() {
        tfPostOfficeNumber.removeFocusListener(ponacf);
        tfPostOfficeNumber.getDocument().removeDocumentListener(ponacf);
        ponacf = null;
        tfCity.removeFocusListener(cacf);
        tfCity.getDocument().removeDocumentListener(cacf);
        cacf = null;
        tfProvince.removeFocusListener(pacf);
        tfProvince.getDocument().removeDocumentListener(pacf);
        pacf = null;
        
        titleAutoCompletion.uninstallListeners();
        postOfficeNumberAutoCompletion.uninstallListeners();
        cityAutoCompletion.uninstallListeners();
        provinceAutoCompletion.uninstallListeners();
        countryAutoCompletion.uninstallListeners();

        super.dispose();
    }
}