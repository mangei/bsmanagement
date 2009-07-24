package cw.coursemanagementmodul.extention;

import cw.boardingschoolmanagement.gui.component.CWPanel;
import cw.coursemanagementmodul.gui.CourseParticipantForPostingPresentationModel;
import cw.coursemanagementmodul.gui.CourseParticipantForPostingView;
import cw.coursemanagementmodul.pojo.CourseAddition;
import cw.coursemanagementmodul.pojo.CourseParticipant;
import cw.coursemanagementmodul.pojo.CoursePosting;
import cw.coursemanagementmodul.pojo.manager.CourseParticipantManager;
import cw.coursemanagementmodul.pojo.manager.CoursePostingManager;
import cw.customermanagementmodul.gui.EditPostingPresentationModel;
import cw.customermanagementmodul.gui.EditReversePostingPresentationModel;
import cw.customermanagementmodul.pojo.Customer;
import cw.customermanagementmodul.pojo.Posting;
import cw.customermanagementmodul.extention.point.EditPostingPostingCategoryExtention;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author André Salmhofer (CreativeWorkers)
 */
public class CoursePostingEditPostingPostingCategoryExtention implements EditPostingPostingCategoryExtention {
    private CourseParticipantForPostingPresentationModel model;
    private EditPostingPresentationModel postingModel;
    private CourseParticipantForPostingView view;
    
    public String getKey(){
        return "Kurs-Buchung";
    }

    public void initPresentationModel(EditPostingPresentationModel postingModel){
        this.postingModel = postingModel;
        Customer customer = ((Posting)postingModel.getBean()).getCustomer();
        List<CourseParticipant> courseParticipants = CourseParticipantManager.getInstance().getAll(customer);
        model = new CourseParticipantForPostingPresentationModel(courseParticipants.get(0), postingModel.getUnsaved(), postingModel);
        view = new CourseParticipantForPostingView(model);
    }
    
    public void save(){
        double amount = 0.0;
        CourseAddition courseAddition = model.getCourseSelection().getSelection();
        amount = courseAddition.getAlreadyPayedAmount();
        amount += ((Posting)postingModel.getBean()).getAmount();
        courseAddition.setAlreadyPayedAmount(amount);//Teilzahlung wird gesetzt
        CoursePosting coursePosting = new CoursePosting();

        courseAddition.setPosted(true);

        coursePosting.setPosting(((Posting)postingModel.getBean()));
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
