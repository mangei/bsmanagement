/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.customermanagementmodul.pojo.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class CourseParticipant extends Model implements AnnotatedClass {
    private Long id;
    private Customer customer;
    private List<CourseAddition> courseList;
    
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_COSTUMER = "costumer";
    public final static String PROPERTYNAME_COURSELIST = "courseList";

    public CourseParticipant() {
        courseList = new ArrayList<CourseAddition>();
    }

    public CourseParticipant(Customer customer) {
        this();
        this.customer = customer;
    }

    @OneToOne
    public Customer getCostumer() {
        return customer;
    }

    public void setCostumer(Customer costumer) {
        Customer old = this.customer;
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
