package cw.boardingschoolmanagement.gui.component;

import com.jgoodies.binding.value.ValueHolder;
import java.beans.PropertyChangeListener;
import java.beans.VetoableChangeListener;
import java.util.HashMap;

/**
 *
 * @author ManuelG
 */
public class CWValueHolder implements CWValueModel {

    private ValueHolder valueHolder;
    private HashMap clientPropertyMap = new HashMap();

    public CWValueHolder() {
        valueHolder = new ValueHolder();
    }

    public CWValueHolder(Object initialValue) {
        valueHolder = new ValueHolder(initialValue);
    }

    public CWValueHolder(boolean initialValue) {
        valueHolder = new ValueHolder(initialValue);
    }

    public CWValueHolder(double initialValue) {
        valueHolder = new ValueHolder(initialValue);
    }

    public CWValueHolder(float initialValue) {
        valueHolder = new ValueHolder(initialValue);
    }

    public CWValueHolder(int initialValue) {
        valueHolder = new ValueHolder(initialValue);
    }

    public CWValueHolder(long initialValue) {
        valueHolder = new ValueHolder(initialValue);
    }

    public CWValueHolder(Object initialValue, boolean checkIdentity) {
        valueHolder = new ValueHolder(initialValue, checkIdentity);
    }


    // NEW METHODS

    public void putClientProperty(Object key, Object value) {
        clientPropertyMap.put(key, value);
    }

    public Object getClientProperty(Object key) {
        return clientPropertyMap.get(key);
    }


    // DELEGATION

    public int hashCode() {
        return valueHolder.hashCode();
    }

    public boolean equals(Object obj) {
        return valueHolder.equals(obj);
    }

    public final synchronized void removeVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
        valueHolder.removeVetoableChangeListener(propertyName, listener);
    }

    public final synchronized void removeVetoableChangeListener(VetoableChangeListener listener) {
        valueHolder.removeVetoableChangeListener(listener);
    }

    public final synchronized void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        valueHolder.removePropertyChangeListener(propertyName, listener);
    }

    public final synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        valueHolder.removePropertyChangeListener(listener);
    }

    public final synchronized VetoableChangeListener[] getVetoableChangeListeners(String propertyName) {
        return valueHolder.getVetoableChangeListeners(propertyName);
    }

    public final synchronized VetoableChangeListener[] getVetoableChangeListeners() {
        return valueHolder.getVetoableChangeListeners();
    }

    public final synchronized PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
        return valueHolder.getPropertyChangeListeners(propertyName);
    }

    public final synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        return valueHolder.getPropertyChangeListeners();
    }

    public final synchronized void addVetoableChangeListener(String propertyName, VetoableChangeListener listener) {
        valueHolder.addVetoableChangeListener(propertyName, listener);
    }

    public final synchronized void addVetoableChangeListener(VetoableChangeListener listener) {
        valueHolder.addVetoableChangeListener(listener);
    }

    public final synchronized void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        valueHolder.addPropertyChangeListener(propertyName, listener);
    }

    public final synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        valueHolder.addPropertyChangeListener(listener);
    }

    public String toString() {
        return valueHolder.toString();
    }

    public final void setValue(long l) {
        valueHolder.setValue(l);
    }

    public final void setValue(int i) {
        valueHolder.setValue(i);
    }

    public final void setValue(float f) {
        valueHolder.setValue(f);
    }

    public final void setValue(double d) {
        valueHolder.setValue(d);
    }

    public final void setValue(boolean b) {
        valueHolder.setValue(b);
    }

    public final void removeValueChangeListener(PropertyChangeListener l) {
        valueHolder.removeValueChangeListener(l);
    }

    public final long longValue() {
        return valueHolder.longValue();
    }

    public final int intValue() {
        return valueHolder.intValue();
    }

    public String getString() {
        return valueHolder.getString();
    }

    public final float floatValue() {
        return valueHolder.floatValue();
    }

    public final void fireValueChange(float oldValue, float newValue) {
        valueHolder.fireValueChange(oldValue, newValue);
    }

    public final void fireValueChange(double oldValue, double newValue) {
        valueHolder.fireValueChange(oldValue, newValue);
    }

    public final void fireValueChange(long oldValue, long newValue) {
        valueHolder.fireValueChange(oldValue, newValue);
    }

    public final void fireValueChange(int oldValue, int newValue) {
        valueHolder.fireValueChange(oldValue, newValue);
    }

    public final void fireValueChange(boolean oldValue, boolean newValue) {
        valueHolder.fireValueChange(oldValue, newValue);
    }

    public final void fireValueChange(Object oldValue, Object newValue, boolean checkIdentity) {
        valueHolder.fireValueChange(oldValue, newValue, checkIdentity);
    }

    public final void fireValueChange(Object oldValue, Object newValue) {
        valueHolder.fireValueChange(oldValue, newValue);
    }

    public final double doubleValue() {
        return valueHolder.doubleValue();
    }

    public final boolean booleanValue() {
        return valueHolder.booleanValue();
    }

    public final void addValueChangeListener(PropertyChangeListener l) {
        valueHolder.addValueChangeListener(l);
    }

    public void setValue(Object newValue, boolean checkIdentity) {
        valueHolder.setValue(newValue, checkIdentity);
    }

    public void setValue(Object newValue) {
        valueHolder.setValue(newValue);
    }

    public void setIdentityCheckEnabled(boolean checkIdentity) {
        valueHolder.setIdentityCheckEnabled(checkIdentity);
    }

    public boolean isIdentityCheckEnabled() {
        return valueHolder.isIdentityCheckEnabled();
    }

    public Object getValue() {
        return valueHolder.getValue();
    }

}
