package cw.accountmanagementmodul.comparator;

import cw.accountmanagementmodul.pojo.AbstractPosting;
import java.util.Comparator;

/**
 * Vergleicht zwei AbstractPostings aufgrund des Erstellungsdatum.
 * @author ManuelG
 */
public class AbstractPostingDateComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        AbstractPosting p1 = (AbstractPosting) o1;
        AbstractPosting p2 = (AbstractPosting) o2;
        
        return p1.getCreationDate().compareTo(p2.getCreationDate());
    }

}
