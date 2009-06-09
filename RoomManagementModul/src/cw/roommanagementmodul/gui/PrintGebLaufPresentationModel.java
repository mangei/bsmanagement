package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.geblauf.BewohnerTarifSelection;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.BewohnerGeb;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import net.sf.jasperreports.engine.util.JRSaver;

/**
 *
 * @author Dominik
 */
public class PrintGebLaufPresentationModel{

    private BewohnerTarifSelection bewohnerTarifSelection;
    private CWHeaderInfo headerInfo;
    private Action backAction;
    private ButtonListenerSupport support;
    private JasperReport jasperReport;
    private JRDataSource ds;
    private JasperPrint jasperPrint;
    private String reportSource;
    private String printHeader;

    public PrintGebLaufPresentationModel(BewohnerTarifSelection bewohnerTarifSelection, CWHeaderInfo headerInfo,String printHeader) {

        this.bewohnerTarifSelection = bewohnerTarifSelection;
        this.headerInfo = headerInfo;
        this.printHeader=printHeader;

        initModels();
        initEventHandling();

    }

    private void initEventHandling() {
    }

    private void initModels() {
        setSupport(new ButtonListenerSupport());
        backAction=new BackAction();

        setReportSource("./jasper/bewohner.jrxml");
//        setReportSource("./cw/boardingschoolmanagement/jasper/templates/bewohner.jrxml");

        List<Bewohner> bewohnerList = getBewohnerSelection(bewohnerTarifSelection);

       for(int i=0;i<bewohnerList.size();i++){
           System.out.println(bewohnerList.get(i).getCustomer().getSurname());
       }

        System.out.println("--------------------------------------------------");
        List<BewohnerGeb> bewohnerGebList= new ArrayList<BewohnerGeb>();
        for(int i=0;i<bewohnerList.size();i++){
            bewohnerGebList.add(new BewohnerGeb(bewohnerList.get(i),bewohnerTarifSelection.get(bewohnerList.get(i))));
        }

        for(int i=0;i<bewohnerGebList.size();i++){
            System.out.println(bewohnerGebList.get(i).getCustomer().getSurname());
        }

        Map<String, Object> main = new HashMap();
//        JRDataSource tarifDS= new CustomDataSource(bewohnerList, bewohnerTarifSelection.getMap());
        JasperReport subreport=null;
        try {
//            subreport=JasperCompileManager.compileReport(ClassLoader.getSystemResourceAsStream("./jasper/gebuehren.jrxml"));
            subreport=JasperCompileManager.compileReport("./jasper/gebuehren.jrxml");
//            subreport=JasperCompileManager.compileReport("./cw/boardingschoolmanagement/jasper/templates/gebuehren.jrxml");

            JRSaver.saveObject(subreport, "./jasper/gebuehren.jasper");
//            JRSaver.saveObject(subreport, "./cw/boardingschoolmanagement/jasper/templates/gebuehren.jasper");
        } catch (JRException ex) {
            Logger.getLogger(PrintGebLaufPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }

        main.put("tarifSubreport", subreport);
        main.put("headerText",printHeader);


        try {
            setJasperReport(JasperCompileManager.compileReport(getReportSource()));
//            setJasperReport(JasperCompileManager.compileReport(ClassLoader.getSystemResourceAsStream(getReportSource())));

//
//            ds = new JRBeanCollectionDataSource(bewohnerList);
              ds= new JRBeanCollectionDataSource(bewohnerGebList);
//
              setJasperPrint(JasperFillManager.fillReport(getJasperReport(), main, ds));
//            setJasperPrint(JasperFillManager.fillReport(ClassLoader.getSystemResourceAsStream("./cw/boardingschoolmanagement/jasper/templates/bewohner.jasper"), main, ds));
        } catch (JRException ex) {
            Logger.getLogger(PrintZimmerPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Bewohner> getBewohnerSelection(BewohnerTarifSelection bewohnerTarifSelection) {
        List<Bewohner> bewohnerList = new ArrayList<Bewohner>();
        Map map = bewohnerTarifSelection.getMap();
        Set keySet = map.keySet();

        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            bewohnerList.add((Bewohner) iterator.next());
        }
        return bewohnerList;
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
     * @return the support
     */
    public ButtonListenerSupport getSupport() {
        return support;
    }

    /**
     * @param support the support to set
     */
    public void setSupport(ButtonListenerSupport support) {
        this.support = support;
    }

    /**
     * @return the jasperReport
     */
    public JasperReport getJasperReport() {
        return jasperReport;
    }

    /**
     * @param jasperReport the jasperReport to set
     */
    public void setJasperReport(JasperReport jasperReport) {
        this.jasperReport = jasperReport;
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

    /**
     * @return the reportSource
     */
    public String getReportSource() {
        return reportSource;
    }

    /**
     * @param reportSource the reportSource to set
     */
    public void setReportSource(String reportSource) {
        this.reportSource = reportSource;
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
