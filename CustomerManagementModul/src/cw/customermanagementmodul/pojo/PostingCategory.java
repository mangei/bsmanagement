package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * This class represents a category of the postings. <br>
 * The key is necessary, to connect extentions to a category.
 * @author CreativeWorkers.at
 */
@Entity
public class PostingCategory
        extends Model
        implements AnnotatedClass {
    
    private Long id;
    private String name;
    private String key;
    private List<Posting> postings;
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_KEY = "key";
    public final static String PROPERTYNAME_POSTINGS = "postings";
    
    public PostingCategory(){
    }

    /**
     * Create a new posting-category
     * @param name Name of the posting-category
     */
    public PostingCategory(String name) {
        this.name = name;
    }

    /**
     * Create a new posting-category
     * @param name Name of the posting-category
     * @param key The key of the category
     */
    public PostingCategory(String name, String key) {
        this.name = name;
        this.key = key;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return false;
        if(!(obj instanceof PostingCategory)) return false;
        if(this.getId()!=((PostingCategory)obj).getId()){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getName();
    }

    /**
     * Get the identical number of the posting-category
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
     * Get the name of posting-category
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the posting-category
     * @param name New name
     */
    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    /**
     * Get the key of the posting-category
     * @return key
     */
    @Column(name="categoryKey")
    public String getKey() {
        return key;
    }

    /**
     * Set the key of the posting-category
     * @param key New key
     */
    public void setKey(String key) {
        String old = this.key;
        this.key = key;
        firePropertyChange(PROPERTYNAME_KEY, old, key);
    }

    /**
     * Get the list of all postings, which have this posting-category
     * @return postings
     */
    @OneToMany(mappedBy = "postingCategory")
    public List<Posting> getPostings() {
        return postings;
    }

    /**
     * Set the new list of postings for this posting-category
     * @param postings
     */
    public void setPostings(List<Posting> postings) {
        List<Posting> old = this.postings;
        this.postings = postings;
        firePropertyChange(PROPERTYNAME_POSTINGS, old, postings);
    }
    
}
