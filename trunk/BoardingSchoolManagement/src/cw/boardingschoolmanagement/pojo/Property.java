package cw.boardingschoolmanagement.pojo;

import cw.boardingschoolmanagement.app.CWModel;

/**
 * A Property
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class Property extends CWModel
{

    private String name;
    private String value;

    public Property() {
        this("","");
    }

    public Property(Property property) {
        this(property.name, property.value);
    }
    
    public Property(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        Property prob = (Property)obj;
        if(prob.name.equals(this.name) && prob.value.equals(this.value)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "\"" + name + "\": \"" + value + "\"";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        super.firePropertyChange("name", old, name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        String old = this.value;
        this.value = value;
        super.firePropertyChange("value", old, value);
    }
}
