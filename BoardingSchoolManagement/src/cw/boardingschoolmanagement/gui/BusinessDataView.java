package cw.boardingschoolmanagement.gui;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.app.CWComponentFactory;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.pojo.BusinessData;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ManuelG
 */
public class BusinessDataView
    implements Disposable
{

    private BusinessDataPresentationModel model;
    private CWComponentFactory.CWComponentContainer componentContainer;
    private JViewPanel panel;
    private JTextField tfName;
    private JTextField tfPostOfficeNumber;
    private JTextField tfCity;
    private JTextField tfStreet;
    private JTextField tfLandlinehone;
    private JTextField tfMobilephone;
    private JTextField tfFax;
    private JTextField tfEmail;
    private JTextField tfDvrNumber;

    public BusinessDataView(BusinessDataPresentationModel model) {
        this.model = model;
    }

    public void initComponents() {
        tfName              = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_NAME));
        tfPostOfficeNumber  = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_POSTOFFICENUMBER));
        tfCity              = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_CITY));
        tfStreet            = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_STREET));
        tfLandlinehone      = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_LANDLINEPHONE));
        tfMobilephone       = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_MOBILEPHONE));
        tfFax               = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_FAX));
        tfEmail             = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_EMAIL));
        tfDvrNumber         = CWComponentFactory.createTextField(model.getBufferedModel(BusinessData.PROPERTYNAME_DVRNUMBER));

        componentContainer = CWComponentFactory.createCWComponentContainer()
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

    public void initEventHandling() {
        
    }

    public JViewPanel buildPanel() {
        initComponents();

        panel = CWComponentFactory.createViewPanel(model.getHeaderInfo());

        FormLayout layout = new FormLayout(
                "right:pref, 4dlu, pref:grow",
                "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu, pref, 4dlu"
        );
        CellConstraints cc = new CellConstraints();
        PanelBuilder builder = new PanelBuilder(layout, panel.getContentPanel());
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

        panel.addDisposableListener(this);

        initEventHandling();

        return panel;
    }

    public void dispose() {
        panel.removeDisposableListener(this);

        componentContainer.dispose();

        model.dispose();
    }
}
