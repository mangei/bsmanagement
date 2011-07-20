/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.geblauf;

import java.util.List;
import cw.roommanagementmodul.pojo.Bewohner;

/**
 *
 * @author Dominik
 */
public class BewohnerGebSelection {

    private long abrMonat;
    private Bewohner bewohner;
    private List<GebuehrAnm> gebList;
    private boolean warning;

    public BewohnerGebSelection(long abrMonat, Bewohner bewohner, List<GebuehrAnm> gebList, boolean warning) {
        this.abrMonat = abrMonat;
        this.bewohner = bewohner;
        this.gebList = gebList;
        this.warning = warning;
    }



    public BewohnerGebSelection() {
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

    /**
     * @return the gebList
     */
    public List<GebuehrAnm> getGebList() {
        return gebList;
    }

    /**
     * @param gebList the gebList to set
     */
    public void setGebList(List<GebuehrAnm> gebList) {
        this.gebList = gebList;
    }

    /**
     * @return the warning
     */
    public boolean isWarning() {
        return warning;
    }

    /**
     * @param warning the warning to set
     */
    public void setWarning(boolean warning) {
        this.warning = warning;
    }




}
