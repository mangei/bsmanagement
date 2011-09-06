package cw.customermanagementmodul.persistence;

import java.util.Date;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.app.CWPersistenceImpl;

/**
 * Implementation of persistence 'Customer'
 * 
 * @author Manuel Geier
 **/
class CustomerImpl
        extends CWPersistenceImpl
        implements Customer {

    private Long id;
    private boolean active              = true;
    private String title                = "";
    private String forename             = "";
    private String surname              = "";
    private boolean gender              = true;
    private String street               = "";
    private String postOfficeNumber     = "";
    private String city                 = "";
    private String province             = "";
    private String country              = "";
    private Date birthday;
    private String landlinephone        = "";
    private String mobilephone          = "";
    private String fax                  = "";
    private String email                = "";
    private Date creationDate;
    private Date inactiveDate;
    private String comment              = "";

    CustomerImpl(EntityManager entityManager) {
    	super(entityManager);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        if(this.id == null || ((CustomerImpl)obj).id == null) {
            return false;
        }
        if(this.id!=((CustomerImpl)obj).id){
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
        buf.append(creationDate);
        buf.append(", ");
        buf.append(inactiveDate);

        return buf.toString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
    	String old = this.title;
        this.title = title;
        firePropertyChange(Customer.PROPERTYNAME_TITLE, old, title);
    }

	public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
    	String old = this.forename;
        this.forename = forename;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
    	String old = this.surname;
        this.surname = surname;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    /**
     * Returns the gender
     * @return true -> man, false -> woman
     */
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
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
    	String old = this.street;
        this.street = street;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
    	String old = this.city;
        this.city = city;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
    	Date old = this.birthday;
        this.birthday = birthday;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);

    }

    public String getLandlinephone() {
        return landlinephone;
    }

    public void setLandlinephone(String landlinephone) {
    	String old = this.landlinephone;
        this.landlinephone = landlinephone;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
    	String old = this.mobilephone;
        this.mobilephone = mobilephone;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
    	String old = this.fax;
        this.fax = fax;
        firePropertyChange(Customer.PROPERTYNAME_FORENAME, old, forename);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
    	String old = this.email;
        this.email = email;
        firePropertyChange(Customer.PROPERTYNAME_EMAIL, old, email);
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
    	Date old = this.creationDate;
        this.creationDate = creationDate;
        firePropertyChange(Customer.PROPERTYNAME_CREATIONDATE, old, creationDate);
    }

    public Date getInactiveDate() {
        return inactiveDate;
    }

    public void setInactiveDate(Date inactiveDate) {
    	Date old = this.inactiveDate;
        this.inactiveDate = inactiveDate;
        firePropertyChange(Customer.PROPERTYNAME_INACTIVEDATE, old, inactiveDate);
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
    	String old = this.comment;
        this.comment = comment;
        firePropertyChange(Customer.PROPERTYNAME_COMMENT, old, comment);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
    	boolean old = this.active;
        this.active = active;
        firePropertyChange(Customer.PROPERTYNAME_ACTIVE, old, active);

        if(active) {
            setInactiveDate(null);
        } else {
            setInactiveDate(new Date());
        }
    }

    public String getPostOfficeNumber() {
        return postOfficeNumber;
    }

    public void setPostOfficeNumber(String postOfficeNumber) {
    	String old = this.postOfficeNumber;
        this.postOfficeNumber = postOfficeNumber;
        firePropertyChange(Customer.PROPERTYNAME_POSTOFFICENUMBER, old, postOfficeNumber);
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
    	String old = this.province;
        this.province = province;
        firePropertyChange(Customer.PROPERTYNAME_PROVINCE, old, province);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
    	String old = this.country;
        this.country = country;
        firePropertyChange(Customer.PROPERTYNAME_COUNTRY, old, country);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
    	Long old = this.id;
        this.id = id;
        firePropertyChange(Customer.PROPERTYNAME_ID, old, id);
    }

}
