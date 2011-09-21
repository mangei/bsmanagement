package cw.roommanagementmodul.persistence;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * @author Jeitler Dominik6
 */

//Dient zur besseren Einteilung der Gebuehren
@Entity
public class GebuehrenKategorie 
	extends CWPersistence
{
    
    public final static String ENTITY_NAME 	= "gebuehren_kategorie";
    public final static String TABLE_NAME 	= "gebuehren_kategorie";
    public final static String PROPERTYNAME_ID 		= "id";
    public final static String PROPERTYNAME_NAME 	= "name";

    private Long id;
    private String name;

    private GebuehrenKategorie() {
        super(null);
    }

    GebuehrenKategorie(EntityManager entityManager) {
    	super(entityManager);
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
