package cw.boardingschoolmanagement.gui;

import com.jgoodies.binding.list.SelectionInList;
import cw.boardingschoolmanagement.app.CWUtils;
import cw.boardingschoolmanagement.gui.component.CWView.CWHeaderInfo;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 *
 * @author ManuelG
 */
public class UpdateConfigurationPresentationModel
{

    private ConfigurationPresentationModel configurationPresentationModel;
    private CWHeaderInfo headerInfo;
    private Action checkAction;
    private Action updateAction;
    private TableModel updatesTableModel;
    private SelectionInList<UpdateInfo> updateInfoList;

    public UpdateConfigurationPresentationModel(ConfigurationPresentationModel configurationPresentationModel) {
        this.configurationPresentationModel = configurationPresentationModel;
        initModels();
        initEventHandling();
    }

    private void initModels() {
        headerInfo = new CWHeaderInfo(
                "Aktualisieren",
                "Bringen Sie Ihr System auf dem aktuellen Stand.",
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/update.png"),
                CWUtils.loadIcon("cw/boardingschoolmanagement/images/update.png")
        );

        checkAction = new CheckAction("Jetzt pruefen!");
        updateAction = new UpdateAction("Jetzt aktualisieren!");

        updateInfoList = new SelectionInList<UpdateInfo>();
        updatesTableModel = new UpdatesTableModel(updateInfoList);
    }

    private void initEventHandling() {
    }

    public void dispose() {
    }

    public List<String> validate() {
        return null;
    }

