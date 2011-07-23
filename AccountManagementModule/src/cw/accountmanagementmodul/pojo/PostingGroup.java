package cw.accountmanagementmodul.pojo;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Eine PostingGroup (zD Buchungsgruppe) ist eine Gruppierung mehrerer Buchungen
 * und soll die Übersichtlichkeit und Strukturierbarkeit zum Beispiel bei
 * Rechnungen verbessern.
 * @author ManuelG
 */
@Entity
public class PostingGroup
        extends Posting
        implements AnnotatedClass  {

    private List<Posting> postings  = new ArrayList<Posting>();

    public PostingGroup() {
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof PostingGroup)) return false;
        if (this.getId() == ((PostingGroup) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @OneToMany
    public List<Posting> getPostings() {
        return postings;
    }

    public void setPostings(List<Posting> postings) {
        this.postings = postings;
    }

    @Override
    @Transient
    public long getAmount() {
        long amount = 0;
        for(int i=0, l=postings.size(); i<l; i++) {
            amount += postings.get(i).getAmount();
        }
        return amount;
    }

}
