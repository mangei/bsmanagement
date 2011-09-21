/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.geblauf;

import cw.roommanagementmodul.persistence.Gebuehr;

/**
 *
 * @author Dominik
 */
public class GebuehrAnm {

    private Gebuehr gebuehr;
    private String anmerkung;

    public GebuehrAnm() {
    }

    public GebuehrAnm(Gebuehr gebuehr, String anmerkung) {
        this.gebuehr = gebuehr;
        this.anmerkung = anmerkung;
    }

    /**
     * @return the gebuehr
     */
    public Gebuehr getGebuehr() {
        return gebuehr;
    }

    /**
     * @param gebuehr the gebuehr to set
     */
    public void setGebuehr(Gebuehr gebuehr) {
        this.gebuehr = gebuehr;
    }

    /**
     * @return the anmerkung
     */
    public String getAnmerkung() {
        return anmerkung;
    }

    /**
     * @param anmerkung the anmerkung to set
     */
    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    

}
