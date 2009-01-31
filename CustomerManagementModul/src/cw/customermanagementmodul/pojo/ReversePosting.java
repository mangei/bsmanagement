/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;

/**
 *
 * @author ManuelG
 */
public class ReversePosting
        extends Model
        implements AnnotatedClass
{

    private Long id;
    private Posting posting;
    private Posting reversePosting;

    public ReversePosting() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Posting getPosting() {
        return posting;
    }

    public void setPosting(Posting posting) {
        this.posting = posting;
    }

    public Posting getReversePosting() {
        return reversePosting;
    }

    public void setReversePosting(Posting reversePosting) {
        this.reversePosting = reversePosting;
    }

}
