/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui;

/**
 *
 * @author André Salmhofer
 */

import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import cw.boardingschoolmanagement.manager.GUIManager;
import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
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
import cw.coursemanagementmodul.pojo.CourseParticipant;
import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.util.JRSaver;


/**
 *
 * @author André Salmhofer
 */
public class PrintPostingRunPresentationModel {

    private List<CourseParticipant> coursePartList;
    private List totalAmountList;
    private HeaderInfo headerInfo;
    private Action backAction;
    private ButtonListenerSupport support;

    private JasperReport jasperReport;
    private JasperReport courseReport;
    private JasperReport activityReport;
    private JasperReport subjectReport;
    private JRDataSource ds;
    private JasperPrint jasperPrint;
    private String reportSource;
    private Course course;

    private ArrayList coursePriceList;

    private double sollValue = 0.0;
    private double habenValue = 0.0;
    private double saldoValue = 0.0;

    private List<CourseAddition> courseAdditionList;

    public PrintPostingRunPresentationModel(List<CourseParticipant> list, HeaderInfo headerInfo, Course course) {
        this.coursePartList = list;
        this.headerInfo = headerInfo;
        this.course = course;
        totalAmountList = new ArrayList();
        courseAdditionList = new ArrayList<CourseAddition>();
        coursePriceList = new ArrayList();
        initModels();
        this.initEventHandling();
    }

    private void initEventHandling() {
    }

    private void initModels(){
        Format f = DateFormat.getDateInstance();
        createTotalAmountList();
        try {
            courseReport = JasperCompileManager.compileReport("./jasper/CourseTemplate.jrxml");
            activityReport = JasperCompileManager.compileReport("./jasper/ActivityTemplate.jrxml");
            subjectReport = JasperCompileManager.compileReport("./jasper/SubjectTemplate.jrxml");

            JRSaver.saveObject(activityReport, "./jasper/ActivityTemplate.jasper");
            JRSaver.saveObject(subjectReport, "./jasper/SubjectTemplate.jasper");
            
            HashMap param = new HashMap();
            param.put("courseName", course.getName());
            param.put("begin", f.format(course.getBeginDate()).toString());
            param.put("end", f.format(course.getEndDate()).toString());
            param.put("price", course.getPrice());
            param.put("currentDate", f.format(new Date()).toString());
            param.put("courseReport", courseReport);
            param.put("activityReport", activityReport);
            param.put("headerText", "Kursteilnehmerliste");
            param.put("coursePriceList", coursePriceList);
            param.put("totalAmountList", sollValue);
            param.put("totalHaben", habenValue);
            param.put("totalSaldo", saldoValue);
            param.put("sizeOfCourseParts", coursePartList.size());
            param.put("courseAdditionList", courseAdditionList);
            param.put("totalAmountList", totalAmountList);

            support = new ButtonListenerSupport();
            setBackAction(new BackAction());
            reportSource = "./jasper/PostingRunTemplate.jrxml";
            jasperReport = JasperCompileManager.compileReport(reportSource);
            ds = new JRBeanCollectionDataSource(coursePartList);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, ds);
        } catch (JRException ex) {
            Logger.getLogger(PrintCourseParticipantPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
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

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();
        }
    }

    public void createTotalAmountList(){
        for(int i = 0; i < coursePartList.size(); i++){
            totalAmountList.add(getPrice(coursePartList.get(i)));
        }
    }

    private double getPrice(CourseParticipant courseParticipant){
        double price = course.getPrice();

        CourseAddition courseAddition = new CourseAddition();

        for(int i = 0; i < courseParticipant.getCourseList().size(); i++){
            if(courseParticipant.getCourseList().get(i).getCourse().getId() == course.getId()){
                courseAddition = courseParticipant.getCourseList().get(i);
            }
        }

        for(int i = 0; i < courseAddition.getActivities().size(); i++){
            price += courseAddition.getActivities().get(i).getPrice();
        }
        return price;
    }
}

