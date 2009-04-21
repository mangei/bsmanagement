package cw.boardingschoolmanagement.pojo;

import cw.boardingschoolmanagement.gui.component.JViewPanel.HeaderInfo;
import java.util.HashMap;

/**
 *
 * @author ManuelG
 */
public class PresentationModelProperties {

    private HashMap<String,Object> properties;

    public final static String HEADERINFO = "headerInfo";

    public PresentationModelProperties() {
        properties = new HashMap<String, Object>();
    }

    public HeaderInfo getHeaderInfo() {
        HeaderInfo headerInfo = (HeaderInfo) properties.get(HEADERINFO);
        if (headerInfo == null) {
            headerInfo = new HeaderInfo();
            properties.put(HEADERINFO, headerInfo);
        }
        return headerInfo;
    }

    public Object put(String key, Object value) {
        return properties.put(key, value);
    }

    public Object get(String key) {
        return properties.get(key);
    }

    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }

}
