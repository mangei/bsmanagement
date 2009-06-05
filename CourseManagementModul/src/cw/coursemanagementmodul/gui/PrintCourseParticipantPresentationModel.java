package cw.coursemanagementmodul.gui;

import cw.boardingschoolmanagement.app.ButtonListener;
import cw.boardingschoolmanagement.app.ButtonListenerSupport;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
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
public class PrintCourseParticipantPresentationModel {

    private List<CourseParticipant> coursePartList;
    private List<CourseParticipant> myCoursePartList;
    private CWHeaderInfo headerInfo;
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

    public PrintCourseParticipantPresentationModel(List<CourseParticipant> list, CWHeaderInfo headerInfo, Course course) {
        myCoursePartList = new ArrayList<CourseParticipant>();
        this.coursePartList = list;
        this.headerInfo = headerInfo;
        this.course = course;

        initModels();
        initEventHandling();
    }


    private void initModels(){
        Format f = DateFormat.getDateInstance();
        try {
            courseReport = JasperCompileManager.compileReport("./jasper/CourseTemplate.jrxml");
            activityReport = JasperCompileManager.compileReport("./jasper/ActivityTemplate.jrxml");
            subjectReport = JasperCompileManager.compileReport("./jasper/SubjectTemplate.jrxml");

            createCourseAddtionList();

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
            
            support = new ButtonListenerSupport();
            backAction = new BackAction();
            reportSource = "./jasper/CoursePartTemplate.jrxml";
            jasperReport = JasperCompileManager.compileReport(reportSource);
            ds = new JRBeanCollectionDataSource(myCoursePartList);
            jasperPrint = JasperFillManager.fillReport(jasperReport, param, ds);
        } catch (JRException ex) {
            Logger.getLogger(PrintCourseParticipantPresentationModel.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    private void initEventHandling() {
    }

    public void dispose() {
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

    private class BackAction
            extends AbstractAction {

        {
            putValue(Action.SMALL_ICON, CWUtils.loadIcon("cw/coursemanagementmodul/images/back.png"));
        }

        public void actionPerformed(ActionEvent e) {
            GUIManager.changeToLastView();
        }
    }

    private void createCourseAddtionList(){
        for(int i = 0; i < coursePartList.size(); i++){
            CourseParticipant cP = new CourseParticipant(coursePartList.get(i).getCustomer());
            List<CourseAddition> courseList = new ArrayList<CourseAddition>();
            for(int j = 0; j < coursePartList.get(i).getCourseList().size(); j++){
                if(coursePartList.get(i).getCourseList().get(j).getCourse().getId() == course.getId()){
                    courseList.add(coursePartList.get(i).getCourseList().get(j));
                }
            }
            cP.setCourseList(courseList);
            myCoursePartList.add(cP);
        }
    }
}
