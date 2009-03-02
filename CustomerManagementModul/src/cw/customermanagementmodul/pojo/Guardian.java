package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author ManuelG
 */
@Entity
public class Guardian
        extends Model
        implements AnnotatedClass {

    private Long id;
    private String forename;
    private String surname;
    private Customer customer;

    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_FORENAME = "forname";
    public final static String PROPERTYNAME_SURNAME = "surname";
    public final static String PROPERTYNAME_CUSTOMER = "customer";

    public Guardian() {
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Guardian)) return false;
        if (this.getId() == ((Guardian) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return surname;
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

    @OneToOne(mappedBy="guardian", cascade=CascadeType.ALL)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        Customer old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_CUSTOMER, old, customer);
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        String old = this.forename;
        this.forename = forename;
        firePropertyChange(PROPERTYNAME_FORENAME, old, forename);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String old = this.surname;
        this.surname = surname;
        firePropertyChange(PROPERTYNAME_SURNAME, old, surname);
    }

}
