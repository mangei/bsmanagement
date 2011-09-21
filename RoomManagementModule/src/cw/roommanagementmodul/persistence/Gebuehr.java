package cw.roommanagementmodul.persistence;

import java.beans.PropertyChangeSupport;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * @author Jeitler Dominik
 */
@Entity
public class Gebuehr 
	extends CWPersistence
{

    public final static String ENTITY_NAME 	= "gebuehr";
    public final static String TABLE_NAME 	= "gebuehr";
    public final static String PROPERTYNAME_ID 			= "id";
    public final static String PROPERTYNAME_NAME 		= "name";
    public final static String PROPERTYNAME_GEBKAT 		= "geb_kat";
    public final static String PROPERTYNAME_TARIFLIST 	= "tarif_list";
    
    private int id;
    private String name;
    private GebuehrenKategorie gebKat;
    private List<Tarif> tarifList;

    private Gebuehr() {
    	super(null);
    }
    
    Gebuehr(EntityManager entityManager) {
    	super(entityManager);
    }

    @ManyToOne
    public GebuehrenKategorie getGebKat() {
        return gebKat;
    }

    public void setGebKat(GebuehrenKategorie gebKat) {
        this.gebKat = gebKat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            if (this.getId() == ((Gebuehr) obj).getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return the tarifList
     */
    @OneToMany(mappedBy = "gebuehr", cascade=CascadeType.REMOVE)
    public List<Tarif> getTarifList() {
        return tarifList;
    }

    /**
     * @param tarifList the tarifList to set
     */
    public void setTarifList(List<Tarif> tarifList) {
        this.tarifList = tarifList;
    }
}
