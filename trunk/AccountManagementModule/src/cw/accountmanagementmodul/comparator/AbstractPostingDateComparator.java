package cw.accountmanagementmodul.comparator;

import cw.accountmanagementmodul.pojo.Posting;
import java.util.Comparator;

/**
 * Vergleicht zwei AbstractPostings aufgrund des Erstellungsdatum.
 * @author ManuelG
 */
public class AbstractPostingDateComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        Posting p1 = (Posting) o1;
        Posting p2 = (Posting) o2;
        
        return p1.getCreationDate().compareTo(p2.getCreationDate());
    }

}
