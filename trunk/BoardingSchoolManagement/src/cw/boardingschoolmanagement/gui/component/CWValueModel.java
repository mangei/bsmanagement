package cw.boardingschoolmanagement.gui.component;

import com.jgoodies.binding.value.ValueModel;

/**
 *
 * @author ManuelG
 */
public interface CWValueModel extends ValueModel {
    public void putClientProperty(Object key, Object value);
    public Object getClientProperty(Object key);
}
