package cw.roommanagementmodul.pojo;

import java.util.List;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.customermanagementmodul.persistence.model.CustomerModel;

/**
 *
 * @author Dominik
 */
public class BewohnerStorno {

    private Long id;
    private CustomerModel customer;
    private Zimmer zimmer;
    private List<AccountPosting> postingList;

    public BewohnerStorno() {
    }

    public BewohnerStorno(Long id, CustomerModel customer, Zimmer zimmer, List<AccountPosting> postingList) {
        this.id = id;
        this.customer = customer;
        this.zimmer = zimmer;
        this.postingList = postingList;
    }

    public BewohnerStorno(CustomerModel customer, Zimmer zimmer, List<AccountPosting> postingList) {
        this.customer = customer;
        this.zimmer = zimmer;
        this.postingList = postingList;
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

    /**
     * @return the customer
     */
    public CustomerModel getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(CustomerModel customer) {
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
     * @return the postingList
     */
    public List<AccountPosting> getPostingList() {
        return postingList;
    }

    /**
     * @param postingList the postingList to set
     */
    public void setPostingList(List<AccountPosting> postingList) {
        this.postingList = postingList;
    }


    

}
