package cw.accountmanagementmodul.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;

/**rivate Long id;
 * This is an Posting-Class which representes one Posting.<br>
 * All needed informations are saved. Also the the posting-category.
 * @author CreativeWorkers.at
 */
@Entity
public class AccountPosting
        extends Posting
        implements AnnotatedClass {

    private Date postingEntryDate;
    protected Account account;
    private boolean reversePosting = false;
    private boolean balancePosting = false;
    private AccountPosting previousPosting;

    // Properties - Constants
    public final static String PROPERTYNAME_ACCOUNT         = "account";
    public final static String PROPERTYNAME_POSTINGENTRYDATE = "postingEntryDate";
    public final static String PROPERTYNAME_REVERSEPOSTING = "reversePosting";
    public final static String PROPERTYNAME_BALANCEPOSTING = "balancePosting";
    public final static String PROPERTYNAME_PREVIOUSPOSTING = "previousPosting";
//    public final static String PROPERTYNAME_SOURCE = "source";

    /**
     * Empty accounting. You need to set the costumer!
     */
    public AccountPosting() {
    }

    /**
     * Posting with an account predefined
     * @param account
     */
    public AccountPosting(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof AccountPosting)) return false;
        if (this.getId() == ((AccountPosting) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }
    
    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        Account old = this.account;
        this.account = account;
        firePropertyChange(PROPERTYNAME_ACCOUNT, old, account);
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

//    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
//    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
//    public Customer getCustomer() {
//        return account;
//    }
//
//    public void setCustomer(Customer customer) {
//        Customer old = this.account;
//        this.account = customer;
//        firePropertyChange(PROPERTYNAME_CUSTOMER, old, customer);
//    }

    public boolean isReversePosting() {
        return reversePosting;
    }

    public void setReversePosting(boolean reversePosting) {
        boolean old = this.reversePosting;
        this.reversePosting = reversePosting;
        firePropertyChange(PROPERTYNAME_REVERSEPOSTING, old, reversePosting);
    }

    public boolean isBalancePosting() {
        return balancePosting;
    }

    public void setBalancePosting(boolean balancePosting) {
        boolean old = this.balancePosting;
        this.balancePosting = balancePosting;
        firePropertyChange(PROPERTYNAME_BALANCEPOSTING, old, balancePosting);
    }

    @ManyToOne
    public AccountPosting getPreviousPosting() {
        return previousPosting;
    }

    public void setPreviousPosting(AccountPosting previousPosting) {
        AccountPosting old = this.previousPosting;
        this.previousPosting = previousPosting;
        firePropertyChange(PROPERTYNAME_PREVIOUSPOSTING, old, previousPosting);
    }

}
