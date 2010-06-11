package cw.accountmanagementmodul.pojo;

import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * Eine PostingGroup (zD Buchungsgruppe) ist eine Gruppierung mehrerer Buchungen
 * und soll die Übersichtlichkeit und Strukturierbarkeit zum Beispiel bei
 * Rechnungen verbessern.
 * @author ManuelG
 */
@Entity
public class PostingGroup
        extends AbstractPosting
        implements AnnotatedClass  {

    private List<AbstractPosting> postings  = new ArrayList<AbstractPosting>();

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
    public List<AbstractPosting> getPostings() {
        return postings;
    }

    public void setPostings(List<AbstractPosting> postings) {
        this.postings = postings;
    }

}
