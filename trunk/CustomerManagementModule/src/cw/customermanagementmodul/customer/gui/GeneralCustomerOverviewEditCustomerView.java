package cw.customermanagementmodul.customer.gui;

import java.awt.Font;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 *
 * @author CreativeWorkers.at
 */
public class GeneralCustomerOverviewEditCustomerView
	extends CWView<GeneralCustomerOverviewEditCustomerPresentationModel>
{

    private CWLabel lActive;
    private CWLabel lGender;
    private CWLabel lTitle;
    private CWLabel lForename;
    private CWLabel lSurname;
    private CWLabel lBirthday;
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
        super(model);
    }

    public void initComponents() {
    	super.initComponents();

        lActive            = CWComponentFactory.createLabelBoolean(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_ACTIVE), "Aktiv", "Inaktiv");
        lGender            = CWComponentFactory.createLabelBoolean(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_GENDER), "Herr", "Frau");
        lTitle             = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_TITLE));
        lForename          = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FORENAME));
        lSurname           = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_SURNAME));

        lBirthday          = CWComponentFactory.createLabelDate(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY));

        lStreet            = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_STREET));
        lPostOfficeNumber  = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER));
        lCity              = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_CITY));
        lCountry           = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COUNTRY));
        lProvince          = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_PROVINCE));
        lMobilphone        = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE));
        lLandlinephone     = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE));
        lFax               = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_FAX));
        lEmail             = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_EMAIL));
        lComment           = CWComponentFactory.createLabel(getModel().getEditCustomerPresentationModel().getBufferedModel(Customer.PROPERTYNAME_COMMENT));

        Font font = lActive.getFont().deriveFont(Font.BOLD);

        lActive.setFont(font);
        lGender.setFont(font);
        lTitle.setFont(font);
        lForename.setFont(font);
        lSurname.setFont(font);
        lBirthday.setFont(font);

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

        getComponentContainer()
                .addComponent(lActive)
                .addComponent(lGender)
                .addComponent(lTitle)
                .addComponent(lForename)
                .addComponent(lSurname)
                .addComponent(lBirthday)
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

    public void buildView() {
    	super.buildView();
    	
//        mainPanel.setUI(new RoundPanelUI());
        
        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, 100dlu, 4dlu, right:pref, 4dlu, 100dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref");

        PanelBuilder builder = new PanelBuilder(layout, this);

        int row = 1;
        row+=2;

        CellConstraints cc = new CellConstraints();
        builder.addSeparator("Allgemein",   cc.xyw(1, row, 8));
        builder.addLabel("Geschlecht:",     cc.xy(1, row+=2));
        builder.add(lGender,                cc.xy(3, row));
        builder.addLabel("Status:",         cc.xy(5, row));
        builder.add(lActive,                cc.xy(7, row));
        builder.addLabel("Titel:",          cc.xy(1, row+=2));
        builder.add(lTitle,                 cc.xy(3, row));
        builder.addLabel("Nachname",        cc.xy(1, row+=2));
        builder.add(lSurname,               cc.xy(3, row));
        builder.addLabel("Vorname:",        cc.xy(5, row));
        builder.add(lForename,              cc.xy(7, row));
        builder.addLabel("Geburtsdatum:",   cc.xy(1, row+=2));
        builder.add(lBirthday,              cc.xy(3, row));

        builder.addSeparator("Adresse",     cc.xyw(1, row+=2, 8));
        builder.addLabel("Straße:",         cc.xy(1, row+=2));
        builder.add(lStreet,                cc.xyw(3, row, 5));
        builder.addLabel("PLZ:",            cc.xy(1, row+=2));
        builder.add(lPostOfficeNumber,      cc.xy(3, row));
        builder.addLabel("Ort:",            cc.xy(5, row));
        builder.add(lCity,                  cc.xy(7, row));
        builder.addLabel("Bundesland:",     cc.xy(1, row+=2));
        builder.add(lProvince,              cc.xy(3, row));
        builder.addLabel("Staat:",          cc.xy(5, row));
        builder.add(lCountry,               cc.xy(7, row));

        builder.addSeparator("Kontakt",     cc.xyw(1, row+=2, 8));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, row+=2));
        builder.add(lMobilphone,            cc.xyw(3, row, 3));
        builder.addLabel("Festnetztelefon", cc.xy(1, row+=2));
        builder.add(lLandlinephone,         cc.xyw(3, row, 3));
        builder.addLabel("Fax:",            cc.xy(1, row+=2));
        builder.add(lFax,                   cc.xyw(3, row, 3));
        builder.addLabel("eMail:",          cc.xy(1, row+=2));
        builder.add(lEmail,                 cc.xyw(3, row, 3));

        builder.addSeparator("Bemerkung",   cc.xyw(1, row+=2, 8));
        builder.add(lComment,               cc.xyw(1, row+=2, 8));
    }

    public void dispose() {
        super.dispose();
    }
}
