/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.component;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Dominik
 */
public class TarifTextField extends JTextField {

    @Override
    protected Document createDefaultModel() {
        return new PlainDocument() {

            @Override
            public void insertString(int offs, String str, AttributeSet a)
                    throws BadLocationException {

                boolean check = true;
                char[] numberArray = str.toCharArray();
                for (int i = 0; i < numberArray.length; i++) {

                    if (!Character.isDigit(numberArray[i]) || numberArray[i] != ',') {

                        check = false;

                    }

                }
                if (check) {
                    super.insertString(offs, str, a);
                }

            }
        };
    }
}
