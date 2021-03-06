package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.customermanagementmodul.persistence.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ManuelG
 */
@Entity
public class Account
        extends Model
        implements AnnotatedClass {

    private Long                    id              = null;
    private CustomerModel                customer        = null;
    private List<Posting>   postings        = new ArrayList<Posting>();
    private List<Bailment>          bailments       = new ArrayList<Bailment>();
    private List<Invoice>           invoices        = new ArrayList<Invoice>();


    public final static String PROPERTYNAME_ID          = "id";
    public final static String PROPERTYNAME_CUSTOMER    = "customer";


    public Account() {
    }

    public Account(CustomerModel customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.id == null || ((Account)obj).id == null) {
            return false;
        }
        if(this.id!=((Account)obj).id){
            return false;
        }
        return true;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    @OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        CustomerModel old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_CUSTOMER, old, customer);
    }

    @OneToMany(mappedBy = "account")
    public List<Bailment> getBailments() {
        return bailments;
    }

    public void setBailments(List<Bailment> bailments) {
        this.bailments = bailments;
    }

    @OneToMany(mappedBy = "account")
    public List<Posting> getPostings() {
        return postings;
    }

    public void setPostings(List<Posting> postings) {
        this.postings = postings;
    }

    @OneToMany(mappedBy = "account")
    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

}
