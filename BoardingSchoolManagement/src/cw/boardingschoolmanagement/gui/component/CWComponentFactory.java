package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.*;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.adapter.TextComponentConnector;
import com.jgoodies.binding.adapter.ToggleButtonAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.toedter.calendar.JTextFieldDateEditor;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.tree.TreeModel;
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

    public static final Color BORDER_COLOR = new Color(215, 220, 228);

    private static String DOCUMENT_LISTENER_KEY = "documentListenerKey";
    private static String DATECHOOSER_FOCUS_LISTENER_KEY = "dateChooserListenerKey";
    private static String TEXT_COMPONENT_CONNECTOR_KEY = "textComponentConnectorKey";
    private static String PROPERTY_CONNECTOR_KEY = "propertyConnectorKey";
    private static String PROPERTY_CHANGE_LISTENER_KEY = "propertyChangeListenerKey";
    private static String PROPERTY_CHANGE_LISTENER2_KEY = "propertyChangeListener2Key";
    private static String RADIO_BUTTON_KEY = "buttonKey";
    private static String RADIO_BUTTON_ITEM_LISTENER_KEY = "buttonItemListenerKey";
    public static String VALUE_MODEL_KEY = "valueModelKey";
    private static String VALUE_MODEL_CHANGE_LISTENER_KEY = "valueModelPropertyChangeListenerKey";
    public static String NULL_LABEL_KEY = "nullLabelKey";

    private CWComponentFactory() {
    }

    public static CWComponentContainer createComponentContainer() {
        return new CWComponentContainer();
    }

    public static class CWComponentContainer {

        private List<JComponent> components;

        public CWComponentContainer() {
            components = new ArrayList<JComponent>();
        }

        public CWComponentContainer addComponent(JComponent comp) {
            if(comp == null) {
                throw new NullPointerException("component is null");
            }
            components.add(comp);
            return this;
        }

        public JComponent addComponentAndReturn(JComponent comp) {
            if(comp == null) {
                throw new NullPointerException("component is null");
            }
            components.add(comp);
            return comp;
        }

        public void dispose() {
            JComponent comp;
            for(int i=0, l=components.size(); i<l; i++) {
                comp = components.get(i);

                Bindings.removeComponentPropertyHandler(comp);

                if(comp instanceof CWTextField) {
                    DocumentListener dl = (DocumentListener) comp.getClientProperty(DOCUMENT_LISTENER_KEY);
                    if(dl != null) {
                        ((CWTextField)comp).getDocument().removeDocumentListener(dl);
                        comp.putClientProperty(DOCUMENT_LISTENER_KEY, null);
                    }
                }

                if(comp instanceof CWDateChooser) {
                    FocusListener fl = (FocusListener) comp.getClientProperty(DATECHOOSER_FOCUS_LISTENER_KEY);
                    if(fl != null) {
                        ((JTextFieldDateEditor)((CWDateChooser)comp).getDateEditor()).getUiComponent().removeFocusListener(fl);
                        comp.putClientProperty(DATECHOOSER_FOCUS_LISTENER_KEY, null);
                    }
                    ((CWDateChooser)comp).cleanup();
                }

                if(comp instanceof CWButton) {
                    ((CWButton)comp).setAction(null);
                }

                if(comp instanceof CWMenuItem) {
                    ((CWMenuItem)comp).setAction(null);
                }

                if(comp instanceof CWPanel) {
                    ((CWPanel)comp).removeAll();
                    ((CWPanel)comp).setLayout(null);
                    ((CWPanel)comp).setUI(null);
                }

                TextComponentConnector textComponentConnector = (TextComponentConnector) comp.getClientProperty(TEXT_COMPONENT_CONNECTOR_KEY);
                if(textComponentConnector != null) {
                    textComponentConnector.release();
                    comp.putClientProperty(TEXT_COMPONENT_CONNECTOR_KEY, null);
                }

                CWNullLabel nullLabelKey = (CWNullLabel) comp.getClientProperty(NULL_LABEL_KEY);
                if(nullLabelKey != null) {
                    nullLabelKey.dispose();
                    comp.putClientProperty(NULL_LABEL_KEY, null);
                }

                PropertyConnector propertyConnector = (PropertyConnector) comp.getClientProperty(PROPERTY_CONNECTOR_KEY);
                if(propertyConnector != null) {
                    propertyConnector.release();
                    comp.putClientProperty(PROPERTY_CONNECTOR_KEY, null);
                }

                PropertyChangeListener propertyChangeListener = (PropertyChangeListener) comp.getClientProperty(PROPERTY_CHANGE_LISTENER_KEY);
                if(propertyChangeListener != null) {
                    comp.removePropertyChangeListener(propertyChangeListener);
                    comp.putClientProperty(PROPERTY_CHANGE_LISTENER_KEY, null);
                }

                PropertyChangeListener propertyChangeListener2 = (PropertyChangeListener) comp.getClientProperty(PROPERTY_CHANGE_LISTENER2_KEY);
                if(propertyChangeListener2 != null) {
                    comp.removePropertyChangeListener(propertyChangeListener2);
                    comp.putClientProperty(PROPERTY_CHANGE_LISTENER2_KEY, null);
                }

                CWRadioButton button = (CWRadioButton) comp.getClientProperty(RADIO_BUTTON_KEY);
                if(button != null) {
                    ItemListener itemListener = (ItemListener) comp.getClientProperty(RADIO_BUTTON_ITEM_LISTENER_KEY);
                    if(itemListener != null) {
                        button.removeItemListener(itemListener);
                        comp.putClientProperty(RADIO_BUTTON_ITEM_LISTENER_KEY, null);
                    }
                    comp.putClientProperty(RADIO_BUTTON_KEY, null);
                }

                ValueModel valueModel = (ValueModel) comp.getClientProperty(VALUE_MODEL_KEY);
                if(valueModel != null) {
                    PropertyChangeListener propertyChangeListener_ = (PropertyChangeListener) comp.getClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY);
                    if(propertyChangeListener_ != null) {
                        valueModel.removeValueChangeListener(propertyChangeListener_);
                        comp.putClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY, null);
                    }
                    comp.putClientProperty(VALUE_MODEL_KEY, null);
                }
            }

            components.clear();
        }

    }

    public static CWDateChooser createDateChooser(final ValueModel valueModel) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        final CWDateChooser dc = new CWDateChooser();

        dc.getJCalendar().setDecorationBordersVisible(false);
        dc.getJCalendar().getDayChooser().setDecorationBackgroundVisible(false);
        dc.getJCalendar().getDayChooser().setWeekOfYearVisible(false);

        PropertyConnector connector = PropertyConnector.connect(valueModel, "value", dc, "date");
        connector.updateProperty2();
        dc.putClientProperty(PROPERTY_CONNECTOR_KEY, connector);

        FocusListener fl = new FocusAdapter() {
             @Override
            public void focusLost(FocusEvent e) {
                if(((JTextFieldDateEditor)dc.getDateEditor()).getText().isEmpty()) {
                    dc.setDate(null);
                    valueModel.setValue(null);
                }
            }
        };
        ((JTextFieldDateEditor)dc.getDateEditor()).getUiComponent().addFocusListener(fl);
        dc.putClientProperty(DATECHOOSER_FOCUS_LISTENER_KEY, fl);

        return dc;
    }

    public static CWTable createTable(String emptyText) {
        return createTable(null, emptyText, null);
    }

    public static CWTable createTable(TableModel tableModel, final String emptyText) {
        return createTable(null, emptyText, null);
    }

    public static CWTable createTable(TableModel tableModel, final String emptyText, String tableStateName) {
        CWTable table;
        
        if (tableModel != null) {
            table = new CWTable(tableModel);
        } else {
            table = new CWTable();
        }

        table.setColumnControlVisible(true);
//        table.setAutoCreateRowSorter(true);
        table.setPreferredScrollableViewportSize(new Dimension(10, 10));
        table.setHighlighters(HighlighterFactory.createSimpleStriping());

        table.setEmptyText(emptyText);
        table.setTableStateName(tableStateName);

        table.loadTableState();

        return table;
    }

    public static CWList createList(ListModel listModel) {
        if(listModel == null) {
            throw new NullPointerException("listModel is null");
        }

        CWList list = new CWList(listModel);

        list.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        return list;
    }

    public static CWList createList(SelectionInList selectionInList) {
        return createList(selectionInList, "");
    }

    public static CWList createList(SelectionInList selectionInList, final String emptyText) {
        if(selectionInList == null) {
            throw new NullPointerException("selectionInList is null");
        }

        CWList list = new CWList();
        list.setEmptyText(emptyText);

        list.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
//        list.setRolloverEnabled(true);
//        list.setHighlighters(HighlighterFactory.createSimpleStriping(HighlighterFactory.LINE_PRINTER));
//        list.addHighlighter(HighlighterFactory.createSimpleStriping(Color.BLUE));
//        list.addHighlighter(HighlighterFactory.createSimpleStriping(Color.RED));
//        list.addHighlighter(new ColorHighlighter(Color.CYAN, Color.WHITE));
//        list.addHighlighter(new ColorHighlighter(Color.RED, Color.WHITE));

//        Bindings.bind(list, selectionInList);
        
        list.setModel(selectionInList);
        list.setSelectionModel(
                new SingleListSelectionAdapter(
                        selectionInList.getSelectionIndexHolder()));

        return list;
    }

    public static JScrollPane createScrollPane(JComponent component) {
        if(component == null) {
            throw new NullPointerException("component is null");
        }

        JScrollPane sp = new JScrollPane(component);
        sp.setBorder(null);
        sp.setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.getHorizontalScrollBar().setUnitIncrement(20);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        return sp;
    }

    public static CWButton createButton(Action action) {
        if(action == null) {
            throw new NullPointerException("action is null");
        }

        CWButton button = new CWButton(action);
        button.setOpaque(false);
        return button;
    }

    public static CWMenuItem createMenuItem(Action action) {
        if(action == null) {
            throw new NullPointerException("action is null");
        }

        CWMenuItem item = new CWMenuItem(action);
        return item;
    }

    public static CWPopupMenu createPopupMenu() {
        CWPopupMenu popupMenu = new CWPopupMenu();
        return popupMenu;
    }

    public static CWRadioButton createRadioButton(Action action) {
        if(action == null) {
            throw new NullPointerException("action is null");
        }

        CWRadioButton rb = new CWRadioButton(action);
        rb.setOpaque(false);
        return rb;
    }

    public static CWRadioButton createRadioButton(String text) {
        CWRadioButton rb = new CWRadioButton(text);
        rb.setOpaque(false);
        return rb;
    }

    public static CWRadioButton createRadioButton(String text, boolean isSelected) {
        CWRadioButton rb = new CWRadioButton(text, isSelected);
        rb.setOpaque(false);
        return rb;
    }

    public static CWButtonGroup createButtonGroup(AbstractButton... buttons) {
        if(buttons == null) {
            throw new NullPointerException("buttons are null");
        }

        CWButtonGroup group = new CWButtonGroup();

        // Add all buttons to the group
        for (int i = 0; i < buttons.length; i++) {
            group.add(buttons[i]);
        }

        return group;
    }

    public static CWPanel createTrueFalsePanel(final ValueModel valueModel, String trueText, String falseText, boolean selected) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }
        
        // Create the RadioButtons
        final CWRadioButton bTrue = createRadioButton(trueText);
        final CWRadioButton bFalse = createRadioButton(falseText);

        // Set the right button selected
        if (selected) {
            bTrue.setSelected(true);
        } else {
            bFalse.setSelected(true);
        }

        // Add both to the group
        createButtonGroup(bTrue, bFalse);

        // Create the panel
        CWPanel panel = createPanel();
        panel.add(bTrue);
        panel.add(bFalse);

        // Create the Listener
        ItemListener itemListener;
        bTrue.addItemListener(itemListener = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    valueModel.setValue(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    valueModel.setValue(false);
                }
            }
        });
        panel.putClientProperty(RADIO_BUTTON_KEY, bTrue);
        panel.putClientProperty(RADIO_BUTTON_ITEM_LISTENER_KEY, itemListener);


        PropertyChangeListener propertyChangeListener;
        panel.addPropertyChangeListener("enabled", propertyChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                bTrue.setEnabled((Boolean)evt.getNewValue());
                bFalse.setEnabled((Boolean)evt.getNewValue());
            }
        });
        panel.putClientProperty(PROPERTY_CHANGE_LISTENER_KEY, propertyChangeListener);

        return panel;
    }

    public static CWIntegerTextField createIntegerTextField(ValueModel valueModel) {
        return createIntegerTextField(valueModel, -1);
    }

    public static CWIntegerTextField createIntegerTextField(final ValueModel valueModel, int maxDigits) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        final CWIntegerTextField textField = new CWIntegerTextField(maxDigits);

        // Try to set the text
        // Important: We have to set the text, before we add the changeListeners
        if(valueModel.getValue() != null) {
            textField.setText( Integer.toString((Integer) valueModel.getValue()) );
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
        DocumentListener documentListener = new DocumentListener() {

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
        };
        textField.getDocument().addDocumentListener(documentListener);
        textField.putClientProperty(DOCUMENT_LISTENER_KEY, documentListener);

        return textField;
    }
    
    public static CWCurrencyTextField createCurrencyTextField(final ValueModel valueModel) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        final CWCurrencyTextField textField = new CWCurrencyTextField();

        // Try to set the text
        // Important: We have to set the text, before we add the changeListeners
        if(valueModel.getValue() != null) {
            textField.setText( Double.toString((Double) valueModel.getValue()) );
        }

        // change the textfield if the value changes
        final PropertyChangeListener valueModelPropertyChangeListener = new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent evt) {
                textField.setText(Double.toString((Double) valueModel.getValue()));
            }
        };

        // add the changelistener
        valueModel.addValueChangeListener(valueModelPropertyChangeListener);
        textField.putClientProperty(VALUE_MODEL_KEY, valueModel);
        textField.putClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY, valueModelPropertyChangeListener);

        // Change the value if the textfield changes
        DocumentListener dl = new DocumentListener() {

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
        };
        textField.getDocument().addDocumentListener(dl);
        textField.putClientProperty(DOCUMENT_LISTENER_KEY, dl);

        return textField;
    }

    public static CWCheckBox createCheckBox(ValueModel valueModel, String text) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        CWCheckBox checkBox = new CWCheckBox(text);

        boolean enabled = checkBox.getModel().isEnabled();
        checkBox.setModel(new ToggleButtonAdapter(valueModel));
        checkBox.setEnabled(enabled);

        checkBox.setOpaque(false);
        return checkBox;
    }
    
    public static CWTree createTree(TreeModel treeModel) {
        if(treeModel == null) {
            throw new NullPointerException("treeModel is null");
        }

        CWTree tree = new CWTree(treeModel);
        tree.setHighlighters(HighlighterFactory.createSimpleStriping());
        tree.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        tree.setShowsRootHandles(true);
        tree.setExpandsSelectedPaths(true);
        return tree;
    }

    public static <E> CWComboBox createComboBox(SelectionInList<E> selectionInList) {
        if(selectionInList == null) {
            throw new NullPointerException("selectionInList is null");
        }

        CWComboBox comboBox = new CWComboBox();

        comboBox.setModel(new ComboBoxAdapter<E>(selectionInList));

        return comboBox;
    }

    public static CWFormattedTextField createFormattedTextField(ValueModel valueModel, Format format) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }
        if(format == null) {
            throw new NullPointerException("format is null");
        }

        CWFormattedTextField textField = new CWFormattedTextField(format);

        PropertyConnector connector = PropertyConnector.connect(valueModel, "value", textField, "value");
        connector.updateProperty2();
        textField.putClientProperty(PROPERTY_CONNECTOR_KEY, connector);

        return textField;
    }

    public static CWTextArea createTextArea(ValueModel valueModel, boolean commitOnFocusLost) {
        return createTextArea(valueModel);
    }

    public static CWTextArea createTextArea(ValueModel valueModel) {
        return createTextArea(valueModel, MAX_TEXTAREA_LENGTH);
    }

    public static CWTextArea createTextArea(ValueModel valueModel, int maxTextLength) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }
        
        CWTextArea textArea = new CWTextArea(maxTextLength);

