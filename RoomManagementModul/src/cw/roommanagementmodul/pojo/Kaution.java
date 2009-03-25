package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.beans.PropertyChangeSupport;
import java.text.DecimalFormat;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Jeitler Dominik
 */
@Entity
public class Kaution extends Model implements AnnotatedClass{

    private Long id;
    private String name;
    private double betrag;
    protected transient PropertyChangeSupport support;
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_BETRAG = "betrag";
    public final static DecimalFormat numberFormat=new DecimalFormat("#0.00");

    public Kaution(Long id, String name, double betrag) {
        this.id = id;
        this.name = name;
        this.betrag = betrag;
    }

    public Kaution() {
    }

    public double getBetrag() {
        return betrag;
    }

    public void setBetrag(double betrag) {
        this.betrag = betrag;
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
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kaution other = (Kaution) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }




    @Override
    public String toString() {
        return this.name + " € " + numberFormat.format(this.betrag);
    }
}
