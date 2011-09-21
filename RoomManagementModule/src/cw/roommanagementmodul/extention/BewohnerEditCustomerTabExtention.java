package cw.roommanagementmodul.extention;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;

import cw.boardingschoolmanagement.extention.point.CWIEditViewExtentionPoint;
import cw.boardingschoolmanagement.gui.CWIEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView;
import cw.customermanagementmodul.customer.gui.EditCustomerPresentationModel;
import cw.customermanagementmodul.customer.gui.EditCustomerView;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.roommanagementmodul.gui.BewohnerEditCustomerTabExtentionPresentationModel;
import cw.roommanagementmodul.gui.BewohnerEditCustomerTabExtentionView;
import cw.roommanagementmodul.persistence.Bewohner;
import cw.roommanagementmodul.persistence.PMBewohner;
import cw.roommanagementmodul.persistence.PMBewohnerHistory;
import cw.roommanagementmodul.persistence.Zimmer;

/**
 *
 * @author Dominik
 */
public class BewohnerEditCustomerTabExtention 
	implements CWIEditViewExtentionPoint<EditCustomerView, EditCustomerPresentationModel> {

    private PMBewohner bewohnerManager;
    private BewohnerEditCustomerTabExtentionView view;
    private PMBewohnerHistory historyManager;
    private BewohnerEditCustomerTabExtentionPresentationModel model;
    private Bewohner b;
    private Customer c;
    private Zimmer tempZimmer;

    public CWView<?> getView() {
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
                    JOptionPane.showMessageDialog(null, "Bewohner kann nicht geloescht werden, da der Status der Kaution EINGEZAHLT ist!", "Kaution", JOptionPane.OK_OPTION);
                    checkKaution = false;
                }
                    if (b.getKautionStatus() == Bewohner.NICHT_EINGEZAHLT) {
                    JOptionPane.showMessageDialog(null, "Bewohner kann nicht geloescht werden, da der Status der Kaution Nicht Eingezahlt ist!", "Kaution", JOptionPane.OK_OPTION);
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

    public CWIEditPresentationModel getModel() {
        return model;
    }

	@Override
	public Class<?> getExtentionClass() {
		return null;
	}

	@Override
	public void initView(EditCustomerView baseView) {
		view = new BewohnerEditCustomerTabExtentionView(model);
	}

	@Override
	public List<Class<?>> getExtentionClassList() {
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(EditCustomerView.class);
		list.add(EditCustomerPresentationModel.class);
		return list;
	}

	@Override
	public void initPresentationModel(EditCustomerPresentationModel baseModel) {
		bewohnerManager = PMBewohner.getInstance();
        historyManager = PMBewohnerHistory.getInstance();
        c = baseModel.getBean();
        b = bewohnerManager.getBewohner(baseModel.getBean());
        if (b == null) {
            b = new Bewohner();
        } else {
            tempZimmer = b.getZimmer();
        }


        model = new BewohnerEditCustomerTabExtentionPresentationModel(bewohnerManager, b, baseModel.getUnsaved());
	}

}
