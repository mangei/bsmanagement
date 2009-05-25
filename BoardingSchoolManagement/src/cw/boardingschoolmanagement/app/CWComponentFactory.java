package cw.boardingschoolmanagement.app;

import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.adapter.TextComponentConnector;
import com.jgoodies.binding.adapter.ToggleButtonAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import cw.boardingschoolmanagement.gui.component.CWBackView;
import cw.boardingschoolmanagement.gui.component.CWCurrencyTextField;
import cw.boardingschoolmanagement.gui.component.CWIntegerTextField;
import cw.boardingschoolmanagement.gui.component.CWButton;
import cw.boardingschoolmanagement.gui.component.CWLabel;
import cw.boardingschoolmanagement.gui.component.CWTextArea;
import cw.boardingschoolmanagement.gui.component.CWTextField;
import cw.boardingschoolmanagement.gui.component.CWList;
import cw.boardingschoolmanagement.gui.component.CWTable;
import cw.boardingschoolmanagement.gui.component.CWToolTip;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.boardingschoolmanagement.gui.component.CWView;
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
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
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

    public static final Color BORDER_COLOR = new Color(215, 220, 228);

    private static String DOCUMENT_LISTENER_KEY = "documentListenerKey";
    private static String DATECHOOSER_FOCUS_LISTENER_KEY = "dateChooserListenerKey";
    private static String TEXT_COMPONENT_CONNECTOR_KEY = "textComponentConnectorKey";
    private static String PROPERTY_CONNECTOR_KEY = "propertyConnectorKey";
    private static String PROPERTY_CHANGE_LISTENER_KEY = "propertyChangeListenerKey";
    private static String RADIO_BUTTON_KEY = "buttonKey";
    private static String RADIO_BUTTON_ITEM_LISTENER_KEY = "buttonItemListenerKey";
    private static String VALUE_MODEL_KEY = "valueModelKey";
    private static String VALUE_MODEL_CHANGE_LISTENER_KEY = "valueModelPropertyChangeListenerKey";

    private CWComponentFactory() {
    }

    public static CWComponentContainer createCWComponentContainer() {
        return new CWComponentContainer();
    }

    public static class CWComponentContainer {

        private List<JComponent> components;

        public CWComponentContainer() {
            components = new ArrayList<JComponent>();
        }

        public CWComponentContainer addComponent(JComponent comp) {
            components.add(comp);
            return this;
        }

        public JComponent addComponentAndReturn(JComponent comp) {
            components.add(comp);
            return comp;
        }

        public void dispose() {
            JComponent comp;
            for(int i=0, l=components.size(); i<l; i++) {
                comp = components.get(i);

                Bindings.removeComponentPropertyHandler(comp);

                if(comp instanceof JTextField) {
                    DocumentListener dl = (DocumentListener) comp.getClientProperty(DOCUMENT_LISTENER_KEY);
                    if(dl != null) {
                        ((JTextField)comp).getDocument().removeDocumentListener(dl);
                        comp.putClientProperty(DOCUMENT_LISTENER_KEY, null);
                    }
                }

                if(comp instanceof JDateChooser) {
                    FocusListener fl = (FocusListener) comp.getClientProperty(DATECHOOSER_FOCUS_LISTENER_KEY);
                    if(fl != null) {
                        ((JTextFieldDateEditor)((JDateChooser)comp).getDateEditor()).getUiComponent().removeFocusListener(fl);
                        comp.putClientProperty(DATECHOOSER_FOCUS_LISTENER_KEY, null);
                    }
                }

                if(comp instanceof JButton) {
                    ((JButton)comp).setAction(null);
                }

                if(comp instanceof JMenuItem) {
                    ((JMenuItem)comp).setAction(null);
                }

                if(comp instanceof JDateChooser) {
                    ((JDateChooser)comp).cleanup();
                }

//                if(comp instanceof JViewPanel) {
//                    ((JViewPanel)comp).dispose();
//                }

                if(comp instanceof JPanel) {
                    ((JPanel)comp).removeAll();
                    ((JPanel)comp).setLayout(null);
                    ((JPanel)comp).setUI(null);
                }

                TextComponentConnector textComponentConnector = (TextComponentConnector) comp.getClientProperty(TEXT_COMPONENT_CONNECTOR_KEY);
                if(textComponentConnector != null) {
                    textComponentConnector.release();
                    comp.putClientProperty(TEXT_COMPONENT_CONNECTOR_KEY, null);
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

                JRadioButton button = (JRadioButton) comp.getClientProperty(RADIO_BUTTON_KEY);
                if(button != null) {
                    ItemListener itemListener = (ItemListener) comp.getClientProperty(RADIO_BUTTON_ITEM_LISTENER_KEY);
                    if(itemListener != null) {
                        button.removeItemListener(itemListener);
                        System.out.println("removeitemlistener");
                        comp.putClientProperty(RADIO_BUTTON_ITEM_LISTENER_KEY, null);
                    }
                    comp.putClientProperty(RADIO_BUTTON_KEY, null);
                }

                ValueModel valueModel = (ValueModel) comp.getClientProperty(VALUE_MODEL_KEY);
                if(valueModel != null) {
                    PropertyChangeListener propertyChangeListener2 = (PropertyChangeListener) comp.getClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY);
                    if(propertyChangeListener2 != null) {
                        valueModel.removeValueChangeListener(propertyChangeListener2);
                        comp.putClientProperty(VALUE_MODEL_CHANGE_LISTENER_KEY, null);
                    }
                    comp.putClientProperty(VALUE_MODEL_KEY, null);
                }
            }

            components.clear();
        }

    }

    public static JDateChooser createDateChooser(final ValueModel valueModel) {
        final JDateChooser dc = new JDateChooser();

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
        return createTable(null, emptyText);
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
        CWList list = new CWList(listModel);

        list.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));

        return list;
    }

    public static CWList createList(SelectionInList selectionInList) {
        return createList(selectionInList, "");
    }

    public static CWList createList(SelectionInList selectionInList, final String emptyText) {
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

    public static JScrollPane createScrollPane(JComponent view) {
        JScrollPane sp = new JScrollPane(view);
        sp.setBorder(null);
        sp.setOpaque(false);
        return sp;
    }

    public static CWButton createButton(Action a) {
        CWButton button = new CWButton(a);
        button.setOpaque(false);
        return button;
    }

    public static JMenuItem createMenuItem(Action a) {
        JMenuItem item = new JMenuItem(a);
        return item;
    }

    public static JPopupMenu createPopupMenu() {
        JPopupMenu popupMenu = new JPopupMenu();
        return popupMenu;
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

        // Add both to the group
        createButtonGroup(bTrue, bFalse);

        // Create the panel
        JPanel panel = createPanel();
        panel.add(bTrue);
        panel.add(bFalse);

        // Create the Listener
        ItemListener itemListener;
        bTrue.addItemListener(itemListener = new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    value.setValue(true);
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    value.setValue(false);
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

    public static JCheckBox createCheckBox(ValueModel valueModel, String text) {
        JCheckBox checkBox = new JCheckBox(text);

        boolean enabled = checkBox.getModel().isEnabled();
        checkBox.setModel(new ToggleButtonAdapter(valueModel));
        checkBox.setEnabled(enabled);

        checkBox.setOpaque(false);
        return checkBox;
    }
    
    public static JXTree createTree(TreeModel treeModel) {
        JXTree tree = new JXTree(treeModel);
        tree.setHighlighters(HighlighterFactory.createSimpleStriping());
        tree.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        tree.setShowsRootHandles(true);
        return tree;
    }

    public static <E> JComboBox createComboBox(SelectionInList<E> selectionInList) {
        JComboBox comboBox = new JComboBox();

        comboBox.setModel(new ComboBoxAdapter<E>(selectionInList));

        return comboBox;
    }

    public static JFormattedTextField createFormattedTextField(ValueModel valueModel, Format format) {
        JFormattedTextField textField = new JFormattedTextField(format);

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
        return createLabel(new ValueHolder(text), icon, null);
    }

    public static CWLabel createLabel(ValueModel valueModel, Icon icon) {
        return createLabel(valueModel, icon, null);
    }

    public static CWLabel createLabel(final ValueModel valueModel, Icon icon, final Format format) {
        CWLabel label = new CWLabel();

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

        PropertyConnector connector = PropertyConnector.connect(newValueModel, "value", label, "text");
        connector.updateProperty2();
        label.putClientProperty(PROPERTY_CONNECTOR_KEY, connector);

        if(icon != null) {
            label.setIcon(icon);
        }
        label.setOpaque(false);
        return label;
    }

    public static CWLabel createLabelBoolean(final ValueModel valueModel, final String trueString, final String falseString) {

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
        return createBackView(panel, "Zurück");
    }

    public static CWBackView createBackView(CWPanel panel, String backText) {
        CWBackView backView = new CWBackView(panel, backText);
        return backView;
    }

    public static CWView createViewPanel() {
        return new CWView();
    }

    public static CWView createViewPanel(CWHeaderInfo headerInfo) {
        return createViewPanel(headerInfo, null);
    }

    public static CWView createViewPanel(String headerText, JComponent comp) {
        return createViewPanel(new CWHeaderInfo(headerText), comp);
    }

    public static CWView createViewPanel( JComponent comp) {
        return createViewPanel(new CWHeaderInfo(), comp);
    }

    public static CWView createViewPanel(CWHeaderInfo headerInfo, JComponent comp) {
        if(headerInfo == null) headerInfo = new CWHeaderInfo();
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
}