    public class SaveListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            updateState();
        }

        public void updateState() {
            configurationPresentationModel.setChanged(true);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Action classes
    ////////////////////////////////////////////////////////////////////////////

    private class CheckAction
            extends AbstractAction {

        private CheckAction(String name) {
            super(name);
        }

        private CheckAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {
            InputStream in = null;
            try {
                // Update URL
                in = new URL("http://bsmanagement.sourceforge.net/bsmanagement/version.xml").openStream();

                // Erzeuge den XML - Leser
                SAXBuilder builder = new SAXBuilder();
                try {
                    // Lies das XML
                    Document doc = builder.build(in);
                    Element root = doc.getRootElement();

// Update Info
//<bsmanagement>
//    <modul name="cw.boardingschoolmanagement.core">
//        <name>Boarding School Management - v1.0.0</name>
//        <description>Boarding School Management - v1.0.0</description>
//        <version>1.0.0</version>
//        <release-date></release-date>
//        <build-date>12-12-2008 23:45</build-date>
//        <update-information></update-information>
//        <download-link>http://bsmanagement.sourceforge.net/bsmanagement/release/bsmanagement_1-0-0.jar</download-link>
//    </modul>
//</bsmanagement>

                    // Get the update information of the modules
                    List<Element> children = root.getChildren();
                    
                    for(int i=0, l=children.size(); i<l; i++) {

                        Element modulElement = children.get(i);
                        Element titleElement        = modulElement.getChild("name");
                        Element versionElement      = modulElement.getChild("version");
                        Element releaseDateElement  = modulElement.getChild("release-date");
                        Element buildDateElement    = modulElement.getChild("build-date");
                        Element updateInfoElement   = modulElement.getChild("update-information");
                        Element downloadURLElement  = modulElement.getChild("download-link");

                        Date releaseDate = null;
                        try {
                            releaseDate = DateFormat.getInstance().parse(releaseDateElement.getText());
                        } catch (ParseException ex) {
                            System.out.println("releaseDate: " + releaseDateElement.getText());
                            ex.printStackTrace();
                            releaseDate = new Date();
                        }
                        Date buildDate = null;
                        try {
                            buildDate = DateFormat.getInstance().parse(buildDateElement.getText());
                        } catch (ParseException ex) {
                            System.out.println("buildDate: " + buildDateElement.getText());
                            ex.printStackTrace();
                            buildDate = new Date();
                        }
                        URL downloadURL = null;
                        try {
                            downloadURL = new URL(downloadURLElement.getText());
                        } catch (MalformedURLException malformedURLException) {
                            System.out.println("downloadUrl: " + downloadURLElement.getText());
                            malformedURLException.printStackTrace();
                        }

                        System.out.println("up:" + updateInfoList);

                        updateInfoList.getList().add(
                                new UpdateInfo(
                                    titleElement.getText(),
                                    versionElement.getText(),
                                    "1.1.3",
                                    releaseDate,
                                    buildDate,
                                    downloadURL
                                )
                        );

                    }

                } catch (JDOMException ex) {
                    // XML-Datei Fehler
                    ex.printStackTrace();
                }

            } catch (MalformedURLException ex) {
                // URL hat falsches Format
                ex.printStackTrace();
            } catch (IOException ex) {
                // URL konnte nicht geladen werden
                ex.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    // Es konnte nicht geschlossen werden
                    ex.printStackTrace();
                }
            }
        }
    }

    private class UpdateAction
            extends AbstractAction {

        private UpdateAction(String name) {
            super(name);
        }

        private UpdateAction(String name, Icon icon) {
            super(name, icon);
        }

        public void actionPerformed(ActionEvent e) {

            for(int i=0, l=updateInfoList.getSize(); i<l; i++) {
                
                UpdateInfo info = updateInfoList.getList().get(i);
                System.out.println(info);

                FileInputStream in = null;
                FileOutputStream out = null;
                try {
                    System.out.println("ses:" + info.getDownloadURL());
                    // Update URL
                    in = (FileInputStream) info.getDownloadURL().openStream();

                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                    System.out.println("file: "
                             + info.getDownloadURL().getFile());

                    out = new FileOutputStream("tmp" + System.getProperty("file.separator") + info.getDownloadURL().getFile());
                    byte[] b = new byte[1024];
                    int len;
                    while((len=in.read(b)) != -1) {
                        out.write(b,0,len);
                    }

                } catch (MalformedURLException ex) {
                    // URL hat falsches Format
                    ex.printStackTrace();
                } catch (IOException ex) {
                    // URL konnte nicht geladen werden
                    ex.printStackTrace();
                } finally {
                    try {
                        in.close();
                        if(out != null) {
                            out.close();
                        }
                    } catch (IOException ex) {
                        // Es konnte nicht geschlossen werden
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private class UpdatesTableModel
            extends AbstractTableModel {

        private SelectionInList<UpdateInfo> updateInfoList;

        public UpdatesTableModel(SelectionInList<UpdateInfo> updateInfos) {
            this.updateInfoList = updateInfos;
        }

        @Override
        public int getRowCount() {
            return updateInfoList.getList().size();
        }

        @Override
        public int getColumnCount() {
            return 3;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            UpdateInfo info = updateInfoList.getList().get(rowIndex);
            switch(columnIndex) {
                case 0:
                    return info.getName();
                case 1:
                    return info.getVersion();
                case 2:
                    return 1;
            }
            return "";
        }

        @Override
        public String getColumnName(int column) {
            switch(column) {
                case 0:
                    return "Modul";
                case 1:
                    return "Version";
                case 2:
                    return "Status";
            }
            return "";
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch(columnIndex) {
                case 2:
                    return Integer.class;
                default:
                    return String.class;
            }
        }
        
    }

    private class UpdateInfo {
        private String name;
        private String version;
        private String systemVersion;
        private Date releaseDate;
        private Date buildDate;
        private URL downloadURL;

        public UpdateInfo(String name, String version, String systemVersion, Date releaseDate, Date buildDate, URL downloadURL) {
            this.name = name;
            this.version = version;
            this.systemVersion = systemVersion;
            this.releaseDate = releaseDate;
            this.buildDate = buildDate;
            this.downloadURL = downloadURL;
        }

        public Date getBuildDate() {
            return buildDate;
        }

        public URL getDownloadURL() {
            return downloadURL;
        }

        public String getName() {
            return name;
        }

        public Date getReleaseDate() {
            return releaseDate;
        }

        public String getVersion() {
            return version;
        }

        public String getSystemVersion() {
            return systemVersion;
        }

        @Override
        public String toString() {
            StringBuffer str = new StringBuffer();
            str.append("URL: ");
            str.append(downloadURL);
            return str.toString();
        }

    }


    public CWHeaderInfo getHeaderInfo() {
        return headerInfo;
    }

    public Action getCheckAction() {
        return checkAction;
    }

    public Action getUpdateAction() {
        return updateAction;
    }

    public TableModel getUpdatesTableModel() {
        return updatesTableModel;
    }

    
}
