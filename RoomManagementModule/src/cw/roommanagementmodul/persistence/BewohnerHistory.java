/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.persistence;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 *
 * @author Dominik
 */
@Entity
public class BewohnerHistory 
	extends CWPersistence
{
	
    public final static String ENTITY_NAME 	= "bewohner_history";
    public final static String TABLE_NAME 	= "bewohner_history";
    public final static String PROPERTYNAME_ID 				= "id";
    public final static String PROPERTYNAME_ZIMMERNAME 		= "zimmer_name";
    public final static String PROPERTYNAME_BEREICHNAME 	= "bereich_name";
    public final static String PROPERTYNAME_VON 			= "von";
    public final static String PROPERTYNAME_BIS 			= "bis";
    public final static String PROPERTYNAME_LASTTIMESTAMP 	= "last_timestamp";
    public final static String PROPERTYNAME_BEWOHNER 		= "bewohner";

    private Long id;
    private String zimmerName;
    private String bereichName;
    private Date von;
    private Date bis;
    private Date lastTimetamp;
    private Bewohner bewohner;
    
    private BewohnerHistory(){
    	super(null);
    }

    BewohnerHistory(EntityManager entityManager) {
    	super(entityManager);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    	Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
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
    	String old = this.zimmerName;
        this.zimmerName = zimmerName;
        firePropertyChange(PROPERTYNAME_ZIMMERNAME, old, zimmerName);
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
    	Date old = this.von;
        this.von = von;
        firePropertyChange(PROPERTYNAME_VON, old, von);
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
    	Date old = this.bis;
        this.bis = bis;
        firePropertyChange(PROPERTYNAME_BIS, old, bis);
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
    	Bewohner old = this.bewohner;
        this.bewohner = bewohner;
        firePropertyChange(PROPERTYNAME_BEWOHNER, old, bewohner);
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
    	String old = this.bereichName;
        this.bereichName = bereichName;
        firePropertyChange(PROPERTYNAME_BEREICHNAME, old, bereichName);
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
    public void setLastDatestamp(Date lastTimetamp) {
    	Date old = this.lastTimetamp;
        this.lastTimetamp = lastTimetamp;
        firePropertyChange(PROPERTYNAME_LASTTIMESTAMP, old, lastTimetamp);
    }
}
