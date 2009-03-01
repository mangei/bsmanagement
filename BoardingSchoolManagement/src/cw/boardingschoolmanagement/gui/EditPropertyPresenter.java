package cw.boardingschoolmanagement.gui;

import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonModel;
import javax.swing.DefaultButtonModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import cw.boardingschoolmanagement.pojo.Property;

/**
 *
 * @author CreativeWorkers.at
 */
public class EditPropertyPresenter {

    private EditPropertyView view;
    private Property property;
    
    private ButtonModel cancelButtonModel;
    private Action cancelButtonAction;
    private ButtonModel okButtonModel;
    private Action okButtonAction;
    private ValueModel nameLabelModel; 
    private ValueModel valueLabelModel; 
    private Document nameDocumentModel;
    private Document valueDocumentModel;

    public EditPropertyPresenter(Property property) {
        this.property = property;
        
        
        cancelButtonModel = new DefaultButtonModel();
        cancelButtonAction = new CancelAction("Abbrechen");
        
        okButtonModel = new DefaultButtonModel();
        okButtonAction = new OkAction("Bestätigen");
        
        nameLabelModel = new ValueHolder("Name");
        valueLabelModel = new ValueHolder("Wert"); 
        nameDocumentModel = new PlainDocument();
        valueDocumentModel = new PlainDocument();
        
        try {
            nameDocumentModel.insertString(0, property.getName(), null);
            valueDocumentModel.insertString(0, property.getValue(), null);
        } catch (BadLocationException ex) {
            Logger.getLogger(EditPropertyPresenter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ButtonModel getCancelButtonModel() {
        return cancelButtonModel;
    }
    
    public Action getCancelButtonAction() {
        return cancelButtonAction;
    }
    
    public ButtonModel getOKButtonModel() {
        return okButtonModel;
    }
    
    public Action getOKButtonAction() {
        return okButtonAction;
    }

    public ValueModel getNameLabelModel() {
        return nameLabelModel;
    }

    public ValueModel getValueLabelModel() {
        return valueLabelModel;
    }

    public Document getNameDocumentModel() {
        return nameDocumentModel;
    }

    public Document getValueDocumentModel() {
        return valueDocumentModel;
    }
    
    private class OkAction
    extends AbstractAction {

        public OkAction(String name) {
            super(name);
        }
        
        public void actionPerformed(ActionEvent e) {
            try {
                System.out.println("propertyChange");
                property.setName(nameDocumentModel.getText(0, nameDocumentModel.getLength()));
                property.setValue(valueDocumentModel.getText(0, valueDocumentModel.getLength()));
                view.setVisible(false);
                view.dispose();
            } catch (BadLocationException ex) {
                Logger.getLogger(EditPropertyPresenter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private class CancelAction
    extends AbstractAction {

        public CancelAction(String name) {
            super(name);
        }
        
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            view.dispose();
        }
    }

    public EditPropertyView getView() {
        return view;
    }

    public void setView(EditPropertyView view) {
        this.view = view;
    }
    
}
