/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.geblauf;

import cw.roommanagementmodul.persistence.Tarif;

/**
 *
 * @author Dominik
 */
public class GebTarifSelection {

    private long abrMonat;
    private GebuehrAnm gebuehr;
    private Tarif tarif;
    private boolean warning;
    private boolean noTarifError;
    private boolean moreTarifError;

    public GebTarifSelection() {
    }

    public GebTarifSelection(long abrMonat,  GebuehrAnm gebuehr, Tarif tarif, boolean warning, boolean noTarifError, boolean moreTarifError) {
        this.abrMonat = abrMonat;
        this.gebuehr = gebuehr;
        this.tarif = tarif;
        this.warning = warning;
        this.noTarifError = noTarifError;
        this.moreTarifError = moreTarifError;
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

    /**
     * @return the noTarifError
     */
    public boolean isNoTarifError() {
        return noTarifError;
    }

    /**
     * @param noTarifError the noTarifError to set
     */
    public void setNoTarifError(boolean noTarifError) {
        this.noTarifError = noTarifError;
    }

    /**
     * @return the moreTarifError
     */
    public boolean isMoreTarifError() {
        return moreTarifError;
    }

    /**
     * @param moreTarifError the moreTarifError to set
     */
    public void setMoreTarifError(boolean moreTarifError) {
        this.moreTarifError = moreTarifError;
    }

    /**
     * @return the gebuehr
     */
    public GebuehrAnm getGebuehr() {
        return gebuehr;
    }

    /**
     * @param gebuehr the gebuehr to set
     */
    public void setGebuehr(GebuehrAnm gebuehr) {
        this.gebuehr = gebuehr;
    }

    /**
     * @return the tarif
     */
    public Tarif getTarif() {
        return tarif;
    }

    /**
     * @param tarif the tarif to set
     */
    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }





}
