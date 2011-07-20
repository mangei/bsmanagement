/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.customermanagementmodul.pojo.Posting;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Dominik
 */
@Entity
public class BuchungsLaufZuordnung implements AnnotatedClass{

    private Long id;
    private GebLauf gebLauf;
    private Posting posting;
    private Gebuehr gebuehr;

    public BuchungsLaufZuordnung(){
        
    }

    public BuchungsLaufZuordnung(GebLauf gebLauf, Posting posting, Gebuehr gebuehr) {
        this.gebLauf = gebLauf;
        this.posting = posting;
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
    public Posting getPosting() {
        return posting;
    }

    /**
     * @param account the account to set
     */
    public void setPosting(Posting posting) {
        this.posting = posting;
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
