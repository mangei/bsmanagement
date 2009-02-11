/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class CourseAddition extends Model implements AnnotatedClass{
    private Long id;
    //private CourseParticipant courseParticipant;
    private Course course;
    private List<Activity> activities;
    private List<Subject> subjects;
    private double individualPrice;
    
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_COURSEPARTICIPANT = "courseParticipant";
    public final static String PROPERTYNAME_COURSE = "course";
    public final static String PROPERTYNAME_ACTIVITIES = "activities";
    public final static String PROPERTYNAME_SUBJECTS = "subjects";
    public final static String PROPERTYNAME_INDIVIDUALPRICE = "individualPrice";
    public final static String PROPERTYNAME_ACCOUNTINGS = "accountings";

    public CourseAddition() {
        course = new Course();
        activities = new ArrayList();
        subjects = new ArrayList();
    }
    
    @ManyToMany
    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activitys) {
        List<Activity> old = this.activities;
        this.activities = activitys;
        firePropertyChange(PROPERTYNAME_ACTIVITIES, old, activities);
    }

    @ManyToMany
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        List<Subject> old = this.subjects;
        this.subjects = subjects;
        firePropertyChange(PROPERTYNAME_SUBJECTS, old, subjects);
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
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        Course old = this.course;
        this.course = course;
        firePropertyChange(PROPERTYNAME_COURSE, old, course);
    }
    
    public double getIndividualPrice() {
        return individualPrice;
    }

    public void setIndividualPrice(double individualPrice) {
        double old = this.individualPrice;
        this.individualPrice = individualPrice;
        firePropertyChange(PROPERTYNAME_INDIVIDUALPRICE, old, individualPrice);
    }
}
