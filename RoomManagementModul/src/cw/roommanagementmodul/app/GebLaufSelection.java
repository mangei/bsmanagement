package cw.roommanagementmodul.app;

import com.jgoodies.binding.beans.Model;
import cw.roommanagementmodul.geblauf.BewohnerGebSelection;
import cw.roommanagementmodul.geblauf.BewohnerTarifSelection;
import cw.roommanagementmodul.geblauf.GebTarifSelection;
import cw.roommanagementmodul.geblauf.GebuehrAnm;
import java.util.ArrayList;
import java.util.List;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.GebuehrZuordnung;
import cw.roommanagementmodul.pojo.Tarif;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import cw.roommanagementmodul.pojo.manager.GebuehrZuordnungManager;
import cw.roommanagementmodul.pojo.manager.TarifManager;

/**
 *
 * @author Dominik
 */
public class GebLaufSelection extends Model {

    private long abrMonat;
    private int jahr;
    private int monat;
    private BewohnerManager bewohnerManager;
    private TarifManager tarifManager;
    private GebuehrZuordnungManager gebZuordnungManager;
    private BewohnerTarifSelection bewohnerTarifSelection;
    public final static String PROPERTYNAME_JAHR = "jahr";
    public final static String PROPERTYNAME_MONAT = "monat";

    public GebLaufSelection() {
        bewohnerManager = BewohnerManager.getInstance();
        tarifManager = TarifManager.getInstance();
        gebZuordnungManager = GebuehrZuordnungManager.getInstance();
    }

    public BewohnerTarifSelection startSelection(long abrMonat) {

        this.setAbrMonat(abrMonat);
        List<Bewohner> bewohnerList = selectBewohnerList();
        List<BewohnerGebSelection> bewGebSelection = selectGebBewohner(bewohnerList);

        this.bewohnerTarifSelection = selectGebTarif(bewGebSelection);

        return this.bewohnerTarifSelection;

    }

    private List<Bewohner> selectBewohnerList() {
        return selectBewohnerList(this.getAbrMonat());
    }

    public List<Bewohner> selectBewohnerList(long abrMonat) {

        List<Bewohner> bewohnerList = bewohnerManager.getBewohner(true);
        
        //Das Einzugs und Auszugsdatum soll keine Auswirkung auf die Selection haben
        //------------------------------------------------------------
//        List<Bewohner> selectedBewohner = new ArrayList<Bewohner>();
//        for (int i = 0; i < bewohnerList.size(); i++) {
//            if (bewohnerList.get(i).getVon().getTime() <= abrMonat) {
//                if (bewohnerList.get(i).getBis() == null || abrMonat <= bewohnerList.get(i).getBis().getTime()) {
//                    selectedBewohner.add(bewohnerList.get(i));
//                }
//            }
//        }
//        return selectedBewohner;
//---------------------------------------------------------------------

        return bewohnerList;
    }

    private List<BewohnerGebSelection> selectGebBewohner(List<Bewohner> selectedBewohner) {

        List<BewohnerGebSelection> bewohnerGebSelection = new ArrayList<BewohnerGebSelection>();

        for (int i = 0; i < selectedBewohner.size(); i++) {
            List<GebuehrAnm> selectedGebuehr = new ArrayList<GebuehrAnm>();
            List<GebuehrZuordnung> gebZuordnungList = gebZuordnungManager.getGebuehrZuordnung(selectedBewohner.get(i));

            for (int j = 0; j < gebZuordnungList.size(); j++) {

                if (gebZuordnungList.get(j).getVon().getTime() <= this.getAbrMonat()) {
                    if (gebZuordnungList.get(j).getBis() == null || getAbrMonat() <= gebZuordnungList.get(j).getBis().getTime()) {
                        selectedGebuehr.add(new GebuehrAnm(gebZuordnungList.get(j).getGebuehr(), gebZuordnungList.get(j).getAnmerkung()));
                    }
                }
            }
            if (selectedGebuehr.size() != 0) {
                bewohnerGebSelection.add(new BewohnerGebSelection( this.getAbrMonat(),selectedBewohner.get(i), selectedGebuehr, false));
            } else {
                bewohnerGebSelection.add(new BewohnerGebSelection( this.getAbrMonat(),selectedBewohner.get(i), null, true));
            }
        }
        return bewohnerGebSelection;
    }

    private BewohnerTarifSelection selectGebTarif(List<BewohnerGebSelection> selectedGebuehren) {


        BewohnerTarifSelection bewTarifSelection = new BewohnerTarifSelection();

        for (int i = 0; i < selectedGebuehren.size(); i++) {

            BewohnerGebSelection bewGeb = selectedGebuehren.get(i);
            Bewohner b = bewGeb.getBewohner();
            boolean noTarifError = false;
            boolean moreTarifError = false;
            List<GebTarifSelection> gebTarifList = new ArrayList<GebTarifSelection>();

            if (bewGeb.getGebList() != null) {
                for (int j = 0; j < bewGeb.getGebList().size(); j++) {
                    Tarif tarif = null;
                    GebuehrAnm gebuehrAnm = bewGeb.getGebList().get(j);
                    List<Tarif> tarifList = tarifManager.getTarif(gebuehrAnm.getGebuehr());
                    List<Tarif> selectedTarif = getSelectedTarif(tarifList,getAbrMonat());

                    if (selectedTarif.size() == 0) {
                        noTarifError = true;
                    }
                    if (selectedTarif.size() > 1) {
                        moreTarifError = true;
                    }
                    if (selectedTarif.size() == 1) {
                        tarif = selectedTarif.get(0);
                        noTarifError = false;
                        moreTarifError = false;
                    }

                    gebTarifList.add(new GebTarifSelection(getAbrMonat(),gebuehrAnm, tarif, bewGeb.isWarning(), noTarifError, moreTarifError));
                }
            } else {
                gebTarifList.add(new GebTarifSelection(getAbrMonat(), null, null, bewGeb.isWarning(), false, false));
            }


            bewTarifSelection.put(b, gebTarifList);
        }
        return bewTarifSelection;

    }

    private List<Tarif> getSelectedTarif(List<Tarif> tarifList, long date) {

        List<Tarif> selectedTarif = new ArrayList<Tarif>();
        for (int i = 0; i < tarifList.size(); i++) {
            if (tarifList.get(i).getAb().getTime() <= date) {
                if (tarifList.get(i).getBis() == null || date <= tarifList.get(i).getBis().getTime()) {
                    selectedTarif.add(tarifList.get(i));
                }
            }
        }
        return selectedTarif;
    }

    /**
     * @return the jahr
     */
    public int getJahr() {
        return jahr;
    }

    /**
     * @param jahr the jahr to set
     */
    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    /**
     * @return the monat
     */
    public int getMonat() {
        return monat;
    }

    /**
     * @param monat the monat to set
     */
    public void setMonat(int monat) {
        this.monat = monat;
    }

    /**
     * @return the abrMonat
     */
    public long getAbrMonat() {
        return abrMonat;
    }

    /**
     * @param abrMonat the abrMonat to set
     */
    public void setAbrMonat(long abrMonat) {
        this.abrMonat = abrMonat;
    }
}
