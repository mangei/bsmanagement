package cw.coursemanagementmodul.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;

/**
 *
 * @author André Salmhofer
 */
@Entity
public class Activity extends Model
        implements AnnotatedClass
{
    
    private Long id                 = null;
    private String name             = "";
    private String description      = "";
    private double price            = 0.0;
    
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_DESCRIPTION = "description";
    public final static String PROPERTYNAME_PRICE = "price";

    public Activity() {
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.id != ((Activity)obj).getId()) {
            return false;
        }

        return true;
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
        firePropertyChange(PROPERTYNAME_ID, old, name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String old = this.description;
        this.description = description;
        firePropertyChange(PROPERTYNAME_DESCRIPTION, old, description);
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
