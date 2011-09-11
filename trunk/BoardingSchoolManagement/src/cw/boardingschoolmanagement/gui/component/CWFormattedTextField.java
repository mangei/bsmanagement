package cw.boardingschoolmanagement.gui.component;

import java.text.Format;

import javax.swing.JFormattedTextField;

/**
 *
 * @author ManuelG
 */
public class CWFormattedTextField extends JFormattedTextField {

    public CWFormattedTextField(AbstractFormatterFactory factory, Object currentValue) {
        super(factory, currentValue);
    }

    public CWFormattedTextField(AbstractFormatterFactory factory) {
        super(factory);
    }

    public CWFormattedTextField(AbstractFormatter formatter) {
        super(formatter);
    }

    public CWFormattedTextField(Format format) {
        super(format);
    }

    public CWFormattedTextField(Object value) {
        super(value);
    }

    public CWFormattedTextField() {
    }
    
}
