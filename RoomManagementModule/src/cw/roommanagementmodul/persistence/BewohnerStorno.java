package cw.roommanagementmodul.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import cw.accountmanagementmodul.pojo.AccountPosting;

/**
 *
 * @author Dominik
 */
public class BewohnerStorno {

    public final static String ENTITY_NAME = "bereich";
    public final static String TABLE_NAME = "bereich";
    public final static String PROPERTYNAME_ID 				= "id";
    public final static String PROPERTYNAME_CUSTOMER 		= "customer";
    public final static String PROPERTYNAME_ZIMMER			= "zimmer";
    public final static String PROPERTYNAME_POSTINGLIST		= "posting_list";
    
    private Long id;
    private CustomerModel customer;
    private Zimmer zimmer;
    private List<AccountPosting> postingList;

    
    private BewohnerStorno() {
    	super(null);
    }

    BewohnerStorno(EntityManager entityManager) {
    	super(entityManager);
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
