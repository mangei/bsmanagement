package cw.coursemanagementmodul.pojo;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class Course extends Model
        implements AnnotatedClass

{
    private Long id                                 = null;
    private String name                             = "";
    private Date beginDate                          = null;
    private Date endDate                            = null;
    private double price                            = 0.0;
    private List<CourseAddition> courseAdditions    = new ArrayList<CourseAddition>();
    
    public final static String PROPERTYNAME_ID                  = "id";
    public final static String PROPERTYNAME_NAME                = "name";
    public final static String PROPERTYNAME_BEGINDATE           = "beginDate";
    public final static String PROPERTYNAME_ENDDATE             = "endDate";
    public final static String PROPERTYNAME_COURSEADDITIONS     = "courseAdditions";
    public final static String PROPERTYNAME_PRICE               = "price";

    public Course() {
    }

    @Override
    public String toString(){
        DateFormat format = DateFormat.getDateInstance();
        return "<html><b>" + name + "</b> " + format.format(beginDate) + "-" + format.format(endDate) + "</html>";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.id != ((Course)obj).getId()) {
            return false;
        }

        return true;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        Date old = this.beginDate;
        this.beginDate = beginDate;
        firePropertyChange(PROPERTYNAME_BEGINDATE, old, beginDate);
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        Date old = this.endDate;
        this.endDate = endDate;
        firePropertyChange(PROPERTYNAME_ENDDATE, old, endDate);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }
    
    @ManyToMany
    public List<CourseAddition> getCourseParticipants() {
        return courseAdditions;
    }

    public void setCourseParticipants(List<CourseAddition> courseAddition) {
        List<CourseAddition> old = this.courseAdditions;
        this.courseAdditions = courseAddition;
        firePropertyChange(PROPERTYNAME_COURSEADDITIONS, old, courseAddition);
    }
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        double old = this.price;
        this.price = price;
        firePropertyChange(PROPERTYNAME_PRICE, old, price);
    }
}
