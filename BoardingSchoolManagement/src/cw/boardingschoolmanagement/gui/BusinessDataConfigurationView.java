package cw.boardingschoolmanagement.gui;

import javax.swing.JTextField;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import cw.boardingschoolmanagement.gui.component.CWComponentFactory;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.pojo.BusinessData;

/**
 *
 * @author ManuelG
 */
public class BusinessDataConfigurationView
	extends CWView<BusinessDataConfigurationPresentationModel>
{

    private JTextField tfName;
    private JTextField tfPostOfficeNumber;
    private JTextField tfCity;
    private JTextField tfStreet;
    private JTextField tfLandlinehone;
    private JTextField tfMobilephone;
    private JTextField tfFax;
    private JTextField tfEmail;
    private JTextField tfDvrNumber;

    public BusinessDataConfigurationView(BusinessDataConfigurationPresentationModel model) {
    	super(model);
    }

    public void initComponents() {
    	super.initComponents();
    	
        tfName              = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_NAME));
        tfPostOfficeNumber  = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_POSTOFFICENUMBER));
        tfCity              = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_CITY));
        tfStreet            = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_STREET));
        tfLandlinehone      = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_LANDLINEPHONE));
        tfMobilephone       = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_MOBILEPHONE));
        tfFax               = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_FAX));
        tfEmail             = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_EMAIL));
        tfDvrNumber         = CWComponentFactory.createTextField(getModel().getBufferedModel(BusinessData.PROPERTYNAME_DVRNUMBER));

        getComponentContainer()
                .addComponent(tfName)
                .addComponent(tfPostOfficeNumber)
                .addComponent(tfCity)
                .addComponent(tfStreet)
                .addComponent(tfLandlinehone)
                .addComponent(tfMobilephone)
                .addComponent(tfFax)
                .addComponent(tfEmail)
                .addComponent(tfDvrNumber);
    }

    public void buildView() {
    	super.buildView();
    	
        this.setHeaderInfo(getModel().getHeaderInfo());

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout);
        builder.addLabel("Name:",           cc.xy(1, 1));
        builder.add(tfName,                 cc.xy(3, 1));
        builder.addLabel("Straße:",         cc.xy(1, 3));
        builder.add(tfStreet,               cc.xy(3, 3));
        builder.addLabel("PLZ:",            cc.xy(1, 5));
        builder.add(tfPostOfficeNumber,     cc.xy(3, 5));
        builder.addLabel("Ort:",            cc.xy(1, 7));
        builder.add(tfCity,                 cc.xy(3, 7));
        builder.addLabel("Festnetztelefon:",cc.xy(1, 9));
        builder.add(tfLandlinehone,         cc.xy(3, 9));
        builder.addLabel("Mobiltelefon:",   cc.xy(1, 11));
        builder.add(tfMobilephone,          cc.xy(3, 11));
        builder.addLabel("Fax:",            cc.xy(1, 13));
        builder.add(tfFax,                  cc.xy(3, 13));
        builder.addLabel("Email:",          cc.xy(1, 15));
        builder.add(tfEmail,                cc.xy(3, 15));
        builder.addLabel("DVR-Nummer:",     cc.xy(1, 17));
        builder.add(tfDvrNumber,            cc.xy(3, 17));

        this.addToContentPanel(builder.getPanel());
        
        addToContentPanel(this);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
