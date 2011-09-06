package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Jeitler Dominik6
 */

//Dient zur besseren Einteilung der Gebuehren
@Entity
public class GebuehrenKategorie extends Model implements AnnotatedClass{

    private Long id;
    private String name;
    public final static String PROPERTYNAME_NAME = "name";

    public GebuehrenKategorie(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GebuehrenKategorie() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (this.getId() == ((GebuehrenKategorie) obj).getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
