package cw.boardingschoolmanagement.gui.component;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author ManuelG
 */
public class CWCurrencyTextField extends JTextField {

    private int decimalPlaces = 2;

    public CWCurrencyTextField() {
    }
    
    @Override
    protected Document createDefaultModel() {
        return new PlainDocument() {

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

                StringBuffer newString = new StringBuffer(getDocument().getText(0, getDocument().getLength()));
                newString.insert(offs, str);
                String dStr = newString.toString();

                // Check if the input is a number
                try {
                    Double.parseDouble(dStr);
                    super.insertString(offs, str, a);
                } catch (NumberFormatException numberFormatException) {}
            }
            
        };
    }

}
