package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JButtonPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.interfaces.Disposable;
import javax.swing.JPanel;
import cw.customermanagementmodul.pojo.Customer;
import javax.swing.JLabel;

/**
 *
 * @author CreativeWorkers.at
 */
public class CustomerOverviewEditCustomerTabExtentionView
    implements Disposable {

    private CustomerOverviewEditCustomerTabExtentionPresentationModel model;

    private JLabel lActive;
    private JLabel lGender;
    private JLabel lTitle;
    private JLabel lForename;
    private JLabel lForename2;
    private JLabel lSurname;
    private JLabel lBirthday;
    private JLabel lStreet;
    private JLabel lPostOfficeNumber;
    private JLabel lCity;
    private JLabel lCountry;
    private JLabel lProvince;
    private JLabel lMobilphone;
    private JLabel lLandlinephone;
    private JLabel lFax;
    private JLabel lEmail;
    private JLabel lComment;

    public CustomerOverviewEditCustomerTabExtentionView(CustomerOverviewEditCustomerTabExtentionPresentationModel model) {
        this.model = model;
    }

    private void initComponents() {

        lActive            = CWComponentFactory.createLabelBoolean(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_ACTIVE), "Aktiv", "Inaktiv");
        lGender            = CWComponentFactory.createLabelBoolean(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_GENDER), "Herr", "Frau");
        lTitle             = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_TITLE));
        lForename          = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME));
        lForename2         = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME2));
        lSurname           = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_SURNAME));

        lBirthday          = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));
        
        lStreet            = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_STREET));
        lPostOfficeNumber  = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER));
        lCity              = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_CITY));
        lCountry           = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COUNTRY));
        lProvince          = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_PROVINCE));
        lMobilphone        = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE));
        lLandlinephone     = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE));
        lFax               = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FAX));
        lEmail             = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_EMAIL));
        lComment           = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COMMENT));

    }

    private void initEvents() {
    }

    public JPanel buildPanel() {
        initComponents();
        
        JViewPanel mainPanel = CWComponentFactory.createViewPanel(model.getHeaderInfo());
        mainPanel.setName("Übersicht");
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout,mainPanel.getContentPanel());
        
        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein:",  cc.xyw(1, 1, 8));
        builder.addLabel("Geschlecht:",     cc.xy(1, 3));
        builder.add(lGender,                cc.xy(3, 3));
        builder.addLabel("Status",          cc.xy(5, 3));
        builder.add(lActive,                cc.xy(7, 3));
        builder.addLabel("Titel:",          cc.xy(1, 5));
        builder.add(lTitle,                 cc.xy(3, 5));
        builder.addLabel("Vorname:",        cc.xy(1, 7));
        builder.add(lForename,              cc.xy(3, 7));
        builder.addLabel("2. Vorname:",     cc.xy(5, 7));
        builder.add(lForename2,             cc.xy(7, 7));
        builder.addLabel("Nachname",        cc.xy(1, 9));
        builder.add(lSurname,               cc.xyw(3, 9, 5));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, 11));
        builder.add(lBirthday,              cc.xy(3, 11));

        builder.addSeparator("Adresse:",    cc.xyw(1, 13, 8));
        builder.addLabel("Straße:",         cc.xy(1, 15));
        builder.add(lStreet,               cc.xyw(3, 15, 5));
        builder.addLabel("PLZ:",            cc.xy(1, 17));
        builder.add(lPostOfficeNumber,     cc.xy(3, 17));
        builder.addLabel("Ort:",            cc.xy(5, 17));
        builder.add(lCity,                 cc.xy(7, 17));
        builder.addLabel("Bundesland:",     cc.xy(1, 19));
        builder.add(lProvince,             cc.xy(3, 19));
        builder.addLabel("Staat:",          cc.xy(5, 19));
        builder.add(lCountry,              cc.xy(7, 19));

        builder.addSeparator("Kontakt:",    cc.xyw(1, 21, 8));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, 23));
        builder.add(lMobilphone,           cc.xyw(3, 23, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, 25));
        builder.add(lLandlinephone,        cc.xyw(3, 25, 3));
        builder.addLabel("Fax:",            cc.xy(1, 27));
        builder.add(lFax,                  cc.xyw(3, 27, 3));
        builder.addLabel("eMail:",          cc.xy(1, 29));
        builder.add(lEmail,                cc.xyw(3, 29, 3));

        builder.addSeparator("Bemerkung",   cc.xyw(1, 31, 8));
        builder.add(lComment,              cc.xyw(1, 33, 8));

        initEvents();

        mainPanel.addDisposableListener(this);

        return mainPanel;
    }

    public void dispose() {

        model.dispose();
        model = null;
    }
}
