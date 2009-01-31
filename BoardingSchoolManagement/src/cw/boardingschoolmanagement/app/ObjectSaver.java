package cw.boardingschoolmanagement.app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ManuelG
 */
public class ObjectSaver {

    private ObjectSaver() {
    }

    public static void save(Object obj, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(path);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            
            oos.writeObject(obj);

            oos.close();
            fos.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static Object load(String path) {
        Object obj = null;

        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);

            obj = ois.readObject();

            ois.close();
            fis.close();
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }

        return obj;
    }
}
