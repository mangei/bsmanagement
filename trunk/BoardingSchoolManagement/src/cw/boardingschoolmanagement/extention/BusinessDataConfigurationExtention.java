package cw.boardingschoolmanagement.extention;

import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.Icon;

import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.BusinessDataConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.BusinessDataConfigurationView;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import cw.boardingschoolmanagement.pojo.BusinessData;

/**
 *  
 *
 *
 * @author ManuelG
 */
public class BusinessDataConfigurationExtention implements IConfigurationExtentionPoint {

    private BusinessData businessData;
    private BusinessDataConfigurationPresentationModel model;
    private BusinessDataConfigurationView view;


    /**
     * Laed die Unternehmensdaten aus der Configdatei und speichert diese in
     * das zustaendige POJO(BusinessData).
     *
     * @param configurationModel
     */
    public void initPresentationModel(ConfigurationPresentationModel configurationModel, EntityManager entityManager) {
        businessData = new BusinessData();

        businessData.setName(               PropertiesManager.getProperty("configuration.businessData.name",""));
        businessData.setStreet(             PropertiesManager.getProperty("configuration.businessData.street",""));
        businessData.setPostOfficeNumber(   PropertiesManager.getProperty("configuration.businessData.postOfficeNumber",""));
        businessData.setCity(               PropertiesManager.getProperty("configuration.businessData.city",""));
        businessData.setLandlinephone(      PropertiesManager.getProperty("configuration.businessData.landlinephone",""));
        businessData.setMobilephone(        PropertiesManager.getProperty("configuration.businessData.mobilephone",""));
        businessData.setFax(                PropertiesManager.getProperty("configuration.businessData.fax",""));
        businessData.setEmail(              PropertiesManager.getProperty("configuration.businessData.email",""));
        businessData.setDvrNumber(          PropertiesManager.getProperty("configuration.businessData.dvrNumber",""));

        model = new BusinessDataConfigurationPresentationModel(businessData, configurationModel, entityManager);
        view = new BusinessDataConfigurationView(model);
    }

    public CWPanel getView() {
        return view;
    }

    public Object getModel() {
        return model;
    }

    /**
     *  Speichert die Unternehmensdaten des BusinessData-POJOs ind die Config-Datei
     *
     */
    public void save() {
        model.save();

        PropertiesManager.setProperty("configuration.businessData.name",              businessData.getName());
        PropertiesManager.setProperty("configuration.businessData.street",            businessData.getStreet());
        PropertiesManager.setProperty("configuration.businessData.postOfficeNumber",  businessData.getPostOfficeNumber());
        PropertiesManager.setProperty("configuration.businessData.city",              businessData.getCity());
        PropertiesManager.setProperty("configuration.businessData.landlinephone",     businessData.getLandlinephone());
        PropertiesManager.setProperty("configuration.businessData.mobilephone",       businessData.getMobilephone());
        PropertiesManager.setProperty("configuration.businessData.fax",               businessData.getFax());
        PropertiesManager.setProperty("configuration.businessData.email",             businessData.getEmail());
        PropertiesManager.setProperty("configuration.businessData.dvrNumber",         businessData.getDvrNumber());
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
