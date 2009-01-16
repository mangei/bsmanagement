package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * This is an Accounting-Class which representes one Accounting.<br>
 * All needed informations are saved. Also the the accounting-category.
 * @author CreativeWorkers.at
 */
@Entity
public class Accounting
        extends Model
        implements AnnotatedClass {

    private Long id;
    private Date accountingDate;
    private Date accountingEntryDate;
    private Customer customer;
    private String description;
    private boolean liabilitiesAssets;
    private double amount;
    private AccountingCategory category;
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_DESCRIPTION = "description";
    public final static String PROPERTYNAME_ACCOUNTINGDATE = "accountingDate";
    public final static String PROPERTYNAME_ACCOUNTINGENTRYDATE = "accountingEntryDate";
    public final static String PROPERTYNAME_AMOUNT = "amount";
    public final static String PROPERTYNAME_CATEGORY = "category";
    public final static String PROPERTYNAME_CUSTOMER = "customer";
    public final static String PROPERTYNAME_LIABILITIESASSETS = "liabilitiesAssets";

    /**
     * Empty accounting. You need to set the costumer!
     */
    public Accounting() {
    }

    /**
     * Accounting with an customer predefined
     * @param customer
     */
    public Accounting(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((Accounting) obj).getId()) {
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
    public Date getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(Date accountingDate) {
        Date old = this.accountingDate;
        this.accountingDate = accountingDate;
        firePropertyChange(PROPERTYNAME_ACCOUNTINGDATE, old, accountingDate);
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getAccountingEntryDate() {
        return accountingEntryDate;
    }

    public void setAccountingEntryDate(Date accountingEntryDate) {
        Date old = this.accountingEntryDate;
        this.accountingEntryDate = accountingEntryDate;
        firePropertyChange(PROPERTYNAME_ACCOUNTINGENTRYDATE, old, accountingEntryDate);
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

    @ManyToOne
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        Customer old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_CUSTOMER, old, customer);
    }

    @ManyToOne
    public AccountingCategory getCategory() {
        return category;
    }

    public void setCategory(AccountingCategory category) {
        AccountingCategory old = this.category;
        this.category = category;
        firePropertyChange(PROPERTYNAME_CATEGORY, old, category);
    }
}
