package cw.boardingschoolmanagement.gui.component;

import javax.swing.JPasswordField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author ManuelG
 */
public class CWPasswordField extends JPasswordField {

    private int maxTextLength;

    CWPasswordField(String text) {
        super(text);
    }

    CWPasswordField() {
    }

    CWPasswordField(int maxTextLength) {
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
