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
public class CoursePosting extends Model 
        implements AnnotatedClass
{

    private Long id                         = null;
    private Posting posting                 = null;
    private CourseAddition courseAddition   = null;

    public final static String PROPERTYNAME_ID              = "id";
    public final static String PROPERTYNAME_POSTING         = "posting";
    public final static String PROPERTYNAME_COURSEADDITION  = "courseAddition";

    public CoursePosting() {
        //setPosting(new Posting());
        //setCourseAddition(new CourseAddition());
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
