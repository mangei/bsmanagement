/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.interfaces.Disposable;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.manager.BewohnerManager;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 *
 * @author Dominik
 */
public class StornoResultPresentationModel implements Disposable{

    private String headerText;
    private Action backAction;
    private Action printAction;
    private ButtonListenerSupport support;
    private List<Posting> postingList;
    private Map<Customer, List<Posting>> customerPostingMap;
    private Map<Bewohner, List<Posting>> bewohnerPostingMap;
    private Map<Customer, List<Posting>> customerNoBewohnerMap;
    private HeaderInfo headerInfo;

    public StornoResultPresentationModel(List<Posting> postingList, HeaderInfo header) {
        this.postingList = postingList;
        this.headerInfo = header;
        initModels();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
        printAction = new PrintAction();

        customerPostingMap = new HashMap<Customer, List<Posting>>();
        customerNoBewohnerMap = new HashMap<Customer, List<Posting>>();
        setBewohnerPostingMap(new HashMap<Bewohner, List<Posting>>());

        List<Customer> cList = new ArrayList<Customer>();
        for (int i = 0; i < postingList.size(); i++) {
            if (!cList.contains(postingList.get(i).getCustomer())) {
                cList.add(postingList.get(i).getCustomer());
            }
        }

        BewohnerManager bewManager = BewohnerManager.getInstance();
        for (int i = 0; i < cList.size(); i++) {

            Customer c = cList.get(i);
            List<Posting> pList = new ArrayList<Posting>();
            for (int j = 0; j < postingList.size(); j++) {

                if (c.equals(postingList.get(j).getCustomer())) {
                    pList.add(postingList.get(j));
                }
            }
            customerPostingMap.put(c, pList);
            Bewohner b = bewManager.getBewohner(c);
            if (b == null) {
                customerNoBewohnerMap.put(c, pList);
            } else {
                getBewohnerPostingMap().put(b, pList);
            }

        }
    }

    public List<Bewohner> getBewohner() {

        List<Bewohner> bewohnerList = new ArrayList<Bewohner>();
        Set keySet = bewohnerPostingMap.keySet();
        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            bewohnerList.add((Bewohner) iterator.next());
        }
        return bewohnerList;
    }

    public List<Customer> getCustomerNoBewohner() {

        List<Customer> customerList = new ArrayList<Customer>();
        Set keySet = customerNoBewohnerMap.keySet();
        Iterator iterator = keySet.iterator();

        while (iterator.hasNext()) {
            customerList.add((Customer) iterator.next());
        }
        return customerList;
    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @param headerText the headerText to set
     */
    public void setHeaderText(String headerText) {
        this.headerText = headerText;
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
     * @return the printAction
     */
    public Action getPrintAction() {
        return printAction;
    }

    /**
     * @param printAction the printAction to set
     */
    public void setPrintAction(Action printAction) {
        this.printAction = printAction;
    }

    public void removeButtonListener(ButtonListener listener) {
        support.removeButtonListener(listener);
    }

    public void addButtonListener(ButtonListener listener) {
        support.addButtonListener(listener);
    }

    /**
     * @return the postingList
     */
    public List<Posting> getPostingList() {
        return postingList;
    }

    /**
     * @param postingList the postingList to set
     */
    public void setPostingList(List<Posting> postingList) {
        this.postingList = postingList;
    }

    /**
     * @return the bewohnerPostingMap
     */
    public Map<Bewohner, List<Posting>> getBewohnerPostingMap() {
        return bewohnerPostingMap;
    }

    /**
     * @param bewohnerPostingMap the bewohnerPostingMap to set
     */
    public void setBewohnerPostingMap(Map<Bewohner, List<Posting>> bewohnerPostingMap) {
        this.bewohnerPostingMap = bewohnerPostingMap;
    }

    /**
     * @return the customerNoBewohnerMap
     */
    public Map<Customer, List<Posting>> getCustomerNoBewohnerMap() {
        return customerNoBewohnerMap;
    }

    /**
     * @param customerNoBewohnerMap the customerNoBewohnerMap to set
     */
    public void setCustomerNoBewohnerMap(Map<Customer, List<Posting>> customerNoBewohnerMap) {
        this.customerNoBewohnerMap = customerNoBewohnerMap;
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

    public void dispose() {

    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();  // Zur Übersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
        //support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));

        }
    }

    private class PrintAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/printer.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final PrintStornoPresentationModel model = new PrintStornoPresentationModel(bewohnerPostingMap,customerNoBewohnerMap, new HeaderInfo("Storno Lauf", "Storno Lauf zum Ausdrucken."));
            final PrintStornoView printView = new PrintStornoView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToLastView();
                    }
                }
            });
            GUIManager.changeView(printView.buildPanel(), true);

        }
    }
}
