package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author ManuelG
 */
@Entity
@Table(name="Groups")
public class Group
        extends Model
        implements AnnotatedClass {

    private Long id;
    private String name;
    private boolean deletable;
    private Group parent;
    private List<Customer> customers = new ArrayList<Customer>();
    private List<Group> children = new ArrayList<Group>() {

        @Override
        public boolean add(Group e) {
            e.setParent(Group.this);
            return super.add(e);
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof Group) {
                ((Group) o).setParent(null);
            }
            return super.remove(o);
        }

        @Override
        public Group remove(int index) {
            Group g = super.remove(index);
            g.setParent(null);
            return g;
        }
    };

    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_DELETABLE = "deletable";
    public final static String PROPERTYNAME_CUSTOMERS = "customers";
    public final static String PROPERTYNAME_PARENT = "parent";
    public final static String PROPERTYNAME_CHILDREN = "children";

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public Group(String name, boolean deletable) {
        this.name = name;
        this.deletable = deletable;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Group)) return false;
        if (this.getId() == ((Group) obj).getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return name;
    }

    @ManyToMany(mappedBy = "groups")
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        List<Customer> old = this.customers;
        this.customers = customers;
        firePropertyChange(PROPERTYNAME_CUSTOMERS, old, customers);
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        boolean old = this.deletable;
        this.deletable = deletable;
        firePropertyChange(PROPERTYNAME_DELETABLE, old, deletable);
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

    @OneToMany(mappedBy = "parent")
    public List<Group> getChildren() {
        return children;
    }

    public void setChildren(List<Group> children) {
        List<Group> old = children;
        this.children = children;
        firePropertyChange(PROPERTYNAME_CHILDREN, old, children);
    }

    @ManyToOne
    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        Group old = this.parent;
        this.parent = parent;
        firePropertyChange(PROPERTYNAME_PARENT, old, parent);
    }
}
