package cw.roommanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.CWPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.customer.persistence.Customer;
import cw.roommanagementmodul.persistence.Bewohner;
import cw.roommanagementmodul.persistence.PMBewohner;

/**
 *
 * @author Dominik
 */
public class StornoResultPresentationModel
	extends CWPresentationModel
{

    private String headerText;
    private Action backAction;
    private Action printAction;
    private ButtonListenerSupport support;
    private List<AccountPosting> postingList;
    private Map<Customer, List<AccountPosting>> customerPostingMap;
    private Map<Bewohner, List<AccountPosting>> bewohnerPostingMap;
    private Map<Customer, List<AccountPosting>> customerNoBewohnerMap;
    private CWHeaderInfo headerInfo;

    public StornoResultPresentationModel(List<AccountPosting> postingList, CWHeaderInfo header) {
        this.postingList = postingList;
        this.headerInfo = header;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        support = new ButtonListenerSupport();
        backAction = new BackAction();
        printAction = new PrintAction();

        customerPostingMap = new HashMap<CustomerModel, List<AccountPosting>>();
        customerNoBewohnerMap = new HashMap<CustomerModel, List<AccountPosting>>();
        setBewohnerPostingMap(new HashMap<Bewohner, List<AccountPosting>>());

        List<CustomerModel> cList = new ArrayList<CustomerModel>();
        for (int i = 0; i < postingList.size(); i++) {
            if (!cList.contains(postingList.get(i).getCustomer())) {
                cList.add(postingList.get(i).getCustomer());
            }
        }

        PMBewohner bewManager = PMBewohner.getInstance();
        for (int i = 0; i < cList.size(); i++) {

            CustomerModel c = cList.get(i);
            List<AccountPosting> pList = new ArrayList<AccountPosting>();
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

    private void initEventHandling() {
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
     * @return the backAction
     */
    public Action getBackAction() {
        return backAction;
    }

    /**
     * @return the printAction
     */
    public Action getPrintAction() {
        return printAction;
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
    public List<AccountPosting> getPostingList() {
        return postingList;
    }

    /**
     * @param postingList the postingList to set
     */
    public void setPostingList(List<AccountPosting> postingList) {
        this.postingList = postingList;
    }

    /**
     * @return the bewohnerPostingMap
     */
    public Map<Bewohner, List<AccountPosting>> getBewohnerPostingMap() {
        return bewohnerPostingMap;
    }

    /**
     * @param bewohnerPostingMap the bewohnerPostingMap to set
     */
    public void setBewohnerPostingMap(Map<Bewohner, List<AccountPosting>> bewohnerPostingMap) {
        this.bewohnerPostingMap = bewohnerPostingMap;
    }

    /**
     * @return the customerNoBewohnerMap
     */
    public Map<CustomerModel, List<AccountPosting>> getCustomerNoBewohnerMap() {
        return customerNoBewohnerMap;
    }

    /**
     * @param customerNoBewohnerMap the customerNoBewohnerMap to set
     */
    public void setCustomerNoBewohnerMap(Map<CustomerModel, List<AccountPosting>> customerNoBewohnerMap) {
        this.customerNoBewohnerMap = customerNoBewohnerMap;
    }

    /**
     * @return the headerInfo
     */
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void dispose() {
    }

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/arrow_left.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToPreviousView();  // Zur Uebersicht wechseln
//                GUIManager.removeView(); // Diese View nicht merken
        //support.fireButtonPressed(new ButtonEvent(ButtonEvent.EXIT_BUTTON));

        }
    }

    private class PrintAction extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/printer.png"));
        }

        public void actionPerformed(ActionEvent e) {
            final PrintStornoPresentationModel model = new PrintStornoPresentationModel(bewohnerPostingMap, customerNoBewohnerMap, new CWHeaderInfo("Storno Lauf", "Storno Lauf zum Ausdrucken."), headerInfo.getHeaderText());
            final PrintStornoView printView = new PrintStornoView(model);
            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    if (evt.getType() == ButtonEvent.SAVE_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                    }
                    if (evt.getType() == ButtonEvent.EXIT_BUTTON || evt.getType() == ButtonEvent.SAVE_EXIT_BUTTON) {
                        model.removeButtonListener(this);
                        GUIManager.changeToPreviousView();
                    }
                }
            });
            GUIManager.changeViewTo(printView, true);

        }
    }
}
