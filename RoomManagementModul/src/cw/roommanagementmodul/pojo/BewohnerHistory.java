/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dominik
 */
@Entity
public class BewohnerHistory extends Model implements AnnotatedClass{

    private Long id;
    private String zimmerName;
    private String bereichName;
    private Date von;
    private Date bis;
    private Date lastTimetamp;
    private Bewohner bewohner;

    public BewohnerHistory(){

    }

    public BewohnerHistory(String zimmerName,String bereichName ,Date von, Date bis, Bewohner bewohner) {
        this.zimmerName = zimmerName;
        this.bereichName=bereichName;
        this.von = von;
        this.bis = bis;
        this.bewohner = bewohner;
    }


    public BewohnerHistory(String zimmerName,String bereichName ,Date von, Date bis, Bewohner bewohner, Date timestamp) {
        this.zimmerName = zimmerName;
        this.bereichName=bereichName;
        this.von = von;
        this.bis = bis;
        this.bewohner = bewohner;
        this.lastTimetamp=timestamp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the zimmerName
     */
    public String getZimmerName() {
        return zimmerName;
    }

    /**
     * @param zimmerName the zimmerName to set
     */
    public void setZimmerName(String zimmerName) {
        this.zimmerName = zimmerName;
    }

    /**
     * @return the von
     */
    @Temporal(TemporalType.DATE)
    public Date getVon() {
        return von;
    }

    /**
     * @param von the von to set
     */
    public void setVon(Date von) {
        this.von = von;
    }

    /**
     * @return the bis
     */
    @Temporal(TemporalType.DATE)
    public Date getBis() {
        return bis;
    }

    /**
     * @param bis the bis to set
     */
    public void setBis(Date bis) {
        this.bis = bis;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((BewohnerHistory) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the bewohner
     */
    @ManyToOne
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
     * @return the bereichName
     */
    public String getBereichName() {
        return bereichName;
    }

    /**
     * @param bereichName the bereichName to set
     */
    public void setBereichName(String bereichName) {
        this.bereichName = bereichName;
    }

    /**
     * @return the timestamp
     */
    @Temporal(TemporalType.DATE)
    public Date getLastDatestamp() {
        return lastTimetamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setLastDatestamp(Date timestamp) {
        this.lastTimetamp = timestamp;
    }
}
