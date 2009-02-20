package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import cw.customermanagementmodul.extentions.interfaces.EditCustomerGUITabExtention;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import javax.swing.Icon;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditCustomerPresentationModel
        extends PresentationModel<Customer> {

    private Customer customer;
    private ValueModel unsaved;
    private HeaderInfo headerInfo;
    private HeaderInfo generalHeaderInfo;
    private Action resetButtonAction;
    private Action saveButtonAction;
    private Action cancelButtonAction;
    private Action saveCancelButtonAction;
    private Action clearLocationDataAction;
    private ButtonListenerSupport support;

    private List<String> titleList;
    private List<String> postOfficeNumberList;
    private List<String> cityList;
    private List<String> provinceList;
    private List<String> countryList;
    
    private List<EditCustomerGUITabExtention> editCustomerGUITabExtentions;

    public EditCustomerPresentationModel(Customer customer) {
        this(customer, new HeaderInfo());
    }

    public EditCustomerPresentationModel(Customer customer, HeaderInfo headerInfo) {
        super(customer);
        this.customer = customer;
        this.headerInfo = headerInfo;

        initModels();
        initEventHandling();
    }

    public void initModels() {
        unsaved = new ValueHolder();

        // Addons initialisieren
        editCustomerGUITabExtentions = getExtentions();
        for (EditCustomerGUITabExtention extention : editCustomerGUITabExtentions) {
            System.out.println("Extention: " + extention.toString());
            extention.initPresentationModel(customer, this);
        }

        saveButtonAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/customermanagementmodul/images/disk_16.png"));
        resetButtonAction = new ResetAction("Zurücksetzen", CWUtils.loadIcon("cw/customermanagementmodul/images/arrow_rotate_anticlockwise.png"));
        cancelButtonAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/customermanagementmodul/images/cancel.png"));
        saveCancelButtonAction = new SaveCancelAction("Speichern u. Schließen", CWUtils.loadIcon("cw/customermanagementmodul/images/save_cancel.png"));

        clearLocationDataAction = new ClearLocationDataAction("");

        support = new ButtonListenerSupport();

        generalHeaderInfo = new HeaderInfo(
                "Allgemeine Kundeninformationen",
                "Hier können sie allgemeine Kundeninformationen eingeben.",
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png"),
                CWUtils.loadIcon("cw/customermanagementmodul/images/user.png")
        );


        titleList               = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_TITLE);
        postOfficeNumberList    = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_POSTOFFICENUMBER);
        cityList                = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_CITY);
        provinceList            = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_PROVINCE);
        countryList             = CustomerManager.getInstance().getList(Customer.PROPERTYNAME_COUNTRY);

        SaveListener saveListener = new SaveListener();
        getBufferedModel(Customer.PROPERTYNAME_ACTIVE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_GENDER).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_TITLE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FORENAME).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FORENAME2).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_SURNAME).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_STREET).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_CITY).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_MOBILEPHONE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_LANDLINEPHONE).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_FAX).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_EMAIL).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_COMMENT).addValueChangeListener(saveListener);
        getBufferedModel(Customer.PROPERTYNAME_BIRTHDAY).addValueChangeListener(saveListener);
    }

    public void initEventHandling() {
        unsaved.addValueChangeListener(new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                if ((Boolean) evt.getNewValue() == true) {
                    saveButtonAction.setEnabled(true);
                    resetButtonAction.setEnabled(true);
                    saveCancelButtonAction.setEnabled(true);
                } else {
                    saveButtonAction.setEnabled(false);
                    resetButtonAction.setEnabled(false);
                    saveCancelButtonAction.setEnabled(false);
                }
            }
        });
        unsaved.setValue(false);
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

    public List<JComponent> getExtentionComponents() {
        List<JComponent> comps = new ArrayList<JComponent>();
        for (EditCustomerGUITabExtention ex : editCustomerGUITabExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<EditCustomerGUITabExtention> getExtentions() {
        if(editCustomerGUITabExtentions == null) {
            editCustomerGUITabExtentions = (List<EditCustomerGUITabExtention>) ModulManager.getExtentions(EditCustomerGUITabExtention.class);
        }
        return editCustomerGUITabExtentions;
    }

    public EditCustomerGUITabExtention getExtention(EditCustomerGUITabExtention extention) {

        for(EditCustomerGUITabExtention ex : editCustomerGUITabExtentions) {
            if(extention.getClass().isInstance(ex)) {
                return ex;
            }
        }

        return null;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public Action getSaveButtonAction() {
        return saveButtonAction;
    }

    public Action getResetButtonAction() {
        return resetButtonAction;
    }

    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }

    public Action getSaveCancelButtonAction() {
        return saveCancelButtonAction;
    }

    public Action getClearLocationDataAction() {
        return clearLocationDataAction;
    }

    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public HeaderInfo getGeneralHeaderInfo() {
        return generalHeaderInfo;
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


    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class SaveAction
            extends AbstractAction {

        private SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            // Fire only when the save-method worked correct
            if(save()) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
            }

        }
    }

    private class ResetAction
            extends AbstractAction {

        private ResetAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = JOptionPane.showConfirmDialog(null, "Wollen Sie alle Änderungen verwerfen?");
            if (i == JOptionPane.OK_OPTION) {
                reset();
                unsaved.setValue(false);
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.RESET_BUTTON));
            }
        }
    }

    private class CancelAction
            extends AbstractAction {

        private CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = 1;
            if ((Boolean) unsaved.getValue() == true) {
                Object[] options = {"Speichern", "Nicht Speichern", "Abbrechen"};
                i = JOptionPane.showOptionDialog(null, "Daten wurden geändert. Wollen Sie die Änderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            if (i == 0) {

                // If the save-method doesn't worked, because of an error, to nothing
                if(!save()) {
                    return;
                }
            }
            if (i == 0 || i == 1) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }

    private class SaveCancelAction
            extends AbstractAction {

        private SaveCancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            if(!save()) {
                return;
            }

            support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_EXIT_BUTTON));
        }
    }

    private class ClearLocationDataAction extends AbstractAction {

        public ClearLocationDataAction(String name) {
            super(name);
        }

        {
            putValue(Action.SHORT_DESCRIPTION, "Ortsdaten leeren");
        }

        public void actionPerformed(ActionEvent e) {
            getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).setValue("");
            getBufferedModel(Customer.PROPERTYNAME_CITY).setValue("");
            getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue("");
            getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue("");
        }

    }


    public boolean save() {
        boolean valid = true;

        List<EditCustomerGUITabExtention> extentions = getExtentions();
        List<String> errorMessages = new ArrayList<String>();
        for (EditCustomerGUITabExtention extention : extentions) {
            if(!extention.validate()) {
                valid = false;
                if(extention.getErrorMessages() != null) {
                    errorMessages.addAll(extention.getErrorMessages());
                }
            }
        }

        if(!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for(String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        triggerCommit();

        for (EditCustomerGUITabExtention extention : extentions) {
            extention.save();
        }

        unsaved.setValue(false);

        return true;
    }

    private boolean validate() {
        return true;
    }

    private List<String> getErrorMessages() {
        return null;
    }

    public void reset() {

        List<EditCustomerGUITabExtention> extentions = getExtentions();
        for (EditCustomerGUITabExtention extention : extentions) {
            extention.reset();
        }

        triggerFlush();
    }

    public ValueModel getUnsaved() {
        return unsaved;
    }

    public void firePostOfficeNumberLostFocus() {
        String postOfficeNumber = (String) getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).getValue();

        if(!postOfficeNumber.isEmpty()) {
            String city = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_CITY);
            String province = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_PROVINCE);
            String country = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_POSTOFFICENUMBER, postOfficeNumber, Customer.PROPERTYNAME_COUNTRY);

            if(city != null &&
                    ((String)getBufferedModel(Customer.PROPERTYNAME_CITY).getValue()).isEmpty()) {
                getBufferedModel(Customer.PROPERTYNAME_CITY).setValue(city);

                if(province != null &&
                        ((String)getBufferedModel(Customer.PROPERTYNAME_PROVINCE).getValue()).isEmpty()) {
                    getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue(province);

                    if(country != null &&
                            ((String)getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                        getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
                    }
                }
            }
        }
    }

    public void fireCityLostFocus() {
        String city = (String) getBufferedModel(Customer.PROPERTYNAME_CITY).getValue();

        if(!city.isEmpty()) {
            String postOfficeNumber = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_POSTOFFICENUMBER);
            String province = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_PROVINCE);
            String country = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_CITY, city, Customer.PROPERTYNAME_COUNTRY);

            if(postOfficeNumber != null &&
                    ((String)getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).getValue()).isEmpty()) {
                getBufferedModel(Customer.PROPERTYNAME_POSTOFFICENUMBER).setValue(postOfficeNumber);

                if(province != null &&
                        ((String)getBufferedModel(Customer.PROPERTYNAME_PROVINCE).getValue()).isEmpty()) {
                    getBufferedModel(Customer.PROPERTYNAME_PROVINCE).setValue(province);

                    if(country != null &&
                            ((String)getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                        getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
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
        String province = (String) getBufferedModel(Customer.PROPERTYNAME_PROVINCE).getValue();

        if(!province.isEmpty()) {
            String country = CustomerManager.getInstance().getResult(Customer.PROPERTYNAME_PROVINCE, province, Customer.PROPERTYNAME_COUNTRY);

            if(country != null &&
                    ((String)getBufferedModel(Customer.PROPERTYNAME_COUNTRY).getValue()).isEmpty()) {
                getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
            }

//            country = (country == null) ? "" : country;
//
//            getBufferedModel(Customer.PROPERTYNAME_COUNTRY).setValue(country);
        }
    }
}
