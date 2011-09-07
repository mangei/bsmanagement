package cw.accountmanagementmodul.pojo;

import com.jgoodies.binding.beans.Model;
import cw.boardingschoolmanagement.interfaces.AnnotatedClass;
import java.util.ArrayList;
import java.util.List;

/**
 * Ein PostingCycle (zD Buchungskreis) ist eine Zusammenfassung mehrerer Buchungen
 * die auf einmal gebucht werden. Ein Buchungskreis hat eine eindeutige Nummer
 * eine Bezeichnung zur besseren Nachvollziehbarkeit und die Liste der Buchungen
 * die zu diesem Buchungskreis gehoeren.
 * @author ManuelG
 */
public class PostingCycle
        extends Model
        implements AnnotatedClass {

    private Long id                 = null;
    private String name             = "";
    private List<AccountPosting> accountPostings  = new ArrayList<AccountPosting>();

    public final static String PROPERTYNAME_ID          = "id";
    public final static String PROPERTYNAME_NAME        = "name";
    public final static String PROPERTYNAME_POSTINGS    = "postings";



}
