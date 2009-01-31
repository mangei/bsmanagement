package cw.boardingschoolmanagement.app;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.toedter.calendar.JDateChooser;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWIntegerTextField;
import cw.boardingschoolmanagement.gui.component.CWJTextArea;
import cw.boardingschoolmanagement.gui.component.CWJTextField;
import cw.boardingschoolmanagement.gui.component.CWJXList;
import cw.boardingschoolmanagement.gui.component.CWJXTable;
import cw.boardingschoolmanagement.gui.component.JBackPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.TreeModel;
import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.decorator.HighlighterFactory;

/**
 * The factory contains all necessary components we have in our application.
 * It contains the support of binding properties and new features of components.
 * All components we use in the application, should only be created with this
 * Factory.
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class CWComponentFactory {

    public static final int MAX_TEXTFIELD_LENGTH = 255;
    public static final int MAX_TEXTAREA_LENGTH = 1023;

    BasicComponentFactory bcf;

    private CWComponentFactory() {
    }

    public static JDateChooser createDateChooser(ValueModel dateProperty) {
        JDateChooser dc = new JDateChooser();

        dc.getJCalendar().setDecorationBordersVisible(false);
        dc.getJCalendar().getDayChooser().setDecorationBackgroundVisible(false);
        dc.getJCalendar().getDayChooser().setWeekOfYearVisible(false);

        PropertyConnector.connectAndUpdate(dateProperty, dc, "date");

        return dc;
    }

    public static CWJXTable createTable() {
        return createTable("");
    }

    public static CWJXTable createTable(String emptyText) {
        return createTable(null, emptyText);
    }
    
    public static CWJXTable createTable(TableModel tableModel) {
        return createTable(tableModel, "");
    }

    public static CWJXTable createTable(TableModel tableModel, final String emptyText) {
        CWJXTable table;
        
        if (tableModel != null) {
            table = new CWJXTable(tableModel);
        } else {
            table = new CWJXTable();
        }

        table.setEmptyText(emptyText);

        table.setColumnControlVisible(true);
        table.setAutoCreateRowSorter(true);
        table.setPreferredScrollableViewportSize(new Dimension(10, 10));
        table.setHighlighters(HighlighterFactory.createSimpleStriping());

        return table;
    }

    public static CWJXList createList(ListModel listModel) {
        CWJXList list = new CWJXList(listModel);

        list.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 228)));

        return list;
    }

    public static CWJXList createList(SelectionInList selectionInList) {
        return createList(selectionInList, "");
    }

    public static CWJXList createList(SelectionInList selectionInList, final String emptyText) {
        CWJXList list = new CWJXList();
        list.setEmptyText(emptyText);

        list.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 228)));
//        list.setRolloverEnabled(true);
//        list.setHighlighters(HighlighterFactory.createSimpleStriping(HighlighterFactory.LINE_PRINTER));
//        list.addHighlighter(HighlighterFactory.createSimpleStriping(Color.BLUE));
//        list.addHighlighter(HighlighterFactory.createSimpleStriping(Color.RED));
//        list.addHighlighter(new ColorHighlighter(Color.CYAN, Color.WHITE));
//        list.addHighlighter(new ColorHighlighter(Color.RED, Color.WHITE));

        Bindings.bind(list, selectionInList);

        return list;
    }

    public static JScrollPane createScrollPane(JComponent view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setBorder(null);
        return sp;
    }

    public static JButton createButton(Action a) {
        JButton button = new JButton(a);
        button.setOpaque(false);
        return button;
    }

    public static JRadioButton createRadioButton(Action a) {
        JRadioButton rb = new JRadioButton(a);
        rb.setOpaque(false);
        return rb;
    }

    public static JRadioButton createRadioButton(String text) {
        JRadioButton rb = new JRadioButton(text);
        rb.setOpaque(false);
        return rb;
    }

    public static JRadioButton createRadioButton(String text, boolean isSelected) {
        JRadioButton rb = new JRadioButton(text, isSelected);
        rb.setOpaque(false);
        return rb;
    }

    public static ButtonGroup createButtonGroup(AbstractButton... buttons) {
        ButtonGroup group = new ButtonGroup();

        // Add all buttons to the group
        for (int i = 0; i < buttons.length; i++) {
            group.add(buttons[i]);
        }

        return group;
    }

    public static JPanel createTrueFalsePanel(final ValueModel value, String trueText, String falseText, boolean selected) {
        // Create the RadioButtons
        final JRadioButton bTrue = createRadioButton(trueText);
        final JRadioButton bFalse = createRadioButton(falseText);

        // Set the right button selected
        if (selected) {
            bTrue.setSelected(true);
        } else {
            bFalse.setSelected(true);
        }

        // Create the Listener
        bTrue.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    value.setValue(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    value.setValue(false);
                }
            }
        });

        // Add both to the group
        createButtonGroup(bTrue, bFalse);

        // Create the panel
        JPanel panel = createPanel();
        panel.add(bTrue);
        panel.add(bFalse);

        return panel;
    }

    public static CWIntegerTextField createIntegerTextField(ValueModel valueModel) {
        return createIntegerTextField(valueModel, -1);
    }

    public static CWIntegerTextField createIntegerTextField(final ValueModel valueModel, int maxDigits) {
        final CWIntegerTextField textField = new CWIntegerTextField(maxDigits);

        // Try to set the text
        // Important: We have to set the text, before we add the changeListeners
        try {
            textField.setText(Integer.toString((Integer) valueModel.getValue()));
        } catch (NullPointerException e) {
        }

        // change the textfield if the value changes
        final PropertyChangeListener valueModelPropertyChangeListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                textField.setText(Integer.toString((Integer) valueModel.getValue()));
            }
        };

        // add the changelistener
        valueModel.addValueChangeListener(valueModelPropertyChangeListener);

        // Change the value if the textfield changes
        textField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            public void changedUpdate(DocumentEvent e) {
                update(e);
            }

            private void update(DocumentEvent e) {
                try {
                    Document d = e.getDocument();

                    // get the new text
                    String newValue = d.getText(0, d.getLength());

                    // remove the old listener, if we don't do this, it would call the PropertycChangeListener
                    // and wants to change the text of the textfield -> java.lang.IllegalStateException: Attempt to mutate in notification
                    valueModel.removeValueChangeListener(valueModelPropertyChangeListener);

                    try {
                        // Set the value, if the tet is an Integer
                        valueModel.setValue(Integer.parseInt(newValue));
                    } catch (NumberFormatException numberFormatException) {
                    }

                    // Add the PropertyChangeListener again
                    valueModel.addValueChangeListener(valueModelPropertyChangeListener);

                } catch (BadLocationException ex) {
                    Logger.getLogger(CWComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        return textField;
    }
    
    public static CWCurrencyTextField createCurrencyTextField(final ValueModel valueModel) {
        final CWCurrencyTextField textField = new CWCurrencyTextField();

        // Try to set the text
        // Important: We have to set the text, before we add the changeListeners
        try {
            String text = Double.toString((Double) valueModel.getValue());
            textField.setText(text);
        } catch (NullPointerException e) {
        }

        // change the textfield if the value changes
        final PropertyChangeListener valueModelPropertyChangeListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                textField.setText(Double.toString((Double) valueModel.getValue()));
            }
        };

        // add the changelistener
        valueModel.addValueChangeListener(valueModelPropertyChangeListener);

        // Change the value if the textfield changes
        textField.getDocument().addDocumentListener(new DocumentListener() {

            public void insertUpdate(DocumentEvent e) {
                update(e);
            }

            public void removeUpdate(DocumentEvent e) {
                update(e);
            }

            public void changedUpdate(DocumentEvent e) {
                update(e);
            }

            private void update(DocumentEvent e) {
                try {
                    Document d = e.getDocument();

                    // get the new text
                    String newValue = d.getText(0, d.getLength());

                    // remove the old listener, if we don't do this, it would call the PropertycChangeListener
                    // and wants to change the text of the textfield -> java.lang.IllegalStateException: Attempt to mutate in notification
                    valueModel.removeValueChangeListener(valueModelPropertyChangeListener);

                    try {
                        // Set the value, if the tet is an Double
                        valueModel.setValue(Double.parseDouble(newValue));
                    } catch (NumberFormatException numberFormatException) {
                    }

                    // Add the PropertyChangeListener again
                    valueModel.addValueChangeListener(valueModelPropertyChangeListener);

                } catch (BadLocationException ex) {
                    Logger.getLogger(CWComponentFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        return textField;
    }

    public static JCheckBox createCheckBox(ValueModel valueModel, String text) {
        JCheckBox cb = BasicComponentFactory.createCheckBox(valueModel, text);
        cb.setOpaque(false);
        return cb;
    }
    
    public static JXTree createTree(TreeModel treeModel) {
        JXTree tree = new JXTree(treeModel);
        tree.setHighlighters(HighlighterFactory.createSimpleStriping());
        tree.setBorder(BorderFactory.createLineBorder(new Color(215, 220, 228)));
        return tree;
    }

    public static <E> JComboBox createComboBox(SelectionInList<E> selectionInList) {
        return BasicComponentFactory.createComboBox(selectionInList);
    }


    public static JColorChooser createColorChooser(ValueModel valueModel) {
        return BasicComponentFactory.createColorChooser(valueModel);
    }

    public static JColorChooser createColorChooser(ValueModel valueModel, Color defaultColor) {
        return BasicComponentFactory.createColorChooser(valueModel, defaultColor);
    }

    public static JFormattedTextField createFormattedTextField(ValueModel valueModel, Format format) {
        return BasicComponentFactory.createFormattedTextField(valueModel, format);
    }

    public static CWJTextArea createTextArea(ValueModel valueModel, boolean commitOnFocusLost) {
        return createTextArea(valueModel);
    }

    public static CWJTextArea createTextArea(ValueModel valueModel) {
        return createTextArea(valueModel, MAX_TEXTAREA_LENGTH);
    }

    public static CWJTextArea createTextArea(ValueModel valueModel, int maxTextLength) {
        CWJTextArea ta = new CWJTextArea(maxTextLength);

        Bindings.bind(ta, valueModel);

        return ta;
    }

    public static CWJTextField createTextField(ValueModel valueModel, boolean commitOnFocusLost) {
        return createTextField(valueModel);
    }

    public static CWJTextField createTextField(ValueModel valueModel) {
        return createTextField(valueModel, MAX_TEXTFIELD_LENGTH);
    }

    public static CWJTextField createTextField(ValueModel valueModel, int maxTextLength) {
        CWJTextField tf = new CWJTextField(maxTextLength);

        Bindings.bind(tf, valueModel);

        return tf;
    }

    public static JLabel createLabel(ValueModel valueModel) {
        JLabel label = BasicComponentFactory.createLabel(valueModel);
        label.setOpaque(false);
        return label;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setOpaque(false);
        return label;
    }

    public static JPanel createPanel() {
        return createPanel(new FlowLayout(FlowLayout.LEFT));
    }

    public static JPanel createPanel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);
        return panel;
    }

    public static JBackPanel createBackPanel(JPanel panel) {
        return createBackPanel(panel, "Zurück");
    }

    public static JBackPanel createBackPanel(JPanel panel, String backText) {
        JBackPanel backPanel = new JBackPanel(panel, backText);
        return backPanel;
    }

    public static JViewPanel createViewPanel() {
        return new JViewPanel();
    }

    public static JViewPanel createViewPanel(HeaderInfo headerInfo) {
        return createViewPanel(headerInfo, null);
    }

    public static JViewPanel createViewPanel(String headerText, JComponent comp) {
        return createViewPanel(new HeaderInfo(headerText), comp);
    }

    public static JViewPanel createViewPanel( JComponent comp) {
        return createViewPanel(new HeaderInfo(), comp);
    }

    public static JViewPanel createViewPanel(HeaderInfo headerInfo, JComponent comp) {
        if(headerInfo == null) headerInfo = new HeaderInfo();
        JViewPanel panel = new JViewPanel(headerInfo);
        if(comp != null) {
            panel.getContentPanel().add(comp, BorderLayout.CENTER);
        }
        return panel;
    }
}
