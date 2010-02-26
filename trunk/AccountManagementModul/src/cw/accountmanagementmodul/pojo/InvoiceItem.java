package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ManuelG
 */
@Entity
public class InvoiceItem
        extends Model
        implements AnnotatedClass {

    private Long            id              = null;
    private Invoice         invoice         = null;
    private String          name            = "";
    private String          description     = "";
    private int             amount          = 0;
    private double          pricePerUnit    = 0.0;
    private Posting         posting         = null;
    private Date            creationDate    = new Date();
    

    public final static String PROPERTYNAME_ID          = "id";


    public InvoiceItem() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.id == null || ((InvoiceItem)obj).id == null) {
            return false;
        }
        if(this.id!=((InvoiceItem)obj).id){
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


}
