package cw.boardingschoolmanagement.app;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ManuelG
 */
public class ObjectSaver {

    private ObjectSaver() {
    }

    public static void save(Object bean, String fileName) {
        AbortExceptionListener el = new AbortExceptionListener();
        XMLEncoder e = null;
        ByteArrayOutputStream bst = new ByteArrayOutputStream();
        try {
            e = new XMLEncoder(bst);
            e.writeObject(bean);
        }
        catch(Exception ex1) {
            ex1.printStackTrace();
        }
        finally {
            if (e != null) {
                e.close();
            }
        }
        if (el.exception != null) {
            System.out.println("save failed \"" + fileName + "\"");
        }
        OutputStream ost = null;
        try {
            File path = new File(fileName);
            try {
                ost = new BufferedOutputStream(new FileOutputStream(path));
                ost.write(bst.toByteArray());
            } catch (IOException e2) {
                System.out.println("couldn't open output file \"" + fileName + "\"");
            }
            
        } finally {
            if (ost != null) {
                try {
                    ost.close();
                } catch (IOException ex) {
                    Logger.getLogger(ObjectSaver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public static Object load(String fileName) {
        File path = new File(fileName);
        if(!path.exists()) return null;

        FileInputStream ist = null;
        try {
            ist = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ObjectSaver.class.getName()).log(Level.SEVERE, null, ex);
        }

        AbortExceptionListener el = new AbortExceptionListener();
        XMLDecoder d = null;
        try {
            d = new XMLDecoder(ist);
            d.setExceptionListener(el);
            Object bean = d.readObject();
            if (el.exception != null) {
                System.out.println("load failed \"" + fileName + "\"");
            }
            return bean;
        } catch (Exception e) {
            System.out.println("ex" + e.getMessage());
            e.printStackTrace();
        }
        finally {
            if (d != null) {
                d.close();
            }
        }
        return null;
    }

    /* If an exception occurs in the XMLEncoder/Decoder, we want
     * to throw an IOException.  The exceptionThrow listener method
     * doesn't throw a checked exception so we just set a flag
     * here and check it when the encode/decode operation finishes
     */
    private static class AbortExceptionListener implements ExceptionListener {

        public Exception exception = null;

        public void exceptionThrown(Exception e) {
            if (exception == null) {
                exception = e;
            }
        }
    }

//    public static void save3(Object obj, String path) {
//        System.out.println("AAAAAA: " + obj + " " + path);
//        try {
//            GUIManager.getInstance().getContext().getLocalStorage().setDirectory(new File(directory));
//            GUIManager.getInstance().getContext().getLocalStorage().save(obj, path);
//        } catch (IOException ex) {
//            Logger.getLogger(ObjectSaver.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }
//
//    public static Object load3(String path) {
//        try {
//            Object obj = null;
//            GUIManager.getInstance().getContext().getLocalStorage().setDirectory(new File(directory));
//            GUIManager.getInstance().getContext().getLocalStorage().load(path);
//            System.out.println("BBBBBB: " + obj);
//            return obj;
//        } catch (Exception ex) {
//            Logger.getLogger(ObjectSaver.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
//
//    public static void save2(Object obj, String path) {
//        try {
//            FileOutputStream fos = new FileOutputStream(path);
//
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//            oos.writeObject(obj);
//
//            oos.close();
//            fos.close();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    public static Object load2(String path) {
//        Object obj = null;
//
//        try {
//            FileInputStream fis = new FileInputStream(path);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//
//            obj = ois.readObject();
//
//            ois.close();
//            fis.close();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//
//        return obj;
//    }
}
