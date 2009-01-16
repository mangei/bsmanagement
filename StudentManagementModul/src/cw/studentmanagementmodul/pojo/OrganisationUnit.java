package cw.studentmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * @author CreativeWorkers.at
 */
@Entity
public class OrganisationUnit 
        extends Model
        implements AnnotatedClass
{

    private Long id;
    private String name;
    private OrganisationUnit parent;
    private List<OrganisationUnit> children;
    private List<StudentClass> studentClasses;
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_PARENT = "parent";
    public final static String PROPERTYNAME_CHILDREN = "children";
    public final static String PROPERTYNAME_STUDENTCLASSES = "studentClasses";
    
    public OrganisationUnit() {
        children = new ArrayList<OrganisationUnit>() {
//
//            @Override
//            public boolean add(OrganisationUnit e) {
//                e.setParent(OrganisationUnit.this);
//                return super.add(e);
//            }
//
//            @Override
//            public void add(int index, OrganisationUnit element) {
//                element.setParent(OrganisationUnit.this);
//                super.add(index, element);
//            }
//
//            @Override
//            public boolean remove(Object o) {
//                ((OrganisationUnit)o).setParent(null);
//                return super.remove(o);
//            }
//
//            @Override
//            public OrganisationUnit remove(int index) {
//                get(index).setParent(null);
//                return super.remove(index);
//            }
//
        };
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        if (this.getId() != ((OrganisationUnit)obj).getId()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    @ManyToOne
    public OrganisationUnit getParent() {
        return parent;
    }

    public void setParent(OrganisationUnit parent) {
        OrganisationUnit old = this.parent;
        this.parent = parent;

//        // TODO
//        // Set the child of the new parent if it isn't already in the list
//        if(parent != null && !parent.getChildren().contains(this)) {
//            parent.getChildren().add(this);
//        }

        firePropertyChange(PROPERTYNAME_PARENT, old, parent);
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

    @OneToMany(mappedBy = "parent", cascade=CascadeType.REMOVE)
    @OrderBy("name")
    public List<OrganisationUnit> getChildren() {
        return children;
    }

    public void setChildren(List<OrganisationUnit> children) {
        List<OrganisationUnit> old = this.children;
        this.children = children;

//        // set the new Parent
//        for(int i=0, l=children.size(); i<l; i++) {
//            children.get(i).setParent(this);
//        }
        firePropertyChange(PROPERTYNAME_CHILDREN, old, children);
    }

    @OneToMany(mappedBy = "organisationUnit")
    public List<StudentClass> getStudentClasses() {
        return studentClasses;
    }

    public void setStudentClasses(List<StudentClass> studentClasses) {
        List<StudentClass> old = this.studentClasses;
        this.studentClasses = studentClasses;
        firePropertyChange(PROPERTYNAME_STUDENTCLASSES, old, studentClasses);
    }

}
