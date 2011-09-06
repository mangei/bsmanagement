package cw.customermanagementmodul.persistence;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

import org.hibernate.annotations.Type;

import cw.boardingschoolmanagement.app.CWPersistence;

/**
 * Speichert einen Kunden
 *
 * Beinhaltet alle Buchungen die einen Kunden betreffen
 **/
@Entity(name=Customer.ENTITY_NAME)
@Table(name=Customer.TABLE_NAME)
public interface Customer extends CWPersistence {

    // Properties - Constants
    public final static String ENTITY_NAME = "customer";
    public final static String TABLE_NAME = "customer";
    public final static String PROPERTYNAME_ID = "id";
    public final static String PROPERTYNAME_TITLE = "title";
    public final static String PROPERTYNAME_FORENAME = "forename";
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
    public final static String PROPERTYNAME_CREATIONDATE = "creationDate";
    public final static String PROPERTYNAME_INACTIVEDATE = "inactiveDate";
    public final static String PROPERTYNAME_COMMENT = "comment";
    public final static String PROPERTYNAME_ACTIVE = "active";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name=PROPERTYNAME_ID)
    public Long getId();
    public void setId(Long id);
    
    @Column(name=PROPERTYNAME_TITLE)
    public String getTitle();
    public void setTitle(String title);

    public String getForename();
    public void setForename(String forename);

    public String getSurname();
    public void setSurname(String surname);
    
    /**
     * Returns the gender
     * @return true -> man, false -> woman
     */
    public boolean getGender();

    /**
     * Sets the gender
     * @param gender true -> man, false -> woman
     */
    public void setGender(boolean gender);

    public String getStreet();
    public void setStreet(String street);
    
    public String getCity();
    public void setCity(String city);

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getBirthday();
    public void setBirthday(Date birthday);

    public String getLandlinephone();
    public void setLandlinephone(String landlinephone);

    public String getMobilephone();
    public void setMobilephone(String mobilephone);

    public String getFax();
    public void setFax(String fax);
    
    public String getEmail();
    public void setEmail(String email);

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getCreationDate();
    public void setCreationDate(Date creationDate);

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    public Date getInactiveDate();
    public void setInactiveDate(Date inactiveDate);

    @Type(type="text")
    public String getComment();
    public void setComment(String comment);
    
    public boolean isActive();
    public void setActive(boolean active);

    public String getPostOfficeNumber();
    public void setPostOfficeNumber(String postOfficeNumber);

    public String getProvince();
    public void setProvince(String province);

    public String getCountry();
    public void setCountry(String country);

}
