/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.gui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import cw.coursemanagementmodul.pojo.Course;

/**
 *
 * @author André Salmhofer
 */
public class CourseListModel implements ListModel{
    private List<Course> courseList;
    
    public CourseListModel(List<Course> list){
        courseList = list;
    }
    
    public CourseListModel(){
        courseList = new ArrayList<Course>();
    }
    
    public int getSize() {
        return courseList.size();
    }

    public Object getElementAt(int index) {
        return courseList.get(index);
    }
    
    public void addElement(Course course){
        courseList.add(course);
        System.out.println("adadasdadadadadadadadad**********");
    }

    public void addListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    public void removeListDataListener(ListDataListener l) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
