/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import java.awt.event.ItemEvent;
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
import cw.roommanagementmodul.pojo.Kaution;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.KautionManager;

/**
 *
 * @author Dominik
 */
public class BewohnerGUIExtentionPresentationModel extends PresentationModel<Bewohner> {

    private Bewohner bewohner;
    private ButtonListenerSupport support;
    private ValueModel unsaved;
    private ZimmerManager zimmerManager;
    private BereichManager bereichManager;
    private KautionManager kautionManager;
    private SelectionInList<Zimmer> zimmerList;
    private SelectionInList<Bereich> bereichList;
    private SelectionInList<Kaution> kautionList;
    private KautionStatusItemListener kautionListener;

    BewohnerGUIExtentionPresentationModel(BewohnerManager bewohnerManager, Bewohner b, ValueModel unsaved) {
        super(b);
        this.bewohner = b;
        zimmerManager = ZimmerManager.getInstance();
        bereichManager = BereichManager.getInstance();
        kautionManager = KautionManager.getInstance();
        initModels();
        this.unsaved = unsaved;

    }

    private void initModels() {
        kautionListener= new KautionStatusItemListener();
        support = new ButtonListenerSupport();
        zimmerList = new SelectionInList(zimmerManager.getAll(), getModel(Bewohner.PROPERTYNAME_ZIMMER));
        zimmerList.addValueChangeListener(new ZimmerPropertyListener());
        kautionList= new SelectionInList(kautionManager.getAll(),getModel(Bewohner.PROPERTYNAME_KAUTION));
        bereichList = new SelectionInList(extractLeafBereich(bereichManager.getBereich()));
        bereichList.addValueChangeListener(new BereichPropertyListener());
        if (getBewohner().getZimmer() != null) {
            bereichList.setSelection(getBewohner().getZimmer().getBereich());
            Bereich b = getBewohner().getZimmer().getBereich();
            if (b != null) {
                zimmerList.setList(b.getZimmerList());
            } else {
                zimmerList.setList(zimmerManager.getAll());
            }
        }

        getBufferedModel(Bewohner.PROPERTYNAME_ZIMMER).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_VON).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_BIS).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTION).addValueChangeListener(new SaveListener());
        getBufferedModel(Bewohner.PROPERTYNAME_KAUTIONSTATUS).addValueChangeListener(new SaveListener());
        zimmerList.addValueChangeListener(new SaveListener());
        kautionList.addValueChangeListener(new SaveListener());

        updateActionEnablement();
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
     * @return the kautionList
     */
    public SelectionInList<Kaution> getKautionList() {
        return kautionList;
    }

    /**
     * @return the kautionListener
     */
    public KautionStatusItemListener getKautionListener() {
        return kautionListener;
    }

    /**
     * @return the unsaved
     */
    public ValueModel getUnsaved() {
        return unsaved;
    }

    /**
     * @param unsaved the unsaved to set
     */
    public void setUnsaved(ValueModel unsaved) {
        this.unsaved = unsaved;
    }

    /**
     * @return the bewohner
     */
    public Bewohner getBewohner() {
        return bewohner;
    }

    /**
     * @param bewohner the bewohner to set
     */
    public void setBewohner(Bewohner bewohner) {
        this.bewohner = bewohner;
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            getUnsaved().setValue(true);
        }
    }

        public class KautionStatusItemListener implements ItemListener{

        public void itemStateChanged(ItemEvent e) {

            if(e.getItem().equals("Keine Kaution")){
                getBewohner().setKautionStatus(Bewohner.KEINE_KAUTION);
               unsaved.setValue(true);

            }
            if(e.getItem().equals("Nicht eingezahlt")){
                getBewohner().setKautionStatus(Bewohner.NICHT_EINGEZAHLT);
                unsaved.setValue(true);
            }
            if(e.getItem().equals("Eingezahlt")){
                getBewohner().setKautionStatus(Bewohner.EINGEZAHLT);
                unsaved.setValue(true);

            }
            if(e.getItem().equals("Zurück gezahlt")){
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
                zimmerList.setList(b.getZimmerList());
            } else {
                zimmerList.setList(zimmerManager.getAll());
            }
        }
    }
   
}
