package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * This is an Posting-Class which representes one Posting.<br>
 * All needed informations are saved. Also the the posting-category.
 * @author CreativeWorkers.at
 */
@Entity
public class Posting
        extends Model
        implements AnnotatedClass {

    private Long id;
    private Date postingDate;
    private Date postingEntryDate;
    private Customer customer;
    private String description;
    private boolean liabilitiesAssets;
    private double amount;
    private PostingCategory postingCategory;
//    private String source;

    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_DESCRIPTION = "description";
    public final static String PROPERTYNAME_POSTINGDATE = "postingDate";
    public final static String PROPERTYNAME_POSTINGENTRYDATE = "postingEntryDate";
    public final static String PROPERTYNAME_AMOUNT = "amount";
    public final static String PROPERTYNAME_CATEGORY = "postingCategory";
    public final static String PROPERTYNAME_CUSTOMER = "customer";
    public final static String PROPERTYNAME_LIABILITIESASSETS = "liabilitiesAssets";
//    public final static String PROPERTYNAME_SOURCE = "source";

    /**
     * Empty accounting. You need to set the costumer!
     */
    public Posting() {
    }

    /**
     * Posting with an customer predefined
     * @param customer
     */
    public Posting(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((Posting) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        double old = this.amount;
        this.amount = amount;
        firePropertyChange(PROPERTYNAME_AMOUNT, old, amount);
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(Date postingDate) {
        Date old = this.postingDate;
        this.postingDate = postingDate;
        firePropertyChange(PROPERTYNAME_POSTINGDATE, old, postingDate);
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getPostingEntryDate() {
        return postingEntryDate;
    }

    public void setPostingEntryDate(Date postingEntryDate) {
        Date old = this.postingEntryDate;
        this.postingEntryDate = postingEntryDate;
        firePropertyChange(PROPERTYNAME_POSTINGENTRYDATE, old, postingEntryDate);
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        String old = this.description;
        this.description = name;
        firePropertyChange(PROPERTYNAME_DESCRIPTION, old, name);
    }

    /**
     * Returns the kind of the accounting
     * true: liabilities
     * false: assets
     * @return Boolean liabilitiesAssets
     */
    public boolean isLiabilitiesAssets() {
        return liabilitiesAssets;
    }

    /**
     * Sets the liabilitiesAssets-status
     * true: liabilities
     * false: assets
     * @param liabilitiesAssets
     */
    public void setLiabilitiesAssets(boolean liabilitiesAssets) {
        boolean old = this.liabilitiesAssets;
        this.liabilitiesAssets = liabilitiesAssets;
        firePropertyChange(PROPERTYNAME_LIABILITIESASSETS, old, liabilitiesAssets);
    }

    public void setLiabilities(boolean liabilities) {
        liabilitiesAssets = liabilities;
    }

    public void setAssets(boolean assets) {
        liabilitiesAssets = !assets;
    }

    @Transient
    public boolean isLiabilities() {
        return liabilitiesAssets;
    }

    @Transient
    public boolean isAssets() {
        return !liabilitiesAssets;
    }

    @ManyToOne(cascade={CascadeType.ALL})
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        Customer old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_CUSTOMER, old, customer);
    }

    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public PostingCategory getPostingCategory() {
        return postingCategory;
    }

    public void setPostingCategory(PostingCategory postingCategory) {
        PostingCategory old = this.postingCategory;
        this.postingCategory = postingCategory;
        firePropertyChange(PROPERTYNAME_CATEGORY, old, postingCategory);
    }

//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        String old = this.source;
//        this.source = source;
//        firePropertyChange(PROPERTYNAME_SOURCE, old, source);
//    }

}
