package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerEditCustomerTabExtentionPresentationModel
        implements Disposable {

    private EditCustomerPresentationModel editCustomerPresentationModel;
    private ValueModel unsaved;
    private HeaderInfo headerInfo;
    private Action clearLocationDataAction;

    private List<String> titleList;
    private List<String> postOfficeNumberList;
    private List<String> cityList;
    private List<String> provinceList;
    private List<String> countryList;
    
    private PropertyChangeListener actionButtonListener;
    private SaveListener saveListener;

    public EditCustomerEditCustomerTabExtentionPresentationModel(EditCustomerPresentationModel editCustomerPresentationModel) {
        this.editCustomerPresentationModel = editCustomerPresentationModel;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        clearLocationDataAction = new ClearLocationDataAction("");

        headerInfo = new HeaderInfo(
                "Allgemeine Kundeninformationen",
                "Hier können sie allgemeine Kundeninformationen eingeben.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );


//        titleList               = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_TITLE);
//        postOfficeNumberList    = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_POSTOFFICENUMBER);
//        cityList                = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_CITY);
//        provinceList            = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_PROVINCE);
//        countryList             = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_COUNTRY);

        saveListener = new SaveListener();
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_ACTIVE).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_GENDER).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_TITLE).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FORENAME).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FORENAME2).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_SURNAME).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_STREET).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_CITY).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FAX).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_EMAIL).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COMMENT).addValueChangeListener(saveListener);
        editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).addValueChangeListener(saveListener);
    }

    public void initEventHandling() {
    }

    public void dispose() {
        if(actionButtonListener != null) {
            System.out.println("DIS...");
            unsaved.removeValueChangeListener(actionButtonListener);
            actionButtonListener = null;
        }
        if(saveListener != null) {
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_ACTIVE).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_GENDER).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_TITLE).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FORENAME).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FORENAME2).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_SURNAME).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_STREET).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_CITY).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_FAX).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_EMAIL).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_COMMENT).removeValueChangeListener(saveListener);
            editCustomerPresentationModel.getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).removeValueChangeListener(saveListener);
            saveListener = null;
        }

        clearLocationDataAction = null;

        titleList = null;
        postOfficeNumberList = null;
        cityList = null;
        provinceList = null;
        countryList = null;

        editCustomerPresentationModel = null;

        unsaved = null;
    }

    /**
     * Wenn sich ein Document ändert, wird saved auf false gesetzt
     */
    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            unsaved.setValue(true);
        }
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

    public boolean save() {
        return true;
    }

    public ValueModel getUnsaved() {
        return unsaved;
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
