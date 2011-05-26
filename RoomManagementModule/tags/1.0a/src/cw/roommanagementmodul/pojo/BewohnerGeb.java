package cw.roommanagementmodul.pojo;

import cw.customermanagementmodul.pojo.Customer;
import cw.roommanagementmodul.geblauf.GebTarifSelection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dominik
 */
public class BewohnerGeb {

    private Long id;
    private Customer customer;
    private Zimmer zimmer;
    private Date von;
    private Date bis;
    private boolean active                  = true;
    private List<GebTarifSelection> gebList = new ArrayList<GebTarifSelection>();


    public BewohnerGeb(){
    }

    public BewohnerGeb(Bewohner b, List<GebTarifSelection> gebList){
        this.id=b.getId();
        this.customer=b.getCustomer();
        this.zimmer=b.getZimmer();
        this.von=b.getVon();
        this.bis=b.getBis();
        this.active=b.isActive();
        this.gebList=gebList;
    }

    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the zimmer
     */
    public Zimmer getZimmer() {
        return zimmer;
    }

    /**
     * @param zimmer the zimmer to set
     */
    public void setZimmer(Zimmer zimmer) {
        this.zimmer = zimmer;
    }

    /**
     * @return the von
     */
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
    public Date getBis() {
        return bis;
    }

    /**
     * @param bis the bis to set
     */
    public void setBis(Date bis) {
        this.bis = bis;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the gebList
     */
    public List<GebTarifSelection> getGebList() {
        return gebList;
    }

    /**
     * @param gebList the gebList to set
     */
    public void setGebList(List<GebTarifSelection> gebList) {
        this.gebList = gebList;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

}
