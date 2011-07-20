package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ManuelG
 */
@Entity
public class Guardian
        extends Model
        implements AnnotatedClass {

    private Long id;
    private boolean active = false;
    private boolean gender = true;
    private String title;
    private String forename;
    private String surname;

    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_TITLE = "title";
    public final static String PROPERTYNAME_GENDER = "gender";
    public final static String PROPERTYNAME_ACTIVE = "active";
    public final static String PROPERTYNAME_FORENAME = "forename";
    public final static String PROPERTYNAME_SURNAME = "surname";

    public Guardian() {
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Guardian)) return false;
        if (this.getId() == ((Guardian) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return surname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        String old = this.forename;
        this.forename = forename;
        firePropertyChange(PROPERTYNAME_FORENAME, old, forename);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String old = this.surname;
        this.surname = surname;
        firePropertyChange(PROPERTYNAME_SURNAME, old, surname);
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        boolean old = this.gender;
        this.gender = gender;
        firePropertyChange(PROPERTYNAME_GENDER, old, gender);
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        boolean old = this.active;
        this.active = active;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        firePropertyChange(PROPERTYNAME_TITLE, old, title);
    }

}
