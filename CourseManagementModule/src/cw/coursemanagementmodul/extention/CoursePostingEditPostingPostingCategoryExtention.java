package cw.coursemanagementmodul.extention;

import java.util.ArrayList;
import java.util.List;

import cw.accountmanagementmodul.gui.EditPostingPresentationModel;
import cw.accountmanagementmodul.pojo.AccountPosting;
import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.coursemanagementmodul.gui.CourseParticipantForPostingPresentationModel;
import cw.coursemanagementmodul.gui.CourseParticipantForPostingView;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.customermanagementmodul.persistence.model.CustomerModel;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CoursePostingEditPostingPostingCategoryExtention implements EditPostingPostingCategoryExtentionPoint {
    private CourseParticipantForPostingPresentationModel model;
    private EditPostingPresentationModel postingModel;
    private CourseParticipantForPostingView view;
    
    public String getKey(){
        return "Kurs-Buchung";
    }

    public void initPresentationModel(EditPostingPresentationModel postingModel){
        this.postingModel = postingModel;
        CustomerModel customer = ((AccountPosting)postingModel.getBean()).getCustomer();
        List<CourseParticipant> courseParticipants = CourseParticipantManager.getInstance().getAll(customer);
        model = new CourseParticipantForPostingPresentationModel(courseParticipants.get(0), postingModel.getUnsaved(), postingModel);
        view = new CourseParticipantForPostingView(model);
    }
    
    public void save(){
        double amount = 0.0;
        CourseAddition courseAddition = model.getCourseSelection().getSelection();
        amount = courseAddition.getAlreadyPayedAmount();
        amount += ((AccountPosting)postingModel.getBean()).getAmount();
        courseAddition.setAlreadyPayedAmount(amount);//Teilzahlung wird gesetzt
        CoursePosting coursePosting = new CoursePosting();

        courseAddition.setPosted(true);

        coursePosting.setPosting(((AccountPosting)postingModel.getBean()));
        coursePosting.setCourseAddition(courseAddition);

        CoursePostingManager.getInstance().save(coursePosting);
    }

    public CWPanel getView() {
        return view;
    }

    public List<String> validate() {
        List<String> list = new ArrayList<String>();
        
        if(!model.getCourseSelection().hasSelection() && postingModel.getPostingCategorySelection().getSelection().getKey().equals("Kurs-Buchung")){
            list.add("Es wurden keine Zeile ausgewählt!");
        }

        return list;
    }

    public void initPresentationModel(EditReversePostingPresentationModel editReversePostingModel) {
        
    }

    public void dispose() {
        view.dispose();
    }
}
