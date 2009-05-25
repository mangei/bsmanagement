package cw.boardingschoolmanagement.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.comparator.PriorityComparator;
import cw.boardingschoolmanagement.extentions.interfaces.ConfigurationExtention;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.ModulManager;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 *
 * @author ManuelG
 */
public class ConfigurationPresentationModel extends PresentationModel
{

    private CWHeaderInfo headerInfo;
    private List<ConfigurationExtention> configurationExtentions;
    private ButtonListenerSupport support;
    private Action saveAction;
    private Action cancelAction;
    private PropertyChangeListener actionButtonListener;

    public ConfigurationPresentationModel() {
        super(new Model() {});
        initModels();
        initEventHandling();
    }

    public void initModels() {
        saveAction = new SaveAction("Speichern", CWUtils.loadIcon("cw/boardingschoolmanagement/images/save.png"));
        cancelAction = new CancelAction("Abbrechen", CWUtils.loadIcon("cw/boardingschoolmanagement/images/cancel.png"));

        support = new ButtonListenerSupport();

        configurationExtentions = getExtentions();
        for (ConfigurationExtention extention : configurationExtentions) {
            extention.initPresentationModel(this);
        }

        headerInfo = new CWHeaderInfo(
                "Einstellungen",
                "Hier können Sie Ihre Einstellungen vornehmen.",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/configuration.png"),
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/configuration.png")
        );
    }

    public void initEventHandling() {
        addPropertyChangeListener(PROPERTYNAME_CHANGED, actionButtonListener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                updateActions();
            }
        });
        updateActions();
        setChanged(false);
    }
    
    public void dispose() {
        getTriggerChannel().removeValueChangeListener(actionButtonListener);

        for (ConfigurationExtention ex : configurationExtentions) {
            ex.dispose();
        }
    }

    public List<JComponent> getExtentionComponents() {
        List<JComponent> comps = new ArrayList<JComponent>();
        for (ConfigurationExtention ex : configurationExtentions) {
            comps.add((ex).getView());
        }
        return comps;
    }

    public List<ConfigurationExtention> getExtentions() {
        if (configurationExtentions == null) {
            configurationExtentions = (List<ConfigurationExtention>) ModulManager.getExtentions(ConfigurationExtention.class);

            Collections.sort(configurationExtentions, new PriorityComparator());

        }
        return configurationExtentions;
    }

    public ConfigurationExtention getExtention(Class extentionClass) {

        for (ConfigurationExtention ex : configurationExtentions) {
            if (extentionClass.isInstance(ex)) {
                return ex;
            }
        }

        return null;
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////
    
    private class SaveAction
            extends AbstractAction {

        private SaveAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            // Fire only when the save-method worked correct
            if (save()) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.SAVE_BUTTON));
            }

        }
    }

    private class CancelAction
            extends AbstractAction {

        private CancelAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            int i = 1;
            if ((Boolean) isChanged()) {
                Object[] options = {"Speichern", "Nicht Speichern", "Abbrechen"};
                i = JOptionPane.showOptionDialog(null, "Daten wurden geändert. Wollen Sie die Änderungen speichern?", "Speichern", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            }
            if (i == 0) {
                // If the save-method doesn't worked, because of an error, to nothing
                if (!save()) {
                    return;
                }
            }
            if (i == 0 || i == 1) {
                support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));
            }
        }
    }

    public boolean save() {
        boolean valid = true;

        List<ConfigurationExtention> extentions = getExtentions();
        List<String> errorMessages = new ArrayList<String>();
        for (ConfigurationExtention extention : extentions) {
            List<String> errorList = extention.validate();
            if (errorList != null) {
                if (errorList.size() > 0) {
                    valid = false;
                    errorMessages.addAll(errorList);
                }
            }
        }

        if (!valid) {

            StringBuffer buffer = new StringBuffer("<html>");

            for (String message : errorMessages) {
                buffer.append(message);
                buffer.append("<br>");
            }

            buffer.append("</html>");

            JOptionPane.showMessageDialog(null, buffer.toString(), "Fehler und Warnungen", JOptionPane.ERROR_MESSAGE);

            return false;
        }

        triggerCommit();

        for (ConfigurationExtention extention : extentions) {
            extention.save();
        }

        setChanged(true);

        return true;
    }

    @Override
    protected void setChanged(boolean newValue) {
        super.setChanged(newValue);
    }

    private void updateActions() {
        if (isChanged()) {
            saveAction.setEnabled(true);
        } else {
            saveAction.setEnabled(false);
        }
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public Action getCancelAction() {
        return cancelAction;
    }

    public Action getSaveAction() {
        return saveAction;
    }

}
