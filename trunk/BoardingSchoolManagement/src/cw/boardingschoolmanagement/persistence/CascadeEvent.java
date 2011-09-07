package cw.boardingschoolmanagement.persistence;

import java.util.EventObject;

import javax.persistence.EntityManager;

/**
 * Descripes an CascadeEvent.
 *
 * @see CascadeListener
 * @see CascadeListenerSupport
 * @see CascadeAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class CascadeEvent extends EventObject {

	private EntityManager entityManager;
	
    public static final int TYPE_DELETE = 3001;
//    public static final int TYPE_UPDATE = 3002;

//    public static final int REFERENTIAL_ACTION_CASCADE      = 3011;
//    public static final int REFERENTIAL_ACTION_RESTRICT     = 3012;
//    public static final int REFERENTIAL_ACTION_NO_ACTION    = 3013;
//    public static final int REFERENTIAL_ACTION_SET_NULL     = 3014;
//    public static final int REFERENTIAL_ACTION_SET_DEFAULT  = 3015;
    
//    private int referentialAction;
    private int type;

    public CascadeEvent(Object source, int type, EntityManager entityManager) {
        super(source);
        this.type = type;
        this.entityManager = entityManager;
//        this.referentialAction = referentialAction;
    }

    public int getType() {
        return type;
    }
    
    public EntityManager getEntityManager() {
		return entityManager;
	}

//    public int getReferentialAction() {
//        return referentialAction;
//    }
}
