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
public class Bereich extends Model implements AnnotatedClass {

    private Long id;
    private String name;
    private Bereich parentBereich;
    private List<Bereich> childBereichList;
    private List<Zimmer> zimmerList;
    protected transient PropertyChangeSupport support;
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_PARENTBEREICH = "parentBereich";
    public final static String PROPERTYNAME_CHILDBEREICHLIST = "childBereichList";
    public final static String PROPERTYNAME_ZIMMERLIST = "zimmerList";

    public Bereich(Long id, String name, Bereich parentBereich) {
        this.id = id;
        this.name = name;
        this.parentBereich = parentBereich;
        this.childBereichList = new ArrayList<Bereich>();
        this.zimmerList = new ArrayList<Zimmer>();
    }

    public Bereich(String name, Bereich parentBereich) {
        this.name = name;
        this.parentBereich = parentBereich;
    }

    public Bereich() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    public Bereich getParentBereich() {
        return parentBereich;
    }

    public void setParentBereich(Bereich bereich) {
        Bereich oldBereich = getParentBereich();
        this.parentBereich = bereich;
        firePropertyChange(PROPERTYNAME_PARENTBEREICH, oldBereich, bereich);
    }

    @OneToMany(mappedBy = "parentBereich", cascade = CascadeType.REMOVE)
    public List<Bereich> getChildBereichList() {
        return childBereichList;
    }

    public void setChildBereichList(List<Bereich> childBereichList) {
        this.childBereichList = childBereichList;
    }

    @OneToMany(mappedBy = "bereich")
    public List<Zimmer> getZimmerList() {
        return zimmerList;
    }

    public void setZimmerList(List<Zimmer> zimmerList) {
        this.zimmerList = zimmerList;
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
