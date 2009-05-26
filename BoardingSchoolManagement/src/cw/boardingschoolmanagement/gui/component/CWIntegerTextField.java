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
public class CWIntegerTextField extends JTextField {

    private int maxDigits;

    CWIntegerTextField() {
        maxDigits = -1;
    }
    
    CWIntegerTextField(int maxDigits) {
        this.maxDigits = maxDigits;
    }

    @Override
    protected Document createDefaultModel() {
        return new PlainDocument() {

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

                // Check the maximum length
                if(maxDigits > -1 && getLength() >= maxDigits) {
                    return;
                }

                // Check if the input is a number
                try {
                    Integer.parseInt(str);
                    super.insertString(offs, str, a);
                } catch (NumberFormatException numberFormatException) {}
            }
            
        };
    }

    public int getMaxDigits() {
        return maxDigits;
    }

    public void setMaxDigits(int maxDigits) {
        this.maxDigits = maxDigits;
    }

}
