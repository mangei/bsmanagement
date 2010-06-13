package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author ManuelG
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class AbstractPosting
        extends Model
        implements AnnotatedClass {

    protected Long id;
    protected Account account;
    protected String name;
    protected Date creationDate;

    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_ACCOUNT         = "account";
    public final static String PROPERTYNAME_NAME            = "name";
    public final static String PROPERTYNAME_CREATIONDATE    = "creationDate";

    
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

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        Account old = this.account;
        this.account = account;
        firePropertyChange(PROPERTYNAME_ACCOUNT, old, account);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        Date old = this.creationDate;
        this.creationDate = creationDate;
        firePropertyChange(PROPERTYNAME_CREATIONDATE, old, creationDate);
    }

    @Transient
    public abstract double getAmount();

}
