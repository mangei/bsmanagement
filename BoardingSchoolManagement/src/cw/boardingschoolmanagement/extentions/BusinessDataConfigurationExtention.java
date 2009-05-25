package cw.boardingschoolmanagement.extentions;

import cw.boardingschoolmanagement.extentions.interfaces.ConfigurationExtention;
import cw.boardingschoolmanagement.gui.BusinessDataPresentationModel;
import cw.boardingschoolmanagement.gui.BusinessDataView;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import cw.boardingschoolmanagement.pojo.BusinessData;
import java.util.List;
import javax.swing.Icon;

/**
 *
 * @author ManuelG
 */
public class BusinessDataConfigurationExtention implements ConfigurationExtention {

    private BusinessData businessData;
    private BusinessDataPresentationModel model;
    private BusinessDataView view;

    public void initPresentationModel(ConfigurationPresentationModel configurationModel) {
        businessData = new BusinessData();

        businessData.setName(               PropertiesManager.getProperty("businessData.name",""));
        businessData.setPostOfficeNumber(   PropertiesManager.getProperty("businessData.postOfficeNumber",""));
        businessData.setCity(               PropertiesManager.getProperty("businessData.city",""));
        businessData.setLandlinephone(      PropertiesManager.getProperty("businessData.landlinephone",""));
        businessData.setMobilephone(        PropertiesManager.getProperty("businessData.mobilephone",""));
        businessData.setFax(                PropertiesManager.getProperty("businessData.fax",""));
        businessData.setEmail(              PropertiesManager.getProperty("businessData.email",""));
        businessData.setDvrNumber(          PropertiesManager.getProperty("businessData.dvrNumber",""));

        model = new BusinessDataPresentationModel(businessData, configurationModel);
        view = new BusinessDataView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public Object getModel() {
        return model;
    }

    public void save() {
        model.save();

        PropertiesManager.setProperty("businessData.name",              businessData.getName());
        PropertiesManager.setProperty("businessData.postOfficeNumber",  businessData.getPostOfficeNumber());
        PropertiesManager.setProperty("businessData.city",              businessData.getCity());
        PropertiesManager.setProperty("businessData.landlinephone",     businessData.getLandlinephone());
        PropertiesManager.setProperty("businessData.mobilephone",       businessData.getMobilephone());
        PropertiesManager.setProperty("businessData.fax",               businessData.getFax());
        PropertiesManager.setProperty("businessData.email",             businessData.getEmail());
        PropertiesManager.setProperty("businessData.dvrNumber",         businessData.getDvrNumber());
    }

    public List<String> validate() {
        return model.validate();
    }

    public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }

    public String getButtonName() {
        return "Unternehmen";
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

}
