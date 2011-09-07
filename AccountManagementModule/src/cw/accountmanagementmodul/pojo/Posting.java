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

import cw.boardingschoolmanagement.persistence.AnnotatedClass;

/**
 * Simple posting. It has a name, an amount, a creationDate and a type. <br>
 * Specific posting classes need to extends this class. 
 * 
 * @author Manuel Geier
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Posting
        extends Model
        implements AnnotatedClass {

	// Member variables
    private Long id;
    private String name;
    private long amount;
    private Date creationDate;
    private String type;

    // Property name constants
    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_NAME            = "name";
    public final static String PROPERTYNAME_AMOUNT 			= "amount";
    public final static String PROPERTYNAME_CREATIONDATE    = "creationDate";
    public final static String PROPERTYNAME_TYPE    		= "type";

    /**
     * Default constructor
     */
    public Posting() {
	}
    
    /**
     * Getter for Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Setter for Id
     * @param id id
     */
    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     * @param name name
     */
    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    /**
     * Getter for creation date
     * @return creation date
     */
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Setter for creation date
     * @param creationDate creation date
     */
    public void setCreationDate(Date creationDate) {
        Date old = this.creationDate;
        this.creationDate = creationDate;
        firePropertyChange(PROPERTYNAME_CREATIONDATE, old, creationDate);
    }

    /**
     * Getter for amount
     * @return amount
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Setter for amount
     * @param amount amount
     */
    public void setAmount(long amount) {
        long old = this.amount;
        this.amount = amount;
        firePropertyChange(PROPERTYNAME_AMOUNT, old, amount);
    }
    
    /**
     * Getter for the type
     * @return type
     */
    public String getType() {
		return type;
	}
    
    /**
     * Setter for the type
     * @param type type
     */
    public void setType(String type) {
    	String old = this.type;
    	this.type = type;
        firePropertyChange(PROPERTYNAME_TYPE, old, type);
	}

}