//        Bindings.bind(textArea, valueModel);

        TextComponentConnector connector = new TextComponentConnector(valueModel, textArea);
        connector.updateTextComponent();
        textArea.putClientProperty(TEXT_COMPONENT_CONNECTOR_KEY, connector);

        return textArea;
    }

    public static CWTextField createTextField(ValueModel valueModel, boolean commitOnFocusLost) {
        return createTextField(valueModel);
    }

    public static CWTextField createTextField(ValueModel valueModel) {
        return createTextField(valueModel, MAX_TEXTFIELD_LENGTH);
    }

    public static CWTextField createTextField(ValueModel valueModel, int maxTextLength) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        CWTextField textField = new CWTextField(maxTextLength);

//        Bindings.bind(textField, valueModel);

        TextComponentConnector connector = new TextComponentConnector(valueModel, textField);
        connector.updateTextComponent();
        textField.putClientProperty(TEXT_COMPONENT_CONNECTOR_KEY, connector);

        return textField;
    }

    public static CWLabel createLabel(String text) {
        return createLabel(text, null);
    }

    public static CWLabel createLabel(ValueModel valueModel) {
        return createLabel(valueModel, null, null);
    }

    public static CWLabel createLabel(ValueModel valueModel, Format format) {
        return createLabel(valueModel, null, format);
    }

    public static CWLabel createLabel(String text, Icon icon) {
        CWLabel label = new CWLabel(text);
        if(icon != null) {
            label.setIcon(icon);
        }
        label.setOpaque(false);
        return label;
    }

    public static CWLabel createLabel(ValueModel valueModel, Icon icon) {
        return createLabel(valueModel, icon, null);
    }

    public static CWLabel createLabel(final ValueModel valueModel, Icon icon, final Format format) {
        if(valueModel == null) {
            System.out.println("va: " + valueModel);
            new NullPointerException("valueModel is null").printStackTrace();
            throw new NullPointerException("valueModel is null");
        }

        final CWLabel label = new CWLabel();
        label.putClientProperty(CWComponentFactory.VALUE_MODEL_KEY, valueModel);

        ValueModel newValueModel = valueModel;

        if(format != null) {
            final ValueModel bufferedValueModel = new ValueHolder();
            
            PropertyChangeListener propertyChangeListener;
            valueModel.addValueChangeListener(propertyChangeListener = new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    bufferedValueModel.setValue(format.format(valueModel.getValue()));
                }
            });
            label.putClientProperty(VALUE_MODEL_KEY, valueModel);
            label.putClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY, propertyChangeListener);

            bufferedValueModel.setValue(format.format(valueModel.getValue()));

            newValueModel = bufferedValueModel;
        }


        // Wenn sich das Model ändert, auch die Anzeige ändern. Umgekehrt darf es nicht sein, da das Model, den Wert verwaltet und nicht die Anzeige
        
        // Eine neue Variable muss erstellt werden, da die alte nicht final sein kann
        final ValueModel newValueModel2 = newValueModel;

        // die setText Methode muss vom CWLabel aufgerufen werden! nicht vom JLabel!
        PropertyChangeListener valueChanged = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                label.setText((String) newValueModel2.getValue());
            }
        };
        label.putClientProperty(PROPERTY_CHANGE_LISTENER2_KEY, valueChanged);

        // Aktualisiere das Label
        if(newValueModel.getValue() != null) {
            label.setText(newValueModel.getValue().toString());
        } else {
            label.setText(null);
        }


        System.out.println("newValueModel.getValue(): " + newValueModel.getValue() + ", " + (newValueModel.getValue() == null));

        if(icon != null) {
            label.setIcon(icon);
        }
        label.setOpaque(false);
        return label;
    }

    public static CWLabel createLabelBoolean(final ValueModel valueModel, final String trueString, final String falseString) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        CWLabel label = new CWLabel();

        final ValueModel bufferedValueModel = new ValueHolder();

        if(((Boolean)valueModel.getValue()) == true) {
            bufferedValueModel.setValue(trueString);
        } else {
            bufferedValueModel.setValue(falseString);
        }

        PropertyChangeListener propertyChangeListener;
        valueModel.addValueChangeListener(propertyChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if(((Boolean)valueModel.getValue()) == true) {
                    bufferedValueModel.setValue(trueString);
                } else {
                    bufferedValueModel.setValue(falseString);
                }
            }
        });
        label.putClientProperty(VALUE_MODEL_KEY, valueModel);
        label.putClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY, propertyChangeListener);


        PropertyConnector connector = PropertyConnector.connect(bufferedValueModel, "value", label, "text");
        connector.updateProperty2();
        label.putClientProperty(PROPERTY_CONNECTOR_KEY, connector);
        
        label.setOpaque(false);
        return label;
    }

    public static CWLabel createLabelDate(final ValueModel valueModel) {
        if(valueModel == null) {
            throw new NullPointerException("valueModel is null");
        }

        CWLabel label = new CWLabel();
        
        final ValueModel bufferedValueModel = new ValueHolder();

        bufferedValueModel.setValue(CalendarUtil.formatDate((Date)valueModel.getValue()));

        PropertyChangeListener propertyChangeListener;
        valueModel.addValueChangeListener(propertyChangeListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                bufferedValueModel.setValue(CalendarUtil.formatDate((Date)valueModel.getValue()));
            }
        });
        label.putClientProperty(VALUE_MODEL_KEY, valueModel);
        label.putClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY, propertyChangeListener);
        
        PropertyConnector connector = PropertyConnector.connect(bufferedValueModel, "value", label, "text");
        connector.updateProperty2();
        label.putClientProperty(PROPERTY_CONNECTOR_KEY, connector);

        label.setOpaque(false);
        return label;
    }

    public static CWPanel createPanel() {
        return createPanel(new FlowLayout(FlowLayout.LEFT));
    }

    public static CWPanel createPanel(LayoutManager layout) {
        CWPanel panel = new CWPanel();
        panel.setLayout(layout);
        panel.setOpaque(false);
        return panel;
    }

    public static CWBackView createBackView(CWPanel panel) {
        if(panel == null) {
            throw new NullPointerException("panel is null");
        }
        return createBackView(panel, "Zurück");
    }

    public static CWBackView createBackView(CWPanel panel, String backText) {
        if(panel == null) {
            throw new NullPointerException("panel is null");
        }

        CWBackView backView = new CWBackView(panel, backText);
        return backView;
    }

    public static CWView createView() {
        return new CWView();
    }

    public static CWView createView(CWHeaderInfo headerInfo) {
        return createView(headerInfo, null);
    }

    public static CWView createView(String headerText, JComponent comp) {
        return createView(new CWHeaderInfo(headerText), comp);
    }

    public static CWView createView( JComponent comp) {
        return createView(new CWHeaderInfo(), comp);
    }

    public static CWView createView(CWHeaderInfo headerInfo, JComponent comp) {
        if(headerInfo == null) {
            headerInfo = new CWHeaderInfo();
        }
        CWView view = new CWView(headerInfo);
        if(comp != null) {
            view.getContentPanel().add(comp, BorderLayout.CENTER);
        }
        return view;
    }

    public static String createToolTip(String header, String description) {
        return createToolTip(header,description,null);
    }

    public static String createToolTip(String header, String description, String path) {
        CWToolTip tt = new CWToolTip(header,description,CWUtils.getURL(path));
        return tt.getToolTip();
    }

    public static CWCheckBoxList createCheckBoxList(ListModel listModel) {
        if(listModel == null) {
            throw new NullPointerException("listModel is null");
        }

        CWCheckBoxList cbl = new CWCheckBoxList(listModel);
        cbl.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        return cbl;
    }

    public static void createNullLabel(CWLabel label, String nullText) throws NullPointerException {
        if(label == null) {
            throw new NullPointerException("label is null");
        }
        CWNullLabel nullLabel = new CWNullLabel(label, nullText);
        label.putClientProperty(CWComponentFactory.NULL_LABEL_KEY, nullLabel);
    }
}
