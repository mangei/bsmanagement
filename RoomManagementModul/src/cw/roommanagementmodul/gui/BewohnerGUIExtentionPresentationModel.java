package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import cw.roommanagementmodul.pojo.manager.BereichManager;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.ZimmerManager;
import cw.roommanagementmodul.pojo.Bereich;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.KautionManager;
import java.awt.event.ItemEvent;

/**
 *
 * @author Dominik
 */
public class BewohnerGUIExtentionPresentationModel 
        extends PresentationModel<Bewohner>
{

    private Bewohner bewohner;
    private ButtonListenerSupport support;
    private ValueModel unsaved;
    private ZimmerManager zimmerManager;
    private BereichManager bereichManager;
    private KautionManager kautionManager;
    private SelectionInList<Zimmer> zimmerList;
    private SelectionInList<Bereich> bereichList;
    private SelectionInList<Bereich> kautionList;
    private int checkBoxState;
    private ValueModel checkBoxModel;
    private CWHeaderInfo headerInfo;
    private CheckBoxListener checkBoxListener;
    private BereichPropertyListener bereichPropertyListener;
    private ZimmerPropertyListener zimmerPropertyListener;
    private SaveListener saveListener;
    private SelectionInList<String> kautionStadi;
    private KautionStatusItemListener kautionListener;

    BewohnerGUIExtentionPresentationModel(BewohnerManager bewohnerManager, Bewohner b, ValueModel unsaved) {
        super(b);
        this.bewohner = b;
        checkBoxState = ItemEvent.DESELECTED;
        zimmerManager = ZimmerManager.getInstance();
        bereichManager = BereichManager.getInstance();
        kautionManager= KautionManager.getInstance();
        kautionListener= new KautionStatusItemListener();
        this.unsaved = unsaved;

        initModels();
        initEventHandling();

    }

    private void initModels() {
        headerInfo = new CWHeaderInfo("Zimmer Zuweisung", "Hier können Sie einem Kunden ein Zimmer zuweisen und machen ihn damit zu einem Bewohner", CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"), CWUtils.loadIcon("cw/roommanagementmodul/images/door.png"));

        checkBoxModel = new ValueHolder();
        checkBoxListener= new CheckBoxListener();
        checkBoxModel.addValueChangeListener(checkBoxListener);
        checkBoxModel.setValue(bewohner.isActive());

        support = new ButtonListenerSupport();

        kautionList = new SelectionInList(kautionManager.getAll(),getModel(Bewohner.PROPERTYNAME_KAUTION));
        kautionList.getList().add(null);
        bereichList = new SelectionInList(extractLeafBereich(bereichManager.getBereich()));
        bereichPropertyListener= new BereichPropertyListener();
        bereichList.addValueChangeListener(bereichPropertyListener);

        zimmerList = new SelectionInList(zimmerManager.getAll(), getModel(Bewohner.PROPERTYNAME_ZIMMER));

        List<String> kList=new ArrayList<String>();
        kList.add("Nicht eingezahlt");
        kList.add("Eingezahlt");
        kList.add("Zurück gezahlt");
        kList.add("Eingezogen");
        kautionStadi = (SelectionInList<String>) new SelectionInList(kList);

        if (getBewohner().getZimmer() != null) {
            bereichList.setSelection(getBewohner().getZimmer().getBereich());
            Bereich b = getBewohner().getZimmer().getBereich();
            bereichManager.refreshBereich(b);
            if (b != null) {
                zimmerList.setList(b.getZimmerList());
            } else {
                zimmerList.setList(zimmerManager.getAll());
            }
        } else {
            zimmerList.setList(zimmerManager.getAll());
        }
    }

    private void initEventHandling() {
        zimmerPropertyListener = new ZimmerPropertyListener();
        zimmerList.addValueChangeListener(zimmerPropertyListener);

        getBufferedModel(Bewohner.PROPERTYNAME_ZIMMER).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_VON).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_BIS).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTION).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTIONSTATUS).addValueChangeListener(new SaveListener());
        saveListener= new SaveListener();
        zimmerList.addValueChangeListener(saveListener);
        checkBoxModel.addValueChangeListener(saveListener);
        kautionList.addValueChangeListener(saveListener);

        updateActionEnablement();
    }

    public void dispose() {
        checkBoxModel.removeValueChangeListener(this.checkBoxListener);
        bereichList.removeValueChangeListener(bereichPropertyListener);
        zimmerList.removeValueChangeListener(this.zimmerPropertyListener);
        zimmerList.removeValueChangeListener(this.saveListener);
        checkBoxModel.removeValueChangeListener(this.saveListener);
        kautionList.removeValueChangeListener(this.saveListener);
        release();

    }

    private List<Bereich> extractLeafBereich(List<Bereich> l) {
        List<Bereich> extractList = new ArrayList<Bereich>();
        for (int i = 0; i < l.size(); i++) {
            if (l.get(i).getChildBereichList().size() == 0) {
                extractList.add(l.get(i));
            }
        }
        return extractList;
    }

    private void updateActionEnablement() {
    }

    public SelectionInList<Zimmer> getZimmerList() {
        return zimmerList;
    }

    public ComboBoxModel createBereichComboModel(SelectionInList bereichList) {
        return new ComboBoxAdapter(bereichList);
    }

    public ComboBoxModel createZimmerComboModel(SelectionInList zimmerList) {
        return new ComboBoxAdapter(zimmerList);
    }

    public ComboBoxModel createKautionComboModel(SelectionInList kautionList) {
        return new ComboBoxAdapter(kautionList);
    }

    public SelectionInList<Bereich> getBereichList() {
        return bereichList;
    }

    /**
     * @return the unsaved
     */
    public ValueModel getUnsaved() {
        return unsaved;
    }

    /**
     * @return the bewohner
     */
    public Bewohner getBewohner() {
        return bewohner;
    }

    /**
     * @return the checkBoxState
     */
    public int getCheckBoxState() {
        return checkBoxState;
    }

    /**
     * @return the checkBoxModel
     */
    public ValueModel getCheckBoxModel() {
        return checkBoxModel;
    }

    /**
     * @return the headerInfo
     */
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     * @return the kautionList
     */
    public SelectionInList<Bereich> getKautionList() {
        return kautionList;
    }

    /**
     * @return the kautionStadi
     */
    public SelectionInList<String> getKautionStadi() {
        return kautionStadi;
    }

    /**
     * @return the kautionListener
     */
    public KautionStatusItemListener getKautionListener() {
        return kautionListener;
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            getUnsaved().setValue(true);
        }
    }

    public class KautionStatusItemListener implements ItemListener {

        public void itemStateChanged(ItemEvent e) {

            if (e.getItem().equals("Eingezogen")) {
                getBewohner().setKautionStatus(Bewohner.EINGEZOGEN);
                unsaved.setValue(true);

            }
            if (e.getItem().equals("Nicht eingezahlt")) {
                getBewohner().setKautionStatus(Bewohner.NICHT_EINGEZAHLT);
                unsaved.setValue(true);
            }
            if (e.getItem().equals("Eingezahlt")) {
                getBewohner().setKautionStatus(Bewohner.EINGEZAHLT);
                unsaved.setValue(true);

            }
            if (e.getItem().equals("Zurück gezahlt")) {
                getBewohner().setKautionStatus(Bewohner.ZURUECK_GEZAHLT);
                unsaved.setValue(true);
            }
        }
    }

    public class ZimmerPropertyListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            Zimmer z = (Zimmer) evt.getNewValue();

            List<Bereich> l = bereichList.getList();
            List<Zimmer> zList;
            for (int i = 0; i < l.size(); i++) {
                bereichManager.refreshBereich(l.get(i));
                zList = l.get(i).getZimmerList();
                for (int j = 0; j < zList.size(); j++) {
                    if (zList.get(j).equals(z)) {
                        bereichList.setSelection(l.get(i));
                    }
                }
            }

        }
    }

    public class BereichPropertyListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            Bereich b = (Bereich) evt.getNewValue();

            if (b != null) {
                bereichManager.refreshBereich(b);
                zimmerList.setList(b.getZimmerList());
            } else {
                zimmerList.setList(zimmerManager.getAll());
            }
        }
    }

    public class CheckBoxListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            if ((Boolean) evt.getNewValue() == true) {
                checkBoxState = ItemEvent.SELECTED;

            } else {
                checkBoxState = ItemEvent.DESELECTED;
            }
        }
    }
}
