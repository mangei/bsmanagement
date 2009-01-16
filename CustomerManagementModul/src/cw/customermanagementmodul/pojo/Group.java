package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
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
@Table(name = "groups")
public class Group
        extends Model
        implements AnnotatedClass {

    private Long id;
    private String name;
    private boolean deletable;
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_DELETABLE = "deletable";
    public final static String PROPERTYNAME_CUSTOMERS = "customers";
    public final static String PROPERTYNAME_PARENT = "parent";
    public final static String PROPERTYNAME_CHILDREN = "children";
    private Group parent;
    private List<Customer> customers = new ArrayList<Customer>() {
//        @Override
//        public boolean add(Costumer c) {
//            c.getGroups().add(Group.this);
//            return super.add(c);
//        }
//
//        @Override
//        public boolean remove(Object o) {
//            if (o instanceof Costumer) {
//                ((Costumer) o).getGroups().remove(Group.this);
//            }
//            return super.remove(o);
//        }
//
//        @Override
//        public Costumer remove(int index) {
//            Costumer c = super.remove(index);
//            c.getGroups().remove(Group.this);
//            return c;
//        }
    };
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
    public String toString() {
        return name;
    }

    @ManyToMany(mappedBy = "groups")
//    @JoinTable(name="groups_costumers",
//        joinColumns=        @JoinColumn(name="group_id", referencedColumnName="id"),
//        inverseJoinColumns= @JoinColumn(name="costumer_id",referencedColumnName="id"))
    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        List<Customer> old = this.customers;
        this.customers = customers;
        firePropertyChange(PROPERTYNAME_CUSTOMERS, old, customers);
    }

    @Column(name = "isDeletable")
    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        boolean old = this.deletable;
        this.deletable = deletable;
        firePropertyChange(PROPERTYNAME_DELETABLE, old, deletable);
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    @Column(name = "name")
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
//        for(Group child: children) {
//            child.setParent(this);
//        }
        firePropertyChange(PROPERTYNAME_CHILDREN, old, children);
    }

    @ManyToOne
    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        Group old = this.parent;
//        this.getChildren().remove(this);
        this.parent = parent;
//        parent.getChildren().add(this);
        firePropertyChange(PROPERTYNAME_PARENT, old, parent);
    }
}
