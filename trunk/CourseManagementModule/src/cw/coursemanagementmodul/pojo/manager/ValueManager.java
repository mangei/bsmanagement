/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.pojo.manager;

import com.jgoodies.binding.list.SelectionInList;

import cw.coursemanagementmodul.pojo.Course;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.customermanagementmodul.persistence.model.CustomerModel;

/**
 *
 * @author André Salmhofer
 */
public class ValueManager {
    private static ValueManager instance;

    private ValueManager() {
    }

    public static ValueManager getInstance() {
        if(instance == null) {
            instance = new ValueManager();
        }
        return instance;
    }

    public double getTotalSoll(CustomerModel customer){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalValue = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getPosting().getCustomer().equals(customer)){
                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalValue += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalValue;
    }
    
    public double getTotalHaben(CustomerModel customer){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalValue = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getPosting().getCustomer().equals(customer)){
                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalValue += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalValue;
    }
    
    public double getTotalSaldo(CustomerModel customer){
        return getTotalSoll(customer)-getTotalHaben(customer);
    }

    public double getTotalSoll(CourseParticipant coursePart){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalValue = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getPosting().getCustomer().equals(coursePart.getCustomer())){
                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalValue += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalValue;
    }

    public double getTotalHaben(CourseParticipant coursePart){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalValue = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getPosting().getCustomer().equals(coursePart.getCustomer())){
                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalValue += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalValue;
    }

    public double getTotalSaldo(CourseParticipant coursePart){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalSoll = 0.0;
        double totalHaben = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getPosting().getCustomer().equals(coursePart.getCustomer())){
                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalSoll += coursePostingList.getList().get(i).getPosting().getAmount();
                }

                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalHaben += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalSoll-totalHaben;
    }

    public double getTotalSaldo(CourseAddition courseAddition){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalSoll = 0.0;
        double totalHaben = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getCourseAddition().getId() == courseAddition.getId()){
                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalSoll += coursePostingList.getList().get(i).getPosting().getAmount();
                }

                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalHaben += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalSoll-totalHaben;
    }

    public double getTotalSoll(CourseAddition courseAddition){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalSoll = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getCourseAddition().getId() == courseAddition.getId()){
                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalSoll += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalSoll;
    }

    public double getTotalHaben(CourseAddition courseAddition){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalHaben = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getCourseAddition().getId() == courseAddition.getId()){
                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalHaben += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalHaben;
    }

    //-----------------------------------------

    public double getTotalSaldo(Course course, CourseParticipant coursePart){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalSoll = 0.0;
        double totalHaben = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getCourseAddition().getCourse().getId() == course.getId()
                    && coursePostingList.getList().get(i).getPosting().getCustomer().getId() == coursePart.getCustomer().getId()){

                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalSoll += coursePostingList.getList().get(i).getPosting().getAmount();
                }

                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalHaben += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalSoll-totalHaben;
    }

    public double getTotalSoll(Course course, CourseParticipant coursePart){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalSoll = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getCourseAddition().getCourse().getId() == course.getId()
                    && coursePostingList.getList().get(i).getPosting().getCustomer().getId() == coursePart.getCustomer().getId()){
                if(coursePostingList.getList().get(i).getPosting().isLiabilities()){
                    totalSoll += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalSoll;
    }

    public double getTotalHaben(Course course, CourseParticipant coursePart){
        SelectionInList<CoursePosting> coursePostingList =
            new SelectionInList<CoursePosting>(CoursePostingManager.getInstance().getAll());
        double totalHaben = 0.0;
        for(int i = 0; i < coursePostingList.getList().size(); i++){
            if(coursePostingList.getList().get(i).getCourseAddition().getCourse().getId() == course.getId()
                    && coursePostingList.getList().get(i).getPosting().getCustomer().getId() == coursePart.getCustomer().getId()){
                if(coursePostingList.getList().get(i).getPosting().isAssets()){
                    totalHaben += coursePostingList.getList().get(i).getPosting().getAmount();
                }
            }
        }
        return totalHaben;
    }
}
