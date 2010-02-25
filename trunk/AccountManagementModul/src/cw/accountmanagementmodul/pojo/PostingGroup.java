package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.accountmanagementmodul.interfaces.PostingInterface;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 * Eine PostingGroup (zD Buchungsgruppe) ist eine Gruppierung mehrerer Buchungen
 * und soll die Übersichtlichkeit und Strukturierbarkeit zum Beispiel bei
 * Rechnungen verbessern.
 * @author ManuelG
 */
@Entity
public class PostingGroup
        extends Model
        implements AnnotatedClass,
                   PostingInterface  {

    private Long id                 = null;
    private Date creationDate       = new Date();
    private Account account       = null;
    private String name             = "";
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

    @ManyToOne
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany
    public List<Posting> getPostings() {
        return postings;
    }

    public void setPostings(List<Posting> posting) {
        this.postings = posting;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Transient
    public double getLiabilites() {
        double sum = 0;
        for(int i=0, l=postings.size(); i<l; i++) {
            if(postings.get(i).isLiabilities()) {
                sum = sum + postings.get(i).getAmount();
            }
        }
        return sum;
    }

    @Transient
    public double getAssets() {
        double sum = 0;
        for(int i=0, l=postings.size(); i<l; i++) {
            if(postings.get(i).isAssets()) {
                sum = sum + postings.get(i).getAmount();
            }
        }
        return sum;
    }

}
