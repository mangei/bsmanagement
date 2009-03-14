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
import javax.persistence.OneToMany;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class PostingRun extends Model implements AnnotatedClass{
    private Long id;
    private List<CoursePosting> coursePostings;
    private String name;
    private Boolean alreadyStorniert;

    public PostingRun() {
        coursePostings = new ArrayList<CoursePosting>();
        alreadyStorniert = false;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany
    public List<CoursePosting> getCoursePostings() {
        return coursePostings;
    }

    public void setCoursePostings(List<CoursePosting> coursePostings) {
        this.coursePostings = coursePostings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAlreadyStorniert() {
        return alreadyStorniert;
    }

    public void setAlreadyStorniert(Boolean alreadyStorniert) {
        this.alreadyStorniert = alreadyStorniert;
    }
}
