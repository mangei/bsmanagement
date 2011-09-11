package cw.boardingschoolmanagement.comparator;

import java.util.Comparator;

import cw.boardingschoolmanagement.interfaces.Priority;

/**
 *
 * @author ManuelG
 */
public class PriorityComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        Priority p1 = (Priority) o1;
        Priority p2 = (Priority) o2;
        if(p1.priority() < p2.priority()) {
            return 1;
        }
        return -1;
    }

}
