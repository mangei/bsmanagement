package cw.boardingschoolmanagement.app;

/**
 * Descripes an CascadeEvent.
 *
 * @see CascadeListener
 * @see CascadeListenerSupport
 * @see CascadeAdapter
 *
 * @author Manuel Geier (CreativeWorkers)
 */
public class CascadeEvent{

    public static final int TYPE_DELETE = 1;
//    public static final int TYPE_UPDATE = 2;

//    public static final int REFERENTIAL_ACTION_CASCADE      = 11;
//    public static final int REFERENTIAL_ACTION_RESTRICT     = 12;
//    public static final int REFERENTIAL_ACTION_NO_ACTION    = 13;
//    public static final int REFERENTIAL_ACTION_SET_NULL     = 14;
//    public static final int REFERENTIAL_ACTION_SET_DEFAULT  = 15;
    
    private Object object;
//    private int referentialAction;
    private int type;

    public CascadeEvent(Object object, int type) {
        this.object = object;
        this.type = type;
//        this.referentialAction = referentialAction;
    }

    public Object getObject() {
        return object;
    }

    public int getType() {
        return type;
    }

//    public int getReferentialAction() {
//        return referentialAction;
//    }
}
