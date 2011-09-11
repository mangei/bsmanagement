package cw.boardingschoolmanagement.extention.point;

import javax.persistence.EntityManager;

import cw.boardingschoolmanagement.extention.CWIExtention;

/**
 *
 * @author Manuel Geier
 */
public interface CWViewExtentionPoint<TView>
        extends CWIExtention {

	public Class getViewExtentionClass();
    public void init(TView baseView, EntityManager entityManager);
}
