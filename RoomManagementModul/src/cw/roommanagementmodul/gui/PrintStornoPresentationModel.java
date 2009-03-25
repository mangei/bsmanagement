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
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.BewohnerStorno;
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
public class PrintStornoPresentationModel implements Disposable {

    private ButtonListenerSupport support;
    private HeaderInfo headerInfo;
    private Action backAction;
    private Map<Bewohner, List<Posting>> bewohnerPostingMap;
    private Map<Customer, List<Posting>> customerNoBewohnerMap;
    private List<BewohnerStorno> printDataList;
    private JasperReport jasperReport;
    private JRDataSource ds;
    private JasperPrint jasperPrint;
    private String reportSource;
    private String printHeader;

    public PrintStornoPresentationModel(Map<Bewohner, List<Posting>> bewohnerPostingMap,
            Map<Customer, List<Posting>> customerNoBewohnerMap, HeaderInfo headerInfo, String printHeader) {

        this.printHeader=printHeader;
        this.bewohnerPostingMap = bewohnerPostingMap;
        this.customerNoBewohnerMap = customerNoBewohnerMap;
        this.headerInfo = headerInfo;

        prepareData();
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();

        reportSource = "./jasper/bewohnerSto.jrxml";

        Map<String, Object> main = new HashMap();
//        JRDataSource tarifDS= new CustomDataSource(bewohnerList, bewohnerTarifSelection.getMap());
        JasperReport subreport = null;
        try {
            subreport = JasperCompileManager.compileReport("./jasper/postingSto.jrxml");
            JRSaver.saveObject(subreport, "./jasper/postingSto.jasper");
        } catch (JRException ex) {
            Logger.getLogger(PrintGebLaufPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        main.put("stornoSubreport", subreport);
        main.put("headerText", this.getPrintHeader());

        try {
            jasperReport = JasperCompileManager.compileReport(reportSource);
//            ds = new JRBeanCollectionDataSource(bewohnerList);
            ds = new JRBeanCollectionDataSource(this.printDataList);
            jasperPrint = JasperFillManager.fillReport(jasperReport, main, ds);
        } catch (JRException ex) {
            Logger.getLogger(PrintZimmerPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void dispose() {

    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    private void prepareData() {
        printDataList = new ArrayList<BewohnerStorno>();
        List<Bewohner> bewohnerList = getBewohnerSelection();
        List<Customer> customerList = getCustomerSelection();
        List<Posting> postingList;
        Bewohner bewohner;
        Customer customer;

        for (int i = 0; i < bewohnerList.size(); i++) {
            bewohner = bewohnerList.get(i);
            postingList = bewohnerPostingMap.get(bewohner);
            printDataList.add(new BewohnerStorno(bewohner.getCustomer(), bewohner.getZimmer(), postingList));
        }

        for (int i = 0; i < customerList.size(); i++) {
            customer = customerList.get(i);
            postingList = customerNoBewohnerMap.get(customer);
            printDataList.add(new BewohnerStorno(customer, null, postingList));
        }
    }

    private List<Bewohner> getBewohnerSelection() {
        List<Bewohner> bewohnerList = new ArrayList<Bewohner>();
        Map map = bewohnerPostingMap;
        Set keySet = map.keySet();

        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            bewohnerList.add((Bewohner) iterator.next());
        }
        return bewohnerList;
    }

    private List<Customer> getCustomerSelection() {
        List<Customer> customerList = new ArrayList<Customer>();
        Map map = customerNoBewohnerMap;
        Set keySet = map.keySet();

        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            customerList.add((Customer) iterator.next());
        }
        return customerList;
    }

    /**
     * @return the headerInfo
     */
    public HeaderInfo getHeaderInfo() {
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

    /**
     * @return the printHeader
     */
    public String getPrintHeader() {
        return printHeader;
    }

    /**
     * @param printHeader the printHeader to set
     */
    public void setPrintHeader(String printHeader) {
        this.printHeader = printHeader;
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
