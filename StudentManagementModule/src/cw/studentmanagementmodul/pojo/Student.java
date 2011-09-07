
package cw.studentmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import cw.customermanagementmodul.persistence.model.CustomerModel;

import javax.persistence.CascadeType;

/**
 * 
 * @author CreativeWorkers
 */
@Entity
public class Student 
        extends Model
        implements AnnotatedClass
{
    private Long id                     = null;
    private StudentClass studentClass   = null;
    private CustomerModel customer           = null;
    private boolean active              = false;
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_STUDENTCLASS    = "studentClass";
    public final static String PROPERTYNAME_CUSTOMER        = "customer";
    public final static String PROPERTYNAME_ACTIVE          = "active";
    
    public Student() {
    }

    public Student(CustomerModel customer) {
        this.customer = customer;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Student)) {
            return false;
        }
        if (this.getId() != ((Student)obj).getId()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(customer.getForename());
        builder.append(" ");
        builder.append(customer.getSurname());
        return builder.toString();
    }

    @OneToOne
    public StudentClass getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(StudentClass studentClass) {
        StudentClass old = this.studentClass;
        this.studentClass = studentClass;
        firePropertyChange(PROPERTYNAME_STUDENTCLASS, old, studentClass);
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

    @OneToOne
    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        CustomerModel old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_CUSTOMER, old, customer);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        boolean old = this.active;
        this.active = active;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

}
