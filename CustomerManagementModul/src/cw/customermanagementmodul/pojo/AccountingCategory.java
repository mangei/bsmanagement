package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class represents a category of the accountings. <br>
 * The code is necessary, to connect extentions to a category.
 * @author CreativeWorkers.at
 */
@Entity
public class AccountingCategory
        extends Model
        implements AnnotatedClass {
    
    private Long id;
    private String name;
    private String code;
    private List<Accounting> accountings;
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_CODE = "code";
    public final static String PROPERTYNAME_ACCOUNTINGS = "accountings";
    
    public AccountingCategory(){
    }

    /**
     * Create a new accounting-category
     * @param name Name of the accounting-category
     * @param code The code of the category
     */
    public AccountingCategory(String name, String code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public boolean equals(Object obj){  
        if(this.getId()!=((AccountingCategory)obj).getId()){
            return false;
        }
        return true;
    }

    /**
     * Get the identical number of the accounting-category
     * @return id
     */
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * Set the identical number<br>
     * This number is automaticly set by Hibernate
     * @param id New id
     */
    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    /**
     * Get the name of accounting-category
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the accounting-category
     * @param name New name
     */
    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    /**
     * Get the code of the accounting-category
     * @return code
     */
    public String getCode() {
        return code;
    }

    /**
     * Set the name of the accounting-category
     * @param code New code
     */
    public void setCode(String code) {
        String old = this.code;
        this.code = code;
        firePropertyChange("code", old, code);
    }

    /**
     * Get the list of all accountings, which have this accounting-category
     * @return accountings
     */
    @OneToMany(mappedBy = "category")
    public List<Accounting> getAccountings() {
        return accountings;
    }

    /**
     * Set the new list of accountings for this accounting-category
     * @param accountings
     */
    public void setAccountings(List<Accounting> accountings) {
        List<Accounting> old = this.accountings;
        this.accountings = accountings;
        firePropertyChange(PROPERTYNAME_ACCOUNTINGS, old, name);
    }
    
}
