/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.pojo.Zimmer;
import java.awt.event.ActionEvent;
import java.util.List;
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
 *
 * @author Dominik
 */
public class PrintZimmerPresentationModel implements Disposable{

    private List<Zimmer> zimmerList;
    private HeaderInfo headerInfo;
    private Action backAction;
    private ButtonListenerSupport support;
    private JasperReport jasperReport;
    private JRDataSource ds;
    private JasperPrint jasperPrint;
    private String reportSource;

    public PrintZimmerPresentationModel(List<Zimmer> zimmerList, HeaderInfo headerInfo) {

        this.zimmerList = zimmerList;
        this.headerInfo = headerInfo;

        initModels();
        this.initEventHandling();

    }

    private void initEventHandling() {
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        setBackAction(new BackAction());

        reportSource="./src/cw/boardingschoolmanagement/jasper/templates/zimmer.jrxml";

        try {
            jasperReport = JasperCompileManager.compileReport(reportSource);
            ds = new JRBeanCollectionDataSource(zimmerList);
            jasperPrint = JasperFillManager.fillReport(jasperReport, null, ds);
        } catch (JRException ex) {
            Logger.getLogger(PrintZimmerPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }

   
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    /**
     * @return the headerInfo
     */
    public HeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     * @param headerInfo the headerInfo to set
     */
    public void setHeaderInfo(HeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

    /**
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }

    /**
     * @param backAction the backAction to set
     */
    public void setBackAction(Action backAction) {
        this.backAction = backAction;
    }

    /**
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    /**
     * @param jasperPrint the jasperPrint to set
     */
    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public void dispose() {
        
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();  // Zur Übersicht wechseln
        }
    }
}
