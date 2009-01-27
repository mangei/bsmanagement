
package cw.studentmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import cw.customermanagementmodul.pojo.Customer;
import javax.persistence.CascadeType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * 
 * @author CreativeWorkers
 */
@Entity
public class Student 
        extends Model
        implements AnnotatedClass
{
    private Long id;
    private StudentClass studentClass;
    private Customer customer;
    private boolean active;
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_STUDENTCLASS = "studentClass";
    public final static String PROPERTYNAME_CustoMER = "customer";
    public final static String PROPERTYNAME_ACTIVE = "active";
    
    public Student() {
    }

    public Student(Customer customer) {
        this.customer = customer;
    }
    
     @Override
    public boolean equals(Object obj) {
        if(obj == null) {
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

    @OneToOne(cascade=CascadeType.ALL)
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

    @OneToOne()
    @OnDelete(action=OnDeleteAction.CASCADE)
//    @Cascade({org.hibernate.annotations.CascadeType.DELETE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        Customer old = this.customer;
        this.customer = customer;
        firePropertyChange(PROPERTYNAME_CustoMER, old, customer);
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
