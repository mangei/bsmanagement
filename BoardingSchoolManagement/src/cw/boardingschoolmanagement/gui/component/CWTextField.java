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
public class CWTextField extends JTextField {

    private int maxTextLength;

    public CWTextField(String text) {
        super(text);
    }

    public CWTextField() {
    }

    public CWTextField(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }

    @Override
    protected Document createDefaultModel() {
        return new PlainDocument() {

            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

                // Check the maximum length
                if (maxTextLength > -1 && getLength() >= maxTextLength) {
                    return;
                }

                super.insertString(offs, str, a);
            }
        };
    }

    public int getMaxTextLength() {
        return maxTextLength;
    }

    public void getMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
    }
}
