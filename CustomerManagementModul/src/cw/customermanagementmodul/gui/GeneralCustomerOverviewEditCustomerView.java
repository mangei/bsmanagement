package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Guardian;
import java.awt.Font;

/**
 *
 * @author CreativeWorkers.at
 */
public class GeneralCustomerOverviewEditCustomerView extends CWPanel
{

    private GeneralCustomerOverviewEditCustomerPresentationModel model;

    private CWComponentFactory.CWComponentContainer componentContainer;
    private CWLabel lActive;
    private CWLabel lGender;
    private CWLabel lTitle;
    private CWLabel lForename;
    private CWLabel lForename2;
    private CWLabel lSurname;
    private CWLabel lBirthday;
    private CWLabel lGuardianForename;
    private CWLabel lGuardianSurname;
    private CWLabel lStreet;
    private CWLabel lPostOfficeNumber;
    private CWLabel lCity;
    private CWLabel lCountry;
    private CWLabel lProvince;
    private CWLabel lMobilphone;
    private CWLabel lLandlinephone;
    private CWLabel lFax;
    private CWLabel lEmail;
    private CWLabel lComment;

    public GeneralCustomerOverviewEditCustomerView(GeneralCustomerOverviewEditCustomerPresentationModel model) {
        this.model = model;

        initComponents();
        buildView();
        initEventHandling();
    }

    private void initComponents() {

        lActive            = CWComponentFactory.createLabelBoolean(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_ACTIVE), "Aktiv", "Inaktiv");
        lGender            = CWComponentFactory.createLabelBoolean(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_GENDER), "Herr", "Frau");
        lTitle             = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_TITLE));
        lForename          = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME));
        lForename2         = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME2));
        lSurname           = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_SURNAME));

        lBirthday          = CWComponentFactory.createLabelDate(model.getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));

        lGuardianForename  = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_FORENAME));
        lGuardianSurname   = CWComponentFactory.createLabel(model.getEditCustomerPresentationModel().getGuardianPresentationModel().getBufferedModel(Guardian.PROPERTYNAME_SURNAME));

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

        Font font = lActive.getFont().deriveFont(Font.BOLD);

        lActive.setFont(font);
        lGender.setFont(font);
        lTitle.setFont(font);
        lForename.setFont(font);
        lForename2.setFont(font);
        lSurname.setFont(font);
        lBirthday.setFont(font);

        lGuardianForename.setFont(font);
        lGuardianSurname.setFont(font);

        lStreet.setFont(font);
        lPostOfficeNumber.setFont(font);
        lCity.setFont(font);
        lCountry.setFont(font);
        lProvince.setFont(font);
        lMobilphone.setFont(font);
        lLandlinephone.setFont(font);
        lFax.setFont(font);
        lEmail.setFont(font);
        lComment.setFont(font);

        componentContainer = CWComponentFactory.createCWComponentContainer()
                .addComponent(lActive)
                .addComponent(lGender)
                .addComponent(lTitle)
                .addComponent(lForename)
                .addComponent(lForename2)
                .addComponent(lSurname)
                .addComponent(lBirthday)
                .addComponent(lGuardianForename)
                .addComponent(lGuardianSurname)
                .addComponent(lStreet)
                .addComponent(lPostOfficeNumber)
                .addComponent(lCity)
                .addComponent(lCountry)
                .addComponent(lProvince)
                .addComponent(lMobilphone)
                .addComponent(lLandlinephone)
                .addComponent(lFax)
                .addComponent(lEmail)
                .addComponent(lComment);
    }

    private void initEventHandling() {
    }

    private void buildView() {
//        mainPanel.setUI(new RoundPanelUI());
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout, this);

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein",  cc.xyw(1, 1, 8));
        builder.addLabel("Geschlecht:",     cc.xy(1, 3));
        builder.add(lGender,                cc.xy(3, 3));
        builder.addLabel("Status:",         cc.xy(5, 3));
        builder.add(lActive,                cc.xy(7, 3));
        builder.addLabel("Titel:",          cc.xy(1, 5));
        builder.add(lTitle,                cc.xy(3, 5));
        builder.addLabel("Vorname:",        cc.xy(1, 7));
        builder.add(lForename,             cc.xy(3, 7));
        builder.addLabel("2. Vorname:",     cc.xy(5, 7));
        builder.add(lForename2,            cc.xy(7, 7));
        builder.addLabel("Nachname",        cc.xy(1, 9));
        builder.add(lSurname,              cc.xyw(3, 9, 5));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, 11));
        builder.add(lBirthday,             cc.xy(3, 11));

        builder.addSeparator("Erziehungsberechtigter",    cc.xyw(1, 13, 8));
        builder.addLabel("Vorname:",        cc.xy(1, 15));
        builder.add(lGuardianForename,     cc.xy(3, 15));
        builder.addLabel("Nachname:",       cc.xy(5, 15));
        builder.add(lGuardianSurname,      cc.xy(7, 15));

        builder.addSeparator("Adresse",    cc.xyw(1, 17, 8));
        builder.addLabel("Straße:",         cc.xy(1, 19));
        builder.add(lStreet,               cc.xyw(3, 19, 5));
        builder.addLabel("PLZ:",            cc.xy(1, 21));
        builder.add(lPostOfficeNumber,     cc.xy(3, 21));
        builder.addLabel("Ort:",            cc.xy(5, 21));
        builder.add(lCity,                 cc.xy(7, 21));
        builder.addLabel("Bundesland:",     cc.xy(1, 23));
        builder.add(lProvince,             cc.xy(3, 23));
        builder.addLabel("Staat:",          cc.xy(5, 23));
        builder.add(lCountry,              cc.xy(7, 23));

        builder.addSeparator("Kontakt",    cc.xyw(1, 25, 8));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, 27));
        builder.add(lMobilphone,           cc.xyw(3, 27, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, 29));
        builder.add(lLandlinephone,        cc.xyw(3, 29, 3));
        builder.addLabel("Fax:",            cc.xy(1, 31));
        builder.add(lFax,                  cc.xyw(3, 31, 3));
        builder.addLabel("eMail:",          cc.xy(1, 33));
        builder.add(lEmail,                cc.xyw(3, 33, 3));

        builder.addSeparator("Bemerkung",   cc.xyw(1, 35, 8));
        builder.add(lComment,              cc.xyw(1, 37, 8));
    }

    public void dispose() {
        componentContainer.dispose();

        model.dispose();
    }
}
