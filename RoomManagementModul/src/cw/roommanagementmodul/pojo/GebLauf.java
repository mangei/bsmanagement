/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.pojo;

import cw.boardingschoolmanagement.app.CalendarUtil;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Dominik
 */
@Entity
public class GebLauf implements AnnotatedClass {

    private Long id;
    private long abrMonat;
    private Date cpuDate;
    private boolean betriebsart;

    public GebLauf() {
    }

    public GebLauf(long abrMonat, Date cpuDate, boolean betriebsart) {
        this.abrMonat = abrMonat;
        this.cpuDate = cpuDate;
        this.betriebsart = betriebsart;
    }

    /**
     * @return the abrMonat
     */
    public long getAbrMonat() {
        return abrMonat;
    }

    /**
     * @param abrMonat the abrMonat to set
     */
    public void setAbrMonat(long abrMonat) {
        this.abrMonat = abrMonat;
    }

    /**
     * @return the cpuDate
     */
    @Temporal(TemporalType.DATE)
    public Date getCpuDate() {
        return cpuDate;
    }

    /**
     * @param cpuDate the cpuDate to set
     */
    public void setCpuDate(Date cpuDate) {
        this.cpuDate = cpuDate;
    }

    /**
     * @return the betriebsart
     */
    public boolean isBetriebsart() {
        return betriebsart;
    }

    /**
     * @param betriebsart the betriebsart to set
     */
    public void setBetriebsart(boolean betriebsart) {
        this.betriebsart = betriebsart;
    }

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(this.abrMonat);
        StringBuilder builder = new StringBuilder();
        
        builder.append(CalendarUtil.getMonth(gc.get(Calendar.MONTH)));
        builder.append(" ");
        builder.append(gc.get(Calendar.YEAR));

        return builder.toString();
    }
}
