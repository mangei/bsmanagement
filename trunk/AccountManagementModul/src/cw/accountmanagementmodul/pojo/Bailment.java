package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Eine Bailment (zD Kaution) ist eine Hinterlegung zum Beispiel eines
 * Gegenstandes.
 *
 * Bsp:
 *  Name: Zimmerschlüsselkaution
 *  Beschreibung: D
 * @author ManuelG
 */
@Entity
public class Bailment
        extends Model
        implements AnnotatedClass {

    private Long    id              = null;
    private String  name            = "";
    private String  description     = "";
    private double  amount          = 0.0;
    private Account account         = null;
    private BailmentStatus status   = BailmentStatus.OPEN;

    public enum  BailmentStatus {
        OPEN, DEDUCTED, REPAID
    };

    public final static String PROPERTYNAME_ID          = "id";
    public final static String PROPERTYNAME_NAME        = "name";
    public final static String PROPERTYNAME_DESCRIPTION = "description";
    public final static String PROPERTYNAME_AMOUNT      = "amount";
    public final static String PROPERTYNAME_ACCOUNT     = "account";
    public final static String PROPERTYNAME_STATUS      = "status";


    public Bailment() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.id == null || ((Bailment)obj).id == null) {
            return false;
        }
        if(this.id!=((Bailment)obj).id){
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

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}
