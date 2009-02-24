/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.customermanagementmodul.pojo.Customer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 *
 * @author Dominik Jeitler
 */

@Entity
@Table(name="Bewohner")
public class Bewohner extends Model implements AnnotatedClass{
    
    private Long id;
    private Customer customer;
//    private Customer einzahler;
    private Zimmer zimmer;
    private Kaution kaution;
    private int kautionStatus;
    private Date von;
    private Date bis;
    private boolean active;

    private List<BewohnerHistory> historyList = new ArrayList<BewohnerHistory>();
    
    public final static int KEINE_KAUTION = 0;
    public final static int NICHT_EINGEZAHLT =1;
    public final static int EINGEZAHLT = 2;
    public final static int ZURUECK_GEZAHLT = 3;

    public final static String PROPERTYNAME_COSTUMER = "costumer";
    public final static String PROPERTYNAME_EINZAHLER = "einzahler";
    public final static String PROPERTYNAME_ZIMMER = "zimmer";
    public final static String PROPERTYNAME_VON = "von";
    public final static String PROPERTYNAME_BIS = "bis";
    public final static String PROPERTYNAME_KAUTION = "kaution";
    public final static String PROPERTYNAME_KAUTIONSTATUS = "kautionStatus";
    
    
    public Bewohner(){
        
    }

    @Temporal(TemporalType.DATE)
    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
        this.bis = bis;
    }

//    @OneToOne
//    public Customer getEinzahler() {
//        return einzahler;
//    }
//
//    public void setEinzahler(Customer einzahler) {
//        this.einzahler = einzahler;
//    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @OneToOne(cascade=javax.persistence.CascadeType.ALL)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Temporal(TemporalType.DATE)
    public Date getVon() {
        return von;
    }

    public void setVon(Date von) {
        Date old = this.von;
        this.von = von;
        firePropertyChange(PROPERTYNAME_VON, old, von);
    }

    @ManyToOne
    public Zimmer getZimmer() {
        return zimmer;
    }

    public void setZimmer(Zimmer zimmer) {
        this.zimmer = zimmer;
    }
   

     
    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((Bewohner)obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the historyList
     */
    @OneToMany(mappedBy = "bewohner")
    public List<BewohnerHistory> getHistoryList() {
        return historyList;
    }

    /**
     * @param historyList the historyList to set
     */
    public void setHistoryList(List<BewohnerHistory> historyList) {
        this.historyList = historyList;
    }

    /**
     * @return the kaution
     */
     @ManyToOne
    public Kaution getKaution() {
        return kaution;
    }

    /**
     * @param kaution the kaution to set
     */
    public void setKaution(Kaution kaution) {
        this.kaution = kaution;
    }

    /**
     * @return the kautionStatus
     */
    public int getKautionStatus() {
        return kautionStatus;
    }

    /**
     * @param kautionStatus the kautionStatus to set
     */
    public void setKautionStatus(int kautionStatus) {
        this.kautionStatus = kautionStatus;
    }
}
