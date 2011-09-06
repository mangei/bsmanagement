package cw.roommanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.roommanagementmodul.gui.*;
import cw.customermanagementmodul.extention.point.EditCustomerTabExtentionPoint;
import cw.customermanagementmodul.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.persistence.model.CustomerModel;

import java.util.List;
import javax.swing.JComponent;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.Zimmer;
import cw.roommanagementmodul.pojo.manager.BewohnerHistoryManager;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class BewohnerEditCustomerTabExtention implements EditCustomerTabExtentionPoint {

    private BewohnerManager bewohnerManager;
    private BewohnerEditCustomerTabExtentionView view;
    private BewohnerHistoryManager historyManager;
    private BewohnerEditCustomerTabExtentionPresentationModel model;
    private Bewohner b;
    private CustomerModel c;
    private Zimmer tempZimmer;

    public CWPanel getView() {
        view=new BewohnerEditCustomerTabExtentionView(model);
        return view;
    }

    public void save() {
        model.triggerCommit();

        if(b.getKaution()==null){
            b.setKautionStatus(-1);
        }
        if (model.getCheckBoxState() == ItemEvent.SELECTED) {

            //Stunde - Minute - Sekunde auf 0 setzten damit auf Gleichheit geprueft werden kann
            Calendar von = new GregorianCalendar();
            von.setTimeInMillis(b.getVon().getTime());
            Calendar vonC = new GregorianCalendar(von.get(Calendar.YEAR), von.get(Calendar.MONTH), von.get(Calendar.DATE));
            b.setVon(new Date(vonC.getTimeInMillis()));

            if (b.getBis() != null) {
                Calendar bis = new GregorianCalendar();
                bis.setTimeInMillis(b.getBis().getTime());
                Calendar bisC = new GregorianCalendar(bis.get(Calendar.YEAR), bis.get(Calendar.MONTH), bis.get(Calendar.DATE));
                b.setBis(new Date(bisC.getTimeInMillis()));
            }
            //--------------------------------------------------------------------------------

            if (checkChrono() == true) {
                b.setCustomer(c);
                b.setActive(true);
                bewohnerManager.save(b);
            }


        }
        if (model.getCheckBoxState() == ItemEvent.DESELECTED) {
             boolean checkKaution = true;
                if (b.getKautionStatus() == Bewohner.EINGEZAHLT) {
                    JOptionPane.showMessageDialog(null, "Bewohner kann nicht gelöscht werden, da der Status der Kaution EINGEZAHLT ist!", "Kaution", JOptionPane.OK_OPTION);
                    checkKaution = false;
                }
                    if (b.getKautionStatus() == Bewohner.NICHT_EINGEZAHLT) {
                    JOptionPane.showMessageDialog(null, "Bewohner kann nicht gelöscht werden, da der Status der Kaution Nicht Eingezahlt ist!", "Kaution", JOptionPane.OK_OPTION);
                    checkKaution = false;
                }
                if (checkKaution==true) {
                    System.out.println("hallo");
                    b.setCustomer(null);
                    bewohnerManager.delete(b);
                    System.out.println("temse");
                }else{
                 model.getCheckBoxModel().setValue(true);
                }
        }

    }

    //Kontroliert ob das Bis-Datum chronologisch nach dem Von-Datum ist
    private boolean checkChrono() {
        if (b.getBis() != null) {
            Date bis = (Date) model.getBufferedModel(Bewohner.PROPERTYNAME_BIS).getValue();
            Date von = (Date) model.getBufferedModel(Bewohner.PROPERTYNAME_VON).getValue();

            if (bis != null) {
                if (bis.getTime() <= von.getTime()) {
                    return false;
                }
            }

        }
        return true;
    }

    public void reset() {
        model.triggerFlush();
    }

    public List<String> validate() {
        ArrayList<String> l = new ArrayList<String>();
        if (model.getCheckBoxState() == ItemEvent.SELECTED && (model.getBufferedModel(Bewohner.PROPERTYNAME_ZIMMER).getValue() == null || model.getBufferedModel(Bewohner.PROPERTYNAME_VON).getValue() == null)) {
            l.add("In der Registerkarte Zimmer muessen die Attribute Einzugsdatum und Zimmer gesetzt sein!");
        }
        if (model.getCheckBoxState() == ItemEvent.SELECTED && checkChrono() == false) {
            l.add("In der Registerkarte Zimmer muss sich das Bis-Datum chronologisch nach dem Von-Datum befinden.");
        }

        return l;
    }

    public void initPresentationModel(EditCustomerPresentationModel editCustomerModel) {
        bewohnerManager = BewohnerManager.getInstance();
        historyManager = BewohnerHistoryManager.getInstance();
        c = editCustomerModel.getBean();
        b = bewohnerManager.getBewohner(editCustomerModel.getBean());
        if (b == null) {
            b = new Bewohner();
        } else {
            tempZimmer = b.getZimmer();
        }


        model = new BewohnerEditCustomerTabExtentionPresentationModel(bewohnerManager, b, editCustomerModel.getUnsaved());
    }

      public void dispose() {
        view.dispose();
    }

    public int priority() {
        return 0;
    }

    public Object getModel() {
        return model;
    }

}
