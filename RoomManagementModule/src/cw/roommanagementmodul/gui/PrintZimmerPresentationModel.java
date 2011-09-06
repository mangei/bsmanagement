package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.pojo.Zimmer;
import java.awt.event.ActionEvent;
import java.util.HashMap;
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
public class PrintZimmerPresentationModel {

    private List<Zimmer> zimmerList;
    private CWHeaderInfo headerInfo;
    private Action backAction;
    private ButtonListenerSupport support;
    private JasperReport jasperReport;
    private JRDataSource ds;
    private JasperPrint jasperPrint;
    private String reportSource;

    public PrintZimmerPresentationModel(List<Zimmer> zimmerList, CWHeaderInfo headerInfo) {

        this.zimmerList = zimmerList;
        this.headerInfo = headerInfo;

        initModels();
        initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();

        reportSource = "./jasper/zimmer.jrxml";
        HashMap para = new HashMap();
        para.put("headerText", "Zimmer Liste");

        try {
            jasperReport = JasperCompileManager.compileReport(reportSource);
            ds = new JRBeanCollectionDataSource(zimmerList);
            jasperPrint = JasperFillManager.fillReport(jasperReport, para, ds);
        } catch (JRException ex) {
            Logger.getLogger(PrintZimmerPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initEventHandling() {
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
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    /**
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }

    /**
     * @return the jasperPrint
     */
    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void dispose() {
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();  // Zur Uebersicht wechseln
        }
    }
}
