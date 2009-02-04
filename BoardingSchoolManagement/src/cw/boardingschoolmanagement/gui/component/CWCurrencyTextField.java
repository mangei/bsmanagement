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
    private double maxValue=9999999.99;

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
                    Double d = Double.parseDouble(dStr);
                    System.out.println(dStr);
                    //check the count of decimal fraction
                    int decCounter = countFraction(dStr);
                    System.out.println(decCounter);
                    if (decCounter <= decimalPlaces && d<=maxValue) {
                        super.insertString(offs, str, a);
                    }

                } catch (NumberFormatException numberFormatException) {
                    
                }
            }
        };
    }
    

    //check the count of decimal fraction
    private int countFraction(String str) {
        try {
            Double d = Double.parseDouble(str);
        } catch (NumberFormatException numberFormatException) {
            return -1;
        }
        

        int decCounter = 0;
        boolean decStart = false;
        char[] decArray = str.toCharArray();
        for (int i = 0; i < decArray.length; i++) {
            if (decStart) {
                decCounter++;
            }
            if (decArray[i] == '.') {
                decStart = true;
            }
        }

        return decCounter;

    }
}
