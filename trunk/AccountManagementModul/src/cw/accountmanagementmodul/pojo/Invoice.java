package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
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
import javax.persistence.Temporal;

/**
 *
 * @author ManuelG
 */
@Entity
public class Invoice
        extends Model
        implements AnnotatedClass {

    private Long                id              = null;
    private Account             account         = null;
    private String              name            = "";
    private List<InvoiceItem>   invoiceItems    = new ArrayList<InvoiceItem>();
    private PostingGroup        postingGroup    = null;
    private Date                creationDate    = new Date();
    

    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_ACCOUNT         = "account";
    public final static String PROPERTYNAME_NAME            = "name";
    public final static String PROPERTYNAME_INVOICEITEMS    = "invoiceItems";
    public final static String PROPERTYNAME_POSTINGGROUP    = "postingGroups";
    public final static String PROPERTYNAME_CREATIONDATE    = "creationDate";


    public Invoice() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.id == null || ((Invoice)obj).id == null) {
            return false;
        }
        if(this.id!=((Invoice)obj).id){
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

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        Account old = this.account;
        this.account = account;
        firePropertyChange(PROPERTYNAME_ACCOUNT, old, account);
    }

    @Temporal(javax.persistence.TemporalType.DATE)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        Date old = this.creationDate;
        this.creationDate = creationDate;
        firePropertyChange(PROPERTYNAME_CREATIONDATE, old, creationDate);
    }

    @OneToMany(mappedBy = "invoice")
    public List<InvoiceItem> getInvoiceItems() {
        return invoiceItems;
    }

    public void setInvoiceItems(List<InvoiceItem> invoiceItems) {
        List<InvoiceItem> old = this.invoiceItems;
        this.invoiceItems = invoiceItems;
        firePropertyChange(PROPERTYNAME_INVOICEITEMS, old, invoiceItems);
    }

    @OneToOne
    public PostingGroup getPostingGroup() {
        return postingGroup;
    }

    public void setPostingGroup(PostingGroup postingGroup) {
        PostingGroup old = this.postingGroup;
        this.postingGroup = postingGroup;
        firePropertyChange(PROPERTYNAME_POSTINGGROUP, old, postingGroup);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

}
