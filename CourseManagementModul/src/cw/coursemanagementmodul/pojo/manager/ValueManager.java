/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cw.coursemanagementmodul.pojo.manager;

import com.jgoodies.binding.list.SelectionInList;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.CourseParticipant;

/**
 *
 * @author André Salmhofer
 */
public class ValueManager {
    public static double getTotalSoll(CourseParticipant coursePart){
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

    public static double getTotalHaben(CourseParticipant coursePart){
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

    public static double getTotalSaldo(CourseParticipant coursePart){
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

    public static double getTotalSaldo(CourseAddition courseAddition){
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

    public static double getTotalSoll(CourseAddition courseAddition){
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

    public static double getTotalHaben(CourseAddition courseAddition){
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
}
