package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author ManuelG
 */
@Entity
public class Invoice
        extends Model
        implements AnnotatedClass {

    private Long                id              = null;
    private Account             account         = null;
    private List<InvoiceItem>   invoiceItems    = new ArrayList<InvoiceItem>();
    private PostingGroup        postingGroup    = null;
    private Date                creationDate    = new Date();
    

    public final static String PROPERTYNAME_ID          = "id";
    public final static String PROPERTYNAME_ACCOUNT     = "account";


    public Invoice() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.id == null || ((Invoice)obj).id == null) {
            return false;
        }
        if(this.id!=((Invoice)obj).id){
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
