/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.persistence.AnnotatedClass;

/**
 *
 * @author Dominik
 */
@Entity
public class BuchungsLaufZuordnung implements AnnotatedClass{

    private Long id;
    private GebLauf gebLauf;
    private AccountPosting accountPosting;
    private Gebuehr gebuehr;

    public BuchungsLaufZuordnung(){
        
    }

    public BuchungsLaufZuordnung(GebLauf gebLauf, AccountPosting accountPosting, Gebuehr gebuehr) {
        this.gebLauf = gebLauf;
        this.accountPosting = accountPosting;
        this.gebuehr = gebuehr;
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
