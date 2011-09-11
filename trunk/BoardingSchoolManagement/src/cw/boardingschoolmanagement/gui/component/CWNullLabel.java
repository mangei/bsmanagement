package cw.boardingschoolmanagement.gui.component;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.jgoodies.binding.value.ValueModel;

/**
 *
 * @author ManuelG
 */
public class CWNullLabel {

    private CWLabel label;
    private ValueModel model;
    private String nullText;
    private PropertyChangeListener listener;
    private Color normalColor;
    private Color nullColor;

    CWNullLabel(CWLabel label, String nullText) {
        if(label == null) {
            throw new NullPointerException("label is null");
        }

        this.label = label;
        this.nullText = nullText;
        model = (ValueModel) label.getClientProperty(CWComponentFactory.VALUE_MODEL_KEY);
        normalColor = label.getForeground();
        nullColor = Color.GRAY;

        if(model == null) {
            throw new NullPointerException("the valueModel of the label is null");
        }

        model.addValueChangeListener(listener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateLabel();
            }
        });
        updateLabel();
    }

    public void dispose() {
        model.removeValueChangeListener(listener);
    }

    private void updateLabel() {
        if(model.getValue() == null) {
            label.setForeground(nullColor);
            label.setText(nullText);
        } else {
            label.setForeground(normalColor);
            label.setText(model.getValue().toString());
        }
    }

}
