package cw.roommanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
public class Bereich
        extends Model
        implements AnnotatedClass
{

    private Long id                         = null;
    private String name                     = "";
    private Bereich parentBereich           = null;
    private List<Bereich> childBereichList  = new ArrayList<Bereich>();
    private List<Zimmer> zimmerList         = new ArrayList<Zimmer>();

    public final static String PROPERTYNAME_ID                  = "id";
    public final static String PROPERTYNAME_NAME                = "name";
    public final static String PROPERTYNAME_PARENTBEREICH       = "parentBereich";
    public final static String PROPERTYNAME_CHILDBEREICHLIST    = "childBereichList";
    public final static String PROPERTYNAME_ZIMMERLIST          = "zimmerList";

    public Bereich() {
    }

    public Bereich(String name, Bereich parentBereich) {
        this.name = name;
        this.parentBereich = parentBereich;
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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    public Bereich getParentBereich() {
        return parentBereich;
    }

    public void setParentBereich(Bereich bereich) {
        Bereich oldBereich = this.parentBereich;
        this.parentBereich = bereich;
        firePropertyChange(PROPERTYNAME_PARENTBEREICH, oldBereich, bereich);
    }

    @OneToMany(mappedBy = "parentBereich", cascade = CascadeType.REMOVE)
    public List<Bereich> getChildBereichList() {
        return childBereichList;
    }

    public void setChildBereichList(List<Bereich> childBereichList) {
        List<Bereich> old = this.childBereichList;
        this.childBereichList = childBereichList;
        firePropertyChange(PROPERTYNAME_CHILDBEREICHLIST, old, childBereichList);
    }

    @OneToMany(mappedBy = "bereich")
    public List<Zimmer> getZimmerList() {
        return zimmerList;
    }

    public void setZimmerList(List<Zimmer> zimmerList) {
        List<Zimmer> old = this.zimmerList;
        this.zimmerList = zimmerList;
        firePropertyChange(PROPERTYNAME_ZIMMERLIST, old, zimmerList);
    }

    @Override
    public String toString() {
        Bereich parent = this;
        StringBuffer str = new StringBuffer();

        if(this.getParentBereich()==null){
            return this.name;
        }
        while (parent.getParentBereich() != null) {

            if(parent.getParentBereich().getParentBereich()==null){
                str.insert(0, parent.getName());
            }else{
                str.insert(0, " / " + parent.getName());
            }
            parent = parent.getParentBereich();
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj != null) {

            if (this.getId() == ((Bereich) obj).getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;

    }
}
