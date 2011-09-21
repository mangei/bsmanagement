/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.persistence;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 *
 * @author Dominik
 */
@Entity
public class BuchungsLaufZuordnung 
	extends CWPersistence
{
    public final static String ENTITY_NAME = "buchungs_lauf_zuordnung";
    public final static String TABLE_NAME = "buchungs_lauf_zuordnung";
    public final static String PROPERTYNAME_ID 				= "id";
    public final static String PROPERTYNAME_GEBLAUF 		= "geb_lauf";
    public final static String PROPERTYNAME_ACCOUNTPOSTING	= "account_posting";
    public final static String PROPERTYNAME_GEBUEHR 		= "gebuehr";

    private Long id;
    private GebLauf gebLauf;
    private AccountPosting accountPosting;
    private Gebuehr gebuehr;

    private BuchungsLaufZuordnung(){
        super(null);
    }

    BuchungsLaufZuordnung(EntityManager entityManager) {
    	super(entityManager);
    }

    

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the gebLauf
     */
    @ManyToOne
    public GebLauf getGebLauf() {
        return gebLauf;
    }

    /**
     * @param gebLauf the gebLauf to set
     */
    public void setGebLauf(GebLauf gebLauf) {
        this.gebLauf = gebLauf;
    }

    /**
     * @return the account
     */
    @ManyToOne
    public AccountPosting getPosting() {
        return accountPosting;
    }

    /**
     * @param account the account to set
     */
    public void setPosting(AccountPosting accountPosting) {
        this.accountPosting = accountPosting;
    }

    /**
     * @return the gebuehr
     */
    @ManyToOne
    public Gebuehr getGebuehr() {
        return gebuehr;
    }

    /**
     * @param gebuehr the gebuehr to set
     */
    public void setGebuehr(Gebuehr gebuehr) {
        this.gebuehr = gebuehr;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BuchungsLaufZuordnung other = (BuchungsLaufZuordnung) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    



}
