package cw.boardingschoolmanagement.pojo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * A Property
 * 
 * @author Manuel Geier (CreativeWorkers)
 */
public class Property
{

    private String name;
    private String value;
    protected transient PropertyChangeSupport support;

    public Property() {
        this("","");
    }

    public Property(String name, String value) {
        this.name = name;
        this.value = value;
        support = new PropertyChangeSupport(this);
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
        return name + ": " + value;
    }
    
    public synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.removePropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public synchronized boolean hasListeners(String propertyName) {
        return support.hasListeners(propertyName);
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return support.getPropertyChangeListeners(propertyName);
    }

    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        return support.getPropertyChangeListeners();
    }

    public synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        support.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        support.firePropertyChange("name", old, name);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        String old = this.value;
        this.value = value;
        support.firePropertyChange("value", old, value);
    }
}
