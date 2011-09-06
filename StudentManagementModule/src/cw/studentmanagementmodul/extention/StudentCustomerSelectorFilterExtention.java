package cw.studentmanagementmodul.extention;

import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.extention.point.CustomerSelectorFilterExtentionPoint;
import cw.customermanagementmodul.persistence.model.CustomerModel;
import cw.studentmanagementmodul.gui.StudentCustomerSelectorFilterExtentionPresentationModel;
import cw.studentmanagementmodul.gui.StudentCustomerSelectorFilterExtentionView;
import cw.studentmanagementmodul.pojo.Student;
import cw.studentmanagementmodul.pojo.manager.StudentManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;



/**
 *
 * @author ManuelG
 */
public class StudentCustomerSelectorFilterExtention
        implements CustomerSelectorFilterExtentionPoint {

    public final static int NO_MATTER     = 0;
    public final static int ACTIVE        = 1;
    public final static int INACTIVE      = 2;

    private StudentCustomerSelectorFilterExtentionPresentationModel model;
    private StudentCustomerSelectorFilterExtentionView view;
    private ValueModel change;

    private PropertyChangeListener changeListener;

    public void init(final ValueModel change) {
        this.change = change;

        model = new StudentCustomerSelectorFilterExtentionPresentationModel();
        view = new StudentCustomerSelectorFilterExtentionView(model);
    }

    public void initEventHandling() {
        model.getOption().addValueChangeListener(changeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                change.setValue(true);
            }
        });
        model.getStudentClassSelection().addValueChangeListener(changeListener);
    }

    public List<CustomerModel> filter(List<CustomerModel> customers) {

        if((Integer)model.getOption().getValue() != NO_MATTER) {
            
            Object[] customersArray =  customers.toArray();

            for (int i = 0, l=customersArray.length; i < l; i++) {
                CustomerModel customer = (CustomerModel)customersArray[i];

                // Get the student object
                Student student = StudentManager.getInstance().get(customer);

                // Check the status
                if( (Integer)model.getOption().getValue() == ACTIVE && !student.isActive() ||
                    (Integer)model.getOption().getValue() == INACTIVE && student.isActive()
                    ) {
                    customers.remove(customer);
                } else {

                    // Check the class

                    // (1) In a specific class
                    if((Integer)model.getOption().getValue() == ACTIVE &&
                       model.getStudentClassSelection().getSelectionIndex() != 0 &&
                       model.getStudentClassSelection().getSelectionIndex() != 1) {
                        
                        // Check if the student is in the selected studentClass
                        if(!model.getStudentClassSelection().getSelection().equals(student.getStudentClass())) {
                            customers.remove(customer);
                        }
                    }
                    // OR (2) in no class
                    else if((Integer)model.getOption().getValue() == ACTIVE &&
                              model.getStudentClassSelection().getSelectionIndex() == 1) {

                        // if the student is in a class, remove him
                        if(student.getStudentClass() != null) {
                            customers.remove(customer);
                        }
                    }
                    // OR (3) it doesn't matter which class
                }
            }

        }

        return customers;
    }

    public CWPanel getView() {
        return view;
    }

    public void dispose() {
        model.getOption().removeValueChangeListener(changeListener);
        model.getStudentClassSelection().removeValueChangeListener(changeListener);
        view.dispose();
    }

    public String getFilterName() {
        return "Schüler/Klassen";
    }
}
