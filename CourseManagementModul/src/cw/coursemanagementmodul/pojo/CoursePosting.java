/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import cw.customermanagementmodul.pojo.Posting;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class CoursePosting extends Model implements AnnotatedClass{
    private Long id;
    private Posting posting;
    private CourseAddition courseAddition;

    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_POSTING = "posting";
    public final static String PROPERTYNAME_COURSEADDITION = "courseAddition";

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

    public CoursePosting() {
        setPosting(new Posting());
        setCourseAddition(new CourseAddition());
    }

    @OneToOne
    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        Posting old = this.posting;
        this.posting = posting;
        firePropertyChange(PROPERTYNAME_POSTING, old, posting);
    }

    @OneToOne
    public CourseAddition getCourseAddition() {
        return courseAddition;
    }

    public void setCourseAddition(CourseAddition courseAddition) {
        CourseAddition old = this.courseAddition;
        this.courseAddition = courseAddition;
        firePropertyChange(PROPERTYNAME_COURSEADDITION, old, courseAddition);
    }
}
