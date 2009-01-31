/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.customermanagementmodul.pojo.Posting;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Dominik
 */
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
    public Posting getPosting() {
        return posting;
    }

    /**
     * @param account the account to set
     */
    public void setAccount(Posting posting) {
        this.posting = posting;
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

    



}
