package cw.customermanagementmodul.extention.point;

import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.extention.point.Extention;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.customermanagementmodul.pojo.Customer;
import java.util.List;

/**
 * If you want to add an Filter to the Customers, implement this interface
 * @author ManuelG
 */
public interface CustomerSelectorFilterExtention extends Extention {
    public void init(ValueModel change);
    public void initEventHandling();
    public List<Customer> filter(List<Customer> costumers);
    public String getPosition();
    public CWPanel getView();
    public void dispose();
}
