package cw.customermanagementmodul.extentions.interfaces;

import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.extentions.interfaces.Extention;
import cw.customermanagementmodul.pojo.Customer;
import java.util.List;
import javax.swing.JPanel;

/**
 * If you want to add an Filter to the Customers, implement this interface
 * @author ManuelG
 */
public interface CustomerSelectorFilterExtention extends Extention {
    public void init(ValueModel change);
    public void initEventHandling();
    public List<Customer> filter(List<Customer> costumers);
    public String getPosition();
    public JPanel getPanel();
}
