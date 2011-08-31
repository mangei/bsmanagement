/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.customermanagementmodul.gui;

import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Group;
import cw.customermanagementmodul.pojo.manager.CustomerManager;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * PresentationModel zu CustomerPrintView
 * Dient für die logisch Abarbeitung des Druckens von Kunden und Gruppen
 *
 * @author tom
 */
public class CustomerPrintPresentationModel {

    private BackAction backAction;
    private JasperReport jasperReport;
    private JRDataSource ds;
    private JasperPrint jasperPrint;
    private String reportSource;
    private List<Customer> customerList;
    private Group group;

    public CustomerPrintPresentationModel(List<Customer> l, Group g) {
        this.customerList = l;
        this.group = g;

        this.initModel();
    }

    private void initModel() {

        backAction = new BackAction();

        try {
            reportSource = "./jasper/Report1.jrxml";
            Map<String, Object> params = new HashMap<String, Object>();
            jasperReport = JasperCompileManager.compileReport(reportSource);
            ds = new JRBeanCollectionDataSource(customerList);
            jasperPrint = JasperFillManager.fillReport(jasperReport, params, ds);
        } catch (JRException ex) {
            Logger.getLogger(CustomerPrintPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Liefert die Aktion zurück die durchgeführt werden soll wenn man auf den
     * Zurück-Button drückt.
     * @return the backAction
     *
     */
    public BackAction getBackAction() {
        return backAction;
    }

    /**
     * Lifert den druckbereiten JasperReport zurück,
     *
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

 
    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.getInstance().unlockMenu();
            GUIManager.changeToLastView();
        }
    }

    /**
     * Gibt den Speicher frei.
     *
     */
    public void dispose() {
    }
}
