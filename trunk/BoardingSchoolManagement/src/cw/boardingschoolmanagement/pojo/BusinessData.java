package cw.boardingschoolmanagement.pojo;

import com.jgoodies.binding.beans.Model;

/**
 * @author ManuelG
 * @version 1.0
 *
 * POJO zur Speicherung der Unternehmensdaten
 */
public class BusinessData extends Model{

    private String name;
    private String postOfficeNumber;
    private String city;
    private String street;
    private String landlinephone;
    private String mobilephone;
    private String fax;
    private String email;
    private String dvrNumber;

    public final static String PROPERTYNAME_NAME = "name";
    public final static String PROPERTYNAME_POSTOFFICENUMBER = "postOfficeNumber";
    public final static String PROPERTYNAME_CITY = "city";
    public final static String PROPERTYNAME_STREET = "street";
    public final static String PROPERTYNAME_LANDLINEPHONE = "landlinephone";
    public final static String PROPERTYNAME_MOBILEPHONE = "mobilephone";
    public final static String PROPERTYNAME_FAX = "fax";
    public final static String PROPERTYNAME_EMAIL = "email";
    public final static String PROPERTYNAME_DVRNUMBER = "dvrNumber";

    public BusinessData() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        String old = this.city;
        this.city = city;
        firePropertyChange(PROPERTYNAME_CITY, old, city);
    }

    public String getDvrNumber() {
        return dvrNumber;
    }

    public void setDvrNumber(String dvrNumber) {
        String old = this.dvrNumber;
        this.dvrNumber = dvrNumber;
        firePropertyChange(PROPERTYNAME_DVRNUMBER, old, dvrNumber);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String old = this.email;
        this.email = email;
        firePropertyChange(PROPERTYNAME_EMAIL, old, email);
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        String old = this.fax;
        this.fax = fax;
        firePropertyChange(PROPERTYNAME_FAX, old, fax);
    }

    public String getLandlinephone() {
        return landlinephone;
    }

    public void setLandlinephone(String landlinephone) {
        String old = this.landlinephone;
        this.landlinephone = landlinephone;
        firePropertyChange(PROPERTYNAME_LANDLINEPHONE, old, landlinephone);
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        String old = this.mobilephone;
        this.mobilephone = mobilephone;
        firePropertyChange(PROPERTYNAME_MOBILEPHONE, old, mobilephone);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        firePropertyChange(PROPERTYNAME_NAME, old, name);
    }

    public String getPostOfficeNumber() {
        return postOfficeNumber;
    }

    public void setPostOfficeNumber(String postOfficeNumber) {
        String old = this.postOfficeNumber;
        this.postOfficeNumber = postOfficeNumber;
        firePropertyChange(PROPERTYNAME_POSTOFFICENUMBER, old, postOfficeNumber);
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        String old = this.street;
        this.street = street;
        firePropertyChange(PROPERTYNAME_STREET, old, street);
    }

}
