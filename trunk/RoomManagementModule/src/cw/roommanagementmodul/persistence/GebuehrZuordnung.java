
package cw.roommanagementmodul.persistence;

import java.beans.PropertyChangeSupport;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;


/**
 * @author Jeitler Dominik
 */

//stellt die Verbindung zwischen einer Gebuehr und einem Kunden in einem gewissen Zeitraum dar
@Entity
public class GebuehrZuordnung 
	extends CWPersistence
{
    
    public final static String ENTITY_NAME 	= "gebuehr_zuordnung";
    public final static String TABLE_NAME 	= "gebuehr_zuordnung";
    public final static String PROPERTYNAME_ID 			= "id";
    public final static String PROPERTYNAME_BEWOHNER 	= "bewohner";
    public final static String PROPERTYNAME_GEBUEHR 	= "gebuehr";
    public final static String PROPERTYNAME_VON 		= "von";
    public final static String PROPERTYNAME_BIS 		= "bis";
    public final static String PROPERTYNAME_ANMERKUNG 	= "anmerkung";

    private Long id;
    private Bewohner bewohner;
    private Gebuehr gebuehr; 
    private Date von;
    private Date bis;
    private String anmerkung;
    
    protected transient PropertyChangeSupport support;

    private GebuehrZuordnung() {
        super(null);
    }

    GebuehrZuordnung(EntityManager entityManager) {
    	super(entityManager);
    }
    
    public String getAnmerkung() {
        return anmerkung;
    }

    public void setAnmerkung(String anmerkung) {
        this.anmerkung = anmerkung;
    }

    @ManyToOne
    public Bewohner getBewohner() {
        return bewohner;
    }

    public void setBewohner(Bewohner bewohner) {
        this.bewohner = bewohner;
    }

    @Temporal(TemporalType.DATE)
    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
        this.bis = bis;
    }

    @ManyToOne
    public Gebuehr getGebuehr() {
        return gebuehr;
    }

    public void setGebuehr(Gebuehr gebuehr) {
        this.gebuehr = gebuehr;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    public Date getVon() {
        return von;
    }

    public void setVon(Date von) {
        this.von = von;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (this.getId() == ((GebuehrZuordnung) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }
}
