package cw.roommanagementmodul.gui;

import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import com.jgoodies.binding.list.SelectionInList;

import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.accountmanagementmodul.pojo.manager.PostingManager;
import cw.boardingschoolmanagement.app.ButtonEvent;
import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.app.CalendarUtil;
import cw.boardingschoolmanagement.gui.CWEditPresentationModel;
import cw.boardingschoolmanagement.gui.component.CWIntegerTextField;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.roommanagementmodul.app.GebLaufSelection;
import cw.roommanagementmodul.geblauf.BewohnerTarifSelection;
import cw.roommanagementmodul.geblauf.GebTarifSelection;
import cw.roommanagementmodul.persistence.Bewohner;
import cw.roommanagementmodul.persistence.BuchungsLaufZuordnung;
import cw.roommanagementmodul.persistence.GebLauf;
import cw.roommanagementmodul.persistence.PMBuchungsLaufZuordnung;
import cw.roommanagementmodul.persistence.PMGebLauf;

/**
 *
 * @author Dominik
 */
public class GebLaufPresentationModel
	extends CWEditPresentationModel<GebLaufSelection>
{

    private GebLaufSelection gebLauf;
    private Action start;
    private Action normalLauf;
    private Action stornoLauf;
    private Action testLauf;
    private Action echtLauf;
    private FocusAdapter yearFocus;
    private Document yearDocument;
    private SelectionInList monatList;
    private List mList;
    private boolean laufart = true;
    private boolean betriebsart = true;
    private SelectionInList<GebLauf> gebLaufList;
    private PMGebLauf gebLaufManager;
    private int stornoInt = 1;
    private CWHeaderInfo headerInfo;
    private Integer year;

    public GebLaufPresentationModel(GebLaufSelection gebLauf, CWHeaderInfo header) {
        super(gebLauf);
        this.gebLauf = gebLauf;
        this.headerInfo = header;
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
        monatList = new SelectionInList();
        yearFocus = new YearFocus();
        setYearDocument(new YearDocument());
        GregorianCalendar gc = new GregorianCalendar();
        year = new Integer(gc.get(Calendar.YEAR));

        gebLaufManager = PMGebLauf.getInstance();
        gebLaufList = new SelectionInList<GebLauf>(gebLaufManager.getAllOrdered());
        mList = new ArrayList();

        mList.add("Jaenner");
        mList.add("Februar");
        mList.add("Maerz");
        mList.add("April");
        mList.add("Mai");
        mList.add("Juni");
        mList.add("Juli");
        mList.add("August");
        mList.add("September");
        mList.add("Oktober");
        mList.add("November");
        mList.add("Dezember");
        monatList.setList(mList);
    }

    /**
     * @return the monatCbModel
     */
    public SelectionInList getMonatCbModel() {
        return monatList;
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

    /**
     * @return the gebLaufList
     */
    public SelectionInList<GebLauf> getGebLaufList() {
        return gebLaufList;
    }

    /**
     * @param gebLaufList the gebLaufList to set
     */
    public void setGebLaufList(SelectionInList<GebLauf> gebLaufList) {
        this.gebLaufList = gebLaufList;
    }

    /**
     * @return the laufart
     */
    public boolean isLaufart() {
        return laufart;
    }

    /**
     * @param laufart the laufart to set
     */
    public void setLaufart(boolean laufart) {
        this.laufart = laufart;
    }

    /**
     * @return the stornoInt
     */
    public int getStornoInt() {
        return stornoInt;
    }

    /**
     * @param stornoInt the stornoInt to set
     */
    public void setStornoInt(int stornoInt) {
        this.stornoInt = stornoInt;
    }

    /**
     * @return the headerInfo
     */
    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public void dispose() {
        gebLaufList.release();
        monatList.release();
        release();
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    private class StartAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/roommanagementmodul/images/lightning.png"));
        }

        public void actionPerformed(ActionEvent e) {

            if (laufart == true && !monatList.isSelectionEmpty()) {
                startGebLauf();
            }
            if (laufart == false) {
                startStornoLauf();
            }
        }
    }

    private void startStornoLauf() {
        GebLauf stornoGebLauf = gebLaufList.getSelection();

        int checkGO = JOptionPane.YES_OPTION;
        if (betriebsart == true) {
            checkGO = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich im Echtlauf starten?", "ACHTUNG!", JOptionPane.YES_NO_OPTION);
        }

        if (checkGO == JOptionPane.YES_OPTION) {
            if (stornoGebLauf != null) {

                PMBuchungsLaufZuordnung blzManager = PMBuchungsLaufZuordnung.getInstance();
                List<BuchungsLaufZuordnung> blzList = blzManager.getBuchungsLaufZuordnung(stornoGebLauf);

                List<AccountPosting> newPostingList = new ArrayList<AccountPosting>();

                for (int i = 0; i < blzList.size(); i++) {

                    AccountPosting oldPosting = blzList.get(i).getPosting();
                    AccountPosting newPosting = new AccountPosting();

                    newPosting.setCustomer(oldPosting.getCustomer());
                    newPosting.setDescription("Storno " + oldPosting.getDescription());
                    newPosting.setAmount(oldPosting.getAmount() * (-1));
                    newPosting.setAssets(oldPosting.isAssets());
                    newPosting.setLiabilities(oldPosting.isLiabilities());
                    newPosting.setLiabilitiesAssets(oldPosting.isLiabilitiesAssets());
                    newPosting.setPostingCategory(oldPosting.getPostingCategory());
                    newPosting.setPostingDate(new Date());
                    newPosting.setPostingEntryDate(new Date(stornoGebLauf.getAbrMonat()));

                    if (betriebsart == true) {
                        PostingManager postingManager = PostingManager.getInstance();
                        postingManager.save(newPosting);
                    }
                    newPostingList.add(newPosting);
                }

                if (betriebsart == true) {
                    gebLaufManager.delete(stornoGebLauf);
                    for (BuchungsLaufZuordnung blz : blzList) {
                        blzManager.delete(blz);
                    }
                    gebLaufList.setList(gebLaufManager.getAllOrdered());

                }
                Calendar c = new GregorianCalendar();
                c.setTimeInMillis(stornoGebLauf.getAbrMonat());
                String monthStr = CalendarUtil.getMonthName(c.get(Calendar.MONTH));
                int yearInt = c.get(Calendar.YEAR);

                String laufString;
                if (betriebsart == true) {
                    laufString = new String("Storno Lauf - " + monthStr + " " + yearInt);
                } else {
                    laufString = new String("Storno Test Lauf - " + monthStr + " " + yearInt);
                }


                final StornoResultPresentationModel model = new StornoResultPresentationModel(newPostingList, new CWHeaderInfo(laufString, "Ergebnis des Storno Lauf"));
                final StornoResultView laufResultView = new StornoResultView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        GUIManager.changeToPreviousView();
                    }
                });
                GUIManager.changeViewTo(laufResultView, true);

            } else {
                JOptionPane.showMessageDialog(null, "Fuer den Storno-Lauf muss ein Gebuehrenlauf ausgewaehlt sein!");
            }
        }

    }

    private void startGebLauf() {
        int checkGO = JOptionPane.YES_OPTION;
        if (betriebsart == true) {
            checkGO = JOptionPane.showConfirmDialog(null, "Wollen Sie wirklich im Echtlauf starten?", "ACHTUNG!", JOptionPane.YES_NO_OPTION);
        }

        if (checkGO == JOptionPane.YES_OPTION) {

            int month = findMonth();
//
//            try {
//                year = Integer.parseInt(yearDocument.getText(0, yearDocument.getLength()));
//            } catch (BadLocationException ex) {
//                Logger.getLogger(GebLaufPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
//            }
            GregorianCalendar gc = new GregorianCalendar(year, month - 1, 1);
            GebLauf gebLauf = new GebLauf(gc.getTime().getTime(), new Date(), betriebsart);

            GebLaufSelection gebLaufSelection = new GebLaufSelection();
            BewohnerTarifSelection selection = gebLaufSelection.startSelection(gebLauf.getAbrMonat());

            boolean checkAccounting = true;
            if (betriebsart == true) {
                checkAccounting = startAccounting(selection, gebLauf);
                gebLaufList.setList(gebLaufManager.getAllOrdered());
            }

            if (checkAccounting == true) {
                Calendar c = new GregorianCalendar();
                c.setTimeInMillis(gebLauf.getAbrMonat());
                String monthStr = CalendarUtil.getMonthName(c.get(Calendar.MONTH));
                int yearInt = c.get(Calendar.YEAR);

                String laufString;
                if (betriebsart == true) {
                    laufString = new String("Gebuehren Lauf - " + monthStr + " " + yearInt);
                } else {
                    laufString = new String("Test Lauf - " + monthStr + " " + yearInt);
                }

                final LaufResultPresentationModel model = new LaufResultPresentationModel(selection, new CWHeaderInfo(laufString, "Ergebnis des Gebuehren Lauf"));
                final LaufResultView laufResultView = new LaufResultView(model);
                model.addButtonListener(new ButtonListener() {

                    public void buttonPressed(ButtonEvent evt) {
                        GUIManager.changeToPreviousView();
                    }
                });
                GUIManager.changeViewTo(laufResultView, true);

            }
        }
    }

    private boolean startAccounting(BewohnerTarifSelection selection, GebLauf gebLauf) {

        gebLaufManager = PMGebLauf.getInstance();
        if (gebLaufManager.existGebLauf(gebLauf.getAbrMonat()) == false) {

            GebLaufSelection gebLaufSelection = new GebLaufSelection();
            List<Bewohner> bewList = gebLaufSelection.selectBewohnerList(gebLauf.getAbrMonat());

            boolean error = checkError(selection, bewList);

            if (error == false) {
                gebLaufManager.save(gebLauf);

                for (int i = 0; i < bewList.size(); i++) {
                    List<GebTarifSelection> gebTarifSelection = selection.get(bewList.get(i));


                    PostingManager postingManager = PostingManager.getInstance();
                    PMBuchungsLaufZuordnung blzManager = PMBuchungsLaufZuordnung.getInstance();
                    for (int j = 0; j < gebTarifSelection.size(); j++) {

                        if (!gebTarifSelection.get(j).isWarning()) {
                            BuchungsLaufZuordnung blz = new BuchungsLaufZuordnung();
                            AccountPosting accountPosting = new AccountPosting();
                            PostingCategory category = PostingCategoryManager.getInstance().get("zimmer");

                            accountPosting.setCustomer(bewList.get(i).getCustomer());
                            accountPosting.setPostingDate(gebLauf.getCpuDate());
                            accountPosting.setPostingEntryDate(new Date(gebLauf.getAbrMonat()));
                            accountPosting.setAmount(gebTarifSelection.get(j).getTarif().getTarif());
                            accountPosting.setLiabilities(true);
                            accountPosting.setDescription(gebTarifSelection.get(j).getGebuehr().getGebuehr().getName());
                            accountPosting.setPostingCategory(category);

                            postingManager.save(accountPosting);
                            blz.setPosting(accountPosting);
                            blz.setGebLauf(gebLauf);
                            blz.setGebuehr(gebTarifSelection.get(j).getGebuehr().getGebuehr());
                            blzManager.save(blz);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Schwerwiegender Fehler! \nEs wurde keine Buchung durchgefuehrt", "Fehler", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Mit diesem Datum wurde bereits ein Gebuehrenlauf durchgefuehrt!");
            return false;
        }

        return true;

    }

    private boolean checkError(BewohnerTarifSelection selection, List<Bewohner> bewList) {

        for (int i = 0; i < bewList.size(); i++) {

            List<GebTarifSelection> gebTarifSelection = selection.get(bewList.get(i));
            for (int j = 0; j < gebTarifSelection.size(); j++) {

                if (gebTarifSelection.get(j).isNoTarifError() == true || gebTarifSelection.get(j).isMoreTarifError() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    private int findMonth() {

        String month = monatList.getSelection().toString();

        if (month.equals("Jaenner")) {
            return 1;
        }
        if (month.equals("Februar")) {
            return 2;
        }
        if (month.equals("Maerz")) {
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
            stornoInt++;
            setLaufart(true);
        }
    }

    private class StornoLaufAction
            extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            stornoInt++;
            setLaufart(false);
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
    /**
     *
     * @return yearFocus
     */
    public FocusAdapter getYearFocus(){
        return yearFocus;
    }


    /**
     * FocusAdapter der die Eingabe die im Jahresfeld in der GebLaufView
     * getaetigt wurde in eine Variable speichert.
     * Wird ausgeloest sobald das bearbeitet Feld den Focus verliert.
     */
    private class YearFocus
            extends FocusAdapter {

        @Override
        public void focusLost(FocusEvent e) {
            CWIntegerTextField cwITF =(CWIntegerTextField) e.getSource();
            setYear(Integer.parseInt(cwITF.getText()));
        }
    }
}
