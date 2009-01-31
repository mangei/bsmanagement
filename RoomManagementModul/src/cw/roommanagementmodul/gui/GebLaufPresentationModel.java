/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cw.roommanagementmodul.gui;

import com.jgoodies.binding.PresentationModel;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.pojo.manager.PostingManager;
import cw.roommanagementmodul.geblauf.BewohnerTarifSelection;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import cw.roommanagementmodul.app.GebLaufSelection;
import cw.roommanagementmodul.geblauf.GebTarifSelection;
import cw.roommanagementmodul.pojo.manager.BuchungsLaufZuordnungManager;
import cw.roommanagementmodul.pojo.manager.GebLaufManager;
import cw.roommanagementmodul.pojo.Bewohner;
import cw.roommanagementmodul.pojo.BuchungsLaufZuordnung;
import cw.roommanagementmodul.pojo.GebLauf;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class GebLaufPresentationModel extends PresentationModel<GebLaufSelection> {

    private String headerText;
    private GebLaufSelection gebLauf;
    private Action start;
    private Action normalLauf;
    private Action stornoLauf;
    private Action testLauf;
    private Action echtLauf;
    private Document yearDocument;
    private DefaultComboBoxModel monatCbModel;
    private boolean laufart = true;
    private boolean betriebsart = true;

    public GebLaufPresentationModel(GebLaufSelection gebLauf, String header) {
        super(gebLauf);
        this.gebLauf = gebLauf;
        this.headerText = header;
        initModels();
        this.initEventHandling();
    }


    private void initEventHandling() {
    }

    private void initModels() {
        setStart(new StartAction());
        normalLauf = new NormalLaufAction();
        stornoLauf = new StornoLaufAction();
        testLauf = new TestLaufAction();
        echtLauf = new EchtLaufAction();
        monatCbModel = new DefaultComboBoxModel();
        setYearDocument(new YearDocument());

        getMonatCbModel().addElement("Jänner");
        getMonatCbModel().addElement("Februar");
        getMonatCbModel().addElement("März");
        getMonatCbModel().addElement("April");
        getMonatCbModel().addElement("Mai");
        getMonatCbModel().addElement("Juni");
        getMonatCbModel().addElement("Juli");
        getMonatCbModel().addElement("August");
        getMonatCbModel().addElement("September");
        getMonatCbModel().addElement("Oktober");
        getMonatCbModel().addElement("November");
        getMonatCbModel().addElement("Dezember");

    }

    /**
     * @return the headerText
     */
    public String getHeaderText() {
        return headerText;
    }

    /**
     * @return the monatCbModel
     */
    public DefaultComboBoxModel getMonatCbModel() {
        return monatCbModel;
    }

    /**
     * @param start the start to set
     */
    public void setStart(Action start) {
        this.start = start;
    }

    /**
     * @return the yearDocument
     */
    public Document getYearDocument() {
        return yearDocument;
    }

    /**
     * @param yearDocument the yearDocument to set
     */
    public void setYearDocument(Document yearDocument) {
        this.yearDocument = yearDocument;
    }

    private class StartAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/lightning.png"));
        }

        public void actionPerformed(ActionEvent e) {

            int month = findMonth();
            int year = 0;
            try {
                year = Integer.parseInt(yearDocument.getText(0, yearDocument.getLength()));
            } catch (BadLocationException ex) {
                Logger.getLogger(GebLaufPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
            }

            GregorianCalendar gc = new GregorianCalendar(year, month - 1, 1);
            GebLauf gebLauf = new GebLauf(gc.getTime().getTime(), new Date(), betriebsart);

            GebLaufSelection gebLaufSelection = new GebLaufSelection();
            BewohnerTarifSelection selection = gebLaufSelection.startSelection(gebLauf.getAbrMonat());

            if (betriebsart == false) {
                System.out.println("huso");
                startAccounting(selection, gebLauf);
            }

            final LaufResultPresentationModel model = new LaufResultPresentationModel(selection, "Lauf Ergebnis");
            final LaufResultView laufResultView = new LaufResultView(model);



            model.addButtonListener(new ButtonListener() {

                public void buttonPressed(ButtonEvent evt) {
                    GUIManager.changeToLastView();
                }
            });
            GUIManager.changeView(laufResultView.buildPanel(), true);


        }
    }

    private void startAccounting(BewohnerTarifSelection selection, GebLauf gebLauf) {

        GebLaufManager gebLaufManager= GebLaufManager.getInstance();
        if(gebLaufManager.existGebLauf(gebLauf.getAbrMonat())==false){

            GebLaufSelection gebLaufSelection= new GebLaufSelection();
            List<Bewohner> bewList=gebLaufSelection.selectBewohnerList(gebLauf.getAbrMonat());

            boolean error=checkError(selection, bewList);

            if(error=false){
                gebLaufManager.save(gebLauf);
                
                for(int i=0;i<bewList.size();i++){
                    List<GebTarifSelection> gebTarifSelection=selection.get(bewList.get(i));

                    PostingManager postingManager = PostingManager.getInstance();
                    BuchungsLaufZuordnungManager blzManager=BuchungsLaufZuordnungManager.getInstance();
                    for(int j=0;j<gebTarifSelection.size();j++){

                        BuchungsLaufZuordnung blz= new BuchungsLaufZuordnung();
                        Posting posting= new Posting();

                        posting.setCustomer(bewList.get(j).getCustomer());
                        posting.setPostingDate(gebLauf.getCpuDate());
                        posting.setPostingEntryDate(new Date(gebTarifSelection.get(j).getAbrMonat()));
                        posting.setAmount(gebTarifSelection.get(j).getTarif().getTarif());
                        posting.setLiabilities(false);
                        posting.setDescription(gebTarifSelection.get(j).getGebuehr().getGebuehr().getName());
                        //acc.setCategory(category);
                        postingManager.save(posting);
                        blz.setAccount(posting);
                        blz.setGebLauf(gebLauf);
                        blz.setGebuehr(gebTarifSelection.get(j).getGebuehr().getGebuehr());
                        blzManager.save(blz);

                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Schwerwiegender Fehler! Es wurde keine Buchung durchgeführt");
            }


        }


    }


    private boolean checkError(BewohnerTarifSelection selection, List<Bewohner> bewList) {

        for(int i=0;i<bewList.size();i++){

            List<GebTarifSelection> gebTarifSelection=selection.get(bewList.get(i));
            for(int j=0;j<gebTarifSelection.size();j++){
                if(gebTarifSelection.get(j).isNoTarifError()== true || gebTarifSelection.get(j).isMoreTarifError()==true){
                    return true;
                }
            }
        }
        return false;
    }

    private int findMonth() {

        String month = monatCbModel.getSelectedItem().toString();

        if (month.equals("Jänner")) {
            return 1;
        }
        if (month.equals("Februar")) {
            return 2;
        }
        if (month.equals("März")) {
            return 3;
        }
        if (month.equals("April")) {
            return 4;
        }
        if (month.equals("Mai")) {
            return 5;
        }
        if (month.equals("Juni")) {
            return 6;
        }
        if (month.equals("Juli")) {
            return 7;
        }
        if (month.equals("August")) {
            return 8;
        }
        if (month.equals("September")) {
            return 9;
        }
        if (month.equals("Oktober")) {
            return 10;
        }
        if (month.equals("November")) {
            return 11;
        }
        if (month.equals("Dezember")) {
            return 12;
        }
        return 0;

    }

    private class NormalLaufAction
            extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            laufart = true;
        }
    }

    private class StornoLaufAction
            extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            System.out.println("storno");
            laufart = false;
        }
    }

    private class TestLaufAction
            extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            betriebsart = false;
        }
    }

    private class EchtLaufAction
            extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            betriebsart = true;
        }
    }

    private class YearDocument extends PlainDocument {

        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {

            if (this.getLength() < 4) {

                boolean isChar = true;
                char[] charArray = str.toCharArray();
                for (int i = 0; i < charArray.length; i++) {

                    if (!Character.isDigit(charArray[i])) {
                        isChar = false;
                    }

                }

                if (isChar == true) {
                    super.insertString(offs, str, a);
                }
            }
        }
    }

    /**
     * @return the start
     */
    public Action getStart() {
        return start;
    }

    /**
     * @return the normalLauf
     */
    public Action getNormalLauf() {
        return normalLauf;
    }

    /**
     * @return the stornoLauf
     */
    public Action getStornoLauf() {
        return stornoLauf;
    }

    /**
     * @return the testLauf
     */
    public Action getTestLauf() {
        return testLauf;
    }

    /**
     * @return the echtLauf
     */
    public Action getEchtLauf() {
        return echtLauf;
    }
}
