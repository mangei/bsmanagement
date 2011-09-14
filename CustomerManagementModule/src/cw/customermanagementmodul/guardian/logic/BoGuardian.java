package cw.customermanagementmodul.guardian.logic;

import cw.boardingschoolmanagement.logic.CWBoPersistence;
import cw.customermanagementmodul.customer.logic.BoCustomer;
import cw.customermanagementmodul.guardian.persistence.Guardian;
import cw.customermanagementmodul.guardian.persistence.PMGuardian;

/**
 * Business logic for guardian
 * 
 * @author Manuel Geier
 */
public class BoGuardian extends CWBoPersistence<Guardian> {
	
	private Guardian guardian;
	
	/**
	 * Basic constructor
	 * @param customer Customer
	 */
	public BoGuardian(BoCustomer boCustomer) {
		super(boCustomer);
		
		guardian = PMGuardian.getInstance().getGuardianForCustomer(boCustomer.getPersistence().getId(), getEntityManager());
		
		if(guardian == null) {
			guardian = PMGuardian.getInstance().create(boCustomer.getPersistence(), getEntityManager());
		}
		
		setPersistence(guardian);
	}
	
}
