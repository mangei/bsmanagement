package cw.accountmanagementmodul.comparator;

import cw.accountmanagementmodul.interfaces.PostingInterface;
import java.util.Comparator;

/**
 * Vergleicht zwei PostingInterfaces aufgrund des Erstellungsdatum.
 * @author ManuelG
 */
public class PostingInterfaceDateComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        PostingInterface p1 = (PostingInterface) o1;
        PostingInterface p2 = (PostingInterface) o2;
        
        return p1.getCreationDate().compareTo(p2.getCreationDate());
    }

}
