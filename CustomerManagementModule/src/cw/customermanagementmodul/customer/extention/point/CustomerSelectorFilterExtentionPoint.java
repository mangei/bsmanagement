package cw.customermanagementmodul.customer.extention.point;

import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.extention.CWIExtention;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.customer.persistence.Customer;

/**
 * If you want to add an Filter to the Customers, implement this interface
 * @author ManuelG
 */
public interface CustomerSelectorFilterExtentionPoint extends CWIExtention {
    public void init(ValueModel change, EntityManager entityManager);
    public void initEventHandling();
    public List<Customer> filter(List<Customer> costumers);
    public CWPanel getView();
    public String getFilterName();
    public void dispose();
}
