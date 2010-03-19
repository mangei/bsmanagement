package cw.boardingschoolmanagement.gui.component;

import cw.boardingschoolmanagement.app.CWUtils;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.factories.ButtonBarFactory;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

/**
 *
 * @author Manuel Geier
 */
public class JObjectChooser
extends JPanel
{
    
    private JLabel objectLabel;
    
    private JButton chooseButton;
    
    private JObjectChooserModel model;
    
    // Dialog
    private JObjectChooserPresentationModel dialogModel;
    private JObjectChooserView dialogView;
    
    public JObjectChooser() {
        this(null,null);
    }
    
    public JObjectChooser(ListModel list, Object selectedObject) {
        this(list, selectedObject, null);
    }
    
     public JObjectChooser(ListModel list, Object selectedObject, Action manageAction) {
         model = new JObjectChooserModel(list, selectedObject, manageAction);
         init();
     }
    
    public JObjectChooser(JObjectChooserModel m) {
        model = m;
        init();
    }
    
    private void init() {
        setName("JObjectChooser");
        setLayout(new BorderLayout());
        
        objectLabel = new JLabel(model.getSelectedObject().toString());
        chooseButton = new JButton("");
        
        dialogModel = new JObjectChooserPresentationModel(model.getObjectList(), model.getSelectedObject(), model.getManageAction());
        
//        PropertyConnector.connect(model.getSelectedObject(), "value", dialogModel.getSelection(), "value");
        model.getSelectedObject().addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                objectLabel.setText(model.getSelectedObject().toString());
            }
        });
        
        add(objectLabel, BorderLayout.CENTER);
        add(chooseButton, BorderLayout.EAST);
        
        chooseButton.setMargin(new Insets(0, 0, 0, 0));
        chooseButton.setAction(new AbstractAction("", new ImageIcon("images" + System.getProperty("file.separator") + "link_go.png")) {
            public void actionPerformed(ActionEvent e) {
                new JObjectChooserView(dialogModel);
            }
        });
    }
    
    public class JObjectChooserModel {
        
        private ListModel objectList;
        private ValueModel selectedObject;
        private Action manageAction;

        public JObjectChooserModel(ListModel objectList, Object selectedObject, Action manageAction) {
            this.objectList = objectList;
            this.selectedObject = new ValueHolder(selectedObject);
            this.manageAction = manageAction;
        }

        public Action getManageAction() {
            return manageAction;
        }

        public void setManageAction(Action manageAction) {
            this.manageAction = manageAction;
        }

        public ListModel getObjectList() {
            return objectList;
        }

        public void setObjectList(ListModel objectList) {
            this.objectList = objectList;
        }

        public ValueModel getSelectedObject() {
            return selectedObject;
        }

        public void setSelectedObject(ValueModel selectedObject) {
            this.selectedObject = selectedObject;
        }
    }
    
    private class JObjectChooserView 
    extends JDialog
    {

        private JObjectChooserPresentationModel model;
        private JList list;
        private JButton bManage;
        private JButton bOk;
        private JButton bCancel;
        
        public JObjectChooserView(JObjectChooserPresentationModel m) {
            model = m;
            setModal(true);

            add(buildPanel());

            setSize(300, 400);
            CWUtils.centerWindow(this);
            setVisible(true);
        }
        
        private void closeDialog() {
            setVisible(false);
            dispose();
        }
        
        public void initComponents() {
            // TODO nix todo
//            bManage = new JButton(model.getManageAction(), "buttons.edit");
//            bOk     = CWComponentFactory.createButton(model.getOkAction(), "buttons.confirm");
//            bCancel = CWComponentFactory.createButton(model.getCancelAction(), "buttons.cancel");
//
//            list = CWComponentFactory.createList(model.getObjectList());
        }
        
        public void initEventHandling() {
            bCancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeDialog();
                }
            });
            bOk.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeDialog();
                }
            });
            if(model.getManageAction() == null) {
                bManage.setVisible(false);
            }
        }
        
        public JPanel buildPanel() {
            initComponents();
            initEventHandling();
            
            JPanel panel = new JPanel(new BorderLayout());
            
            panel.add(list, BorderLayout.CENTER);
            panel.add(ButtonBarFactory.buildHelpBar(bManage, bOk, bCancel), BorderLayout.SOUTH);
            
            return panel;
        }
        
    }
    
    private class JObjectChooserPresentationModel {
    
        private SelectionInList<Object> objectList;
        private Action manageAction;
        private ValueModel activeObject;
        private ValueModel selectedObject;
        private Action okAction;
        private Action cancelAction;
        
        public JObjectChooserPresentationModel(ListModel list, ValueModel activeObject, Action manageAction) {
            
            this.activeObject = activeObject;
            selectedObject = new ValueHolder(activeObject.getValue());
            
            objectList = new SelectionInList<Object>(list, selectedObject);
            objectList.setSelection(activeObject.getValue());
            this.manageAction = manageAction;
            
            if(manageAction == null) {
                manageAction = new AbstractAction() {
                    public void actionPerformed(ActionEvent e) {
                    }
                };
                manageAction.setEnabled(false);
            }
            
            okAction = new OkAction();
            cancelAction = new CancelAction();
            
            initEventHandling();
        }
        
        public void initEventHandling() {
            objectList.getSelectionHolder().addValueChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    checkOkAction();
                }
            });
            checkOkAction();
        }
        
        private void checkOkAction() {
            if(getSelection().getValue() == null) {
                okAction.setEnabled(false);
            } else {
                okAction.setEnabled(true);
            }
        }
        
        public ValueModel getSelection() {
            return objectList.getSelectionHolder();
        }

        public Action getManageAction() {
            return manageAction;
        }

        public Action getOkAction() {
            return okAction;
        }

        public Action getCancelAction() {
            return cancelAction;
        }

        public SelectionInList getObjectList() {
            return objectList;
        }
        
        private class OkAction
                extends AbstractAction {

            public void actionPerformed(ActionEvent e) {
                activeObject.setValue(selectedObject.getValue());
            }
        }
        
        private class CancelAction
                extends AbstractAction {

            public void actionPerformed(ActionEvent e) {
                // ...
            }
        }
    }
}
