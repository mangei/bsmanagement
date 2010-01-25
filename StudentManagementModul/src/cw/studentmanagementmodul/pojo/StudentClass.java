package cw.studentmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * 
 * @author CreativeWorkers.at
 */
@Entity
public class StudentClass 
        extends Model
        implements AnnotatedClass
{

    private Long id                             = null;
    private String name                         = "";
    private StudentClass nextStudentClass       = null;
    private OrganisationUnit organisationUnit   = null;
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID                  = "id";
    public final static String PROPERTYNAME_NAME                = "name";
    public final static String PROPERTYNAME_NEXTSTUDENTCLASS    = "nextStudentClass";
    public final static String PROPERTYNAME_ORGANISATIONUNIT    = "organisationUnit";
    
    public StudentClass() {
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof StudentClass)) {
            return false;
        }
        if (this.getId() != ((StudentClass)obj).getId()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name);
        if(organisationUnit != null) {
            builder.append(" (");
            builder.append(organisationUnit.toString());
            builder.append(")");
        }
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    @OneToOne
    public StudentClass getNextStudentClass() {
        return nextStudentClass;
    }

    public void setNextStudentClass(StudentClass nextKlasse) {
        StudentClass old = this.nextStudentClass;
        this.nextStudentClass = nextKlasse;
        firePropertyChange(PROPERTYNAME_NEXTSTUDENTCLASS, old, nextKlasse);
    }

    @ManyToOne
    public OrganisationUnit getOrganisationUnit() {
        return organisationUnit;
    }

    public void setOrganisationUnit(OrganisationUnit orgEinheit) {
        OrganisationUnit old=this.organisationUnit;
        this.organisationUnit = orgEinheit;
        firePropertyChange(PROPERTYNAME_ORGANISATIONUNIT, old, orgEinheit);
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
