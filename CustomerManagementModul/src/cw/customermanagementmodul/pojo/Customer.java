
package cw.customermanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.Type;

/**
 * Speichert einen Kunden
 * 
 * Beinhaltet alle Buchungen die einen Kunden betreffen
 **/
@Entity
@Table(name = "customers")
public class Customer
        extends Model
        implements AnnotatedClass {

    private Long id;
    private boolean active;
    
    //General Information
    private String title                = "";
    private String forename             = "";
    private String forename2            = "";
    private String surname              = "";
    private boolean gender              = true;
    private String street               = "";
    private Integer postOfficeNumber;
    private String city                 = "";
    private String province             = "";
    private String country              = "";
    private Date birthday;
    private String landlinephone        = "";
    private String mobilephone          = "";
    private String fax                  = "";
    private String email                = "";
    private Date creationdate;
    private String comment              = "";
    
    private List<Group> groups          = new ArrayList<Group>();

    private List<Accounting> accountings = new ArrayList<Accounting>();
    
    // Properties - Constants
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_TITLE = "title";
    public final static String PROPERTYNAME_FORENAME = "forename";
    public final static String PROPERTYNAME_FORENAME2 = "forename2";
    public final static String PROPERTYNAME_SURNAME = "surname";
    public final static String PROPERTYNAME_GENDER = "gender";
    public final static String PROPERTYNAME_STREET = "street";
    public final static String PROPERTYNAME_CITY = "city";
    public final static String PROPERTYNAME_POSTOFFICENUMBER = "postOfficeNumber";
    public final static String PROPERTYNAME_PROVINCE = "province";
    public final static String PROPERTYNAME_COUNTRY = "country";
    public final static String PROPERTYNAME_BIRTHDAY = "birthday";
    public final static String PROPERTYNAME_LANDLINEPHONE = "landlinephone";
    public final static String PROPERTYNAME_MOBILEPHONE = "mobilephone";
    public final static String PROPERTYNAME_EMAIL = "email";
    public final static String PROPERTYNAME_FAX = "fax";
    public final static String PROPERTYNAME_CREATIONDATE = "creationdate";
    public final static String PROPERTYNAME_COMMENT = "comment";
    public final static String PROPERTYNAME_ACTIVE = "active";
    public final static String PROPERTYNAME_GROUPS = "groups";
    public final static String PROPERTYNAME_ACCOUNTINGS = "accountings";
    
    
    public Customer() {
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append("\nId: ");
        buf.append(id);
        buf.append("\nTitle: ");
        buf.append(title);
        buf.append("\nForename: ");
        buf.append(forename);
        buf.append("\nSurname: ");
        buf.append(surname);

        buf.append(id);
        buf.append(", ");
        buf.append(title);
        buf.append(", ");
        buf.append(forename);
        buf.append(", ");
        buf.append(forename2);
        buf.append(", ");
        buf.append(surname);
        buf.append(", ");
        buf.append(street);
        buf.append(", ");
        buf.append(city);
        buf.append(", ");
        buf.append(province);
        buf.append(", ");
        buf.append(country);
        buf.append(", ");
        buf.append(mobilephone);
        buf.append(", ");
        buf.append(landlinephone);
        buf.append(", ");
        buf.append(gender);
        buf.append(", ");
        buf.append(email);
        buf.append(", ");
        buf.append(fax);
        buf.append(", ");
        buf.append(comment);
        buf.append(", ");
        buf.append(birthday);
        buf.append(", ");
        buf.append(creationdate);

        return buf.toString();
    }

    @Column(name="title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String old = this.title;
        this.title = title;
        firePropertyChange(PROPERTYNAME_TITLE, old, title);
    }

    @Column(name="forename")
    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        String old = this.forename;
        this.forename = forename;
        firePropertyChange(PROPERTYNAME_FORENAME, old, forename);
    }

    @Column(name="forename2")
    public String getForename2() {
        return forename2;
    }

    public void setForename2(String forename2) {
        String old = this.forename2;
        this.forename2 = forename2;
        firePropertyChange(PROPERTYNAME_FORENAME2, old, forename2);
    }

    @Column(name="surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        String old = this.surname;
        this.surname = surname;
        firePropertyChange(PROPERTYNAME_SURNAME, old, surname);
    }

    /**
     * Returns the gender
     * @return true -> man, false -> woman
     */
    @Column(name="gender")
    public boolean getGender() {
        return gender;
    }

    /**
     * Sets the gender
     * @param gender true -> man, false -> woman
     */
    public void setGender(boolean gender) {
        boolean old = this.gender;
        this.gender = gender;
        firePropertyChange(PROPERTYNAME_GENDER, old, gender);
    }
    
    @Column(name="street")
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        String old = this.street;
        this.street = street;
        firePropertyChange(PROPERTYNAME_STREET, old, street);
    }

    @Column(name="city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        String old = this.city;
        this.city = city;
        firePropertyChange(PROPERTYNAME_CITY, old, city);
    }

    @Column(name="birthday")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        Date old = this.birthday;
        this.birthday = birthday;
        firePropertyChange(PROPERTYNAME_BIRTHDAY, old, birthday);
        
    }

    @Column(name="landlinephone")
    public String getLandlinephone() {
        return landlinephone;
    }

    public void setLandlinephone(String landlinephone) {
        String old = this.landlinephone;
        this.landlinephone = landlinephone;
        firePropertyChange(PROPERTYNAME_LANDLINEPHONE, old, landlinephone);
    }

    @Column(name="mobilephone")
    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        String old = this.mobilephone;
        this.mobilephone = mobilephone;
        firePropertyChange(PROPERTYNAME_MOBILEPHONE, old, mobilephone);
    }

    @Column(name="fax")
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        String old = this.fax;
        this.fax = fax;
        firePropertyChange(PROPERTYNAME_FAX, old, fax);
    }

    @Column(name="email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String old = this.email;
        this.email = email;
        firePropertyChange(PROPERTYNAME_EMAIL, old, email);
    }

    @Column(name="creationdate")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date erstelldatum) {
        Date old = this.creationdate;
        this.creationdate = erstelldatum;
        firePropertyChange(PROPERTYNAME_CREATIONDATE, old, erstelldatum);
    }

    @Column(name="comment")
    @Type(type="text")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        String old = this.comment;
        this.comment = comment;
        firePropertyChange(PROPERTYNAME_COMMENT, old, comment);
    }

    @Column(name="isActive")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        boolean old = this.active;
        this.active = active;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

    @Column(name="postofficenumber")
    public Integer getPostOfficeNumber() {
        return postOfficeNumber;
    }

    public void setPostOfficeNumber(Integer postOfficeNumber) {
        Integer old = this.postOfficeNumber;
        this.postOfficeNumber = postOfficeNumber;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

    @Column(name="province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        String old = this.province;
        this.province = province;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

    @Column(name="country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String state) {
        String old = this.country;
        this.country = state;
        firePropertyChange(PROPERTYNAME_ACTIVE, old, active);
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        Long old = this.id;
        this.id = id;
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    @ManyToMany
    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        List<Group> old = this.groups;
        this.groups = groups;
        firePropertyChange(PROPERTYNAME_GROUPS, old, groups);
    }

    
    @OneToMany(mappedBy = "customer")
    public List<Accounting> getAccountings() {
        return accountings;
    }

    public void setAccountings(List<Accounting> accountings) {
        List<Accounting> old = this.accountings;
        this.accountings = accountings;
        firePropertyChange(PROPERTYNAME_ACCOUNTINGS, old, accountings);
    }

}
