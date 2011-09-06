package cw.customermanagementmodul.extention.point;

import java.util.List;

import javax.persistence.EntityManager;

import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.interfaces.Extention;
import cw.customermanagementmodul.persistence.Customer;

/**
 * If you want to add an Filter to the Customers, implement this interface
 * @author ManuelG
 */
public interface CustomerSelectorFilterExtentionPoint extends Extention {
    public void init(ValueModel change, EntityManager entityManager);
    public void initEventHandling();
    public List<Customer> filter(List<Customer> costumers);
    public CWPanel getView();
    public String getFilterName();
    public void dispose();
}
