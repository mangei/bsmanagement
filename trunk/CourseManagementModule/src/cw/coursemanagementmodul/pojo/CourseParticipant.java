package cw.coursemanagementmodul.pojo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.customermanagementmodul.persistence.model.CustomerModel;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class CourseParticipant extends Model 
        implements AnnotatedClass
{
    
    private Long id                             = null;
    private CustomerModel customer                   = null;
    private List<CourseAddition> courseList     = new ArrayList<CourseAddition>();;
    
    public final static String PROPERTYNAME_ID          = "id";
    public final static String PROPERTYNAME_COSTUMER    = "customer";
    public final static String PROPERTYNAME_COURSELIST  = "courseList";

    public CourseParticipant() {
    }

    public CourseParticipant(CustomerModel customer) {
        this.customer = customer;
    }

    @OneToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel costumer) {
        CustomerModel old = this.customer;
        this.customer = costumer;
        firePropertyChange(PROPERTYNAME_COSTUMER, old, costumer);
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


    @OneToMany
    public List<CourseAddition> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<CourseAddition> courseList) {
        List<CourseAddition> old = this.courseList;
        this.courseList = courseList;
        firePropertyChange(PROPERTYNAME_COURSELIST, old, courseList);
    }
}
