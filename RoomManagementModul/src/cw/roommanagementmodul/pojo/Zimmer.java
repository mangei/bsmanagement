package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Jeitler Dominik
 */
@Entity
public class Zimmer extends Model implements AnnotatedClass{

    private Long id;
    private String name;
    private Bereich bereich;
    private String anzBetten;
    private boolean activ;
    private List<Bewohner> bewohnerList = new ArrayList<Bewohner>();
    
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_BEREICH = "bereich";
    public final static String PROPERTYNAME_ANZBETTEN = "anzBetten";
    public final static String PROPERTYNAME_ACTIV = "activ";
    public final static String PROPERTYNAME_BEWOHNERLIST = "bewohnerList";

    public Zimmer(Long id, String name, Bereich bereich, String anzBetten, boolean activ) {
        this.id = id;
        this.name = name;
        this.bereich = bereich;
        this.anzBetten = anzBetten;
        this.activ = activ;
    }

    public Zimmer() {
    }

    @Column(nullable = true)
    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        boolean old = this.activ;
        this.activ = activ;
        firePropertyChange(PROPERTYNAME_ACTIV, old, activ);
    }

    @Column(nullable = true)
    public String getAnzBetten() {
        return anzBetten;
    }

    public void setAnzBetten(String anzBetten) {
        String old = this.anzBetten;
        this.anzBetten = anzBetten;
        firePropertyChange(PROPERTYNAME_ANZBETTEN, old, anzBetten);
    }

    //@Column(nullable=true)
    @ManyToOne
    public Bereich getBereich() {
        return bereich;
    }

    public void setBereich(Bereich bereich) {
        Bereich old = this.bereich;
        this.bereich = bereich;
        firePropertyChange(PROPERTYNAME_BEREICH, old, bereich);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null) {
            if (this.getId() == ((Zimmer) obj).getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Column(nullable = true)
    @OneToMany(mappedBy = "zimmer")
    public List<Bewohner> getBewohnerList() {
        return bewohnerList;
    }

    @Override
    public String toString(){
        return this.name;
    }
    
    public void setBewohnerList(List<Bewohner> bewohnerList) {
        List<Bewohner> old = this.bewohnerList;
        this.bewohnerList = bewohnerList;
        firePropertyChange(PROPERTYNAME_BEWOHNERLIST, old, bewohnerList);
    }
}
