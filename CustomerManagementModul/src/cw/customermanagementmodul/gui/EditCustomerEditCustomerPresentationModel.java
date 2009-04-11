package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerEditCustomerPresentationModel
        implements Disposable {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private HeaderInfo headerInfo;
    private Action clearLocationDataAction;

    private List<String> titleList;
    private List<String> postOfficeNumberList;
    private List<String> cityList;
    private List<String> provinceList;
    private List<String> countryList;
    
    public EditCustomerEditCustomerPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel) {
        this.editCustomerPresentationModel = editCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {

        clearLocationDataAction = new ClearLocationDataAction("");

        headerInfo = new HeaderInfo(
                "Allgemein",
                "Hier können sie allgemeine Kundeninformationen eingeben.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );

        titleList               = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_TITLE);
        postOfficeNumberList    = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_POSTOFFICENUMBER);
        cityList                = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_CITY);
        provinceList            = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_PROVINCE);
        countryList             = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_COUNTRY);

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

    public HeaderInfo getHeaderInfo() {
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

    public void save() {
    }

    public void firePostOfficeNumberLostFocus() {
        String postOfficeNumber = (String) editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).getValue();

        if(!postOfficeNumber.isEmpty()) {
            String city = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_CITY);
            String province = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_PROVINCE);
            String country = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_COUNTRY);

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
            String postOfficeNumber = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_POSTOFFICENUMBER);
            String province = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_PROVINCE);
            String country = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_COUNTRY);

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
            String country = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_PROVINCE, province, Customer.PROPERTYNAME_COUNTRY);

            if(country != null &&
                    ((String)editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
            }

//            country = (country == null) ? "" : country;
//
//            getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
        }
    }
}
