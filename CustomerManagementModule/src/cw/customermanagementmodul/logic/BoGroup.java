package cw.customermanagementmodul.logic;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.logic.CWBo;
import cw.customermanagementmodul.persistence.Customer;
import cw.customermanagementmodul.persistence.Group;
import cw.customermanagementmodul.persistence.PMGroup;

/**
 * Business logic for group
 * 
 * @author Manuel Geier
 */
public class BoGroup extends CWBo<Group> {
	
	/**
	 * Basic constructor
	 * @param group Group
	 */
	public BoGroup(Group group) {
		super(group);
	}
	
	/**
	 * Returns the persistence object
	 */
	public Group getPersistence() {
		return (Group) getBaseClass();
	}
	
	/**
	 * Removes a group
	 * @param groupId Long
	 */
	public static void remove(Long groupId, EntityManager entityManager) {
		Group group = PMGroup.getInstance().get(groupId, entityManager);
		PMGroup.getInstance().remove(group);
	}
	
	/**
	 * Removes the group which is adapted from
	 */
	public void remove() {
		PMGroup.getInstance().remove(getPersistence());
	}
	
	/**
	 * Returns a list of groups where the customer is in
	 * @return
	 */
	public List<Group> getGroups(Customer customer) {
		return new ArrayList<Group>();
	}
	
}
