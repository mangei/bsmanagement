package cw.roommanagementmodul.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jgoodies.binding.beans.Model;

import cw.boardingschoolmanagement.persistence.AnnotatedClass;
import cw.boardingschoolmanagement.persistence.CWPersistence;

/**
 * @author Jeitler Dominik
 */
@Entity
public class Zimmer 
	extends CWPersistence
{
    public final static String ENTITY_NAME 	= "zimmer";
    public final static String TABLE_NAME 	= "zimmer";
    public final static String PROPERTYNAME_ID 				= "id";
    public final static String PROPERTYNAME_NAME 			= "name";
    public final static String PROPERTYNAME_BEREICH 		= "bereich";
    public final static String PROPERTYNAME_ANZBETTEN 		= "anzbetten";
    public final static String PROPERTYNAME_ACTIV 			= "activ";
    public final static String PROPERTYNAME_BEWOHNERLIST 	= "bewohner_list";

    private Long id;
    private String name;
    private Bereich bereich;
    private int anzbetten;
    private boolean activ;
    private List<Bewohner> bewohnerList = new ArrayList<Bewohner>();

    private Zimmer() {
    	super(null);
    }

    Zimmer(EntityManager entityManager) {
    	super(entityManager);
    }

    @Column(nullable = true)
    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public int getAnzbetten() {
        return anzbetten;
    }

    public void setAnzbetten(int anzBetten) {
        this.anzbetten = anzBetten;
    }

    //@Column(nullable=true)
    @ManyToOne
    public Bereich getBereich() {
        return bereich;
    }

    public void setBereich(Bereich bereich) {
        this.bereich = bereich;
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
