package cw.customermanagementmodul.customer.gui;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.AbstractAction;
import javax.swing.Action;

import com.jgoodies.common.base.Strings;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.ValidationUtils;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.customermanagementmodul.customer.persistence.PMCustomer;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerEditCustomerPresentationModel
	extends CWEditPresentationModel<Customer> {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private CWHeaderInfo headerInfo;
    private Action clearLocationDataAction;

    private List<String> titleList;
    private List<String> postOfficeNumberList;
    private List<String> cityList;
    private List<String> provinceList;
    private List<String> countryList;
    
    public EditCustomerEditCustomerPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel, EntityManager entityManager) {
        super(entityManager, EditCustomerEditCustomerView.class);
    	this.editCustomerPresentationModel = editCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {

        clearLocationDataAction = new ClearLocationDataAction("");

        headerInfo = new CWHeaderInfo(
                "Allgemein",
                "Hier koennen sie allgemeine Kundeninformationen eingeben.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );

        titleList               = PMCustomer.getInstance().getList(Customer.PROPERTYNAME_TITLE, getEntityManager());
        postOfficeNumberList    = PMCustomer.getInstance().getList(Customer.PROPERTYNAME_POSTOFFICENUMBER, getEntityManager());
        cityList                = PMCustomer.getInstance().getList(Customer.PROPERTYNAME_CITY, getEntityManager());
        provinceList            = PMCustomer.getInstance().getList(Customer.PROPERTYNAME_PROVINCE, getEntityManager());
        countryList             = PMCustomer.getInstance().getList(Customer.PROPERTYNAME_COUNTRY, getEntityManager());

    }

    public void initEventHandling() {
    }

    public void dispose() {
//        clearLocationDataAction = null;
//
//        titleList = null;
//        postOfficeNumberList = null;
//        cityList = null;
//        provinceList = null;
//        countryList = null;
//
//        editCustomerPresentationModel = null;
    }

    public Action getClearLocationDataAction() {
        return clearLocationDataAction;
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public List<String> getPostOfficeNumberList() {
        return postOfficeNumberList;
    }

    public List<String> getCityList() {
        return cityList;
    }

    public List<String> getProvinceList() {
        return provinceList;
    }

    public List<String> getCountryList() {
        return countryList;
    }

    public EditCustomerPresentationModel getEditCustomerPresentationModel() {
        return editCustomerPresentationModel;
    }

    private class ClearLocationDataAction extends AbstractAction {

        public ClearLocationDataAction(String name) {
            super(name);
        }

        {
            putValue(Action.SHORT_DESCRIPTION, "Ortsdaten leeren");
        }

        public void actionPerformed(ActionEvent e) {
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).setValue("");
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_CITY).setValue("");
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue("");
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue("");
        }

    }

    public void firePostOfficeNumberLostFocus() {
        String postOfficeNumber = (String) editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).getValue();

        if(!postOfficeNumber.isEmpty()) {
            String city = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_CITY, getEntityManager());
            String province = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_PROVINCE, getEntityManager());
            String country = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_COUNTRY, getEntityManager());

            if(city != null &&
                    ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_CITY).getValue()).isEmpty()) {
                editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_CITY).setValue(city);

                if(province != null &&
                        ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_PROVINCE).getValue()).isEmpty()) {
                    editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue(province);

                    if(country != null &&
                            ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
                    }
                }
            }
        }
    }

    public void fireCityLostFocus() {
        String city = (String) editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_CITY).getValue();

        if(!city.isEmpty()) {
            String postOfficeNumber = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_POSTOFFICENUMBER, getEntityManager());
            String province = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_PROVINCE, getEntityManager());
            String country = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_COUNTRY, getEntityManager());

            if(postOfficeNumber != null &&
                    ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).getValue()).isEmpty()) {
                editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).setValue(postOfficeNumber);

                if(province != null &&
                        ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_PROVINCE).getValue()).isEmpty()) {
                    editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue(province);

                    if(country != null &&
                            ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
                    }
                }
            }
//
//            postOfficeNumber    = (postOfficeNumber == null)    ? "" : postOfficeNumber;
//            province            = (province == null)            ? "" : province;
//            country             = (country == null)             ? "" : country;
//
//            getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).setValue(postOfficeNumber);
//            getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue(province);
//            getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
        }
    }

    public void fireProvinceLostFocus() {
        String province = (String) editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_PROVINCE).getValue();

        if(!province.isEmpty()) {
            String country = PMCustomer.getInstance().getResult(Customer.PROPERTYNAME_PROVINCE, province, Customer.PROPERTYNAME_COUNTRY, getEntityManager());

            if(country != null &&
                    ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
            }

//            country = (country == null) ? "" : country;
//
//            getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
        }
    }
	
	public ValidationResult validate() {
		
		ValidationResult validationResult = super.validate();
		
		if(Strings.isEmpty((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FORENAME).getValue())) {
			validationResult.addError("Vorname darf nicht leer sein.");
		}
		
		if(Strings.isEmpty((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_SURNAME).getValue())) {
			validationResult.addError("Nachname darf nicht leer sein.");
		}
		
		return validationResult;
	}
	
    public void save() {
    	super.save();
    }

	public void cancel() {
		super.cancel();
	}
}
