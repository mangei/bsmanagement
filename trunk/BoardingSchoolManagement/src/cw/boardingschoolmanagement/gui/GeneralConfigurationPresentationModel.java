package cw.boardingschoolmanagement.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;

import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWPathPanel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;

/**
 *
 * @author ManuelG
 */
public class GeneralConfigurationPresentationModel
	extends CWPresentationModel
{

    private ConfigurationPresentationModel configurationPresentationModel;
    private CWHeaderInfo headerInfo;
    private HashMap generalConfigruationMap;
    private ValueModel pathPanelActiveModel;
    private SelectionInList<String> pathPanelPositionSelection;
    private SaveListener saveListener;

    public GeneralConfigurationPresentationModel(HashMap generalConfigruationMap, ConfigurationPresentationModel configurationPresentationModel) {
        super(null);
    	this.configurationPresentationModel = configurationPresentationModel;
        this.generalConfigruationMap = generalConfigruationMap;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Allgemein",
                "",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/general_configuration.png"),
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/general_configuration.png")
        );

        pathPanelActiveModel = new ValueHolder((Boolean)generalConfigruationMap.get("pathPanelActive"));
        pathPanelPositionSelection = new SelectionInList<String>();
        pathPanelPositionSelection.getList().add("Oben");
        pathPanelPositionSelection.getList().add("Unten");
        switch(CWPathPanel.PathPanelPosition.valueOf((String)generalConfigruationMap.get("pathPanelPosition"))) {
            case NORTH: pathPanelPositionSelection.setSelectionIndex(0); break;
            default:
            case SOUTH: pathPanelPositionSelection.setSelectionIndex(1);
        }
    }

    private void initEventHandling() {
        saveListener = new SaveListener();
        pathPanelActiveModel.addValueChangeListener(saveListener);
        pathPanelPositionSelection.addValueChangeListener(saveListener);
    }

    public void dispose() {
        pathPanelActiveModel.removeValueChangeListener(saveListener);
        pathPanelPositionSelection.removeValueChangeListener(saveListener);
        pathPanelPositionSelection.release();
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            configurationPresentationModel.setChanged(true);
        }
    }

    public CWPathPanel.PathPanelPosition getPathPanelPosition() {
        switch(pathPanelPositionSelection.getSelectionIndex()) {
            case 0: return CWPathPanel.PathPanelPosition.NORTH;
            default:
            case 1: return CWPathPanel.PathPanelPosition.SOUTH;
        }
    }

    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public List<String> validate() {
        return null;
    }

    public ValueModel getPathPanelActiveModel() {
        return pathPanelActiveModel;
    }

    public SelectionInList<String> getPathPanelPositionSelection() {
        return pathPanelPositionSelection;
    }

}
