/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author ManuelG
 */
@Entity
public class ReversePosting
        extends Model
        implements AnnotatedClass
{

    private Long id;
    private Posting posting;
    private Posting reversePosting;

    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_POSTING = "posting";
    public final static String PROPERTYNAME_REVERSEPOSTING = "reversePosting";

    public ReversePosting() {
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

    @OneToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        Posting old = this.posting;
        this.posting = posting;
        firePropertyChange(PROPERTYNAME_POSTING, old, posting);

    }

    @OneToOne(cascade={CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.REMOVE})
    public Posting getReversePosting() {
        return reversePosting;
    }

    public void setReversePosting(Posting reversePosting) {
        Posting old = this.reversePosting;
        this.reversePosting = reversePosting;
        firePropertyChange(PROPERTYNAME_REVERSEPOSTING, old, reversePosting);

    }

}
