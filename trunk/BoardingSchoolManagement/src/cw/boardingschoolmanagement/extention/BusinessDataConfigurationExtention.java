package cw.boardingschoolmanagement.extention;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import com.jgoodies.validation.ValidationResult;

import cw.boardingschoolmanagement.extention.point.IConfigurationExtentionPoint;
import cw.boardingschoolmanagement.gui.BusinessDataConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.BusinessDataConfigurationView;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationPresentationModel;
import cw.boardingschoolmanagement.gui.ConfigurationView;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import cw.boardingschoolmanagement.pojo.BusinessData;

/**
 *  
 * @author ManuelG
 */
public class BusinessDataConfigurationExtention
	implements IConfigurationExtentionPoint<ConfigurationView, ConfigurationPresentationModel> {

    private BusinessData businessData;
    private BusinessDataConfigurationPresentationModel model;
    private BusinessDataConfigurationView view;


    /**
     * Laed die Unternehmensdaten aus der Configdatei und speichert diese in
     * das zustaendige POJO(BusinessData).
     *
     * @param baseModel
     */
    public void initPresentationModel(ConfigurationPresentationModel baseModel) {
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

        model = new BusinessDataConfigurationPresentationModel(businessData, baseModel);
    }

    public CWView getView() {
        return view;
    }

    public CWIEditPresentationModel getModel() {
        return model;
    }

    public String getButtonName() {
        return "Unternehmen";
    }

    public Icon getButtonIcon() {
        return model.getHeaderInfo().getIcon();
    }

	@Override
	public void initView(ConfigurationView baseView) {
		view = new BusinessDataConfigurationView(model);
	}

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(ConfigurationView.class);
		list.add(ConfigurationPresentationModel.class);
		return list;
	}

}
