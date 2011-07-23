package cw.accountmanagementmodul.pojo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;

/**
 * Simple posting. It has a name, an amount and a creationDate. <br>
 * Specific posting classes need to extends this class. 
 * 
 * @author Manuel Geier
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Posting
        extends Model
        implements AnnotatedClass {

    protected Long id;
    protected String name;
    private long amount;
    protected Date creationDate;

    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_NAME            = "name";
    public final static String PROPERTYNAME_AMOUNT 			= "amount";
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
    

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        long old = this.amount;
        this.amount = amount;
        firePropertyChange(PROPERTYNAME_AMOUNT, old, amount);
    }

}
