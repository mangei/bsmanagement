package cw.boardingschoolmanagement.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.validation.ValidationResult;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.PropertiesManager;
import cw.boardingschoolmanagement.pojo.BusinessData;

/**
 *
 * @author ManuelG
 * @version 1.0
 *
 */
public class BusinessDataConfigurationPresentationModel
    extends CWEditPresentationModel<BusinessData>
{

    private BusinessData businessData;
    private ConfigurationPresentationModel configurationPresentationModel;
    private CWHeaderInfo headerInfo;
    private SaveListener saveListener;

    /**
     *Konstruktor
     *
     * @param businessData POJO
     * @param configurationPresentationModel
     */
    public BusinessDataConfigurationPresentationModel(BusinessData businessData, ConfigurationPresentationModel baseModel) {
        super(businessData, baseModel.getEntityManager(), BusinessDataConfigurationView.class);
        this.businessData = businessData;
        this.configurationPresentationModel = configurationPresentationModel;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Unternehmensinformationen",
                "Geben Sie hier alle Informationen ueber Ihr Unternehmen ein.",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/businessdata_info.png"),
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/businessdata_info.png")
        );
    }

    private void initEventHandling() {
        saveListener = new SaveListener();
        getBufferedModel(BusinessData.PROPERTYNAME_NAME).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_POSTOFFICENUMBER).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_CITY).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_STREET).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_LANDLINEPHONE).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_MOBILEPHONE).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_FAX).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_EMAIL).addValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_DVRNUMBER).addValueChangeListener(saveListener);
    }

    public void dispose() {
        getBufferedModel(BusinessData.PROPERTYNAME_NAME).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_POSTOFFICENUMBER).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_CITY).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_STREET).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_LANDLINEPHONE).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_MOBILEPHONE).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_FAX).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_EMAIL).removeValueChangeListener(saveListener);
        getBufferedModel(BusinessData.PROPERTYNAME_DVRNUMBER).removeValueChangeListener(saveListener);
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            configurationPresentationModel.setChanged(true);
        }
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void save() {
    	super.save();
        triggerCommit();

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

	@Override
	public ValidationResult validate() {
		ValidationResult validationResult = super.validate();
		return validationResult;
	}

	@Override
	public void cancel() {
		super.cancel();
	}

}
