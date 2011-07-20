
package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * @author Jeitler Dominik
 */

//stellt die Verbindung zwischen einer Gebühr und einem Kunden in einem gewissen Zeitraum dar
@Entity
public class GebuehrZuordnung extends Model implements AnnotatedClass{

    private Long id;
    private Bewohner bewohner;
    private Gebuehr gebuehr; 
    private Date von;
    private Date bis;
    private String anmerkung;
    
    public final static String PROPERTYNAME_BEWOHNER = "bewohner";
    public final static String PROPERTYNAME_GEBUEHR = "gebuehr";
    public final static String PROPERTYNAME_ANMERKUNG = "anmerkung";
    public final static String PROPERTYNAME_VON = "von";
    public final static String PROPERTYNAME_BIS = "bis";
    
    protected transient PropertyChangeSupport support;

    public GebuehrZuordnung(Long id, Bewohner bewohner, Gebuehr gebuehr, Date von, Date bis, String anmerkung) {
        this.id = id;
        this.bewohner = bewohner;
        this.gebuehr = gebuehr;
        this.von = von;
        this.bis = bis;
        this.anmerkung = anmerkung;
    }

    public GebuehrZuordnung() {
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
